package com.app.blog.security;

import java.io.IOException;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JwtTokenHelper jwtTokenHelper;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		//1.To generate Token from request
		
		String requestToken = request.getHeader("Authorization");   //We'll get the token by calling getHeader() and key is "Authorization" that we get in the Header section whose value will be requestToken " 
		System.out.println(requestToken);  ////requestToken generated will be like Bearer 2525875dfhsgf
		
		String userName = null;
		String token = null;
		
		if(requestToken != null && requestToken.startsWith("Bearer"))
		{
			token = requestToken.substring(7); ////This will give the actual token...we need to exclude Bearer and use just 2525875dfhsgf....Also 7 is beginning index of substring
			try
			{
				userName = this.jwtTokenHelper.extractUsername(token);
			}catch (IllegalArgumentException e) {
				 
				System.out.println("Unable to get jwtToken!!");
			}catch (ExpiredJwtException e) {
				
				System.out.println("jwtToken has Expired!!");
			}catch (MalformedJwtException e) {
				
				System.out.println("Invalid jwtToken!!");
			}
			
		}
		else {
			System.out.println("jwtToken does not start with Bearer!!");
		}
		
		//2. Once we get the token...now we validate token
		
		if(userName != null && SecurityContextHolder.getContext().getAuthentication() == null)
		{
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(userName);
			if(this.jwtTokenHelper.validateToken(token, userDetails)) //this will return true or false
			{
				// for true condition
				//3.we need to set authentication
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				
			}
			else {
				
				System.out.println("Invalid jwtToken!!");
			}
		}
		else {
			
			System.out.println("UserName is null or context is not null!!");
		}
		
		
		filterChain.doFilter(request, response);

	}

}
