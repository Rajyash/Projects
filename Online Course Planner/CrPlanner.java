package cpd.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
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
import cpd.model.QuarterPlan;

@WebServlet("/CrPlanner")
public class CrPlanner extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public CrPlanner() {
		super();
		// TODO Auto-generated constructor stub
	}

	private Courses getCourses(Integer id) {
		List<Courses> courseList = (List<Courses>) getServletContext()
				.getAttribute("courseList");
		for (Courses cr : courseList) {
			if (cr.getId() == id) {
				return cr;
			}
		}
		return null;
	}

	private Quarter getQTCourses(Integer id) {
		List<Quarter> crList = (List<Quarter>) getServletContext()
				.getAttribute("crList");
		for (Quarter qt : crList) {
			if (qt.getC_Id() == id) {
				return qt;
			}
		}
		return null;
	}

	public String getQuarterName(int nxtCount) {
		Calendar rightNow = Calendar.getInstance();
		int weekNumber = rightNow.get(Calendar.WEEK_OF_YEAR);
		int incCnt = 0;
		if (weekNumber >= 1 && weekNumber <= 12) {
			incCnt = 0;

		}
		if (weekNumber >= 13 && weekNumber <= 24) {
			incCnt = 1;

		}
		if (weekNumber >= 25 && weekNumber <= 37) {
			incCnt = 2;

		}
		if (weekNumber >= 38 && weekNumber <= 52) {
			incCnt = 3;

		}
		int quarter_name_id = nxtCount + incCnt;
		quarter_name_id = quarter_name_id % 4;
		switch (quarter_name_id) {
		case 0:
			return "Winter";
		case 1:
			return "Spring";
		case 2:
			return "Summer";
		case 3:
			return "Fall";
		}
		return "winter";
	}

	public int getQuarterYear(int nxtCount) {
		Calendar rightNow = Calendar.getInstance();
		int weekNumber = rightNow.get(Calendar.WEEK_OF_YEAR);
		int yearNumber = rightNow.get(Calendar.YEAR);
		int incCnt = 0;
		if (weekNumber >= 1 && weekNumber <= 12) {
			incCnt = 0;

		}
		if (weekNumber >= 13 && weekNumber <= 24) {
			incCnt = 1;

		}
		if (weekNumber >= 25 && weekNumber <= 37) {
			incCnt = 2;

		}
		if (weekNumber >= 38 && weekNumber <= 52) {
			incCnt = 3;

		}
		int year = 0;
		int quater_name_id = nxtCount % 4;
		quater_name_id = quater_name_id + incCnt;
		int increased_year = (nxtCount - quater_name_id) / 4;
		year = yearNumber + increased_year;
		return year;
	}

	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config);
		List<Quarter> crList = (List<Quarter>) getServletContext()
				.getAttribute("crList");
		List<Courses> selectedList = new ArrayList<Courses>();
		List<Quarter> tmpQTList = new ArrayList<Quarter>();
		getServletContext().setAttribute("selectedList", selectedList);
		// getServletContext().setAttribute("crList", crList);
		getServletContext().setAttribute("tmpQTList", tmpQTList);
		List<QuarterPlan> quarterPlans = new ArrayList<QuarterPlan>();
		getServletContext().setAttribute("quarterPlans", quarterPlans);
		/*
		 * Map<Integer,List<Integer>> quarter_subject = new
		 * HashMap<Integer,List<Integer>>();
		 * getServletContext().setAttribute("quarter_subject", quarter_subject);
		 */
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.getRequestDispatcher("/WEB-INF/CrPlanner.jsp?index=-2")
				.forward(request, response);
	}

	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		String[] id = request.getParameterValues("cp");

		int index = Integer.parseInt(request.getParameter("index"));
		List<Courses> courseList = (List<Courses>) getServletContext()
				.getAttribute("courseList");
		List<Courses> selectedList = (List<Courses>) getServletContext()
				.getAttribute("selectedList");
		List<Quarter> tmpQTList = (List<Quarter>) getServletContext()
				.getAttribute("tmpQTList");
		List<Quarter> crList = (List<Quarter>) getServletContext()
				.getAttribute("crList");
		List<QuarterPlan> quarterPlans = (List<QuarterPlan>) getServletContext()
				.getAttribute("quarterPlans");
		Map<Integer, List<Integer>> quarter_subject = new HashMap<Integer, List<Integer>>();
		if (request.getSession().getAttribute("quarter_subject") != null) {
			quarter_subject = (Map<Integer, List<Integer>>) request
					.getSession().getAttribute("quarter_subject");
		}
		List<Integer> pre_reqs = new ArrayList<Integer>();
		// request.getSession().setAttribute("quarter_subject",
		// quarter_subject);
		List<Courses> nextList = new ArrayList<Courses>();
		getServletContext().setAttribute("nextList", nextList);
		quarterPlans.add(new QuarterPlan(quarterPlans.size(),
				getQuarterName(quarterPlans.size() + 1),
				getQuarterYear(quarterPlans.size() + 1)));
		boolean coursesLeft;
		coursesLeft = true;
		getServletContext().setAttribute("coursesLeft", coursesLeft);
		if (id != null) {
			if (id.length == crList.size()) {
				coursesLeft = false;
				getServletContext().setAttribute("coursesLeft", coursesLeft);
			} else {
				for (int i = 0; i < id.length; i++) {
					int sel_id = Integer.parseInt(id[i]);
					Quarter qtDemo = getQTCourses(sel_id);
					qtDemo.setCompleted(false);
					tmpQTList.add(qtDemo);
					pre_reqs.add(sel_id);
				}
			}
		}
		quarter_subject.put(quarterPlans.size() - 1, pre_reqs);
		request.getSession().setAttribute("quarter_subject", quarter_subject);
		if (crList != null) {
			for (int i = 0; i < crList.size(); i++) {
				if (crList.get(i).isCompleted()) {
					int c_id = crList.get(i).getC_Id();
					Courses crDemo = getCourses(c_id);
					String code = crList.get(i).getC_Code();
					String pre = crList.get(i).getC_PreStr().trim();
					String[] preqs = pre.split(" ");
					int preLength = preqs.length;
					int cnt = 0;
					if (pre.contentEquals("")) {
						nextList.add(crDemo);
						// request.getSession().setAttribute("nextList",
						// nextList);
					} else {
						for (int j = 0; j < preLength; j++) {
							for (int k = 0; k < tmpQTList.size(); k++) {
								if (preqs[j].equalsIgnoreCase(tmpQTList.get(k)
										.getC_Code())) {
									cnt = cnt + 1;
									if (cnt == preLength) {
										nextList.add(crDemo);
										// request.getSession().setAttribute("nextList",
										// nextList);

									}
								}
							}
						}
					}
				}
			}
		}
		
		int finish = 0;
		if (request.getParameter("finish") != null
				&& request.getParameter("finish").equals("Finish")) {
			finish = 1;
			// quarter_subject.remove(quarter_subject.size()-1);
			request.getSession().setAttribute("quarter_subject",
					quarter_subject);
		}
		boolean none = (Boolean) getServletContext().getAttribute("coursesLeft");
		if (!none) {
			finish = 1;
		}
		if (nextList.size() == 0 || finish == 1) {
			coursesLeft = (Boolean) getServletContext().getAttribute(
					"coursesLeft");
			request.getRequestDispatcher("/WEB-INF/Plan.jsp").forward(request,
					response);
		} else {
			request.getRequestDispatcher(
					"/WEB-INF/CrPlanner.jsp?index=" + index).forward(request,
					response);
		}
	}

}
