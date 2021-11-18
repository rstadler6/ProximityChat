package ch.cmi.proximitychat.model

import java.time.LocalDate

class Message(val user: User, val content: String, val timestamp: LocalDate) {
}