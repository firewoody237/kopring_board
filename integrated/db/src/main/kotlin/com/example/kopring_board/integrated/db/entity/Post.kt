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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "post")
    var heartList: MutableList<Heart>? = mutableListOf(),

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "post")
    val commentList: MutableList<Comment> = mutableListOf(),

    @ManyToOne(fetch = FetchType.EAGER)
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

//    프런트(리액트, 앵귤러, 뷰)
//
//    프런트(회원) -> 회원 가입, 회원 탈퇴, 회원 수정, 회원 정보(캐시 서버)
//
//
//    회원 팀 -> 회원 테이블(회원 가입, 회원 정보 제공, 회원 탈퇴, 회원 수정)
//    (어드민)
//
//
//    회원정보
//  천안 gslb 게시판 팀 -> 게시판 테이블
//    (배치, 어드민)
//
//    gslb 쪽지, 선물 팀 -> 쪽지 테이블
//
//  천안  gslb 잔액 팀 -> 잔액 관리 테이블
//
//
//    어드민
//    api -> 같은 디비
//    batch
//
//    어드민(7, 8)
//    은행 모듈 (1, 2, 3)
//    잔액 모듈 (4, 5, 6)-> 하나의 디비(유저)
//    배치(7)






    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun toString(): String {
        return "Post(id=$id, title='$title', content='$content', heartList='$heartList', commentList='$commentList', category='$category', author='$author', deletedAt='$deletedAt')"
    }
}