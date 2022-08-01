package net.sakrak.demoshoppingcart.services

import net.sakrak.demoshoppingcart.commands.CreateBasketEntryCommand
import net.sakrak.demoshoppingcart.commands.UpdateBasketEntryCommand
import net.sakrak.demoshoppingcart.domain.BasketEntry
import net.sakrak.demoshoppingcart.dto.ProductDto

interface BasketService {
    fun addToBasket(customerId: Long, createBasketEntryCommand: CreateBasketEntryCommand): ProductDto
    fun updateBasketEntry(customerId: Long, updateBasketEntryCommand: UpdateBasketEntryCommand): ProductDto
    fun deleteBasketEntry(customerId: Long, productId: Long): ProductDto?
    fun getBasketEntries(customerId: Long): List<BasketEntry>
}
