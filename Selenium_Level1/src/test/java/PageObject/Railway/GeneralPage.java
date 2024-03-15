package PageObject.Railway;

import Common.Constant.Constant;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class GeneralPage {
    //Locators
    public final By tabLogin = By.xpath("//div[@id='menu']//a[@href='/Account/Login.cshtml']");
    public final By tabLogout = By.xpath("//div[@id='menu']//a[@href='/Account/Logout']");
    public final By lblWelcomeMessage = By.xpath("//div[@class='account']/strong");

    //Elements: getter method for retrieving WebElements
    protected WebElement getTabLogin(){

        return Constant.WEBDRIVER.findElement(tabLogin);
    }
    protected WebElement getTabLogout(){
        return Constant.WEBDRIVER.findElement(tabLogout);
    }
    protected WebElement getLblWelcomeMessage(){
        return Constant.WEBDRIVER.findElement(lblWelcomeMessage);
    }

    //Methods: page's methods
    public String getWelcomeMessage(){
        return this.getLblWelcomeMessage().getText();
    }
    public LoginPage gotoLoginPage(){
        this.getTabLogin().click();
        return new LoginPage();
    }
}