package ru.chuchalin.tech.servlet;

import java.util.Date;

import ru.chuchalin.tech.model.dao.Auth;
import ru.chuchalin.tech.model.dao.DBSession;
import ru.chuchalin.tech.model.dao.DataAccess;

public class MethodsFunctions {
	public static String getQueryResult(DataAccess DA, String req){
		return DA.getGraphQLSchema().jsonResult("query" + req);
	}
	
	public static String getRegistrationResult(DataAccess DA, String login, String password, String email){
		String queryResult;
		if (login == null || password == null)
			queryResult = "{\"errors\":[{\"message\":\"No login or password.\"}]}";
		else {
			boolean regResult = false;
			if (email == null)
				regResult = DA.getAuthAccess().registration(login, password);
			else
				regResult = DA.getAuthAccess().registration(login, password, email);
			queryResult = "{\"data\":[{\"result\": " + regResult + "}]}";
		}
		return queryResult;
	}
	
	public static String getRegistrationAdminResult(DataAccess DA, String login, String password){
		String queryResult;
		if (login == null || password == null)
			queryResult = "{\"errors\":[{\"message\":\"No login or password.\"}]}";
		else
			queryResult = "{\"data\":[{\"result\": " + DA.getAuthAccess().registrationAdmin(login, password)
					+ "}]}";
		return queryResult;
	}
	
	public static String getSessionID(DataAccess DA, String login, String password, String secret){
		String queryResult;
		if(secret != null){
			DBSession session = DA.getDBSessionAccess().getDBSession(secret);
			if(session != null)
				if (session.getIsActual().equals(1)) {
					queryResult = "{\"data\":{\"session_id\": \"" + secret + "\"}}";
				}else{
					session = DA.getDBSessionAccess().getActualDBSession(session.getAuthDataID());
					if(session != null){
						queryResult = "{\"data\":{\"session_id\": " + session.getAccessToken() + "}}";
					} 
					else queryResult = "{\"errors\":[{\"message\":\"Authorization is needed to access.\", \"code\":0}]}";
				}
			else queryResult = "{\"errors\":[{\"message\":\"Authorization is needed to access.\", \"code\":0}]}";
		}
		else if ((login == null || login.equals("")) && (password == null || password.equals("")) && secret == null){
			queryResult = "{\"errors\":[{\"message\":\"Authorization is needed to access.\", \"code\":0}]}";
		} 
		else if ((login == null || login.equals("") || password == null || password.equals("")) && secret == null)
			queryResult = "{\"errors\":[{\"message\":\"No login or password.\", \"code\":1}]}";
		else {
			Auth authData = DA.getAuthAccess().getAuth(login);
			if (authData != null) {
				String salt = authData.getSalt();
				if (salt != null && authData.getPasshash() != null && Auth.checkPassword(password, salt, authData.getPasshash())) {
					DBSession session = DA.getDBSessionAccess().getActualDBSession(authData.getAuthID());
					if (session == null){
						Date now = new Date();
						secret = DA.getDBSessionAccess().createDBSession(authData.getAuthID(), now);
						if (secret != null) {
							queryResult = "{\"data\":{\"session_id\": \"" + secret + "\"}}";
						} 
						else{
							queryResult = "{\"errors\":[{\"message\":\"Server error.\", \"code\":3}]}";
						}
					}
					else queryResult = "{\"data\":{\"session_id\": \"" + session.getAccessToken() + "\"}}";
				} 
				else queryResult = "{\"errors\":[{\"message\":\"Incorrect username or password.\", \"code\":2}]}";
			} 
			else queryResult = "{\"errors\":[{\"message\":\"Incorrect username or password.\", \"code\":2}]}";
		}
		return queryResult;
	}
	
