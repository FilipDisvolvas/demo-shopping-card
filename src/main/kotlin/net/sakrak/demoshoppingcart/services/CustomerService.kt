package net.sakrak.demoshoppingcart.services

import net.sakrak.demoshoppingcart.commands.CreateCustomerCommand
import net.sakrak.demoshoppingcart.commands.LoginCommand
import net.sakrak.demoshoppingcart.commands.UpdateCustomerCommand
import net.sakrak.demoshoppingcart.commands.UpdatePasswordCommand
import net.sakrak.demoshoppingcart.domain.Customer
import net.sakrak.demoshoppingcart.dto.CustomerDto

interface CustomerService {
    fun build(createCustomerCommand: CreateCustomerCommand): Customer
    fun create(createCustomerCommand: CreateCustomerCommand): CustomerDto
    fun update(customerId: Long, updateCustomerCommand: UpdateCustomerCommand) : CustomerDto
    fun updatePassword(customerId: Long, updatePasswordCommand: UpdatePasswordCommand)
    fun findByLogin(loginCommand: LoginCommand): CustomerDto?
    fun findById(id: Long): Customer?
}
