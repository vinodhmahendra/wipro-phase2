package com.simplilearn.workshop;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public static String fixSqlFieldValue (String value) {
		if ( value == null )
			return null;
		int length = value.length();
		StringBuffer fixedValue = new StringBuffer((int) (length * 1.1));
		for ( int i = 0 ; i < length; i++) {
			char c= value.charAt(i);
			if ( c == '\'')
				fixedValue.append("''");
			else
				fixedValue.append(c);
		}
		return fixedValue.toString();
	}
	
	public void doPost(HttpServletRequest request,HttpServletResponse response) 
			throws ServletException, IOException {
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		if ( login(userName, password)) {
			RequestDispatcher rd = request.getRequestDispatcher("AnotherServlet");
			rd.forward(request, response);
		}else {
			sendLoginForm(response, true);
		}
	}
	
	boolean login ( String userName, String password) {
		String url = "jdbc:mysql://localhost:3306/studentdb?allowPublicKeyRetrieval=true&useSSL=false";
		String user = "root";
		String dbpassword = "admin";
		try {
			//load a driver 
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con =
					DriverManager.getConnection(url, user, dbpassword);
			System.out.println("got connection");
			
			Statement s = con.createStatement();
			
			String sql = "select username from users"
					+ " where username = '"+fixSqlFieldValue(userName)+"' and password = '"+fixSqlFieldValue(password)+"'";
			ResultSet rs = s.executeQuery(sql);
			
			if ( rs.next() ) {
				rs.close();
				s.close();
				con.close();
				return true;
			}
			rs.close();
			s.close();
			con.close();
		}catch(ClassNotFoundException e) {
			System.out.println(e.toString());
		}catch(SQLException e) {
			System.out.println(e.toString());
		}catch(Exception e) {
			System.out.println(e.toString());
		}
		return false;
	}
	

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
		out.println("<center>");
		if ( withErrorMessage )
			out.println("Login failed. Please try again.");
		out.println("<br>");
		out.println("<br> <h2>Login Page</h2>");
		out.println("<br>");
		out.println("<br>Please enter your username and password.");
		out.println("<br>");
		out.println("<br> <form method=post>");
		out.println("<table>");
		out.println("<tr>");
		out.println("<td>User Name: </td>");
		out.println("<td> <input type=text name=userName></td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<td>Password: </td>");
		out.println("<td> <input type=password name=password></td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<td align=right colspan=2>");
		out.println("<input type=submit value=Login></td>");
		out.println("</tr>");
		out.println("</table>");
		out.println("</form>");
		out.println("</center>");
		out.println("</body>");
		out.println("</html>");
	}

}
