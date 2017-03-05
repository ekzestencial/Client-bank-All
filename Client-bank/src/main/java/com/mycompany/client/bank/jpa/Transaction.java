/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.client.bank.jpa;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ekzestencial
 */
@Entity
@Table(name = "transaction", catalog = "financial", schema = "")
@XmlRootElement
@NamedQueries({
	@NamedQuery(name = "Transaction.findAll", query = "SELECT t FROM Transaction t")
	, @NamedQuery(name = "Transaction.findByTransactionId", query = "SELECT t FROM Transaction t WHERE t.transactionId = :transactionId")
	, @NamedQuery(name = "Transaction.findByValue", query = "SELECT t FROM Transaction t WHERE t.value = :value")
	, @NamedQuery(name = "Transaction.findByDate", query = "SELECT t FROM Transaction t WHERE t.date = :date")})
public class Transaction implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
        @Basic(optional = false)
        @Column(name = "transaction_id")
	private Long transactionId;
	@Basic(optional = false)
        @Column(name = "value")
	private int value;
	@Basic(optional = false)
        @Column(name = "date")
        @Temporal(TemporalType.TIMESTAMP)
	private Date date;
	@JoinColumn(name = "accaount_id", referencedColumnName = "account_id")
        @ManyToOne(optional = false)
	private Account accaountId;

	public Transaction() {
	}

	public Transaction(Long transactionId) {
		this.transactionId = transactionId;
	}

	public Transaction(Long transactionId, int value, Date date) {
		this.transactionId = transactionId;
		this.value = value;
		this.date = date;
	}

	public Long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Account getAccaountId() {
		return accaountId;
	}

	public void setAccaountId(Account accaountId) {
		this.accaountId = accaountId;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (transactionId != null ? transactionId.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof Transaction)) {
			return false;
		}
		Transaction other = (Transaction) object;
		if ((this.transactionId == null && other.transactionId != null) || (this.transactionId != null && !this.transactionId.equals(other.transactionId))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "com.mycompany.client.bank.jpa.Transaction[ transactionId=" + transactionId + " ]";
	}
	
}
