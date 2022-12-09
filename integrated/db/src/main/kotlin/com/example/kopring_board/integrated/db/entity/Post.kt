package com.example.kopring_board.integrated.db.entity

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "post")
class Post(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null,
    @Column(nullable = false, length = 100)
    val title: String? = null,
    @Column(nullable = false)
    val content: String? = null,

    @ManyToOne
    @JoinColumn(name="user_id")
    val author: User? = null,

    @CreatedDate
    val createdAt: LocalDateTime? = null,
    @LastModifiedDate
    val modifiedAt: LocalDateTime? = null,
    @LastModifiedDate
    val deletedAt: LocalDateTime? = null,

    //heart : Long

    //commetCount : Long

) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Post

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }

    override fun toString(): String {
        return "Post(id=$id, title='$title', content='$content', author='$author', createdAt='$createdAt', modifiedAt='$modifiedAt')"
    }
}