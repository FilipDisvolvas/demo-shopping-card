package net.sakrak.demoshoppingcard.converters

import net.sakrak.demoshoppingcard.commands.CreateCustomerCommand
import net.sakrak.demoshoppingcard.commands.UpdateCustomerCommand
import net.sakrak.demoshoppingcard.domain.Customer
import net.sakrak.demoshoppingcard.dto.CustomerDto

object CustomerConverter {
    fun commandToCustomerWithoutPassword(commandCreate: CreateCustomerCommand): Customer {
        val cust = Customer()
        cust.firstName = commandCreate.firstName!!
        cust.middleName = commandCreate.middleName
        cust.lastName = commandCreate.lastName!!
        cust.email = commandCreate.email!!
        cust.addressFirstLine = commandCreate.addressFirstLine!!
        cust.addressSecondLine = commandCreate.addressSecondLine!!
        cust.addressThirdLine = commandCreate.addressThirdLine
        return cust
    }

    fun customerToDto(customer: Customer): CustomerDto {
        return CustomerDto(
            id = customer.id,
            firstName = customer.firstName,
            middleName = customer.middleName,
            lastName = customer.lastName,
            email = customer.email,
            addressFirstLine = customer.firstName,
            addressSecondLine = customer.addressSecondLine,
            addressThirdLine = customer.addressThirdLine
        )
    }

    fun customerToUpdateCommand(customer: Customer) : UpdateCustomerCommand {
        return UpdateCustomerCommand(
            firstName = customer.firstName,
            middleName = customer.middleName,
            lastName = customer.lastName,
            email = customer.email,
            addressFirstLine = customer.addressFirstLine,
            addressSecondLine = customer.addressSecondLine,
            addressThirdLine = customer.addressThirdLine
        )
    }
}