package com.anytask.exceptions

class UserAlreadyExistsException(override val message: String? = "User already exists with this email") : Exception(message)