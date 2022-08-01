package net.sakrak.demoshoppingcart.services

import net.sakrak.demoshoppingcart.commands.*
import net.sakrak.demoshoppingcart.domain.BasketEntry
import net.sakrak.demoshoppingcart.dto.CustomerDto
import net.sakrak.demoshoppingcart.dto.ProductDto
import net.sakrak.demoshoppingcart.exceptions.ProductNotFoundException
import net.sakrak.demoshoppingcart.repositories.BasketRepository
import net.sakrak.demoshoppingcart.repositories.CustomerRepository
import net.sakrak.demoshoppingcart.repositories.ProductRepository
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service
import org.springframework.test.context.ActiveProfiles

@DataJpaTest(
    includeFilters = [ComponentScan.Filter(Service::class),
        ComponentScan.Filter(Repository::class), ComponentScan.Filter(Configuration::class),
        ComponentScan.Filter(Component::class)]
)
@ActiveProfiles(value = ["test"])
class BasketIT {
    @Autowired
    lateinit var productService: ProductService
    @Autowired
    lateinit var customerService: CustomerService
    @Autowired
    lateinit var basketService: BasketService

    // brauchen wir nur zum Aufräumen der Datenbank
    @Autowired
    lateinit var customerRepository: CustomerRepository
    @Autowired
    lateinit var basketRepository: BasketRepository
    @Autowired
    lateinit var productRepository: ProductRepository

    lateinit var savedProductDto1: ProductDto
    lateinit var savedProductDto2: ProductDto
    lateinit var savedProductDto3: ProductDto
    lateinit var savedProductDto4: ProductDto
    lateinit var savedCustomer: CustomerDto

    @BeforeEach
    fun setup() {
        productRepository.deleteAll()
        basketRepository.deleteAll()
        customerRepository.deleteAll()

        val product1 = ProductCommand(
            name = "Per Anhalter durch die Galaxis",
            description = "Ich hole nur noch schnell mein Handtuch..."
        )
        savedProductDto1 = productService.save(product1)

        val product2 = ProductCommand(
            name = "Doctor Who: Time Lord Fairy Tales Slipcase",
            description = "Es war eigentlich nur eine Telefonzelle. Doch als er rein ging, geschah unglaubliches..."
        )
        savedProductDto2 = productService.save(product2)

        val product3 =
            ProductCommand(name = "High-Performance Java Persistence", description = "Höher, weiter, schneller...")
        savedProductDto3 = productService.save(product3)

        val product4 =
            ProductCommand(name = "Entwurfsmuster von Kopf bis Fuß", description = "Musterhaft bis in die Spitzen")
        savedProductDto4 = productService.save(product4)

        val customerCommand = CreateCustomerCommand(
            firstName = "Maximilian", lastName = "Mustermann", email = "test@byom.de",
            addressFirstLine = "Hauptstraße 42", addressSecondLine = "42042 Entenhausen",
            password = "insecurePassword", passwordRepitition = "insecurePassword"
        )

        savedCustomer = customerService.create(customerCommand)
    }


    @Test
    fun testCreate() {
        // Zunächst hat der Kunde keinen Warenkorb. Damit der Warenkorb über mehrere Logins hinweg funktioniert,
        // wird dieser in der Datenbank verwaltet und nun entsprechend angelegt. Initial ist er leer.
        val basketEntriesBeforeUpdate = basketService.getBasketEntries(savedCustomer.id!!)
        assertThat(basketEntriesBeforeUpdate, empty())

        // Kunde legt eine Einheit von Produkt 1 in den Warenkorb.
        basketService.addToBasket(
            customerId = savedCustomer.id!!,
            CreateBasketEntryCommand(productId = savedProductDto1.id, 1)
        )

        // Refresh. Wir holen den Warenkorb neu aus der Datenbank.
        var basketEntriesAfterUpdate = basketService.getBasketEntries(savedCustomer.id!!)

        // Dieser sollte nun genau einen Eintrag enthalten.
        assertThat(basketEntriesAfterUpdate.size, equalTo(1))

        // Wir fügen 3 Einheiten von Produkt 2 hinzu.
        basketService.addToBasket(
            customerId = savedCustomer.id!!,
            CreateBasketEntryCommand(productId = savedProductDto2.id, 3)
        )

        // Refresh...
        basketEntriesAfterUpdate = basketService.getBasketEntries(savedCustomer.id!!)


        // Nun sollten zwei Einträge enthalten sein.
        assertThat(basketEntriesAfterUpdate.size, equalTo(2))

        // Detail-Prüfung:
        // Eine Einheit von Produkt 1 im Korb?
        var product1entry: BasketEntry =
            basketEntriesAfterUpdate.find { it.product.id == savedProductDto1.id } ?: throw ProductNotFoundException()
        assertThat(product1entry.quantity, equalTo(1))

        // Drei Einheiten von Produkt 2 im Korb?
        var product2entry: BasketEntry =
            basketEntriesAfterUpdate.find { it.product.id == savedProductDto2.id } ?: throw ProductNotFoundException()
        assertThat(product2entry.quantity, equalTo(3))

        // Wir fügen eine weitere Einheit von Produkt 1 hinzu.
        basketService.addToBasket(
            customerId = savedCustomer.id!!,
            CreateBasketEntryCommand(productId = savedProductDto1.id, 1)
        )

        // Refresh...
        basketEntriesAfterUpdate = basketService.getBasketEntries(savedCustomer.id!!)

        // Detail-Prüfung: 2 Einheiten von Produkt 1 im Korb?
        product1entry = basketEntriesAfterUpdate.find { it.product.id == savedProductDto1.id } ?: throw ProductNotFoundException()
        assertThat(product1entry.quantity, equalTo(2))

        // Aktualisierung der Einheiten von Produkt 2 auf 10
        basketService.updateBasketEntry(
            customerId = savedCustomer.id!!,
            UpdateBasketEntryCommand(productId = savedProductDto2.id, 10)
        )

        // Detail-Prüfung: 10 Einheiten von Produkt 2 im Korb?
        basketEntriesAfterUpdate = basketService.getBasketEntries(savedCustomer.id!!)
        product2entry = basketEntriesAfterUpdate.find { it.product.id == savedProductDto2.id } ?: throw ProductNotFoundException()
        assertThat(product2entry.quantity, equalTo(10))

        // Product 2 aus dem Korb löschen
        basketService.deleteBasketEntry(
            customerId = savedCustomer.id!!,
            productId = savedProductDto2.id
        )

        // Refresh...
        basketEntriesAfterUpdate = basketService.getBasketEntries(savedCustomer.id!!)

        val foundDeletedBasketEntry: BasketEntry? = basketEntriesAfterUpdate.find { it.product.id == savedProductDto2.id }
        assertThat(foundDeletedBasketEntry, `is`(nullValue()))

        // Produkt aus dem Warenkorb löschen, welches gar nicht im Warenkorb ist.
        val invalidProduct = basketService.deleteBasketEntry(
            customerId = savedCustomer.id!!,
            productId = 348342
        )
        assertThat(invalidProduct, `is`(nullValue()))
    }
}
