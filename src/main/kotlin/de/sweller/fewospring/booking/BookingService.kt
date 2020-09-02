package de.sweller.fewospring.booking

import org.springframework.beans.factory.annotation.Value
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service

@Service
class BookingService(
    val bookingRepository: BookingRepository,
    val mailSender: JavaMailSender,
) {

    @Value("\${MAIL_USER}")
    lateinit var fromAddress: String

    fun getBookings(): MutableIterable<Booking> = bookingRepository.findAll()

    fun addBooking(bookingData: BookingData) {
        bookingRepository.save(bookingData.toBooking())
        sendConfirmationMail(bookingData.email)
    }

    private fun sendConfirmationMail(toAddress: String) {
        val message = SimpleMailMessage()
        message.setFrom(fromAddress)
        message.setTo(toAddress)
        message.setSubject("Confirmation mail")
        message.setText("Your booking is hereby confirmed!")
        mailSender.send(message)
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
