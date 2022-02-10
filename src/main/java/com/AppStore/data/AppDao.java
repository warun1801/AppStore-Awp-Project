/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.AppStore.data;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.AppStore.domain.AppCategory;
import com.AppStore.domain.Application;
import com.AppStore.domain.Downloads;

public class AppDao {

	public AppDao() {
		DatabaseInitialize initialize = new DatabaseInitialize();
		initialize.initializeDatabase();
	}

        public List<Downloads> getAllDownloads() {
		List<Downloads> orders = new ArrayList<Downloads>();
		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/zenithdb","root","clasicoelmadrid10");
			 Statement stm = conn.createStatement();
			 ) {	
			
			ResultSet results = stm.executeQuery("SELECT * FROM downloads");
			
			while (results.next()) {
                                Downloads order = new Downloads();
                                order.setId(results.getLong("id"));
                                order.setStatus(results.getString("status"));
                                Map<Application,Double> orderMap = convertContentsToDownloadsMap(results.getString("contents"));
                                order.setContents(orderMap);
                                order.setCustomer(results.getString("customer"));
                                order.setPercentage(results.getInt("percentage"));
                                orders.add(order);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e); 
		}
		return orders;
	}

        
	private List<Application> buildApplication(ResultSet results) throws SQLException {
		List<Application> items = new ArrayList<>();
		while (results.next()) {
                        Application item = new Application();
                        item.setId(results.getInt("id"));
                        item.setDescription(results.getString("description"));
                        item.setName(results.getString("name"));
                        item.setNumDownloads(results.getInt("downloads"));
                        item.setRating(results.getDouble("rating"));
                        item.setLogo(results.getString("logo"));
                        item.setVersion(results.getDouble("version"));
                        item.setCategory(AppCategory.valueOf(results.getString("category")));
                        items.add(item);
		}
		return items;
	}

	public List<Application>  getFullApplication() {
		List<Application> items = null;
		try (	Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/zenithdb","root","clasicoelmadrid10");
				Statement stm = conn.createStatement();
				ResultSet results = stm.executeQuery("SELECT * FROM apps");
				) {	
			items = buildApplication(results);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return items;
	}

	public List<Application> find(String searchString) {
		List<Application> items = null;
		try (	Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/zenithdb","root","clasicoelmadrid10");
				PreparedStatement stm = conn.prepareStatement("SELECT * FROM apps WHERE name LIKE ? OR description LIKE ? OR category LIKE ? ");
				) {	

			stm.setString(1, "%" + searchString + "%");
			stm.setString(2, "%" + searchString + "%");
                        stm.setString(3, "%" + searchString + "%");

			ResultSet results = stm.executeQuery();
			items = buildApplication(results);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return items;
	}

	public Application getItem(int id) {
		List<Application> items = null;
		try (	Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/zenithdb","root","clasicoelmadrid10");
				PreparedStatement stm = conn.prepareStatement("SELECT * FROM apps WHERE id = ?");
				) {	

			stm.setInt(1, id);

			ResultSet results = stm.executeQuery();
			items = buildApplication(results);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return items.get(0);
	}


	public Downloads newDownloads(String customer) {
		Downloads order = new Downloads(); 
		order.setStatus("downloading");
		order.setCustomer(customer);
		try (	Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/zenithdb","root","clasicoelmadrid10");
				PreparedStatement stm = conn.prepareStatement("INSERT INTO downloads (status, customer) values (?,?)", Statement.RETURN_GENERATED_KEYS);
				) {	
			stm.setString(1, order.getStatus());
			stm.setString(2,  order.getCustomer());
			stm.execute();
			
			try(ResultSet generatedKeys = stm.getGeneratedKeys()) {
				generatedKeys.next();
		        order.setId(generatedKeys.getLong(1));		        
		    }
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return order;
	}

	private Map<Application,Double> convertContentsToDownloadsMap(String contents) {
		Map<Application,Double> orderMap = new HashMap<>();
		if (contents == null || contents.equals("")) {
			return orderMap;
		}
			String[] items = contents.split(":");
			for (int i = 0; i < items.length; i++) {
				String key = items[i].split(",")[0];
				String value = items[i].split(",")[1];
				Application item = getItem(Integer.valueOf(key));
				orderMap.put(item,Double.valueOf(value));
			}
		return orderMap;
	}

	private String convertDownloadsMapToContents(Map<Application,Double> orderMap) {
		String contents = "";
		if (orderMap.keySet().isEmpty()) {
			return contents;
		}
		for (Application item : orderMap.keySet() ) {
			contents = contents + item.getId() + "," + orderMap.get(item) + ":";
		}
		contents = contents.substring(0, contents.length()-1);
		return contents;
	}

	public void addToDownloads(Long id, Application item, Double version) {
		try (	Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/zenithdb","root","clasicoelmadrid10");
				Statement stm = conn.createStatement();
				ResultSet res = stm.executeQuery("SELECT * FROM downloads WHERE ID = " + id);
				PreparedStatement stmUpdate = conn.prepareStatement("UPDATE downloads SET contents = ? WHERE id = ?");
				) {	
			res.next();
			String contents = res.getString("contents");
			Map<Application, Double> orderMap = convertContentsToDownloadsMap(contents);
			if (orderMap.get(item) != null) {
				orderMap.put(item, version);
			}
			else {
				orderMap.put(item, version);
			}
			contents = convertDownloadsMapToContents(orderMap);
			stmUpdate.setString(1, contents);
			stmUpdate.setLong(2, id);
			stmUpdate.execute();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

        
        public Downloads getDownloads(Long id) {
		try (	Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/zenithdb","root","clasicoelmadrid10");
				Statement stm = conn.createStatement();
				ResultSet res = stm.executeQuery("SELECT * FROM downloads WHERE id = " + id);
				) {
			res.next();
			Map<Application,Double> orderMap = convertContentsToDownloadsMap(res.getString("contents"));
			Downloads order = new Downloads();
			order.setCustomer(res.getString("customer"));
			order.setId(res.getLong("id"));
			order.setStatus(res.getString("status"));
			order.setContents(orderMap);
			return order;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}
        
        public void updateDownloadsStatus(Long id, String status) {
		try (	Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/zenithdb","root","clasicoelmadrid10");
				Statement stm = conn.createStatement();
				PreparedStatement stmUpdate = conn.prepareStatement("UPDATE downloads SET status = ? WHERE id = ?");
				) {	
			stmUpdate.setString(1, status);
			stmUpdate.setLong(2, id);
			stmUpdate.execute();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}

