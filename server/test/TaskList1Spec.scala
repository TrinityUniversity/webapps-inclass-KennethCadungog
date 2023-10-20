import org.scalatestplus.play.PlaySpec 
import org.scalatestplus.play.guice.GuiceOneServerPerSuite
import org.scalatestplus.play.OneBrowserPerSuite
import org.scalatestplus.play.HtmlUnitFactory

class TaskList1Spec extends PlaySpec with GuiceOneServerPerSuite with OneBrowserPerSuite with HtmlUnitFactory {
    // Error, might be related to React files. Probably should delete React
    "Task List 1" must {
        "login and access functions" in {
            go to s"http://localhost:$port/login1"
            pageTitle mustBe "Login"
            find(cssSelector("h2")).isEmpty mustBe false 
            find(cssSelector("h2")).foreach(e => e.text mustBe "Login")
            click on "username-login"
            textField("username-login").value = "web"
            click on "password-login"
            pwdField("password").value = "apps"
            submit()
            eventually {
                //Unsure if correct
                println("In eventually")
                println(currentUrl)
                pageTitle mustBe "Login"
                println("title passed")
                find(cssSelector("h1")).isEmpty mustBe false 
                println("h2 found")
                find(cssSelector("h1")).foreach(e => e.text mustBe "@username's Message Board")
            }
        }
    }
}