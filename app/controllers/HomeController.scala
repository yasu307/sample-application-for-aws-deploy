package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import scala.util.Try

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

  val logger: Logger = Logger(this.getClass())

  def ping() = Action { implicit request: Request[AnyContent] =>
    Ok
  }

  def index() = Action { implicit request: Request[AnyContent] =>
    logger.info("Access to index")
    Ok(views.html.index())
  }

  def error() = Action { implicit request: Request[AnyContent] =>
    Try("hoge".toInt).recover(e => logger.error("parceInt error", e))
    Ok(views.html.index())
  }
}
