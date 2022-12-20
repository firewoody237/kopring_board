package com.example.kopring_board.integrated.db.entity

import com.example.kopring_board.integrated.common.BaseTime
import com.example.kopring_board.integrated.post.Category
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@EntityListeners(value = [AuditingEntityListener::class])
@Table(name = "post")
data class Post(
    @Id
    @GeneratedValue
    val id: Long = 0L,
    @Column(nullable = false, length = 100)
    var title: String = "",
    @Column(nullable = false, length = 10000)
    var content: String = "",
    @Column
    @Enumerated(EnumType.STRING)
    var category: Category = Category.UNCATEGORIZED,

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY)
    var heart: List<Heart>? = null,
    @OneToMany(mappedBy = "post", fetch = FetchType.EAGER)
    val comment: List<Comment>? = null,

    @ManyToOne //TODO: check LAZY?
    @JoinColumn(name="user_id")
    val author: User? = null,

    @Column
    var deletedAt: LocalDateTime? = null,
): BaseTime() {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Post

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun toString(): String {
        return "Post(id=$id, title='$title', content='$content', heart='$heart', comment='$comment', category='$category', author='$author', deletedAt='$deletedAt')"
    }
}