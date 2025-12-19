package models

import java.time.LocalDateTime

case class BookEntry (id: Long, user_id: Long, isbn: String,
                      created_at: LocalDateTime = LocalDateTime.now(), status: BookStatus = BookStatus.ToRead,
                      pages_read: Int = 0)