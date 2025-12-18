package models

import java.time.LocalDateTime

case class BookEntry (id: Int, user_id: Int, isbn: String,
                      created_at: LocalDateTime, status: String,
                      pages_read: Integer)