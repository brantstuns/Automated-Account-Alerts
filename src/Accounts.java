import org.openqa.selenium.firefox.FirefoxDriver;

public interface Accounts {
	
	public void load(FirefoxDriver driver);
	
	public void signIn(String u, String p);
	
	public String getAccountBalance(FirefoxDriver driver);

}
