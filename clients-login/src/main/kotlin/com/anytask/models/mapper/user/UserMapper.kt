package com.anytask.models.mapper.user

import com.anytask.models.dto.user.AnyTaskUserDto
import com.anytask.models.entity.user.AnyTaskUser
import com.anytask.models.entity.user.Status
import org.springframework.stereotype.Component
import java.time.Instant
import java.util.*

@Component
class UserMapper {
    fun mapToAnyTask(anyTaskUserDto: AnyTaskUserDto): AnyTaskUser {
        return AnyTaskUser(
                id = UUID.randomUUID(),
                userNo = anyTaskUserDto.userNo,
                email = anyTaskUserDto.email,
                firstName = anyTaskUserDto.firstName,
                lastName = anyTaskUserDto.lastName,
                middleName = anyTaskUserDto.middleName,
                mobileNumber = anyTaskUserDto.mobileNumber,
                gender = anyTaskUserDto.gender,
                age = anyTaskUserDto.age,
                birth_date = anyTaskUserDto.birth_date,
                role = anyTaskUserDto.role,
                createdOn = Instant.now(),
                status = Status.ACTIVE

        )
    }
}