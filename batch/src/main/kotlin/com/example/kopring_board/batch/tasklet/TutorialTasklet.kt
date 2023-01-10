package com.example.kopring_board.batch.tasklet

import com.example.kopring_board.integrated.db.repository.PostRepository
import com.example.kopring_board.integrated.db.repository.UserRepository
import org.apache.logging.log4j.LogManager
import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Component

@Component
class TutorialTasklet(
    val userRepository: UserRepository,
    val postRepository: PostRepository
) : Tasklet {


    companion object {
        private val log = LogManager.getLogger()
    }

    override fun execute(stepContribution: StepContribution, chunkContext: ChunkContext): RepeatStatus? {
        log.info("execute tasklet!!")
        // call admin api


        // ----------------------------

        val countPostEachUser = postRepository.findAll().groupingBy { it.author?.id }.eachCount()
            .toList().sortedByDescending { (_, value) -> value }

        log.debug("countPostEachUser = {}", countPostEachUser)

        val countPostEachUser2 = postRepository.findAll().groupingBy { it.author?.id }.eachCount()

        log.debug("countPostEachUser2 = {}", countPostEachUser2)


        // get posts top 5 order by heart desc from db

        //loop

        //smtp email or call api
        return RepeatStatus.FINISHED
    }
}