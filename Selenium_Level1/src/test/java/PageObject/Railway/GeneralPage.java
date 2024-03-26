package PageObject.Railway;

import Common.Constant.Constant;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class GeneralPage {
    //Locators
    public final By tabLogin = By.xpath("//div[@id='menu']//a[@href='/Account/Login.cshtml']");
    public final By tabLogout = By.xpath("//div[@id='menu']//a[@href='/Account/Logout']");
    public final By lblWelcomeMessage = By.xpath("//div[@class='account']/strong");

    //TC04
    public final By tabBookTicket = By.xpath("//div[@id='menu']//a[@href='/Page/BookTicketPage.cshtml']");
    //TC06
    public final By tabMyTicket = By.xpath("//div[@id='menu']//a[@href='/Page/ManageTicket.cshtml']");
    //TC06
    public final By tabChangePassword = By.xpath("//div[@id='menu']//a[@href='/Account/ChangePassword.cshtml']");
    //TC07
    public final By tabRegister = By.xpath("//div[@id='menu']//a[@href='/Account/Register.cshtml']");

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
    //TC04
    protected WebElement getTabBookTicket(){
        return Constant.WEBDRIVER.findElement(tabBookTicket);
    }
    public BookTicketPage gotoBookTicketPage(){
        this.getTabBookTicket().click();
        return new BookTicketPage();
    }
    //TC06
    protected WebElement getTabMyTicket(){
        return Constant.WEBDRIVER.findElement(tabMyTicket);
    }
    public MyTicketPage gotoMyTicketPage(){
        this.getTabMyTicket().click();
        return new MyTicketPage();
    }

    protected WebElement getTabChangePassword(){
        return Constant.WEBDRIVER.findElement(tabChangePassword);
    }
    public ChangePasswordPage gotoChangePasswordPage(){
        this.getTabChangePassword().click();
        return new ChangePasswordPage();
    }
    //TC07
    protected WebElement getTabRegister(){
        return Constant.WEBDRIVER.findElement(tabRegister);
    }
    public RegisterPage gotoRegisterPage(){
        this.getTabRegister().click();
        return new RegisterPage();
    }
}