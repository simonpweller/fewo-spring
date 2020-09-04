package de.sweller.fewospring

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service

@Service
class EmailService(
        val mailSender: JavaMailSender,
        val messageSource: MessageSource,
) {

    @Value("\${MAIL_USER}")
    lateinit var fromAddress: String

    fun sendConfirmationMail(toAddress: String) {
        val message = SimpleMailMessage()
        val locale = LocaleContextHolder.getLocale()
        message.setFrom(fromAddress)
        message.setTo(toAddress)
        message.setSubject(messageSource.getMessage("confirmationMail.subject", null, locale))
        message.setText(messageSource.getMessage("confirmationMail.text", null, locale))
        mailSender.send(message)
    }

}
