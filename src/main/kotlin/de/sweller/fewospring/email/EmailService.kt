package de.sweller.fewospring.email

import de.sweller.fewospring.booking.Booking
import de.sweller.fewospring.booking.Property
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder
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
    lateinit var fromAddress: String

    fun sendRequestConfirmationMail(booking: Booking) {
        val dateTimeFormatter = DateTimeFormatter.ofPattern(if (booking.locale == Locale.ENGLISH) "dd/MM/yyyy" else "dd.MM.yyyy")
        sendTemplatedMessage(
                to = booking.email,
                subject = messageSource.getMessage("mail.bookingRequest.subject", null, booking.locale),
                template = "mail/booking-request-confirmation",
                mapOf(
                        "isBungalow" to (booking.property == Property.BUNGALOW),
                        "arrivalDate" to booking.arrivalDate.format(dateTimeFormatter),
                        "departureDate" to booking.departureDate.format(dateTimeFormatter),
                )
        )
    }

    fun sendConfirmationMail(booking: Booking) {
        val dateTimeFormatter = DateTimeFormatter.ofPattern(if (booking.locale == Locale.ENGLISH) "dd/MM/yyyy" else "dd.MM.yyyy")
        sendTemplatedMessage(
                to = booking.email,
                subject = messageSource.getMessage("mail.bookingConfirmation.subject", null, booking.locale),
                template = "mail/booking-confirmation",
                mapOf(
                        "isBungalow" to (booking.property == Property.BUNGALOW),
                        "arrivalDate" to booking.arrivalDate.format(dateTimeFormatter),
                        "departureDate" to booking.departureDate.format(dateTimeFormatter),
                )
        )
    }

    private fun sendTemplatedMessage(to: String, subject: String, template: String, templateModel: Map<String, Any>? = emptyMap()) {
        val locale = LocaleContextHolder.getLocale()
        val context = Context(locale, templateModel)
        val htmlBody = emailTemplateEngine.process(template, context)
        sendHtmlMessage(to, subject, htmlBody)
    }

    private fun sendHtmlMessage(to: String, subject: String, htmlBody: String) {
        val mimeMessage = mailSender.createMimeMessage()
        MimeMessageHelper(mimeMessage, true, "UTF-8").apply {
            setFrom(fromAddress)
            setTo(to)
            setSubject(subject)
            setText(htmlBody, true)
        }
        mailSender.send(mimeMessage)
    }

}
