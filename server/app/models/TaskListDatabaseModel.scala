package models

import slick.jdbc.PostgresProfile.api._ 
import scala.concurrent.ExecutionContext
import models.Tables._ 
import scala.concurrent.Future 
import org.mindrot.jbcrypt.BCrypt
import models._

class TaskListDatabaseModel(db: Database)(implicit ec: ExecutionContext) {

    def createUser(username: String, password: String): Future[Boolean] = {
        db.run(Users += UsersRow(-1, username, password)).map(addCount => addCount > 0)
    }

    def sendMessage(message: Option[String], username: Option[String], receiver: Option[String]): Future[Boolean] = {
        db.run(Messages += MessagesRow(message, username, receiver)).map(addCount => addCount > 0)
    }

    def validateUser(username: String, password: String): Future[Boolean] = {
        val matches = db.run(Users.filter(UsersRow => UsersRow.username === username && UsersRow.password === password).result)
        matches.map(UserRows => UserRows.nonEmpty)
    }

    def getMessages(username: String): Future[Seq[MessageItem]] = {
        db.run(
            (for {
                    user <- Users if user.username === username
                    message <- Messages if message.receiver === username || message.receiver === "everyone"
                 } yield {
                    message
            }).result
        ).map(messages => messages.map(messages => MessageItem(messages.text, messages.sender)))
    }

}
