package com.crowdbotics.emptyapp1dev7170.security;

import com.auth0.jwt.JWT;
import com.crowdbotics.emptyapp1dev7170.user.ApplicationUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

/**
 * <h1>JWT Authentication Filter</h1>
 *
 * <p>his implementation supports JWT authentication. It is an authorization filter
 * to validate requests containing JWTs.</p>
 *
 * @author crowdbotics.com
 */
public class JWTAuthenticationFilter 
	extends UsernamePasswordAuthenticationFilter 
{
	/**
	 * Autowired constructor for {@link JWTAuthenticationFilter}.
	 *
	 * @param _authenticationManager	{@link AuthenticationManager}
	 */
	public JWTAuthenticationFilter(
		final AuthenticationManager _authenticationManager
	)
	{
		authenticationManager = _authenticationManager;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see AbstractAuthenticationProcessingFilter#attemptAuthentication(HttpServletRequest, HttpServletResponse)
	 */
	@Override
	public Authentication attemptAuthentication(
		final HttpServletRequest _request
		, final HttpServletResponse _response
	) 
		throws AuthenticationException 
	{
		try 
		{
			final ApplicationUser credentials = new ObjectMapper()
				.readValue(
					_request.getInputStream()
					, ApplicationUser.class
				);

			return authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
					credentials.getUsername()
					, credentials.getPassword()
					, new ArrayList<>()
				)
			);
		} 
		catch (IOException e) 
		{
			throw new RuntimeException(e);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see AbstractAuthenticationProcessingFilter#successfulAuthentication(HttpServletRequest, HttpServletResponse, FilterChain, Authentication) 
	 */
	@Override
	protected void successfulAuthentication(
		final HttpServletRequest _request
		, final HttpServletResponse _response
		, final FilterChain _filterChain
		, final Authentication _authentication
	)
		throws IOException
			, ServletException 
	{
		final String token = JWT.create()
			.withSubject( ((User)_authentication.getPrincipal()).getUsername() )
			.withExpiresAt( new Date( System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME ) )
			.sign( HMAC512( SecurityConstants.SECRET.getBytes() ) );
			
		_response.addHeader(
			SecurityConstants.HEADER_STRING
			, SecurityConstants.TOKEN_PREFIX + token
		);
	}
	
	//
	// Autowired
	//
	
	private final AuthenticationManager authenticationManager;

}
