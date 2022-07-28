package net.sakrak.demoshoppingcard.commands

import javax.validation.constraints.NotBlank

data class LoginCommand(@NotBlank var email: String? = null, @NotBlank var password: String? = null)