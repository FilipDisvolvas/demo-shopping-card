package net.sakrak.demoshoppingcart.controllers

import net.sakrak.demoshoppingcart.domain.Customer
import net.sakrak.demoshoppingcart.services.I18nService
import net.sakrak.demoshoppingcart.services.CustomerService
import net.sakrak.demoshoppingcart.services.RedirectService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.servlet.ModelAndView
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import org.springframework.web.util.UriComponentsBuilder
import java.net.URI
import javax.servlet.http.HttpServletRequest

abstract class AbstractController {
    @Autowired
    private lateinit var redirectService: RedirectService

    @Autowired
    private lateinit var customerService: CustomerService

    @Autowired
    protected lateinit var i18nService: I18nService

    protected fun redirectReferer(request: HttpServletRequest) = redirectService.buildCleanRedirect(request.getHeader("Referer"))

    protected fun redirectWithSuccessMsg(url: String, message: String, attributes: RedirectAttributes) : ModelAndView {
        attributes.addFlashAttribute("successFlash", message)
        return redirectService.buildCleanRedirect(url)
    }

    protected fun redirectWithSuccessMsg(request: HttpServletRequest, message: String, attributes: RedirectAttributes) : ModelAndView {
        attributes.addFlashAttribute("successFlash", message)
        return redirectService.buildCleanRedirect(request.getHeader("Referer") ?: "/")
    }

    protected fun redirectWithErrorMsg(url: String, message: String, attributes: RedirectAttributes) : ModelAndView {
        attributes.addFlashAttribute("errorFlash", message)
        return redirectService.buildCleanRedirect(url)
    }

    protected fun redirectWithErrorMsg(request: HttpServletRequest, message: String, attributes: RedirectAttributes) : ModelAndView {
        attributes.addFlashAttribute("errorFlash", message)
        return redirectService.buildCleanRedirect(request.getHeader("Referer") ?: "/")
    }

    protected fun addFormErrorFlashMessage(modelAndView: ModelAndView, request: HttpServletRequest): ModelAndView {
        modelAndView.model["errorFlash"] = i18nService.getMessage("standardErrorFlash.invalidForm", request)!!
        return modelAndView
    }

    protected fun redirectWithLoginErrorMsg(request: HttpServletRequest, attributes: RedirectAttributes) : ModelAndView {
        return redirectWithErrorMsg(buildRedirectUrl(request), i18nService.getMessage("standardErrorFlash.notLoggedIn", request)!!,  attributes)
    }

    protected fun isLoggedIn(request: HttpServletRequest): Boolean {
        return request.session.getAttribute("customerId") != null
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

    protected fun buildRedirectUrl(request: HttpServletRequest): String {
        var redirectUri = request.getHeader("Referer") ?: "/"

        return if (redirectUri.contains("redirected=true")) {
            "/"
        } else {
            val uri = URI(redirectUri)
            UriComponentsBuilder.fromUri(uri).queryParam("redirected", "true").build().toUriString()
        }
    }
}
