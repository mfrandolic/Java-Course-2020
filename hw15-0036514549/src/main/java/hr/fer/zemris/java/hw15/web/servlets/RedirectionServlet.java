package hr.fer.zemris.java.hw15.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.model.BlogUser;

/**
 * Servlet which is responsible for reading the requested path and which 
 * forwards requests to correct servlets or produces an error.
 * 
 * @author Matija Frandolić
 */
@WebServlet("/servleti/author/*")
public class RedirectionServlet extends HttpServlet {

	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		
		serve(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		serve(req, resp);
	}
	
	/**
	 * Serves the requests to this servlet by forwarding requests to correct
	 * servlets or producing an error.
	 * 
	 * @param  req  an {@link HttpServletRequest} object that contains the 
	 *              request the client has made of the servlet
	 * @param  resp an {@link HttpServletResponse} object that contains the 
	 *              response the servlet sends to the client
	 * @throws ServletException is request could not be handled
	 * @throws IOException      if I/O error occurred
	 */
	private static void serve(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		
		String path = req.getPathInfo();
		if (path == null) {
			ServletUtil.sendError(req, resp, "Invalid URL.");
			return;
		}
		
		String[] pathSplit = path.substring(1).split("/");
		if (pathSplit.length != 1 && pathSplit.length != 2) {
			ServletUtil.sendError(req, resp, "Invalid URL.");
			return;
		}
		
		String nick = pathSplit[0];
		BlogUser author = DAOProvider.getDAO().getUser(nick);
		if (author == null) {
			ServletUtil.sendError(req, resp, "Author does not exist.");
			return;
		}
		req.setAttribute("author", author);
		
		if (pathSplit.length == 1) {
			req.getRequestDispatcher("/servleti/entries").forward(req, resp);
			return;
		}
		
		String action = pathSplit[1];
		switch (action) {
		case "new":
			req.getRequestDispatcher("/servleti/new").forward(req, resp);
			break;
		case "edit":
			req.getRequestDispatcher("/servleti/edit").forward(req, resp);
			break;
		default:
			try {
				Long id = Long.valueOf(action);
				req.setAttribute("id", id);
				req.getRequestDispatcher("/servleti/entry").forward(req, resp);
			} catch (NumberFormatException e) {
				ServletUtil.sendError(req, resp, "Invalid URL.");
			}
		}
	}

}
