package com.userapp.servlets;

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

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
     
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/mydb", "root", "root");
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("Select * from user where email='"+email+"' and password='"+password+"'");
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("readServlet");
			if(resultSet.next()){
				request.setAttribute("message", "Welcome "+email);
				requestDispatcher.forward(request, response);
			}else{
				requestDispatcher = request.getRequestDispatcher("loginUser.html");
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('Invalid email/password');");
				out.println("</script>");
				requestDispatcher.include(request, response);
			}
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

}
