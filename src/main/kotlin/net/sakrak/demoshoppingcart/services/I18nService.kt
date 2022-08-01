package net.sakrak.demoshoppingcart.services

import org.springframework.validation.BindingResult
import javax.servlet.http.HttpServletRequest

interface I18nService {
    fun getMessages(request: HttpServletRequest, bindingResult: BindingResult): List<String>
    fun getMessage(request: HttpServletRequest, i18nCode: String, vararg placeholderValues: String): String?
}