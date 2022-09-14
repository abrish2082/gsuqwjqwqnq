/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.insurance;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author insa
 */
@Entity
@Table(name = "HR_LU_INSURANCES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrLuInsurances.findAll", query = "SELECT h FROM HrLuInsurances h"),
    @NamedQuery(name = "HrLuInsurances.findByTerminationNameLike", query = "SELECT h FROM HrLuInsurances h WHERE UPPER(h.insuranceName)LIKE :insuranceName"),
    @NamedQuery(name = "HrLuInsurances.findById", query = "SELECT h FROM HrLuInsurances h WHERE h.id = :id"),
    @NamedQuery(name = "HrLuInsurances.findByInsuranceName", query = "SELECT h FROM HrLuInsurances h WHERE h.insuranceName = :insuranceName"),
    @NamedQuery(name = "HrLuInsurances.findByDescription", query = "SELECT h FROM HrLuInsurances h WHERE h.description = :description")})
public class HrLuInsurances implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private BigDecimal id;
    @Size(max = 100)
    @Column(name = "INSURANCE_NAME")
    private String insuranceName;
    @Size(max = 200)
    @Column(name = "DESCRIPTION")
    private String description;
    @OneToMany(mappedBy = "insuranceId")
    private List<HrLuInsuranceBranches> hrLuInsuranceBranchesList;
    @OneToMany(mappedBy = "insuranceId")
    private List<HrInsurance> hrInsuranceList;

    public HrLuInsurances() {
    }

    public HrLuInsurances(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getInsuranceName() {
        return insuranceName;
    }

    public void setInsuranceName(String insuranceName) {
        this.insuranceName = insuranceName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @XmlTransient
    public List<HrLuInsuranceBranches> getHrLuInsuranceBranchesList() {
        return hrLuInsuranceBranchesList;
    }

    public void setHrLuInsuranceBranchesList(List<HrLuInsuranceBranches> hrLuInsuranceBranchesList) {
        this.hrLuInsuranceBranchesList = hrLuInsuranceBranchesList;
    }

    @XmlTransient
    public List<HrInsurance> getHrInsuranceList() {
        return hrInsuranceList;
    }

    public void setHrInsuranceList(List<HrInsurance> hrInsuranceList) {
        this.hrInsuranceList = hrInsuranceList;
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
        if (!(object instanceof HrLuInsurances)) {
            return false;
        }
        HrLuInsurances other = (HrLuInsurances) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return id.toString();

    }
    
}
