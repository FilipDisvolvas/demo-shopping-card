package net.sakrak.demoshoppingcard.services

import net.sakrak.demoshoppingcard.commands.ProductCommand
import net.sakrak.demoshoppingcard.dto.ProductDto

interface ProductService {
    fun save(productCommand: ProductCommand): ProductDto
    fun findAll(): List<ProductDto>
}