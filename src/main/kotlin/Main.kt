var listNotes = mutableListOf<Notes>()
var notesIdCount = 0
var commentsIdCount = 0

fun main() {
    NotesService.addNotes("TestNotes!", "First Test", 1663412919, 1, true, "Test", "Test")
    NotesService.addNotes("TestNotes!", "Second Test", 1623115919, 1, false, "Test", "Test")
    NotesService.addNotes("TestNotes!", "Third Test", 1665315359, 1, true, "Test", "Test")
    NotesService.addNotes("TestNotes!", "Fourth Test", 1661915919, 1, true, "Test", "Test")
    NotesService.addNotes("TestNotes!", "Fifth Test", 1065315919, 1, true, "Test", "Test")

    NotesService.createComment(0, 13, 1, "HELLO NOTE", 1663412555)
    NotesService.createComment(4, 13, 1, "testing", 1662341755)
    NotesService.createComment(4, 13, 1, "HELLO HELLO", 1663412145)
    NotesService.createComment(4, 13, 1, "12345", 1663412395)
    NotesService.createComment(2, 24, 1, "deleteTest", 1663412395)
    NotesService.createComment(0, 24, 1, "getComments test", 1663412395)
    NotesService.createComment(0, 24, 1, "getComments test", 1663412999)

    println(NotesService.deleteNotes(4))
    println(NotesService.deleteComment(4))

    println(
        NotesService.editNotes(0, "Test Edit notes", "Test edit", 1969395919, 0, false, "teest", "teest")
    )
    println(NotesService.editComment(0, 24, "Hello edit test!"))

    println(NotesService.getNotes(1, 3, true))
    println(NotesService.getById(0, 11))
    println(NotesService.getComments(0, true, 1, 4))

    println(NotesService.restoreComment(4))

}