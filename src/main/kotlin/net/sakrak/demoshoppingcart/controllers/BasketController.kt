package net.sakrak.demoshoppingcart.controllers

import net.sakrak.demoshoppingcart.commands.CreateBasketEntryCommand
import net.sakrak.demoshoppingcart.commands.UpdateBasketEntryCommand
import net.sakrak.demoshoppingcart.dto.ProductDto
import net.sakrak.demoshoppingcart.services.BasketService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.ModelAndView
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import javax.servlet.http.HttpServletRequest
import javax.validation.Valid

@Controller
class BasketController : AbstractController() {
    @Autowired
    lateinit var basketService: BasketService

    @GetMapping("/basket")
    fun getIndex(model: Model, attributes: RedirectAttributes, request: HttpServletRequest) : ModelAndView {
        val customerId = customerId(request) ?: return redirectWithLoginErrorMsg(request, attributes)

        model["basketEntries"] = basketService.getBasketEntries(customerId)
        model["updateBasketEntryCommand"] = UpdateBasketEntryCommand(-1, -1)


        return ModelAndView("basket/index")
    }

    @PostMapping("/basket")
    fun create(@Valid @ModelAttribute basketEntryCommand: CreateBasketEntryCommand, bindingResult: BindingResult, attributes: RedirectAttributes, request: HttpServletRequest) : ModelAndView {

        val customerId = customerId(request) ?: return redirectWithLoginErrorMsg(request, attributes)

        if (bindingResult.hasErrors()) {
            val errorMessage = i18nService.getMessages(bindingResult, request).joinToString("; ")
            return redirectWithErrorMsg(request, errorMessage, attributes)
        }

        val product = basketService.addToBasket(customerId, basketEntryCommand)

        return redirectWithSuccessMsg(request, "Artikel \"${product.name}\" erfolgreich dem Warenkorb hinzugefügt!", attributes)
    }

    @PutMapping("/basket/{productId}")
    fun update(@PathVariable productId: Long, @ModelAttribute updateBasketEntryCommand: UpdateBasketEntryCommand, attributes: RedirectAttributes, request: HttpServletRequest): ModelAndView {
        val customerId = customerId(request) ?: return redirectWithLoginErrorMsg(request, attributes)

        val product = basketService.updateBasketEntry(customerId, updateBasketEntryCommand.copy(productId = productId))

        return redirectWithSuccessMsg(request, "Anzahl für Artikel \"${product.name}\" erfolgreich aktualisiert!", attributes)
    }

    @DeleteMapping("/basket/{productId}")
    fun delete(@PathVariable productId: Long, attributes: RedirectAttributes, request: HttpServletRequest): ModelAndView {
        val customerId = customerId(request) ?: return redirectWithLoginErrorMsg(request, attributes)

        val affectedProduct: ProductDto? = basketService.deleteBasketEntry(customerId, productId)

        if (affectedProduct != null) {
            attributes.addFlashAttribute("successFlash", "Artikel \"${affectedProduct.name}\" erfolgreich aus dem Warenkorb entfernt.")
        }

        return redirectReferer(request)
    }
}