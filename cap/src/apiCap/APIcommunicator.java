package apiCap;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import android.os.AsyncTask;
import android.util.Log;
import common.Challenge;
import common.MyApp;
import common.User;

public abstract class APIcommunicator extends AsyncTask<String, String, String> {
	
	private static final String urlAddUser = "http://delmasromain.com:4242/user";
	private static final String urlLogin = "http://delmasromain.com:4242/login";
	private static final String urlUpdateUser = "http://delmasromain.com:4242/user/";
	private static final String urlCathegory = "http://delmasromain.com:4242/tag";
	private static final String urlAddChallenge = "http://delmasromain.com:4242/challenge";
	private static final String urlDoChallenge = "http://delmasromain.com:4242/userchallenge";
	private static final String urlVoteChallenge = "http://delmasromain.com:4242/vote";
	private static final String urlChallengeCat = "http://delmasromain.com:4242/tag/";
	private static final String urlChallenge = "http://delmasromain.com:4242/challenge/";
	private static final String urlUserChallenge = "http://delmasromain.com:4242/user/";
	private static final String urlGetUserID = "http://delmasromain.com:4242/user/0/";
	private static final String urlLastChallenges = "http://delmasromain.com:4242/userchallenge/last/";

	public static void cathegoryChallenge(String idtag) throws IOException{
		
		String uri = urlChallengeCat+idtag+"/"+MyApp.getToken()+"/userchallenge";
		Log.v("__uri__", uri);
		URL url = new URL(uri);
		URLConnection urlConnection = url.openConnection();
		InputStream in = new BufferedInputStream(urlConnection.getInputStream());
		try {
			BufferedReader r = new BufferedReader(new InputStreamReader(in));
			StringBuilder total = new StringBuilder();
			String line;
			while ((line = r.readLine()) != null) {
			    total.append(line);
			}
			MyApp.setCathegoryList(total.toString());
		}finally {
			in.close();
		}
	}
	
	public static void getUserID(String id) throws IOException{
		String uri = urlGetUserID+MyApp.getToken()+"/getid";
		URL url = new URL(uri);
		URLConnection urlConnection = url.openConnection();
		InputStream in = new BufferedInputStream(urlConnection.getInputStream());
		try {
			BufferedReader r = new BufferedReader(new InputStreamReader(in));
			StringBuilder total = new StringBuilder();
			String line;
			while ((line = r.readLine()) != null) {
			    total.append(line);
			}
			MyApp.setCurUserId(total.toString());
		}finally {
			in.close();
		}
	}
	
	public static void lastUserChallenge() throws IOException{
		
		String uri = urlLastChallenges+MyApp.getToken();
		URL url = new URL(uri);
		URLConnection urlConnection = url.openConnection();
		InputStream in = new BufferedInputStream(urlConnection.getInputStream());
		try {
			BufferedReader r = new BufferedReader(new InputStreamReader(in));
			StringBuilder total = new StringBuilder();
			String line;
			while ((line = r.readLine()) != null) {
			    total.append(line);
			}
			MyApp.setCathegoryList(total.toString());
		}finally {
			in.close();
		}
	}
	
	public static void getUserChallenge(String id) throws IOException{
		
		String uri = urlUserChallenge+id+"/"+MyApp.getToken();
		URL url = new URL(uri);
		URLConnection urlConnection = url.openConnection();
		InputStream in = new BufferedInputStream(urlConnection.getInputStream());
		try {
			BufferedReader r = new BufferedReader(new InputStreamReader(in));
			StringBuilder total = new StringBuilder();
			String line;
			while ((line = r.readLine()) != null) {
			    total.append(line);
			}
			MyApp.setCathegoryList(total.toString());
		}finally {
			in.close();
		}
	}
	
	public static void getChallenge(String id) throws IOException{
		
		String uri = urlChallenge+id+"/"+MyApp.getToken();
		URL url = new URL(uri);
		URLConnection urlConnection = url.openConnection();
		InputStream in = new BufferedInputStream(urlConnection.getInputStream());
		try {
			BufferedReader r = new BufferedReader(new InputStreamReader(in));
			StringBuilder total = new StringBuilder();
			String line;
			while ((line = r.readLine()) != null) {
			    total.append(line);
			}
			MyApp.setCathegoryList(total.toString());
		}finally {
			in.close();
		}
	}
	
	public static void getChallengeCathegory(String id) throws IOException{
		
		String uri = urlChallengeCat+id+"/"+MyApp.getToken();
		URL url = new URL(uri);
		URLConnection urlConnection = url.openConnection();
		InputStream in = new BufferedInputStream(urlConnection.getInputStream());
		try {
			BufferedReader r = new BufferedReader(new InputStreamReader(in));
			StringBuilder total = new StringBuilder();
			String line;
			while ((line = r.readLine()) != null) {
			    total.append(line);
			}
			MyApp.setCathegoryList(total.toString());
		}finally {
			in.close();
		}
	}
	
