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

public class TechApiServlet extends HttpServlet
{
	private GraphQLSchemaModel ql;
	private FileWritter file;
	
    public static void main( String[] args ) {
    }
    
    @Override
    public void init(ServletConfig config) throws ServletException {
    	file = FileWritter.newReletiveFileWritter("logs", "TechApiServlet.log");
    	file.write("INFO-(" + ru.chuchalin.tech.model.TransformData.transformTimestamp(new Date()) +")- TechApiServlet.init() Begin");
    	GraphQLSchemaModel ql = new GraphQLSchemaModel(new HibernateUtil().init("194.87.214.229/TechCalDB", "a.chuchalin", "Achprocedure1@99.2"));
    	file.write("INFO-(" + ru.chuchalin.tech.model.TransformData.transformTimestamp(new Date()) +")- TechApiServlet.init() End");
    	super.init(config);
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	resp.setContentType("text/html");
		PrintWriter pw = resp.getWriter();
		file.write(ql.jsonResult("query {city {name}}"));
		
		pw.println("<p>" + file.getPath().toString() + "</p>");
		pw.println("<p>GET</p>");
		pw.close();
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	resp.setContentType("text/html");
		PrintWriter pw = resp.getWriter();
		file.write("POST");
    }
}
