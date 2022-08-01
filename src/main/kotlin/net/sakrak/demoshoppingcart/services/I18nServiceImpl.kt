package net.sakrak.demoshoppingcart.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.MessageSource
import org.springframework.core.env.Environment
import org.springframework.stereotype.Service
import org.springframework.validation.BindingResult
import org.springframework.validation.FieldError
import org.springframework.web.servlet.LocaleResolver
import javax.servlet.http.HttpServletRequest

@Service
class I18nServiceImpl : I18nService {
    @Autowired
    lateinit var env: Environment

    @Autowired
    lateinit var messageSource: MessageSource

    @Autowired
    lateinit var localeResolver: LocaleResolver

    override fun getMessage(request: HttpServletRequest, i18nCode: String, vararg placeholderValues: String): String? {
        val locale = localeResolver.resolveLocale(request)

        return if (env.activeProfiles.contains("test")) {
                messageSource.getMessage(i18nCode, placeholderValues, locale)
        } else {
                messageSource.getMessage(i18nCode, placeholderValues, i18nCode, locale)
        }
    }

    override fun getMessages(request: HttpServletRequest, bindingResult: BindingResult): List<String> {
        val locale = localeResolver.resolveLocale(request)

        val i18nLookup = if (env.activeProfiles.contains("test")) {
            { i18nCode: String ->
                messageSource.getMessage(i18nCode, arrayOf(), locale)
            }
        } else {
            { i18nCode: String ->
                messageSource.getMessage(i18nCode, arrayOf(), i18nCode, locale)
            }
        }

        return bindingResult.allErrors.map {
            val fieldError = it as FieldError

            val fieldMessage = i18nLookup(listOf(fieldError.objectName, fieldError.field).joinToString("."))

            val errorMessage = if (fieldError.defaultMessage.isNullOrBlank()) {
                i18nLookup(listOf("validationError", fieldError.code).joinToString("."))
            } else {
                fieldError.defaultMessage
            }

            listOf(fieldMessage, errorMessage).joinToString(" ")
        }
    }
}