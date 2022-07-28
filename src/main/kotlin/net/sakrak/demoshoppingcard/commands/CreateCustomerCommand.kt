package net.sakrak.demoshoppingcard.commands

data class CreateCustomerCommand(
    var firstName: String? = null,

    var middleName: String? = null,

    var lastName: String? = null,

    var email: String? = null,

    var addressFirstLine: String? = null,

    var addressSecondLine: String? = null,

    var addressThirdLine: String? = null,

    var password: String? = null,

    var passwordRepitition: String? = null
)
