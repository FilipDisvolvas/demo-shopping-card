package net.sakrak.demoshoppingcart.commands

import javax.validation.constraints.NotBlank

data class LoginCommand(@field:NotBlank var email: String? = null, @field:NotBlank var password: String? = null)
