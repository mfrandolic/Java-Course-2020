package hr.fer.zemris.java.hw15.jpa;

import javax.persistence.EntityManagerFactory;

/**
 * Class which remembers {@link EntityManagerFactory} object which is used by 
 * this application.
 */
public class JPAEMFProvider {

	/**
	 * Entity manages factory used by this application.
	 */
	private static EntityManagerFactory emf;
	
	/**
	 * Returns {@link EntityManagerFactory} object which is used by this application.
	 * 
	 * @return {@code EntityManagerFactory} object which is used by this application
	 */
	public static EntityManagerFactory getEmf() {
		return emf;
	}
	
	/**
	 * Sets {@link EntityManagerFactory} object which will be used by this application.
	 * 
	 * @param emf {@code EntityManagerFactory} object which will be used by this application
	 */
	public static void setEmf(EntityManagerFactory emf) {
		JPAEMFProvider.emf = emf;
	}
	
}
