package com.simplilearn.workshop;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ContentServlet")
public class ContentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public String loginUrl = "CookieLoginServlet";

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		// read the cookies
		Cookie[] cookies = request.getCookies();
		
		int length = cookies.length;
		
		String userName = null;
		String password = null;
		
		for ( int i = 0 ; i < length ;i++) {
			Cookie cookie = cookies[i];
			if ( cookie.getName().equals("userName"))
				userName = cookie.getValue();
			else if (cookie.getName().equals("password"))
				password = cookie.getValue();
		}
		
		if (userName == null || password == null || !CookieLoginServlet.login(userName,password))
			response.sendRedirect(loginUrl);
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<HTML>");
		out.println("<HEAD>");
		out.println("<TITLE>Welcome</TITLE>");
		out.println("</HEAD>");
		out.println("<BODY>");
		out.println("Welcome.");
		out.println("</BODY>");
		out.println("</HTML>");
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

}
