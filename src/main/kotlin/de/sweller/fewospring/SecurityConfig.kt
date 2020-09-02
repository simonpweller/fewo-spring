package de.sweller.fewospring

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.InMemoryUserDetailsManager

@Configuration
@EnableWebSecurity
class SecurityConfig(val encoder: PasswordEncoder): WebSecurityConfigurerAdapter() {

    @Value("\${USER_PW}")
    lateinit var userPassword: String

    override fun configure(http: HttpSecurity) {
        http
                .authorizeRequests()
                    .antMatchers("/admin/**").authenticated()
                    .anyRequest().permitAll()
                .and().formLogin()
    }

    @Bean
    override fun userDetailsService(): UserDetailsService {
        val user = User.builder()
                .username("user")
                .password(encoder.encode(userPassword))
                .authorities(emptyList())
                .build()

        return InMemoryUserDetailsManager(user)
    }
}
