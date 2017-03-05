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
import javax.persistence.Lob;
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
@Table(name = "notification", catalog = "financial", schema = "")
@XmlRootElement
@NamedQueries({
	@NamedQuery(name = "Notification.findAll", query = "SELECT n FROM Notification n")
	, @NamedQuery(name = "Notification.findByNotificationId", query = "SELECT n FROM Notification n WHERE n.notificationId = :notificationId")
	, @NamedQuery(name = "Notification.findByDate", query = "SELECT n FROM Notification n WHERE n.date = :date")})
public class Notification implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
        @Basic(optional = false)
        @Column(name = "notification_id")
	private Long notificationId;
	@Basic(optional = false)
        @Lob
        @Column(name = "text")
	private String text;
	@Basic(optional = false)
        @Column(name = "date")
        @Temporal(TemporalType.TIMESTAMP)
	private Date date;
	@JoinColumn(name = "user_id", referencedColumnName = "user_id")
        @ManyToOne(optional = false)
	private Appuser userId;

	public Notification() {
	}

	public Notification(Long notificationId) {
		this.notificationId = notificationId;
	}

	public Notification(Long notificationId, String text, Date date) {
		this.notificationId = notificationId;
		this.text = text;
		this.date = date;
	}

	public Long getNotificationId() {
		return notificationId;
	}

	public void setNotificationId(Long notificationId) {
		this.notificationId = notificationId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Appuser getUserId() {
		return userId;
	}

	public void setUserId(Appuser userId) {
		this.userId = userId;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (notificationId != null ? notificationId.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof Notification)) {
			return false;
		}
		Notification other = (Notification) object;
		if ((this.notificationId == null && other.notificationId != null) || (this.notificationId != null && !this.notificationId.equals(other.notificationId))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "com.mycompany.client.bank.jpa.Notification[ notificationId=" + notificationId + " ]";
	}
	
}
