package net.sakrak.demoshoppingcart.services

import net.sakrak.demoshoppingcart.commands.ProductCommand
import net.sakrak.demoshoppingcart.converters.ProductConverter.commandToProduct
import net.sakrak.demoshoppingcart.converters.ProductConverter.productToDto
import net.sakrak.demoshoppingcart.dto.ProductDto
import net.sakrak.demoshoppingcart.repositories.ProductRepository
import org.springframework.stereotype.Service

@Service
class ProductServiceImpl(private val productRepository: ProductRepository) : ProductService {
    override fun save(productCommand: ProductCommand): ProductDto {
        val savedProduct = productRepository.save(commandToProduct(productCommand))
        return productToDto(savedProduct)
    }

    override fun findAll(): List<ProductDto> {
        return productRepository.findAll().map { productToDto(it) }
    }
}