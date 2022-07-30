package net.sakrak.demoshoppingcard.commands

import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank

data class CreateBasketEntryCommand(
    @field:NotBlank
    var productId: Long? = null,

    @field:NotBlank
    @field:Min(1)
    var quantity: Int? = null
)
