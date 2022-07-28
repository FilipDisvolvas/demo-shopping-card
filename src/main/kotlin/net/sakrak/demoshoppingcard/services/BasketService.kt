package net.sakrak.demoshoppingcard.services

import net.sakrak.demoshoppingcard.commands.CreateBasketEntryCommand
import net.sakrak.demoshoppingcard.commands.UpdateBasketEntryCommand
import net.sakrak.demoshoppingcard.domain.BasketEntry
import net.sakrak.demoshoppingcard.domain.Customer
import net.sakrak.demoshoppingcard.domain.Product
import net.sakrak.demoshoppingcard.dto.ProductDto
import org.springframework.transaction.annotation.Transactional

interface BasketService {
    fun addToBasket(customerId: Long, createBasketEntryCommand: CreateBasketEntryCommand): ProductDto
    fun updateBasketEntry(customerId: Long, updateBasketEntryCommand: UpdateBasketEntryCommand): ProductDto
    fun deleteBasketEntry(customerId: Long, productId: Long): ProductDto?
    fun getBasketEntries(customerId: Long): List<BasketEntry>
}
