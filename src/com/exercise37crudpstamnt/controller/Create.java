package com.exercise37crudpstamnt.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.exercise37crudpstamnt.model.Employee;

/**
 * Servlet implementation class Create
 * @author crist
 * @version 1.0
 */
@WebServlet("/Create")
public class Create extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     * @post method that allows me to create the employee
     */
  
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html charset='UTF-8'");
		PrintWriter output = response.getWriter();
		
		Employee myEmployee = new Employee();
		//myEmployee.setIdEmployee(Integer.parseInt(request.getParameter("txtIdEmployee")));
		myEmployee.setNameEmployee(request.getParameter("txtNameEmployee"));
		myEmployee.setAgeEmployee(Integer.parseInt(request.getParameter("txtAgeEmployee")));
		myEmployee.setAddressEmployee(request.getParameter("txtAddressEmployee"));
		myEmployee.setSalaryEmployee(Double.parseDouble(request.getParameter("txtSalaryEmployee")));
		myEmployee.setDepartmentEmployee(request.getParameter("txtDepartmentEmployee"));
		
		//1.Delcara variables
		String urlServer = "jdbc:mysql://localhost:3306/tiendita?useSSL=false&serverTimezone=UTC";
		String userName = "admi";
		String password ="admi";
		int rowsAffected=0;
		
		String sentenciaSQLPreparedStatements="INSERT INTO Empleados (nombreEmpleado, edaEmpleado, domicilioEmpleado, salarioEmpleado, departamentoEmpleado)VALUES(?,?,?,?,?)";
		//Declarar objetos
		Connection conn=null;
		PreparedStatement pstmnt=null;
		
		try 
		{
		//3.Instanciamos el drive
			Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
		//4.Abrimos la conexion
			conn = DriverManager.getConnection(urlServer, userName, password);
			
			
		//5. Configuramos el prerades Statement
			pstmnt=conn.prepareStatement(sentenciaSQLPreparedStatements);
			pstmnt.setString(1, myEmployee.getNameEmployee());
			pstmnt.setInt(2, myEmployee.getAgeEmployee());
			pstmnt.setString(3, myEmployee.getAddressEmployee());
			pstmnt.setDouble(4, myEmployee.getSalaryEmployee());
			pstmnt.setString(5, myEmployee.getDepartmentEmployee());
			
			//6.Ejecutar query
			//Si estoy aplicando un select en mi sentenciaSQL select= debo utilzar leer query
			//SI estoy plicando un INSERT INTO se bebe utilizar UPDATE para crear, modificar y borrar
			rowsAffected=pstmnt.executeUpdate();
			if(rowsAffected>0)
			{
				output.append("Registro añadido con exito");
			}
			else
			{
				output.append("Registro no añadido");
			}
			
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally
		{
			try {
				pstmnt.close();
				conn.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		output.close();
	}

}
