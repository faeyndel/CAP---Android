package apiCap;

import java.io.IOException;
import java.io.ObjectInputStream.GetField;
import java.net.URI;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;
import common.Challenge;
import common.MyApp;
import common.User;

public class RequestTask extends AsyncTask<String, URI, String>{

	private final ProgressDialog dialog = new ProgressDialog(MyApp.getContext());
	
    @Override
    protected String doInBackground(String... uri) {

    	if(uri[0].equals("getUserID")){
    		MyApp.setAction("getUserID");
    		getUserID(uri);
    	}
    	if(uri[0].equals("adduser")){
    		MyApp.setAction("adduser");
    		addUser(uri);
    	}
    	if(uri[0].equals("login")){
    		MyApp.setAction("login");
    		login(uri);
    	}
    	if(uri[0].equals("cathegory")){
    		MyApp.setAction("cathegory");
    		try {
				APIcommunicator.getCathegory();
			} catch (IOException e) { e.printStackTrace(); }
    	}
    	if(uri[0].equals("challengeCathegory")){
    		MyApp.setAction("challengeCathegory");
    		try {
				APIcommunicator.getChallengeCathegory(uri[1]);
			} catch (IOException e) { e.printStackTrace(); }
    	}
    	if(uri[0].equals("userChallenge")){
    		MyApp.setAction("userChallenge");
    		try {
				APIcommunicator.getUserChallenge(uri[1]);
			} catch (IOException e) { e.printStackTrace(); }
    	}
    	if(uri[0].equals("addChallenge")){
    		MyApp.setAction("addChallenge");
    		createChallenge(uri);
    	}
    	if(uri[0].equals("lastUserChallenge")){
    		MyApp.setAction("lastUserChallenge");
    		try {
				APIcommunicator.lastUserChallenge();
			} catch (IOException e) { e.printStackTrace(); }
    	}
    	if(uri[0].equals("cathegoryChallenge")){
    		MyApp.setAction("cathegoryChallenge");
    		try {
				APIcommunicator.cathegoryChallenge(uri[1]);
			} catch (IOException e) { e.printStackTrace(); }
    	}
    	if(uri[0].equals("doChallenge")){
    		MyApp.setAction("doChallenge");
    		doChallenge(uri);
    	}
    	if(uri[0].equals("challenge")){
    		MyApp.setAction("challenge");
    		try {
				APIcommunicator.getChallenge(uri[1]);
			} catch (IOException e) { e.printStackTrace(); }
    	}
    	return null;
    }
    
    
    @Override
    protected void onPreExecute() {
        this.dialog.setMessage("Chargement...");
        this.dialog.setCancelable(false);
        this.dialog.show();
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        if (dialog.isShowing()) {
            dialog.dismiss();
        }
        MyApp.getAction();
        if(MyApp.getAction().equals("cathegory")){}
        if(MyApp.getAction().equals("challengeCathegory")){}
        if(MyApp.getAction().equals("addChallenge")){
        	Toast toast = Toast.makeText(MyApp.getContext(), MyApp.getGlobalMessage(), Toast.LENGTH_LONG);
            toast.show();
        }
        if(MyApp.getAction().equals("doChallenge")){
        	Toast toast = Toast.makeText(MyApp.getContext(), MyApp.getGlobalMessage(), Toast.LENGTH_LONG);
            toast.show();
        }
        if(MyApp.getAction().equals("adduser")){
        	Toast toast = Toast.makeText(MyApp.getContext(), MyApp.getGlobalMessage(), Toast.LENGTH_LONG);
            toast.show();
        }
        if(MyApp.getAction().equals("login")){
        	MyApp.setToken(MyApp.getGlobalMessage().toString());
        	/*if(MyApp.getGlobalMessage().toString().equals("false")){} else{
        		MyApp.setToken(MyApp.getGlobalMessage().toString());
        	}*/
 		}
        
    }
    
    protected void getUserID(String... uri){
    	try {
			APIcommunicator.getUserID(uri[1]);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    protected void addUser(String... uri){
    	User user = new User();
    	user.setUsername(uri[1]);
		user.setEmail(uri[2]);
		user.setPassword(uri[3]);
    	APIcommunicator.addUserPost(user);
    }
    
    protected void login(String... uri){
    	User user = new User();
    	user.setUsername(uri[1]);
		user.setPassword(uri[2]);
    	APIcommunicator.loginPost(user);
    }
    
    protected void doChallenge(String... uri){
    	Challenge challenge = new Challenge();
    	challenge.setFileName(uri[1]);
    	challenge.setFilePath(uri[2]);
    	challenge.setChallengeId(uri[3]);
    	challenge.setToken(MyApp.getToken());
    	APIcommunicator.sendDoChallenge(challenge);
    }
    
    protected void createChallenge(String... uri){
    	Challenge challenge = new Challenge();
    	challenge.setFileName(uri[1]);
    	challenge.setFilePath(uri[2]);
    	challenge.setChallengeCathegory(uri[3]);
    	challenge.setChallengeName(uri[4]);
    	challenge.setToken(MyApp.getToken());
    	APIcommunicator.sendCreateChallenge(challenge);
    }
    
}