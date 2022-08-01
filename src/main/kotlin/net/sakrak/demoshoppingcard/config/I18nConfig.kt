package net.sakrak.demoshoppingcard.config

import org.springframework.boot.autoconfigure.web.WebProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.LocaleResolver
import org.springframework.web.servlet.i18n.SessionLocaleResolver
import java.util.*


@Configuration
class I18nConfig {
    @Bean
    fun localeResolver(): LocaleResolver {
        val slr = SessionLocaleResolver()
        slr.setDefaultLocale(Locale.GERMAN)
        return slr
    }
}