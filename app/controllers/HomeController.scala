package controllers

import play.api.mvc._

import javax.inject._

import repositories.BookRepository

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(
                                val controllerComponents: ControllerComponents,
                                bookRepository: BookRepository
                              ) extends BaseController {

  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index() = Action { implicit request =>
    val books = bookRepository.findAll()
    Ok(views.html.index(books)) // selectedBook = None by default
  }

  def showBook(isbn: String) = Action { implicit request =>
    val books = bookRepository.findAll()
    val selectedBook = books.find(_.isbn == isbn)
    Ok(views.html.index(books, selectedBook))
  }

  def addBook() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.addBook())
  }

}
