/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.lookup;

import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.prms.entity.PrmsBidOpeningChecklstDt;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
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
import javax.persistence.OneToMany;
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
@Table(name = "HR_LU_BANK_BRANCHES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrLuBankBranches.findAll", query = "SELECT h FROM HrLuBankBranches h"),
    @NamedQuery(name = "HrLuBankBranches.findById", query = "SELECT h FROM HrLuBankBranches h WHERE h.id = :id"),    
    @NamedQuery(name = "HrLuBankBranches.findByBankId", query = "SELECT h FROM HrLuBankBranches h WHERE h.bankId.id = :bankId"),
    @NamedQuery(name = "HrLuBankBranches.findByBranchName", query = "SELECT h FROM HrLuBankBranches h WHERE h.branchName = :branchName")})
public class HrLuBankBranches implements Serializable {
//    @Column(name = "BANK_ID")
//    private BigInteger bankId;
    
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @Column(name = "ID")
    private BigDecimal id;
    @Size(max = 50)
    @Column(name = "BRANCH_NAME")
    private String branchName;
    @JoinColumn(name = "BANK_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrLuBanks bankId;
    @OneToMany(mappedBy = "bankBranch")
    private List<HrEmployees> hrEmployeesList;
       @OneToMany(mappedBy = "bankBranchId")
    private List<PrmsBidOpeningChecklstDt> prmsBidOpeningChecklstDts;

   
    public HrLuBankBranches() {
    }

    /**
     *
     * @param id
     */
    public HrLuBankBranches(BigDecimal id) {
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
    public String getBranchName() {
        return branchName;
    }

    /**
     *
     * @param branchName
     */
    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    /**
     *
     * @return
     */
    public HrLuBanks getBankId() {
        return bankId;
    }
  
    /**
     *
     * @param bankId
     */
    public void setBankId(HrLuBanks bankId) {
        this.bankId = bankId;
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
        if (!(object instanceof HrLuBankBranches)) {
            return false;
        }
        HrLuBankBranches other = (HrLuBankBranches) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return branchName;
    }

    /**
     *
     * @return
     */
    @XmlTransient
    public List<HrEmployees> getHrEmployeesList() {
        return hrEmployeesList;
    }

    /**
     *
     * @param hrEmployeesList
     */
    public void setHrEmployeesList(List<HrEmployees> hrEmployeesList) {
        this.hrEmployeesList = hrEmployeesList;
    }
     @XmlTransient

    public List<PrmsBidOpeningChecklstDt> getPrmsBidOpeningChecklstDts() {
        return prmsBidOpeningChecklstDts;
    }

    public void setPrmsBidOpeningChecklstDts(List<PrmsBidOpeningChecklstDt> prmsBidOpeningChecklstDts) {
        this.prmsBidOpeningChecklstDts = prmsBidOpeningChecklstDts;
    }

//    public BigInteger getBankId() {
//        return bankId;
//    }
//
//    public void setBankId(BigInteger bankId) {
//        this.bankId = bankId;
//    }


    }
