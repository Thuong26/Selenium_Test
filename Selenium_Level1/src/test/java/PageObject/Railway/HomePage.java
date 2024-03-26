package PageObject.Railway;

import Common.Constant.Constant;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

public class HomePage extends  GeneralPage{
    //Locators
    //Elements
    //Methods
    public HomePage open(){
        Constant.WEBDRIVER.navigate().to(Constant.RAILWAY_URL);
        return this;
        //This is a method that starts ours test cases
    }


    //Phương thức kiểm tra xem người dùng đã đăng nhập hay chưa
    public boolean isUserLoggedIn() {
        try {
            WebElement logoutTab = Constant.WEBDRIVER.findElement(tabLogout);
            return logoutTab.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }
}