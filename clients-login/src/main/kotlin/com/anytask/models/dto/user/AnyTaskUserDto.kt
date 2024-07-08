package com.anytask.models.dto.user

import com.anytask.models.entity.user.Gender
import com.anytask.models.entity.user.Role
import com.anytask.models.entity.user.Status
import com.fasterxml.jackson.annotation.JsonInclude
import java.time.Instant
import java.util.*

@JsonInclude(JsonInclude.Include.NON_NULL)
data class AnyTaskUserDto(
    var userNo:String? = null,
    val email: String,
    val firstName: String,
    val lastName: String,
    val middleName: String?,
    val mobileNumber: String,
    val gender: Gender,
    val age: String?,
    val birth_date: Instant,
    val role: Role,
    val password: String
) {
    val fullName: String
        get() = firstName.plus(" ").plus(middleName).plus(" ").plus(lastName)

    fun validateSignupAndCreateId() {
        require(email != null) { "User name is empty" }
        require(password != null) { " User password is empty" }
    }

}
