package com.prasarana.sample.service

import com.prasarana.sample.models.UserModel
import com.prasarana.sample.repository.UserRepository
import org.mindrot.jbcrypt.BCrypt
import org.springframework.stereotype.Service

interface UserService {
    fun createUser(user: UserModel): UserModel
    fun getUser(id: Long): UserModel?
    fun updateUser(updatedUser: UserModel): UserModel?
    fun deleteUser(id: Long)
    fun findByEmail(email: String): UserModel?
    fun loginUser(email: String, password: String): UserModel?

}
@Service
class UserServiceImpl(private val userRepository: UserRepository): UserService {
    override fun createUser(user: UserModel): UserModel {
        return userRepository.save(user)
    }

    override fun getUser(id: Long): UserModel? {
        return userRepository.findById(id.toInt()).orElse(null)
    }

    override fun updateUser( updatedUser: UserModel): UserModel? {
        return userRepository.save(updatedUser)
    }

    override fun deleteUser(id: Long) {
        userRepository.deleteById(id.toInt())
    }

    override fun findByEmail(email: String): UserModel? {
        return userRepository.findByEmail(email)
    }

    override fun loginUser(email: String, password: String): UserModel? {
        val user = userRepository.findByEmail(email)
        if (user != null && verifyPassword(password, user.password!!)) {
            return user
        }
        return null
    }

    private fun verifyPassword(rawPassword: String, hashedPassword: String): Boolean {
        // Implement password verification logic here, e.g., using BCrypt.checkpw()
        return BCrypt.checkpw(rawPassword, hashedPassword)
    }

}
