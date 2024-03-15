package PageObject.Railway;

import Common.Constant.Constant;

public class HomePage extends  GeneralPage{
    //Locators
    //Elements
    //Methods
    public HomePage open(){
        Constant.WEBDRIVER.navigate().to(Constant.RAILWAY_URL);
        return this;
        //This is a method that starts ours test cases
    }
}