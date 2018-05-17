package com;

/**
 * Created by farshad2 on 4/19/17.
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import javax.net.ssl.HttpsURLConnection;

import java.net.URL;

import java.io.DataOutputStream;

/**
 * Created by macbook on 4/13/17.
 */

public class HttpURLConnectionExample {

    private  static final String USER_AGENT = "Mozilla/5.0";

    private  static final String GET_URL = "http://www.google.com";

    public   static String POST_URL_curl;
    public   static String POST_URL_login;
    //  private static final String POST_URL = "http://localhost:3006/actuatorAPI";

     void sendGET() throws IOException {
        URL obj = new URL(GET_URL);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);
        int responseCode = con.getResponseCode();
        System.out.println("GET Response Code :: " + responseCode);
        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // print result
            System.out.println(response.toString());
        } else {
            System.out.println("GET request not worked");
        }

    }
    static String  sendingPostRequestToLogin(String username,String password,String server) throws Exception {
        System.out.println("username="+username);
        System.out.println("password="+password);
        String urlParameters  = "username="+username+"&password="+password;
        POST_URL_login = "http://www.ayyaka.com:"+server+"/login";

        String url = POST_URL_login;
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        //add reuqest header
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");


        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + urlParameters);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
            System.out.println("my response="+response);

        }
        in.close();

        //print result
        System.out.println(response.toString());
         return String.valueOf(response);
    }



   static   void sendingPostRequestToCurlDevice(String mac,int value,String user,String server) throws Exception {
        System.out.println("mac="+mac);
        System.out.println("value="+value);
        System.out.println("user="+user);

       POST_URL_curl = "http://www.ayyaka.com:"+server+"/curlDevice";

       String url = POST_URL_curl;


        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // Setting basic post request
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        con.setRequestProperty("Content-Type","application/json");


        String postJsonData ;

        postJsonData="{\"message\""+":"+
                "{"+"\"mac\""+":"+"\""+mac+"\""+","+"\"value\""+":"
                +value+","+"\"user\""+":"+"\""+user+"\""+","+"\"ip\""+":"+"\"91.98.158.53"+":"+4000+"\""+"}}";
        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(postJsonData);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        System.out.println("nSending 'POST' request to URL : " + url);
        System.out.println("Post Data : " + postJsonData);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String output;
        StringBuffer response = new StringBuffer();

        while ((output = in.readLine()) != null) {
            response.append(output);
        }
        in.close();

        //printing result from response
        System.out.println(response.toString());
    }

}