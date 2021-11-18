package ch.cmi.proximitychat.model

class Chat(val user: User) {
    val messages: ArrayList<Message> = ArrayList()
}