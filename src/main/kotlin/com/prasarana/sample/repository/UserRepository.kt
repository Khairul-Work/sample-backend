package com.prasarana.sample.repository

import com.prasarana.sample.models.UserModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface UserRepository : JpaRepository<UserModel, Int?> {
    @Query(value = "SELECT u FROM User u WHERE u.name= :name")
    fun findByName(name: String?): List<UserModel?>?


    fun findByEmail(email: String): UserModel?
}

