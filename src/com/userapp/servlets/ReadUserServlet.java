package com.userapp.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CreateUserServlet
 */
@WebServlet(urlPatterns = "/readServlet", initParams={
		@WebInitParam(name = "dbUrl", value = "jdbc:mysql://localhost/mydb"),
		@WebInitParam(name = "dbUser", value = "root"),
		@WebInitParam(name = "dbPassword", value = "root")
})
public class ReadUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection connection;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(config.getInitParameter("dbUrl"), config.getInitParameter("dbUser"), config.getInitParameter("dbPassword"));
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}   
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("Select * from user");
			PrintWriter out = response.getWriter();
			out.println("<h2>"+request.getAttribute("message")+"</h2>");
			out.println("<table>");
			out.println("<tr>");
			out.println("<th>First Name</th>");
			out.println("<th>Last Name</th>");
			out.println("<th>Email</th>");
			out.println("</tr>");
			while(resultSet.next()){
				out.println("<tr>");
				out.print("<td>");
				out.print(resultSet.getString(1));
				out.print("</td>");
				out.print("<td>");
				out.print(resultSet.getString(2));
				out.print("</td>");
				out.print("<td>");
				out.print(resultSet.getString(3));
				out.print("</td>");
				out.println("</tr>");				
			}
			out.println("</table>");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse  response) throws ServletException, IOException {
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("Select * from user");
			PrintWriter out = response.getWriter();
			out.println("<table>");
			out.println("<tr>");
			out.println("<th>First Name</th>");
			out.println("<th>Last Name</th>");
			out.println("<th>Email</th>");
			out.println("</tr>");
			while(resultSet.next()){
				out.println("<tr>");
				out.print("<td>");
				out.print(resultSet.getString(1));
				out.print("</td>");
				out.print("<td>");
				out.print(resultSet.getString(2));
				out.print("</td>");
				out.print("<td>");
				out.print(resultSet.getString(3));
				out.print("</td>");
				out.println("</tr>");				
			}
			out.println("</table>");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void destroy() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
