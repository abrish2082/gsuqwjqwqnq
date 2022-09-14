/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.entity.admin;

import java.io.Serializable;
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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import et.gov.eep.fcms.entity.budget.FmsBudgetCode;
import et.gov.eep.fcms.entity.budget.FmsBudgetControl;
import et.gov.eep.fcms.entity.budget.FmsOperatingBudgetDetail;
import et.gov.eep.fcms.entity.fixedasset.FmsDprBuilding;
import et.gov.eep.fcms.entity.fixedasset.FmsDprGeothermal;
import et.gov.eep.fcms.entity.fixedasset.FmsDprHydropower;
import et.gov.eep.fcms.entity.fixedasset.FmsDprSubstation;
import et.gov.eep.fcms.entity.fixedasset.FmsDprTransmisson;
import et.gov.eep.fcms.entity.fixedasset.FmsDprWind;
import et.gov.eep.fcms.entity.loan.FmsLoanCommAccounts;
import et.gov.eep.fcms.entity.loan.FmsLoanDisbAccount;
import et.gov.eep.fcms.entity.loan.FmsPrincipalPayAccounts;
import et.gov.eep.hrms.entity.payroll.HrPayrollEarningDeductions;
import et.gov.eep.mms.entity.MmsFixedassetRegstDetail;
import et.gov.eep.pms.entity.PmsCreateProjects;
import et.gov.eep.prms.entity.PrmsRevenueContarct;
import javax.persistence.Transient;

/**
 *
 * @author user
 */
@Entity
@Table(name = "FMS_GENERAL_LEDGER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsGeneralLedger.findAll", query = "SELECT f FROM FmsGeneralLedger f ORDER BY f.generalLedgerCode"),
    @NamedQuery(name = "FmsGeneralLedger.findByGeneralLedgerId", query = "SELECT f FROM FmsGeneralLedger f WHERE f.generalLedgerId = :generalLedgerId"),
    @NamedQuery(name = "FmsGeneralLedger.findByGeneralLedgerCode", query = "SELECT f FROM FmsGeneralLedger f WHERE f.generalLedgerCode = :generalLedgerCode"),
    @NamedQuery(name = "FmsGeneralLedger.findByGeneralLedgerCodeLike", query = "SELECT f FROM FmsGeneralLedger f WHERE f.generalLedgerCode LIKE :generalLedgerCode"),
    @NamedQuery(name = "FmsGeneralLedger.findByAccountTitle", query = "SELECT f FROM FmsGeneralLedger f WHERE f.accountTitle = :accountTitle"),
    @NamedQuery(name = "FmsGeneralLedger.findProjGLAccount", query = "SELECT f FROM FmsGeneralLedger f WHERE f.accountType = 1 OR f.accountType = 2"),
    @NamedQuery(name = "FmsGeneralLedger.findOprGLAccount", query = "SELECT f FROM FmsGeneralLedger f WHERE f.accountType = 1 OR  f.accountType = 3"),
    @NamedQuery(name = "FmsGeneralLedger.findByAccountType", query = "SELECT f FROM FmsGeneralLedger f WHERE f.accountType = :accountType"),
    @NamedQuery(name = "FmsGeneralLedger.findByStatus", query = "SELECT f FROM FmsGeneralLedger f WHERE f.status = :status")})
public class FmsGeneralLedger implements Serializable {

