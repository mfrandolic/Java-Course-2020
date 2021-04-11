package hr.fer.zemris.java.hw14;

import hr.fer.zemris.java.hw14.dao.sql.SQLConnectionProvider;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.sql.DataSource;

/**
 * Implementation of {@link Filter} which obtains a connection to the database
 * and sets it as an active connection for the current thread in 
 * {@link SQLConnectionProvider}.
 */
@WebFilter("/servleti/*")
public class ConnectionSetterFilter implements Filter {
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// do nothing
	}
	
	@Override
	public void destroy() {
		// do nothing
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		ServletContext context = request.getServletContext();
		DataSource ds = (DataSource) context.getAttribute("hr.fer.zemris.dbpool");
		
		try (Connection con = ds.getConnection()) {
			SQLConnectionProvider.setConnection(con);
			chain.doFilter(request, response);
			SQLConnectionProvider.setConnection(null);
		} catch (SQLException e) {
			throw new RuntimeException("Database access error occurred.", e);
		}
	}
	
}
