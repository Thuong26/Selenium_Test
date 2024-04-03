package Testcases.Railway;
import Common.Constant.Constant;
import PageObject.Railway.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class LoginTest {

    @BeforeMethod
    public void beforeMethod(){
        System.out.println("Pre-condition");
        Constant.WEBDRIVER = new ChromeDriver();
        Constant.WEBDRIVER.manage().window().maximize();
    }

    @AfterMethod
    public void afterMethod(){
        System.out.println("Post-condition");
        Constant.WEBDRIVER.quit();
    }

    @Test
    public void TC01(){
        System.out.println("TC01 - User can log into Railway with valid username and password");
        HomePage homePage = new HomePage();
        homePage.open();

        LoginPage loginPage = homePage.gotoLoginPage();

        String actualMsg = loginPage.login(Constant.USERNAME, Constant.PASSWORD).getWelcomeMessage();
        String expectedMsg = "Welcome " + Constant.USERNAME;

        Assert.assertEquals(actualMsg, expectedMsg, "Welcome message is not displayed as expected");
    }

    @Test
    public void TC02() {
        System.out.println("TC02-User can't login with blank 'Username' textbox");
        HomePage homePage = new HomePage();
        homePage.open();
        LoginPage loginPage = homePage.gotoLoginPage();
        loginPage.login("", Constant.PASSWORD);
        String actualErrorMsg = loginPage.getLblLoginErrorMsg().getText();
        String expectedErrorMsg = "There was a problem with your login and/or errors exist in your form.";
        Assert.assertEquals(actualErrorMsg, expectedErrorMsg, "Error message is not displayed as expected");
    }

    @Test
    public void TC03() throws InterruptedException {
        System.out.println("TC03-User cannot log into Railway with invalid password");
        HomePage homePage = new HomePage();
        homePage.open();
        LoginPage loginPage = homePage.gotoLoginPage();
        loginPage.login(Constant.USERNAME, "invalidPassword");
        String actualErrorMsg = loginPage.getLblLoginErrorMsg().getText();
        String expectedErrorMsg = "There was a problem with your login and/or errors exist in your form.";
        Assert.assertEquals(actualErrorMsg, expectedErrorMsg, "Error message is not displayed as expected");
        Thread.sleep(10000);
    }

    @Test
    public void TC04() throws InterruptedException {
        System.out.println("TC04 - Login page displays when un-logged User clicks on 'Book ticket' tab");
        HomePage homePage = new HomePage();
        homePage.open();

        if (!homePage.isUserLoggedIn()) {
            homePage.gotoLoginPage();
        }
        homePage.gotoBookTicketPage();
        // Kiểm tra xem trang đăng nhập có hiển thị không
        boolean isLoginPageDisplayed = true;
        try {
            WebElement usernameTextbox = Constant.WEBDRIVER.findElement(By.id("username"));
            WebElement passwordTextbox = Constant.WEBDRIVER.findElement(By.id("password"));
            WebElement loginButton = Constant.WEBDRIVER.findElement(By.xpath("//input[@value='login']"));

            isLoginPageDisplayed = usernameTextbox.isDisplayed() && passwordTextbox.isDisplayed() && loginButton.isDisplayed();
        }
        catch (NoSuchElementException e) {
            // Nếu không tìm thấy các phần tử của trang đăng nhập, thì trang không hiển thị
            isLoginPageDisplayed = false;
        }
        // Kiểm tra xem trang đăng nhập có hiển thị sau khi nhấn vào tab "Book ticket" không
        Assert.assertFalse(isLoginPageDisplayed, "Login page is not displayed when un-logged User clicks on 'Book ticket' tab");
        Thread.sleep(5000); }

    @Test
    public void TC05() throws InterruptedException {
        System.out.println("TC05 - System shows message when user enters wrong password several times");
        HomePage homePage = new HomePage();
        homePage.open();
        LoginPage loginPage = homePage.gotoLoginPage();
        for (int i = 0; i < 4; i++) {
            loginPage.login(Constant.USERNAME, "invalidPassword");
        }
        loginPage.login(Constant.USERNAME, "invalidPassword");
        String actualErrorMsg = loginPage.getLblLoginErrorMsg().getText();
        String expectedErrorMsg = "You have used 4 out of 5 login attempts. After all 5 have been used, you will be unable to login for 15 minutes.";
        Assert.assertEquals(actualErrorMsg, expectedErrorMsg, "Error message is not displayed as expected");
        Thread.sleep(10000);
    }

    @Test
    public void TC06() throws InterruptedException {
        System.out.println("TC06 - Additional pages display once user logged in");
        HomePage homePage = new HomePage();
        homePage.open();
        LoginPage loginPage = homePage.gotoLoginPage();
        loginPage.getTxtUsername().sendKeys(Constant.USERNAME);
        loginPage.getTxtPassword().sendKeys(Constant.PASSWORD);
        loginPage.getBtnLogin().click();
        homePage = new HomePage();
        MyTicketPage myTicketPage = homePage.gotoMyTicketPage();
        boolean ticketPageIsDisplay = true;
        try {
            WebElement _lblManageTicket = Constant.WEBDRIVER.findElement(By.xpath("//*[@id='content']/h1"));
            ticketPageIsDisplay = _lblManageTicket.isDisplayed();
        } catch (NoSuchElementException e) {
            ticketPageIsDisplay = false;
        }
        Assert.assertTrue(ticketPageIsDisplay, "My ticket page is not displayed when user click My ticket tab");
        homePage = new HomePage();
        ChangePasswordPage changePasswordPage = homePage.gotoChangePasswordPage();
        boolean managePasswordIsDisplay = true;
        try {
            WebElement changePasswordButton = Constant.WEBDRIVER.findElement(By.xpath("//input[@value='Change Password']"));
            managePasswordIsDisplay = changePasswordButton.isDisplayed();
        } catch (NoSuchElementException e) {
            managePasswordIsDisplay = false;
        }
        Assert.assertTrue(managePasswordIsDisplay, "Change password page is not displayed when user click Change password tab");
        Thread.sleep(3000);
    }

    @Test
    public void TC07_UserCanCreateNewAccount() throws InterruptedException{
        System.out.println("TC07 - User can create a new account");
        HomePage homePage = new HomePage();
        homePage.open();
        RegisterPage registerPage = homePage.gotoRegisterPage();
        String email = "test@example.com";
        String password = "password123";
        String pid = "123456789";
        registerPage.register(email, password, password, pid);
        Mess mess = new Mess();
        String congraMsg = mess.getCongraMsg().getText();
        String expectedMessage = "Thank you for registering your account";
        Assert.assertTrue(congraMsg.contains(expectedMessage), "Registration Confirmed! You can now log in to the site.");
        Thread.sleep(10000);
    }

    @Test
    public void TC08() throws InterruptedException {
        System.out.println("TC08 - User can't login with an account hasn't been activated");
        HomePage homePage = new HomePage();
        homePage.open();
        LoginPage loginPage = homePage.gotoLoginPage();
        loginPage.getTxtUsername().sendKeys("invaliduser@gmail.com");
        loginPage.getTxtPassword().sendKeys("invalid password");
        loginPage.getBtnLogin().click();
        String actualErrorMsg = loginPage.getLblLoginErrorMsg().getText();
        String expectedErrorMsg = "Invalid username or password. Please try again.";
        Assert.assertEquals(actualErrorMsg, expectedErrorMsg, "Error message is not displayed as expected");
        Thread.sleep(10000);
    }

    @Test
    public void TC09() throws InterruptedException {
        System.out.println("TC09 - User can change password");
        HomePage homePage = new HomePage();
        homePage.open();
        LoginPage loginPage = homePage.gotoLoginPage();
        loginPage.getTxtUsername().sendKeys(Constant.USERNAME);
        loginPage.getTxtPassword().sendKeys(Constant.PASSWORD);
        loginPage.getBtnLogin().click();
        ChangePasswordPage changePasswordPage = homePage.gotoChangePasswordPage();
        changePasswordPage.getTxtCurrentPassword().sendKeys(Constant.PASSWORD);
        changePasswordPage.getTxtNewPassword().sendKeys("12345678");
        changePasswordPage.getTxtConfirmPassword().sendKeys("12345678");
        changePasswordPage.getBtnChangePassword().click();
        String actualSuccessMsg = changePasswordPage.getLblChangePasswordErrorMsg().getText();
        String expectedSuccessMsg = "Your password has been updated!";
        Assert.assertEquals(actualSuccessMsg, expectedSuccessMsg, "Success message is not displayed as expected");
        Thread.sleep(10000);
    }

    @Test
    public void TC10() throws InterruptedException {
        System.out.println("TC10 - User can't create account with Confirm password is not the same with Password");
        HomePage homePage = new HomePage();
        homePage.open();
        RegisterPage registerPage = homePage.gotoRegisterPage();
        registerPage.enterEmail().sendKeys("huyenthuong2606032gmail.com");
        registerPage.enterPassword().sendKeys("thuong123");
        registerPage.enterConfirmPassword().sendKeys("thuong2003");
        registerPage.enterPersonalID().sendKeys("123456789");
        registerPage.getbtnRegister().submit();
        String actualSuccessMsg = registerPage.getLblRegisterErrorMsg().getText();
        String expectedSuccessMsg = "There're errors in the form. Please correct the errors and try again.";
        Assert.assertEquals(actualSuccessMsg, expectedSuccessMsg, "Success message is not displayed as expected");
        Thread.sleep(10000);
    }


    @Test
    public void TC11() {
        System.out.println("TC11 - User can't create account while password and PID fields are empty");
        HomePage homePage = new HomePage();
        homePage.open();
        RegisterPage registerPage = homePage.gotoRegisterPage();
        registerPage.enterEmail().sendKeys("thuongabc@gmail.com");
        registerPage.getbtnRegister().submit();
        String expectedErrorMsg = "There're errors in the form. Please correct the errors and try again.";
        String expectedPasswordErrorMsg = "Invalid password length.";
        String expectedIdLengthErrorMsg = "Invalid ID length.";

        String actualErrorMsg = registerPage.getLblRegisterErrorMsg().getText();
        Assert.assertEquals(actualErrorMsg, expectedErrorMsg, "Error message is not displayed as expected");

        String actualPasswordLengthMsg = registerPage.getLblPasswordLengthErrorMsg().getText();
        Assert.assertEquals(actualPasswordLengthMsg, expectedPasswordErrorMsg, "Password length error message is not displayed as expected");

        String actualIdLengthMsg = registerPage.getLblIdLengthErrorMsg().getText();
        Assert.assertEquals(actualIdLengthMsg, expectedIdLengthErrorMsg, "ID length error message is not displayed as expected");
    }

    @Test
    public void TC12() {
        System.out.println("TC12 - Errors display when password reset token is blank");
        HomePage homePage = new HomePage();
        homePage.open();
        LoginPage loginPage = homePage.gotoLoginPage();
        ForgotPasswordPage forgotPasswordPage = loginPage.gotoForgotPasswordPage();
        forgotPasswordPage.getTxtEmailAddress().sendKeys("loveinamist86161@gmail.com");
        forgotPasswordPage.getBtnSendInstructors().submit();
        ChangePasswordPage changePasswordPage = homePage.gotoChangePasswordPage();
        boolean managePasswordIsDisplay = true;
        try {
            WebElement changePasswordButton = Constant.WEBDRIVER.findElement(By.xpath("//input[@value='Change Password']"));
            managePasswordIsDisplay = changePasswordButton.isDisplayed();
        } catch (NoSuchElementException e) {
            managePasswordIsDisplay = false;
        }
        Assert.assertTrue(managePasswordIsDisplay, "Could not reset password. Please correct the errors and try again.");
        changePasswordPage.getTxtCurrentPassword().sendKeys(Constant.PASSWORD);
        changePasswordPage.getTxtNewPassword().sendKeys("87654321");
        changePasswordPage.getTxtConfirmPassword().sendKeys("87654321");
        changePasswordPage.getBtnChangePassword().click();
        String actualSuccessMsg = changePasswordPage.getLblChangePasswordErrorMsg().getText();
        String expectedSuccessMsg = "Could not reset password. Please correct the errors and try again.";
        Assert.assertEquals(actualSuccessMsg, expectedSuccessMsg, "Error message is not displayed as expected");
    }

    @Test
    public void TC13(){
        System.out.println("TC13 - Errors display if password and confirm password don't match when resetting password");
        HomePage homePage = new HomePage();
        homePage.open();

        LoginPage loginPage = homePage.gotoLoginPage();
        ForgotPasswordPage forgotPasswordPage = homePage.gotoForgotPasswordPage();

        forgotPasswordPage.getTxtEmailAddress().sendKeys("loveinamist861612gmail.com");
        forgotPasswordPage.getBtnSendInstructors().click();

        ChangePasswordPage changePasswordPage = homePage.gotoChangePasswordPage();
        boolean managePasswordIsDisplay = true;
        try {
            WebElement changePasswordButton = Constant.WEBDRIVER.findElement(By.xpath("//input[@value='Change Password']"));
            managePasswordIsDisplay = changePasswordButton.isDisplayed();
        } catch (NoSuchElementException e) {
            managePasswordIsDisplay = false;
        }

        Assert.assertTrue(managePasswordIsDisplay, "Could not reset password. Please correct the errors and try again.");

        changePasswordPage.getTxtCurrentPassword().sendKeys("12345678");
        changePasswordPage.getTxtNewPassword().sendKeys("123456789");
        changePasswordPage.getTxtConfirmPassword().sendKeys("123456789");
        changePasswordPage.getBtnChangePassword().click();

        String actualSuccessMsg = changePasswordPage.getLblChangePasswordErrorMsg().getText();
        String expectedSuccessMsg = "Could not reset password. Please correct the errors and try again.";
        Assert.assertEquals(actualSuccessMsg, expectedSuccessMsg, "Error message is not displayed as expected");
    }
    @Test
    public void TC14() {
        System.out.println("TC14 - User can book 1 ticket at a time");
        HomePage homePage = new HomePage();
        homePage.open();
        LoginPage loginPage = homePage.gotoLoginPage();
        loginPage.login(Constant.USERNAME, Constant.PASSWORD);
        BookTicketPage bookTicketPage = homePage.gotoBookTicketPage();
        // Lấy ngày hiện tại và cộng thêm 4 ngày
        LocalDate currentDate = LocalDate.now().plusDays(4);
        String departDate = currentDate.format(DateTimeFormatter.ofPattern("M/d/yyyy"));
        // Chọn ngày đi
        bookTicketPage.selectDepartDate(departDate);
        bookTicketPage.selectDepartFrom("Sài Gòn");
        bookTicketPage.selectArriveStation("Nha Trang");
        bookTicketPage.selectSeatType("Soft bed with air conditioner");
        bookTicketPage.selectTicketAmount("1");
        bookTicketPage.clickBookTicketButton();
        // Kiểm tra thông báo thành công
        String expectedMsg = "Ticket booked successfully!";
        String actualMsg = bookTicketPage.getLblBookTicketSuccessMsg().getText();
        Assert.assertEquals(actualMsg, expectedMsg, "Success message doesn't match");
        // Kiểm tra thông tin đặt vé
        List<String> bookingInfo = bookTicketPage.getBookingInformation();
        String expectedDepartDate = currentDate.format(DateTimeFormatter.ofPattern("M/d/yyyy"));
        List<String> expectedInfo = Arrays.asList("Sài Gòn", "Nha Trang", "Soft bed with air conditioner", expectedDepartDate, "1");
        Assert.assertEquals(bookingInfo, expectedInfo, "Booking information doesn't match");
    }

    @Test
    public void TC15() throws InterruptedException {
        System.out.println("TC15 - User can open 'Book ticket' page by clicking on 'Book ticket' link in 'Train timetable' page");

        HomePage homePage = new HomePage();
        homePage.open();

        LoginPage loginPage = homePage.gotoLoginPage();
        loginPage.login(Constant.USERNAME, Constant.PASSWORD);
        TimeTablePage trainTimetablePage = homePage.gotoTrainTimetablePage();
        trainTimetablePage.SelectbookTicketClick();
        Thread.sleep(2000);
        Select DepartFrom= new Select(Constant.WEBDRIVER.findElement(By.xpath("//*[@id=\"content\"]/div[1]/form/fieldset/ol/li[2]/select")));
        Select ArriveStation=new Select(Constant.WEBDRIVER.findElement(By.xpath("//*[@id=\"ArriveStation\"]/select")));
        ArrayList<String> compare = new ArrayList<String>();
        compare.add(DepartFrom.getFirstSelectedOption().getText());
        compare.add(ArriveStation.getFirstSelectedOption().getText());
        List<String> expectedInfo = Arrays.asList("Sài Gòn", "Huế");
        Assert.assertEquals(compare, expectedInfo, "Booking information doesn't match");
    }

    @Test
    public void TC16()
    {
        System.out.println("TC16 -  User can cancel a ticket");
        HomePage homePage = new HomePage();
        homePage.open();
        LoginPage loginPage = homePage.gotoLoginPage();
        String username =Constant.USERNAME;
        String pass=Constant.PASSWORD;
        loginPage.login(username, pass);
        BookTicketPage bookTicketPage = homePage.gotoBookTicketPage();
        LocalDate currentDate = LocalDate.now().plusDays(4);
        String departDate = currentDate.format(DateTimeFormatter.ofPattern("d"));
        bookTicketPage.selectDepartDate(departDate);
        bookTicketPage.selectDepartFrom("Sài Gòn");
        bookTicketPage.selectArriveStation("Nha Trang");
        bookTicketPage.selectSeatType("Soft bed with air conditioner");
        bookTicketPage.selectTicketAmount("1");
        bookTicketPage.clickBookTicketButton();
        String url = Constant.WEBDRIVER.getCurrentUrl();
        String[] urls = url.split("id=");
        String delete= urls[1];
        String xp ="//input[@onclick='DeleteTicket("+delete+");']";
        MyTicketPage ticket = homePage.gotoMyTicketPage();
        JavascriptExecutor js = (JavascriptExecutor) Constant.WEBDRIVER;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
        ticket.clickCancelButton(xp);
        Constant.WEBDRIVER.switchTo().alert().accept();
        boolean isTicketDisplayed = true;
        try {
            WebElement CancelButtonElement = Constant.WEBDRIVER.findElement(By.xpath(xp));
            isTicketDisplayed = CancelButtonElement.isDisplayed();
        }
        catch (NoSuchElementException e) {
            isTicketDisplayed = false;
        }
        Assert.assertFalse(isTicketDisplayed, "The ticket does not disappear");
    }
}
