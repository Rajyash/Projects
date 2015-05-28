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

import cpd.model.Courses;
import cpd.model.Quarter;

@WebServlet("/EditCourse")
public class EditCourse extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public EditCourse() {
        super();
        // TODO Auto-generated constructor stub
    }

    private Courses getCourses(Integer id) throws ServletException
    {
    	Courses courseDao = null;
        Connection conn = null;
        try
        {
            String url = "jdbc:mysql://cs3.calstatela.edu/cs320stu08";
            String username = "cs320stu08";
            String password = "MB4Oc!fa";

            String sql = "select * from courses where id = ?";

            conn = DriverManager.getConnection( url, username, password );
            PreparedStatement pstmt = conn.prepareStatement( sql );
            pstmt.setInt( 1, id+1);
            ResultSet rs = pstmt.executeQuery();

            if( rs.next() )
            	courseDao = new Courses(rs.getInt("id")-1, rs.getString("course_code"),
                        rs.getString("course_name"), rs.getString("prerequisites"));
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

        return courseDao;
    }
    
    private Quarter getQTCourses(Integer id)
    {
        List<Quarter> crList = (List<Quarter>) getServletContext().getAttribute("crList");
        for( Quarter qt : crList )
        {
            if(qt.getC_Id() == id)
            {
            	return qt;
            }
        }
        return null;
    }

    
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Integer id = Integer.valueOf(request.getParameter("id"));
	    Courses cr = getCourses(id);
	    Quarter qt = getQTCourses(id);
	    List<Courses> courseList = (List<Courses>)getServletContext().getAttribute("courseList");
		getServletContext().setAttribute("qt", qt);
		List<String> preReqList = new ArrayList<String>();
		Connection conn = null;
		try
        {
            String url = "jdbc:mysql://cs3.calstatela.edu/cs320stu08";
            String username = "cs320stu08";
            String password = "MB4Oc!fa";
            String preCode="";
            String sql = "select course_code from courses where id != ?";
            conn = DriverManager.getConnection( url, username, password );
            PreparedStatement pstmt = conn.prepareStatement( sql );
            pstmt.setInt(1, id+1);
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
		request.setAttribute( "cr", cr );
		
		request.setAttribute("preReqList", preReqList);
		
		request.getRequestDispatcher("/WEB-INF/EditCourse.jsp").forward(request, response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String userName = (String) request.getSession().getAttribute("user");
        if(userName != null && !userName.equals("")) 
        {
        	Integer id = Integer.valueOf(request.getParameter("id"));
        	Courses cr = getCourses(id);
        	Quarter qt = getQTCourses(id);
        	
        	
        	List<String> preReqList = (List<String>)getServletContext().getAttribute("preReqList");
        	String code = request.getParameter("courseCode");
        	String title = request.getParameter("courseName");
        	String[] edittedPreReqList = request.getParameterValues("e1");
        	List<Courses> courseList = (List<Courses>) getServletContext().getAttribute("courseList");
        	List<Quarter> crList = (List<Quarter>)getServletContext().getAttribute("crList");
    		
        	String edittedPre = "";

        	if(edittedPreReqList != null){
        		for(int i = 0; i < edittedPreReqList.length; i++){
        			edittedPre = edittedPre.concat(edittedPreReqList[i].concat(" "));
        		}
        	}
        	/*if(preReqList.contains(cr.getCourseCode())){
        		if(!cr.getCourseCode().equalsIgnoreCase(code)){
        			preReqList.remove(cr.getCourseCode());
        			preReqList.add(code);
        		}
        	}*/
        	
        	 Connection c = null;
             try
             {
                 String url = "jdbc:mysql://cs3.calstatela.edu/cs320stu08";
                 String username = "cs320stu08";
                 String password = "MB4Oc!fa";

                 String sql = "update courses set course_code = ?, course_name = ?, prerequisites = ? where id = ?";

                 c = DriverManager.getConnection( url, username, password );
                 PreparedStatement pstmt = c.prepareStatement( sql );
                 pstmt.setString( 1, code );
                 pstmt.setString( 2, title );
                 pstmt.setString( 3, edittedPre );
                 pstmt.setInt( 4, id+1 );
                 pstmt.executeUpdate();
             }
             catch( SQLException e )
             {
                 throw new ServletException( e );
             }
             finally
             {
                 try
                 {
                     if( c != null ) c.close();
                 }
                 catch( SQLException e )
                 {
                     throw new ServletException( e );
                 }
             }
        	cr.setCourseCode(code);
        	cr.setCourseName(title);
        	cr.setPreReqs(edittedPre);
        	qt.setC_Code(code);
        	qt.setC_Name(title);
        	qt.setC_PreStr(edittedPre);
        	response.sendRedirect("DisplayCourses");
        }
        else
        {
        	response.sendRedirect("Login");
        }
	}

}
