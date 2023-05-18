package com.prasarana.sample.controller
import com.fasterxml.jackson.databind.ser.std.StdKeySerializers.Dynamic
import com.prasarana.sample.models.PostModel
import com.prasarana.sample.models.UserModel
import com.prasarana.sample.service.PostService
import com.prasarana.sample.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/posts")
class PostController(private val postService: PostService, private val userService: UserService) {

    @PostMapping
    fun createPost(@RequestBody post: PostModel): ResponseEntity<PostModel> {
        val user = userService.getUser(post.userId!!) // Assuming you have a userService to retrieve the user by ID

        return if (user != null) {
            val createdPost = postService.createPost(post)
            ResponseEntity(createdPost, HttpStatus.CREATED)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }


    @GetMapping("/{id}")
    fun getPostById(@PathVariable id: Long): ResponseEntity<PostModel> {
        val post = postService.getPostById(id)
        return if (post != null) {
            ResponseEntity(post, HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @GetMapping
    fun getAllPosts(): ResponseEntity<List<PostModel>> {
        val posts = postService.getAllPosts()
        return ResponseEntity(posts, HttpStatus.OK)
    }

    @PutMapping("/{id}")
    fun updatePost(@PathVariable id: Long, @RequestBody post: PostModel): ResponseEntity<PostModel> {
        val existingPost = postService.getPostById(id)
        return if (existingPost != null) {
            // Update the necessary fields of the existing post
            existingPost.title = post.title
            existingPost.content = post.content

            // Update the relationship with the User (if applicable)
            existingPost.user = post.user

            // Save the updated post
            val updatedPost = postService.updatePost(existingPost)
            ResponseEntity(updatedPost, HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @DeleteMapping("/{id}")
    fun deletePost(@PathVariable id: Long): ResponseEntity<Unit> {
        postService.deletePost(id)
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }

    @GetMapping("/user/{userId}")
    fun getPostsByUserId(@PathVariable userId: Long): ResponseEntity<Map<String, Any?>> {
        val emptyImmutableMap = emptyMap<String, Any?>().toMutableMap()

        val posts = postService.getPostsByUserId(userId)
        var list = listOf<PostModel>()
        return if (posts.isNotEmpty()) {
            list = posts
            emptyImmutableMap["status"] = "success"
            emptyImmutableMap["data"] = list
            ResponseEntity(emptyImmutableMap, HttpStatus.OK)
        } else {
            emptyImmutableMap["status"] = "failed"
            emptyImmutableMap["data"] = list
            ResponseEntity(emptyImmutableMap, HttpStatus.NOT_FOUND)
        }
    }

}
