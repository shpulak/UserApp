package com.userapp.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CreateUserServlet
 */
@WebServlet("/updateServlet")
public class UpdateUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection connection;
	
	@Override
	public void init() throws ServletException {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost/mydb", "root", "root");
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}   
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		try {
			Statement statement = connection.createStatement();
			int result = statement.executeUpdate("update user set password='"+password+"' where email='"+email+"'");
			PrintWriter out = response.getWriter();
			if(result>0){
				out.println("<h1>Password Updated</h1>");				
			}else{
				out.println("<h1>Error updating the user</h1>");
			}
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
