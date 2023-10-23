import org.scalatestplus.play.PlaySpec 
import org.scalatestplus.play.guice.GuiceOneServerPerSuite
import org.scalatestplus.play.OneBrowserPerSuite
import org.scalatestplus.play.HtmlUnitFactory

class TaskList1Spec extends PlaySpec with GuiceOneServerPerSuite with OneBrowserPerSuite with HtmlUnitFactory {
    "Task List 1" must {
        "login and access functions" in {
            go to s"http://localhost:$port/login1"
            pageTitle mustBe "Login"
            find(cssSelector("h2")).isEmpty mustBe false 
            find(cssSelector("h2")).foreach(e => e.text mustBe "Login")
            click on "username-login"
            val username = "web"
            textField("username-login").value = username 
            click on "password-login"
            pwdField(id("password-login")).value = "apps"
            submit()
            eventually {
                //Unsure if correct
                println("In eventually")
                println(currentUrl)
                pageTitle mustBe "Message Board"
                println("title passed")
                find(cssSelector("h1")).isEmpty mustBe false 
                println("h1 found")
                find(cssSelector("h1")).foreach(e => e.text mustBe s"$username's Message Board")
                val expectedMessage = "mlewis: Scala anybody?"
                findAll(cssSelector("li")).map(_.text) mustBe s"$expectedMessage"
            }
        }
    }
}