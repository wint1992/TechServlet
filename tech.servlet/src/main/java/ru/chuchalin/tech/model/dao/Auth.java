package ru.chuchalin.tech.model.dao;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.jpa.QueryHints;

public class Auth {
	protected HibernateUtil dataSession;

	protected Integer authID;
	protected String login;
	protected String email;
	protected Date registrationDatetime;
	protected String passhash;
	protected String salt;
	protected Integer role;
	protected boolean confirm;
	protected boolean fake;

	public Auth(HibernateUtil _hibernate) {
		dataSession = _hibernate;
	}

	private Auth() {
	}

	public Integer getAuthID() {
		return authID;
	}

	public void setAuthID(Integer authID) {
		this.authID = authID;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getRegistrationDatetime() {
		return registrationDatetime;
	}

	public void setRegistrationDatetime(Date registrationDatetime) {
		this.registrationDatetime = registrationDatetime;
	}

	public String getPasshash() {
		return passhash;
	}

	public void setPasshash(String passhash) {
		this.passhash = passhash;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public Integer getRole() {
		return role;
	}

	public void setRole(Integer role) {
		this.role = role;
	}

	public boolean isConfirm() {
		return confirm;
	}

	public void setConfirm(boolean confirm) {
		this.confirm = confirm;
	}

	private static String getSHA512PassLP(String passwordToHash, String salt) {
		String generatedPassword = null;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-512");
			md.update(passwordToHash.getBytes("UTF-8"));
			md.update(salt.getBytes("UTF-8"));
			byte[] bytes = md.digest(new String("24081992").getBytes("UTF-8"));
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < bytes.length; i++) {
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			generatedPassword = sb.toString();
		} catch (Exception e) {
		}
		return generatedPassword;
	}

	public boolean registrationAdmin(String newLogin, String newPass) {
		boolean vResult = false;
		Date regDatetime = new Date();
		String salt = String.valueOf(regDatetime.getTime());
		Auth auth = new Auth();
		auth.setConfirm(false); // Email is confirmed
		auth.setLogin(newLogin);
		auth.setPasshash(getSHA512PassLP(newPass, salt));
		auth.setSalt(salt);
		auth.setRegistrationDatetime(regDatetime);
		auth.setRole(1);

		if (dataSession != null && dataSession.getSessionFactory() != null) {
			Session sf = null;
			Transaction tx = null;
			try {
				sf = dataSession.getSessionFactory().openSession();
				if (sf != null) {
					tx = sf.beginTransaction();
					sf.save(auth);
					tx.commit();
					vResult = true;
				}
			} catch (Throwable e) {
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

	public boolean registration(String newLogin, String newPass) {
		boolean vResult = false;
		Date regDatetime = new Date();
		String salt = String.valueOf(regDatetime.getTime());
		Auth auth = new Auth();
		auth.setConfirm(false); // Email is confirmed
		auth.setLogin(newLogin);
		auth.setPasshash(getSHA512PassLP(newPass, salt));
		auth.setSalt(salt);
		auth.setRegistrationDatetime(regDatetime);
		auth.setRole(2);

		if (dataSession != null && dataSession.getSessionFactory() != null) {
			Session sf = null;
			Transaction tx = null;
			try {
				sf = dataSession.getSessionFactory().openSession();
				if (sf != null) {
					tx = sf.beginTransaction();
					sf.save(auth);
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

	public boolean registration(String newLogin, String newPass, String newEmail) {
		boolean vResult = false;
		Date regDatetime = new Date();
		String salt = String.valueOf(regDatetime.getTime());
		Auth auth = new Auth();
		auth.setConfirm(false); // Email is confirmed
		auth.setLogin(newLogin);
		auth.setPasshash(getSHA512PassLP(newPass, salt));
		auth.setSalt(salt);
		auth.setRegistrationDatetime(regDatetime);
		auth.setRole(2);
		auth.setEmail(newEmail);

		if (dataSession != null && dataSession.getSessionFactory() != null) {
			Session sf = null;
			Transaction tx = null;
			try {
				sf = dataSession.getSessionFactory().openSession();
				if (sf != null) {
					tx = sf.beginTransaction();
					sf.save(auth);
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

	public Auth getAuth(String inpLoginOrEmail) {
		Auth auth = null;
		List resultList = new ArrayList();
		if (dataSession != null && dataSession.getSessionFactory() != null) {
			Session sf = null;
			try {
				sf = dataSession.getSessionFactory().openSession();
				if (sf != null)
					resultList = sf.createQuery(
							"from Auth where login = '" + inpLoginOrEmail + "' or email = '" + inpLoginOrEmail + "'")
							.setHint(QueryHints.HINT_FETCH_SIZE, 10).list();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (sf != null && sf.isOpen())
					sf.close();
			}
		}
		if (resultList.size() > 0)
			auth = (Auth) resultList.get(0);
		return auth;
	};

	public static boolean checkPassword(String pass, String salt, String passHash) {
		return getSHA512PassLP(pass, salt).equals(passHash) ? true : false;
	}

	public static String generateToken(String secret) {
		return getSHA512PassLP(secret, "");
	}
}
