package cpd.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.corba.se.spi.orbutil.fsm.Guard.Result;

import cpd.model.Courses;
import cpd.model.Quarter;

@WebServlet("/AddCourse")
public class AddCourse extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	public AddCourse() {
        super();
        // TODO Auto-generated constructor stub
    }

	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		List<Courses> courseList = (List<Courses>)getServletContext().getAttribute("courseList");
		List<String> preReqList = new ArrayList<String>();
		Connection conn = null;
		try
        {
            String url = "jdbc:mysql://cs3.calstatela.edu/cs320stu08";
            String username = "cs320stu08";
            String password = "MB4Oc!fa";
            String preCode="";
            String sql = "select course_code from courses";
            conn = DriverManager.getConnection( url, username, password );
            PreparedStatement pstmt = conn.prepareStatement( sql );
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()){
            	preCode = rs.getString("course_code");
            	preReqList.add(preCode);
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
		request.setAttribute("preReqList", preReqList);
		request.getRequestDispatcher("/WEB-INF/AddCourse.jsp").forward(request, response);
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String userName = (String) request.getSession().getAttribute("user");
		if(userName != null && !userName.equals("")){
			ServletContext servletContext = getServletContext();
			List<String> preReqList = (List<String>)getServletContext().getAttribute("preReqList");
			String code = request.getParameter("courseCode").toUpperCase();
			String title = request.getParameter("courseName");
			String[] newPreReqList = request.getParameterValues("addedPreReqs");
			String Pre = "";

			List<Courses> courseList = (List<Courses>) getServletContext().getAttribute("courseList");
			List<Quarter> crList = (List<Quarter>)getServletContext().getAttribute("crList");
			
			if(newPreReqList != null){
				for(int i = 0; i < newPreReqList.length; i++){
					Pre = Pre.concat(newPreReqList[i].concat(" "));
				}
			}
			//courseList.add(new Courses(idSeed++,code,title,Pre));
			//crList.add(new Quarter(qtSeed++, code, title, Pre, true));
			/*if(!preReqList.contains(code)){
				preReqList.add(code);
			}*/
			Connection conn = null;
			try
	        {
	            String url = "jdbc:mysql://cs3.calstatela.edu/cs320stu08";
	            String username = "cs320stu08";
	            String password = "MB4Oc!fa";

	            String sql = "insert into courses (course_code, course_name, prerequisites) values (?, ?, ?)";

	            conn = DriverManager.getConnection( url, username, password );
	            PreparedStatement pstmt = conn.prepareStatement( sql );
	            pstmt.setString( 1, code );
	            pstmt.setString( 2, title );
	            pstmt.setString( 3, Pre );
	            pstmt.executeUpdate();
	            crList.add(new Quarter(courseList.size()+1, code, title, Pre, true));
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
			
			//request.setAttribute("preReqList", preReqList);
			
			response.sendRedirect("DisplayCourses");
		}
		else
		{
			response.sendRedirect("Login");
		}
	}

}
