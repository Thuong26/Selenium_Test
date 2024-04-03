package PageObject.Railway;
import Common.Constant.Constant;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import java.util.List;

public class TimeTablePage{
    public final By allRows = By.xpath("//table[@class='MyTable WideTable']//tr[@class='OddRow' or @class='EvenRow']");
    private final By bookTicketClick = By.xpath("//*[@id=\"content\"]/div/div/table/tbody/tr[4]/td[7]/a");

    public void SelectbookTicketClick() {
        JavascriptExecutor js = (JavascriptExecutor) Constant.WEBDRIVER;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
        WebElement bookTicketButtonElement = Constant.WEBDRIVER.findElement(bookTicketClick);
        bookTicketButtonElement.click();
    }
    public  List<WebElement> getAllRows() {
        return Constant.WEBDRIVER.findElements(allRows);
    }
}