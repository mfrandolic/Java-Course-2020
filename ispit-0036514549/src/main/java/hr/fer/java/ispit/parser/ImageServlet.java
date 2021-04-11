package hr.fer.java.ispit.parser;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.java.ispit.models.ImageModel;

@WebServlet("/image")
public class ImageServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		
		String text = req.getParameter("text");
		Image image = null;
		try {
			ImageModel model = Parser.parse(text);
			image = Parser.createImage(model);
		} catch (ParserException e) {
			req.setAttribute("text", text);
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}
		
		resp.setContentType("image/png");
		OutputStream outputStream = resp.getOutputStream();

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			ImageIO.write((BufferedImage) image, "png", bos);
			outputStream.write(bos.toByteArray());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
