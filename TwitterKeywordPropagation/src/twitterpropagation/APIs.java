package twitterpropagation;

import java.net.UnknownHostException;
import java.util.ArrayList;

import com.mongodb.*;

import org.json.simple.JSONArray;
//import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class APIs {

    private static DBUtils Dbobj;
    static {
        try {
            Dbobj = new DBUtils();
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void GetCoordinatesFromCity(String City) {

    }

    public static JSONArray listofCelebsthatusekeyword(String keyword,
            JSONArray Celebs) throws UnknownHostException {
        JSONArray totalsuggests = new JSONArray();
        for (int i = 0; i < Celebs.size(); i++) {
            JSONObject celebrity = (JSONObject) Celebs.get(i);
            System.out.println(celebrity);
            totalsuggests.add(Dbobj.listofCelebsthatusedgivenkeyword(keyword,
                    (Long)celebrity.get("value"),(String)celebrity.get("text")));
        }
        return totalsuggests;
    }

    public static long GetCelebIDfromScreenName(String ScreenName)
            throws UnknownHostException {
        return Dbobj.CelebIDfromScreenName(ScreenName);

    }
    
   

    public static JSONArray GetAllCelebs() throws UnknownHostException {
        return Dbobj.Celeblists();
    }

    public static JSONObject SuggestlistbyCelebID(Long CelebID, int limit)
            throws UnknownHostException {
        return Dbobj.SuggestlistperCelebrity(CelebID, limit);

    }

    public static JSONArray GetGeoInfo(Long CelebID, String keyword)
            throws UnknownHostException {

        JSONArray jsonopcache = Dbobj.getGeoFromCache(CelebID, keyword);
        if (jsonopcache != null) {
            return jsonopcache;
        }
        ArrayList tweets = Dbobj.getCTweetsHavingKeyword(CelebID, keyword);
        JSONArray json = Dbobj.getLocationsForTweets(
                Dbobj.processTweetsToLocations(tweets), tweets);
        System.out.println(json);
        if (json != null)
            Dbobj.createGeoEntry(CelebID, keyword, json);
        // cache mgmt

        return json;

    }
    
    public static JSONArray GetTimeLineData(Long CelebID, String keyword)
            throws UnknownHostException {

   /*     JSONArray jsonopcache = Dbobj.getGeoFromCache(CelebID, keyword);
        if (jsonopcache != null) {
            return jsonopcache;
        }*/
        JSONArray tweets = Dbobj.getCTweetsMainHavingKeyword(CelebID, keyword);
        /*JSONArray json = Dbobj.getLocationsForTweets(
                Dbobj.processTweetsToLocations(tweets), tweets);*/
        
        System.out.println(tweets);
        
        /*if (json != null)
            Dbobj.createGeoEntry(CelebID, keyword, json);*/
        // cache mgmt

        return tweets;

    }


    public String CountryCodefromCountry(String Country) {
        CountryNametoCountryCode obj = new CountryNametoCountryCode();
        return obj.getCountryCode(Country);

    }

    public void BuildCodefromCountry() {
        CountryNametoCountryCode obj = new CountryNametoCountryCode();
        // obj.BuildHashMap();
    }

    public static void UpdateSuggestionLists(ArrayList<String> TobeRemoved)
            throws UnknownHostException {
        DBUtils Dbobj = new DBUtils();
        Dbobj.UpdateSuggestion(TobeRemoved);
    }
}
