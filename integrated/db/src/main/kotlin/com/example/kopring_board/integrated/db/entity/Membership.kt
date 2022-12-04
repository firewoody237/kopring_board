package com.example.kopring_board.integrated.db.entity

import com.example.kopring_board.integrated.user.Grade
import org.jetbrains.annotations.NotNull
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "membership")
data class Membership(
    @Id
    @OneToOne
    @JoinColumn(name="user_id")
    val id: User? = null,
    @Enumerated(EnumType.STRING)
    val grade: Grade? = null,
    @Column
    val point: Int? = null,

    @CreatedDate
    val joinedAt: LocalDateTime? = null,
    @LastModifiedDate
    val modifiedAt: LocalDateTime? = null,
): java.io.Serializable {

    override fun equals(other: Any?): Boolean {
        return super.equals(other)
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }

    override fun toString(): String {
        return super.toString()
    }
}
