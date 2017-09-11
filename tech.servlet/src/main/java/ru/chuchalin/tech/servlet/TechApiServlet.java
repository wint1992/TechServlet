package ru.chuchalin.tech.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TechApiServlet extends HttpServlet
{
	private FileWritter file = FileWritter.newReletiveFileWritter("logs", "TechApiServlet.log");
	
    public static void main( String[] args ) {
    }
    
    @Override
    public void init(ServletConfig config) throws ServletException {
    	super.init(config);
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	resp.setContentType("text/html");
		PrintWriter pw = resp.getWriter();
		file.write("GET");
		
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
