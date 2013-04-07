package com.aic.keywordanalysis;
import java.net.UnknownHostException;

import com.mongodb.*;

import org.json.simple.JSONArray;

public class Celeblist
{
	public static void main(String args[]) throws UnknownHostException
	{
		APIs testobject = new APIs();
		System.out.println(testobject.GetAllCelebs());
		Long CelebID=testobject.GetCelebIDfromScreenName("kevinpp24");
		System.out.println(testobject.SuggestlistbyCelebID(CelebID, 50));
		testobject.BuildCodefromCountry();
		String country="Argentina";
		country.toUpperCase();
		System.out.println(testobject.CountryCodefromCountry(country));
	}
}