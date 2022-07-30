package net.sakrak.demoshoppingcard.services

import org.springframework.validation.BindingResult
import javax.servlet.http.HttpServletRequest

interface BindingResultTranslator {
    fun getMessages(bindingResult: BindingResult, request: HttpServletRequest): List<String>
}