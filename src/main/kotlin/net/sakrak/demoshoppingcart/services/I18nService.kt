package net.sakrak.demoshoppingcart.services

import org.springframework.validation.BindingResult
import javax.servlet.http.HttpServletRequest

interface I18nService {
    fun getMessages(bindingResult: BindingResult, request: HttpServletRequest): List<String>
    fun getMessage(i18nCode: String, request: HttpServletRequest): String?
}