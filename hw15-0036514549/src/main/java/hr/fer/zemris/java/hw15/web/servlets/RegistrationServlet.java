package hr.fer.zemris.java.hw15.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.model.BlogUser;
import hr.fer.zemris.java.hw15.web.forms.RegistrationForm;

/**
 * Servlet which shows to the user a form for registration of new users and 
 * validates the data and registers a new user when the form is submitted.
 * 
 * @author Matija Frandolić
 */
@WebServlet("/servleti/register")
public class RegistrationServlet extends HttpServlet {

	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		
		req.getRequestDispatcher("/WEB-INF/pages/registration.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		
		String button = req.getParameter("button");
		if(!"Register".equals(button)) {
			resp.sendRedirect(req.getContextPath() + "/servleti/main");
			return;
		}

		RegistrationForm form = new RegistrationForm();
		form.fillFromHttpRequest(req);
		form.validate();
		
		if (form.errorsExist()) {
			req.setAttribute("form", form);
			doGet(req, resp);
			return;
		}
		
		BlogUser user = new BlogUser();
		form.fillIntoUser(user);
		DAOProvider.getDAO().addUser(user);

		resp.sendRedirect(req.getServletContext().getContextPath() + "/servleti/main");
	}
	
}
