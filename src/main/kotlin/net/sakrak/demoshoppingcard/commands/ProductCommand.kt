package net.sakrak.demoshoppingcard.commands

data class ProductCommand(
    val id: Long? = null,
    val name: String,
    val description: String
)