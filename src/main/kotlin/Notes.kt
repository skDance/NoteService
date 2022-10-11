class Notes(
    val noteId: Int,
    var title: String,
    var text: String,
    var date: Long,
    var privacy: Int,
    var commentPrivacy: Boolean,
    var privacyView: String,
    var privacyComment: String,
    var listComments: MutableList<Comments> = mutableListOf<Comments>()
) {
    override fun toString(): String {
        return "noteId: $noteId, title: $title,text: $text, date: $date, privacy: $privacy,commentPrivacy: $commentPrivacy," +
                "privacyView: $privacyView, privacyComment: $privacyComment, listComments: $listComments"
    }
}