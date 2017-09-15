package ru.chuchalin.tech.servlet;

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
			file.write("INFO-(" + ru.chuchalin.tech.model.TransformData.transformTimestamp(new Date()) + ")- init() EXCEPTION: " + sb.toString());
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {		
		String apiMethod = req.getParameter("apiMethod");
		String queryResult = "{\"errors\":[{\"message\":\"Invalid Method\"}]}";
		
		if (apiMethod != null)
		switch (apiMethod){
			case "query":{
				String query = req.getParameter("query");
				queryResult = DA.getGraphQLSchema().jsonResult("query" + query);
				break;
			}
			case "registration":{
				String login = req.getParameter("login");
				String password = req.getParameter("password");
				String email = req.getParameter("email");
				if (login == null || password == null) queryResult = "{\"errors\":[{\"message\":\"No login or password\"}]}";
				else {
					boolean regResult = false;
					if (email == null) regResult = DA.getAuth().registration(login, password);
					else regResult = DA.getAuth().registration(login, password, email);
					queryResult = "{\"data\":[{\"result\": " + regResult + "}]}";
				}
				break;
			}
			case "registrationAdmin":{
				String login = req.getParameter("login");
				String password = req.getParameter("password");
				if (login == null || password == null) queryResult = "{\"errors\":[{\"message\":\"No login or password\"}]}";
				else queryResult = "{\"data\":[{\"result\": " + DA.getAuth().registration(login, password) + "}]}";
				break;
			}
		}

		resp.setContentType("text/json; charset=UTF-8");
		PrintWriter pw = resp.getWriter();
		pw.println(queryResult);
		pw.close();
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String query = req.getParameter("query");
		if (query != null)
			file.write("INFO-(" + ru.chuchalin.tech.model.TransformData.transformTimestamp(new Date()) + ")- Request: "
					+ query);
		else
			file.write("INFO-(" + ru.chuchalin.tech.model.TransformData.transformTimestamp(new Date())
					+ ")- Request: null");

		resp.setContentType("text/json; charset=UTF-8");
		PrintWriter pw = resp.getWriter();
		String queryResult = DA.getGraphQLSchema().jsonResult("query" + query);
		file.write("INFO-(" + ru.chuchalin.tech.model.TransformData.transformTimestamp(new Date()) + ")- Response: "
				+ queryResult);
		pw.println(queryResult);
		pw.close();
	}
}
