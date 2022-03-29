/**
*** INF 6450 -  Travail noté 5 - Robert M. Vigneault,  fait avec JDK java 14.0.2
*** TN5_Q2.java
**/

package TN5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class TN5_Q2 {
   public static void main(String[] args) {
      /** url Address for CNN rss channel --------------------------------------*/
      String urlAddress = "http://rss.cnn.com/rss/edition.rss";

      /** Handle malformed URL and othe non specific errors --------------------*/
      try {
         URL rssURL = new URL (urlAddress);
         BufferedReader read = 
               new BufferedReader(new InputStreamReader(rssURL.openStream()));
         String inLine;
         /** Loop within all records from 'read' -------------------------------*/
         while ((inLine = read.readLine()) != null) {
            int clTagPos = 0;
            int opTagPos = 0;
            while (opTagPos >= 0) {
               opTagPos = inLine.indexOf("<title>", clTagPos);
               if (opTagPos >= 0) {
                  clTagPos = inLine.indexOf("</title>", opTagPos);
                  String title = inLine.substring(opTagPos +
                        "<title>".length(), clTagPos);
                  /* Get rid of unwanted tags and info (.replace) --------------*/   
                  System.out.println(title.replace("<![CDATA[", "")
                                          .replace("]]>","") + "\n");
               }
            }
         }
         read.close();
      } catch (MalformedURLException ue){
         System.out.println("Malformed URL");
      } catch (IOException ioe){
         System.out.println("Something went wrong reading the contents");
      }
   }
}
