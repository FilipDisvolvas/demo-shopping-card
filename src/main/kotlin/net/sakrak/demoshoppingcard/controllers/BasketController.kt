package net.sakrak.demoshoppingcard.controllers

import net.sakrak.demoshoppingcard.commands.CreateBasketEntryCommand
import net.sakrak.demoshoppingcard.commands.UpdateBasketEntryCommand
import net.sakrak.demoshoppingcard.dto.ProductDto
import net.sakrak.demoshoppingcard.services.BasketService
import net.sakrak.demoshoppingcard.services.RedirectService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.ModelMap
import org.springframework.ui.set
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.ModelAndView
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import org.springframework.web.servlet.view.RedirectView
import javax.servlet.http.HttpServletRequest

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
    fun create(@ModelAttribute basketEntryCommand: CreateBasketEntryCommand, attributes: RedirectAttributes, request: HttpServletRequest) : ModelAndView {

        val customerId = customerId(request) ?: return redirectWithLoginErrorMsg(request, attributes)

        // TODO: Echte Validations verwenden!
        if (basketEntryCommand.productId == null) {
            return redirectWithErrorMsg(request, "Produkt nicht angegeben!", attributes)
        }

        if (basketEntryCommand.quantity == null || basketEntryCommand.quantity == 0) {
            return redirectWithErrorMsg(request, "Produkt nicht angegeben!", attributes)
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