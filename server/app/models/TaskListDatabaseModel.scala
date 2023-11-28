package models

import slick.jdbc.PostgresProfile.api._ 
import scala.concurrent.ExecutionContext

class TaskListDatabaseModel(db: Database)(implicit ec: ExecutionContext) {
    def validateUser(username: String, password: String): Boolean = {
        ???
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