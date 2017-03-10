package com.devproject.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.devproject.dao.UsuarioDao;
import com.devproject.model.Usuario;

@Service
public class UserDetailService implements UserDetailsService {
	
	@Autowired
	private UsuarioDao usuarios;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = usuarios.buscarPeloUsername(username);
		UsuarioSistema user = null;
		
		if(usuario != null){
			user = new UsuarioSistema(usuario, this.getAuthorities());
		}
		
		return user;
		
	}
	
	private Collection<? extends GrantedAuthority> getAuthorities(){
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		
		authorities.add(new SimpleGrantedAuthority("USUARIO"));
		
		return authorities;
		
	}
	
}