    @Column(name = "ACCOUNT_TYPE")
    private Integer accountType;
    @JoinColumn(name = "PROJECT_ID", referencedColumnName = "PROJECT_ID")
    @ManyToOne
    private PmsCreateProjects projectId;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FMS_SEQ_GENERAL_LEDGER")
    @SequenceGenerator(name = "FMS_SEQ_GENERAL_LEDGER", sequenceName = "FMS_SEQ_GENERAL_LEDGER", allocationSize = 1)
    @NotNull
    @Column(name = "GENERAL_LEDGER_ID")
    private Integer generalLedgerId;
    @Size(max = 45)
    @Column(name = "GENERAL_LEDGER_CODE")
    private String generalLedgerCode;
    @Size(max = 200)
    @Column(name = "ACCOUNT_TITLE")
    private String accountTitle;
    @Size(max = 45)
    @Column(name = "STATUS")
    private String status;
    @OneToMany(mappedBy = "generalLedgerId", cascade = CascadeType.ALL)
    private List<FmsSubsidiaryLedger> fmsSubsid1aryLedger1List;
    @OneToMany(mappedBy = "generalLedgerId")
    private List<HrPayrollEarningDeductions> hrPayrollEarningDeductionsList;
    @OneToMany(mappedBy = "accountNo")
    private List<FmsDprSubstation> fmsDprSubstationList;
    @OneToMany(mappedBy = "accountNo")
    private List<FmsDprGeothermal> fmsDprGeothermalList;
    @OneToMany(mappedBy = "accountNo")
    private List<FmsDprHydropower> fmsDprHydropowerList;
    @OneToMany(mappedBy = "accountNo")
    private List<FmsDprWind> fmsDprWindList;
    @OneToMany(mappedBy = "accountNo")
    private List<FmsDprBuilding> fmsDprBuildingList;
    @OneToMany(mappedBy = "accountNoTrns")
    private List<FmsDprTransmisson> fmsDprTransmissonList;
    @OneToMany(mappedBy = "accountCode")
    private List<MmsFixedassetRegstDetail> fixedAssetRegDtlList;
    @OneToMany(mappedBy = "generalLedger")
    private List<FmsBudgetCode> fmsBudgetCodeList;
    @OneToMany(mappedBy = "generalLedger", fetch = FetchType.LAZY)
    private List<FmsOperatingBudgetDetail> fmsOperatingBudgetDetailList;
    @JoinColumn(name = "ACCOUNT_CATEGORY", referencedColumnName = "ID")
    @ManyToOne
    private FmsAccountType accountCategory;
    @OneToMany(mappedBy = "generalLedgerIdFk")
    private List<FmsPrincipalPayAccounts> fmsPrincipalPayAccountsList;
    @OneToMany(mappedBy = "generalLedgerFk")
    private List<FmsLoanCommAccounts> fmsLoanCommAccountsList;
    @OneToMany(mappedBy = "generalLedgerFk")
    private List<FmsLoanDisbAccount> fmsLoanDisbAccountList;
    @OneToMany(mappedBy = "generalLedgerId")
    private List<FmsBudgetControl> fmsBudgetControlList;
    @OneToMany(mappedBy = "glId", fetch = FetchType.LAZY)
    private List<PrmsRevenueContarct> prmsRevenueContarctList;

    public FmsGeneralLedger() {
    }

    /**
     *
     * @param generalLedgerId
     */
    public FmsGeneralLedger(Integer generalLedgerId) {
        this.generalLedgerId = generalLedgerId;
    }
    @Transient
    private String columnName;

    @Transient
    private String columnValue;

    public String getColumnValue() {
        return columnValue;
    }

