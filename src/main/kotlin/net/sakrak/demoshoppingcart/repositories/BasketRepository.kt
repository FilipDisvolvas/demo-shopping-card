package net.sakrak.demoshoppingcart.repositories

import net.sakrak.demoshoppingcart.domain.Basket
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface BasketRepository : CrudRepository<Basket, Long> {
    @Query("select b from Basket b left join fetch b.entries e left join fetch e.product where b.customer.id = :customerId")
    fun findByCustomerIdWithEntriesAndProducts(customerId: Long) : Basket?
}