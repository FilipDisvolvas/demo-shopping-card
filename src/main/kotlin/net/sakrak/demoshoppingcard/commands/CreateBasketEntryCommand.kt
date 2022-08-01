package net.sakrak.demoshoppingcard.commands

import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class CreateBasketEntryCommand(
    @field:NotNull
    @field:Min(1)
    var productId: Long? = null,

    @field:NotNull
    @field:Min(1)
    var quantity: Int? = null
) {
    constructor() : this(null, null) {

    }
}
