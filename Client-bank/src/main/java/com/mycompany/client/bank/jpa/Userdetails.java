/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.client.bank.jpa;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ekzestencial
 */
@Entity
@Table(name = "userdetails", catalog = "financial", schema = "")
@XmlRootElement
@NamedQueries({
	@NamedQuery(name = "Userdetails.findAll", query = "SELECT u FROM Userdetails u")
	, @NamedQuery(name = "Userdetails.findByUserId", query = "SELECT u FROM Userdetails u WHERE u.userId = :userId")
	, @NamedQuery(name = "Userdetails.findByFirstName", query = "SELECT u FROM Userdetails u WHERE u.firstName = :firstName")
	, @NamedQuery(name = "Userdetails.findByLastName", query = "SELECT u FROM Userdetails u WHERE u.lastName = :lastName")
	, @NamedQuery(name = "Userdetails.findByAdress", query = "SELECT u FROM Userdetails u WHERE u.adress = :adress")
	, @NamedQuery(name = "Userdetails.findByPhone", query = "SELECT u FROM Userdetails u WHERE u.phone = :phone")})
public class Userdetails implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
        @Basic(optional = false)
        @Column(name = "user_id")
	private Long userId;
	@Basic(optional = false)
        @Column(name = "first_name")
	private String firstName;
	@Basic(optional = false)
        @Column(name = "last_name")
	private String lastName;
	@Column(name = "adress")
	private String adress;
	@Column(name = "phone")
	private String phone;
	@JoinColumn(name = "user_id", referencedColumnName = "user_id", insertable = false, updatable = false)
        @OneToOne(optional = false)
	private Appuser appuser;

	public Userdetails() {
	}

	public Userdetails(Long userId) {
		this.userId = userId;
	}

	public Userdetails(Long userId, String firstName, String lastName) {
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAdress() {
		return adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Appuser getAppuser() {
		return appuser;
	}

	public void setAppuser(Appuser appuser) {
		this.appuser = appuser;
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
		if (!(object instanceof Userdetails)) {
			return false;
		}
		Userdetails other = (Userdetails) object;
		if ((this.userId == null && other.userId != null) || (this.userId != null && !this.userId.equals(other.userId))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "com.mycompany.client.bank.jpa.Userdetails[ userId=" + userId + " ]";
	}
	
}
