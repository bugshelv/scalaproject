package repositories

import models.Book

import javax.inject._

@Singleton
class BookRepository {

  // Fake books for now
  private var books = List(
    Book("9787201106380", "1984", "George Orwell", 2018, 248, "https://ia800100.us.archive.org/view_archive.php?archive=/5/items/l_covers_0012/l_covers_0012_72.zip&file=0012725444-L.jpg"),
    Book("9788393535873", "Pożegnanie Słońca", "Maciej Gorywoda", 2024, 480, "https://s.lubimyczytac.pl/upload/books/5112000/5112035/1145332-352x500.jpg")
  )

  def findAll(): List[Book] =
    books

  def add(book: Book): Unit =
    books = books :+ book
}