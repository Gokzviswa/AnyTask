package com.anytask.service.user

import com.anytask.models.entity.user.AnyTaskUser
import org.springframework.web.multipart.MultipartFile

interface AnyTaskUserService {
    fun saveUser(user: AnyTaskUser): AnyTaskUser
    fun getAllUsers(): List<AnyTaskUser>
    fun getUserByEmail(email: String): AnyTaskUser
    fun checkIfMailExistsActive(email: String): Boolean
    fun upload(multipartFile: MultipartFile): Any?

}