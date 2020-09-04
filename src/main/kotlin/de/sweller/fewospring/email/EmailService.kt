package de.sweller.fewospring.email

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service

@Service
class EmailService(
        val mailSender: JavaMailSender,
        val messageSource: MessageSource,
) {

    @Value("\${MAIL_USER}")
    lateinit var fromAddress: String

    fun sendConfirmationMail(toAddress: String) {
        val locale = LocaleContextHolder.getLocale()
        sendHtmlMessage(
                to = toAddress,
                from = fromAddress,
                subject = messageSource.getMessage("confirmationMail.subject", null, locale),
                htmlBody = messageSource.getMessage("confirmationMail.text", null, locale)
        )
    }

    private fun sendHtmlMessage(to: String, from: String, subject: String, htmlBody: String) {
        val mimeMessage = mailSender.createMimeMessage()
        MimeMessageHelper(mimeMessage, true, "UTF-8").apply {
            setTo(to)
            setFrom(from)
            setSubject(subject)
            setText(htmlBody, true)
        }
        mailSender.send(mimeMessage)
    }

}
