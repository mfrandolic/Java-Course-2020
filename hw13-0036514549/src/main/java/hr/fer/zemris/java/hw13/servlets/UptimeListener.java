package hr.fer.zemris.java.hw13.servlets;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Implementation of {@link ServletContextListener} that adds information when it
 * was called into servlet context's attributes under name {@code uptime}. 
 * Actual value that is stored is the result of {@code System.currentTimeMillis()}.
 * 
 * @author Matija Frandolić
 */
@WebListener
public class UptimeListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		sce.getServletContext().setAttribute("uptime", System.currentTimeMillis());
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// do nothing
	}

}
