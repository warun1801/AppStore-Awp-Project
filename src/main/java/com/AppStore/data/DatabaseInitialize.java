package com.AppStore.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.AppStore.domain.AppCategory;
import com.AppStore.domain.Application;

public class DatabaseInitialize {
	
	private List<Application> getAppList() {
		List<Application> allApps = new ArrayList<>();
		allApps.add(new Application(1,"Chrome","Best browser.Eats RAM.",AppCategory.APPS,100000000,4.5d,"images/chrome.jpg",6.9d));
                allApps.add(new Application(2,"Pubg","Best browser.Eats RAM.",AppCategory.GAMES,100000000,4.5d,"images/pubg.png",6.9d));
                allApps.add(new Application(3,"Tinder","Best browser.Eats RAM.",AppCategory.APPS,100000000,4.5d,"images/tinder.jpg",6.9d));
                allApps.add(new Application(4,"Facebook","Best browser.Eats RAM.",AppCategory.APPS,100000000,4.5d,"images/facebook.jpg",6.9d));
                allApps.add(new Application(5,"AngryBirds","Best browser.Eats RAM.",AppCategory.GAMES,100000000,4.5d,"images/angrybirds.png",6.9d));
		return allApps;
	}

	public void initializeDatabase() {
            
		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/zenithdb","root","clasicoelmadrid10");) {
			
				try (PreparedStatement prepStm = conn.prepareStatement("DROP TABLE IF EXISTS apps;")) {
					prepStm.execute();
				}
				
				try (PreparedStatement prepStm = conn.prepareStatement("DROP TABLE IF EXISTS downloads;")) {
					prepStm.execute();
				}
				
				try (PreparedStatement prepStm = conn.prepareStatement("CREATE TABLE apps (id int primary key, name varchar(30), description varchar(150), category varchar(30), downloads int,rating double,logo varchar(200),version double);")) {
					prepStm.execute();
				}
				
				List<Application> itemsList = getAppList();
				for (Application app : itemsList) {
					try (PreparedStatement prepStm = conn.prepareStatement("INSERT INTO apps (id, name, description, category, downloads,rating,logo,version) values (?,?,?,?,?,?,?,?);");) {
						prepStm.setInt(1, app.getId());
						prepStm.setString(2, app.getName());
						prepStm.setString(3, app.getDescription());
						prepStm.setString(4, app.getCategory().toString());
						prepStm.setInt(5, app.getNumDownloads());
                                                 prepStm.setDouble(6,app.getRating());
                                                 prepStm.setString(7,app.getLogo());
                                                 prepStm.setDouble(8, app.getVersion());
						prepStm.execute();
					}
				}	
				
				try (PreparedStatement prepStm = conn.prepareStatement("CREATE TABLE downloads (id int auto_increment primary key, customer varchar(30), contents varchar(255), status varchar(20),percentage int);")) {
					prepStm.execute();
				}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}