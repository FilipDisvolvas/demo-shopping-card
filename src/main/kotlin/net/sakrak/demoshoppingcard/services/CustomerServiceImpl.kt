package net.sakrak.demoshoppingcard.services

import net.sakrak.demoshoppingcard.commands.CreateCustomerCommand
import net.sakrak.demoshoppingcard.commands.LoginCommand
import net.sakrak.demoshoppingcard.commands.UpdateCustomerCommand
import net.sakrak.demoshoppingcard.commands.UpdatePasswordCommand
import net.sakrak.demoshoppingcard.converters.CustomerConverter.commandToCustomerWithoutPassword
import net.sakrak.demoshoppingcard.converters.CustomerConverter.customerToDto
import net.sakrak.demoshoppingcard.domain.Customer
import net.sakrak.demoshoppingcard.dto.CustomerDto
import net.sakrak.demoshoppingcard.exceptions.CustomerNotFoundException
import net.sakrak.demoshoppingcard.exceptions.EmailAddressExistsException
import net.sakrak.demoshoppingcard.exceptions.WrongPasswordException
import net.sakrak.demoshoppingcard.repositories.CustomerRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class CustomerServiceImpl(
    private val customerRepository: CustomerRepository,
    private val passwordEncoder: PasswordEncoder) : CustomerService {

    override fun build(createCustomerCommand: CreateCustomerCommand): Customer {
        val customer = commandToCustomerWithoutPassword(createCustomerCommand)

        customer.hashedPassword = passwordEncoder.encode(createCustomerCommand.password)

        return customer
    }

    override fun create(createCustomerCommand: CreateCustomerCommand): CustomerDto {
        if (customerRepository.existsByEmail(createCustomerCommand.email!!)) {
            throw EmailAddressExistsException()
        }

        return customerToDto(customerRepository.save(build(createCustomerCommand)))
    }

    override fun update(customerId: Long, updateCustomerCommand: UpdateCustomerCommand): CustomerDto {
        val customer = customerRepository.findByIdOrNull(customerId) ?: throw CustomerNotFoundException()

        customer.firstName = updateCustomerCommand.firstName
        customer.middleName = updateCustomerCommand.middleName
        customer.lastName = updateCustomerCommand.lastName
        customer.email = updateCustomerCommand.email
        customer.addressFirstLine = updateCustomerCommand.addressFirstLine
        customer.addressSecondLine = updateCustomerCommand.addressSecondLine
        customer.addressThirdLine = updateCustomerCommand.addressThirdLine
        return customerToDto(customerRepository.save(customer))
    }

    override fun updatePassword(customerId: Long, updatePasswordCommand: UpdatePasswordCommand) {
        val foundCustomer = customerRepository.findById(customerId)

        if (foundCustomer.isEmpty) {
            throw CustomerNotFoundException()
        }

        val customer = foundCustomer.get()

        if (!isCorrectPassword(updatePasswordCommand.currentPassword, customer.hashedPassword!!)) {
            throw WrongPasswordException()
        }

        customer.hashedPassword = passwordEncoder.encode(updatePasswordCommand.newPassword)

        customerRepository.save(customer)
    }

    override fun findByLogin(loginCommand: LoginCommand): CustomerDto? {
        val foundCustomer = customerRepository.findByEmail(loginCommand.email!!) ?: return null

        return if (isCorrectPassword(loginCommand.password!!, foundCustomer.hashedPassword!!)) {
            customerToDto(foundCustomer)
        } else {
            null
        }
    }

    override fun findById(id: Long): Customer? {
        return customerRepository.findByIdOrNull(id)
    }

    private fun isCorrectPassword(password: String, hashedPassword: String) =
        passwordEncoder.matches(password, hashedPassword)

}