package net.sakrak.demoshoppingcart.commands

import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

data class UpdateBasketEntryCommand(
    @field:NotNull
    @field:Min(1)
    val productId: Long,

    @field:NotNull
    @field:Min(1)
    val quantity: Int
    )
