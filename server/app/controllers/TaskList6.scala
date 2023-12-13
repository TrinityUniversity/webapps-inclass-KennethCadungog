package controllers

import javax.inject._

import play.api.mvc._
import play.api.i18n._
import models.TaskListInMemoryModel
import play.api.libs.json._
import models._
import play.twirl.api.Html

@Singleton
class TaskList6 @Inject() (cc: ControllerComponents) extends AbstractController(cc) {
  def load = Action { implicit request =>
    val title = "HI"
    val content = Html("Welcome")
    Ok(views.html.main(title)(content))
  }
}