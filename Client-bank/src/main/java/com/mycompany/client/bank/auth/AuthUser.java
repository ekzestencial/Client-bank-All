/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.client.bank.auth;

/**
 *
 * @author alex
 */
import com.mycompany.client.bank.jpa.Appuser;
import com.mycompany.client.bank.jpa.Role;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 *
 * @author al
 */
public class AuthUser implements UserDetails{
    private final Appuser user;
    Collection<UserAuthority> authorities = new ArrayList<>();
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }   
    
    public Appuser getAppUser(){
        return user;
    }   
    
    public AuthUser(Appuser user){
        this.user = user;
        Role role = user.getRoleId();
        authorities.add(new UserAuthority(AuthorityName.ROLE_USER));
            if(role.getRoleId()==0L){
                authorities.add(new UserAuthority(AuthorityName.ROLE_ADMIN));
            }
            }

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
        }

