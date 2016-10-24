import java.text.DecimalFormat;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ChasePage implements Accounts {
	
	private static ChromeDriver driver;
	DecimalFormat df = new DecimalFormat("#.00");
	private WebElement user;
	private WebElement pass;
    private WebElement loginButton;

	public ChasePage(final ChromeDriver driver) {
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

	public void load() {
		driver.get("https://chaseonline.chase.com/");
		user = driver.findElementById("UserID");
		pass = driver.findElementById("Password");
		loginButton = driver.findElementById("logon");
	}
	
	public void signIn(final String u, final String p) {
		load();
		WebElement user = getUserName();
		WebElement pass = getpass();
		WebElement signIn = getSignInButton();
		
		user.sendKeys(u);
		pass.sendKeys(p);
		signIn.click();
	}
	
	public String getAccountBalance() {
		String currentBalance, pendingBalance;

		WebElement cb = (new WebDriverWait(driver, 10)).until(ExpectedConditions.
				presenceOfElementLocated(By.className("TILEBNUMACTIVE")));
		WebElement pb = (new WebDriverWait(driver, 10)).until(ExpectedConditions.
				presenceOfElementLocated(By.className("pendingcharge-value")));
				
		currentBalance = cb.getText();
		pendingBalance = pb.getText();
		
		double curBal = Double.parseDouble(currentBalance.substring(1));
		double penBal = Double.parseDouble(pendingBalance.substring(1));
		
		return "$" + df.format(curBal + penBal);
	}

    public void endBrowserSession() {
        driver.close();
    }
}
