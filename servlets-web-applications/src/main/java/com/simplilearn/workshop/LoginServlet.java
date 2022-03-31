package com.simplilearn.workshop;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		sendLoginForm(response, false);
	}

	private void sendLoginForm(HttpServletResponse response, boolean withErrorMessage)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<head>");
		out.println("<title>Login</title>");
		out.println("</head>");
		out.println("<body>");
		if ( withErrorMessage ) 
			out.println("Login Failed , Please try again. <BR>");
		
		out.println("<br>");
		out.println("<br>Please enter your user name and password.");
		out.println("<br> <form method=post>");
		out.println("<br>Username: <input type=text name=userName>");
		out.println("<br>Password: <input type=password name=password>");
		out.println("<br> <input type=submit value=Submit>");
		out.println("</form>");
		out.println("</body>");
		out.println("</html>");

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String userName = request.getParameter("userName");
		String password = request.getParameter("password");

		if (userName != null && password != null && userName.equals("simplilearn") && password.equals("simplilearn")) {
//			response.sendRedirect("http://www.simplilearn.com");
			
			RequestDispatcher rd = request.getRequestDispatcher("WelcomeServlet");
			rd.forward(request, response);
		}
		else {
			sendLoginForm(response, true);
//			response.sendError(response.SC_FORBIDDEN, "Login Failed");
		}

	}

}
