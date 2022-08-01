package net.sakrak.demoshoppingcart.services

import org.springframework.validation.BindingResult
import javax.servlet.http.HttpServletRequest

interface BindingResultTranslator {
    fun getMessages(bindingResult: BindingResult, request: HttpServletRequest): List<String>
}