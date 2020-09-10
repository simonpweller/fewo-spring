package de.sweller.fewospring.booking

import de.sweller.fewospring.email.EmailService
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class BookingService(
        val bookingRepository: BookingRepository,
        val emailService: EmailService,
) {

    fun getBookings(): MutableIterable<Booking> = bookingRepository.findAll()

    fun addBooking(bookingData: BookingData) {
        val booking = bookingRepository.save(bookingData.toBooking())
        GlobalScope.launch { emailService.sendRequestConfirmationMail(booking) }
        GlobalScope.launch { emailService.sendRequestNotificationMail(booking) }
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
