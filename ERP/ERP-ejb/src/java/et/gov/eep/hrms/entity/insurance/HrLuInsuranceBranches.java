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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "HR_LU_INSURANCE_BRANCHES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrLuInsuranceBranches.findAll", query = "SELECT h FROM HrLuInsuranceBranches h"),
    @NamedQuery(name = "HrLuInsuranceBranches.findById", query = "SELECT h FROM HrLuInsuranceBranches h WHERE h.id = :id"),
    @NamedQuery(name = "HrLuInsuranceBranches.insu", query = "SELECT h FROM HrLuInsuranceBranches h WHERE h.insuranceId.id = :sid"),
    @NamedQuery(name = "HrLuInsuranceBranches.findByBranchName", query = "SELECT h FROM HrLuInsuranceBranches h WHERE h.branchName = :branchName")})
public class HrLuInsuranceBranches implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private BigDecimal id;
    @Size(max = 100)
    @Column(name = "BRANCH_NAME")
    private String branchName;
    @JoinColumn(name = "INSURANCE_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrLuInsurances insuranceId;
    @OneToMany(mappedBy = "branchId")
    private List<HrInsurance> hrInsuranceList;

    public HrLuInsuranceBranches() {
    }

    public HrLuInsuranceBranches(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public HrLuInsurances getInsuranceId() {
        return insuranceId;
    }

    public void setInsuranceId(HrLuInsurances insuranceId) {
        this.insuranceId = insuranceId;
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
        if (!(object instanceof HrLuInsuranceBranches)) {
            return false;
        }
        HrLuInsuranceBranches other = (HrLuInsuranceBranches) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.insurance.HrLuInsuranceBranches[ id=" + id + " ]";

    }
    
}
