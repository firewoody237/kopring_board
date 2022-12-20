package com.example.kopring_board.batch.tasklet

import org.apache.logging.log4j.LogManager
import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.stereotype.Component

@Component
class TutorialTasklet : Tasklet {

    companion object {
        private val log = LogManager.getLogger()
    }

    override fun execute(stepContribution: StepContribution, chunkContext: ChunkContext): RepeatStatus? {
        log.info("execute tasklet!!")
        return RepeatStatus.FINISHED
    }
}