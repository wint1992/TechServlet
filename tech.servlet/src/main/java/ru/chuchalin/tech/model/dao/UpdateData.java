package ru.chuchalin.tech.model.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.google.gson.Gson;

import ru.chuchalin.tech.model.*;

public class UpdateData {
	protected HibernateUtil dataSession;
	public UpdateData(HibernateUtil _hibernate) {
		dataSession = _hibernate;
	}
	
	public boolean saveCity(String jsonObj){
		try{
			return saveObject(new Gson().fromJson(jsonObj, City.class));
		}catch (Exception e) {
			return false;
		}
	}
	
	public boolean deleteCity(String jsonObj){
		try{
			return deleteObject(new Gson().fromJson(jsonObj, City.class));
		}catch (Exception e) {
			return false;
		}
	}
	
	public boolean saveMusicStyle(String jsonObj){
		try{
			return saveObject(new Gson().fromJson(jsonObj, EventMusicStyle.class));
		}catch (Exception e) {
			return false;
		}
	}
	
	public boolean deleteMusicStyle(String jsonObj){
		try{
			return deleteObject(new Gson().fromJson(jsonObj, EventMusicStyle.class));
		}catch (Exception e) {
			return false;
		}
	}
	
	public boolean saveEventAddress(String jsonObj){
		try{
			return saveObject(new Gson().fromJson(jsonObj, EventAddress.class));
		}catch (Exception e) {
			return false;
		}
	}
	
	public boolean deleteEventAddress(String jsonObj){
		try{
			return deleteObject(new Gson().fromJson(jsonObj, EventAddress.class));
		}catch (Exception e) {
			return false;
		}
	}
	
	public boolean saveEvent(String jsonObj){
		try{
			return saveObject(new Gson().fromJson(jsonObj, Event.class));
		}catch (Exception e) {
			return false;
		}
	}
	
	public boolean deleteEvent(String jsonObj){
		try{
			return deleteObject(new Gson().fromJson(jsonObj, Event.class));
		}catch (Exception e) {
			return false;
		}
	}
	
	private boolean saveObject (Object obj){
		boolean vResult = false;
		
		if (dataSession != null && dataSession.getSessionFactory() != null) {
			Session sf = null;
			Transaction tx = null;
			try {
				sf = dataSession.getSessionFactory().openSession();
				if (sf != null) {
					tx = sf.beginTransaction();
					sf.saveOrUpdate(obj);
					tx.commit();
					vResult = true;
				}
			} catch (Exception e) {
				if (tx != null)
					tx.rollback();
				e.printStackTrace();
			} finally {
				if (sf != null && sf.isOpen())
					sf.close();
			}
		}
		
		return vResult;
	}
	
	private boolean deleteObject (Object obj){
		boolean vResult = false;
		
		if (dataSession != null && dataSession.getSessionFactory() != null) {
			Session sf = null;
			Transaction tx = null;
			try {
				sf = dataSession.getSessionFactory().openSession();
				if (sf != null) {
					tx = sf.beginTransaction();
					sf.delete(obj);
					tx.commit();
					vResult = true;
				}
			} catch (Exception e) {
				if (tx != null)
					tx.rollback();
				e.printStackTrace();
			} finally {
				if (sf != null && sf.isOpen())
					sf.close();
			}
		}
		
		return vResult;
	}

}
