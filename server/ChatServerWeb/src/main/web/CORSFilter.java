package main.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

/**
 * This filter changes the Access-Control-Allow-Origin header for all web requests to *.
 * 
 * We must use this filter to bypass the browser's security checks, for our JAX-RS resources,
 * since we are not routing the requests through an http server, and so they are made to a different
 * domain and with a different port than the requesting side, thus making the request
 * a CORS request, which needs to meet stricter security demands.
 * 
 * This should obviously not be used in production.
 * 
 * @author Gilad Ber
 *
 */
public class CORSFilter implements Filter 
{

	@Override
	public void destroy() 
	{
		//do nothing
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException 
	{
		( (HttpServletResponse) response ).addHeader("Access-Control-Allow-Origin", "*");
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig config) 
	{
		//do nothing
	}

}
