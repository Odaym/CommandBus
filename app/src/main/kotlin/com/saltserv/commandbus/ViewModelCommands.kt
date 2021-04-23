package com.saltserv.commandbus

interface ViewModelCommand

object CloseScreen : ViewModelCommand

object CloseKeyboard : ViewModelCommand

object OpenMainScreen : ViewModelCommand

object OpenGatewayScreen : ViewModelCommand

object OpenCreateBookScreen : ViewModelCommand

object OpenBarcodeScannerActivity : ViewModelCommand

object ShowForgotPasswordDialog : ViewModelCommand

data class ToastCommand(val message: String) : ViewModelCommand


