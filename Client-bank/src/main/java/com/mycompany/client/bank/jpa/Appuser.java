/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.client.bank.jpa;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author ekzestencial
 */
@Entity
@Table(name = "appuser", catalog = "financial", schema = "")
@XmlRootElement
@NamedQueries({
	@NamedQuery(name = "Appuser.findAll", query = "SELECT a FROM Appuser a")
	, @NamedQuery(name = "Appuser.findByUserId", query = "SELECT a FROM Appuser a WHERE a.userId = :userId")
	, @NamedQuery(name = "Appuser.findByUsername", query = "SELECT a FROM Appuser a WHERE a.username = :username")
	, @NamedQuery(name = "Appuser.findByPassword", query = "SELECT a FROM Appuser a WHERE a.password = :password")
	, @NamedQuery(name = "Appuser.findByEmail", query = "SELECT a FROM Appuser a WHERE a.email = :email")
	, @NamedQuery(name = "Appuser.findByRegDate", query = "SELECT a FROM Appuser a WHERE a.regDate = :regDate")
	, @NamedQuery(name = "Appuser.findByLastActivity", query = "SELECT a FROM Appuser a WHERE a.lastActivity = :lastActivity")})
public class Appuser implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
        @Basic(optional = false)
        @Column(name = "user_id")
	private Long userId;
	@Basic(optional = false)
        @Column(name = "username")
	private String username;
	@Basic(optional = false)
        @Column(name = "password")
	private String password;
	@Basic(optional = false)
        @Column(name = "email")
	private String email;
	@Basic(optional = false)
        @Column(name = "reg_date")
        @Temporal(TemporalType.DATE)
	private Date regDate;
	@Basic(optional = false)
        @Column(name = "last_activity")
        @Temporal(TemporalType.TIMESTAMP)
	private Date lastActivity;
	@JoinColumn(name = "role_id", referencedColumnName = "role_id")
        @ManyToOne(optional = false)
	private Role roleId;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "userId")
	private Collection<Notification> notificationCollection;
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "appuser")
	private Userdetails userdetails;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "userId")
	private Collection<Account> accountCollection;

	public Appuser() {
	}

	public Appuser(Long userId) {
		this.userId = userId;
	}

	public Appuser(Long userId, String username, String password, String email, Date regDate, Date lastActivity) {
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.email = email;
		this.regDate = regDate;
		this.lastActivity = lastActivity;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	public Date getLastActivity() {
		return lastActivity;
	}

	public void setLastActivity(Date lastActivity) {
		this.lastActivity = lastActivity;
	}

	public Role getRoleId() {
		return roleId;
	}

	public void setRoleId(Role roleId) {
		this.roleId = roleId;
	}

	@XmlTransient
	public Collection<Notification> getNotificationCollection() {
		return notificationCollection;
	}

	public void setNotificationCollection(Collection<Notification> notificationCollection) {
		this.notificationCollection = notificationCollection;
	}

	public Userdetails getUserdetails() {
		return userdetails;
	}

	public void setUserdetails(Userdetails userdetails) {
		this.userdetails = userdetails;
	}

	@XmlTransient
	public Collection<Account> getAccountCollection() {
		return accountCollection;
	}

	public void setAccountCollection(Collection<Account> accountCollection) {
		this.accountCollection = accountCollection;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (userId != null ? userId.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof Appuser)) {
			return false;
		}
		Appuser other = (Appuser) object;
		if ((this.userId == null && other.userId != null) || (this.userId != null && !this.userId.equals(other.userId))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "com.mycompany.client.bank.jpa.Appuser[ userId=" + userId + " ]";
	}
	
}
