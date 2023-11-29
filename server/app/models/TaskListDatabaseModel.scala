package models

import slick.jdbc.PostgresProfile.api._ 
import scala.concurrent.ExecutionContext
import models.Tables._ 
import scala.concurrent.Future 

class TaskListDatabaseModel(db: Database)(implicit ec: ExecutionContext) {
    def validateUser(username: String, password: String): Future[Boolean] = {
        val matches = db.run(Users.filter(userRow => userRow.username === username && userRow.password === password).result)
        matches.map(userRows => userRows.nonEmpty)
    }

    def createUser(username: String, password: String): Boolean = {
        ???
    }

    def getTasks(username: String): Seq[String] = {
        ???
    }

    def addTask(username: String, task: String): Unit = {
        ???
    }

    def removeTask(username: String, index: Int): Boolean = {
        ???
    }
}