	public static void sendDoChallenge(Challenge challenge){
		HttpClient httpclient = new DefaultHttpClient();
		HttpContext localContext = new BasicHttpContext();
	    HttpPost httppost = new HttpPost(urlDoChallenge);

	    String image = challenge.getFilePath()+challenge.getFileName();
	    File file = new File(image);

	    try {
	    	 MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
	    	 ContentBody cbFile = new FileBody(file, "image/jpg");

	    	 List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
	    	 nameValuePairs.add(new BasicNameValuePair("challenge", challenge.getChallengeId()));
		     nameValuePairs.add(new BasicNameValuePair("token", challenge.getToken()));

		     entity.addPart("media", cbFile);
		     
	    	 for(int index=0; index < nameValuePairs.size(); index++) {
	    		 entity.addPart(nameValuePairs.get(index).getName(), new StringBody(nameValuePairs.get(index).getValue()));
	         }

	         httppost.setEntity(entity);
	         
	         HttpResponse response = httpclient.execute(httppost, localContext);
	         HttpEntity entityResp = response.getEntity();
		     MyApp.setGlobalMessage(responseToString(entityResp));

	         httpclient.getConnectionManager().shutdown();

	    } catch (ClientProtocolException e) {
	    } catch (IOException e) {
	    }
	}
	
	public static Boolean sendCreateChallenge(Challenge challenge){
		HttpClient httpclient = new DefaultHttpClient();
		HttpContext localContext = new BasicHttpContext();
	    HttpPost httppost = new HttpPost(urlAddChallenge);

	    String image = challenge.getFilePath()+challenge.getFileName();
	    
	    try {
	    	 MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
	    	 
	    	 List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(4);
	    	 nameValuePairs.add(new BasicNameValuePair("description", challenge.getChallengeName()));
		     nameValuePairs.add(new BasicNameValuePair("tags", challenge.getChallengeCathegory()));
		     nameValuePairs.add(new BasicNameValuePair("token", challenge.getToken()));
		     nameValuePairs.add(new BasicNameValuePair("image", image));
		        
	    	 for(int index=0; index < nameValuePairs.size(); index++) {
	             if(nameValuePairs.get(index).getName().equalsIgnoreCase("image")) {
	                 entity.addPart(nameValuePairs.get(index).getName(), new FileBody(new File (nameValuePairs.get(index).getValue())));
	             } else {
	                 entity.addPart(nameValuePairs.get(index).getName(), new StringBody(nameValuePairs.get(index).getValue()));
	             }
	         }

	         httppost.setEntity(entity);
	         
	         HttpResponse response = httpclient.execute(httppost, localContext);
	         HttpEntity entityResp = response.getEntity();
		     MyApp.setGlobalMessage(responseToString(entityResp));
		     
	        return true;
	    } catch (ClientProtocolException e) {
	    } catch (IOException e) {
	    }
	    return false;
	}
	
	
	public static void getCathegory() throws IOException{
		
		URL url = new URL(urlCathegory);
		URLConnection urlConnection = url.openConnection();
		InputStream in = new BufferedInputStream(urlConnection.getInputStream());
		try {
			BufferedReader r = new BufferedReader(new InputStreamReader(in));
			StringBuilder total = new StringBuilder();
			String line;
			while ((line = r.readLine()) != null) {
			    total.append(line);
			}
			MyApp.setCathegoryList(total.toString());
		}finally {
			in.close();
		}
	}
	
	public static Boolean loginPost(User user){
		HttpClient httpclient = new DefaultHttpClient();
	    HttpPost httppost = new HttpPost(urlLogin);
	    try {
	        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
	        nameValuePairs.add(new BasicNameValuePair("username", user.getUsername()));
	        nameValuePairs.add(new BasicNameValuePair("password", user.getPassword()));
	        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

	        HttpResponse response = httpclient.execute(httppost);
	        HttpEntity entity = response.getEntity();
	        
	        MyApp.setGlobalMessage(responseToString(entity));
	        
	        return true;
	    } catch (ClientProtocolException e) {} catch (IOException e) {}
	    return false;
	}
	
	public static Boolean addUserPost(User user){
		HttpClient httpclient = new DefaultHttpClient();
	    HttpPost httppost = new HttpPost(urlAddUser);
	    try {
	        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
	        nameValuePairs.add(new BasicNameValuePair("username", user.getUsername()));
	        nameValuePairs.add(new BasicNameValuePair("email", user.getEmail()));
	        nameValuePairs.add(new BasicNameValuePair("password", user.getPassword()));
	        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	        
	        HttpResponse response = httpclient.execute(httppost);
	        HttpEntity entity = response.getEntity();
	        
	        MyApp.setGlobalMessage(responseToString(entity));

	        return true;
	    } catch (ClientProtocolException e) {
	    } catch (IOException e) {
	    }
	    return false;
	}

	private static String responseToStringMultipart(HttpResponse response){
		StringBuilder sb = new StringBuilder();
		try {
		    BufferedReader reader = 
		           new BufferedReader(new InputStreamReader(((MultipartEntity) response).getContent()), 65728);
		    String line = null;

		    while ((line = reader.readLine()) != null) {
		        sb.append(line);
		    }
		}
		catch (IOException e) { e.printStackTrace(); }
		catch (Exception e) { e.printStackTrace(); }
		return sb.toString();
	}
	
	private static String responseToString(HttpEntity entity){
		StringBuilder sb = new StringBuilder();
		try {
		    BufferedReader reader = 
		           new BufferedReader(new InputStreamReader(entity.getContent()), 65728);
		    String line = null;

		    while ((line = reader.readLine()) != null) {
		        sb.append(line);
		    }
		}
		catch (IOException e) { e.printStackTrace(); }
		catch (Exception e) { e.printStackTrace(); }
		return sb.toString();
	}
}
