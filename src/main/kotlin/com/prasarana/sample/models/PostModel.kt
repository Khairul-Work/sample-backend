package com.prasarana.sample.models

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Post")
@Table(name = "posts", schema = "sample")
class PostModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    var id: Long? = null
        private set

    var title: String? = null
    var content: String? = null

    @Column(name = "user_id", nullable = false)
    var userId: Long? = null

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
    var user: UserModel? = null
}

