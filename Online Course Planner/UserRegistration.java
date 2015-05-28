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
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cpd.model.Quarter;
import cpd.model.Users;

@WebServlet("/UserRegistration")
public class UserRegistration extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public UserRegistration() {
        super();
        // TODO Auto-generated constructor stub
    }

	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config);
		//List<String> errors = new ArrayList<String>();
		//getServletContext().setAttribute("errors", errors);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//List<String> errors = new ArrayList<String>();
		
		
		request.getRequestDispatcher("/WEB-INF/UserRegistration.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		List<String> errors = new ArrayList<String>();
		errors.clear();
		String uName = request.getParameter("username");
		String userPassword = request.getParameter("password");
		String reTypePassword = request.getParameter("reTypePassword");
		String firstName = ""; 
		firstName =	request.getParameter("firstName");
		String lastName = "";
		lastName = request.getParameter("lastName");
		//List<Users> usrList = new ArrayList<Users>();
		List<String> userNameList = new ArrayList<String>();
		
		
		Connection conn = null;
		try
        {
            String url = "jdbc:mysql://cs3.calstatela.edu/cs320stu08";
            String username = "cs320stu08";
            String password = "MB4Oc!fa";
            
            conn = DriverManager.getConnection( url, username, password );
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery( "select user_name from users" );
            
            while(rs.next()){
            	userNameList.add(rs.getString("user_name"));
            }
            
            if((!uName.equalsIgnoreCase("") && uName!=null) && (!userPassword.equalsIgnoreCase("") && userPassword!=null))
    		{
    			if(uName.length()>=4)
    			{
    				if(!(userNameList.contains(uName)))
    				{
    					if(userPassword.length()>=4)
    					{
    						if(userPassword.equals(reTypePassword))
    						{
    							String sql = "insert into users (user_name, password, retype_password, first_name, last_name) values (?, ?, ?, ?, ?)";

    				            PreparedStatement pstmt = conn.prepareStatement( sql );
    				            pstmt.setString( 1, uName );
    				            pstmt.setString( 2, userPassword );
    				            pstmt.setString( 3, reTypePassword );
    				            pstmt.setString( 4, firstName );
    				            pstmt.setString( 5, lastName );
    				            pstmt.executeUpdate();
    							errors.clear();
    							response.sendRedirect("Login");
    						}
    						else
    						{
    							errors.add("Password and Re-Type Password does not match");
    							request.setAttribute("errors", errors);
    							//response.sendRedirect("UserRegistration");
    							doGet(request, response);
    						}
    					}
    					else
    					{
    						errors.add("Password must be at least 4 characters");
    						request.setAttribute("errors", errors);
    						//response.sendRedirect("UserRegistration");
    						doGet(request, response);
    					}
    				}
    				else
    				{
    					errors.add("Username already exists. Please use a different username.");
    					request.setAttribute("errors", errors);
    					//response.sendRedirect("UserRegistration");
    					doGet(request, response);
    				}
    			}
    			else
    			{
    				errors.add("Username must be at least 4 characters");
    				request.setAttribute("errors", errors);
    				//response.sendRedirect("UserRegistration");
    				doGet(request, response);
    			}
    		}
    		else
    		{
    			errors.add("One or more required fields are empty");
    			request.setAttribute("errors", errors);
    			//response.sendRedirect("UserRegistration");
    			doGet(request, response);
    		}
           
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
		//request.setAttribute("errors", errors);
		//response.sendRedirect("UserRegistration");
		
	}

}
