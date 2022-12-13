package com.example.kopring_board.integrated.db.entity

import com.example.kopring_board.integrated.common.BaseTime
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@EntityListeners(value = [AuditingEntityListener::class])
@Table(name = "comment")
data class Comment(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long = 0L,
    @Column(nullable = false, length = 200)
    var content: String = "",

    @ManyToOne
    @JoinColumn(name = "post_id")
    val post: Post? = null,

    @ManyToOne
    @JoinColumn(name = "user_id")
    val author: User? = null,

    @Column
    var deletedAt: LocalDateTime? = null,
): BaseTime() {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Comment

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }

    override fun toString(): String {
        return "Comment(id='$id', content='$content', post='$post', author='$author', deletedAt='$deletedAt'"
    }
}
