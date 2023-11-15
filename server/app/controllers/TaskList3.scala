package controllers

import javax.inject._

import play.api.mvc._
import play.api.i18n._
import models.TaskListInMemoryModel
import play.api.libs.json.Json 
import models._
import play.api.libs.json.JsSuccess
import javafx.concurrent.Task
import scala.annotation.unspecialized

@Singleton
class TaskList3 @Inject() (cc: ControllerComponents) extends AbstractController(cc) {
    def load = Action { implicit request => 
        Ok(views.html.version3Main())   
    }

    implicit val userDataReads = Json.reads[UserData]

    def validate = Action { implicit request =>
        request.body.asJson.map { body => 
          Json.fromJson[UserData](body) match {
            case JsSuccess(ud, path) => 
                ud.username
                if (TaskListInMemoryModel.validateUser(ud.username, ud.password)) {
                    Ok(Json.toJson(true))
                        .withSession("username" -> ud.username, "csrfToken" -> play.filters.csrf.CSRF.getToken.get.value)
                } else {
                    Ok(Json.toJson(false))
                }
            case e @ JsError(_) => Redirect(routes.TaskList3.load())
          }
        }.getOrElse(Redirect(routes.TaskList3.load()))
    }

    def taskList = Action { implicit request =>
        val usernameOption = request.session.get("username")
        usernameOption.map { username => 
            Ok(Json.toJson(TaskListInMemoryModel.getTask(username)))    
        }.getOrElse(Ok(Json.toJson(Seq.empty[String])))
    }

    def addTask = Action { implicit request =>
        val usernameOption = request.session.get("username")
        usernameOption.map { username => 
            request.body.asJson.map { body => 
            Json.fromJson[String](body) match {
                case JsSuccess(task, path) => 
                    TaskListInMemoryModel.addTask(username, task);
                    Ok(Json.toJson(true))
                case e @ JsError(_) => Redirect(routes.TaskList3.load())
                } 
            }.getOrElse(Ok(Json.toJson(true)))
        }.getOrElse(Ok(Json.toJson(false)))
    }

}