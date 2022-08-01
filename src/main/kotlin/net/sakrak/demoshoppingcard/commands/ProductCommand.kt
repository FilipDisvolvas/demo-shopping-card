package net.sakrak.demoshoppingcard.commands

import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

data class ProductCommand(
    val id: Long? = null,
    val name: String,
    val description: String
)