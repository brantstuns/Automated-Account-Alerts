import java.text.DecimalFormat;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class chasePage implements Accounts {
	
	private FirefoxDriver driver;
	DecimalFormat df = new DecimalFormat("#.00");
	private WebElement user;
	private WebElement pass;
    private WebElement loginButton;

	public chasePage(FirefoxDriver driver) {
		this.driver = driver;
	}
	
	public WebElement getUserName() {
		return user;
	}

	public WebElement getpass() {
		return pass;
	}
	
	public WebElement getSignInButton() {
		return loginButton;
	}

	public void load(FirefoxDriver driver) {
		// driver.get("https://www.chase.com/");
		// user = driver.findElementById("usr_name_home");
		// pass = driver.findElementById("usr_password_home");
		// loginButton = driver.findElementByClassName("signin--button");

		driver.get("https://chaseonline.chase.com/");
		user = driver.findElementById("UserID");
		pass = driver.findElementById("Password");
		loginButton = driver.findElementById("logon");
	}
	
	public void signIn(String u, String p) {
		load(driver);
		WebElement user = getUserName();
		WebElement pass = getpass();
		WebElement signIn = getSignInButton();
		
		user.sendKeys(u);
		pass.sendKeys(p);
		signIn.click();
	}
	
	public String getAccountBalance(FirefoxDriver driver) {
		String currentBalance, pendingBalance;

		WebElement seeActivityLink = (new WebDriverWait(driver, 10)).until(ExpectedConditions.
				presenceOfElementLocated(By.xpath("//a[contains(@href,'https://cards.chase.com/cc/Account/Activity/536208348')]")));
		seeActivityLink.click();
		WebElement cb = (new WebDriverWait(driver, 5)).until(ExpectedConditions.
				presenceOfElementLocated(By.xpath(".//*[@id='AccountDetailTable']/tbody/tr/td[1]/table[1]/tbody/tr[1]/td[2]")));
		WebElement pb = (new WebDriverWait(driver, 5)).until(ExpectedConditions.
				presenceOfElementLocated(By.xpath(".//*[@id='AccountDetailTable']/tbody/tr/td[1]/table[1]/tbody/tr[2]/td[2]")));
				
		currentBalance = cb.getText();
		pendingBalance = pb.getText();
		
		double curBal = Double.parseDouble(currentBalance.substring(1, currentBalance.length() - 1));
		double penBal = Double.parseDouble(pendingBalance.substring(1, pendingBalance.length() - 1));
		
		return "$" + df.format(curBal + penBal);
	}
}
