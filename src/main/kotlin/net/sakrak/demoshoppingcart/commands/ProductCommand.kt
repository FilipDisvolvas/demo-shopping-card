package net.sakrak.demoshoppingcart.commands

data class ProductCommand(
    val id: Long? = null,
    val name: String,
    val description: String
)