package com.crowdbotics.emptyapp1dev7170.user;



import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * <h1>Application User</h1>
 *
 * <p>...</p>
 *
 * @author crowdbotics.com
 */
@Entity
public class ApplicationUser 
{
	protected ApplicationUser() 
	{
		super();
	}

	/**
	 * <h1>ID</h1>
	 * 
	 * <p>Internal ID for the user.</p>
	 */
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	private Long id;

	private String username;

	private String password;


	//
	// Access methods
	//

	public Long getId() { return id; }

	public String getUsername() { return username; }
	public void setUsername( final String _value ) { username = _value; }

	public String getPassword() { return password; }
	public void setPassword( final String _value ) { password = _value; }
}
