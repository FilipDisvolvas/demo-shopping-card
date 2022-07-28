package net.sakrak.demoshoppingcard.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder


@Configuration
class PasswordEncoderConfig {
    @Bean
    @Profile("test")
    fun fastPasswordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder(4)
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder(10)
    }
}