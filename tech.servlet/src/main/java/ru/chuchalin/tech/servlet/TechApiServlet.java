package ru.chuchalin.tech.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Date;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import ru.chuchalin.tech.model.dao.GraphQLSchemaModel;
import ru.chuchalin.tech.model.dao.HibernateUtil;

public class TechApiServlet extends HttpServlet {
	private GraphQLSchemaModel ql;
	private FileWritter file;

	public static void main(String[] args) {
		TechApiServlet ts = new TechApiServlet();
		try {
			ts.init();
		} catch (Exception e) {
		}
	}
	@Override
	public void init() throws ServletException {
		super.init();
		file = FileWritter.newReletiveFileWritter("logs", "TechApiServlet.log");
		file.write("INFO-(" + ru.chuchalin.tech.model.TransformData.transformTimestamp(new Date()) + ")- TechApiServlet.init() Begin");
		try {
			file.write("INFO-(" + ru.chuchalin.tech.model.TransformData.transformTimestamp(new Date()) + ")- HibernateUtil START");
			ql = new GraphQLSchemaModel(new HibernateUtil().init("194.87.214.229/TechCalDB", "a.chuchalin", "Achprocedure1@99.2", file));
			file.write("INFO-(" + ru.chuchalin.tech.model.TransformData.transformTimestamp(new Date()) + ")- GraphQLSchemaModel created");
			
			file.write("INFO-(" + ru.chuchalin.tech.model.TransformData.transformTimestamp(new Date()) + ")- TechApiServlet.init() End");
		} catch (Exception e) {
			//StringBuffer sb = new StringBuffer();
			//FileWritter.printStackTrace(sb, e);
			file.write("INFO-(" + ru.chuchalin.tech.model.TransformData.transformTimestamp(new Date()) + ")- init() EXCEPTION: " );

		}
		file.write("init END");
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String query = req.getParameter("query");
		if (query != null)
			file.write("INFO-(" + ru.chuchalin.tech.model.TransformData.transformTimestamp(new Date()) + ")- Request: "
					+ query);
		else
			file.write("INFO-(" + ru.chuchalin.tech.model.TransformData.transformTimestamp(new Date())
					+ ")- Request: null");

		resp.setContentType("text/json");
		PrintWriter pw = resp.getWriter();
		String queryResult = ql.jsonResult("query" + query);
		file.write("INFO-(" + ru.chuchalin.tech.model.TransformData.transformTimestamp(new Date()) + ")- Response: " + queryResult);
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

		resp.setContentType("text/json");
		PrintWriter pw = resp.getWriter();
		String queryResult = ql.jsonResult("query" + query);
		file.write("INFO-(" + ru.chuchalin.tech.model.TransformData.transformTimestamp(new Date()) + ")- Response: " + queryResult);
		pw.println(queryResult);
		pw.close();
	}
}
