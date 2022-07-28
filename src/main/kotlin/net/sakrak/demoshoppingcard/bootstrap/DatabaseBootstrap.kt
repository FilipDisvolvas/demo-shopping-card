package net.sakrak.demoshoppingcard.bootstrap

import net.sakrak.demoshoppingcard.commands.CreateCustomerCommand
import net.sakrak.demoshoppingcard.commands.UpdateCustomerCommand
import net.sakrak.demoshoppingcard.commands.ProductCommand
import net.sakrak.demoshoppingcard.services.BasketService
import net.sakrak.demoshoppingcard.services.CustomerService
import net.sakrak.demoshoppingcard.services.ProductService
import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationListener
import org.springframework.context.annotation.Profile
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.stereotype.Component

/**
 * Füllt im "dev" profile die H2-Datenbank mit Inhalt.
 */
@Component
@Profile("dev")
class DatabaseBootstrap(
    private val productService: ProductService,
    private val customerService: CustomerService
) : ApplicationListener<ContextRefreshedEvent> {
    var logger: org.slf4j.Logger? = LoggerFactory.getLogger(DatabaseBootstrap::class.java)

    override fun onApplicationEvent(event: ContextRefreshedEvent) {
        listOf(
            ProductCommand(name = "Per Anhalter durch die Galaxis", description = "Ich hole nur noch schnell mein Handtuch..."),
            ProductCommand(name = "Doctor Who: Time Lord Fairy Tales Slipcase", description = "Es war eigentlich nur eine Telefonzelle. Doch als er rein ging, geschah unglaubliches..."),
            ProductCommand(name = "High-Performance Java Persistence", description = "Höher, weiter, schneller..."),
            ProductCommand(name = "Entwurfsmuster von Kopf bis Fuß", description = "Musterhaft bis in die Spitzen")
        ).forEach {
            productService.save(it)
        }

        val customerCommand = CreateCustomerCommand(
            firstName = "Maximilian", lastName = "Mustermann", email = "test@byom.de",
            addressFirstLine = "Hauptstraße 42", addressSecondLine = "42042 Entenhausen",
            password = "insecurePassword", passwordRepitition = "insecurePassword")

        customerService.create(customerCommand)

        logger!!.info("\u001B[32mExistierender Demo-User: E-Mail = ${customerCommand.email}; Passwort = ${customerCommand.password}\u001B[0m")
    }
}