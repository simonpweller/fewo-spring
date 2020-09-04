package de.sweller.fewospring.admin

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Configuration
class EncoderConfig {

    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder(10)
}
