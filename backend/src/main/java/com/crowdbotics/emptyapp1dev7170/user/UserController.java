package com.crowdbotics.emptyapp1dev7170.user;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <h1>User Controller</h1>
 *
 * <p>REST controller for entity {@link ApplicationUser}.</p>
 * 
 * @author crowdbotics.com
 */
@RequestMapping(
	"/users"
)
@RestController
public class UserController
{
	/**
	 * Autowired constructor for {@link UserController}.
	 *
	 * @param _applicationUserRepository    {@link ApplicationUserRepository}
	 * @param _bCryptPasswordEncoder        {@link BCryptPasswordEncoder}
	 */
	public UserController(
		final ApplicationUserRepository _applicationUserRepository
		, final BCryptPasswordEncoder _bCryptPasswordEncoder
	)
	{
		applicationUserRepository = _applicationUserRepository;
		bCryptPasswordEncoder = _bCryptPasswordEncoder;
	}

	/**
	 * End point to sign up a new user.
	 *
	 * @param user                      {@link ApplicationUser}
	 */
	@PostMapping(
		"/sign-up"
	)
	public void signUp(
		@RequestBody final ApplicationUser user
	)
	{
		user.setPassword( bCryptPasswordEncoder.encode( user.getPassword() ) );
		applicationUserRepository.save( user );
	}

	//
	// Autowired
	//

	private final ApplicationUserRepository applicationUserRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

}
