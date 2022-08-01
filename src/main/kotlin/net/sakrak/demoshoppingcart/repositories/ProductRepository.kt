package net.sakrak.demoshoppingcart.repositories

import net.sakrak.demoshoppingcart.domain.Product
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository : CrudRepository<Product, Long> {
}