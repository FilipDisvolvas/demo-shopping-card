package net.sakrak.demoshoppingcard.commands

import javax.validation.constraints.NotBlank

data class CreateCustomerCommand(
    @field:NotBlank
    var firstName: String? = null,

    var middleName: String? = null,

    @field:NotBlank
    var lastName: String? = null,

    @field:NotBlank
    var email: String? = null,

    @field:NotBlank
    var addressFirstLine: String? = null,

    @field:NotBlank
    var addressSecondLine: String? = null,

    var addressThirdLine: String? = null,

    @field:NotBlank
    var password: String? = null,

    @field:NotBlank
    var passwordRepitition: String? = null
)
