package com.aic.keywordanalysis;
import java.net.UnknownHostException;
import java.util.ArrayList;

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
	public String CountryCodefromCountry(String Country)
	{
		CountryNametoCountryCode obj= new CountryNametoCountryCode();
		return obj.getCountryCode(Country);
		
	}
	public void BuildCodefromCountry()
	{
		CountryNametoCountryCode obj= new CountryNametoCountryCode();
		obj.BuildHashMap();
	}
	public void UpdateSuggestionLists(ArrayList<String> TobeRemoved) throws UnknownHostException
	{
		DBUtils Dbobj = new DBUtils();
		Dbobj.UpdateSuggestion(TobeRemoved);
	}
	
}