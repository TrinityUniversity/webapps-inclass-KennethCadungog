package controllers

import javax.inject._

import play.api.mvc._
import play.api.i18n._
import models.TaskListInMemoryModel
import play.api.libs.json.Json 
import models._
import play.api.libs.json.JsSuccess

@Singleton
class TaskList3 @Inject() (cc: ControllerComponents) extends AbstractController(cc) {
    def load = Action { implicit request => 
        Ok(views.html.version3.Main())   
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
                    Ok(views.html.login2())
                }
            case e @ JsError(_) => Redirect(routes.TaskList3.load())
          }
        }.getOrElse(Redirect(routes.TaskList3.load()))
    }

    def data = Action {
        Ok(Json.toJson(Seq("a", "b", "c")))
    }

}