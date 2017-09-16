package ru.chuchalin.tech.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ru.chuchalin.tech.model.dao.Auth;
import ru.chuchalin.tech.model.dao.DBSession;
import ru.chuchalin.tech.model.dao.DataAccess;

public class TechApiServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public DataAccess DA;
	private FileWritter file;

	public static void main(String[] args) {
		DataAccess DA = new DataAccess("194.87.214.229/TechCalDB", "a.chuchalin", "Achprocedure1@99.2");
		Auth authData = DA.getAuthAccess().getAuth("11111111");
		if (authData != null) {
			System.out.println("Login: " + authData.getLogin());
			System.out.println("PassHash: " + authData.getPasshash());
			System.out.println("Email: " + authData.getEmail());
			System.out.println("Salt: " + authData.getSalt());
			System.out.println("Role: " + authData.getRole());
		}
	}

	@Override
	public void init() throws ServletException {
		super.init();
		file = FileWritter.newReletiveFileWritter("logs", "TechApiServlet.log");
		try {
			DA = new DataAccess("194.87.214.229/TechCalDB", "a.chuchalin", "Achprocedure1@99.2");
		} catch (Exception e) {
			StringBuffer sb = new StringBuffer();
			FileWritter.printStackTrace(sb, e);
			file.write("INFO-(" + ru.chuchalin.tech.model.TransformData.transformTimestamp(new Date())
					+ ")- init() EXCEPTION: " + sb.toString());
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String apiMethod = req.getParameter("apiMethod");
		String queryResult = "{\"errors\":[{\"message\":\"Invalid Method.\"}]}";
		Cookie[] cks = req.getCookies();
		String secretFromCookie;
		if (cks != null)
			for (int i = 0; i < cks.length; i++)
				if (cks[i].getName().equals("session_id"))
					secretFromCookie = cks[i].getValue();

		if (apiMethod != null)
			switch (apiMethod) {
			case "query": {
				String query = req.getParameter("query");
				queryResult = DA.getGraphQLSchema().jsonResult("query" + query);
				break;
			}
			case "registration": {
				String login = req.getParameter("login");
				String password = req.getParameter("password");
				String email = req.getParameter("email");
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
				break;
			}
			case "registrationAdmin": {
				String login = req.getParameter("login");
				String password = req.getParameter("password");
				if (login == null || password == null)
					queryResult = "{\"errors\":[{\"message\":\"No login or password.\"}]}";
				else
					queryResult = "{\"data\":[{\"result\": " + DA.getAuthAccess().registrationAdmin(login, password)
							+ "}]}";
				break;
			}
			case "access": {
				String login = req.getParameter("login");
				String password = req.getParameter("password");
				String secret = req.getParameter("session_id");
				if (login == null || password == null)
					queryResult = "{\"errors\":[{\"message\":\"No login or password.\"}]}";
				else {
					Auth authData = DA.getAuthAccess().getAuth(login);
					if (authData != null) {
						String salt = authData.getSalt();
						if (salt != null && authData.getPasshash() != null
								&& Auth.checkPassword(password, salt, authData.getPasshash())) {
							if (secret == null) {
								Date now = new Date();
								secret = DA.getDBSessionAccess().createDBSession(authData.getAuthID(), now);
								if (secret != null) {
									Cookie ck = new Cookie("session_id", secret);
									resp.addCookie(ck);
									// !!!!!!!!!!!! return SECRET!!!!!!!!!
								} else
									queryResult = "{\"errors\":[{\"message\":\"Server error.\"}]}";
							} else {
								DBSession session = DA.getDBSessionAccess().getDBSession(secret);
								if (session.getIsActual().equals(1)) {
									Cookie ck = new Cookie("session_id", secret);
									resp.addCookie(ck);
									// !!!!!!!!!!!! return SECRET!!!!!!!!!
								} else {
									Cookie ck = new Cookie("session_id", session.getAccessToken());
									resp.addCookie(ck);
									// !!!!!!!!!!!! return
									// session.getAccessToken!!!!!!!!!
								}
							}
							// queryResult = "{\"data\":[{\"access_token\": "
							// + DA.getAuthAccess().registrationAdmin(login,
							// password) + "}]}";
						} else
							queryResult = "{\"errors\":[{\"message\":\"Incorrect username or password.\"}]}";
					} else
						queryResult = "{\"errors\":[{\"message\":\"Incorrect username or password.\"}]}";
				}
				break;
			}
			}

		resp.setContentType("text/json; charset=UTF-8");
		PrintWriter pw = resp.getWriter();
		pw.println(queryResult);
		pw.close();
	}
}
