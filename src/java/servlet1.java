/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet("/servlet1")
public class servlet1 extends HttpServlet 
{
    PrintWriter out = null;
    Connection con;
    PreparedStatement pst;
    ResultSet rs;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        try 
        {
            String result;
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/jdbc", "root", "");
            ServletContext context = getServletContext();
            context.setAttribute("accno", "");
            String accno = request.getParameter("accno");
            String pinno = request.getParameter("pinno");         
            pst = con.prepareStatement("select * from login where accno = ? and pinno = ?");
            pst.setString(1, accno);
            pst.setString(2, pinno);
            rs = pst.executeQuery();
            boolean row = false;
            row = rs.next();
            
        if(row == true)
        {
                result = rs.getString(2);
                context.setAttribute("accno", result);
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/secondservlet");
                if(dispatcher == null)
                {   
                }
                dispatcher.forward(request, response);
                con.close();       
        }
        
        else
        {
                out = response.getWriter();
                response.setContentType("text/html");
                out.println("<html>");
                out.println("<body bgcolor=pink>");
                out.println("Please check the Accno and Balance");
                out.println("</body");
                out.println("</html");
                out.close();
         }
            
        } catch (ClassNotFoundException ex) {
           ex.printStackTrace();
        } catch (SQLException ex) {
             ex.printStackTrace();
        }

    }


}