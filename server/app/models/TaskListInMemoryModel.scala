package models

import collection.mutable

object TaskListInMemoryModel {
    private val users = mutable.Map[String, String]("web" -> "apps","mlewis" -> "prof")
    private val tasks = mutable.Map[String, List[String]]("Mark" -> List("Make vidoes", "eat", "sleep", "code"))

    def validateUser(username: String, password: String): Boolean = {
        users.get(username).map(_ == password).getOrElse(false)
    }

    def createUser(username: String, password: String): Boolean = {
        if (users.contains(username)) false else {
            users(username) = password
            true 
        }
    }

    def getTask(username: String): Seq[String] = {
        tasks.get(username).getOrElse(Nil)
    }

    def addTask(username: String, task: String): Unit = {
        tasks(username) = task :: tasks.get(username).getOrElse(Nil)
    }

    def removeTask(username: String, index: Int): Boolean = {
        if (index < 0 || tasks.get(username).isEmpty || index >= tasks(username).length) false
        else {
            tasks(username) = tasks(username).patch(index, Nil, 1)
            true 
        }
    }

    //Task 5 Code
    
    val pub_msgs = mutable.Buffer("mlewis: Scala anybody?")
    private val pvt_msgs = mutable.Map[String, List[String]]("web" -> List("mlewis: Long Live Scala!"))
    //private val pvt_msgs = mutable.Map[String, List[String]]()


    
    def getPubMsg(username: String): Seq[String] = {
        pub_msgs.toSeq
    }

    def getPvtMsg(username: String): Seq[String] = {
        pvt_msgs.get(username).getOrElse(Nil)
    }

    def addPubMsg(message: String): Unit = {
        pub_msgs += message 
    }

    def addPvtMsg(username: String, message: String): Unit = {
        pvt_msgs(username) = message :: pvt_msgs.get(username).getOrElse(Nil)
    }
    
}
