package ch.cmi.proximitychat.model

import java.time.LocalDate

class Message(val user: User, val timestamp: LocalDate, val content: String) {
}