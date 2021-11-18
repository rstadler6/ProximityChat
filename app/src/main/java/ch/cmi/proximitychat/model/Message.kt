package ch.cmi.proximitychat.model

import java.time.LocalDate

class Message(user: User, timestamp: LocalDate, content: String) {
    lateinit var user: User
    lateinit var timestamp: LocalDate
    lateinit var content: String
}