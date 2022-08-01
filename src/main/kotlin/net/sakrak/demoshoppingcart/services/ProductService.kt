package net.sakrak.demoshoppingcart.services

import net.sakrak.demoshoppingcart.commands.ProductCommand
import net.sakrak.demoshoppingcart.dto.ProductDto

interface ProductService {
    fun save(productCommand: ProductCommand): ProductDto
    fun findAll(): List<ProductDto>
}