package hr.fer.zemris.java.hw13.voting;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * Servlet that transforms voting results to XLS format (Microsoft Excel document).
 * 
 * @author Matija Frandolić
 */
@WebServlet(urlPatterns="/glasanje-xls")
public class GlasanjeXLSServlet extends HttpServlet {

	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		
		String definitionFileName = req.getServletContext().getRealPath(
				"/WEB-INF/glasanje-definicija.txt"
		);
		String resultsFileName = req.getServletContext().getRealPath(
			"/WEB-INF/glasanje-rezultati.txt"
		);
		
		List<Band> bands = BandUtils.getBandsAndVotes(definitionFileName, resultsFileName);
		
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet();
		
		for (int i = 0, last = bands.size(); i < last; i++) {
			HSSFRow row = sheet.createRow(i);
			row.createCell(0).setCellValue(bands.get(i).getName());
			row.createCell(1).setCellValue(bands.get(i).getNumberOfVotes());
		}

		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-Disposition", "attachment; filename=\"rezultati.xls\"");
		
		workbook.write(resp.getOutputStream());
		workbook.close();
	}
	
}
