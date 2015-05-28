package cpd.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cpd.model.Quarter;
import cpd.model.Courses;
import cpd.model.QuarterPlan;

@WebServlet("/Done")
public class Done extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	
    public Done() {
        super();
    }
    
    public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config);
	}

	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Quarter> crList = (List<Quarter>)getServletContext().getAttribute("crList");
		for(int i=0;i<crList.size();i++){
			crList.get(i).setCompleted(true);
		}
		List<Courses> nextList = (List<Courses>)getServletContext().getAttribute("nextList");
		List<Quarter> tmpQTList = (List<Quarter>)getServletContext().getAttribute("tmpQTList");
		List<QuarterPlan> quarterPlans = (List<QuarterPlan>)getServletContext().getAttribute("quarterPlans");
		nextList.clear();
		tmpQTList.clear();
		quarterPlans.clear();
		request.getSession().invalidate();
		response.sendRedirect("DisplayCourses");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}

}
