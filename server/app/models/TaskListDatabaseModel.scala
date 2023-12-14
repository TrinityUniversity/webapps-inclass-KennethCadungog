package models

import slick.jdbc.PostgresProfile.api._ 
import scala.concurrent.ExecutionContext
import models.Tables._ 
import scala.concurrent.Future 
import org.mindrot.jbcrypt.BCrypt
import shared.MessageItem

class TaskListDatabaseModel(db: Database)(implicit ec: ExecutionContext) {
    def validateUser(username: String, password: String): Future[Option[Int]] = {
        val matches = db.run(Users.filter(userRow => userRow.username === username).result)
        matches.map(userRows => userRows.headOption.flatMap {
            userRow => if (BCrypt.checkpw(password, userRow.password)) Some(userRow.id) else None
        })
    }

    def createUser(username: String, password: String): Future[Option[Int]] = {
        val matches = db.run(Users.filter(userRow => userRow.username === username).result)
        matches.flatMap { userRows =>
            if (userRows.isEmpty) {
            db.run(Users += UsersRow(-1, username, BCrypt.hashpw(password, BCrypt.gensalt())))
                .flatMap{ addCount => 
                    if (addCount > 0) db.run(Users.filter(userRow => userRow.username === username).result)
                        .map(_.headOption.map(_.id))
                    else Future.successful(None)
                }
            } else Future.successful(None)
        }
    }


    def getMessage(username: String): Future[Seq[MessageItem]] = {
        db.run(
            (for {
                user <- Users if user.username === username
                message <- Messages if messages.receiver === user.username || messages.username === "evryone"
            } yield {
                message
            }).result 
        ).map(messages => messages.map(message => MessageItem(message.sender, message.text)))
    }


    def addMessage(receiver: Int, message: String): Future[Int] = {
        db.run(Messages += MessagesRow(-1, Option(receiver), Option(message)))
    }

}
