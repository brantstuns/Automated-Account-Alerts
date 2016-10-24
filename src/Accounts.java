public interface Accounts {
	
	public void load();
	
	public void signIn(String u, String p);
	
	public String getAccountBalance();

	public void endBrowserSession();

}
