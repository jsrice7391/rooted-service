package com.stayrooted

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class StayRootedApplication

fun main(args: Array<String>) {
    runApplication<StayRootedApplication>(*args)
}