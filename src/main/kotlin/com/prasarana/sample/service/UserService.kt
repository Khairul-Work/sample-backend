package com.prasarana.sample.service

import com.prasarana.sample.models.UserModel
import com.prasarana.sample.repository.UserRepository
import org.mindrot.jbcrypt.BCrypt
import org.springframework.stereotype.Service

interface UserService {
    fun createUser(user: UserModel): UserModel
    fun getUser(id: Long): UserModel?
    fun updateUser(id:Long, updatedUser: UserModel): UserModel?
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

    override fun updateUser(id: Long, updatedUser: UserModel): UserModel? {
        val existingUser: UserModel? = userRepository.findById(id.toInt()).orElse(null)
        if (existingUser != null) {
            // Update the existing user with the new data
            existingUser.name = updatedUser.name
            existingUser.email = updatedUser.email
            // ... Update other properties as needed

            // Save the updated user
            return userRepository.save(existingUser!!)
        }
        return null
    }

    override fun deleteUser(id: Long) {
        userRepository.deleteById(id.toInt())
    }

    override fun findByEmail(email: String): UserModel? {
        return userRepository.findByEmail(email)
    }

    override fun loginUser(email: String, password: String): UserModel? {
        val user = userRepository.findByEmail(email)
        if (user != null && _verifyPassword(password, user.password!!)) {
            return user
        }
        return null
    }

    private fun _verifyPassword(rawPassword: String, hashedPassword: String): Boolean {
        // Implement password verification logic here, e.g., using BCrypt.checkpw()
        return BCrypt.checkpw(rawPassword, hashedPassword)
    }

}
