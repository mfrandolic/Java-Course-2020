package hr.fer.zemris.java.hw15.web.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.model.BlogUser;
import hr.fer.zemris.java.hw15.web.forms.LoginForm;

/**
 * Servlet which shows to the user the main page of this application and a form
 * for login of existing users. When the form is submitted, data from the form
 * are validated and login is successful if no errors were registered.
 * 
 * @author Matija Frandolić
 */
@WebServlet("/servleti/main")
public class MainPageServlet extends HttpServlet {

	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		
		List<BlogUser> authors = DAOProvider.getDAO().getAllUsers();
		req.setAttribute("authors", authors);
		req.getRequestDispatcher("/WEB-INF/pages/main.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		
		String button = req.getParameter("button");
		if(!"Login".equals(button)) {
			resp.sendRedirect(req.getContextPath() + "/servleti/main");
			return;
		}

		LoginForm form = new LoginForm();
		form.fillFromHttpRequest(req);
		form.validate();
		
		if (form.errorsExist()) {
			req.setAttribute("form", form);
			doGet(req, resp);
			return;
		}

		BlogUser user = DAOProvider.getDAO().getUser(form.getNick());
		req.getSession().setAttribute("current.user.id", user.getId());
		req.getSession().setAttribute("current.user.fn", user.getFirstName());
		req.getSession().setAttribute("current.user.ln", user.getLastName());
		req.getSession().setAttribute("current.user.nick", user.getNick());
		req.getSession().setAttribute("current.user.email", user.getEmail());
		
		resp.sendRedirect(req.getServletContext().getContextPath() + "/servleti/main");
	}
	
}
