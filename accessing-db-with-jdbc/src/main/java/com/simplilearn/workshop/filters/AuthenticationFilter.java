package com.simplilearn.workshop.filters;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import com.simplilearn.workshop.utils.StringUtil;

//@WebFilter(servletNames = { "SearchServlet" })

@WebFilter("/SearchServlet")
public class AuthenticationFilter implements Filter {


	public void destroy() {
		System.out.println("destroy method");
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		
		String ipAddress = request.getRemoteAddr();
		
		if ( login(userName, password)){
			System.out.println("user logged in" + ipAddress + " at "+ new Date());
			chain.doFilter(request, response); 
		}else {
			PrintWriter out = response.getWriter();
			out.println("<h3> Sorry, you are not authorized to access this resource.</h3>");
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
					+ " where username = '"+StringUtil.fixSqlFieldValue(userName)+"' and password = '"+StringUtil.fixSqlFieldValue(password)+"'";
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
	

	public void init(FilterConfig fConfig) throws ServletException {
		System.out.println("init");
	}

}
