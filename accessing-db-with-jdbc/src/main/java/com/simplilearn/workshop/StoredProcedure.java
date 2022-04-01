package com.simplilearn.workshop;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
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

@WebServlet("/StoredProcedure")
public class StoredProcedure extends HttpServlet {
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
		
		String url = "jdbc:mysql://localhost:3306/studentdb?allowPublicKeyRetrieval=true&useSSL=false";
		String user = "root";
		String dbpassword = "admin";
		try {

			Connection con = DriverManager.getConnection(url, user, dbpassword);
			System.out.println("got connection");
			
//			String create_procedure = "create procedure show_users()"
//									  + "BEGIN"
//									  + " select * from users;"
//									  + "END";
//			Statement s = con.createStatement();
//			s.executeUpdate(create_procedure);
			
			CallableStatement callableStatement = con.prepareCall("{call show_users()}");
			ResultSet rs  = callableStatement.executeQuery();
			
			PrintWriter out = response.getWriter();
			response.setContentType("text/html");
			
			while ( rs.next() ) {
				out.println(rs.getString("firstname") + "<BR>");
			}
			
			callableStatement.close();
			
			con.close();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
