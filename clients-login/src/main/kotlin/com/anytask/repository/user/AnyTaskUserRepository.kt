package com.anytask.repository.user

import com.anytask.models.entity.user.AnyTaskUser
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import java.util.*

interface AnyTaskUserRepository : JpaRepository<AnyTaskUser, UUID> {
    fun getById(Id: UUID): AnyTaskUser?

    @Query("SELECT u FROM AnyTaskUser u where u.email= ?1")
    fun getByEmail(email: String): AnyTaskUser?

    @Modifying
    @Query("UPDATE Credentials c SET c.password= ?1 where c.userName= ?2")
    fun updatePassword(password: String, userName: String)
}