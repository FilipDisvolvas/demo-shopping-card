package net.sakrak.demoshoppingcart.converters

import net.sakrak.demoshoppingcart.commands.ProductCommand
import net.sakrak.demoshoppingcart.domain.Product
import net.sakrak.demoshoppingcart.dto.ProductDto

object ProductConverter {
    fun commandToProduct(productCommand: ProductCommand) =
        Product(id = productCommand.id, name = productCommand.name, description = productCommand.description)

    fun productToDto(product: Product) = ProductDto(id = product.id!!, name = product.name, description = product.description)
}