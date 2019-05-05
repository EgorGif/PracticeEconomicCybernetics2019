import java.io.*;
import java.util.Enumeration;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FileChooserViewer extends HttpServlet {
	
	final static String location = "/Users/vladasheleg/Documents/Git/Projects/EP/sechkoProb/src/books/";

	 public void doGet( HttpServletRequest req, HttpServletResponse resp)
		        throws ServletException, IOException {
		      performTask(req, resp);
		    }
		    public void doPost( HttpServletRequest req, HttpServletResponse resp)
		        throws ServletException, IOException {
		      performTask(req, resp);
		    }
		    public void performTask(HttpServletRequest req, HttpServletResponse resp) {
		      try {
		        String name, value;
		        PrintWriter out = resp.getWriter();
		        resp.setContentType("text/html; charset=CP1251");
		        out.println("<HTML><HEAD>");
		        out.println("<TITLE>InfoFromRequest</TITLE>");
		        out.println("</HEAD><BODY style=\"margin:50\"><BR>");
		     
		        Enumeration names = req.getParameterNames();
		        if (names.hasMoreElements()) {
		            name = (String) names.nextElement();
		            value = req.getParameterValues(name)[0];
		            String filename = name + "/" + value + ".txt";
		            out.println("<h3>" + filename + "<br></h3>");
		            try {
		            Scanner in = new Scanner (new File (location + "/" + filename));
		            out.println("<i>");
		            while (in.hasNextLine()) {
		            	out.println(in.nextLine() + "<br>");
		            }
		            out.println("</i>");
		            in.close();
		            }
		            catch (Exception e) {
		            	out.println("FY FAAAEN");
		            	out.println(e.getMessage());
		            }
		        }
		        out.println("</BODY></HTML>");
		        out.close();
		      } catch (Throwable theException) {
		        theException.printStackTrace();
		      }
		    }
}

