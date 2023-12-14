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

//case class LoginData(username: String, password: String)

@Singleton
class Task9 @Inject()(protected val dbConfigProvider: DatabaseConfigProvider, cc: ControllerComponents)
  (implicit ec: ExecutionContext) extends AbstractController(cc) with HasDatabaseConfigProvider[JdbcProfile] {

    def login = Action { implicit request =>
        Ok(views.html.login9())
    }

    private val models = new TaskListDatabaseModel(db)

    def validateLogin = Action.async { implicit request => 
        val postVals = request.body.asFormUrlEncoded
        postVals.map { args =>
          val username = args("username").head 
          val password = args("password").head 
          models.validateUser(username,password).map { userExists =>
            if(userExists) {
              Redirect(routes.Task9.msgList).withSession("username" -> username)
            } else {
              Redirect(routes.Task9.login).flashing("error" -> "Invalid username/password combination.")
            }
          }
        }.getOrElse(Future.successful(Redirect(routes.Task9.login)))
    }

    def createUser = Action.async { implicit request => 
        val postVals = request.body.asFormUrlEncoded
        postVals.map { args =>
          val username = args("username").head 
          val password = args("password").head
          models.createUser(username, password).map { userExists => 
            if(userExists) {
              Redirect(routes.Task9.msgList).withSession("username" -> username)
            } else {
              Redirect(routes.Task9.login).flashing("error" -> "User creation failed.")
            }
          }
        }.getOrElse(Future.successful(Redirect(routes.Task9.login)))
    }

    def logout = Action {
        Redirect(routes.Task9.login).withNewSession 
    }

    def msgList =  Action.async { implicit request => 
      val usernameOption = request.session.get("username").getOrElse("")
        models.getMessages(usernameOption).map { messages =>
          Ok(views.html.msgBoard9(messages,usernameOption))
        }
    } 

    def add_Msg = Action.async { implicit request =>
      val usernameOption = request.session.get("username").getOrElse("")

        val postVals = request.body.asFormUrlEncoded
        postVals.map { args =>
          val user = args("user").head 
          val message = args("message").head
          models.sendMessage(Some(message), Some(usernameOption), Some(user)).map { messageExits =>
            if(messageExits) {
              Redirect(routes.Task9.msgList)
            } else {
              Redirect(routes.Task9.login).flashing("error" -> "User creation failed.")
            }
          }
        }.getOrElse(Future.successful(Redirect(routes.Task9.login)))
      
    }

}
