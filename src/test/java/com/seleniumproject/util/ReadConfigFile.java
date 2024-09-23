package com.seleniumproject.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ReadConfigFile{
	Properties prop;
	FileInputStream file; 
	public ReadConfigFile() {
		try {
			file= new FileInputStream(System.getProperty("user.dir")+"//Config//config.properties");
			prop = new Properties();
			prop.load(file);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
	}
	
	public String getAppUrl() {
		String appurl=prop.getProperty("app_url");
		return appurl;
	}
	
	public String getUsername() {
		String username=prop.getProperty("username");
		return username;
	}
	
	public String getPassword() {
		String password=prop.getProperty("password");
		return password;
	}

}
