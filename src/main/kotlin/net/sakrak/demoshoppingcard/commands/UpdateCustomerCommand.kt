package net.sakrak.demoshoppingcard.commands

data class UpdateCustomerCommand(
    var firstName : String,

    var middleName : String? = null,

    var lastName : String,

    var email : String,

    var addressFirstLine : String,

    var addressSecondLine : String,

    var addressThirdLine : String? = null
)