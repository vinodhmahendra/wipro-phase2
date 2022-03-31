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

@WebServlet("/SearchServlet")
public class SearchServlet extends HttpServlet {

	private String keyword = "";
	
	void sendSearchResult(HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		String url = "jdbc:mysql://localhost:3306/studentdb?allowPublicKeyRetrieval=true&useSSL=false";
		String user = "root";
		String dbpassword = "admin";
		try {

			Connection con = DriverManager.getConnection(url, user, dbpassword);
			System.out.println("got connection");
			Statement s = con.createStatement();
			
			out.println("<TABLE>");
			out.println("<TR>");
			out.println("<TH>First Name </TH>");
			out.println("<TH>Last Name </TH>");
			out.println("<TH>User Name </TH>");
			out.println("<TH>Password </TH>");
			out.println("</TR>");
			
			String sql = "select firstname, lastname, username,password" +
						" FROM users" +
						" WHERE firstname LIKE '%" + StringUtil.fixSqlFieldValue(keyword)+"%'"+
						" OR lastname LIKE '%" + StringUtil.fixSqlFieldValue(keyword) +"%'";
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
			
		}catch(Exception e) {
			
		}
		out.println("</TABLE>");
	}

	private void sendSearchForm(HttpServletResponse response) throws IOException {

		PrintWriter out = response.getWriter();
		out.println("<BR><H2>Search Form</H2>");
		out.println("<BR> Please enter the first name, last name or part of any.");
		out.println("<BR>");
		out.println("<BR> <FORM METHOD=POST>");
		out.println("Name: <INPUT TYPE=TEXT Name=keyword");
		out.println(" VALUE=\"" + StringUtil.encodeHtmlTag(keyword ) +"\"");
		out.println(">");
		out.println("<INPUT TYPE=SUBMIT>");
		out.println("</FORM>");
		out.println("<BR>");
		out.println("<BR>");
	}

	public void init() {
		try {
			// load a driver
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("JDBC driver loaded");
		} catch (ClassNotFoundException e) {
			System.out.println(e.toString());
		}
	}

	private void sendPageHeader(HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<HTML>");
		out.println("<HEAD>");
		out.println("<TITLE>Displaying Selected Record(s) </TITLE>");
		out.println("</HEAD>");
		out.println("<BODY>");
		out.println("<CENTER>");
	}

	private void sendPageFooter(HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		out.println("</CENTER>");
		out.println("</BODY>");
		out.println("</HTML>");
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		sendPageHeader(response);
		sendSearchForm(response);
		sendPageFooter(response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		keyword = request.getParameter("keyword");
		sendPageHeader(response);
		sendSearchForm(response);
		sendSearchResult(response);
		sendPageFooter(response);
	}

}
