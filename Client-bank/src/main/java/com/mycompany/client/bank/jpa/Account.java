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
@Table(name = "account", catalog = "financial", schema = "")
@XmlRootElement
@NamedQueries({
	@NamedQuery(name = "Account.findAll", query = "SELECT a FROM Account a")
	, @NamedQuery(name = "Account.findByAccountId", query = "SELECT a FROM Account a WHERE a.accountId = :accountId")
	, @NamedQuery(name = "Account.findByValue", query = "SELECT a FROM Account a WHERE a.value = :value")
	, @NamedQuery(name = "Account.findByOpenDate", query = "SELECT a FROM Account a WHERE a.openDate = :openDate")
	, @NamedQuery(name = "Account.findByCloseDate", query = "SELECT a FROM Account a WHERE a.closeDate = :closeDate")})
public class Account implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
        @Basic(optional = false)
        @Column(name = "account_id")
	private Long accountId;
	@Basic(optional = false)
        @Column(name = "value")
	private int value;
	@Basic(optional = false)
        @Column(name = "open_date")
        @Temporal(TemporalType.DATE)
	private Date openDate;
	@Column(name = "close_date")
        @Temporal(TemporalType.DATE)
	private Date closeDate;
	@JoinColumn(name = "user_id", referencedColumnName = "user_id")
        @ManyToOne(optional = false)
	private Appuser userId;
	@JoinColumn(name = "bank_id", referencedColumnName = "bank_id")
        @ManyToOne(optional = false)
	private Bank bankId;
	@JoinColumn(name = "status_id", referencedColumnName = "status_id")
        @ManyToOne(optional = false)
	private Status statusId;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "accaountId")
	private Collection<Transaction> transactionCollection;

	public Account() {
	}

	public Account(Long accountId) {
		this.accountId = accountId;
	}

	public Account(Long accountId, int value, Date openDate) {
		this.accountId = accountId;
		this.value = value;
		this.openDate = openDate;
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public Date getOpenDate() {
		return openDate;
	}

	public void setOpenDate(Date openDate) {
		this.openDate = openDate;
	}

	public Date getCloseDate() {
		return closeDate;
	}

	public void setCloseDate(Date closeDate) {
		this.closeDate = closeDate;
	}

	public Appuser getUserId() {
		return userId;
	}

	public void setUserId(Appuser userId) {
		this.userId = userId;
	}

	public Bank getBankId() {
		return bankId;
	}

	public void setBankId(Bank bankId) {
		this.bankId = bankId;
	}

	public Status getStatusId() {
		return statusId;
	}

	public void setStatusId(Status statusId) {
		this.statusId = statusId;
	}

	@XmlTransient
	public Collection<Transaction> getTransactionCollection() {
		return transactionCollection;
	}

	public void setTransactionCollection(Collection<Transaction> transactionCollection) {
		this.transactionCollection = transactionCollection;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (accountId != null ? accountId.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof Account)) {
			return false;
		}
		Account other = (Account) object;
		if ((this.accountId == null && other.accountId != null) || (this.accountId != null && !this.accountId.equals(other.accountId))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "com.mycompany.client.bank.jpa.Account[ accountId=" + accountId + " ]";
	}
	
}
