import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BoaPage implements Accounts {
	
	private static ChromeDriver driver;
	private WebElement user;		
	private WebElement pass;
	private WebElement signInButton;
	private WebElement visaCard;	
	private WebElement currentBalance;	
	private WebElement checkingBalance;
	
	public BoaPage(final ChromeDriver driver) {
		this.driver = driver;
	}

	public WebElement getUserName() {
		return user;
	}

	public WebElement getpass() {
		return pass;
	}

	public void clickSignInButton() {
		signInButton.click();
	}

	public void signIn(final String u, final String p) {
		load();
		WebElement user = getUserName();
		WebElement pass = getpass();
		
		user.sendKeys(u);
		pass.sendKeys(p);
		clickSignInButton();
	}
	
	public void load() {
		driver.get("https://www.bankofamerica.com");
		user = driver.findElementById("onlineId1");
		pass = (new WebDriverWait(driver, 5)).until(ExpectedConditions
				.presenceOfElementLocated(By.id("passcode1")));
		signInButton = driver.findElementById("hp-sign-in-btn");
	}
	
	 public WebElement loadAndGetChecking() {
		checkingBalance = (new WebDriverWait(driver, 5)).until(ExpectedConditions.
				presenceOfElementLocated(By.cssSelector(".balanceValue.TL_NPI_L1")));
		visaCard = (new WebDriverWait(driver, 5)).until(ExpectedConditions.
				presenceOfElementLocated(By.name("CCA_details")));
		return checkingBalance;
	}
	
	public String getAccountBalance() {
		currentBalance = driver.findElement(By.xpath("//div[@class='fl-rt bold TL_NPI_L1']"));
		return currentBalance.getText();
	}

	public void endBrowserSession() {
		driver.close();
	}
}
