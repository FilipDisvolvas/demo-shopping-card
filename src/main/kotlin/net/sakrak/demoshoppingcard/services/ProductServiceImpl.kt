package net.sakrak.demoshoppingcard.services

import net.sakrak.demoshoppingcard.commands.ProductCommand
import net.sakrak.demoshoppingcard.converters.ProductConverter.commandToProduct
import net.sakrak.demoshoppingcard.converters.ProductConverter.productToDto
import net.sakrak.demoshoppingcard.dto.ProductDto
import net.sakrak.demoshoppingcard.repositories.ProductRepository
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