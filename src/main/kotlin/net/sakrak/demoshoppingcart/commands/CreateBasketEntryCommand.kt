package net.sakrak.demoshoppingcart.commands

import javax.validation.constraints.Min
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
