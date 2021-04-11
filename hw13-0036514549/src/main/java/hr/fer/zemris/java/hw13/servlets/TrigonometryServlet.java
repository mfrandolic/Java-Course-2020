package hr.fer.zemris.java.hw13.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet that calculates values of trigonometric functions {@code sin(x)} and
 * {@code cos(x)} for all integer angles (in degrees) in a range determined by
 * URL parameters {@code a} and {@code b} and sets request attributes {@code sines}
 * and {@code cosines} to the calculated values. If any of the parameters is missing,
 * the default values are {@code a = 0} and {@code b = 90}. If {@code a > b}, they 
 * are swapped. If {@code b > a + 720}, then {@code b} is set to {@code a + 720}.
 * 
 * @author Matija Frandolić
 */
@WebServlet(urlPatterns="/trigonometric")
public class TrigonometryServlet extends HttpServlet {

	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		
		int a = loadIntParameter(req, "a", 0);
		int b = loadIntParameter(req, "b", 360);
		
		if (a > b) {
			int tmp = b;
			b = a;
			a = tmp;
		}
		
		if (b > a + 720) {
			b = a + 720;
		}
		
		int length = b - a + 1;
		double[] sines = new double[length];
		double[] cosines = new double[length];
		
		for (int i = 0; i < length; i++) {
			double angle = (i + a) * Math.PI / 180;
			sines[i] = Math.sin(angle);
			cosines[i] = Math.cos(angle);
		}
		
		req.setAttribute("a", a);
		req.setAttribute("b", b);
		req.setAttribute("sines", sines);
		req.setAttribute("cosines", cosines);
		
		req.getRequestDispatcher("/WEB-INF/pages/trigonometric.jsp").forward(req, resp);
	}
	
	/**
	 * Tries to parse parameter of the given request and with the given name and
	 * returns either parsed integer or the given default value if parsing was
	 * unsuccessful.
	 * 
	 * @param req          request in which to find the parameter with the given name
	 * @param name         name of the parameter to find in the given request
	 * @param defaultValue default value to return if parsing was unsuccessful
	 * @return             parsed integer or the given default value if parsing 
	 *                     was unsuccessful
	 */
	private static int loadIntParameter(HttpServletRequest req, String name, int defaultValue) {
		String value = req.getParameter(name);
		int intValue;
		
		try {
			intValue = Integer.parseInt(value);
		} catch (NumberFormatException e) {
			intValue = defaultValue;
		}
		
		return intValue;
	}
	
}
