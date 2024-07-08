package com.anytask.service.credentials

import com.anytask.models.dto.user.AnyTaskUserDto
import com.anytask.models.entity.user.Credentials
import java.util.*

interface CredentialsService {
    fun saveCredentials(userDto: AnyTaskUserDto, id: UUID): Credentials
    fun updateForgotPassword(userName: String, password: String): Boolean
    fun checkIfUserNameExistsActive(userName: String): Boolean
    fun checkIfPasswordExists(userName: String, password: String): Credentials
}
