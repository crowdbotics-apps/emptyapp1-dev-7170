package com.crowdbotics.emptyapp1dev7170.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * <h1>Application User Repository</h1>
 *
 * <p>Spring data repository for {@link ApplicationUser}.</p>
 * 
 * @author crowdbotics.com
 */
public interface ApplicationUserRepository 
	extends JpaRepository<ApplicationUser,  Long> 
		, JpaSpecificationExecutor<ApplicationUser>
{
    ApplicationUser findByUsername( final String _username );
}
