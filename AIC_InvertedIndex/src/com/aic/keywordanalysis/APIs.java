package com.aic.keywordanalysis;
import java.net.UnknownHostException;

import com.mongodb.*;

import org.json.simple.JSONArray;
//import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
public class APIs
{
	public void GetCoordinatesFromCity(String City)
	{
	
	}
	
	public long GetCelebIDfromScreenName(String ScreenName) throws UnknownHostException
	{
		DBUtils Dbobj = new DBUtils();
		return Dbobj.CelebIDfromScreenName(ScreenName); 
		
	}

	public JSONArray GetAllCelebs() throws UnknownHostException
	{
		DBUtils Dbobj = new DBUtils();
		return Dbobj.Celeblists(); 
	}
	public JSONArray SuggestlistbyCelebID(Long CelebID,int limit) throws UnknownHostException
	{
		DBUtils Dbobj = new DBUtils();
		return Dbobj.SuggestlistperCelebrity(CelebID, limit);
		
	}
	public JSONArray GetGeoInfo(Long CelebID,String keyword)
	{
		
		return null;
	}
	
}