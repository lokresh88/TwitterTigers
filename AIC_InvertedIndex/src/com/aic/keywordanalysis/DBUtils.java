package com.aic.keywordanalysis;

import java.net.UnknownHostException;
import java.util.logging.Logger;

import org.bson.types.ObjectId;
import org.json.simple.JSONObject;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;

public class DBUtils {
	private static final Logger log = Logger.getLogger(DBUtils.class.getName());
	Mongo mongoObj;
	DB db;
	
	private final static String DB_CELEBS = "Celebritries";
	private final static String DB_CELEBS_CID = "CID";
	private final static String DB_CELEBS_SCREENNAME = "Screen_Name";
	private final static String DB_CELEBS_JSON = "JSON";
	
	private final static String DB_SUGGESTIONS = "Suggestions";
	private final static String DB_SUG_KEYS = "Keywords";
	private final static String DB_SUG_CID = "CID";
	private final static String DB_SUG_FREQ = "Frequency";
	private final static String DB_SUG_ISACTIVE = "Isactive";
	
	private final static String DB_CTWEETS = "CTweets";
	private final static String DB_CTWEETS_CID = "CID"; // 
	private final static String DB_CTWEETS_TID = "TID";
	private final static String DB_CTWEETS_JSON = "TJSON";
	private final static String DB_CTWEETS_Time = "Time";
	private final static String DB_CTWEETS_Text = "Text";
	
	private final static String DB_CTWEETS_RTO = "RTO";   // -1 for Originals , CID for Retweets
	private final static String DB_CTWEETS_UserJson = "User"; // Null for Originals, User followers details for retweets
	
	
	/*********** Managing DB CONNECTIONS ***************/

	public DBUtils() throws UnknownHostException {

		try{
		// object will be a connection to a MongoDB server for the specified
		// database.
		mongoObj = new Mongo("127.0.0.1", 27017);

		// get a intsance to db
		db = mongoObj.getDB("TwitterData");
		}catch(Exception exp){
			exp.printStackTrace();
		}
	}

	public void closeConnection() {
		mongoObj.close();
	}

	/*********** Managing Celeb Info ***************/

	/**
	 * createUser Input : user details contained in the User object. Output :
	 * Status of the database transaction and new user details. Description :
	 * Adds a new user , creates default list and returns the new user id.
	 */

	public void createCeleb(JSONObject tjson) {

		// Create your BSON
		BasicDBObject doc = new BasicDBObject();
		doc.put(DB_CELEBS_CID, tjson.get("id"));
		doc.put(DB_CELEBS_SCREENNAME, tjson.get("screen_name"));
		doc.put(DB_CELEBS_JSON, tjson);

		// insert into the database
		DBCollection coll = db.getCollection(DB_CELEBS);
		coll.insert(doc);
				
	}
	
	public void createSuggestion(JSONObject sugJson) {

		// Create your BSON
		BasicDBObject doc = new BasicDBObject();
		doc.put(DB_SUG_ISACTIVE, sugJson.get("ISACTIVE"));
		doc.put(DB_SUG_FREQ, sugJson.get("FREQ"));
		doc.put(DB_SUG_KEYS, sugJson.get("KEYWORD"));
		doc.put(DB_SUG_CID, sugJson.get("CID"));
		
		// insert into the database
		DBCollection coll = db.getCollection(DB_SUGGESTIONS);
		coll.insert(doc);		
	}

	public void createCTweet(JSONObject tweetJson) {

		// Create your BSON
		BasicDBObject doc = new BasicDBObject();
		doc.put(DB_CTWEETS_CID, tweetJson.get("CID"));
		doc.put(DB_CTWEETS_TID, tweetJson.get("TID"));
		doc.put(DB_CTWEETS_Text, tweetJson.get("Text"));
		doc.put(DB_CTWEETS_Time, tweetJson.get("Time"));
		doc.put(DB_CTWEETS_JSON, tweetJson.get("JSON"));
		doc.put(DB_CTWEETS_UserJson, tweetJson.get("USERJSON"));
		doc.put(DB_CTWEETS_RTO, tweetJson.get("RTO"));
		
		// insert into the database
		DBCollection coll = db.getCollection(DB_CTWEETS);
		coll.insert(doc);		

	}
	
	public void refreshDB(){
		DBCollection coll = db.getCollection(DB_CELEBS);
		coll.remove(new BasicDBObject());
		
		DBCollection coll1 = db.getCollection(DB_SUGGESTIONS);
		coll1.remove(new BasicDBObject());
		
		DBCollection coll2 = db.getCollection(DB_CTWEETS);
		coll2.remove(new BasicDBObject());

	}
	
}
