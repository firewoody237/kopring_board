package com.example.kopring_board.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.servlet.ServletComponentScan
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication(scanBasePackages = ["com.example.kopring_board"])
@ServletComponentScan(basePackages = ["com.example.kopring_board"])
class ApiApplication{

}

fun main(args: Array<String>) {
    runApplication<ApiApplication>(*args)
}
