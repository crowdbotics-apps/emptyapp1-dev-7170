package com.crowdbotics.emptyapp1dev7170.task;



import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@Entity
public class Task 
{
	protected Task() 
	{
		super();
	}

	public Task(
		final String _description
	) 
	{
		description = _description;
	}

	/**
	 * <h1>ID</h1>
	 * 
	 * <p>Internal ID for the task.</p>
	 */
    @Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;

	private String description;



	//
	// Access methods
	//

	public Long getId() { return id; }

	public String getDescription() { return description; }
	public void setDescription( final String _value ) { description = _value; }
}
