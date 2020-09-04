package de.sweller.fewospring.email

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service
import org.thymeleaf.context.Context
import org.thymeleaf.spring5.SpringTemplateEngine

@Service
class EmailService(
        val mailSender: JavaMailSender,
        val messageSource: MessageSource,
        val emailTemplateEngine: SpringTemplateEngine,
) {

    @Value("\${spring.mail.username}")
    lateinit var fromAddress: String

    fun sendConfirmationMail(toAddress: String) {
        val locale = LocaleContextHolder.getLocale()
        sendTemplatedMessage(
                to = toAddress,
                subject = messageSource.getMessage("confirmationMail.subject", null, locale),
                template = "mail/booking-confirmation",
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
