package hr.fer.zemris.java.hw14.voting;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import hr.fer.zemris.java.hw14.dao.DAOProvider;
import hr.fer.zemris.java.hw14.models.PollOption;

/**
 * Servlet which transforms to XLS format (Microsoft Excel document) the voting
 * results for the poll with {@code id} equal to the {@code pollID} parameter 
 * received through the URL.
 * 
 * @author Matija Frandolić
 */
@WebServlet("/servleti/glasanje-xls")
public class GlasanjeXLSServlet extends HttpServlet {

	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		
		long pollID;
		try {
			pollID = Long.parseLong(req.getParameter("pollID"));
		} catch (NumberFormatException e) {
			return;
		}
		
		List<PollOption> pollOptions = DAOProvider.getDao().getPollOptions(pollID);
		pollOptions.sort(
			Comparator.comparing(PollOption::getVotesCount).reversed()
			.thenComparing(PollOption::getOptionTitle)
		);
		
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet();
		
		for (int i = 0, last = pollOptions.size(); i < last; i++) {
			HSSFRow row = sheet.createRow(i);
			row.createCell(0).setCellValue(pollOptions.get(i).getOptionTitle());
			row.createCell(1).setCellValue(pollOptions.get(i).getVotesCount());
		}

		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-Disposition", "attachment; filename=\"rezultati.xls\"");
		
		workbook.write(resp.getOutputStream());
		workbook.close();
	}
	
}
