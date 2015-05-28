package cpd.servlet;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cpd.model.Courses;
import cpd.model.Quarter;
import cpd.model.Users;

@WebServlet(urlPatterns="/DisplayCourses", loadOnStartup=1)
public class DisplayCourses extends HttpServlet {
	private static final long serialVersionUID = 1L;
	/*Integer id = 1;
	Integer qt_id = 1;*/
       
    public DisplayCourses() {
        super();
        // TODO Auto-generated constructor stub
    }

	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config);
		
		
		try
        {
            Class.forName( "com.mysql.jdbc.Driver" );
        }
        catch( ClassNotFoundException e )
        {
            throw new ServletException( e );
        }
		
		ServletContext servletContext = getServletContext();
		
		
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		List<Courses> courseList = new ArrayList<Courses>();
		List<Courses> courseObjList = new ArrayList<Courses>();
		List<Quarter> crList = new ArrayList<Quarter>();
		getServletContext().setAttribute("crList",crList);
		getServletContext().setAttribute("courseList",courseList);
        		
		Connection conn = null;
        try
        {
            String url = "jdbc:mysql://cs3.calstatela.edu/cs320stu08";
            String username = "cs320stu08";
            String password = "MB4Oc!fa";

            conn = DriverManager.getConnection( url, username, password );
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery( "select * from courses" );

            while( rs.next() )
            {
                Courses courseDao = new Courses( rs.getInt("id")-1, rs.getString("course_code"),
                    rs.getString("course_name"), rs.getString("prerequisites") );
                courseObjList.add(courseDao);
                Courses courses = new Courses( rs.getInt("id")-1, rs.getString("course_code"),
                        rs.getString("course_name"), rs.getString("prerequisites"));
                courseList.add(courses);
                Quarter quarterCoursesDao = new Quarter(rs.getInt("id")-1, rs.getString("course_code"),
                    rs.getString("course_name"), rs.getString("prerequisites"), true);
                crList.add(quarterCoursesDao);
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

        request.setAttribute("courseObjList", courseObjList);
		
		request.getRequestDispatcher("/WEB-INF/DisplayCourses.jsp").forward(request, response);
		
        
 	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		HttpSession session = request.getSession();
        String userName = (String) session.getAttribute("user");
        if( userName != null && !userName.equals(""))
        {
        	userName = request.getParameter("username");
            // save the name in session
            session.setAttribute("user", userName);
        }
        else{
        	//response.sendRedirect("DisplayCourses");
        	doGet(request, response);
        }
		//doGet(request, response);
	}

}
