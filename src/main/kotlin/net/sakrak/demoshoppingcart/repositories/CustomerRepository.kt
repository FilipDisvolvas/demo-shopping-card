package net.sakrak.demoshoppingcart.repositories

import net.sakrak.demoshoppingcart.domain.Customer
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface CustomerRepository : CrudRepository<Customer, Long> {
    fun existsByEmail(email: String) : Boolean
    fun findByEmail(email: String) : Customer?
}