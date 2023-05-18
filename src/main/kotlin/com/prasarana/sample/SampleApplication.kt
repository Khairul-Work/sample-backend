package com.prasarana.sample

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
class SampleApplication

fun main(args: Array<String>) {
	runApplication<SampleApplication>(*args)
}
