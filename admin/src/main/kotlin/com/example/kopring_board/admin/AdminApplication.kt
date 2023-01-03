package com.example.kopring_board.admin

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.servlet.ServletComponentScan
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@EnableJpaAuditing
@SpringBootApplication(scanBasePackages = ["com.example.kopring_board"])
@ServletComponentScan(basePackages = ["com.example.kopring_board"])

class AdminApplication

fun main(args: Array<String>) {
    runApplication<AdminApplication>(*args)
}
