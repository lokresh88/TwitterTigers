package twitterpropagation;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.Calendar;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import org.bson.types.ObjectId;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class TweetOperations extends HttpServlet {
    private static final Logger log = Logger.getLogger(TweetOperations.class
            .getName());
    public final static int NUMBERMAX_CNT = 50;

    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        String action = req.getParameter("action") != null ? req
                .getParameter("action") : null;
        System.out.print("test");

        if (action != null && (action.equalsIgnoreCase("getCelebs"))) {
            JSONArray jsonArr = APIs.GetAllCelebs();
            System.out.print("test" + jsonArr);
            
            PrintWriter pout = resp.getWriter();
            pout.write(jsonArr.toString());
            return;

        } else if ((action.equalsIgnoreCase("getCelebsSugg"))) {
            String celebId = req.getParameter("cid") != null ? req
                    .getParameter("cid") : null;
            Long celebID = Long.parseLong(celebId);
            JSONObject jsonArr = APIs.SuggestlistbyCelebID(celebID,
                    NUMBERMAX_CNT);
            System.out.print("test" + jsonArr);

            PrintWriter pout = resp.getWriter();
            pout.write(jsonArr.toString());
            return;
        } else if ((action.equalsIgnoreCase("getGeoInfo"))) {
            String celebId = req.getParameter("cid") != null ? req
                    .getParameter("cid") : null;
            String keyword = req.getParameter("key") != null ? req
                    .getParameter("key") : null;
            Long celebID = Long.parseLong(celebId);
            JSONArray jsonArr = APIs.GetGeoInfo(celebID, keyword);
   //         System.out.print("test" + jsonArr);
            PrintWriter pout = resp.getWriter();
            pout.write(jsonArr.toString());
            return;

        }else if ((action.equalsIgnoreCase("getComparisons"))) {
            String celebId = req.getParameter("cid") != null ? req
                    .getParameter("cid") : null;
            String keyword = req.getParameter("key") != null ? req
                    .getParameter("key") : null;
            Long celebID = Long.parseLong(celebId);
            JSONArray jsonArr = APIs.listofCelebsthatusekeyword(keyword,APIs.GetAllCelebs());
            PrintWriter pout = resp.getWriter();
            pout.write(jsonArr.toString());
            return;

        }else if ((action.equalsIgnoreCase("getTimeline"))) {
            String celebId = req.getParameter("cid") != null ? req
                    .getParameter("cid") : null;
            String keyword = req.getParameter("key") != null ? req
                    .getParameter("key") : null;
            Long celebID = Long.parseLong(celebId);
            JSONArray jsonArr = APIs.GetTimeLineData(celebID, keyword);
          //  System.out.print("test" + jsonArr);
            PrintWriter pout = resp.getWriter();
            pout.write(jsonArr.toString());
            return;

        }

    }
}
