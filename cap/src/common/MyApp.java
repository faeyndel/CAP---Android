package common;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.widget.ImageButton;

public class MyApp extends Application implements OnInitListener {

    private static Context mContext;
    private static String mMessage;
    private static String mAction;
    private static String cathegoryList;
    private static String token;
    private static Boolean tokenFlag;
    private static String curUserId;
    private static String userName;
    private static Bitmap image; 
    private static int screenWidth; 
    private static ImageButton cap; 
    private static ImageButton uncap;  
   
    public static ImageButton getUncapImageButton() {
        return uncap;
    }

    public static void setUncapImageButton(ImageButton uncap) {
        MyApp.uncap = uncap;
    }
    
    /**/
    
    public static ImageButton getCapImageButton() {
        return cap;
    }

    public static void setCapImageButton(ImageButton cap) {
        MyApp.cap = cap;
    }
    
    /**/
    
    public static int getScreenWidth() {
        return screenWidth;
    }

    public static void setScreenWidth(int screenWidth) {
        MyApp.screenWidth = screenWidth;
    }
    
    /**/
    
    public static String getUserName() {
        return userName;
    }

    public static void setUserName(String userName) {
        MyApp.userName = userName;
    }
    
    /**/
    
    public static Bitmap getImage() {
        return image;
    }

    public static void setImage(Bitmap image) {
        MyApp.image = image;
    }
    
    /**/
    
    public static String getCurUserId() {
        return curUserId;
    }

    public static void setCurUserId(String curUserId) {
        MyApp.curUserId = curUserId;
    }
    
    /**/
    
    public static Boolean getTokenFlag() {
        return tokenFlag;
    }

    public static void setTokenFlag(Boolean tokenFlag) {
        MyApp.tokenFlag = tokenFlag;
    }
    
    /**/
    
    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        MyApp.token = token;
    }
    
    /**/
    
    public static String getCathegoryList() {
        return cathegoryList;
    }

    public static void setCathegoryList(String cathegoryList) {
        MyApp.cathegoryList = cathegoryList;
    }
    
    /**/
    
    
    public static Context getContext() {
        return mContext;
    }

    public static void setContext(Context mContext) {
        MyApp.mContext = mContext;
    }
    
    /**/
    public static String getAction() {
        return mAction;
    }

    public static void setAction(String action) {
        MyApp.mAction = action;
    }
    
    /**/
    public static String getGlobalMessage() {
        return mMessage;
    }

    public static void setGlobalMessage(String mMessage) {
        MyApp.mMessage = mMessage;
    }

	@Override
	public void onInit(int status) {
		// TODO Auto-generated method stub
	}

}