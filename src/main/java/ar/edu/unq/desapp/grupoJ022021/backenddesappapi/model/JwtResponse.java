package ar.edu.unq.desapp.grupoJ022021.backenddesappapi.model;

import java.io.Serializable;

public class JwtResponse implements Serializable {

	private static final long serialVersionUID = -8091879091924046844L;
	private Long id ;
	private final String jwttoken;
	private String username ;

	public JwtResponse(Long id, String username,String token) {
		this.id = id;
		this.jwttoken = token;
		this.username = username;
	}
	public Long getId() {
		return id;
	}

	public String getname() {
		return username;
	}

	public String getAccessToken() {
		return this.jwttoken;
	}
}