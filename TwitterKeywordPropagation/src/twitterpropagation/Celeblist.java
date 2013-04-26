package twitterpropagation;
import java.net.UnknownHostException;
import java.util.ArrayList;

import com.mongodb.*;

import org.json.simple.JSONArray;

public class Celeblist
{
	public static void main(String args[]) throws UnknownHostException
	{
		APIs testobject = new APIs();
		String Remove="me,i,the,of,and,a,to,in,is,you,that,it,he,was,for,on,are,as,with,his,they,I,at,be,this,have,from,or,one,had,by,word,but,not,what,all,were,we,when,your,can,said,there,use,an,each,which,she,do,how,their,if,will,up,other,about,out,many,then,them,these,so,some,her,would,make,like,him,into,time,has,look,two,more,write,go,see,number,no,way,could,people,my,than,first,water,been,call,who,oil,its,now,find,long,down,day,did,get,come,made,may,part,RT, ,:,...,.,..,,"; 
		String[] split=Remove.split(",");
		ArrayList<String> TobeRemoved = new ArrayList<String>();
		for(int i=0;i<split.length;i++)
		{
			TobeRemoved.add(split[i]);
		}
		System.out.println(testobject.GetAllCelebs());
		
	     // System.out.println(testobject.listofCelebsthatusekeyword("smile",testobject.GetAllCelebs()));
		
		Long CelebID=testobject.GetCelebIDfromScreenName("KaleyCuoco");
		/*System.out.println(testobject.SuggestlistbyCelebID(CelebID, 50));
		testobject.BuildCodefromCountry();
		String country="Argentina";
		country = country.toUpperCase();
		System.out.println(testobject.CountryCodefromCountry(country));*/
		
		DBUtils db=new DBUtils();
		//db.processTweetsToLocations(db.getCTweetsHavingKeyword(CelebID,"Bazinga"));
		//ArrayList tweets = db.getCTweetsHavingKeyword(CelebID,"Bazinga");
		//db.getLocationsForTweets(db.processTweetsToLocations(tweets),tweets);
		
		
		//country=country.toUpperCase();
		//System.out.println(testobject.CountryCodefromCountry(country));
		testobject.UpdateSuggestionLists(TobeRemoved);

	}
}