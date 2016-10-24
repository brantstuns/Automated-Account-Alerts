import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AccountAlertMain {

	public static void main(String[] args) {
		
		File file = new File("/Users/Brant/dev/chromedriver");
		System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
		
		List<String> credentials = null;
		if (args[0].length() < 1) {
			System.err.println("You didn't pass a file path");
		} else {
			credentials = getLoginFromFile(args[0]);
		}
		final MainService service = new MainService();
		service.getBalancesAndEmail(credentials);
	}
	
	public static List<String> getLoginFromFile(final String path) {
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
