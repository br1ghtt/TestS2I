package ch.buedev.MyTestApp

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id


@SpringBootApplication
class MyTestAppApplication

fun main(args: Array<String>) {
    runApplication<MyTestAppApplication>(*args)
}

@Entity(name = "MESSAGES")
class Message(@Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long?, val body: String)

interface MessageRepository: CrudRepository<Message, Long>

@Service
class MessageService(private val repository: MessageRepository) {
	fun getAllMessages(): MutableIterable<Message> = repository.findAll()
	fun post(message: Message): Message = repository.save(message)
}

@RestController
class MessageResource(private val service: MessageService) {

	@GetMapping
	fun index(): List<Message> = service.getAllMessages().toList()

	@PostMapping
	fun post(@RequestBody message: Message) {
		println(service.post(message).toString())
	}
}