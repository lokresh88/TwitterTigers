package com.aic.keywordanalysis;
import java.io.*;
import java.util.*;

//import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
//import org.json.simple.parser.JSONParser;
//import org.json.simple.parser.ParseException;

/** InvertedIndex represents an inverted index.
 *
 * It contains methods for creating, storing/loading, and using
 * an inverted index.
 */
	public class AIC_InvertedIndex 
	{
		public static LinkedHashMap<String, Integer> sortHashMapByValuesD(HashMap passedMap) {
			   List mapKeys = new ArrayList(passedMap.keySet());
			   List mapValues = new ArrayList(passedMap.values());
			   Collections.sort(mapValues);
			   Collections.sort(mapKeys);
			   Collections.reverse(mapValues);
			   Collections.reverse(mapKeys);
			   LinkedHashMap sortedMap = 
			       new LinkedHashMap();

			   Iterator valueIt = mapValues.iterator();
			   while (valueIt.hasNext()) {
			       Object val = valueIt.next();
			    Iterator keyIt = mapKeys.iterator();

			    while (keyIt.hasNext()) {
			        Object key = keyIt.next();
			        String comp1 = passedMap.get(key).toString();
			        String comp2 = val.toString();

			        if (comp1.equals(comp2)){
			            passedMap.remove(key);
			            mapKeys.remove(key);
			            sortedMap.put((String)key, (Integer)val);
			            break;
			        }

			    }

			}
			return sortedMap;
			}
	public static void main(String args[]) throws IOException
	{
		//String name;
		JSONObject jsonObject;
		Object obj;
		HashMap<String,Integer> Wordcount = new HashMap<String, Integer>();
		int count=0;
	//	Wordcount.put("Test", 2);
		BufferedReader buf = new BufferedReader(new FileReader("C:\\Users\\lokesh\\Desktop\\AIC\\Project\\run1\\db\\BarackObama.json"));
		do
		{
			obj = JSONValue.parse(buf.readLine());
			jsonObject = (JSONObject) obj;
			 
					String text = (String) jsonObject.get("text");
					//System.out.println(text);
					String tokens[] = text.split(" ");
					for(int i=0;i<tokens.length;i++)
					{
						//System.out.println(tokens[i]);
						if(!Wordcount.containsKey(tokens[i]))
						{
							Wordcount.put(tokens[i], 1);
							//System.out.println(Wordcount.get(tokens[i]));
						}
						if(Wordcount.containsKey(tokens[i]))
						{
							count=Wordcount.get(tokens[i]);
							Wordcount.put(tokens[i], count+1);
							//System.out.println(Wordcount.get(tokens[i]));
						}
						
					}
					
					
					
		}
		while(buf.readLine()!=null);
		Wordcount=sortHashMapByValuesD(Wordcount);
		for (String key : Wordcount.keySet()) {
			   System.out.println("" + key + ":" + Wordcount.get(key));
			}
		
	}
	
	}
	