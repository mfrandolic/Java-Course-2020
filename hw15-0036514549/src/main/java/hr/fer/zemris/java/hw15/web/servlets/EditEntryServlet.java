package hr.fer.zemris.java.hw15.web.servlets;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.model.BlogEntry;
import hr.fer.zemris.java.hw15.model.BlogUser;
import hr.fer.zemris.java.hw15.web.forms.EntryForm;

/**
 * Servlet which shows to the user a form for editing an existing entry. 
 * When the form is submitted, data from the form are validated and edits are
 * successfuly saved if no errors were registered.
 * 
 * @author Matija Frandolić
 */
@WebServlet("/servleti/edit")
public class EditEntryServlet extends HttpServlet {

	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		
		if (req.getAttribute("author") == null) {
			ServletUtil.sendError(req, resp, "Forbidden.");
			return;
		}
		
		String nick = ((BlogUser) req.getAttribute("author")).getNick();
		
		if (!nick.equals(req.getSession().getAttribute("current.user.nick"))) {
			ServletUtil.sendError(req, resp, "Unauthorized.");
			return;
		}
		
		Long id;
		try {
			id = Long.valueOf(req.getParameter("id"));
		} catch (NumberFormatException e) {
			ServletUtil.sendError(req, resp, "Invalid parameter.");
			return;
		}

		BlogEntry entry = DAOProvider.getDAO().getEntry(id);
		EntryForm form = new EntryForm();
		form.fillFromEntry(entry);
		
		req.setAttribute("form", form);
		req.setAttribute("option", "edit");
		req.setAttribute("id", id);
		
		req.getRequestDispatcher("/WEB-INF/pages/entryForm.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		
		if (req.getAttribute("author") == null) {
			ServletUtil.sendError(req, resp, "Forbidden.");
			return;
		}
		
		String nick = ((BlogUser) req.getAttribute("author")).getNick();
		
		if (!nick.equals(req.getSession().getAttribute("current.user.nick"))) {
			ServletUtil.sendError(req, resp, "Unauthorized.");
			return;
		}
		
		Long id;
		try {
			id = Long.valueOf(req.getParameter("id"));
		} catch (NumberFormatException e) {
			req.setAttribute("error", "Invalid parameter.");
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}
		
		String button = req.getParameter("button");
		if(!"Save".equals(button)) {
			resp.sendRedirect(
				req.getContextPath() + "/servleti/author/" + nick + "/" + id
			);
			return;
		}

		EntryForm form = new EntryForm();
		form.fillFromHttpRequest(req);
		form.validate();
		
		if (form.errorsExist()) {
			req.setAttribute("form", form);
			req.setAttribute("option", "edit");
			req.setAttribute("id", id);
			req.getRequestDispatcher("/WEB-INF/pages/entryForm.jsp").forward(req, resp);
			return;
		}
		
		BlogEntry entry = DAOProvider.getDAO().getEntry(id);
		form.fillIntoEntry(entry);
		entry.setLastModifiedAt(new Date());

		resp.sendRedirect(
			req.getContextPath() + "/servleti/author/" + nick + "/" + id
		);
	}
	
}
