import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class mainService {
	
	private boaPage boa;
	private chasePage chase;
	private amexPage amex;
	private String balanceBoaVisa, balanceChecking, balanceChaseVisa, balanceAmex;
	
	public mainService(FirefoxDriver driver) {
		boa = new boaPage(driver);
		chase = new chasePage(driver);
		amex = new amexPage(driver);
	}
	
	void loginBoa(String userName, String pass) {
		boa.signIn(userName, pass);
	}
	
	void loginChase(String u, String p) {
		chase.signIn(u, p);
	}
	
	void loginAmex(String u, String p) {
		amex.signIn(u, p);
	}
	
	public void endSession(FirefoxDriver driver) {
		
		driver.close();
	}
	
	public void getBoaAmounts(FirefoxDriver driver) {
		balanceChecking = boa.loadAndGetChecking(driver).getText();
		boa.clickVisaLink();
		balanceBoaVisa = boa.getAccountBalance(driver);
	}
	
	public void getChaseAmounts(FirefoxDriver driver) {
		balanceChaseVisa = chase.getAccountBalance(driver);
	}
	
	public void getAmexAmounts(FirefoxDriver driver) {
		balanceAmex = amex.getAccountBalance(driver);
	}
	
	public void emailCurrentBalance(String email, String emailPass) {
		final String u = email, p = emailPass;
		Properties props = new Properties();
		DateFormat dateformat = new SimpleDateFormat("MMMM dd, yyyy");
		Date date = new Date();
		props.put("mail.smtp.auth", true);
		props.put("mail.smtp.starttls.enable", true);
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		Session session = Session.getInstance(props,		
		new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(u, p);
			}
		});
		try {
			MimeMessage msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(u));
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(u));
			msg.setSubject("Current Account Balances - " + dateformat.format(date));
			msg.setText("Your current Bank Of America Visa account balance is:      " + balanceBoaVisa + "\n\n\n" 
					+ "Your current Amazon Rewards Visa account balance is:			" + balanceChaseVisa + "\n\n\n"
					+ "Your current American Express balance is:			" + balanceAmex + "\n\n\n" 
					+ "Your current Bank of America checking account balance is:	" + balanceChecking + "\n\n\n");
			Transport.send(msg);
			System.out.println("Email sent. ;)");
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
	}
}
