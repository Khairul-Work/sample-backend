package com.prasarana.sample.models

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Data
import lombok.NoArgsConstructor

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "User")
@Table(name = "users", schema = "sample")
class UserModel{
        // Add more properties as needed
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(nullable = false, updatable = false)
        var id: Long? = null
                private set
        @Column(nullable = false)
        var password: String? = null
        var firstName: String? = null
        var lastName: String? = null
        var age: Int? = null
        var name: String? = null
        var username: String? = null
        @Column(nullable = false)
        var email: String? = null
}