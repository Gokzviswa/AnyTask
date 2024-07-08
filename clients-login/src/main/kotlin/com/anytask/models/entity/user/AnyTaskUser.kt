package com.anytask.models.entity.user

import java.time.Instant
import java.util.*
import javax.persistence.*

@Entity
@Table(name="any_task_users")
data class AnyTaskUser(
        @Id
        @Column(name = "id")
        var id: UUID,
        @Column(name = "user_no")
        var userNo:String? = null,
        @Column(name = "email", unique = true)
        val email: String,
        @Column(name = "first_name")
        val firstName: String,
        @Column(name = "last_name")
        val lastName: String,
        @Column(name = "middle_name")
        val middleName: String?,
        @Column(name = "mobile_number")
        val mobileNumber: String,
        @Column(name = "gender")
        val gender: Gender,
        @Column(name = "age")
        val age: String?,
        @Column(name = "birth_date")
        val birth_date: Instant,
        @Column(name = "status_local")
        val status: Status = Status.ACTIVE,
        @Column(name = "role")
        val role: Role,
        @Column(name = "created_on")
        var createdOn: Instant
) {
        constructor() : this(
                id = UUID.randomUUID(),
                userNo = null,
                email = "",
                firstName = "",
                lastName = "",
                middleName = "",
                mobileNumber = "",
                gender = Gender.OTHERS,
                age = "",
                birth_date = Instant.now(),
                role = Role.CUSTOMER,
                createdOn = Instant.now()
        )
}