package hr.fer.zemris.java.hw15.web.init;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import hr.fer.zemris.java.hw15.jpa.JPAEMFProvider;

/**
 * Implementation of {@link ServletContextListener} which is responsible for
 * initializing {@link EntityManagerFactory} when this web-application is started
 * and closing it when this web-application ends.
 */
@WebListener
public class Initialization implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		EntityManagerFactory emf = 
			Persistence.createEntityManagerFactory("blog.database"); 
		
		sce.getServletContext().setAttribute("blog.emf", emf);
		JPAEMFProvider.setEmf(emf);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		JPAEMFProvider.setEmf(null);
		
		ServletContext context = sce.getServletContext();
		EntityManagerFactory emf = 
			(EntityManagerFactory) context.getAttribute("blog.emf");
		
		if (emf != null) {
			emf.close();
		}
	}
	
}