    public void setColumnValue(String columnValue) {
        this.columnValue = columnValue;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    /**
     *
     * @return
     */
    public Integer getGeneralLedgerId() {
        return generalLedgerId;
    }

    /**
     *
     * @param generalLedgerId
     */
    public void setGeneralLedgerId(Integer generalLedgerId) {
        this.generalLedgerId = generalLedgerId;
    }

    /**
     *
     * @return
     */
    public String getGeneralLedgerCode() {
        return generalLedgerCode;
    }

    /**
     *
     * @param generalLedgerCode
     */
    public void setGeneralLedgerCode(String generalLedgerCode) {
        this.generalLedgerCode = generalLedgerCode;
    }

    public List<MmsFixedassetRegstDetail> getFixedAssetRegDtlList() {
        return fixedAssetRegDtlList;
    }

    public void setFixedAssetRegDtlList(List<MmsFixedassetRegstDetail> fixedAssetRegDtlList) {
        this.fixedAssetRegDtlList = fixedAssetRegDtlList;
    }

    public List<FmsBudgetCode> getFmsBudgetCodeList() {
        return fmsBudgetCodeList;
    }

    public void setFmsBudgetCodeList(List<FmsBudgetCode> fmsBudgetCodeList) {
        this.fmsBudgetCodeList = fmsBudgetCodeList;
    }

    /**
     *
     * @return
     */
    public String getAccountTitle() {
        return accountTitle;
    }

    /**
     *
     * @param accountTitle
     */
    public void setAccountTitle(String accountTitle) {
        this.accountTitle = accountTitle;
    }

    public String getStatus() {
        return status;
    }

    /**
     *
     * @param status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     *
     * @return
     */
    public FmsAccountType getAccountCategory() {
        return accountCategory;
    }

    public void setAccountCategory(FmsAccountType accountCategory) {
        this.accountCategory = accountCategory;
    }

    public Integer getAccountType() {
        return accountType;
    }

    public void setAccountType(Integer accountType) {
        this.accountType = accountType;
    }

    public PmsCreateProjects getProjectId() {
        return projectId;
    }

    public void setProjectId(PmsCreateProjects projectId) {
        this.projectId = projectId;
    }

    @XmlTransient
    public List<PrmsRevenueContarct> getPrmsRevenueContarctList() {
        if (prmsRevenueContarctList == null) {
            prmsRevenueContarctList = new ArrayList<>();
        }
        return prmsRevenueContarctList;
    }

    public void setPrmsRevenueContarctList(List<PrmsRevenueContarct> prmsRevenueContarctList) {
        this.prmsRevenueContarctList = prmsRevenueContarctList;
    }

    @XmlTransient
    public List<FmsSubsidiaryLedger> getFmsSubsid1aryLedger1List() {
        return fmsSubsid1aryLedger1List;
    }

    /**
     *
     * @param fmsSubsid1aryLedger1List
     */
    public void setFmsSubsid1aryLedger1List(List<FmsSubsidiaryLedger> fmsSubsid1aryLedger1List) {
        this.fmsSubsid1aryLedger1List = fmsSubsid1aryLedger1List;
    }

    @XmlTransient
    public List<FmsDprSubstation> getFmsDprSubstationList() {
        return fmsDprSubstationList;
    }

    public void setFmsDprSubstationList(List<FmsDprSubstation> fmsDprSubstationList) {
        this.fmsDprSubstationList = fmsDprSubstationList;
    }

    @XmlTransient
    public List<FmsDprGeothermal> getFmsDprGeothermalList() {
        return fmsDprGeothermalList;
    }

    public void setFmsDprGeothermalList(List<FmsDprGeothermal> fmsDprGeothermalList) {
        this.fmsDprGeothermalList = fmsDprGeothermalList;
    }

    @XmlTransient
    public List<FmsDprHydropower> getFmsDprHydropowerList() {
        return fmsDprHydropowerList;
    }

    public void setFmsDprHydropowerList(List<FmsDprHydropower> fmsDprHydropowerList) {
        this.fmsDprHydropowerList = fmsDprHydropowerList;
    }

    @XmlTransient
    public List<FmsDprWind> getFmsDprWindList() {
        return fmsDprWindList;
    }

    public void setFmsDprWindList(List<FmsDprWind> fmsDprWindList) {
        this.fmsDprWindList = fmsDprWindList;
    }

    @XmlTransient
    public List<FmsDprBuilding> getFmsDprBuildingList() {
        return fmsDprBuildingList;
    }

    public void setFmsDprBuildingList(List<FmsDprBuilding> fmsDprBuildingList) {
        this.fmsDprBuildingList = fmsDprBuildingList;
    }

    @XmlTransient
    public List<FmsDprTransmisson> getFmsDprTransmissonList() {
        return fmsDprTransmissonList;
    }

    public void setFmsDprTransmissonList(List<FmsDprTransmisson> fmsDprTransmissonList) {
        this.fmsDprTransmissonList = fmsDprTransmissonList;
    }

    @XmlTransient
    public List<HrPayrollEarningDeductions> getHrPayrollEarningDeductionsList() {
        return hrPayrollEarningDeductionsList;
    }

    public void setHrPayrollEarningDeductionsList(List<HrPayrollEarningDeductions> hrPayrollEarningDeductionsList) {
        this.hrPayrollEarningDeductionsList = hrPayrollEarningDeductionsList;
    }

    @XmlTransient
    public List<FmsOperatingBudgetDetail> getFmsOperatingBudgetDetailList() {
        return fmsOperatingBudgetDetailList;
    }

    public void setFmsOperatingBudgetDetailList(List<FmsOperatingBudgetDetail> fmsOperatingBudgetDetailList) {
        this.fmsOperatingBudgetDetailList = fmsOperatingBudgetDetailList;
    }

    @XmlTransient
    public List<FmsBudgetControl> getFmsBudgetControlList() {
        return fmsBudgetControlList;
    }

    public void setFmsBudgetControlList(List<FmsBudgetControl> fmsBudgetControlList) {
        this.fmsBudgetControlList = fmsBudgetControlList;
    }

    @XmlTransient
    public List<FmsLoanCommAccounts> getFmsLoanCommAccountsList() {
        return fmsLoanCommAccountsList;
    }

    public void setFmsLoanCommAccountsList(List<FmsLoanCommAccounts> fmsLoanCommAccountsList) {
        this.fmsLoanCommAccountsList = fmsLoanCommAccountsList;
    }

    @XmlTransient
    public List<FmsLoanDisbAccount> getFmsLoanDisbAccountList() {
        return fmsLoanDisbAccountList;
    }

    public void setFmsLoanDisbAccountList(List<FmsLoanDisbAccount> fmsLoanDisbAccountList) {
        this.fmsLoanDisbAccountList = fmsLoanDisbAccountList;
    }

    @XmlTransient
    public List<FmsPrincipalPayAccounts> getFmsPrincipalPayAccountsList() {
        return fmsPrincipalPayAccountsList;
    }

    public void setFmsPrincipalPayAccountsList(List<FmsPrincipalPayAccounts> fmsPrincipalPayAccountsList) {
        this.fmsPrincipalPayAccountsList = fmsPrincipalPayAccountsList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (generalLedgerId != null ? generalLedgerId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FmsGeneralLedger)) {
            return false;
        }
        FmsGeneralLedger other = (FmsGeneralLedger) object;
        if ((this.generalLedgerId == null && other.generalLedgerId != null) || (this.generalLedgerId != null && !this.generalLedgerId.equals(other.generalLedgerId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return generalLedgerCode;
    }

}
