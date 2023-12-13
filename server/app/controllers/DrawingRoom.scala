package controllers

import javax.inject._

import play.api.mvc._
import play.api.i18n._
import models.TaskListInMemoryModel
import play.api.libs.json._ 
import models._ 

@Singleton
class DrawingRoom @Inject() (cc: ControllerComponents) extends AbstractController(cc) {
    def load = Action { implicit request =>
        Ok(views.html.DrawingPage())
    }
}