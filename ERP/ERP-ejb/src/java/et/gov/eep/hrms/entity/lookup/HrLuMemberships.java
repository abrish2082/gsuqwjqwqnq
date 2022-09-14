/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.lookup;

import java.io.Serializable;
import java.math.BigDecimal;
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
 * @author user
 */
@Entity
@Table(name = "HR_LU_MEMBERSHIPS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrLuMemberships.findAll", query = "SELECT h FROM HrLuMemberships h"),
    @NamedQuery(name = "HrLuMemberships.findById", query = "SELECT h FROM HrLuMemberships h WHERE h.id = :id"),
    @NamedQuery(name = "HrLuMemberships.findByMembership", query = "SELECT h FROM HrLuMemberships h WHERE h.membership = :membership"),
    @NamedQuery(name = "HrLuMemberships.findByDescription", query = "SELECT h FROM HrLuMemberships h WHERE h.description = :description")})
public class HrLuMemberships implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private BigDecimal id;
    @Size(max = 100)
    @Column(name = "MEMBERSHIP")
    private String membership;
    @Size(max = 200)
    @Column(name = "DESCRIPTION")
    private String description;

    public HrLuMemberships() {
    }

    public HrLuMemberships(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getMembership() {
        return membership;
    }

    public void setMembership(String membership) {
        this.membership = membership;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrLuMemberships)) {
            return false;
        }
        HrLuMemberships other = (HrLuMemberships) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return membership;
    }
    
}
