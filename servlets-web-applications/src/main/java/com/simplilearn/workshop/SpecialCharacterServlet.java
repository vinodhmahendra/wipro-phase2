package com.simplilearn.workshop;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/SpecialCharacterServlet")
public class SpecialCharacterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public static String encodeHtmlTag(String tag) {
		if (tag == null)
			return null;
		int length = tag.length();
		StringBuffer encodedTag = new StringBuffer(2 * length);
		for (int i = 0; i < length; i++) {
			char c = tag.charAt(i);

			if (c == '<')
				encodedTag.append("&lt;");
			else if (c == '>')
				encodedTag.append("&gt;");
			else if (c == '&')
				encodedTag.append("&amp;");
			else if (c == '"')
				encodedTag.append("&quot;");
			else if (c == ' ')
				encodedTag.append("&nbsp;");
			else
				encodedTag.append(c);
		}
		return encodedTag.toString();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<HTML>");
		out.println("<head>");
		out.println("<title>HTML Tuutorial - Changing Line</title>");
		out.println("</head>");
		out.println("<body>");
	
		
		out.println(encodeHtmlTag("In HTML, you use <BR> to change line."));
		out.println("</body>");
		out.println("</HTML>");
	}

}
