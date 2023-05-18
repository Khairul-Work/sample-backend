package com.prasarana.sample.service

import com.prasarana.sample.models.PostModel
import com.prasarana.sample.repository.PostRepository
import org.springframework.stereotype.Service
interface PostService {
    fun createPost(post: PostModel): PostModel
    fun getPostById(id: Long): PostModel?
    fun getAllPosts(): List<PostModel>
    fun updatePost(post: PostModel): PostModel?
    fun deletePost(id: Long)

    fun getPostsByUserId(userId:Long): List<PostModel>
}


@Service
class PostServiceImpl(private val postRepository: PostRepository) : PostService {

    override fun createPost(post: PostModel): PostModel {
        return postRepository.save(post)
    }

    override fun getPostById(id: Long): PostModel? {
        return postRepository.findById(id).orElse(null)
    }

    override fun getAllPosts(): List<PostModel> {
        return postRepository.findAll()
    }

    override fun updatePost(post: PostModel): PostModel? {
        return if (postRepository.existsById(post.id!!)) {
            postRepository.save(post)
        } else {
            null
        }
    }

    override fun deletePost(id: Long) {
        postRepository.deleteById(id)
    }

    override fun getPostsByUserId(userId: Long): List<PostModel> {
        return postRepository.findByUserId(userId)
    }

}
