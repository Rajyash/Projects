package cpd.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cpd.model.Courses;
import cpd.model.Quarter;
import cpd.model.Users;

@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.getRequestDispatcher("/WEB-INF/Login.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		List<Users> usrList = (List<Users>) getServletContext().getAttribute("usrList");
		String uName = request.getParameter("username");
		String userPassword = request.getParameter("password");
		Connection conn = null;
        try
        {
            String url = "jdbc:mysql://cs3.calstatela.edu/cs320stu08";
            String username = "cs320stu08";
            String password = "MB4Oc!fa";

            String sql = "select user_name, password from users where user_name= ? and password = ?";
            conn = DriverManager.getConnection( url, username, password );
            PreparedStatement pstmt = conn.prepareStatement( sql );
            pstmt.setString( 1, uName );
            pstmt.setString( 2, userPassword );
            
            ResultSet rs = pstmt.executeQuery();

            while( rs.next() )
            {
            	if(uName.equals(rs.getString("user_name")) && userPassword.equals(rs.getString("password"))){
    				request.getSession().setAttribute("user", uName);
    				response.sendRedirect("DisplayCourses");
    				return;
    			}
            }
            doGet(request, response);
        }
        catch( SQLException e )
        {
            throw new ServletException( e );
        }
        finally
        {
            try
            {
                if( conn != null ) conn.close();
            }
            catch( SQLException e )
            {
                throw new ServletException( e );
            }
        }
		
		//response.sendRedirect("Login");
		//doGet(request, response);
		//return;
	}

}