	public static String getSaveCityResult(DataAccess DA, String jsonObj){
		String queryResult;
		if (jsonObj != null) 
			if (DA.getUpdater().saveCity(jsonObj)) queryResult = "{\"data\":{\"result\": \"OK\"}}";
			else queryResult = "{\"errors\":[{\"message\":\"Save error.\", \"code\":5}]}";
		else queryResult = "{\"errors\":[{\"message\":\"No Data to save.\", \"code\":4}]}";
		return queryResult;
	}
	
	public static String getSaveMusicStyleResult(DataAccess DA, String jsonObj){
		String queryResult;
		if (jsonObj != null) 
			if (DA.getUpdater().saveMusicStyle(jsonObj)) queryResult = "{\"data\":{\"result\": \"OK\"}}";
			else queryResult = "{\"errors\":[{\"message\":\"Save error.\", \"code\":5}]}";
		else queryResult = "{\"errors\":[{\"message\":\"No Data to save.\", \"code\":4}]}";
		return queryResult;
	}
	
	public static String getSaveEventAddressResult(DataAccess DA, String jsonObj){
		String queryResult;
		if (jsonObj != null) 
			if (DA.getUpdater().saveEventAddress(jsonObj)) queryResult = "{\"data\":{\"result\": \"OK\"}}";
			else queryResult = "{\"errors\":[{\"message\":\"Save error.\", \"code\":5}]}";
		else queryResult = "{\"errors\":[{\"message\":\"No Data to save.\", \"code\":4}]}";
		return queryResult;
	}
	
	public static String getSaveEventResult(DataAccess DA, String jsonObj){
		String queryResult;
		if (jsonObj != null) 
			if (DA.getUpdater().saveEvent(jsonObj)) queryResult = "{\"data\":{\"result\": \"OK\"}}";
			else queryResult = "{\"errors\":[{\"message\":\"Save error.\", \"code\":5}]}";
		else queryResult = "{\"errors\":[{\"message\":\"No Data to save.\", \"code\":4}]}";
		return queryResult;
	}
	
	public static String getDeleteCityResult(DataAccess DA, String jsonObj){
		String queryResult;
		if (jsonObj != null) 
			if (DA.getUpdater().deleteCity(jsonObj)) queryResult = "{\"data\":{\"result\": \"OK\"}}";
			else queryResult = "{\"errors\":[{\"message\":\"Save error.\", \"code\":5}]}";
		else queryResult = "{\"errors\":[{\"message\":\"No Data to save.\", \"code\":4}]}";
		return queryResult;
	}
	
	public static String getDeleteMusicStyleResult(DataAccess DA, String jsonObj){
		String queryResult;
		if (jsonObj != null) 
			if (DA.getUpdater().deleteMusicStyle(jsonObj)) queryResult = "{\"data\":{\"result\": \"OK\"}}";
			else queryResult = "{\"errors\":[{\"message\":\"Save error.\", \"code\":5}]}";
		else queryResult = "{\"errors\":[{\"message\":\"No Data to save.\", \"code\":4}]}";
		return queryResult;
	}
	
	public static String getDeleteEventAddressResult(DataAccess DA, String jsonObj){
		String queryResult;
		if (jsonObj != null) 
			if (DA.getUpdater().deleteEventAddress(jsonObj)) queryResult = "{\"data\":{\"result\": \"OK\"}}";
			else queryResult = "{\"errors\":[{\"message\":\"Save error.\", \"code\":5}]}";
		else queryResult = "{\"errors\":[{\"message\":\"No Data to save.\", \"code\":4}]}";
		return queryResult;
	}
	
	public static String getDeleteEventResult(DataAccess DA, String jsonObj){
		String queryResult;
		if (jsonObj != null) 
			if (DA.getUpdater().deleteEvent(jsonObj)) queryResult = "{\"data\":{\"result\": \"OK\"}}";
			else queryResult = "{\"errors\":[{\"message\":\"Save error.\", \"code\":5}]}";
		else queryResult = "{\"errors\":[{\"message\":\"No Data to save.\", \"code\":4}]}";
		return queryResult;
	}
}
