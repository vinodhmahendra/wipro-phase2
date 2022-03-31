package com.simplilearn.workshop;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.simplilearn.workshop.utils.StringUtil;


@WebServlet("/DataViewerServlet")
public class DataViewerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	public void init() {
		try {
			// load a driver
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("JDBC driver loaded");
		} catch (ClassNotFoundException e) {
			System.out.println(e.toString());
		}
	}
	
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<HTML>");
		out.println("<HEAD>");
		out.println("<TITLE>Display All Users </TITLE>");
		out.println("</HEAD>");
		out.println("<BODY>");
		out.println("<CENTER>");
		out.println("<BR><H2>Displaying All Users</H2>");
		out.println("<BR>");
		out.println("<BR>");
		out.println("<TABLE>");
		out.println("<TR>");
		out.println("<TH>First Name </TH>");
		out.println("<TH>Last Name </TH>");
		out.println("<TH>User Name </TH>");
		out.println("<TH>Password </TH>");
		out.println("</TR>");
		
		String sql = "select firstname, lastname, username, password" +
				" from users";
		String url = "jdbc:mysql://localhost:3306/studentdb?allowPublicKeyRetrieval=true&useSSL=false";
		String user = "root";
		String dbpassword = "admin";
		try {

			Connection con = DriverManager.getConnection(url, user, dbpassword);
			System.out.println("got connection");
			
			Statement s = con.createStatement();
			
			ResultSet rs = s.executeQuery(sql);
			
			while ( rs.next() ) {
				out.println("<TR>");
				out.println("<TD>" + StringUtil.encodeHtmlTag(rs.getString(1)) +"</TD>");
				out.println("<TD>" + StringUtil.encodeHtmlTag(rs.getString(2)) +"</TD>");
				out.println("<TD>" + StringUtil.encodeHtmlTag(rs.getString(3)) +"</TD>");
				out.println("<TD>" + StringUtil.encodeHtmlTag(rs.getString(4)) +"</TD>");
				out.println("</TR>");
			}
			rs.close();
			s.close();
			con.close();
		}catch(SQLException e) {
			System.out.println(e.toString());
		}catch(Exception e) {
			System.out.println(e.toString());
		}
		out.println("</TABLE>");
		out.println("</CENTER>");
		out.println("</BODY>");
		out.println("</HTML");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		doGet(request, response);
	}

}
