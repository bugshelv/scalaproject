package utils

import models.{Book, BookStatus, Entry}
import java.nio.file.Paths

object BookUtils {
  def ensureCover(book: Book): Book =
    if (book.cover.isEmpty) book.copy(cover = "/assets/images/placeholder_cover.png")
    else book

  def filterAndSortBooks(entries: List[Entry], books: Seq[Book], filters: Map[String, String]): (List[Entry], List[Book]) = {
    val statusFilter: Option[BookStatus] =
        filters.get("status").flatMap(s => BookStatus.fromString(s.toLowerCase))

    val searchQuery: Option[String] =
        filters.get("search").map(_.trim).filter(_.nonEmpty)

    val sortParam: String = filters.getOrElse("sort", "title_asc")
    val (sortField, sortOrder) = sortParam.split("_") match {
        case Array(field, order) => (field.toLowerCase, order.toLowerCase)
        case Array(field)        => (field.toLowerCase, "asc")
        case _                   => ("title", "asc")
    }

    val filteredEntries = statusFilter match {
        case Some(status) => entries.filter(_.status == status)
        case None         => entries
    }

    val userRefIds = filteredEntries.map(_.refId).toSet
    val filteredBooks = books.filter(b => userRefIds.contains(b.isbn)).map(ensureCover).toList

    val searchFilteredEntries = searchQuery match {
        case Some(query) =>
        val lowerCaseQuery = query.toLowerCase
        filteredEntries.filter { entry =>
            filteredBooks.find(_.isbn == entry.refId).exists { book =>
            book.title.toLowerCase.contains(lowerCaseQuery) ||
            book.author.toLowerCase.contains(lowerCaseQuery)
            }
        }
        case None => filteredEntries
    }

    val finalFilteredBooks = filteredBooks.filter { book =>
        searchFilteredEntries.exists(_.refId == book.isbn)
    }

    val sortedBooks = (sortField, sortOrder) match {
        case ("title", "asc")   => finalFilteredBooks.sortBy(_.title)
        case ("title", "desc")  => finalFilteredBooks.sortBy(_.title)(Ordering[String].reverse)
        case ("author", "asc")  => finalFilteredBooks.sortBy(_.author)
        case ("author", "desc") => finalFilteredBooks.sortBy(_.author)(Ordering[String].reverse)
        case ("year", "asc")    => finalFilteredBooks.sortBy(_.publishYear)
        case ("year", "desc")   => finalFilteredBooks.sortBy(_.publishYear)(Ordering[Int].reverse)
        case _                  => finalFilteredBooks
    }

    val sortedEntries = searchFilteredEntries.sortBy(entry =>
        sortedBooks.indexWhere(_.isbn == entry.refId)
    )

    (sortedEntries, sortedBooks)
  }
}
