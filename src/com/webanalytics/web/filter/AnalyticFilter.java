package com.webanalytics.web.filter;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.webanalytics.web.dto.MemberProfile;
import com.webanalytics.web.util.Cache;
import com.webanalytics.web.util.SecurityHolder;

public class AnalyticFilter implements Filter {

	static Logger logger = Logger.getLogger(AnalyticFilter.class);
	private FilterConfig filterConfig = null;

	/**
	 * destroy() : destroy() method called when the filter is taken out of
	 * service.
	 * 
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {
		this.filterConfig = null;

	}

	/**
	 * doFilter() : doFilter() method called before the servlet that this filter
	 * is mapped is invoked.
	 * 
	 * doFilter() checks the session and if the session is true then it checks
	 * for the authToken Token sent as a request parameter based on the result
	 * serverResponse validation is set. Once the validation is true the
	 * CurrentSessionUser object is set.
	 * 
	 * @param request
	 * @param response
	 * @param FilterChain
	 * @throws IOException
	 * @throws ServletException
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest,
	 *      javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		if (filterConfig == null)
			return;

		// Response header
		HttpServletResponse res = (HttpServletResponse) response;
		res.addHeader("Access-Control-Allow-Headers",
				"origin, content-type, accept, x-requested-with, my-cool-header");
		res.addHeader("Access-Control-Max-Age", "60"); // seconds to cache
														// preflight request -->
														// less OPTIONS traffic
		res.addHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
		HttpServletRequest req = (HttpServletRequest) request;
		//res.addHeader("Access-Control-Allow-Origin", "http://"+req.getServerName()+":"+req.getServerPort());
		res.addHeader("Access-Control-Allow-Origin", "http://localhost:1841");
		res.addHeader("Access-Control-Allow-Credentials", "true");
		
		HttpSession session = req.getSession(true);
		
		MemberProfile loggedInMember = ((MemberProfile) session
				.getAttribute(SecurityHolder.LOGGED_MEMBER));
		if (session.getAttribute("locale") == null)
			// If there is no locale in the session, set it to default locale
			session.setAttribute("locale", new Locale("en", "US"));
		if (loggedInMember == null) {

//			if (!req.getRequestURL().toString().contains("login")
//					&& !req.getRequestURL().toString()
//							.contains("admin/register")) {
//				response.getWriter().write("notAuthorized");
//				response.getWriter().flush();
//				logger.warn("Not authorized");
//				return;
//			}
		} else {
			logger.warn("Logged in as " + loggedInMember.getEmail());
		}

		SecurityHolder.putCurrentSession(session);
		SecurityHolder.putCurrentSessionUser(loggedInMember);
		// Populate session level cache
		Cache.initializeCache();

		chain.doFilter(request, response);
		Cache.clearCache();

		SecurityHolder.putCurrentSessionUser(null);
		SecurityHolder.putCurrentSession(null);

	}

	/**
	 * init() : init() method called when the filter is instantiated.
	 * 
	 * @param FilterConfig
	 * @throws ServletException
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		this.filterConfig = arg0;
	}
}