/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.termination;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author INSA
 */
@Entity
@Table(name = "HR_TERMINATION_TYPES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrTerminationTypes.findAll", query = "SELECT h FROM HrTerminationTypes h"),
    @NamedQuery(name = "HrTerminationTypes.findById", query = "SELECT h FROM HrTerminationTypes h WHERE h.id = :id"),
    @NamedQuery(name = "HrTerminationTypes.findByTerminationName", query = "SELECT h FROM HrTerminationTypes h WHERE h.terminationName = :terminationName"),
    @NamedQuery(name = "HrTerminationTypes.findByTerminationNameLike", query = "SELECT h FROM HrTerminationTypes h WHERE UPPER(h.terminationName) LIKE :terminationName"),
    @NamedQuery(name = "HrTerminationTypes.findByDescription", query = "SELECT h FROM HrTerminationTypes h WHERE h.description = :description")})
public class HrTerminationTypes implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_TERMINATION_TYPES_SEQ")
    @SequenceGenerator(name = "HR_TERMINATION_TYPES_SEQ", sequenceName = "HR_TERMINATION_TYPES_SEQ", allocationSize = 1)
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private BigDecimal id;
    @Size(max = 20)
    @Column(name = "TERMINATION_NAME")
    private String terminationName;
    @Size(max = 200)
    @Column(name = "DESCRIPTION")
    private String description;
    @OneToMany(mappedBy = "terminationType")
    private List<HrTerminationRequests> hrTerminationRequestsList;

    public HrTerminationTypes() {
    }

    public HrTerminationTypes(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getTerminationName() {
        return terminationName;
    }

    public void setTerminationName(String terminationName) {
        this.terminationName = terminationName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @XmlTransient
    public List<HrTerminationRequests> getHrTerminationRequestsList() {
        return hrTerminationRequestsList;
    }

    public void setHrTerminationRequestsList(List<HrTerminationRequests> hrTerminationRequestsList) {
        this.hrTerminationRequestsList = hrTerminationRequestsList;
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
        if (!(object instanceof HrTerminationTypes)) {
            return false;
        }
        HrTerminationTypes other = (HrTerminationTypes) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return terminationName;
    }
    
}
