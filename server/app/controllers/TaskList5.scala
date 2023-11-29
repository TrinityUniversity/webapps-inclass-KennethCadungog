package controllers

package controllers

import javax.inject._

import play.api.mvc._
import play.api.i18n._
import models.TaskListInMemoryModel
import play.api.libs.json._ 
import models._ 

import play.api.db.slick.DatabaseConfigProvider
import scala.concurrent.ExecutionContext
import play.api.db.slick.HasDatabaseConfigProvider
import slick.jdbc.JdbcProfile
import slick.jdbc.PostgresProfile.api._
import scala.concurrent.Future 

@Singleton
class TaskList5 @Inject()(protected val dbConfigProvider: DatabaseConfigProvider, cc: ControllerComponents)(implicit ec: ExecutionContext) 
      extends AbstractController(cc) with HasDatabaseConfigProvider[JdbcProfile] {
        
    private val model = new TaskListDatabaseModel(db)

    def load = Action { implicit request =>
        Ok(views.html.version5Main())
    }

    def withJsonBody[A](f: A => Future[Result])(implicit request: Request[AnyContent], reads: Reads[A]) = {
        request.body.asJson.map { body =>
            Json.fromJson[A](body) match {
                case JsSuccess(a, path) => f(a)
                case e @ JsError(_) => Redirect(routes.TaskList3.load)
            }    
        }.getOrElse(Redirect(routes.TaskList3.load))
    }

    def withSessionUsername(f: String => Result)(implicit request: Request[AnyContent]) = {
        request.session.get("username").map(f).getOrElse(Ok(Json.toJson(Seq.empty[String])))
    }

    def validate = Action.async { implicit request =>
        withJsonBody[UserData] { ud =>
            model.validateUser(ud.username, ud.password).map { userExists => 
                if (userExists) {
                    Ok(Json.toJson(true))
                        .withSession("username" -> ud.username, "csrfToken" -> play.filters.csrf.CSRF.getToken.get.value)
                } else {
                    Ok(Json.toJson(false))
                }
          }
        }
    }

    def createUser = Action { implicit request =>
        withJsonBody[UserData] { ud =>
            if (model.createUser(ud.username, ud.password)) {
                Ok(Json.toJson(true))
                  .withSession("username" -> ud.username, "csrfToken" -> play.filters.csrf.CSRF.getToken.get.value)
            } else {
                Ok(Json.toJson(false))
            }
        }
    }

    def taskList = Action {
        withSessionUsername { username =>
            Ok(Json.toJson(model.getTasks(username)))
        }
    }

    def addTask = Action { implicit request =>
        withSessionUsername { username =>
            withJsonBody[String] { task =>
                model.addTask(username, task);
                Ok(Json.toJson(true))
            }
        }    
    }

    def delete = Action { implicit request => 
        withSessionUsername { username =>
            withJsonBody[Int] { index =>
                model.removeTask(username, index)
                Ok(Json.toJson(true))    
            }
        }    
    }

    def logout = Action { implicit request =>
        Ok(Json.toJson(true)).withSession(request.session = "username")    
    }

}