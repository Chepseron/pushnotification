/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amon.db;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author amon.sabul
 */
@Entity
@Table(name = "endpoint")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Endpoint.findAll", query = "SELECT e FROM Endpoint e")
    , @NamedQuery(name = "Endpoint.findByIdendpoint", query = "SELECT e FROM Endpoint e WHERE e.idendpoint = :idendpoint")
    , @NamedQuery(name = "Endpoint.findByEndpointName", query = "SELECT e FROM Endpoint e WHERE e.endpointName = :endpointName")
    , @NamedQuery(name = "Endpoint.findByUrltoendpoint", query = "SELECT e FROM Endpoint e WHERE e.urltoendpoint = :urltoendpoint")})
public class Endpoint implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idendpoint")
    private Integer idendpoint;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "endpointName")
    private String endpointName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "urltoendpoint")
    private String urltoendpoint;

    public Endpoint() {
    }

    public Endpoint(Integer idendpoint) {
        this.idendpoint = idendpoint;
    }

    public Endpoint(Integer idendpoint, String endpointName, String urltoendpoint) {
        this.idendpoint = idendpoint;
        this.endpointName = endpointName;
        this.urltoendpoint = urltoendpoint;
    }

    public Integer getIdendpoint() {
        return idendpoint;
    }

    public void setIdendpoint(Integer idendpoint) {
        this.idendpoint = idendpoint;
    }

    public String getEndpointName() {
        return endpointName;
    }

    public void setEndpointName(String endpointName) {
        this.endpointName = endpointName;
    }

    public String getUrltoendpoint() {
        return urltoendpoint;
    }

    public void setUrltoendpoint(String urltoendpoint) {
        this.urltoendpoint = urltoendpoint;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idendpoint != null ? idendpoint.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Endpoint)) {
            return false;
        }
        Endpoint other = (Endpoint) object;
        if ((this.idendpoint == null && other.idendpoint != null) || (this.idendpoint != null && !this.idendpoint.equals(other.idendpoint))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.amon.db.Endpoint[ idendpoint=" + idendpoint + " ]";
    }
    
}
