package ru.chuchalin.tech.model.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.jpa.QueryHints;

public class DBSession {
	protected HibernateUtil dataSession;

	protected Integer sessionID;
	protected Integer authDataID;
	protected String accessToken;
	protected Date createDateTime;
	protected Date expiresIn;
	protected Integer isActual;
	protected boolean fake;

	public DBSession(HibernateUtil _hibernate) {
		dataSession = _hibernate;
	}

	private DBSession() {
	}

	public HibernateUtil getDataSession() {
		return dataSession;
	}

	public void setDataSession(HibernateUtil dataSession) {
		this.dataSession = dataSession;
	}

	public Integer getSessionID() {
		return sessionID;
	}

	public void setSessionID(Integer sessionID) {
		this.sessionID = sessionID;
	}

	public Integer getAuthDataID() {
		return authDataID;
	}

	public void setAuthDataID(Integer authDataID) {
		this.authDataID = authDataID;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public Date getCreateDateTime() {
		return createDateTime;
	}

	public void setCreateDateTime(Date createDateTime) {
		this.createDateTime = createDateTime;
	}

	public Date getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(Date expiresIn) {
		this.expiresIn = expiresIn;
	}

	public Integer getIsActual() {
		return isActual;
	}

	public void setIsActual(Integer isActual) {
		this.isActual = isActual;
	}

	public DBSession getDBSession(String access_token) {
		DBSession dbSession = null;
		List resultList = new ArrayList();
		if (dataSession != null && dataSession.getSessionFactory() != null) {
			Session sf = null;
			try {
				sf = dataSession.getSessionFactory().openSession();
				if (sf != null)
					resultList = sf.createQuery("from DBSession where accessToken = '" + access_token + "'")
							.setHint(QueryHints.HINT_FETCH_SIZE, 10).list();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (sf != null && sf.isOpen())
					sf.close();
			}
		}
		if (resultList.size() > 0)
			dbSession = (DBSession) resultList.get(0);
		return dbSession;
	};
	
	public DBSession getActualDBSession(Integer authID) {
		DBSession dbSession = null;
		List resultList = new ArrayList();
		if (dataSession != null && dataSession.getSessionFactory() != null) {
			Session sf = null;
			try {
				sf = dataSession.getSessionFactory().openSession();
				if (sf != null)
					resultList = sf.createQuery("from DBSession where authDataID = " + authID + " and isActual = 1")
							.setHint(QueryHints.HINT_FETCH_SIZE, 10).list();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (sf != null && sf.isOpen())
					sf.close();
			}
		}
		if (resultList.size() > 0)
			dbSession = (DBSession) resultList.get(0);
		return dbSession;
	};

	public String createDBSession(Integer authID, Date createDate) {
		return createDBSession(authID, createDate, new GregorianCalendar(9999, 12, 30).getTime());
	}

	public String createDBSession(Integer authID, Date createDate, Date expires) {
		String vResult = null;
		DBSession session = new DBSession();
		String secret = Auth.generateToken(String.valueOf(createDate.getTime()));
		session.setAuthDataID(authID);
		session.setIsActual(1);
		session.setAccessToken(Auth.generateToken(secret));
		session.setCreateDateTime(createDate);
		session.setExpiresIn(expires);

		if (dataSession != null && dataSession.getSessionFactory() != null) {
			Session sf = null;
			Transaction tx = null;
			try {
				sf = dataSession.getSessionFactory().openSession();
				if (sf != null) {
					tx = sf.beginTransaction();
					sf.save(session);
					tx.commit();
					vResult = secret;
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
