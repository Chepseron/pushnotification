/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amon.db;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author FAZOUL
 */
@Entity
@Table(name = "transactions")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Transactions.findAll", query = "SELECT t FROM Transactions t"),
    @NamedQuery(name = "Transactions.findByIdtransactions", query = "SELECT t FROM Transactions t WHERE t.idtransactions = :idtransactions"),
    @NamedQuery(name = "Transactions.findByCreatedOn", query = "SELECT t FROM Transactions t WHERE t.createdOn = :createdOn"),
    @NamedQuery(name = "Transactions.findByAmount", query = "SELECT t FROM Transactions t WHERE t.amount = :amount"),
    @NamedQuery(name = "Transactions.findByMpesastatement", query = "SELECT t FROM Transactions t WHERE t.mpesastatement = :mpesastatement")})
public class Transactions implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idtransactions")
    private Integer idtransactions;
    @Basic(optional = false)
    @NotNull
    @Column(name = "createdOn")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;
    @Basic(optional = false)
    @NotNull
    @Column(name = "amount")
    private int amount;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "mpesastatement")
    private String mpesastatement;
    @JoinColumn(name = "statusID", referencedColumnName = "idstatus")
    @ManyToOne(optional = false)
    private Status statusID;
    @JoinColumn(name = "outletID", referencedColumnName = "idoutlet")
    @ManyToOne(optional = false)
    private Outlet outletID;

    public Transactions() {
    }

    public Transactions(Integer idtransactions) {
        this.idtransactions = idtransactions;
    }

    public Transactions(Integer idtransactions, Date createdOn, int amount, String mpesastatement) {
        this.idtransactions = idtransactions;
        this.createdOn = createdOn;
        this.amount = amount;
        this.mpesastatement = mpesastatement;
    }

    public Integer getIdtransactions() {
        return idtransactions;
    }

    public void setIdtransactions(Integer idtransactions) {
        this.idtransactions = idtransactions;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getMpesastatement() {
        return mpesastatement;
    }

    public void setMpesastatement(String mpesastatement) {
        this.mpesastatement = mpesastatement;
    }

    public Status getStatusID() {
        return statusID;
    }

    public void setStatusID(Status statusID) {
        this.statusID = statusID;
    }

    public Outlet getOutletID() {
        return outletID;
    }

    public void setOutletID(Outlet outletID) {
        this.outletID = outletID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idtransactions != null ? idtransactions.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Transactions)) {
            return false;
        }
        Transactions other = (Transactions) object;
        if ((this.idtransactions == null && other.idtransactions != null) || (this.idtransactions != null && !this.idtransactions.equals(other.idtransactions))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.amon.db.Transactions[ idtransactions=" + idtransactions + " ]";
    }
    
}
