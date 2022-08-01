package net.sakrak.demoshoppingcart.controllers

import net.sakrak.demoshoppingcart.domain.Customer
import net.sakrak.demoshoppingcart.services.I18nService
import net.sakrak.demoshoppingcart.services.CustomerService
import net.sakrak.demoshoppingcart.services.RedirectService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.BindingResult
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

    protected fun redirectWithSuccessMsg(request: HttpServletRequest, url: String, attributes: RedirectAttributes, i18nCode: String, vararg placeholderValues: String) : ModelAndView {
        val message = i18nService.getMessage(request, i18nCode, *placeholderValues)
        attributes.addFlashAttribute("successFlash", message)

        return redirectService.buildCleanRedirect(url)
    }

    protected fun redirectWithSuccessMsg(request: HttpServletRequest, attributes: RedirectAttributes, i18nCode: String, vararg placeholderValues: String) : ModelAndView {
        val message = i18nService.getMessage(request, i18nCode, *placeholderValues)
        attributes.addFlashAttribute("successFlash", message)

        return redirectService.buildCleanRedirect(request.getHeader("Referer") ?: "/")
    }

    protected fun redirectWithErrorMsg(request: HttpServletRequest, url: String, attributes: RedirectAttributes, i18nCode: String, vararg placeholderValues: String) : ModelAndView {
        val message = i18nService.getMessage(request, i18nCode, *placeholderValues)

        attributes.addFlashAttribute("errorFlash", message)
        return redirectService.buildCleanRedirect(url)
    }

    protected fun redirectWithErrorMsg(request: HttpServletRequest, attributes: RedirectAttributes, i18nCode: String, vararg placeholderValues: String) : ModelAndView {
        val message = i18nService.getMessage(request, i18nCode, *placeholderValues)
        attributes.addFlashAttribute("errorFlash", message)

        return redirectService.buildCleanRedirect(buildRedirectUrl(request))
    }

    protected fun redirectWithErrorMsg(request: HttpServletRequest, attributes: RedirectAttributes, bindingResult: BindingResult) : ModelAndView {
        val message = i18nService.getMessages(request, bindingResult).joinToString("; ")
        attributes.addFlashAttribute("errorFlash", message)

        return redirectService.buildCleanRedirect(buildRedirectUrl(request))
    }


    protected fun addFormErrorFlashMessage(modelAndView: ModelAndView, request: HttpServletRequest): ModelAndView {
        modelAndView.model["errorFlash"] = i18nService.getMessage(request, "standardErrorFlash.invalidForm")!!
        return modelAndView
    }

    protected fun redirectWithLoginErrorMsg(request: HttpServletRequest, attributes: RedirectAttributes) : ModelAndView {
        return redirectWithErrorMsg(request, attributes, "standardErrorFlash.notLoggedIn")
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

    private fun buildRedirectUrl(request: HttpServletRequest): String {
        var redirectUri = request.getHeader("Referer") ?: "/"

        return if (redirectUri.contains("redirected=true")) {
            "/"
        } else {
            val uri = URI(redirectUri)
            UriComponentsBuilder.fromUri(uri).queryParam("redirected", "true").build().toUriString()
        }
    }
}
