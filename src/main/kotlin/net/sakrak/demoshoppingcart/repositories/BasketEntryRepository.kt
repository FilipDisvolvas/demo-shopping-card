package net.sakrak.demoshoppingcart.repositories

import net.sakrak.demoshoppingcart.domain.Basket
import net.sakrak.demoshoppingcart.domain.BasketEntry
import net.sakrak.demoshoppingcart.domain.Product
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface BasketEntryRepository : CrudRepository<BasketEntry, Long> {
    fun findByBasketAndProduct(basket: Basket, product: Product): BasketEntry?
}