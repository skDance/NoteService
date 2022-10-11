import Exceptions.ActiveCommentException
import Exceptions.CommentNotFoundException
import Exceptions.NoAccessException
import Exceptions.NoteNotFoundException
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import javax.xml.stream.events.Comment
import kotlin.math.exp

class NotesServiceTest {
    @Before
    fun clearBeforeTest() {
        NotesService.clear()
    }

    @Test
    fun addNotesSuccess() {
        val result = NotesService.addNotes("test", "test", 1122334455, 1, true, "test", "test")
        assertEquals(0, result)
    }

    @Test
    fun createCommentSuccess() {
        NotesService.addNotes("add", "comments", 1122334455, 1, true, "test", "test")
        val result = NotesService.createComment(0, 21, 23, "test comment", 1113332222)
        assertEquals(0, result)
    }

    @Test(expected = NoAccessException::class)
    fun createCommentWithoutAccess() {
        NotesService.addNotes("add", "comments", 1122334455, 1, false, "test", "test")
        NotesService.createComment(0, 11, 11, "test comment", 1113332222)
    }

    @Test(expected = NoteNotFoundException::class)
    fun createCommentNoteNotFound() {
        NotesService.addNotes("add", "comments1", 1122334455, 1, true, "test", "test")
        NotesService.addNotes("add", "comments2", 1122334455, 1, true, "test", "test")
        NotesService.addNotes("add", "comments3", 1122334455, 1, true, "test", "test")
        NotesService.createComment(5, 11, 11, "test comment1", 1113332222)

    }

    @Test
    fun deleteNoteSuccess() {
        NotesService.addNotes("delete", "note", 1122334455, 1, true, "test", "test")
        val result = NotesService.deleteNotes(0)
        assertTrue(result)
    }

    @Test(expected = NoteNotFoundException::class)
    fun deleteNoteError() {
        NotesService.addNotes("delete", "note", 1122334455, 1, true, "test", "test")
        NotesService.deleteNotes(1)
    }

    @Test
    fun deleteCommentSuccess() {
        NotesService.addNotes("delete", "comment", 1122334455, 1, true, "test", "test")
        NotesService.createComment(0, 11, 11, "test delete", 1113332222)
        assertTrue(NotesService.deleteComment(0))
    }

    @Test(expected = CommentNotFoundException::class)
    fun deleteCommentError() {
        NotesService.addNotes("delete", "comment", 1122334455, 1, true, "test", "test")
        NotesService.createComment(0, 11, 11, "test delete", 1113332222)
        NotesService.deleteComment(1)
    }

    @Test
    fun editNoteSuccess() {
        NotesService.addNotes("edit", "note", 1122334455, 1, true, "test", "test")
        assertTrue(NotesService.editNotes(0, "we", "edit note", 12121212, 1, true, "test", "test"))
    }

    @Test(expected = NoteNotFoundException::class)
    fun editNoteError() {
        NotesService.addNotes("edit", "note", 1122334455, 1, true, "test", "test")
        NotesService.editNotes(1, "not found", "note", 12121212, 1, true, "test", "test")
    }

    @Test
    fun editCommentSuccess() {
        NotesService.addNotes("edit", "comment", 1122334455, 1, true, "test", "test")
        NotesService.createComment(0, 11, 11, "test edit", 1113332222)
        assertTrue(NotesService.editComment(0, 11, "success edit"))
    }

    @Test(expected = CommentNotFoundException::class)
    fun editCommentError() {
        NotesService.addNotes("edit", "comment", 1122334455, 1, true, "test", "test")
        NotesService.createComment(0, 11, 11, "test edit", 1113332222)
        NotesService.editComment(1, 11, "comment not found")
    }

    @Test
    fun getNotesSuccess() {
        NotesService.addNotes("get", "notes1", 1122334455, 1, true, "test", "test")
        NotesService.addNotes("get", "notes2", 1122334455, 1, true, "test", "test")
        NotesService.addNotes("get", "notes3", 1122334455, 1, true, "test", "test")
        val result = NotesService.getNotes(2, 1, true)
        assertEquals(2, result[0].noteId)
    }

    @Test(expected = NoteNotFoundException::class)
    fun getNotesError() {
        NotesService.addNotes("get", "notes1", 1122334455, 1, true, "test", "test")
        NotesService.addNotes("get", "notes2", 1122334455, 1, true, "test", "test")
        NotesService.addNotes("get", "notes3", 1122334455, 1, true, "test", "test")
        NotesService.getNotes(3, 1, true)
    }

    @Test
    fun getByIdSuccess() {
        NotesService.addNotes("getById", "notes1", 1122334455, 1, true, "test", "test")
        NotesService.addNotes("getById", "notes2", 1122334455, 1, true, "test", "test")
        NotesService.addNotes("getById", "notes3", 1122334455, 1, true, "test", "test")
        val result = NotesService.getById(2, 11)
        assertEquals(2, result.noteId)
    }

    @Test(expected = NoteNotFoundException::class)
    fun getByIdError() {
        NotesService.addNotes("getById", "notes1", 1122334455, 1, true, "test", "test")
        NotesService.addNotes("getById", "notes2", 1122334455, 1, true, "test", "test")
        NotesService.addNotes("getById", "notes3", 1122334455, 1, true, "test", "test")
        NotesService.getById(3, 11)
    }

    @Test
    fun getCommentsSuccess() {
        NotesService.addNotes("get", "comment", 1122334455, 1, true, "test", "test")
        NotesService.createComment(0, 11, 11, "test get", 1113332222)
        NotesService.createComment(0, 11, 11, "test get", 1113332222)
        NotesService.createComment(0, 11, 11, "test get", 1113332222)
        val result = NotesService.getComments(0, true, 2, 1)
        assertEquals(2, result[0].commentId)
    }

    @Test(expected = CommentNotFoundException::class)
    fun getCommentsError() {
        NotesService.addNotes("get", "comment", 1122334455, 1, true, "test", "test")
        NotesService.createComment(0, 11, 11, "test get", 1113332222)
        NotesService.createComment(0, 11, 11, "test get", 1113332222)
        NotesService.createComment(0, 11, 11, "test get", 1113332222)
        NotesService.getComments(0, true, 3, 1)
    }

    @Test
    fun restoreCommentSuccess() {
        NotesService.addNotes("restore", "comment", 1122334455, 1, true, "test", "test")
        NotesService.createComment(0, 11, 11, "test restore", 1113332222)
        NotesService.deleteComment(0)
        assertTrue(NotesService.restoreComment(0))
    }

    @Test(expected = ActiveCommentException::class)
    fun restoreActiveCommentError() {
        NotesService.addNotes("restore", "comment", 1122334455, 1, true, "test", "test")
        NotesService.createComment(0, 11, 11, "test restore", 1113332222)
        assertTrue(NotesService.restoreComment(0))
    }

    @Test(expected = CommentNotFoundException::class)
    fun restoreCommentNoteNotFoundError() {
        NotesService.addNotes("restore", "comment", 1122334455, 1, true, "test", "test")
        NotesService.createComment(0, 11, 11, "test restore", 1113332222)
        assertTrue(NotesService.restoreComment(1))
    }
}