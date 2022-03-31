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

@WebServlet("/RegistrationServlet")
public class RegistrationServlet extends HttpServlet {

	public void init() {
		try {
			// load a driver
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("JDBC driver loaded");
		} catch (ClassNotFoundException e) {
			System.out.println(e.toString());
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		sendPageHeader(response);

		// read the payload
		firstName = request.getParameter("firstName");
		lastName = request.getParameter("lastName");
		userName = request.getParameter("userName");
		password = request.getParameter("password");

		boolean error = false;
		String message = null;

		String url = "jdbc:mysql://localhost:3306/studentdb?allowPublicKeyRetrieval=true&useSSL=false";
		String user = "root";
		String dbpassword = "admin";
		try {

			Connection con = DriverManager.getConnection(url, user, dbpassword);
			System.out.println("got connection");

			Statement s = con.createStatement();

			String sql = "select username from users" + " where username='" + StringUtil.fixSqlFieldValue(userName)
					+ "'";

			ResultSet rs = s.executeQuery(sql);

			if (rs.next()) {
				rs.close();
				message = "The user name <B>" + StringUtil.encodeHtmlTag(userName)
						+ "</B> has been taken. Please selecct another name.";
				error = true;
			} else {
				rs.close();
				sql = "INSERT into users" 
						+ " (firstname, lastname, username, password)" +
						" values" + 
						" ('"+ StringUtil.fixSqlFieldValue(firstName) + "'," + 
						" '" + StringUtil.fixSqlFieldValue(lastName) + "'," + 
						" '" + StringUtil.fixSqlFieldValue(userName) + "'," + 
						" '"+ StringUtil.fixSqlFieldValue(password) + "')";
				int i = s.executeUpdate(sql);
				if (i == 1) {
					message = " Successfully added one user";
				}

			}
			s.close();
			con.close();
		} catch (SQLException e) {
			message = "Error." + e.toString();
			error = true;
		} catch (Exception e) {
			message = "Error." + e.toString();
			error = true;
		}
		if (message != null) {
			PrintWriter out = response.getWriter();
			out.println("<B>" + message + "</B><BR>");
			out.println("<BR><BR>");
		}
		if (error == true)
			sendRegistrationForm(request, response, true);
		else
			sendRegistrationForm(request, response, false);
		sendPageFooter(response);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		sendPageHeader(response);
		sendRegistrationForm(request, response, false);
		sendPageFooter(response);
	}

	private void sendPageFooter(HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		out.println("</CENTER>");
		out.println("</BODY>");
		out.println("</HTML>");
	}

	private void sendPageHeader(HttpServletResponse response) 
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<HTML>");
		out.println("<HEAD>");
		out.println("<TITLE> Registration Page </TITLE>");
		out.println("</HEAD>");
		out.println("<BODY>");
		out.println("<CENTER>");
	}

	private String firstName;
	private String lastName;
	private String userName;
	private String password;

	private void sendRegistrationForm(HttpServletRequest request, HttpServletResponse response,
			boolean displayPreviousValues) throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		out.println("<BR><H2>Registration Page</H2>");
		out.println("<BR>Please enter the user details.");
		out.println("<BR>");
		out.println("<BR><FORM METHOD=POST>");
		out.println("<TABLE>");
		out.println("<TR>");
		out.println("<TD>First Name</TD>");
		out.println("<TD><INPUT TYPE=TEXT NAME=firstName");
		if (displayPreviousValues)
			out.println("VALUE=\" " + StringUtil.encodeHtmlTag(firstName) + "\"");
		out.println("></TD>");
		out.println("</TR>");
		out.println("<TR>");
		out.println("<TD>Last Name</TD>");
		out.println("<TD><INPUT TYPE=TEXT NAME=lastName");
		if (displayPreviousValues)
			out.println("VALUE=\" " + StringUtil.encodeHtmlTag(lastName) + "\"");
		out.println("></TD>");
		out.println("</TR>");

		out.println("<TR>");
		out.println("<TD>User Name</TD>");
		out.println("<TD><INPUT TYPE=TEXT NAME=userName");
		if (displayPreviousValues)
			out.println("VALUE=\" " + StringUtil.encodeHtmlTag(userName) + "\"");
		out.println("></TD>");
		out.println("</TR>");

		out.println("<TR>");
		out.println("<TD>Password</TD>");
		out.println("<TD><INPUT TYPE=PASSWORD NAME=password");
		if (displayPreviousValues)
			out.println("VALUE=\" " + StringUtil.encodeHtmlTag(password) + "\"");
		out.println("></TD>");
		out.println("</TR>");

		out.println("<TR>");
		out.println("<TD><INPUT TYPE=RESET></TD>");
		out.println("<TD><INPUT TYPE=SUBMIT></TD>");
		out.println("</TR>");
		out.println("</TABLE>");
		out.println("</FORM>");
		out.println("<BR>");
		out.println("<BR>");
	}

}
