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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author amon.sabul
 */
@Entity
@Table(name = "usergroup")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usergroup.findAll", query = "SELECT u FROM Usergroup u")
    , @NamedQuery(name = "Usergroup.findByIdgroups", query = "SELECT u FROM Usergroup u WHERE u.idgroups = :idgroups")
    , @NamedQuery(name = "Usergroup.findByName", query = "SELECT u FROM Usergroup u WHERE u.name = :name")
    , @NamedQuery(name = "Usergroup.findByCreatedAt", query = "SELECT u FROM Usergroup u WHERE u.createdAt = :createdAt")
    , @NamedQuery(name = "Usergroup.findByResponsibilities", query = "SELECT u FROM Usergroup u WHERE u.responsibilities = :responsibilities")})
public class Usergroup implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idgroups")
    private Integer idgroups;
    @Size(max = 100)
    @Column(name = "name")
    private String name;
    @Column(name = "createdAt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Size(max = 100)
    @Column(name = "responsibilities")
    private String responsibilities;
    @JoinColumn(name = "createdBy", referencedColumnName = "idusers")
    @ManyToOne
    private User createdBy;
    @JoinColumn(name = "statusID", referencedColumnName = "idstatus")
    @ManyToOne
    private Status statusID;

    public Usergroup() {
    }

    public Usergroup(Integer idgroups) {
        this.idgroups = idgroups;
    }

    public Integer getIdgroups() {
        return idgroups;
    }

    public void setIdgroups(Integer idgroups) {
        this.idgroups = idgroups;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getResponsibilities() {
        return responsibilities;
    }

    public void setResponsibilities(String responsibilities) {
        this.responsibilities = responsibilities;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public Status getStatusID() {
        return statusID;
    }

    public void setStatusID(Status statusID) {
        this.statusID = statusID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idgroups != null ? idgroups.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usergroup)) {
            return false;
        }
        Usergroup other = (Usergroup) object;
        if ((this.idgroups == null && other.idgroups != null) || (this.idgroups != null && !this.idgroups.equals(other.idgroups))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.amon.db.Usergroup[ idgroups=" + idgroups + " ]";
    }
    
}
