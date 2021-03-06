/*
 * PEARSON PROPRIETARY AND CONFIDENTIAL INFORMATION SUBJECT TO NDA 
 * Copyright (c) 2017 Pearson Education, Inc.
 * All Rights Reserved. 
 * 
 * NOTICE: All information contained herein is, and remains the property of 
 * Pearson Education, Inc. The intellectual and technical concepts contained 
 * herein are proprietary to Pearson Education, Inc. and may be covered by U.S. 
 * and Foreign Patents, patent applications, and are protected by trade secret 
 * or copyright law. Dissemination of this information, reproduction of this  
 * material, and copying or distribution of this software is strictly forbidden   
 * unless prior written permission is obtained from Pearson Education, Inc.
 */
package com.autofusion.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.autofusion.BaseClass;

public final class DeleteRestAPI extends BaseClass {
    public DeleteRestAPI() {
        // TODO Auto-generated constructor stub
    }

    private final static String USER_AGENT = "Mozilla/5.0";

    public static void sendGet(String url) {
        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            // optional default is GET
            con.setRequestMethod("DELETE");

            // add request header
            con.setRequestProperty("User-Agent", USER_AGENT);
            int responseCode = con.getResponseCode();
            APP_LOG.info("\nSending 'DELETE' request to URL : " + url);
            APP_LOG.info("Response Code : " + responseCode);

            InputStreamReader in = new InputStreamReader(con.getInputStream());
            BufferedReader reader = new BufferedReader(in);
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = reader.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            // print result
            APP_LOG.info(response.toString());
        } catch (Exception e) {
            APP_LOG.error("Exception coming on executing Delete Rest API", e);
        }

    }
}
