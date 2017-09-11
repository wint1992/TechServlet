package ru.chuchalin.tech.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ru.chuchalin.tech.model.dao.GraphQLSchemaModel;
import ru.chuchalin.tech.model.dao.HibernateUtil;

public class TechApiServlet extends HttpServlet {
	private GraphQLSchemaModel ql;
	private FileWritter file;

	public static void main(String[] args) {
		TechApiServlet ts = new TechApiServlet();
		try {
			ts.init(null);
		} catch (Exception e) {
		}
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		file = FileWritter.newReletiveFileWritter("logs", "TechApiServlet.log");
		file.write("init");
		try {
			file.write("INFO-(" + ru.chuchalin.tech.model.TransformData.transformTimestamp(new Date())
					+ ")- TechApiServlet.init() Begin");
			ql = new GraphQLSchemaModel(
					new HibernateUtil().init("194.87.214.229/TechCalDB", "a.chuchalin", "Achprocedure1@99.2"));
			file.write("INFO-(" + ru.chuchalin.tech.model.TransformData.transformTimestamp(new Date())
					+ ")- TechApiServlet.init() End");
		} catch (Exception e) {
		}
		// super.init(config);
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
		// String queryResult = ql.jsonResult("query" + query);
		// file.write("INFO-(" +
		// ru.chuchalin.tech.model.TransformData.transformTimestamp(new Date())
		// + ")- Response: "
		// + queryResult);
		// pw.println(queryResult);
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
		// String queryResult = ql.jsonResult("query" + query);
		// file.write("INFO-(" +
		// ru.chuchalin.tech.model.TransformData.transformTimestamp(new Date())
		// + ")- Response: "
		// + queryResult);
		// pw.println(queryResult);
		pw.close();
	}
}
