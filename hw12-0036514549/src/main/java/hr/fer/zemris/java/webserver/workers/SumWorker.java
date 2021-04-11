package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Implementation of {@link IWebWorker} that accepts two parameters, a and b, 
 * calculates their sum and generates HTML table with the operands and the result.
 * If values of the parameters are not integers or nothing was passed, the default 
 * value of a is 1 and the default value of b is 2.
 * 
 * @author Matija Frandolić
 */
public class SumWorker implements IWebWorker {

	@Override
	public void processRequest(RequestContext context) throws Exception {
		context.setMimeType("text/html");
		
		String paramA = context.getParameter("a");
		String paramB = context.getParameter("b");
		
		int a;
		try {
			a = Integer.parseInt(paramA);
		} catch (NumberFormatException e) {
			a = 1;
		}
		
		int b;
		try {
			b = Integer.parseInt(paramB);
		} catch (NumberFormatException e) {
			b = 2;
		}
		
		int zbroj = a + b;
		
		context.setTemporaryParameter("varA", Integer.toString(a));
		context.setTemporaryParameter("varB", Integer.toString(b));
		context.setTemporaryParameter("zbroj", Integer.toString(zbroj));
		
		String imgName = zbroj % 2 == 0 ? "even.jpg" : "odd.jpg";
		context.setTemporaryParameter("imgName", "/images/" + imgName);
		
		context.getDispatcher().dispatchRequest("/private/pages/calc.smscr");
	}

}
