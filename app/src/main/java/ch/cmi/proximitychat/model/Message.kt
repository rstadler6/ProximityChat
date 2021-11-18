package ch.cmi.proximitychat.model

import java.time.LocalDate

class Message {
    lateinit var user: User
    lateinit var timestamp: LocalDate
    lateinit var content: String
}