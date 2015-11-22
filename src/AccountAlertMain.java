import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.*;

public class AccountAlertMain {

	public static void main(String[] args) {
		List<String> l = null;
		if (args[0].length() < 1) {
			System.err.println("You didn't pass a file path");
		} else {
			l = getLoginFromFile(args[0]);
		}
		FirefoxDriver driver = new FirefoxDriver();
		mainService service = new mainService(driver);
		service.loginBoa(l.get(0), l.get(1));
		service.getBoaAmounts(driver);
		service.loginChase(l.get(0), l.get(2));
		service.getChaseAmounts(driver);
		service.loginAmex(l.get(0), l.get(1));
		service.getAmexAmounts(driver);
		service.emailCurrentBalance(l.get(3), l.get(2));
		service.endSession(driver);
	}
	
	public static List<String> getLoginFromFile(String path) {
		List<String> fileContents = new ArrayList<String>();
		BufferedReader in = null;
		
		try {
			String s;
			in = new BufferedReader(new FileReader(path));
			while ((s = in.readLine()) != null) {
				fileContents.add(s);
			}
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
		} finally {
			try {
				in.close();
			} catch (IOException closeIssue) {
				System.out.println(closeIssue.getMessage());
			}
		}
		return fileContents;
	}
}
