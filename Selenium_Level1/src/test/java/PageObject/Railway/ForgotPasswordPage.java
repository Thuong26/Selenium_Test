package PageObject.Railway;

import Common.Constant.Constant;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ForgotPasswordPage extends GeneralPage{
    private final By _txtEmailAddress = By.xpath("//input[@id='email']");
    private final By _btnSendInstructors = By.xpath("//input[@value='Send Instructions']");
    private final By _lblForgotPasswordErrorMsg = By.xpath("//*[@id=\"content\"]/form/fieldset/p[2]");

    //Elements
    public WebElement getTxtEmailAddress(){
        return Constant.WEBDRIVER.findElement(_txtEmailAddress);
    }
    public WebElement getBtnSendInstructors(){
        return Constant.WEBDRIVER.findElement(_btnSendInstructors);
    }
    public WebElement getLblForgotPasswordErrorMsg(){
        return Constant.WEBDRIVER.findElement(_lblForgotPasswordErrorMsg);
    }

}
