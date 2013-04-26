package twitterpropagation;

import java.math.BigDecimal;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import org.bson.BSONObject;
import org.bson.types.ObjectId;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.CommandResult;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.util.JSON;

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

    private final static String DB_GEO = "GeoMap";
    private final static String DB_GEO_LOC = "LOCID";
    private final static String DB_GEO_CNT = "CNTRY";
    private final static String DB_GEO_CTY = "CTY";
    private final static String DB_GEO_LAT = "LATITUDE";
    private final static String DB_GEO_LONG = "LONGITUDE";

    private final static String DB_GEO_CACHE = "GeoMapCache";
    private final static String DB_GEO_CID = "CID";
    private final static String DB_GEO_KEY = "KEY";
    private final static String DB_GEO_OP = "OUTPUT";

    private final static String DB_CTWEETS = "CTweets";
    private final static String DB_CTWEETS_CID = "CID"; //
    private final static String DB_CTWEETS_TID = "TID";
    private final static String DB_CTWEETS_JSON = "TJSON";
    private final static String DB_CTWEETS_Time = "Time";
    private final static String DB_CTWEETS_Text = "Text";

    private final static String DB_CTWEETS_RTO = "RTO"; // -1 for Originals ,
                                                        // CID for Retweets
    private final static String DB_CTWEETS_UserJson = "User"; // Null for
                                                              // Originals, User
                                                              // followers
                                                              // details for
                                                              // retweets

    /*********** Managing DB CONNECTIONS ***************/

    public DBUtils() throws UnknownHostException {

        try {
            // object will be a connection to a MongoDB server for the specified
            // database.
            mongoObj = new Mongo("127.0.0.1", 27017);

            // get a intsance to db
            db = mongoObj.getDB("TwitterData");
        } catch (Exception exp) {
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

    public Long getCelebId(String screen_name) {

        // Create your BSON
        BasicDBObject doc = new BasicDBObject();
        doc.put(DB_CELEBS_SCREENNAME, screen_name);

        DBCollection coll = db.getCollection(DB_CELEBS);
        DBCursor cur = coll.find(doc);
        if (cur.hasNext()) {
            BasicDBObject op = (BasicDBObject) (cur.next());
            return op.getLong("CID");
        }
        return new Long(-1);
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

    public void createGeoEntry(String[] csvLine) {

        // Create your BSON
        BasicDBObject doc = new BasicDBObject();
        doc.put(DB_GEO_CNT, csvLine[1]);
        doc.put(DB_GEO_LOC, csvLine[0]);
        doc.put(DB_GEO_CTY, csvLine[3]);
        doc.put(DB_GEO_LAT, csvLine[5]);
        doc.put(DB_GEO_LONG, csvLine[6]);

        // insert into the database
        DBCollection coll = db.getCollection(DB_GEO);
        coll.insert(doc);

    }

    // cache for Geo ..
    public void createGeoEntry(Long cid, String keyword, JSONArray op) {

        // Create your BSON
        BasicDBObject doc = new BasicDBObject();
        doc.put(DB_GEO_CID, cid);
        doc.put(DB_GEO_KEY, keyword);
        doc.put(DB_GEO_OP, op);

        // insert into the database
        DBCollection coll = db.getCollection(DB_GEO_CACHE);
        coll.insert(doc);

    }

    public JSONArray getGeoFromCache(Long cid, String keyword)
            throws UnknownHostException {
        DBCollection coll = db.getCollection(DB_GEO_CACHE);
        BasicDBObject query1 = new BasicDBObject(DB_GEO_CID, cid);
        query1.put(DB_GEO_KEY, keyword);
        System.out.println(query1);
        JSONArray op = null;
        DBCursor cursor = coll.find(query1, new BasicDBObject());
        while (cursor.hasNext()) {
            JSONObject Celeb;
            Celeb = (JSONObject) JSONValue.parse(cursor.next().toString());
            System.out.println(Celeb + " hit");
            op = (JSONArray) Celeb.get(DB_GEO_OP);
            break;
        }
        cursor.close();

        return op;
    }

    // /************* Cache ends

    public JSONArray Celeblists() throws UnknownHostException {
        DBCollection coll = db.getCollection("Celebritries");
        BasicDBObject query = new BasicDBObject();

        DBCursor cursor = coll.find(new BasicDBObject(), query);
        JSONArray Celebs = new JSONArray();
        query.put("_id", 0);
        query.put("Screen_Name", 1);
        query.put("JSON", 1);
        while (cursor.hasNext()) {
            JSONObject Celeb, rawJson;
            JSONObject displayCeleb = new JSONObject();
            Celeb = (JSONObject) JSONValue.parse(cursor.next().toString());
            rawJson = (JSONObject) Celeb.get("JSON");
            displayCeleb.put("value", (Long) rawJson.get("id"));
            displayCeleb.put("imageSrc",
                    (String) rawJson.get("profile_image_url"));
            displayCeleb.put("sname", (String) rawJson.get("screen_name"));
            displayCeleb.put("text", (String) rawJson.get("name"));
            Celebs.add(displayCeleb);
        }
        cursor.close();

        return Celebs;

    }
    
    public JSONObject getDBSize(Long cid){
        JSONObject counts = new JSONObject();
        DBCollection coll = db.getCollection(DB_CTWEETS);
        BasicDBObject query1 = new BasicDBObject(DB_GEO_CID, cid);
        query1.put(DB_CTWEETS_RTO,null);        
        JSONArray op = null;
        int tweets = coll.find(query1, new BasicDBObject()).count();
        
        BasicDBObject query2 = new BasicDBObject(DB_GEO_CID, cid);
        query2.put(DB_CTWEETS_RTO,new BasicDBObject("$ne",null));        
        int rtweets = coll.find(query2, new BasicDBObject()).count();

        counts.put("tweets", tweets);
        counts.put("rtweets", rtweets);
        return counts;   
    }
    
   

    public JSONObject SuggestlistperCelebrity(Long CelebID, int limit)
            throws UnknownHostException {
        // DBUtils db = new DBUtils();
        // BasicDBObject doc = new BasicDBObject();
        DBCollection coll = db.getCollection("Suggestions");
        BasicDBObject query = new BasicDBObject();
        query.put("_id", 0);
        query.put("Keywords", 1);
        query.put("Frequency", 1);
        DBCursor cursor = coll.find(new BasicDBObject("CID", CelebID), query)
                .limit(limit);
        
        JSONObject result = new JSONObject();
        
        JSONObject dbforceleb = getDBSize(CelebID);
        result.put("counts", dbforceleb);
        // create our pipeline operations, first with the $match
        DBObject match = new BasicDBObject("$match", new BasicDBObject("CID", CelebID) );

        // build the $projection operation
        DBObject fields = new BasicDBObject("CID", 1);
        fields.put("Frequency", 1);
        fields.put("_id", 0);
        DBObject project = new BasicDBObject("$project", fields );
        
        DBObject groupFields = new BasicDBObject( "_id", "$CID");
        groupFields.put("total", new BasicDBObject( "$sum", "$Frequency"));
        DBObject group = new BasicDBObject("$group", groupFields);
        AggregationOutput agg = coll.aggregate(match,project,group);
        System.out.print(agg);
        Iterator ittt = agg.results().iterator();
        Long freq = new Long(1);
        if(ittt.hasNext()){
            DBObject dboj = (DBObject)ittt.next();
            freq = (long) ((int)dboj.get("total")+1);
        }
        JSONArray Suggestlist = new JSONArray();
        while (cursor.hasNext()) {
            JSONObject Celeb;
            Celeb = (JSONObject) JSONValue.parse(cursor.next().toString());
            if(Celeb.get("Keywords")==null || ((String)(Celeb.get("Keywords"))).trim().equals("") || ((String)(Celeb.get("Keywords"))).trim().contains("@")){
                continue;
            }
            Long ftemp = (Long)Celeb.get("Frequency");
            float percent = ((float)ftemp / (float)freq)*100;
            DecimalFormat twoDForm = new DecimalFormat("##.##");
            Celeb.put("percent", Double.valueOf(twoDForm.format(percent)));            
            Suggestlist.add(Celeb);
        }
        cursor.close();
        result.put("op", Suggestlist);
        return result;
    }

    public long CelebIDfromScreenName(String ScreenName)
            throws UnknownHostException {
        // DBUtils db = new DBUtils();
        // BasicDBObject doc = new BasicDBObject();
        DBCollection coll = db.getCollection("Celebritries");
        BasicDBObject query1 = new BasicDBObject("Screen_Name", ScreenName);
        BasicDBObject query = new BasicDBObject();
        query.put("_id", 0);
        query.put("CID", 1);
        Long CelebID = (long) 0;
        DBCursor cursor = coll.find(query1, query);
        // cursor.getKeysWanted("Screen_Name");
        // System.out.println(coll.find());
        // JSONArray Suggestlist = new JSONArray();

        while (cursor.hasNext()) {
            JSONObject Celeb;
            Celeb = (JSONObject) JSONValue.parse(cursor.next().toString());
            // String screen_name = (String) Celeb.get("Screen_Name");
            // Suggestlist.add(Celeb);
            System.out.println(Celeb);
            // JSONValue.parse(cursor.next().toString()));

            CelebID = Long.valueOf(Celeb.get("CID").toString()).longValue();

        }

        cursor.close();

        return CelebID;
    }

    // get all the tweets containing this keyword
    public ArrayList<JSONObject> getCTweetsHavingKeyword(Long celebId,
            String keyword) throws UnknownHostException {
        ArrayList<JSONObject> tweetsList = new ArrayList<JSONObject>();
        DBCollection coll = db.getCollection("CTweets");
        BasicDBObject andQuery = new BasicDBObject();
        List<BasicDBObject> obj = new ArrayList<BasicDBObject>();
        obj.add(new BasicDBObject("CID", celebId));
        obj.add(new BasicDBObject("Text", new BasicDBObject("$regex", ".*"
                + keyword + ".*").append("$options", "i")));
        andQuery.put("$and", obj);
        System.out.println(andQuery);
        BasicDBObject projection = new BasicDBObject();
        projection.put("_id", 0);
        projection.put("Text", 1);
        projection.put("User", 1);
        DBCursor cursor = coll.find(andQuery, projection);
        while (cursor.hasNext()) {
            JSONObject Celeb;
            Celeb = (JSONObject) JSONValue.parse(cursor.next().toString());
            System.out.println(Celeb);
            tweetsList.add(Celeb);
        }
        cursor.close();
        return tweetsList;
    }
    
    public JSONArray getCTweetsMainHavingKeyword(Long celebId,
            String keyword) throws UnknownHostException {
       JSONArray tweetsList = new JSONArray();
        Map opList = new TreeMap();
        DBCollection coll = db.getCollection("CTweets");
        BasicDBObject andQuery = new BasicDBObject();
        List<BasicDBObject> obj = new ArrayList<BasicDBObject>();
        obj.add(new BasicDBObject("CID", celebId));
        obj.add(new BasicDBObject("RTO", null));
        obj.add(new BasicDBObject("Text", new BasicDBObject("$regex", ".*"
                + keyword + ".*").append("$options", "i")));
        andQuery.put("$and", obj);
        System.out.println(andQuery);
        BasicDBObject projection = new BasicDBObject();
        projection.put("_id", 0);
        projection.put("Text", 1);
        projection.put("TID", 1);        
        projection.put("Time", 1);
        DBCursor cursor = coll.find(andQuery, projection).sort(new BasicDBObject("Time",-1));
        while (cursor.hasNext()) {
            JSONObject Celeb;
            Celeb = (JSONObject) JSONValue.parse(cursor.next().toString());
            System.out.println(Celeb +"\n");
            String d = (String)Celeb.get("Time");
            System.out.println(d);
            Date thedate;
            Calendar cld = Calendar.getInstance();
            try {
                thedate = new SimpleDateFormat("EEE MMM dd HH:mm:ss ZZZZZ yyyy", Locale.ENGLISH).parse(d);
                cld.setTime(thedate);
                cld.set(Calendar.HOUR_OF_DAY, 0);  
                cld.set(Calendar.MINUTE, 0);  
                cld.set(Calendar.SECOND, 0);  
                cld.set(Calendar.MILLISECOND, 0); 
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                continue;
            }
            Long ms = cld.getTimeInMillis();         
            if(opList.containsKey(ms)){
                opList.put(ms, (Long)opList.get(ms)+1);
            }else{
                opList.put(ms, new Long(1));
            }
            
        }
        cursor.close();
        
        Iterator itr = opList.keySet().iterator();
        while(itr.hasNext()){
            Long d = (Long)itr.next();
            JSONArray json = new JSONArray();
            json.add(d);
            json.add(opList.get(d));
            tweetsList.add(json);
        }
        return tweetsList;
    }
    
    /*public ArrayList<JSONObject> getCTweetsSubs(Long pId) throws UnknownHostException {
        ArrayList<JSONObject> tweetsList = new ArrayList<JSONObject>();
        DBCollection coll = db.getCollection("CTweets");
        BasicDBObject andQuery = new BasicDBObject();
        List<BasicDBObject> obj = new ArrayList<BasicDBObject>();
        obj.add(new BasicDBObject("RTO", pId));
        andQuery.put("$and", obj);
        System.out.println(andQuery);
        BasicDBObject projection = new BasicDBObject();
        projection.put("_id", 0);
        projection.put("Text", 1);
        projection.put("RTO", 1);
        projection.put("User", 1);
        projection.put("Time", 1);
        DBCursor cursor = coll.find(andQuery, projection).sort(new BasicDBObject("Time",1)).limit(1000);
        while (cursor.hasNext()) {
            JSONObject Celeb;
            Celeb = (JSONObject) JSONValue.parse(cursor.next().toString());            
            tweetsList.add(Celeb);
        }
        cursor.close();
        System.out.println(tweetsList.size());
        return tweetsList;
    }*/

    public City getLocationFromParams(String location) {
        BasicDBObject andQuery = new BasicDBObject();
        try {
            BasicDBList obj = new BasicDBList();
            BasicDBObject tmp = new BasicDBObject("CNTRY", Pattern.compile(".*"
                    + location + ".*", Pattern.CASE_INSENSITIVE));
            obj.add(tmp);

            BasicDBObject tmp1 = new BasicDBObject("CTY", Pattern.compile(".*"
                    + location + ".*", Pattern.CASE_INSENSITIVE));
            obj.add(tmp1);

            andQuery.put("$or", obj);
            System.out.println(andQuery);
            DBCollection coll = db.getCollection("GeoMap");
            DBCursor cursor = coll.find(andQuery, new BasicDBObject("_id", 0));
            while (cursor.hasNext()) {
                JSONObject Celeb;
                Celeb = (JSONObject) JSONValue.parse(cursor.next().toString());
                String lat = (String) Celeb.get(DB_GEO_LAT);
                Double latD = (Double.parseDouble(lat));
                String longi = (String) Celeb.get(DB_GEO_LONG);
                Double longiD = (Double.parseDouble(longi));
                City ctyObj = new City(location, latD, longiD);
                return ctyObj;
            }
            cursor.close();
        } catch (Exception exp) {
            exp.printStackTrace();
        }
        return null;
    }

    // get all the tweets containing this keyword
    public JSONArray getLocationsForTweets(
            HashMap<String, HashMap<String, String>> results, ArrayList tweets)
            throws UnknownHostException {
        HashMap densityMap = new HashMap();
        HashMap<String, String> country = results.get("country");
        HashMap<String, String> city = results.get("city");
        ArrayList<JSONObject> tweetsList = new ArrayList<JSONObject>();
        DBCollection coll = db.getCollection("GeoMap");
        /*
         * HashMap<String, JSONObject> cty = new HashMap<String, JSONObject>();
         * HashMap<String, JSONObject> contry = new HashMap<String,
         * JSONObject>();
         */
        System.out.println(city.size());
        System.out.println(country.size());

        /*
         * for (int t = 0; t < tweets.size(); t++) { JSONObject json =
         * (JSONObject) tweets.get(t); String loc = json.get("User") != null ?
         * (String) ((JSONObject) json .get("User")).get("location") : null;
         */

        int hit = 0;
        Iterator ctyIterator = city.keySet().iterator();
        while (ctyIterator.hasNext()) {
            String loc = (String) ctyIterator.next();
            if (loc == null)
                continue;
            City mapEntry = getLocationFromParams(loc);
            // if (cty.containsKey(loc)) {
            // JSONObject jsonn = (JSONObject) cty.get(loc);
            // String lat = (String) jsonn.get(DB_GEO_LAT);
            // Double latD = (Double.parseDouble(lat));
            // String longi = (String) jsonn.get(DB_GEO_LONG);
            // Double longiD = (Double.parseDouble(longi));
            // mapEntry = new City(loc, latD.intValue(), longiD.intValue());
            /*
             * System.out.println("cty"+loc+" "+mapEntry.getCityName()); } else
             * if (contry.containsKey(loc)) { JSONObject jsonn = (JSONObject)
             * contry.get(loc); String lat = (String) jsonn.get(DB_GEO_LAT);
             * Double latD = (Double.parseDouble(lat)); String longi = (String)
             * jsonn.get(DB_GEO_LONG); Double longiD =
             * (Double.parseDouble(longi)); System.out.println("country"+loc);
             * mapEntry = new City(loc, latD.intValue(), longiD.intValue()); }
             */
            if (mapEntry == null) {
                System.out.println("miss" + loc);
                continue;
            }
            if (densityMap.containsKey(mapEntry)) {
                Integer d = (Integer) densityMap.get(mapEntry);
                densityMap.put(mapEntry, d + 1);
            } else {
                densityMap.put(mapEntry, 1);
            }
            hit++;
        }

        Iterator cntyIteratir = country.keySet().iterator();
        while (cntyIteratir.hasNext()) {
            String loc = (String) cntyIteratir.next();
            if (loc == null)
                continue;
            City mapEntry = getLocationFromParams(loc);
            if (mapEntry == null) {
                System.out.println("miss" + loc);
                continue;
            }
            if (densityMap.containsKey(mapEntry)) {
                Integer d = (Integer) densityMap.get(mapEntry);
                densityMap.put(mapEntry, d + 1);
            } else {
                densityMap.put(mapEntry, 1);
            }
            hit++;
        }

        JSONArray jsonGeoLoc = new JSONArray();
        Iterator<City> keys = densityMap.keySet().iterator();
        while (keys.hasNext()) {
            City keyCty = keys.next();
            Integer den = (Integer) densityMap.get(keyCty);
            JSONObject jsonobj = new JSONObject();
            jsonobj.put("latitude", keyCty.getLatitude());
            jsonobj.put("longitude", keyCty.getLongitude());
            jsonobj.put("value", den);
            jsonGeoLoc.add(jsonobj);
        }

        System.out.println(hit + " opopop");
        return jsonGeoLoc;
    }

    public void UpdateSuggestion(ArrayList<String> Toberemoved)
            throws UnknownHostException {
        DBUtils db = new DBUtils();
        DBCollection coll = db.db.getCollection("Suggestions");
        BasicDBObject updateQuery = new BasicDBObject();
        updateQuery.append("$set",
                new BasicDBObject().append("Isactive", "false"));

        BasicDBObject searchQuery = new BasicDBObject();
        // for(int i=0;i<Toberemoved.size();i++)
        {
            searchQuery.append("Keywords",
                    new BasicDBObject("$in", Toberemoved.toArray()));
        }
        System.out.println(searchQuery);
        System.out.println(updateQuery);

        coll.updateMulti(searchQuery, updateQuery);
    }

    public HashMap<String, HashMap<String, String>> processTweetsToLocations(
            ArrayList<JSONObject> tweetsList) {

        HashMap<String, HashMap<String, String>> resultsMap = new HashMap<String, HashMap<String, String>>();
        HashMap<String, String> locationsMap = new HashMap<String, String>();
        HashMap<String, String> contryLocs = new HashMap<String, String>();
        CountryNametoCountryCode cnn = new CountryNametoCountryCode();

        for (int t = 0; t < tweetsList.size(); t++) {

            JSONObject json = (JSONObject) tweetsList.get(t);
            String loc = json.get("User") != null ? (String) ((JSONObject) json
                    .get("User")).get("location") : null;

            if (loc == null)
                continue;

            if (loc.contains(",")) {
                String[] str = loc.split(",");
                for (int s = 0; s < str.length; s++)
                    if (!locationsMap.containsKey(str[s]))
                        locationsMap.put(str[s], str[s]);
            } else if (cnn.getCountryCode(loc.toUpperCase()) != null) {
                contryLocs.put(cnn.getCountryCode(loc.toUpperCase()),
                        cnn.getCountryCode(loc.toUpperCase()));
            } else {
                locationsMap.put(loc, loc);
            }

        }

        resultsMap.put("city", locationsMap);
        resultsMap.put("country", contryLocs);
        System.out.println(locationsMap);
        System.out.println(contryLocs);
        return resultsMap;
    }
    
    public JSONArray listofCelebsthatusedgivenkeyword(String keyword,Long cid,String screenName) throws UnknownHostException
    {
        DBUtils db = new DBUtils();
        JSONArray op = new JSONArray();
        //BasicDBObject doc = new BasicDBObject();
        DBCollection coll = db.db.getCollection("Suggestions");
        BasicDBObject projection = new BasicDBObject();
        BasicDBObject query = new BasicDBObject();
        query.put("CID", cid);
        query.put("Keywords", keyword);
        projection.put("_id", 0);
        projection.put("Keywords", 1);
        projection.put("Frequency", 1);
        projection.put("Isactive", "true");
        DBCursor cursor = coll.find(query,projection);
        JSONObject Celeb = new JSONObject();
               while(cursor.hasNext()) {
                  Celeb = (JSONObject)JSONValue.parse(cursor.next().toString());
               }
               cursor.close();
        Celeb.put("Name", screenName);
        op.add(screenName);
        op.add(Celeb.get("Frequency"));
        return op;
        
    }


    public void refreshDB() {
        DBCollection coll = db.getCollection(DB_CELEBS);
        coll.remove(new BasicDBObject());

        DBCollection coll2 = db.getCollection(DB_CTWEETS);
        coll2.remove(new BasicDBObject());

    }

    public void refreshDBGEO() {
        DBCollection coll = db.getCollection(DB_GEO);
        coll.remove(new BasicDBObject());

    }

    public void refreshDBSuggestions() {
        DBCollection coll1 = db.getCollection(DB_SUGGESTIONS);
        coll1.remove(new BasicDBObject());

    }

}
