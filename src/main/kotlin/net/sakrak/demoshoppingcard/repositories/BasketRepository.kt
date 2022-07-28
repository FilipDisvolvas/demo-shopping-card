package net.sakrak.demoshoppingcard.repositories

import net.sakrak.demoshoppingcard.domain.Basket
import net.sakrak.demoshoppingcard.domain.Customer
import org.hibernate.annotations.Fetch
import org.hibernate.annotations.FetchMode
import org.springframework.data.jpa.repository.Query
import org.springframework.data.jpa.repository.QueryHints
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface BasketRepository : CrudRepository<Basket, Long> {
    //@Query("select b from Basket b join fetch b.entries e join fetch e.product where b.customer.id = :customerId")
    @Query("select b from Basket b left join fetch b.entries e where b.customer.id = :customerId")
    fun findByCustomerIdWithEntriesAndProducts(customerId: Long) : Basket?
}