package com.AppStore.data;

public class DataConnection {
	
	private static AppDao appDao;
	
	public static AppDao getAppDao() {
		if (appDao == null) {
			appDao = new AppDao();
		}
		return appDao;
	}

}
