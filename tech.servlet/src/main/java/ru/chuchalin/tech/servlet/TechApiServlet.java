package ru.chuchalin.tech.servlet;

import static ru.chuchalin.tech.servlet.MethodsFunctions.getDeleteCityResult;
import static ru.chuchalin.tech.servlet.MethodsFunctions.getDeleteEventAddressResult;
import static ru.chuchalin.tech.servlet.MethodsFunctions.getDeleteEventResult;
import static ru.chuchalin.tech.servlet.MethodsFunctions.getDeleteMusicStyleResult;
import static ru.chuchalin.tech.servlet.MethodsFunctions.getQueryResult;
import static ru.chuchalin.tech.servlet.MethodsFunctions.getRegistrationAdminResult;
import static ru.chuchalin.tech.servlet.MethodsFunctions.getRegistrationResult;
import static ru.chuchalin.tech.servlet.MethodsFunctions.getSaveCityResult;
import static ru.chuchalin.tech.servlet.MethodsFunctions.getSaveEventAddressResult;
import static ru.chuchalin.tech.servlet.MethodsFunctions.getSaveEventResult;
import static ru.chuchalin.tech.servlet.MethodsFunctions.getSaveMusicStyleResult;
import static ru.chuchalin.tech.servlet.MethodsFunctions.getSessionID;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ru.chuchalin.tech.model.dao.DataAccess;

public class TechApiServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public DataAccess DA;
	private FileWritter file;

	public static void main(String[] args) {
		// EventAddress newEventAddress = new Gson().fromJson("{\"place\":\"Some
		// place\",\"city\":{\"name\":\"Москва\"}}", EventAddress.class);
		// System.out.println(getQueryResult(DA, req.getParameter("query")));
		// new Gson()
		DataAccess DA = new DataAccess("194.87.214.229/TechCalDB", "a.chuchalin", "Achprocedure1@99.2");
		System.out.println(getQueryResult(DA, "{event{eventID}}"));
		// Auth authData = DA.getAuthAccess().getAuth("11111111");
		// if (authData != null) {
		// System.out.println("Login: " + authData.getLogin());
		// System.out.println("PassHash: " + authData.getPasshash());
		// System.out.println("Email: " + authData.getEmail());
		// System.out.println("Salt: " + authData.getSalt());
		// System.out.println("Role: " + authData.getRole());
		// }
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
		String queryResult = "{\"errors\":[{\"message\":\"Invalid Method.\", \"code\":-1}]}";

		if (apiMethod != null)
			switch (apiMethod) {
			case "query": {
				queryResult = getQueryResult(DA, req.getParameter("query"));
				break;
			}
			case "registration": {
				queryResult = getRegistrationResult(DA, req.getParameter("login"), req.getParameter("password"),
						req.getParameter("email"));
				break;
			}
			case "registrationAdmin": {
				queryResult = getRegistrationAdminResult(DA, req.getParameter("login"), req.getParameter("password"));
				break;
			}
			case "access": {
				queryResult = getSessionID(DA, req.getParameter("login"), req.getParameter("password"),
						req.getParameter("session_id"));
				break;
			}

			case "saveCity": {
				queryResult = getSaveCityResult(DA, req.getParameter("inputObject"));
				break;
			}
			case "saveMusicStyle": {
				queryResult = getSaveMusicStyleResult(DA, req.getParameter("inputObject"));
				break;
			}
			case "saveEventAddress": {
				queryResult = getSaveEventAddressResult(DA, req.getParameter("inputObject"));
				break;
			}
			case "saveEvent": {
				queryResult = getSaveEventResult(DA, req.getParameter("inputObject"));
				break;
			}
			case "deleteCity": {
				queryResult = getDeleteCityResult(DA, req.getParameter("inputObject"));
				break;
			}
			case "deleteMusicStyle": {
				queryResult = getDeleteMusicStyleResult(DA, req.getParameter("inputObject"));
				break;
			}
			case "deleteEventAddress": {
				queryResult = getDeleteEventAddressResult(DA, req.getParameter("inputObject"));
				break;
			}
			case "deleteEvent": {
				queryResult = getDeleteEventResult(DA, req.getParameter("inputObject"));
				break;
			}
			}

		resp.setContentType("text/json; charset=UTF-8");
		resp.addHeader("Access-Control-Allow-Origin", "*");
		PrintWriter pw = resp.getWriter();
		pw.println(queryResult);
		pw.close();
	}
}
