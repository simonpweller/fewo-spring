package de.sweller.fewospring.email

import de.sweller.fewospring.booking.Booking
import de.sweller.fewospring.booking.Property
import de.sweller.fewospring.booking.calculatePrice
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.MessageSource
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service
import org.thymeleaf.context.Context
import org.thymeleaf.spring5.SpringTemplateEngine
import java.time.format.DateTimeFormatter
import java.util.*

@Service
class EmailService(
    val mailSender: JavaMailSender,
    val messageSource: MessageSource,
    val emailTemplateEngine: SpringTemplateEngine,
) {

    @Value("\${spring.mail.username}")
    lateinit var adminMail: String

    fun sendRequestConfirmationMail(booking: Booking) {
        val dateTimeFormatter =
            DateTimeFormatter.ofPattern(if (booking.locale == Locale.ENGLISH) "dd/MM/yyyy" else "dd.MM.yyyy")
        sendTemplatedMessage(
            to = booking.email,
            locale = booking.locale,
            subject = messageSource.getMessage("bookingRequest", null, booking.locale),
            template = "mail/booking-request-confirmation",
            templateModel = mapOf(
                "isBungalow" to (booking.property == Property.BUNGALOW),
                "arrivalDate" to booking.arrivalDate.format(dateTimeFormatter),
                "departureDate" to booking.departureDate.format(dateTimeFormatter),
            )
        )
    }

    fun sendRequestNotificationMail(booking: Booking) {
        val calculatedPrice = calculatePrice(booking)
        sendTemplatedMessage(
            to = adminMail,
            locale = Locale.GERMAN,
            subject = messageSource.getMessage("notification.bookingRequest", null, Locale.GERMAN),
            template = "mail/booking-notification",
            templateModel = mapOf(
                "isBungalow" to (booking.property == Property.BUNGALOW),
                "hasFullAddress" to (booking.streetLine.isNotBlank() && (booking.city.isNotBlank() || booking.zipCode.isNotBlank())),
                "booking" to booking,
                "totalPrice" to calculatedPrice.totalPrice,
                "pricePerNight" to calculatedPrice.pricePerNight,
            ),
        )
    }

    fun sendConfirmationMail(booking: Booking) {
        val calculatedPrice = calculatePrice(booking)
        val dateTimeFormatter =
            DateTimeFormatter.ofPattern(if (booking.locale == Locale.ENGLISH) "dd/MM/yyyy" else "dd.MM.yyyy")
        sendTemplatedMessage(
            to = booking.email,
            locale = booking.locale,
            subject = messageSource.getMessage("bookingConfirmation.subject", null, booking.locale),
            template = "mail/booking-confirmation",
            templateModel = mapOf(
                "isBungalow" to (booking.property == Property.BUNGALOW),
                "arrivalDate" to booking.arrivalDate.format(dateTimeFormatter),
                "departureDate" to booking.departureDate.format(dateTimeFormatter),
                "totalPrice" to calculatedPrice.totalPrice,
                "pricePerNight" to calculatedPrice.pricePerNight,
                )
        )
    }

    private fun sendTemplatedMessage(
        to: String,
        subject: String,
        template: String,
        templateModel: Map<String, Any>? = emptyMap(),
        locale: Locale
    ) {
        val context = Context(locale, templateModel)
        val htmlBody = emailTemplateEngine.process(template, context)
        sendHtmlMessage(to, subject, htmlBody)
    }

    private fun sendHtmlMessage(to: String, subject: String, htmlBody: String) {
        val mimeMessage = mailSender.createMimeMessage()
        MimeMessageHelper(mimeMessage, true, "UTF-8").apply {
            setFrom(adminMail)
            setTo(to)
            setSubject(subject)
            setText(htmlBody, true)
        }
        mailSender.send(mimeMessage)
    }

}
