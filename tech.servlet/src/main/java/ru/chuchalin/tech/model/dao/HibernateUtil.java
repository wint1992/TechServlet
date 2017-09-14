package ru.chuchalin.tech.model.dao;

import java.nio.file.Paths;
import java.util.Date;

import org.hibernate.SessionFactory;
import org.hibernate.service.*;

import ru.chuchalin.tech.servlet.FileWritter;

import org.hibernate.cfg.Configuration;

public class HibernateUtil {
	private SessionFactory sessionFactory;

	public HibernateUtil init(String url, String login, String pass, FileWritter file) {
		HibernateUtil vResult = null;
		try {
			file.write("INFO-(" + ru.chuchalin.tech.model.TransformData.transformTimestamp(new Date()) + ")- HibernateUtil sessionFactory START");
//			sessionFactory = new Configuration()//
//					//.setProperties(System.getProperties())
//					.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver")
//					.setProperty("hibernate.connection.url", "jdbc:postgresql://" + url)
//					.setProperty("hibernate.connection.username", login)
//					.setProperty("hibernate.connection.password", pass)
//					.setProperty("hibernate.connection.pool_size", "1")//
//					.setProperty("hibernate.c3p0.min_size", "5")
//					.setProperty("hibernate.c3p0.max_size", "20").setProperty("hibernate.c3p0.timeout", "500")
//					.setProperty("hibernate.c3p0.max_statements", "50")//
//					.setProperty("hibernate.jdbc.batch_size", "10")//
//					.setProperty("hibernate.jdbc.fetch_size", "100")//
//					.setProperty("hibernate.id.new_generator_mappings", "true")//
//					.setProperty("hibernate.connection.release_mode", "after_transaction")//
//					.setProperty("hibernate.connection.handling_mode", "DELAYED_ACQUISITION_AND_RELEASE_AFTER_TRANSACTION")//
//					.setProperty("hibernate.current_session_context_class", "thread")//
//					.addClass(ru.chuchalin.tech.model.City.class)//
//					.addClass(ru.chuchalin.tech.model.EventMusicStyle.class)//
//					.addClass(ru.chuchalin.tech.model.EventAddress.class)//
//					.addClass(ru.chuchalin.tech.model.Profile.class)//
//					.addClass(ru.chuchalin.tech.model.Event.class)//
//					.buildSessionFactory();
//					;
			sessionFactory = new Configuration().configure().buildSessionFactory();
			file.write("INFO-(" + ru.chuchalin.tech.model.TransformData.transformTimestamp(new Date()) + ")- HibernateUtil sessionFactory END");
			//con.configure();
			//file.write("INFO-(" + ru.chuchalin.tech.model.TransformData.transformTimestamp(new Date()) + ")- HibernateUtil Configure END");
			//sessionFactory = con.buildSessionFactory();
			//file.write("INFO-(" + ru.chuchalin.tech.model.TransformData.transformTimestamp(new Date()) + ")- HibernateUtil buildSessionFactory END");
			vResult = this;
		} catch (Throwable  e) {
			StringBuffer sb = new StringBuffer();
			FileWritter.printStackTrace(sb, e);
			file.write("INFO-(" + ru.chuchalin.tech.model.TransformData.transformTimestamp(new Date()) + ")- HibernateUtil sessionFactory EXCEPTION: "+ sb.toString());
			//e.printStackTrace();
			vResult = null;
			throw e;			
		}
		return vResult;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
}
