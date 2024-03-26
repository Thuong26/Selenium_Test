package PageObject.Railway;
import Common.Constant.Constant;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class MyTicketPage extends GeneralPage {
    private final By _lblManageTicket = By.xpath("//*[@id=\"content\"]/h1[text()='Manage ticket']");

    public WebElement getLblManageTicket(){
        return Constant.WEBDRIVER.findElement(_lblManageTicket);
    }
}