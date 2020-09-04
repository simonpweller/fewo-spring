package de.sweller.fewospring.email

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.thymeleaf.TemplateEngine
import org.thymeleaf.spring5.SpringTemplateEngine
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver
import org.thymeleaf.templatemode.TemplateMode

@Configuration
class EmailConfig {

    @Bean
    fun emailTemplateResolver(): SpringResourceTemplateResolver = SpringResourceTemplateResolver().apply {
        resolvablePatterns = setOf("/mail/**")
        suffix = ".html"
        templateMode = TemplateMode.HTML
        characterEncoding = "UTF-8"
    }

    @Qualifier
    @Bean
    fun emailTemplateEngine(): TemplateEngine = SpringTemplateEngine().apply {
        addTemplateResolver(emailTemplateResolver())
    }
}
