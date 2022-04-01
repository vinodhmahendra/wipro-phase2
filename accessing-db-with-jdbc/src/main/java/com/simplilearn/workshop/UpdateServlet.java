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

@WebServlet("/UpdateServlet")
public class UpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	void updateRecord(HttpServletRequest request, HttpServletResponse response)
	throws IOException {
		String id = request.getParameter("id");
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String password = request.getParameter("password");
		PrintWriter out = response.getWriter();

		String url = "jdbc:mysql://localhost:3306/studentdb?allowPublicKeyRetrieval=true&useSSL=false";
		String user = "root";
		String dbpassword = "admin";
		try {
			String sql = "UPDATE users"+
					" SET firstname='" + StringUtil.fixSqlFieldValue(firstName) + "'," +
					" lastname='" + StringUtil.fixSqlFieldValue(lastName) + "'," +
					" password='" + StringUtil.fixSqlFieldValue(password)+ "'" +
					" where id=" + id;
			
			Connection con = DriverManager.getConnection(url, user, dbpassword);
			System.out.println("got connection");
			Statement s = con.createStatement();
			
			int i = s.executeUpdate(sql);
			if ( i == 1) 
				out.println("Record updated");
			else
				out.println("Error updating record");
			s.close();
			con.close();
		}catch(SQLException e) {
			out.println(e.toString());
		}catch (Exception e) {
			out.println(e.toString());
		}
		
		out.println("<A HREF=SearchServlet>Go back </A> to the search page");
	}

	private void sendUpdateForm(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String id = request.getParameter("id");
		PrintWriter out = response.getWriter();
		out.println("<BR><H2>Update Form</H2>");
		out.println("<BR>Please edit the first name, last name or password.");
		out.println("<BR>");
		
		String url = "jdbc:mysql://localhost:3306/studentdb?allowPublicKeyRetrieval=true&useSSL=false";
		String user = "root";
		String dbpassword = "admin";
		try {

			Connection con = DriverManager.getConnection(url, user, dbpassword);
			System.out.println("got connection");
			Statement s = con.createStatement();
			
			String sql = "SELECT firstname, lastname," +
					" username, password" +
					" FROM users" +
					" WHERE id=" +id;
			
			ResultSet rs = s.executeQuery(sql);
			if ( rs.next() ) {
				String firstName = rs.getString(1);
				String lastName = rs.getString(2);
				String userName = rs.getString(3);
				String password = rs.getString(4);
				
				out.println("<BR> <FORM METHOD=POST ACTION="+request.getRequestURI()+"?id="+id+">");
				out.println("<TABLE>");
				out.println("<TR>");
				out.println("<TD>First Name</TD>");
				out.println("<TD><INPUT TYPE=TEXT Name=firstName");
				out.println(" VALUE=\""+ StringUtil.encodeHtmlTag(firstName) +"\"");
				out.println("></TD>");
				out.println("</TR>");
				out.println("<TR>");
				out.println("<TD>Last Name</TD>");
				out.println("<TD><INPUT TYPE=TEXT Name=lastName");
				out.println(" VALUE=\""+ StringUtil.encodeHtmlTag(lastName) +"\"");
				out.println("></TD>");
				out.println("</TR>");
				out.println("<TR>");
				out.println("<TD>User Name</TD>");
				out.println("<TD>"+ StringUtil.encodeHtmlTag(userName) + "</TD>");
				out.println("</TR>");
				out.println("<TR>");
				out.println("<TD>Password</TD>");
				out.println("<TD><INPUT TYPE=PASSWORD Name=password");
				out.println(" VALUE=\""+ StringUtil.encodeHtmlTag(password) +"\"");
				out.println("></TD>");
				out.println("</TR>");
				out.println("<TR>");
				out.println("<TD><INPUT TYPE=RESET></TD>");
				out.println("<TD><INPUT TYPE=SUBMIT></TD>");
				out.println("</TR>");
				out.println("</TABLE>");
				out.println("</FORM>");
			}
			s.close();
			con.close();
			}catch(SQLException e) {
				out.println(e.toString());
			}catch(Exception e) {
				out.println(e.toString());
			}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		sendPageHeader(response);
		sendUpdateForm(request,response);
		sendPageFooter(response);
	}

	private void sendPageHeader(HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<HTML>");
		out.println("<HEAD>");
		out.println("<TITLE>Updating Record </TITLE>");
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

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		sendPageHeader(response);
		updateRecord(request, response);
		sendPageFooter(response);
	}

}
