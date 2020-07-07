package com.crowdbotics.emptyapp1dev7170.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * <h1>JWT Authorization Filter</h1>
 *
 * <p>This implementation supports JWT authentication. It is an authentication
 * filter to issue JWTs to users sending credentials</p>
 * 
 * @author crowdbotics.com
 */
public class JWTAuthorizationFilter 
	extends BasicAuthenticationFilter 
{
	/**
	 * Autowired constructor for {@link JWTAuthorizationFilter}.
	 *
	 * @param _authenticationManager {@link AuthenticationManager}
	 */
	public JWTAuthorizationFilter(
		final AuthenticationManager _authenticationManager
	) 
	{
		super( _authenticationManager );
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see BasicAuthenticationFilter#doFilterInternal(HttpServletRequest, HttpServletResponse, FilterChain)
	 */
	@Override
	protected void doFilterInternal(
		final HttpServletRequest _request
		, final HttpServletResponse _response
		, final FilterChain _filterChain
	)
		throws IOException
			, ServletException
	{
		final String header = _request.getHeader( SecurityConstants.HEADER_STRING );

		// Is there a header and is it valid?
		if ((header == null)
		 || (header.startsWith( SecurityConstants.TOKEN_PREFIX ) == false) )
		{
			// No, next filter in the chain
			_filterChain.doFilter( _request, _response );
			return;
		}

		// Valid header
		final UsernamePasswordAuthenticationToken authentication = getAuthentication( _request );

		// Assign the currently authenticated principal.
		SecurityContextHolder.getContext().setAuthentication( authentication );
		
		// next filter in the chain
		_filterChain.doFilter( _request, _response );
	}

	/**
	 * Create and return a {@link UsernamePasswordAuthenticationToken}.
	 *
	 * @param _request					{@link HttpServletRequest}
	 * @return {@link UsernamePasswordAuthenticationToken} if a token is found in the header and
	 * 			it has a valid token signature; otherwise returns {@code null}
	 */
	private UsernamePasswordAuthenticationToken getAuthentication(
		final HttpServletRequest _request
	)
	{
		final String token = _request.getHeader( SecurityConstants.HEADER_STRING );

		// Is a token present?
		if (token != null)
		{
			// Yes, parse the token.
			try
			{
				final String user = JWT.require( Algorithm.HMAC512( SecurityConstants.SECRET.getBytes() ) )
					.build()
					.verify( token.replace(
						SecurityConstants.TOKEN_PREFIX
						, ""
					) )
					.getSubject();

				// Is a user present?
				if (user != null)
				{
					// Yes, build the authentication token
					return new UsernamePasswordAuthenticationToken(
						user
						, null
						, new ArrayList<>()
					);
				}

				// No user token present
				return null;
			}
			catch (JWTVerificationException _e)
			{
				log.debug(
					String.format(
						"Error occurred while validating JWT token: %s"
						, _e.getMessage()
					)
				);
			}
		}

		// No token present
		return null;
	}

	//
	// Attributes
	//
	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger( JWTAuthorizationFilter.class );
}
