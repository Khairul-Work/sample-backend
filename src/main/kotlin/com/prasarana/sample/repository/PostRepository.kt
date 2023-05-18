package com.prasarana.sample.repository

import com.prasarana.sample.models.PostModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PostRepository : JpaRepository<PostModel, Long> {
    // Add any custom query methods if needed
    fun findByUserId(userId: Long): List<PostModel>
}
