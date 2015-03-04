package dataStorage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class capBDStorage extends SQLiteOpenHelper {

	//	public static final String DATABASE INIT;
	public static final String DB_NAME = "capdb";
	public static final int DB_VERSION = 1;
	//	public static final String TABLE CHALLENGE;
	public static final String TABLE_CHALLENGE = "challenge";
	public static final String CHALLENGE_ID = "id";
	public static final String CHALLENGE_AUTHOR_ = "author";
	public static final String CHALLENGE_DESC_ = "description";
	public static final String CHALLENGE_APPROVED = "approved";
	public static final String CHALLENGE_APPROVED_BY = "approvedBy";
	public static final String CHALLENGE_APPROVED_AT = "approvedAt";
	public static final String CHALLENGE_CREATE_AT = "createdAt";
	public static final String CHALLENGE_UPDATE_AT = "updatedAt";
	//	public static final String TABLE ABUSE;
	public static final String TABLE_ABUSE = "abuse";
	public static final String ABUSE_ID = "id";
	public static final String ABUSE_USER_ID = "user_id";
	public static final String ABUSE_CHALLENGE_ID = "challenge_id";
	public static final String ABUSE_DESCRIPTION = "description";
	public static final String ABUSE_CREATE_AT = "createdAt";
	//	public static final String TABLE CHALLENGE TAG;
	public static final String TABLE_CHALLENGE_TAG = "challenge_tag";
	public static final String CHALLENGE_TAG_CHALLENGE_ID = "challenge_id";
	public static final String CHALLENGE_TAG_TAG_ID = "tag_id";
	//	public static final String TABLE TAG;
	public static final String TABLE_TAG = "tag";
	public static final String TAG_ID = "id";
	public static final String TAG_NAME = "name";
	//	public static final String TABLE USER;
	public static final String TABLE_USER = "user";
	public static final String USER_ID = "id";
	public static final String USER_USERNAME = "username";
	public static final String USER_FIRSTNAME = "firstname";
	public static final String USER_LASTNAME = "lastname";
	public static final String USER_EMAIL = "email";
	public static final String USER_SALT = "salt";
	public static final String USER_PASSWORD = "password";
	public static final String USER_PICTURE = "picture";
	public static final String USER_BIRTHDAY = "birthday";
	public static final String USER_CREATE_AT = "createdAt";
	public static final String USER_UPDATE_AT = "updatedAt";
	public static final String USER_TOKEN = "token";
	//	public static final String TABLE USER CHALLENGE;
	public static final String TABLE_USER_CHALLENGE = "user_challenge";
	public static final String USER_CHALLENGE_ID = "id";
	public static final String USER_CHALLENGE_CHALLENGE_ID = "challenge_id";
	public static final String USER_CHALLENGE_USER_ID = "user_id";
	public static final String USER_CHALLENGE_MEDIA = "media";
	public static final String USER_CHALLENGE_COUNTCAP = "countCap";
	public static final String USER_CHALLENGE_COUNTPASCAP = "countPasCap";
	public static final String USER_CHALLENGE_CREATED_AT = "createdAt";
	//	public static final String TABLE VOTE;
	public static final String TABLE_VOTE = "vote";
	public static final String VOTE_ID = "id";
	public static final String VOTE_USERCHALLENGE_ID = "userchallenge_id";
	public static final String VOTE_USER_ID = "user_id";
	public static final String VOTE_CAP = "cap";
	public static final String VOTE_CREATED_AT = "createdAt";
	
	
	private static final String CREATE_TABLE_ABUSE = "CREATE TABLE " + TABLE_ABUSE + "" +
			"(_ID INTEGER PRIMARY KEY AUTOINCREMENT, "+ABUSE_ID+
			" INT, "+ABUSE_USER_ID+
			" INT, "+ABUSE_CHALLENGE_ID+
			" TEXT, "+ABUSE_DESCRIPTION+
			" TIMESTAMP, "+ABUSE_CREATE_AT+")";

	private static final String CREATE_TABLE_CHALLENGE = "CREATE TABLE " + TABLE_CHALLENGE + "" +
			"(_ID INTEGER PRIMARY KEY AUTOINCREMENT, "+CHALLENGE_ID+
			" INT, "+CHALLENGE_AUTHOR_+
			" TEXT, "+CHALLENGE_DESC_+
			" TINYINT, "+CHALLENGE_APPROVED+
			" INT, "+CHALLENGE_APPROVED_BY+
			" DATETIME, "+CHALLENGE_APPROVED_AT+
			" TIMESTAMP, "+CHALLENGE_CREATE_AT+
			" TIMESTAMP, "+CHALLENGE_UPDATE_AT+
			")";
	
	private static final String CREATE_TABLE_CHALLENGE_TAG = "CREATE TABLE " + TABLE_CHALLENGE_TAG + "" +
			"(_ID INTEGER PRIMARY KEY AUTOINCREMENT, "+CHALLENGE_TAG_CHALLENGE_ID+
			" INT, "+CHALLENGE_TAG_TAG_ID+
			")";
	
	private static final String CREATE_TABLE_TAG = "CREATE TABLE " + TABLE_TAG + "" +
			"(_ID INTEGER PRIMARY KEY AUTOINCREMENT, "+TAG_ID+
			" VARCHAR, "+TAG_NAME+
			")";
	
	private static final String CREATE_TABLE_USER = "CREATE TABLE " + TABLE_USER + "" +
			"(_ID INTEGER PRIMARY KEY AUTOINCREMENT, "+USER_ID+
			" VARCHAR, "+USER_USERNAME+
			" VARCHAR, "+USER_FIRSTNAME+
			" VARCHAR, "+USER_LASTNAME+
			" VARCHAR, "+USER_EMAIL+
			" VARCHAR, "+USER_SALT+
			" VARCHAR, "+USER_PASSWORD+
			" VARCHAR, "+USER_PICTURE+
			" DATE, "+USER_BIRTHDAY+
			" TIMESTAMP, "+USER_CREATE_AT+
			" TIMESTAMP, "+USER_UPDATE_AT+
			" VARCHAR, "+USER_TOKEN+
			")";
	
	private static final String CREATE_TABLE_USER_CHALLENGE = "CREATE TABLE " + TABLE_USER_CHALLENGE + "" +
			"(_ID INTEGER PRIMARY KEY AUTOINCREMENT, "+USER_CHALLENGE_ID+
			" INT, "+USER_CHALLENGE_CHALLENGE_ID+
			" INT, "+USER_CHALLENGE_USER_ID+
			" VARCHAR, "+USER_CHALLENGE_MEDIA+
			" SMALLINT, "+USER_CHALLENGE_COUNTCAP+
			" SMALLINT, "+USER_CHALLENGE_COUNTPASCAP+
			" TIMESTAMP, "+USER_CHALLENGE_CREATED_AT+
			")";
	
	private static final String CREATE_TABLE_VOTE = "CREATE TABLE " + TABLE_VOTE + "" +
			"(_ID INTEGER PRIMARY KEY AUTOINCREMENT, "+VOTE_ID+
			" INT, "+VOTE_USERCHALLENGE_ID+
			" INT, "+VOTE_USER_ID+
			" TINYINT, "+VOTE_CAP+
			" TIMESTAMP, "+VOTE_CREATED_AT+
			")";
	
	
	/**
	 * Constructeur
	 * @param context
	 * @param name
	 * @param factory
	 * @param version
	 */
	public capBDStorage(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
	}



	/**
	 * Méthode nécessaire pour la création du modèle de la base de données
	 * @param db
	 *            La base de données
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE_ABUSE);
		db.execSQL(CREATE_TABLE_CHALLENGE);
		db.execSQL(CREATE_TABLE_CHALLENGE_TAG);
		db.execSQL(CREATE_TABLE_TAG);
		db.execSQL(CREATE_TABLE_USER);
		db.execSQL(CREATE_TABLE_USER_CHALLENGE);
		db.execSQL(CREATE_TABLE_VOTE);
	}

	/**
	 * Méthode nécessaire à la mise à jour du modèle de la base de données
	 * @param db
	 *            La base de données
	 * @param oldVersion
	 *            Numéro de l'ancienne version du modèle de données
	 * @param newVersion
	 *            Numéro de la nouvelle version du modèle de données
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

}
