package net.sakrak.demoshoppingcard.repositories

import net.sakrak.demoshoppingcard.domain.Basket
import net.sakrak.demoshoppingcard.domain.BasketEntry
import net.sakrak.demoshoppingcard.domain.Product
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface BasketEntryRepository : CrudRepository<BasketEntry, Long> {
    fun findByBasketAndProduct(basket: Basket, product: Product): BasketEntry?
}