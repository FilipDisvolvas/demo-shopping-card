package net.sakrak.demoshoppingcart.controllers

import net.sakrak.demoshoppingcart.commands.CreateBasketEntryCommand
import net.sakrak.demoshoppingcart.services.ProductService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping

@Controller
class ProductsController(private val productService: ProductService) {


    @GetMapping(path = ["", "/"])
    fun index(model: Model): String {
        model["products"] = productService.findAll()
        model["newBasketEntry"] = CreateBasketEntryCommand()

        return "products/index"
    }
}