package com.crowdbotics.emptyapp1dev7170.security;

/**
 * <h1>Security Constants</h1>
 *
 * <p>...</p>
 *
 * @author crowdbotics.com
 */
public class SecurityConstants 
{
	public static final String SECRET = "Cr0wdb0t1c515gr3at";
	public static final long EXPIRATION_TIME = 864_000_000; // 10 days
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String HEADER_STRING = "Authorization";
	public static final String SIGN_UP_URL = "/users/sign-up";
}
