package com.anytask.repository.credentials

import com.anytask.models.entity.user.Credentials
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import java.util.*

interface CredentialsRepository : JpaRepository<Credentials, UUID> {
    fun getByUserName(userName: String): Credentials?

    @Modifying
    @Query("UPDATE Credentials c SET c.password= ?1 where c.userName= ?2")
    fun updatePassword(password: String, userName: String)
}