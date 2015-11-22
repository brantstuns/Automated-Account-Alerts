import java.text.DecimalFormat;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class amexPage implements Accounts {
	
	private FirefoxDriver driver;
	DecimalFormat df = new DecimalFormat("#.00");
	private WebElement user;
	private WebElement pass;
    private WebElement loginButton;

	public amexPage(FirefoxDriver driver) {
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
		driver.get("https://www.americanexpress.com/");
		user = driver.findElement(By.xpath("//label[@id='LabelUserID']"));
		pass = driver.findElement(By.xpath("//label[@id='LabelPassword']"));
		loginButton = driver.findElement(By.xpath("//a[@id='loginLink']"));
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
		WebElement totalBalance = driver.findElement(By.xpath("//div[@id='ah-outstanding-balance']"));
		return totalBalance.getText();
	}
}
