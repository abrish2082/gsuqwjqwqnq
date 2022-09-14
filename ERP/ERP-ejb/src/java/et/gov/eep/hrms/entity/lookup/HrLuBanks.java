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
import java.util.ArrayList;
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
@Table(name = "HR_LU_BANKS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrLuBanks.findAll", query = "SELECT h FROM HrLuBanks h"),
    @NamedQuery(name = "HrLuBanks.findById", query = "SELECT h FROM HrLuBanks h WHERE h.id = :id"),
    @NamedQuery(name = "HrLuBanks.findByBankName", query = "SELECT h FROM HrLuBanks h WHERE h.bankName = :bankName"),
    @NamedQuery(name = "HrLuBanks.findByDescription", query = "SELECT h FROM HrLuBanks h WHERE h.description = :description")})
public class HrLuBanks implements Serializable {

    @OneToMany(mappedBy = "bankName")
    private List<HrEmployees> hrEmployeesList;
    @OneToMany(mappedBy = "bankId")
    private List<PrmsBidOpeningChecklstDt> prmsBidOpeningChecklstDts;
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @Column(name = "ID")
    private BigDecimal id;
    @Size(max = 50)
    @Column(name = "BANK_NAME")
    private String bankName;
    @Size(max = 50)
    @Column(name = "DESCRIPTION")
    private String description;
    @OneToMany(mappedBy = "bankId")
    private List<HrLuBankBranches> hrLuBankBranchesList;

    /**
     *
     */
    public HrLuBanks() {
    }

    /**
     *
     * @param id
     */
    public HrLuBanks(BigDecimal id) {
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
    public String getBankName() {
        return bankName;
    }

    /**
     *
     * @param bankName
     */
    public void setBankName(String bankName) {
        this.bankName = bankName;
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
    public List<HrLuBankBranches> getHrLuBankBranchesList() {
        return hrLuBankBranchesList;
    }

    /**
     *
     * @param hrLuBankBranchesList
     */
    public void setHrLuBankBranchesList(List<HrLuBankBranches> hrLuBankBranchesList) {
        this.hrLuBankBranchesList = hrLuBankBranchesList;
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
        if (!(object instanceof HrLuBanks)) {
            return false;
        }
        HrLuBanks other = (HrLuBanks) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return bankName;
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
        if (prmsBidOpeningChecklstDts == null) {
            prmsBidOpeningChecklstDts = new ArrayList<>();
        }
        return prmsBidOpeningChecklstDts;
    }

    public void setPrmsBidOpeningChecklstDts(List<PrmsBidOpeningChecklstDt> prmsBidOpeningChecklstDts) {
        this.prmsBidOpeningChecklstDts = prmsBidOpeningChecklstDts;
    }

}
