package com.karan.SocialLogin;

import java.io.BufferedReader; 
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.karan.Model.Register;
import com.karan.Services.UserService;
import com.google.gson.Gson;

@Service
public class CallBack {

	final Logger loger = Logger.getLogger(CallBack.class);

	@Autowired
	UserService service;

	public Register getGoogleCallBack(String code) {

		OutputStreamWriter writer = null;
		BufferedReader reader = null;
		com.karan.Model.Register user = null;
		String email;

		try {

			loger.warn("googleCallBack()");

			String temp = "", accessToken, exchangeTokens = "", data = "", tempToken,
					urlParameters = "code=" + code
							+ "&client_id=766044093056-v3f0cums0r9rhjgmqeblgjnbupnskkm2.apps.googleusercontent.com"
							+ "&client_secret=HFQN6oHmshoLBN9CQfwwG5Qs"
							+ "&redirect_uri=http://localhost:8080/MVCLogin2/callBack" + "&grant_type=authorization_code";

			loger.warn("My URL parameters:" + urlParameters);

			URL url = new URL("https://accounts.google.com/o/oauth2/token");

			URLConnection conn = url.openConnection();
			conn.setDoOutput(true);
			writer = new OutputStreamWriter(conn.getOutputStream());
			writer.write(urlParameters);
			writer.flush();

			reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			while ((tempToken = reader.readLine()) != null) {

				exchangeTokens += tempToken;
			}

			loger.warn("My URL parameters:" + urlParameters);
			accessToken = GsonUtils.getJsonElementString("access_token", exchangeTokens);
			//System.out.println("Access Token: " + accessToken + "\n");
			loger.info("My AccessToken:" + accessToken);
			url = new URL("https://www.googleapis.com/oauth2/v1/userinfo?access_token=" + accessToken);
			;
			conn = url.openConnection();
			reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			loger.warn("Reading parameters");
			while ((temp = reader.readLine()) != null) {

				data += temp;
			}

			System.out.println("User Data: " + data);
			user = (Register) new Gson().fromJson(data, Register.class);
			System.out.println(user);
			loger.error("UserData printed..");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				writer.close();
				reader.close();
			} catch (IOException e) {

				e.printStackTrace();
			}

		}

		//System.out.println("Email of Google user" + user.getEmail());
		email = service.checkEmail(user.getEmail());
		//System.out.println("Email from db: " + email);

		if (email == null) {
			user.setUsername("   ");
			service.insertuser(user);
			//System.out.println(user);
			loger.info("DB insertion...");
			return user;
		} else {
			return user;
		}
	}
}