package com.example.kopring_board.integrated.db.entity;

import com.example.kopring_board.integrated.common.BaseTime
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import javax.persistence.*;

@Entity
@EntityListeners(value = [AuditingEntityListener::class])
@Table(name = "heart")
data class Heart (
    @Id
    @GeneratedValue
    val id: Long = 0L,

    @ManyToOne
    @JoinColumn(name="user_id")
    val user: User? = null,

    @ManyToOne
    @JoinColumn(name="post_id")
    val post: Post? = null
): BaseTime() {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Heart

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun toString(): String {
        return "Heart(id=$id, user='$user', createdAt='$createdAt', modifiedAt='$modifiedAt')"
    }
}
