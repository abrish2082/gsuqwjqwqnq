/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.lookup;

import et.gov.eep.hrms.entity.committee.HrCommittees;
import et.gov.eep.prms.entity.PrmsBidOpeningCheckList;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author user
 */
@Entity
@Table(name = "HR_LU_COMMITTEE_TYPES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrLuCommitteeTypes.findAll", query = "SELECT h FROM HrLuCommitteeTypes h"),
    @NamedQuery(name = "HrLuCommitteeTypes.findById", query = "SELECT h FROM HrLuCommitteeTypes h WHERE h.id = :id"),
    @NamedQuery(name = "HrLuCommitteeTypes.findByDescription", query = "SELECT h FROM HrLuCommitteeTypes h WHERE h.description = :description")})
public class HrLuCommitteeTypes implements Serializable {
    @OneToOne(mappedBy = "committeeTypeId", fetch = FetchType.LAZY)
    private HrCommittees hrCommittees;

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_LU_COMMITTEE_TYPES_SEQ_GENERATOR")
    @SequenceGenerator(name = "HR_LU_COMMITTEE_TYPES_SEQ_GENERATOR", sequenceName = "HR_LU_COMMITTEE_TYPES_SEQ", allocationSize = 1)
    private BigDecimal id;

    @Size(max = 100)
    @Column(name = "DESCRIPTION")
    private String description;

    @OneToMany(mappedBy = "committeeTypeId",cascade = CascadeType.ALL)
    private List<HrCommittees> hrCommitteesList = new ArrayList<>();

    /**
     *
     */
    public HrLuCommitteeTypes() {
    }

    /**
     *
     * @param id
     */
    public HrLuCommitteeTypes(BigDecimal id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public BigDecimal getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(BigDecimal id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     *
     * @return
     */
    @XmlTransient
    public List<HrCommittees> getHrCommitteesList() {
        return hrCommitteesList;
    }

    /**
     *
     * @param hrCommitteesList
     */
    public void setHrCommitteesList(List<HrCommittees> hrCommitteesList) {
        this.hrCommitteesList = hrCommitteesList;
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
        if (!(object instanceof HrLuCommitteeTypes)) {
            return false;
        }
        HrLuCommitteeTypes other = (HrLuCommitteeTypes) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return description;//"et.gov.eep.hrms.entity.HrLuCommitteeTypes[ id=" + id + " ]";
    }

    public HrCommittees getHrCommittees() {
        return hrCommittees;
    }

    public void setHrCommittees(HrCommittees hrCommittees) {
        this.hrCommittees = hrCommittees;
    }

}
