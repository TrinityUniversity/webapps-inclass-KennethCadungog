import org.scalatestplus.play._ 
import models._

class TaskListInMemoryModelSpec extends PlaySpec {
    "TaskListInMemoryModel" must {
        "do valid login for default user" in {
            TaskListInMemoryModel.validateUser("web", "apps") mustBe (true)
        }
    

        "reject login with wrong password" in {
            TaskListInMemoryModel.validateUser("web", "scala") mustBe (false)
        }

        "reject login with wrong username" in {
            TaskListInMemoryModel.validateUser("scala", "apps") mustBe (false)
        }

        "reject login with wrong username and password" in {
            TaskListInMemoryModel.validateUser("scala", "rules") mustBe (false)
        }

        "get correct public message" in {
            TaskListInMemoryModel.getPubMsg("web") mustBe (List("mlewis: Scala anybody?"))
        }

        "get correct private message" in { 
            TaskListInMemoryModel.getPvtMsg("web") mustBe (List("mlewis: Long Live Scala!"))
        }

        "create new user with no tasks" in {
            TaskListInMemoryModel.createUser("Kenneth","password") mustBe (true)
            TaskListInMemoryModel.getPvtMsg("Kenneth") mustBe (Nil)
        }

        "create new user with existing name" in {
            TaskListInMemoryModel.createUser("web","apps") mustBe (false)
        }

        "add new public message for default user" in {
            TaskListInMemoryModel.addPubMsg("web: Hi web users!")
            TaskListInMemoryModel.getPubMsg("web") must contain ("web: Hi web users!")
        }

        "add new public message for new user" in {
            TaskListInMemoryModel.addPubMsg("web: Hi web users!")
            TaskListInMemoryModel.getPubMsg("Kenneth") must contain ("web: Hi web users!")
        }

        "add new private message for default user" in {
            TaskListInMemoryModel.addPvtMsg("web", "Hi web!")
            TaskListInMemoryModel.getPvtMsg("web") must contain ("Hi web!")
        }

        "add new private message for new user" in {
            TaskListInMemoryModel.addPvtMsg("Kenneth", "Hi Kenneth")
            TaskListInMemoryModel.getPvtMsg("Kenneth") must contain ("Hi Kenneth")
        }


    }
}
