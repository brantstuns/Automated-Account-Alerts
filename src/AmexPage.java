import java.text.DecimalFormat;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class AmexPage implements Accounts {
	
	private static ChromeDriver driver;
	private final DecimalFormat df = new DecimalFormat("#.00");
	private WebElement user;
	private WebElement pass;
    private WebElement loginButton;

	public AmexPage(final ChromeDriver driver) {
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
		driver.get("https://online.americanexpress.com/myca/logon/us/action/LogonHandler?request_type=LogonHandler&Face=en_US");
		user = driver.findElement(By.id("lilo_userName"));
		pass = driver.findElement(By.id("lilo_password"));
		loginButton = driver.findElement(By.id("lilo_formSubmit"));
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
		WebElement totalBalance = driver.findElement(By.id("ah-outstanding-balance-value"));
		return totalBalance.getText();
	}

	public void endBrowserSession() {
		driver.close();
	}
}
