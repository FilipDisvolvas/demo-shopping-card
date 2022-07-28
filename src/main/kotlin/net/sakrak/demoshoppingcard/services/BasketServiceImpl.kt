package net.sakrak.demoshoppingcard.services

import net.sakrak.demoshoppingcard.commands.CreateBasketEntryCommand
import net.sakrak.demoshoppingcard.commands.UpdateBasketEntryCommand
import net.sakrak.demoshoppingcard.converters.ProductConverter.productToDto
import net.sakrak.demoshoppingcard.domain.Basket
import net.sakrak.demoshoppingcard.domain.BasketEntry
import net.sakrak.demoshoppingcard.domain.Product
import net.sakrak.demoshoppingcard.dto.ProductDto
import net.sakrak.demoshoppingcard.exceptions.ProductNotFoundException
import net.sakrak.demoshoppingcard.repositories.BasketEntryRepository
import net.sakrak.demoshoppingcard.repositories.BasketRepository
import net.sakrak.demoshoppingcard.repositories.CustomerRepository
import net.sakrak.demoshoppingcard.repositories.ProductRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.persistence.TransactionRequiredException


typealias BasketEntryFunction = (entry: BasketEntry) -> BasketEntry

@Service
class BasketServiceImpl(
    private val basketRepository: BasketRepository,
    private val basketEntryRepository: BasketEntryRepository,
    private val customerRepository: CustomerRepository,
    private val productRepository: ProductRepository
) : BasketService {

    @PersistenceContext
    private lateinit var entityManager: EntityManager


    @Transactional
    override fun addToBasket(customerId: Long, createBasketEntryCommand: CreateBasketEntryCommand): ProductDto {
        val product = executeBasketUpdate(customerId, createBasketEntryCommand.productId!!) {
            it.quantity += createBasketEntryCommand.quantity!!
            it
        }.product

        return productToDto(product)
    }

    @Transactional
    override fun updateBasketEntry(customerId: Long, updateBasketEntryCommand: UpdateBasketEntryCommand): ProductDto {
        val product = executeBasketUpdate(customerId, updateBasketEntryCommand.productId) {
            it.quantity = updateBasketEntryCommand.quantity
            it
        }.product

        return productToDto(product)
    }

    @Transactional
    override fun deleteBasketEntry(customerId: Long, productId: Long) : ProductDto? {
        val basket = getOrCreateBasket(customerId)

        val (product: Product?, foundBasketEntry: BasketEntry?) = basket.entries
            .filter { it.product.id == productId }
            .map { Pair(it.product, it) }
            .firstOrNull() ?: Pair(null, null)

        return if (product != null) {
            basket.entries.remove(foundBasketEntry)
            basketRepository.save(basket)
            productToDto(product)
        } else {
            null
        }
    }

    override fun getBasketEntries(customerId: Long): List<BasketEntry> {
        val basket = basketRepository.findByCustomerIdWithEntriesAndProducts(customerId) ?: return listOf()


        // Beim Lesen des Warenkorbs immer den first-level cache "umgehen", da wir ansonsten nicht die neusten
        // Entries bekommen. Auch und gerade im Hinblick auf möglicher Weise verteilte Systeme...
        // TODO: Überprüfen, ob wir die Exception mit TransactionSynchronizationManager.isActualTransactionActive() umgehen können.
        try {
            entityManager.refresh(basket)
        } catch (e: TransactionRequiredException) {
            // nada
        }

        return basket.entries.toList()
    }

    private fun getOrCreateBasket(customerId: Long): Basket {
        var basket = basketRepository.findByCustomerIdWithEntriesAndProducts(customerId)

        if (basket == null) {
            val customer = customerRepository.findById(customerId).get() // TODO: Nur customerId verwenden
            basket = Basket(customer)

            basketRepository.save(basket)
        }

        return basket
    }

    private fun executeBasketUpdate(customerId: Long, productId: Long, basketEntryProcessor: BasketEntryFunction): BasketEntry {
        val basket = getOrCreateBasket(customerId)
        val product = productRepository.findByIdOrNull(productId) ?: throw ProductNotFoundException()
        val entry =
            try {
                basketEntryRepository.findByBasketAndProduct(basket, product) ?: BasketEntry(basket, product)
            } catch (e: Exception) {
                BasketEntry(basket, product)
            }
        return basketEntryRepository.save(basketEntryProcessor.invoke(entry))
    }
}
