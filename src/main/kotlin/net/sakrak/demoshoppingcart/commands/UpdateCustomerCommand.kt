package net.sakrak.demoshoppingcart.commands

import javax.validation.constraints.NotBlank

data class UpdateCustomerCommand(
    @field:NotBlank
    var firstName : String,

    var middleName : String? = null,

    @field:NotBlank
    var lastName : String,

    @field:NotBlank
    var email : String,

    @field:NotBlank
    var addressFirstLine : String,

    @field:NotBlank
    var addressSecondLine : String,

    var addressThirdLine : String? = null
)