package com.example.kopring_board.integrated.db.entity

import com.example.kopring_board.integrated.common.BaseTime
import com.example.kopring_board.integrated.post.Category
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@EntityListeners(value = [AuditingEntityListener::class])
@Table(name = "post")
class Post(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null,
    @Column(nullable = false, length = 100)
    val title: String? = null,
    @Column(nullable = false)
    val content: String? = null,
    @Column
    @Enumerated(EnumType.STRING)
    val category: Category? = null,

    @Column
    val heart: Int = 0,
    @Column
    val commentCount: Int = 0,

    @ManyToOne
    @JoinColumn(name="user_id")
    val author: User? = null,

    @Column
    val deletedAt: LocalDateTime? = null,
): BaseTime() {

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
        return "Post(id=$id, title='$title', content='$content', heart='$heart', commentCount='$commentCount', category='$category', author='$author', deletedAt='$deletedAt')"
    }
}