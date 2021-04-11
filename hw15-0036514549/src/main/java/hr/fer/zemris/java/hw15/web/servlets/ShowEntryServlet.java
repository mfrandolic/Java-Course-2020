package hr.fer.zemris.java.hw15.web.servlets;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.model.BlogComment;
import hr.fer.zemris.java.hw15.model.BlogEntry;
import hr.fer.zemris.java.hw15.model.BlogUser;
import hr.fer.zemris.java.hw15.web.forms.CommentForm;

/**
 * Servlet which shows to the user a page containing specified blog entry along
 * with comments about that entry.
 * 
 * @author Matija Frandolić
 */
@WebServlet("/servleti/entry")
public class ShowEntryServlet extends HttpServlet {

	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		
		if (req.getAttribute("author") == null || req.getAttribute("id") == null) {
			ServletUtil.sendError(req, resp, "Forbidden.");
			return;
		}
		
		String nick = ((BlogUser) req.getAttribute("author")).getNick();
		Long id = (Long) req.getAttribute("id");
		
		BlogEntry entry = DAOProvider.getDAO().getEntry(id);
		if (entry == null || !entry.getCreator().getNick().equals(nick)) {
			ServletUtil.sendError(req, resp, "Entry does not exist.");
			return;
		}
		req.setAttribute("entry", entry);
		
		List<BlogComment> comments = DAOProvider.getDAO().getCommentsForEntry(id);
		req.setAttribute("comments", comments);
		
		req.getRequestDispatcher("/WEB-INF/pages/entry.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		
		if (req.getAttribute("author") == null || req.getAttribute("id") == null) {
			ServletUtil.sendError(req, resp, "Forbidden.");
			return;
		}
		
		String nick = ((BlogUser) req.getAttribute("author")).getNick();
		Long id = (Long) req.getAttribute("id");
		
		String button = req.getParameter("button");
		if(!"Post".equals(button)) {
			resp.sendRedirect(
				req.getContextPath() + "/servleti/author/" + nick + "/" + id
			);
			return;
		}

		CommentForm form = new CommentForm();
		form.fillFromHttpRequest(req);
		form.validate();
		
		if (form.errorsExist()) {
			req.setAttribute("form", form);
			doGet(req, resp);
			return;
		}
		
		BlogComment comment = new BlogComment();
		form.fillIntoComment(comment);
		
		String email = (String) req.getSession().getAttribute("current.user.email");
		comment.setUsersEmail(email != null ? email : "Anonymous");
		comment.setPostedOn(new Date());
		comment.setBlogEntry(DAOProvider.getDAO().getEntry(id));
		
		DAOProvider.getDAO().addComment(comment);

		resp.sendRedirect(
			req.getContextPath() + "/servleti/author/" + nick + "/" + id
		);
	}
	
}
