package de.sweller.fewospring.booking

import de.sweller.fewospring.EmailService
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.stereotype.Service

@Service
class BookingService(
        val bookingRepository: BookingRepository,
        val emailService: EmailService,
) {

    fun getBookings(): MutableIterable<Booking> = bookingRepository.findAll()

    fun addBooking(bookingData: BookingData) {
        bookingRepository.save(bookingData.toBooking())
        emailService.sendConfirmationMail(bookingData.email)
    }

    fun delete(id: Long): Boolean {
        return try {
            bookingRepository.deleteById(id)
            true
        } catch (e: EmptyResultDataAccessException) {
            false
        }
    }
}
