package Exceptions

import java.lang.RuntimeException

class ActiveCommentException(message: String): RuntimeException(message) {
}