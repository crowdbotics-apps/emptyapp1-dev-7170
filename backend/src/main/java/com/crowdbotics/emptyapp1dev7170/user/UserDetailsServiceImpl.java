package com.crowdbotics.emptyapp1dev7170.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static java.util.Collections.emptyList;

/**
 * <h1>User Details Service Impl</h1>
 *
 * <p>This implementation supports JWT authentication.</p>
 * 
 * @author crowdbotics.com
 */
@Service
public class UserDetailsServiceImpl 
	implements UserDetailsService 
{
	/**
	 * Autowired constructor for {@link UserDetailsServiceImpl}.
	 * 
	 * @param _applicationUserRepository	{@link ApplicationUserRepository}
	 */
	@Autowired 
	public UserDetailsServiceImpl(
		final ApplicationUserRepository _applicationUserRepository 
	) 
	{
		applicationUserRepository = _applicationUserRepository;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserDetails loadUserByUsername(
		final String _username
	) 
		throws UsernameNotFoundException
	{
		ApplicationUser applicationUser = applicationUserRepository.findByUsername( _username );
		if (applicationUser == null) 
		{
			throw new UsernameNotFoundException( _username );
		}
		
		return new User(
			applicationUser.getUsername()
			, applicationUser.getPassword()
			, emptyList()
		);
	}
	
	//
	// Autowired
	//
	
	private final ApplicationUserRepository applicationUserRepository;

}
