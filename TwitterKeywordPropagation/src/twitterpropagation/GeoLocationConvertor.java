package twitterpropagation;

import java.io.FileReader;
import java.io.IOException;

import au.com.bytecode.opencsv.CSVReader;

public class GeoLocationConvertor {

    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub

        DBUtils db = new DBUtils();
        db.refreshDBGEO();
        CSVReader reader = new CSVReader(
                new FileReader(
                        "C:\\Users\\lokesh\\Desktop\\AIC\\GeoLiteCity-latest\\GeoLiteCity_20130402\\GeoLiteCity-Location.csv"));
        String[] nextLine;
        while ((nextLine = reader.readNext()) != null) {
            if (nextLine.length == 9) {
                 System.out.println(nextLine[2] +" "+nextLine[4] );
                db.createGeoEntry(nextLine);
            }
        }

    }

}
