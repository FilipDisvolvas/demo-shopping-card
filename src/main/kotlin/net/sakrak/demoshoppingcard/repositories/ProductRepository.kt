package net.sakrak.demoshoppingcard.repositories

import net.sakrak.demoshoppingcard.domain.Product
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository : CrudRepository<Product, Long> {
}