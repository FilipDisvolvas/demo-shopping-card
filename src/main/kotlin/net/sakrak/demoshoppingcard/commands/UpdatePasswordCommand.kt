package net.sakrak.demoshoppingcard.commands

data class UpdatePasswordCommand(
    val currentPassword: String,
    val newPassword: String,
    val newPasswordRepitition: String
)
