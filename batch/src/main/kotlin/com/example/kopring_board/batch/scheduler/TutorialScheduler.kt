package com.example.kopring_board.batch.scheduler

import com.example.kopring_board.batch.config.TutorialConfig
import org.apache.logging.log4j.LogManager
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
@EnableScheduling
class TutorialScheduler(
    private val jobLauncher: JobLauncher,
    private val tutorialConfig: TutorialConfig
) {

    companion object {
        private val log = LogManager.getLogger()
    }

    @Scheduled(cron = "\${batch.crontab.tutorialJob}")
    fun executeJob() {
        log.info("execute TutorialScheduler job")
        try {
            tutorialConfig.tutorialJob()?.let {
                jobLauncher.run(
                    it,
                    JobParametersBuilder()
                        .addString("datetime", LocalDateTime.now().toString())
                        .toJobParameters()
                )
            }
        } catch (e: Exception) {
            log.error("execute TutorialScheduler job error", e)
        }
    }
}