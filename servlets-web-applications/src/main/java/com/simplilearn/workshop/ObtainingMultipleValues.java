package com.simplilearn.workshop;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/ObtainingMultipleValues")
public class ObtainingMultipleValues extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		//set the MIME type
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		out.println("<HTML>");
		out.println("<HEAD>");
		out.println("<TITLE>Obtaining Multi Value Parameter </TITLE>");
		out.println("</HEAD>");
		out.println("<BODY>");
		out.println("<BR>");
		out.println("<BR>Select your favourite music:");
		out.println("<BR><FORM METHOD=POST>");
		out.println("<BR><INPUT TYPE=CHECKBOX NAME=favoriteMusic VALUE=Rock>Rock");
		out.println("<BR><INPUT TYPE=CHECKBOX NAME=favoriteMusic VALUE=Jazz>Jazz");
		out.println("<BR><INPUT TYPE=CHECKBOX NAME=favoriteMusic VALUE=Classical>Classical");
		out.println("<BR><INPUT TYPE=CHECKBOX NAME=favoriteMusic VALUE=Country>Country");
		out.println("<BR><INPUT TYPE=SUBMIT VALUE=Submit>");
		out.println("</FORM>");
		out.println("</BODY>");
		out.println("</HTML>");
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String[] values = request.getParameterValues("favoriteMusic");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		if ( values != null ) {
			int length = values.length;
			out.println("You have selected: ");
			for (int i = 0; i < length; i++) {
				out.println("<BR>" +values[i]);
			}
					
		}
	}

}
