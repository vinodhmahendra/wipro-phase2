package com.simplilearn.workshop;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/Content2Servlet")
public class Content2Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public String loginurl = "SessionLoginServlet";
	

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		if ( session == null ) 
			response.sendRedirect(loginurl);
		else {
			String loggedIn = ( String ) session.getAttribute("loggedIn");
			if (!loggedIn.equals("true"))
				response.sendRedirect(loginurl);
		}
		//This is an authorized user, okay to display content
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<HTML>");
		out.println("<HEAD>");
		out.println("<TITLE>Welcome</TITLE>");
		out.println("</HEAD>");
		out.println("Welcome.");
		out.println("</BODY>");
		out.println("</HTML>");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
