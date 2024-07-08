package com.anytask.models.mapper.user

import com.anytask.models.dto.user.AnyTaskUserDto
import com.anytask.models.entity.user.Credentials
import com.anytask.models.entity.user.Status
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component
import java.util.*

@Component
class CredentialsMapper(
    private val passwordEncoder: BCryptPasswordEncoder
) {
    fun mapToCredentials(
        user: AnyTaskUserDto,
        id: UUID
    ): Credentials {
        return Credentials(
            id = id,
            userName = user.email,
            password = passwordEncoder.encode(user.password)
                ?: throw IllegalArgumentException("Password is not provided."),
            status = Status.ACTIVE
        )
    }
}