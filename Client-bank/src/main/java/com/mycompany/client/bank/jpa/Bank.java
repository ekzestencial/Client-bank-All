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
@Table(name = "bank", catalog = "financial", schema = "")
@XmlRootElement
@NamedQueries({
	@NamedQuery(name = "Bank.findAll", query = "SELECT b FROM Bank b")
	, @NamedQuery(name = "Bank.findByBankId", query = "SELECT b FROM Bank b WHERE b.bankId = :bankId")
	, @NamedQuery(name = "Bank.findByName", query = "SELECT b FROM Bank b WHERE b.name = :name")
	, @NamedQuery(name = "Bank.findByDepositPersent", query = "SELECT b FROM Bank b WHERE b.depositPersent = :depositPersent")
	, @NamedQuery(name = "Bank.findByCreditPersent", query = "SELECT b FROM Bank b WHERE b.creditPersent = :creditPersent")})
public class Bank implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
        @Basic(optional = false)
        @Column(name = "bank_id")
	private Long bankId;
	@Basic(optional = false)
        @Column(name = "name")
	private String name;
	@Basic(optional = false)
        @Column(name = "deposit_persent")
	private int depositPersent;
	@Basic(optional = false)
        @Column(name = "credit_persent")
	private int creditPersent;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "bankId")
	private Collection<Account> accountCollection;

	public Bank() {
	}

	public Bank(Long bankId) {
		this.bankId = bankId;
	}

	public Bank(Long bankId, String name, int depositPersent, int creditPersent) {
		this.bankId = bankId;
		this.name = name;
		this.depositPersent = depositPersent;
		this.creditPersent = creditPersent;
	}

	public Long getBankId() {
		return bankId;
	}

	public void setBankId(Long bankId) {
		this.bankId = bankId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getDepositPersent() {
		return depositPersent;
	}

	public void setDepositPersent(int depositPersent) {
		this.depositPersent = depositPersent;
	}

	public int getCreditPersent() {
		return creditPersent;
	}

	public void setCreditPersent(int creditPersent) {
		this.creditPersent = creditPersent;
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
		hash += (bankId != null ? bankId.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof Bank)) {
			return false;
		}
		Bank other = (Bank) object;
		if ((this.bankId == null && other.bankId != null) || (this.bankId != null && !this.bankId.equals(other.bankId))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "com.mycompany.client.bank.jpa.Bank[ bankId=" + bankId + " ]";
	}
	
}
