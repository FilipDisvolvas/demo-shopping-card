package net.sakrak.demoshoppingcard.services

import net.sakrak.demoshoppingcard.commands.CreateCustomerCommand
import net.sakrak.demoshoppingcard.commands.LoginCommand
import net.sakrak.demoshoppingcard.commands.UpdateCustomerCommand
import net.sakrak.demoshoppingcard.commands.UpdatePasswordCommand
import net.sakrak.demoshoppingcard.domain.Customer
import net.sakrak.demoshoppingcard.dto.CustomerDto

interface CustomerService {
    fun build(createCustomerCommand: CreateCustomerCommand): Customer
    fun create(createCustomerCommand: CreateCustomerCommand): CustomerDto
    fun update(customerId: Long, updateCustomerCommand: UpdateCustomerCommand) : CustomerDto
    fun updatePassword(customerId: Long, updatePasswordCommand: UpdatePasswordCommand)
    fun findByLogin(loginCommand: LoginCommand): CustomerDto?
    fun findById(id: Long): Customer?
}
