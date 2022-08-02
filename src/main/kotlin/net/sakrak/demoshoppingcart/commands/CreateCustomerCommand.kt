package net.sakrak.demoshoppingcart.commands

import net.sakrak.demoshoppingcart.validations.MatchField
import org.springframework.validation.annotation.Validated
import javax.validation.constraints.NotBlank

@MatchField(
    firstField = "password",
    secondField = "passwordRepitition",
    message = "Password and confirmation password must be the same!"
)
@Validated
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
