package com.example.kopring_board.integrated.db.entity

import com.example.kopring_board.integrated.common.BaseTime
import com.example.kopring_board.integrated.common.PasswordConverter
import com.example.kopring_board.integrated.user.Grade
import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@EntityListeners(value = [AuditingEntityListener::class])
@Table(name = "user")
data class User(
    @Id
    val id: String = "",
    @Column(nullable = false, length = 15)
    var name: String? = "",
    @Column(nullable = false)
    var email: String? = "",
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var grade: Grade = Grade.GREEN,
    @Column
    var point: Long = 0,
): BaseTime() {

//    @JsonIgnore
    @Column
    @Convert(converter = PasswordConverter::class)
    val password: String? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as User

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun toString(): String {
        return "User(id=$id, name='$name', email='$email', grade='$grade', point='$point', createdAt='$createdAt', modifiedAt='$modifiedAt')"
    }
}