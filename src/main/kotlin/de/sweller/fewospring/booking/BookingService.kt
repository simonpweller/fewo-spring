package de.sweller.fewospring.booking

import de.sweller.fewospring.email.EmailService
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.YearMonth

@Service
class BookingService(
        val bookingRepository: BookingRepository,
        val emailService: EmailService,
) {

    fun getBookings(): MutableIterable<Booking> = bookingRepository.findAll()

    fun getBooking(id: Long) = bookingRepository.findByIdOrNull(id)

    fun getBookedDates(year: Int, month: Int, property: Property): Set<LocalDate> {
        val lastDateBefore = YearMonth.of(year, month).atDay(1).minusDays(1)
        val firstDateAfter = YearMonth.of(year, month).atEndOfMonth().plusDays(1)
        val bookings = bookingRepository.findByDepartureDateAfterAndPropertyAndConfirmedTrue(lastDateBefore, property)
        return bookings.fold(emptySet()) {
            bookedDates, booking -> bookedDates.plus(booking.bookedDates.filter { it.isBefore(firstDateAfter) })
        }
    }

    fun requestBooking(bookingData: BookingData): Booking {
        val booking = bookingRepository.save(bookingData.toBooking())
        GlobalScope.launch { emailService.sendRequestConfirmationMail(booking) }
        GlobalScope.launch { emailService.sendRequestNotificationMail(booking) }
        return booking
    }

    fun saveBooking(bookingData: BookingData) {
        bookingRepository.save(bookingData.toBooking().apply { this.confirmed = true })
    }

    fun delete(id: Long): Boolean {
        return try {
            bookingRepository.deleteById(id)
            true
        } catch (e: EmptyResultDataAccessException) {
            false
        }
    }

    fun confirm(id: Long): Boolean {
        val booking = bookingRepository.findByIdOrNull(id) ?: return false
        booking.confirmed = true
        bookingRepository.save(booking)
        GlobalScope.launch { emailService.sendConfirmationMail(booking) }
        return true
    }
}
