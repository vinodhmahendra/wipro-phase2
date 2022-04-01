package com.simplilearn.workshop;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DeleteServlet
 */
@WebServlet("/DeleteServlet")
public class DeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		int recordAffected = 0 ;
		String url = "jdbc:mysql://localhost:3306/studentdb?allowPublicKeyRetrieval=true&useSSL=false";
		String user = "root";
		String dbpassword = "admin";
		try {

			Connection con = DriverManager.getConnection(url, user, dbpassword);
			System.out.println("got connection");
			Statement s = con.createStatement();
			String id = request.getParameter("id");
			String sql = "DELETE FROM users WHERE id=" + id;
			recordAffected = s.executeUpdate(sql);
			s.close();
			con.close();
		}catch(SQLException e) {
			
		}catch  (Exception e) {
			
		}
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<HTML>");
		out.println("<HEAD>");
		out.println("<TITLE> Deleting A Record</TITLE>");
		out.println("</HEAD>");
		out.println("<BODY>");
		out.println("<CENTER>");
		if ( recordAffected == 1 ) {
			out.println("<p> Record deleted. </p>");
		}else {
			out.println("<p>Error deleting record . </p>");
		}
		out.println("<A HREF=SearchServlet>Go back</A> to the search page.");
	}

}
