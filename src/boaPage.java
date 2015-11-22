import org.apache.bcel.generic.BALOAD;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class boaPage implements Accounts {
	
	private FirefoxDriver driver;
	private WebElement user;		
	private WebElement pass;
	private WebElement signInButton;
	private WebElement SignInButtonPage2;	
	private WebElement questionAnswer;	
	private WebElement submitQuestion;
	private WebElement visaCard;	
	private WebElement currentBalance;	
	private WebElement checkingBalance;	
	private WebElement highschoolQuestion;
	
	public boaPage(FirefoxDriver driver) {
		this.driver = driver;
	}
	
	public void clickVisaLink() {
		visaCard.click();
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

	public void clickSignInButtonPage2() {
		SignInButtonPage2.click();
	}

	public WebElement getSignInButtonPage2() {
		return SignInButtonPage2;
	}
	
	public void signIn(String u, String p) {
		load(driver);
		WebElement user = getUserName();
		WebElement pass = getpass();
		
		user.sendKeys(u);
		pass.sendKeys(p);
		clickSignInButton();
	}
	
	public void load(FirefoxDriver driver) {
		driver.get("https://www.bankofamerica.com");
		user = driver.findElementById("onlineId1");
		pass = (new WebDriverWait(driver, 5)).until(ExpectedConditions
				.presenceOfElementLocated(By.id("passcode1")));
		signInButton = driver.findElementById("hp-sign-in-btn");
	}
	
	 public WebElement loadAndGetChecking(FirefoxDriver driver) {
		//visaCard = boaLogin.driver.findElementById("BankAmericard Cash Rewards Signature Visa - 3963");
		checkingBalance = (new WebDriverWait(driver, 5)).until(ExpectedConditions.
				presenceOfElementLocated(By.cssSelector(".balanceValue.TL_NPI_L1")));
		visaCard = (new WebDriverWait(driver, 5)).until(ExpectedConditions.
				presenceOfElementLocated(By.name("CCA_details")));
		return checkingBalance;
	}

	public void loginPage2(FirefoxDriver driver) {
		pass = (new WebDriverWait(driver, 5)).until(ExpectedConditions
				.presenceOfElementLocated(By.id("passcode1")));
		SignInButtonPage2 = (new WebDriverWait(driver, 5))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.name("enter-online-id-submit")));
	}
	
	public String getAccountBalance(FirefoxDriver driver) {
		currentBalance = driver.findElement(By.xpath("//div[@class='fl-rt bold TL_NPI_L1']"));
		return currentBalance.getText();
	}
}
