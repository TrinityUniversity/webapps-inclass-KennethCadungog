package controllers 

import javax.inject._

import play.api.mvc._
import play.api.i18n._
import play.api.data._ 
import play.api.data.Forms._

case class LoginData(username: String, password: String)

@Singleton
class TaskList1 @Inject()(cc: MessagesControllerComponents) extends MessagesAbstractController(cc) {
    val loginForm = Form(mapping(
        "Username" -> text(3, 10), 
        "Password" -> text(8)
        )(LoginData.apply)(LoginData.unapply))

    def login = Action { implicit request =>
        Ok(views.html.login1(loginForm))
    }

    def validateLoginGet(username: String, password: String) = Action {
        Ok(s"$username logged in with $password.")
    }

    def validateLoginPost = Action { implicit request => 
        val postVals = request.body.asFormUrlEncoded
        postVals.map { args =>
          val username = args("username").head 
          val password = args("password").head 
          if(models.TaskListInMemoryModel.validateUser(username,password)) {
            //Redirect(routes.TaskList1.taskList).withSession("username" -> username)
            Redirect(routes.TaskList1.msgList).withSession("username" -> username)
          } else {
            Redirect(routes.TaskList1.login).flashing("error" -> "Invalid username/password combination.")
          }
        }.getOrElse(Redirect(routes.TaskList1.login))
    }

    def validateLoginForm = Action { implicit request =>
      loginForm.bindFromRequest.fold(
          formWithError => BadRequest(views.html.login1(formWithError)),
          ld => 
            if(models.TaskListInMemoryModel.validateUser(ld.username,ld.password)) {
            //Redirect(routes.TaskList1.taskList).withSession("username" -> ld.username)
            Redirect(routes.TaskList1.msgList).withSession("username" -> ld.username)
            } else {
            Redirect(routes.TaskList1.login).flashing("error" -> "Invalid username/password combination.")
            }
      )
    }

    def createUser = Action { implicit request => 
        val postVals = request.body.asFormUrlEncoded
        postVals.map { args =>
          val username = args("username").head 
          val password = args("password").head 
          if(models.TaskListInMemoryModel.createUser(username,password)) {
            //Redirect(routes.TaskList1.taskList).withSession("username" -> username)
            Redirect(routes.TaskList1.msgList).withSession("username" -> username)
          } else {
            Redirect(routes.TaskList1.login).flashing("error" -> "User creation failed.")
          }
        }.getOrElse(Redirect(routes.TaskList1.login))
    }

    def taskList = Action { implicit request =>
        val usernameOption = request.session.get("username")
        usernameOption.map { username =>
            val tasks = models.TaskListInMemoryModel.getTask(username)
            Ok(views.html.taskList1(tasks))
        }.getOrElse(Redirect(routes.TaskList1.login))
    } 

    def logout = Action {
        Redirect(routes.TaskList1.login).withNewSession 
    }

    def addTask = Action { implicit request =>
      val usernameOption = request.session.get("username")
      usernameOption.map { username =>
        val postVals = request.body.asFormUrlEncoded
        postVals.map { args =>
          val task = args("newTask").head
          models.TaskListInMemoryModel.addTask(username, task);
          Redirect(routes.TaskList1.taskList)
        }.getOrElse(Redirect(routes.TaskList1.taskList))
      }.getOrElse(Redirect(routes.TaskList1.login)) 
    }

    def deleteTask = Action { implicit request =>
      val usernameOption = request.session.get("username")
      usernameOption.map { username =>
        val postVals = request.body.asFormUrlEncoded
        postVals.map { args =>
          val index = args("index").head.toInt
          models.TaskListInMemoryModel.removeTask(username, index);
          Redirect(routes.TaskList1.taskList)
        }.getOrElse(Redirect(routes.TaskList1.taskList))
      }.getOrElse(Redirect(routes.TaskList1.login)) 
    } 

    //Task 5 Code
    
    def msgList =  Action { implicit request => 
      val usernameOption = request.session.get("username")
        usernameOption.map { username =>
            val msgs = models.TaskListInMemoryModel.getPubMsg(username)
            val pvt_msgs = models.TaskListInMemoryModel.getPvtMsg(username)
            Ok(views.html.msgBoard1(username, msgs,pvt_msgs))
        }.getOrElse(Redirect(routes.TaskList1.login))  
    } 

    def add_pubMsg = Action { implicit request =>
      val usernameOption = request.session.get("username")
      usernameOption.map { username =>
        val postVals = request.body.asFormUrlEncoded
        postVals.map { args =>
          val message = args("newPubMsg").head
          models.TaskListInMemoryModel.addPubMsg(s"$username: $message");
          Redirect(routes.TaskList1.msgList)
        }.getOrElse(Redirect(routes.TaskList1.msgList))
      }.getOrElse(Redirect(routes.TaskList1.login)) 
    }

    def add_pvtMsg = Action { implicit request =>
      val usernameOption = request.session.get("username")
      usernameOption.map { username =>
        val postVals = request.body.asFormUrlEncoded
        postVals.map { args =>
          val user = args("user").head 
          val message = args("newPvtMsg").head
          models.TaskListInMemoryModel.addPvtMsg(user, s"$username: $message");
          Redirect(routes.TaskList1.msgList)
        }.getOrElse(Redirect(routes.TaskList1.msgList))
      }.getOrElse(Redirect(routes.TaskList1.login)) 
    }
    
}
