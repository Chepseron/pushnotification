/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amon.db;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 * @author amon.sabul
 */
@Entity
@Table(name = "outlet")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Outlet.findAll", query = "SELECT o FROM Outlet o")
    , @NamedQuery(name = "Outlet.findByIdoutlet", query = "SELECT o FROM Outlet o WHERE o.idoutlet = :idoutlet")
    , @NamedQuery(name = "Outlet.findByCreatedBy", query = "SELECT o FROM Outlet o WHERE o.createdBy = :createdBy")
    , @NamedQuery(name = "Outlet.findByCreatedOn", query = "SELECT o FROM Outlet o WHERE o.createdOn = :createdOn")
    , @NamedQuery(name = "Outlet.findByOutletname", query = "SELECT o FROM Outlet o WHERE o.outletname = :outletname")
    , @NamedQuery(name = "Outlet.findByAddress", query = "SELECT o FROM Outlet o WHERE o.address = :address")})
public class Outlet implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idoutlet")
    private Integer idoutlet;
    @Basic(optional = false)
    @NotNull
    @Column(name = "createdBy")
    private int createdBy;
    @Basic(optional = false)
    @NotNull
    @Column(name = "createdOn")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "outletname")
    private String outletname;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "address")
    private String address;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "outletID")
    private Collection<Transactions> transactionsCollection;
    @OneToMany(mappedBy = "outlet")
    private Collection<User> userCollection;

    public Outlet() {
    }

    public Outlet(Integer idoutlet) {
        this.idoutlet = idoutlet;
    }

    public Outlet(Integer idoutlet, int createdBy, Date createdOn, String outletname, String address) {
        this.idoutlet = idoutlet;
        this.createdBy = createdBy;
        this.createdOn = createdOn;
        this.outletname = outletname;
        this.address = address;
    }

    public Integer getIdoutlet() {
        return idoutlet;
    }

    public void setIdoutlet(Integer idoutlet) {
        this.idoutlet = idoutlet;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public String getOutletname() {
        return outletname;
    }

    public void setOutletname(String outletname) {
        this.outletname = outletname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @XmlTransient
    @JsonIgnore
    public Collection<Transactions> getTransactionsCollection() {
        return transactionsCollection;
    }

    public void setTransactionsCollection(Collection<Transactions> transactionsCollection) {
        this.transactionsCollection = transactionsCollection;
    }

    @XmlTransient
    @JsonIgnore
    public Collection<User> getUserCollection() {
        return userCollection;
    }

    public void setUserCollection(Collection<User> userCollection) {
        this.userCollection = userCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idoutlet != null ? idoutlet.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Outlet)) {
            return false;
        }
        Outlet other = (Outlet) object;
        if ((this.idoutlet == null && other.idoutlet != null) || (this.idoutlet != null && !this.idoutlet.equals(other.idoutlet))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.amon.db.Outlet[ idoutlet=" + idoutlet + " ]";
    }
    
}
