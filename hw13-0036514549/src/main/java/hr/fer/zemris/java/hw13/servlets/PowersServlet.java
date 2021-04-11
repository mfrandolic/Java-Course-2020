package hr.fer.zemris.java.hw13.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * Servlet that accepts parameter {@code a} (integer from {@code [-100,100]},
 * parameter {@code b} (integer from {@code [-100,100]}, parameter {@code n} 
 * (integer from {@code [1,5]} and creates Microsoft Excel document with {@code n}
 * pages, where on page {@code i} there are two columns - first with integers from
 * {@code a} to {@code b} and second with {@code i}-th powers of these numbers.
 * 
 * @author Matija Frandolić
 */
@WebServlet(urlPatterns="/powers")
public class PowersServlet extends HttpServlet {

	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		
		int a, b, n;		
		try {
			a = loadIntParameter(req, "a", -100, 100);
			b = loadIntParameter(req, "b", -100, 100);
			n = loadIntParameter(req, "n", 1, 5);			
		} catch (IllegalArgumentException e) {
			req.setAttribute("exceptionMessage", e.getMessage());
			req.getRequestDispatcher("/WEB-INF/pages/invalid.jsp").forward(req, resp);
			return;
		}
		
		if (a > b) {
			int tmp = b;
			b = a;
			a = tmp;
		}
		
		HSSFWorkbook workbook = new HSSFWorkbook();
		
		for (int i = 0; i < n; i++) {
			HSSFSheet sheet = workbook.createSheet();
			for (int j = 0; j <= b - a; j++) {
				HSSFRow row = sheet.createRow(j);
				row.createCell(0).setCellValue(j + a);
				row.createCell(1).setCellValue(Math.pow(j + a, i));
			}
		}

		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-Disposition", "attachment; filename=\"tablica.xls\"");
		
		workbook.write(resp.getOutputStream());
		workbook.close();
	}
	
	/**
	 * Tries to parse parameter of the given request and with the given name and
	 * returns either parsed integer or throws {@link IllegalArgumentException}.
	 * Value of the parameter must be in a range determined by {@code lowerBound}
	 * and {@code upperBound} (both included).
	 * 
	 * @param  req        request in which to find the parameter with the given name
	 * @param  name       name of the parameter to find in the given request
	 * @param  lowerBound lower bound of allowed range (included)
	 * @param  upperBound upper bound of allowed range (included)
	 * @return            parsed integer
	 * @throws IllegalArgumentException if the value of the parameter with the given
	 *                                  name is not parsable to integer or if the
	 *                                  parsed integer is not in the specified range
	 */
	private static int loadIntParameter(HttpServletRequest req, String name, 
			int lowerBound, int upperBound) {
		
		String value = req.getParameter(name);
		int intValue;
		
		try {
			intValue = Integer.parseInt(value);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(
				"Parameter \"" + name + "\" is not parsable to integer."
			);
		}
		
		if (intValue < lowerBound || intValue > upperBound) {
			throw new IllegalArgumentException(String.format(
				"Parameter \"%s\" is %d but integer from [%d,%d] was expected.", 
				name, intValue, lowerBound, upperBound
			));
		}
		
		return intValue;
	}
	
}
