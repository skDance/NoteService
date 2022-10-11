package Exceptions

import java.lang.RuntimeException

class CommentNotFoundException(message: String) : RuntimeException(message) {
}