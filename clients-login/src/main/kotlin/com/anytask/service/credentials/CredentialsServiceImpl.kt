package com.anytask.service.credentials

import com.anytask.models.dto.user.AnyTaskUserDto
import com.anytask.models.entity.user.Credentials
import com.anytask.models.entity.user.Status
import com.anytask.models.mapper.user.CredentialsMapper
import com.anytask.repository.credentials.CredentialsRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional
open class CredentialsServiceImpl(
    @Autowired
    private val credentialsRepository: CredentialsRepository,
    private val credentialsMapper: CredentialsMapper,
    private val passwordEncoder: BCryptPasswordEncoder
): CredentialsService {

    override fun saveCredentials(userDto: AnyTaskUserDto, id: UUID): Credentials {
        return credentialsRepository.save(credentialsMapper.mapToCredentials(userDto, id))
    }

    @Transactional
    override fun updateForgotPassword(userName: String, password: String): Boolean {
        val userCredentials = credentialsRepository.getByUserName(userName) != null
        return if(userCredentials){
            credentialsRepository.updatePassword(
                passwordEncoder.encode(password),
                userName
            )
            userCredentials
        }else{
            userCredentials
        }
    }

    override fun checkIfUserNameExistsActive(userName: String): Boolean {
        val user = credentialsRepository.getByUserName(userName = userName)
        if(user != null){
            return if(user.status == Status.ACTIVE) true else throw UnsupportedOperationException("User is in-activated.")
        }else{
            throw UnsupportedOperationException("User is not found.")
        }
    }


    override fun checkIfPasswordExists(userName: String, password: String): Credentials {
        val checkLogin =
            credentialsRepository.getByUserName(userName)?.let { passwordEncoder.matches(password, it.password) }
                ?: throw UnsupportedOperationException("User is not found.")
        if (checkLogin) {
            return credentialsRepository.getByUserName(userName)!!
        } else {
            throw UnsupportedOperationException("Password doesn't match.")
        }
    }
}