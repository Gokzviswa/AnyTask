package com.anytask.models.entity.user

enum class Status {
    INACTIVE,
    ACTIVE,
    BANNED
}

enum class Role {
    ADMIN,
    CUSTOMER,
    EMPLOYEE
}

enum class Gender {
    MALE,
    FEMALE,
    TRANSGENDER,
    OTHERS
}