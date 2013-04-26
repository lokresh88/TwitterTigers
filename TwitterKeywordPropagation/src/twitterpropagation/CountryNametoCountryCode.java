package twitterpropagation;
import java.util.ArrayList;
import java.util.HashMap;
public class CountryNametoCountryCode {
	private final static HashMap<String,String> NametoCode = new HashMap<String, String>();
//	ArrayList<String> Country = new ArrayList<String>();
//	ArrayList<String> Code = new ArrayList<String>();
	
	static{
	//public void BuildHashMap()
	//{
		String Codes="AF,AL,DZ,AS,AD,AO,AI,AQ,AG,AR,AM,AW,AU,AT,AZ,BS,BH,BD,BB,BY,BE,BZ,BJ,BM,BT,BO,BA,BW,BV,BR,IO,BN,BG,BF,BI,KH,CM,CA,CV,KY,CF,TD,CL,CN,CX,CC,CO,KM,CG,CD,CK,CR,CI,HR,CU,CY,CZ,DK,DJ,DM,DO,TP,EC,EG,SV,GQ,ER,EE,ET,FK,FO,FJ,FI,FR,FX,GF,PF,TF,GA,GM,GE,DE,GH,GI,GR,GL,GD,GP,GU,GT,GN,GW,GY,HT,HM,VA,HN,HK,HU,IS,IN,ID,IR,IQ,IE,IL,IT,JM,JP,JO,KZ,KE,KI,KP,KR,KW,KG,LA,LV,LB,LS,LR,LY,LI,LT,LU,MO,MK,MG,MW,MY,MV,ML,MT,MH,MQ,MR,MU,YT,MX,FM,MD,MC,MN,ME,MS,MA,MZ,MM,NA,NR,NP,NL,AN,NC,NZ,NI,NE,NG,NU,NF,MP,NO,OM,PK,PW,PA,PG,PY,PE,PH,PN,PL,PT,PR,QA,RE,RO,RU,RW,KN,LC,VC,WS,SM,ST,SA,SN,RS,SC,SL,SG,SK,SI,SB,SO,ZA,SS,GS,ES,LK,SH,PM,SD,SR,SJ,SZ,SE,CH,SY,TW,TJ,TZ,TH,TG,TK,TO,TT,TN,TR,TM,TC,TV,UG,UA,AE,GB,US,UM,UY,UZ,VU,VE,VN,VG,VI,WF,EH,YE,ZM,ZW";
		String Countries="AFGHANISTAN,ALBANIA,ALGERIA,AMERICAN SAMOA,ANDORRA,ANGOLA,ANGUILLA,ANTARCTICA,ANTIGUA AND BARBUDA,ARGENTINA,ARMENIA,ARUBA,AUSTRALIA,AUSTRIA,AZERBAIJAN,BAHAMAS,BAHRAIN,BANGLADESH,BARBADOS,BELARUS,BELGIUM,BELIZE,BENIN,BERMUDA,BHUTAN,BOLIVIA,BOSNIA AND HERZEGOWINA,BOTSWANA,BOUVET ISLAND,BRAZIL,BRITISH INDIAN OCEAN TERRITORY,BRUNEI DARUSSALAM,BULGARIA,BURKINA FASO,BURUNDI,CAMBODIA,CAMEROON,CANADA,CAPE VERDE,CAYMAN ISLANDS,CENTRAL AFRICAN REPUBLIC,CHAD,CHILE,CHINA,CHRISTMAS ISLAND,COCOS (KEELING) ISLANDS,COLOMBIA,COMOROS,CONGO,COOK ISLANDS,COSTA RICA,COTE D'IVOIRE,CROATIA,CUBA,CYPRUS,CZECH REPUBLIC,DENMARK,DJIBOUTI,DOMINICA,DOMINICAN REPUBLIC,EAST TIMOR,ECUADOR,EGYPT,EL SALVADOR,EQUATORIAL GUINEA,ERITREA,ESTONIA,ETHIOPIA,FALKLAND ISLANDS (MALVINAS),FAROE ISLANDS,FIJI,FINLAND,FRANCE,FRANCE METROPOLITAN,FRENCH GUIANA,FRENCH POLYNESIA,FRENCH SOUTHERN TERRITORIES,GABON,GAMBIA,GEORGIA,GERMANY,GHANA,GIBRALTAR,GREECE,GREENLAND,GRENADA,GUADELOUPE,GUAM,GUATEMALA,GUINEA,GUINEA-BISSAU,GUYANA,HAITI,HEARD AND MC DONALD ISLANDS,HOLY SEE (VATICAN CITY STATE),HONDURAS,HONG KONG,HUNGARY,ICELAND,INDIA,INDONESIA,IRAN (ISLAMIC REPUBLIC OF),IRAQ,IRELAND,ISRAEL,ITALY,JAMAICA,JAPAN,JORDAN,KAZAKHSTAN,KENYA,KIRIBATI,SOUTH KOREA,NORTH KOREA, REPUBLIC OF,KUWAIT,KYRGYZSTAN,LAOS ,LATVIA,LEBANON,LESOTHO,LIBERIA,LIBYAN ARAB JAMAHIRIYA,LIECHTENSTEIN,LITHUANIA,LUXEMBOURG,MACAU,MACEDONIA,MADAGASCAR,MALAWI,MALAYSIA,MALDIVES,MALI,MALTA,MARSHALL ISLANDS,MARTINIQUE,MAURITANIA,MAURITIUS,MAYOTTE,MEXICO,MICRONESIA,MOLDOVA,MONACO,MONGOLIA,MONTENEGRO,MONTSERRAT,MOROCCO,MOZAMBIQUE,MYANMAR,NAMIBIA,NAURU,NEPAL,NETHERLANDS,NETHERLANDS ANTILLES,NEW CALEDONIA,NEW ZEALAND,NICARAGUA,NIGER,NIGERIA,NIUE,NORFOLK ISLAND,NORTHERN MARIANA ISLANDS,NORWAY,OMAN,PAKISTAN,PALAU,PANAMA,PAPUA NEW GUINEA,PARAGUAY,PERU,PHILIPPINES,PITCAIRN,POLAND,PORTUGAL,PUERTO RICO,QATAR,REUNION,ROMANIA,RUSSIAN FEDERATION,RWANDA,SAINT KITTS AND NEVIS,SAINT LUCIA,SAINT VINCENT AND THE GRENADINES,SAMOA,SAN MARINO,SAO TOME AND PRINCIPE,SAUDI ARABIA,SENEGAL,SERBIA,SEYCHELLES,SIERRA LEONE,SINGAPORE,SLOVAKIA (Slovak Republic),SLOVENIA,SOLOMON ISLANDS,SOMALIA,SOUTH AFRICA,SOUTH SUDAN,SOUTH GEORGIA AND SOUTH S.S.,SPAIN,SRI LANKA,ST. HELENA,ST. PIERRE AND MIQUELON,SUDAN,SURINAME,SVALBARD AND JAN MAYEN ISLANDS,SWAZILAND,SWEDEN,SWITZERLAND,SYRIAN ARAB REPUBLIC,TAIWAN,TAJIKISTAN,TANZANIA,THAILAND,TOGO,TOKELAU,TONGA,TRINIDAD AND TOBAGO,TUNISIA,TURKEY,TURKMENISTAN,TURKS AND CAICOS ISLANDS,TUVALU,UGANDA,UKRAINE,UNITED ARAB EMIRATES,UNITED KINGDOM,UNITED STATES,U.S. MINOR ISLANDS,        URUGUAY,UZBEKISTAN,VANUATU,VENEZUELA,VIET NAM,VIRGIN ISLANDS (BRITISH),VIRGIN ISLANDS (U.S.),WALLIS AND FUTUNA ISLANDS,WESTERN SAHARA,YEMEN,ZAMBIA,ZIMBABWE";
	//	System.out.println(Codes.length());
		//System.out.println(Countries.length());
		String[] Codesplit=Codes.split(",");
		String[] Countrysplit=Countries.split(",");
	//	System.out.println(Codesplit.length);
		//System.out.println(Countrysplit.length);
		for(int i=0;i<Countrysplit.length;i++)
		{
			NametoCode.put(Countrysplit[i],Codesplit[i]);
	//		System.out.println("Code:"+NametoCode.get(Countrysplit[i]));
		//	System.out.println("Country:"+Countrysplit[i]);
			
		}
	}
	public String getCountryCode(String CountryName)
	{
		return NametoCode.get(CountryName);
	}
	
	public static HashMap<String, String> getNametocode() {
        return NametoCode;
    }
}
