package net.sakrak.demoshoppingcart.commands

import javax.validation.constraints.NotBlank

data class UpdatePasswordCommand(
    @field:NotBlank
    val currentPassword: String,

    @field:NotBlank
    val newPassword: String,

    @field:NotBlank
    val newPasswordRepitition: String
)
