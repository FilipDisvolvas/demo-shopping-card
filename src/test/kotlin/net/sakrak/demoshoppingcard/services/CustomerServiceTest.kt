package net.sakrak.demoshoppingcard.services

import net.sakrak.demoshoppingcard.commands.CreateCustomerCommand
import net.sakrak.demoshoppingcard.config.PasswordEncoderConfig
import net.sakrak.demoshoppingcard.repositories.CustomerRepository
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.text.MatchesPattern.matchesPattern
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.mockito.MockitoAnnotations
import org.mockito.Spy


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CustomerServiceTest {
    @Spy
    lateinit var customerRepository: CustomerRepository

    lateinit var customerService: CustomerService

    @BeforeAll
    fun setUp() {
        MockitoAnnotations.openMocks(this)

        customerService = CustomerServiceImpl(customerRepository, PasswordEncoderConfig().passwordEncoder())
    }

    @Test
    fun testCreate() {
        val customerCommand = CreateCustomerCommand(
            firstName = "Maximilian", lastName = "Mustermann", email = "test@byom.de",
            addressFirstLine = "Hauptstraße 42", addressSecondLine = "42042 Entenhausen",
            password = "insecurePassword", passwordRepitition = "insecurePassword")

        val customer = customerService.build(customerCommand)

        assertThat(customer.firstName, equalTo(customerCommand.firstName))
        assertThat(customer.middleName, equalTo(customerCommand.middleName))
        assertThat(customer.lastName, equalTo(customerCommand.lastName))
        assertThat(customer.addressFirstLine, equalTo(customerCommand.addressFirstLine))
        assertThat(customer.addressSecondLine, equalTo(customerCommand.addressSecondLine))
        assertThat(customer.addressThirdLine, equalTo(customerCommand.addressThirdLine))
        assertThat(customer.hashedPassword, notNullValue())
        assertThat(customer.hashedPassword, startsWith("$2a$"))
    }
}