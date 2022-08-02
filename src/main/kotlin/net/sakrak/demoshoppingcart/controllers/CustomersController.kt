package net.sakrak.demoshoppingcart.controllers

import javax.validation.Valid
import net.sakrak.demoshoppingcart.commands.CreateCustomerCommand
import net.sakrak.demoshoppingcart.commands.LoginCommand
import net.sakrak.demoshoppingcart.commands.UpdateCustomerCommand
import net.sakrak.demoshoppingcart.converters.CustomerConverter.customerToUpdateCommand
import net.sakrak.demoshoppingcart.services.CustomerService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.ModelAndView
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import javax.servlet.http.HttpServletRequest


@Controller
@RequestMapping("customers")
class CustomersController(private val customerService: CustomerService) : AbstractController(), WebMvcConfigurer {
    @GetMapping("/registration")
    fun registrationForm(model: Model) : String {
        model.addAttribute("customerCommand", CreateCustomerCommand())

        return "customers/new"
    }

    @PostMapping("/registration")
    fun create(@ModelAttribute("customerCommand") @Valid customerCommand: CreateCustomerCommand, bindingResult: BindingResult, model: Model, attributes: RedirectAttributes, request: HttpServletRequest) : ModelAndView {
        if (bindingResult.hasErrors()) {
            return addFormErrorFlashMessage(ModelAndView("customers/new", mapOf("customerCommand" to customerCommand)), request)
        }

        customerService.create(customerCommand)

        return redirectWithSuccessMsg(request, "/", attributes, "customersController.successCreate")
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
    fun update(@ModelAttribute @Valid customerCommand: UpdateCustomerCommand, bindingResult: BindingResult, attributes: RedirectAttributes, request: HttpServletRequest) : ModelAndView {
        if (customerId(request) == null) {
            return redirectWithLoginErrorMsg(request, attributes)
        }

        if (bindingResult.hasErrors()) {
            return addFormErrorFlashMessage(ModelAndView("customers/edit", mapOf("updateCommand" to customerCommand)), request)
        }

        customerService.update(customerId(request)!!, customerCommand)

        return redirectWithSuccessMsg(request, attributes, "customersController.successUpdate")
    }

    @PostMapping("/login")
    fun login(@Valid @ModelAttribute loginCommand: LoginCommand, bindingResult: BindingResult, attributes: RedirectAttributes, request: HttpServletRequest) : ModelAndView {
        if (bindingResult.hasErrors()) {
            return redirectWithErrorMsg(request, attributes, bindingResult)
        }

        val foundCustomer = customerService.findByLogin(loginCommand)

        return if (foundCustomer == null) {
            redirectWithErrorMsg(request, "/", attributes, "customerController.invalidLogin")
        } else {
            request.session.setAttribute("customerId", foundCustomer.id)
            redirectWithSuccessMsg(request, "/", attributes, "customerController.successLogin")
        }
    }

    @GetMapping("/logout")
    fun logout(attributes: RedirectAttributes, request: HttpServletRequest) : ModelAndView {
        for (attributeName in request.session.attributeNames) {
            request.session.removeAttribute(attributeName)
        }

        return redirectWithSuccessMsg(request, "/", attributes,"customersController.successLogout")
    }
}