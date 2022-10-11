class Comments(
    val commentId: Int,
    val noteId: Int,
    val ownerId: Int,
    val replyTo: Int,
    var message: String,
    var date: Long,
    var isDeleted: Boolean = false
) {
    override fun toString(): String {
        return "commentId: $commentId, noteId: $noteId, ownerId: $ownerId, replyTo: $replyTo, message: $message, date: $date"
    }
}