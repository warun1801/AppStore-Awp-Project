/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.AppStore.domain;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author HP
 */
public class Downloads {
    private Long id;
    private Map<Application,Double> currentApp = new HashMap<>();
    private String status;
    private String customer;
    private int percentage;
    
    
    public void addToDownloads(Application app, Double ver) {
		Double currentVersion = 0d;
		if (currentApp.get(app) != null) 
                    currentVersion = currentApp.get(app);
		currentApp.put(app, currentVersion);
    }
    
    public String getStatus() {
		return status;
    }
    
    public void setStatus(String status){
        this.status = status;
    }
    @Override
    public String toString() {
		StringBuilder sb = new StringBuilder();
			for (Application app : currentApp.keySet()) {
				sb.append(app.getName()).append(" :VER(").append(currentApp.get(app)).append(")<br/>");
			}
		return sb.toString();
    }
	
    public Long getId() {
            return id;
    }

    public void setId(Long id) {
            this.id = id;
    }

    public void setCustomer(String customer) {
            this.customer = customer;
    }

    public String getCustomer() {
            return customer;
    }

    public void setContents(Map<Application, Double> downloads) {
            this.currentApp=downloads;
    }
    
    public int getPercentage(){
        return percentage;
    }
    
    public void setPercentage(int percentage){
        this.percentage = percentage;
    }	
}

