package twitterpropagation;

import java.io.*;
import java.util.*;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

/**
 * InvertedIndex represents an inverted index.
 * 
 * It contains methods for creating, storing/loading, and using an inverted
 * index.
 */
public class AIC_DataProcessing {

	public static void main(String args[]) throws IOException {
		JSONObject jsonObject;
		String obj;
		DBUtils db = new DBUtils();
		
//		db.refreshDB();
		File folder = new File(
				"C:\\Users\\lokesh\\Desktop\\AIC\\Project\\run2\\");
		for (final File fileEntry : folder.listFiles()) {
			if (!fileEntry.isDirectory()
					&& fileEntry.getAbsolutePath().contains("ama.json")) {
			//    if(fileEntry.getAbsolutePath().contains("oco.json") || fileEntry.getAbsolutePath().contains("ama.json"))
			  //      continue;
				JSONObject userjson = null;
				System.out.println("Starting for --"+fileEntry.getAbsolutePath());
				BufferedReader buf = new BufferedReader(new FileReader(
						fileEntry.getAbsolutePath()));
				while((obj=buf.readLine())!=null) {
					jsonObject = (JSONObject)JSONValue.parse(obj);
					if(jsonObject==null)continue;
					if (!jsonObject.containsKey("retweeted_status")
							&& (!jsonObject.containsKey("in_reply_to_screen_name") || jsonObject.get("in_reply_to_screen_name")==null)) {
						// C Tweets ..
						userjson = (JSONObject) jsonObject.get("user");
					//	System.out.print("Starting for --"+userjson);
						
						JSONObject cdata = new JSONObject();
						cdata.put("CID", userjson.get("id"));
						cdata.put("TID", jsonObject.get("id"));
						cdata.put("Text", jsonObject.get("text"));
						cdata.put("Time", jsonObject.get("created_at"));
						cdata.put("JSON", jsonObject);
						cdata.put("USERJSON", null);
						cdata.put("RTO", -1);
						db.createCTweet(cdata);
					}					
				}
				if(userjson==null)
					continue;
				
				db.createCeleb(userjson);
				buf.close();
				
				// seconf pass
				buf = new BufferedReader(new FileReader(
						fileEntry.getAbsolutePath()));
				while((obj=buf.readLine())!=null) {
					jsonObject = (JSONObject)JSONValue.parse(obj);		
					if(jsonObject==null)continue;
					if(jsonObject.containsKey("retweeted_status")){
						// All retweets from folowers - fans etc
						JSONObject folowsjson = (JSONObject)jsonObject.get("user");
						JSONObject retweetjson = (JSONObject)jsonObject.get("retweeted_status");
						JSONObject cdata = new JSONObject();
						cdata.put("CID", userjson.get("id"));
						cdata.put("TID", jsonObject.get("id"));  // link to parent
						cdata.put("Text", jsonObject.get("text"));
						cdata.put("Time", jsonObject.get("created_at"));
						cdata.put("JSON", jsonObject);
						cdata.put("USERJSON", folowsjson); // follower json
						cdata.put("RTO", retweetjson.get("id"));					
						db.createCTweet(cdata);
						
					}else if(jsonObject.containsKey("in_reply_to_screen_name")){
						// All the Direct replies
						JSONObject folowsjson = (JSONObject)jsonObject.get("user");
						JSONObject cdata = new JSONObject();
						cdata.put("CID", userjson.get("id"));
						cdata.put("TID", jsonObject.get("id"));
						cdata.put("Text", jsonObject.get("text"));
						cdata.put("Time", jsonObject.get("created_at"));
						cdata.put("JSON", jsonObject);
						cdata.put("USERJSON", folowsjson);
						cdata.put("RTO", null);					
						db.createCTweet(cdata);
					}
				} ;
				buf.close();
				System.out.print("Ending for --"+fileEntry.getName());
			}
		
		}
		db.closeConnection();
		return;
	}

}
