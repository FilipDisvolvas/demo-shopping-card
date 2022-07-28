package net.sakrak.demoshoppingcard.repositories

import net.sakrak.demoshoppingcard.domain.Customer
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface CustomerRepository : CrudRepository<Customer, Long> {
    fun existsByEmail(email: String) : Boolean
    fun findByEmail(email: String) : Customer?
}