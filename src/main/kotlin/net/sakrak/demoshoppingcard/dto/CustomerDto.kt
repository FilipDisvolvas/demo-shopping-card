package net.sakrak.demoshoppingcard.dto

data class CustomerDto(
    val id: Long? = null,
    var firstName: String,
    var middleName: String? = null,
    var lastName: String,
    var email: String,
    var addressFirstLine: String,
    var addressSecondLine: String,
    var addressThirdLine: String?
)