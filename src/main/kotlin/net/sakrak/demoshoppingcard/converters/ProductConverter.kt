package net.sakrak.demoshoppingcard.converters

import net.sakrak.demoshoppingcard.commands.ProductCommand
import net.sakrak.demoshoppingcard.domain.Product
import net.sakrak.demoshoppingcard.dto.ProductDto

object ProductConverter {
    fun commandToProduct(productCommand: ProductCommand) =
        Product(id = productCommand.id, name = productCommand.name, description = productCommand.description)

    fun productToDto(product: Product) = ProductDto(id = product.id!!, name = product.name, description = product.description)
}