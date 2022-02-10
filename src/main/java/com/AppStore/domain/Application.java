/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.AppStore.domain;

/**
 *
 * @author HP
 */
public class Application {
	private int id;
	private String name;
	private String description;
	private AppCategory category;
	private int numDownloads;
        private Double rating;
        private String logo;
	private Double version;
        
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public AppCategory getCategory() {
		return category;
	}
	public void setCategory(AppCategory category) {
		this.category = category;
	}
        
        public int getNumDownloads() {
		return numDownloads;
	}
	public void setNumDownloads(int nd) {
		this.numDownloads = nd;
	}
        
        public Double getRating() {
		return rating;
	}
	public void setRating(Double rating) {
		this.rating = rating;
	}
        
        public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
        
        public Double getVersion(){
            return version;
        }
        public void setVersion(Double ver){
            this.version = ver;
        }
	
	public Application(int id, String name, String description, AppCategory category, int numDownloads,Double rating,String logo,Double ver) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.category = category;
		this.numDownloads = numDownloads;
                this.rating = rating;
                this.logo = logo;
                this.version = ver;
	}
	
	public Application() {}
	
        @Override
	public String toString() {
		return (this.category + "\t\t " + this.name + " \nRating: " + this.rating + "");
	}
}
