package net.sakrak.demoshoppingcard.controllers

import net.sakrak.demoshoppingcard.commands.CreateCustomerCommand
import net.sakrak.demoshoppingcard.commands.LoginCommand
import net.sakrak.demoshoppingcard.commands.UpdateCustomerCommand
import net.sakrak.demoshoppingcard.converters.CustomerConverter.customerToUpdateCommand
import net.sakrak.demoshoppingcard.services.CustomerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.MessageSource
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.validation.FieldError
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.LocaleResolver
import org.springframework.web.servlet.ModelAndView
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import javax.servlet.http.HttpServletRequest
import javax.validation.Valid


@Controller
@RequestMapping("customers")
class CustomersController(private val customerService: CustomerService) : AbstractController(), WebMvcConfigurer {
    @GetMapping("/registration")
    fun registrationForm(model: Model) : String {
        model.addAttribute("customerCommand", CreateCustomerCommand())

        return "customers/registration/index"
    }

    @PostMapping("/registration")
    fun create(@ModelAttribute("customerCommand") @Valid customerCommand: CreateCustomerCommand, bindingResult: BindingResult, model: Model, attributes: RedirectAttributes, request: HttpServletRequest) : ModelAndView {

        if (bindingResult.hasErrors()) {
            return ModelAndView("customers/registration/index", mapOf("customerCommand" to customerCommand))
        }

        customerService.create(customerCommand)

        return ModelAndView("customers/registration/index", mapOf("customerCommand" to customerCommand))

        //return redirectWithSuccessMsg("/", "Die Registrierung ist abgeschlossen.", attributes)
    }

    @GetMapping("/edit")
    fun edit(attributes: RedirectAttributes, request: HttpServletRequest) : ModelAndView {
        if (customerId(request) == null) {
            return redirectWithLoginErrorMsg(request, attributes)
        }

        val model = ModelAndView("/customers/edit")
        model.modelMap["updateCommand"] = customerToUpdateCommand(customer(request)!!)

        return model
    }

    @PutMapping("/edit")
    fun update(@ModelAttribute customerCommand: UpdateCustomerCommand, attributes: RedirectAttributes, request: HttpServletRequest) : ModelAndView {
        if (customerId(request) == null) {
            return redirectWithLoginErrorMsg(request, attributes)
        }

        customerService.update(customerId(request)!!, customerCommand)

        return redirectWithSuccessMsg(request, "Deine Daten wurden erfolgreich aktualisiert", attributes)
    }

    @PostMapping("/login")
    fun login(@Valid @ModelAttribute loginCommand: LoginCommand, bindingResult: BindingResult, attributes: RedirectAttributes, request: HttpServletRequest) : ModelAndView {
        if (bindingResult.hasErrors()) {
            val errorMsg = bindingResultTranslator.getMessages(bindingResult, request).joinToString("; ")

            return redirectWithErrorMsg("/", errorMsg, attributes)
            }

        val foundCustomer = customerService.findByLogin(loginCommand)

        return if (foundCustomer == null) {
            redirectWithErrorMsg("/", "E-Mail-Adresse oder Passwort falsch", attributes)
        } else {
            request.session.setAttribute("customerId", foundCustomer.id)
            redirectWithSuccessMsg("/", "Erfolgreich eingeloggt!", attributes)
        }
    }

    @GetMapping("/logout")
    fun logout(attributes: RedirectAttributes, request: HttpServletRequest) : ModelAndView {
        for (attributeName in request.session.attributeNames) {
            request.session.removeAttribute(attributeName)
        }

        return redirectWithSuccessMsg("/", "Erfolgreich ausgeloggt!", attributes)
    }
}