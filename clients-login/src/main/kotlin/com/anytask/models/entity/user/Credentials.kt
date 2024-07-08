package com.anytask.models.entity.user

import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name="credentials")
data class Credentials(
    @Id
    @Column(name = "id")
    val id: UUID,
    @Column(name = "user_name", unique = true)
    val userName: String,
    @Column(name = "password")
    val password: String,
    @Column(name = "status")
    val status: Status

) {
    constructor() : this(UUID.randomUUID(), "", "", Status.ACTIVE)
}
