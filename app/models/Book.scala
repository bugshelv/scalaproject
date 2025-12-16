package models

case class Book(isbn: String, title: String,
                author: String, publish_year: Integer,
                pages: Integer, cover: String)
