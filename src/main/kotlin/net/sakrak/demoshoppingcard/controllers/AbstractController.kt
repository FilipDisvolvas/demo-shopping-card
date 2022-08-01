package net.sakrak.demoshoppingcard.controllers

import net.sakrak.demoshoppingcard.domain.Customer
import net.sakrak.demoshoppingcard.services.BindingResultTranslator
import net.sakrak.demoshoppingcard.services.CustomerService
import net.sakrak.demoshoppingcard.services.RedirectService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.MessageSource
import org.springframework.web.servlet.LocaleResolver
import org.springframework.web.servlet.ModelAndView
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import javax.servlet.http.HttpServletRequest

abstract class AbstractController {
    @Autowired
    private lateinit var redirectService: RedirectService

    @Autowired
    private lateinit var customerService: CustomerService

    @Autowired
    protected lateinit var bindingResultTranslator: BindingResultTranslator

    protected fun redirectReferer(request: HttpServletRequest) = redirectService.buildCleanRedirect(request.getHeader("Referer"))

    protected fun redirectWithSuccessMsg(url: String, message: String, attributes: RedirectAttributes) : ModelAndView {
        attributes.addFlashAttribute("successFlash", message)
        return redirectService.buildCleanRedirect(url)
    }

    protected fun redirectWithSuccessMsg(request: HttpServletRequest, message: String, attributes: RedirectAttributes) : ModelAndView {
        attributes.addFlashAttribute("successFlash", message)
        return redirectService.buildCleanRedirect(request.getHeader("Referer"))
    }

    protected fun redirectWithErrorMsg(url: String, message: String, attributes: RedirectAttributes) : ModelAndView {
        attributes.addFlashAttribute("errorFlash", message)
        return redirectService.buildCleanRedirect(url)
    }

    protected fun redirectWithErrorMsg(request: HttpServletRequest, message: String, attributes: RedirectAttributes) : ModelAndView {
        attributes.addFlashAttribute("errorFlash", message)
        return redirectService.buildCleanRedirect(request.getHeader("Referer"))
    }

    protected fun redirectWithLoginErrorMsg(request: HttpServletRequest, attributes: RedirectAttributes) : ModelAndView {
        return redirectWithErrorMsg(request.getHeader("Referer"), "Du bist nicht eingeloggt",  attributes)
    }

    protected fun customerId(request: HttpServletRequest): Long? {
        return request.session.getAttribute("customerId") as Long?
    }

    protected fun customer(request: HttpServletRequest): Customer? {
        val customerId = customerId(request)

        return if (customerId != null) {
            customerService.findById(customerId)
        } else {
            null
        }
    }
}
