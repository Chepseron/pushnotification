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
 * @author amon.sabul
 */
@Entity
@Table(name = "audit")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Audit.findAll", query = "SELECT a FROM Audit a")
    , @NamedQuery(name = "Audit.findByIdaudit", query = "SELECT a FROM Audit a WHERE a.idaudit = :idaudit")
    , @NamedQuery(name = "Audit.findByTimer", query = "SELECT a FROM Audit a WHERE a.timer = :timer")
    , @NamedQuery(name = "Audit.findByCreatedby", query = "SELECT a FROM Audit a WHERE a.createdby = :createdby")
    , @NamedQuery(name = "Audit.findByAction", query = "SELECT a FROM Audit a WHERE a.action = :action")})
public class Audit implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idaudit")
    private Integer idaudit;
    @Basic(optional = false)
    @NotNull
    @Column(name = "timer")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timer;
    @Basic(optional = false)
    @NotNull
    @Column(name = "createdby")
    private int createdby;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "action")
    private String action;

    public Audit() {
    }

    public Audit(Integer idaudit) {
        this.idaudit = idaudit;
    }

    public Audit(Integer idaudit, Date timer, int createdby, String action) {
        this.idaudit = idaudit;
        this.timer = timer;
        this.createdby = createdby;
        this.action = action;
    }

    public Integer getIdaudit() {
        return idaudit;
    }

    public void setIdaudit(Integer idaudit) {
        this.idaudit = idaudit;
    }

    public Date getTimer() {
        return timer;
    }

    public void setTimer(Date timer) {
        this.timer = timer;
    }

    public int getCreatedby() {
        return createdby;
    }

    public void setCreatedby(int createdby) {
        this.createdby = createdby;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idaudit != null ? idaudit.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Audit)) {
            return false;
        }
        Audit other = (Audit) object;
        if ((this.idaudit == null && other.idaudit != null) || (this.idaudit != null && !this.idaudit.equals(other.idaudit))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.amon.db.Audit[ idaudit=" + idaudit + " ]";
    }
    
}
