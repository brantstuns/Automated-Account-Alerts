import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.chrome.ChromeDriver;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MainService {
	
	private static BoaPage boaPage;
	private static ChasePage chasePage;
	private static AmexPage amexPage;

    public MainService() {
        boaPage = new BoaPage(new ChromeDriver());
        chasePage = new ChasePage(new ChromeDriver());
        amexPage = new AmexPage(new ChromeDriver());
    }

	public void getBalancesAndEmail(final List<String> credentialsList) {
        String boaTotal = boa(credentialsList.get(0), credentialsList.get(1));
        String chaseTotal = chase(credentialsList.get(0), credentialsList.get(2));
        String amexTotal = amex(credentialsList.get(0), credentialsList.get(1));
		emailCurrentBalance(credentialsList.get(3), credentialsList.get(4), boaTotal, chaseTotal, amexTotal);
    }

    private String boa(final String username, final String password) {
        loginBoa(username, password);
        return getBoaAmounts();
    }

    private String chase(final String username, final String password) {
        loginChase(username, password);
        return getChaseAmounts();
    }

    private String amex(final String username, final String password) {
        loginAmex(username, password);
        return getAmexAmounts();
    }

	private void loginBoa(final String userName, final String pass) {
		boaPage.signIn(userName, pass);
	}
	
	private void loginChase(final String u, final String p) {
		chasePage.signIn(u, p);
	}
	
	private void loginAmex(final String u, final String p) {
		amexPage.signIn(u, p);
	}
	
	private String getBoaAmounts() {
		final String balanceChecking = boaPage.loadAndGetChecking().getText();
        boaPage.endBrowserSession();
        return balanceChecking;
	}
	
	private String getChaseAmounts() {
		final String balanceChaseVisa = chasePage.getAccountBalance();
        chasePage.endBrowserSession();
        return balanceChaseVisa;
	}
	
	private String getAmexAmounts() {
		final String balanceAmex = amexPage.getAccountBalance();
		amexPage.endBrowserSession();
        return balanceAmex;
	}

	private String getCombinedCardTotal(final String total1, final String total2) {
        DecimalFormat df = new DecimalFormat("#.00");
        double cardBalance1 = Double.parseDouble(total1.substring(1));
        double cardBalance2 = Double.parseDouble(total2.substring(1));
        return "$" + df.format(cardBalance1 + cardBalance2);
    }
	
	private void emailCurrentBalance(final String u, final String p,
                                     final String boaTotal, final String chaseTotal, final String amexTotal) {
        System.out.println("yo");
        final String totalCardAmount = getCombinedCardTotal(chaseTotal, amexTotal);

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
			msg.setSubject("Current Account Balances - " + dateformat.format(date) + "  :  " + totalCardAmount);
			msg.setText("Your current BOA checking Act balance is:	" + boaTotal + "\n\n\n"
					+ "Your current Chase Visa card balance is:	    " + chaseTotal + "\n\n\n"
					+ "Your current American Express card balance is:    " + amexTotal + "\n\n\n");
			Transport.send(msg);
			System.out.println("Email sent. ;)");
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
	}
}
