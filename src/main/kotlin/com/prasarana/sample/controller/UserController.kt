package com.prasarana.sample.controller

import com.prasarana.sample.models.LoginCredentials
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

import com.prasarana.sample.models.UserModel
import com.prasarana.sample.service.UserService
import org.mindrot.jbcrypt.BCrypt
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import io.jsonwebtoken.JwtBuilder

@RestController
@RequestMapping("/users")
class UserController @Autowired constructor(private val userService: UserService) {

    // Create a new user
    @PostMapping
    fun createUser(@RequestBody user: UserModel): ResponseEntity<Any?> {
        val userExist = userService.findByEmail(user.email!!)
        val resultMap = mutableMapOf<String, Any>()

        if(userExist != null){
            resultMap["status"] = "failed"
            resultMap["message"] = "User already exist"
            return ResponseEntity(resultMap, HttpStatus.CONFLICT)
        }
        //hash password
        val salt = BCrypt.gensalt()
        val hashedPassword = BCrypt.hashpw(user.password, salt)

        //assign hashed password into the variable password
        user.password = hashedPassword
        //create user
        val createdUser = userService.createUser(user)

        //create resultmap
        resultMap["status"] = "success"
        resultMap["message"] = "User registered"
        resultMap["data"] = createdUser
        return ResponseEntity(resultMap, HttpStatus.CREATED)
    }

    // Get a user by ID
    @GetMapping("/{id}")
    fun getUser(@PathVariable("id") id: Long): UserModel {
        val user = userService.getUser(id)

        // Access the properties using the getter methods
        val userId = user?.id
        val name = user?.name
        val username = user?.username
        val email = user?.email

        // Process the user object as needed
        // ...

        return user!!
    }

    // Update a user
    @PutMapping("/{id}")
    fun updateUser(@PathVariable("id") id: Long, @RequestBody updatedUser: UserModel): UserModel? {
        val user = userService.getUser(id)

        // Access the properties using the setter methods
        user?.name = updatedUser.name
        user?.username = updatedUser.username
        user?.email = updatedUser.email

        return userService.updateUser(id, user!!)
    }

    // Delete a user
    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable("id") id: Long) {
        userService.deleteUser(id)
    }

    @PostMapping("/login")
    fun loginUser(@RequestBody credentials: LoginCredentials): ResponseEntity<String> {
        val loggedInUser = userService.loginUser(credentials.username, credentials.password)
        return if (loggedInUser != null) {
            // Generate and return a JWT token for authentication

//            val token = JwtBuilder.
            ResponseEntity("token", HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.UNAUTHORIZED)
        }
    }
}
