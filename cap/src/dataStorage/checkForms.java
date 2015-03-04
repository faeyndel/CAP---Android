package dataStorage;


public class checkForms {
	public static Boolean checkPasswords(String password1, String password2){
		if(password1.equals(password2) && password1.length() > 0 && password2.length() > 0 ){
			return false;
		}
		return true;
	}
	
	public static Boolean checkMail(String mail){
		if (mail.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+") && mail.length() > 0){
			return false;
		}
		return true;
	}
}
