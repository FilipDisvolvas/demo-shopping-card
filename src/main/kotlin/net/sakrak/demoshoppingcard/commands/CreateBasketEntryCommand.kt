package net.sakrak.demoshoppingcard.commands

data class CreateBasketEntryCommand(var productId: Long? = null, var quantity: Int? = null)
