import Exceptions.ActiveCommentException
import Exceptions.CommentNotFoundException
import Exceptions.NoAccessException
import Exceptions.NoteNotFoundException

object NotesService {

    fun clear() {
        listNotes = mutableListOf<Notes>()
        notesIdCount = 0
        commentsIdCount = 0
    }

    fun addNotes(
        title: String,
        text: String,
        date: Long,
        privacy: Int,
        commentPrivacy: Boolean,
        privacyView: String,
        privacyComment: String
    ): Int {
        val newNotes = Notes(notesIdCount, title, text, date, privacy, commentPrivacy, privacyView, privacyComment)
        listNotes += newNotes
        println("Success create new note with id: ${notesIdCount++}")
        return listNotes.last().noteId
    }

    fun createComment(noteId: Int, ownerId: Int, replyTo: Int, message: String, date: Long): Int {
        for (index in listNotes.withIndex()) {
            when {
                noteId == index.value.noteId && index.value.commentPrivacy -> {
                    val newComment = Comments(commentsIdCount, noteId, ownerId, replyTo, message, date)
                    index.value.listComments += newComment
                    println("Success create new comment with id: ${commentsIdCount++}")
                    return index.value.listComments.last().commentId
                }

                noteId == index.value.noteId && !index.value.commentPrivacy -> throw NoAccessException("No access to note comments")
            }
        }
        throw NoteNotFoundException("No note with id: $noteId")
    }

    fun deleteNotes(noteId: Int): Boolean {
        for (element in listNotes) {
            if (noteId == element.noteId) {
                listNotes.remove(element)
                return true
            }
        }
        throw NoteNotFoundException("No note with id: $noteId")
    }

    fun deleteComment(commentId: Int): Boolean {
        for (element in listNotes) {
            for (index in element.listComments.withIndex()) {
                if (commentId == index.value.commentId) {
                    index.value.isDeleted = true
                    return true
                }
            }
        }
        throw CommentNotFoundException("No comment with id: $commentId")
    }

    fun editNotes(
        noteId: Int,
        title: String,
        text: String,
        date: Long,
        privacy: Int,
        commentPrivacy: Boolean,
        privacyView: String,
        privacyComment: String
    ): Boolean {
        for (element in listNotes) {
            if (noteId == element.noteId) {
                val updateNote = Notes(
                    noteId,
                    title,
                    text,
                    date,
                    privacy,
                    commentPrivacy,
                    privacyView,
                    privacyComment,
                    element.listComments
                )
                listNotes[element.noteId] = updateNote
                return true
            }
        }
        throw NoteNotFoundException("No note with id: $noteId")
    }

    fun editComment(commentId: Int, ownerId: Int, message: String): Boolean {
        for (element in listNotes) {
            for (index in element.listComments.withIndex()) {
                if (commentId == index.value.commentId && !index.value.isDeleted) {
                    index.value.message = message
                    return true
                }
            }
        }
        throw CommentNotFoundException("No comment with id: $commentId")
    }

    fun getNotes(offset: Int, count: Int, sort: Boolean): MutableList<Notes> {
        var getterListNotes = mutableListOf<Notes>()
        for (index in listNotes.withIndex()) {
            if (index.value.noteId in offset..(offset + count)) {
                getterListNotes += index.value
            }
        }
        if (getterListNotes.isEmpty()) {
            throw NoteNotFoundException("Notes not found")
        } else {
            if (sort) getterListNotes.sortBy { it.date } else getterListNotes.sortByDescending { it.date }
            return getterListNotes
        }
    }

    fun getById(noteId: Int, ownerId: Int): Notes {
        for (index in listNotes.withIndex()) {
            if (noteId == index.value.noteId) {
                return index.value
            }
        }
        throw NoteNotFoundException("No note with id: $noteId")
    }

    fun getComments(noteId: Int, sort: Boolean, offset: Int, count: Int): MutableList<Comments> {
        var resultCommentsArray = mutableListOf<Comments>()

        for (index in listNotes.withIndex()) {
            if (noteId == index.value.noteId) {
                var commentsList = index.value.listComments

                if (sort) {
                    commentsList.sortBy { it.date }
                } else {
                    commentsList.sortByDescending { it.date }
                }
                for (index in commentsList) {
                    if (index.commentId >= offset && !index.isDeleted) {
                        if (resultCommentsArray.size < count) {
                            resultCommentsArray += index
                        }
                    }
                }
            }
        }
        if (resultCommentsArray.isEmpty()) throw CommentNotFoundException("Comments not found") else return resultCommentsArray
    }

    fun restoreComment(commentId: Int): Boolean {
        for (index in listNotes) {
            for (element in index.listComments) {
                when {
                    element.commentId == commentId && element.isDeleted -> {
                        element.isDeleted = false
                        return true
                    }

                    element.commentId == commentId && !element.isDeleted -> throw ActiveCommentException("Comment with id $commentId is Active")
                }
            }
        }
        throw CommentNotFoundException("No comment with id $commentId")
    }
}