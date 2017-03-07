/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.client.bank.jpa;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author ekzestencial
 */
@Entity
@Table(name = "status", schema = "")
@XmlRootElement
@NamedQueries({
	@NamedQuery(name = "Status.findAll", query = "SELECT s FROM Status s")
	, @NamedQuery(name = "Status.findByStatusId", query = "SELECT s FROM Status s WHERE s.statusId = :statusId")
	, @NamedQuery(name = "Status.findByName", query = "SELECT s FROM Status s WHERE s.name = :name")})
public class Status implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
        @Basic(optional = false)
        @Column(name = "status_id")
	private Long statusId;
	@Basic(optional = false)
        @Column(name = "name")
	private String name;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "statusId")
	private Collection<Account> accountCollection;

	public Status() {
	}

	public Status(Long statusId) {
		this.statusId = statusId;
	}

	public Status(Long statusId, String name) {
		this.statusId = statusId;
		this.name = name;
	}

	public Long getStatusId() {
		return statusId;
	}

	public void setStatusId(Long statusId) {
		this.statusId = statusId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
		hash += (statusId != null ? statusId.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof Status)) {
			return false;
		}
		Status other = (Status) object;
		if ((this.statusId == null && other.statusId != null) || (this.statusId != null && !this.statusId.equals(other.statusId))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "com.mycompany.client.bank.jpa.Status[ statusId=" + statusId + " ]";
	}
	
}
