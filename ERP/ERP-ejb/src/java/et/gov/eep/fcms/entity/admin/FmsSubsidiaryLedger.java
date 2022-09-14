/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.entity.admin;

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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
import et.gov.eep.fcms.entity.FmsBeginningBalance;
import et.gov.eep.fcms.entity.Ifrs.FinancialInstrumentRegister;
import et.gov.eep.fcms.entity.bank.FmsBankBranchAccounts;
import et.gov.eep.fcms.entity.budget.FmsBudgetControl;
import et.gov.eep.fcms.entity.budget.FmsCapitalBudgetTasks;
import et.gov.eep.fcms.entity.budget.FmsOperatingBudgetTasks;
import et.gov.eep.fcms.entity.loan.FmsLoan;
import et.gov.eep.fcms.entity.loan.FmsLoanCommAccounts;
import et.gov.eep.fcms.entity.loan.FmsLoanDisbAccount;
import et.gov.eep.fcms.entity.loan.FmsPrincipalPayAccounts;
import et.gov.eep.fcms.entity.pettyCash.FmsCasherAccount;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.mms.entity.IfrsFixedAsset;
import et.gov.eep.pms.entity.PmsWorkAuthorization;
import et.gov.eep.prms.entity.PrmsRevenueContarct;
import javax.persistence.Transient;

/**
 *
 * @author user
 */
@Entity
@Table(name = "FMS_SUBSIDIARY_LEDGER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsSubsidiaryLedger.findAll", query = "SELECT f FROM FmsSubsidiaryLedger f"),
    @NamedQuery(name = "FmsSubsidiaryLedger.findBySubsidiaryId", query = "SELECT f FROM FmsSubsidiaryLedger f WHERE f.subsidiaryId = :subsidiaryId"),
    @NamedQuery(name = "FmsSubsidiaryLedger.findBySubsidiaryCode", query = "SELECT f FROM FmsSubsidiaryLedger f WHERE f.subsidiaryCode = :subsidiaryCode"),
    @NamedQuery(name = "FmsSubsidiaryLedger.findBySubsidiaryCodeLike", query = "SELECT f FROM FmsSubsidiaryLedger f WHERE f.subsidiaryCode LIKE :subsidiaryCode"),
    @NamedQuery(name = "FmsSubsidiaryLedger.findByAccountTitle", query = "SELECT f FROM FmsSubsidiaryLedger f WHERE f.accountTitle = :accountTitle"),
    @NamedQuery(name = "FmsSubsidiaryLedger.findByDebit", query = "SELECT f FROM FmsSubsidiaryLedger f WHERE f.debit = :debit"),
    @NamedQuery(name = "FmsSubsidiaryLedger.findByCredit", query = "SELECT f FROM FmsSubsidiaryLedger f WHERE f.credit = :credit"),
    @NamedQuery(name = "FmsSubsidiaryLedger.findByStatus", query = "SELECT f FROM FmsSubsidiaryLedger f WHERE f.status = :status"),
    @NamedQuery(name = "FmsSubsidiaryLedger.findByGeneralLedgerCCSS", query = "SELECT f FROM FmsSubsidiaryLedger f WHERE f.generalLedgerId = :generalLedgerId AND f.ssCcJunction = :ssCcJunction"),
    @NamedQuery(name = "FmsSubsidiaryLedger.findByGeneralLedgerId", query = "SELECT f FROM FmsSubsidiaryLedger f WHERE f.generalLedgerId = :generalLedgerId")})
public class FmsSubsidiaryLedger implements Serializable {

    @Column(name = "DEBIT")
    private BigDecimal debit;
    @Column(name = "CREDIT")
    private BigDecimal credit;
    @OneToMany(mappedBy = "subsidiaryIds", fetch = FetchType.LAZY)
    private List<PrmsRevenueContarct> prmsRevenueContarctList;
    @OneToMany(mappedBy = "subsidiaryLedger")
    private List<FmsLoan> fmsLoanList;
    @JoinColumn(name = "SS_CC_JUNCTION", referencedColumnName = "ID")
    @ManyToOne
    private FmsCostcSystemJunction ssCcJunction;
    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "slId")
    private List<FmsOperatingBudgetTasks> fmsOperatingBudgetTasksList;
    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "slId")
    private List<FmsCapitalBudgetTasks> fmsCapitalBudgetTasksList;
    @OneToMany(mappedBy = "subsidiaryLedgerIdFk")
    private List<FmsPrincipalPayAccounts> fmsPrincipalPayAccountsList;
    @OneToMany(mappedBy = "subsidiaryLedgerFk")
    private List<FmsLoanCommAccounts> fmsLoanCommAccountsList;
    @OneToMany(mappedBy = "subsidiaryLedgerFk")
    private List<FmsLoanDisbAccount> fmsLoanDisbAccountList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "subsidiaryId")
    private List<FmsBudgetControl> fmsBudgetControlList;
    @OneToMany(mappedBy = "subsidiaryLedger")
    private List<FinancialInstrumentRegister> financialInstrumentRegisterList;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "SUBSIDIARY_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FMS_SEQ_SUBSIDIARY_LEDGER")
    @SequenceGenerator(name = "FMS_SEQ_SUBSIDIARY_LEDGER", sequenceName = "FMS_SEQ_SUBSIDIARY_LEDGER", allocationSize = 1)
    private Integer subsidiaryId;
    @Size(max = 45)
    @Column(name = "SUBSIDIARY_CODE")
    @NotNull
    private String subsidiaryCode;
    @Size(max = 20)
    @Column(name = "ACCOUNT_TITLE")
    @NotNull
    private String accountTitle;
    @Size(max = 20)
    @Column(name = "STATUS")
    private String status;
    @JoinColumn(name = "GENERAL_LEDGER_ID", referencedColumnName = "GENERAL_LEDGER_ID")
    @ManyToOne
    private FmsGeneralLedger generalLedgerId;
    @JoinColumn(name = "JOBID", referencedColumnName = "WORK_AUTHO_ID")
    @ManyToOne
    private PmsWorkAuthorization jobid;
    @JoinColumn(name = "EMP_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrEmployees empId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "slCode")
    private List<FmsBeginningBalance> fmsBeginningBalanceList;
    @OneToOne(mappedBy = "subsidiaryId")
    private IfrsFixedAsset fixedassetList;
    @OneToOne(mappedBy = "subsidiaryId")
    private FmsCasherAccount fmsCasherAccount;
    @OneToOne(mappedBy = "subsidiaryId")
    private FmsBankBranchAccounts fmsBankBranchAccounts;

    public FmsSubsidiaryLedger() {
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

    public FmsSubsidiaryLedger(Integer subsidiaryId) {
        this.subsidiaryId = subsidiaryId;
    }

    public Integer getSubsidiaryId() {
        return subsidiaryId;
    }

    public void setSubsidiaryId(Integer subsidiaryId) {
        this.subsidiaryId = subsidiaryId;
    }

    public String getSubsidiaryCode() {
        return subsidiaryCode;
    }

    public void setSubsidiaryCode(String subsidiaryCode) {
        this.subsidiaryCode = subsidiaryCode;
    }

    public String getAccountTitle() {
        return accountTitle;
    }

    public void setAccountTitle(String accountTitle) {
        this.accountTitle = accountTitle;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public FmsGeneralLedger getGeneralLedgerId() {
        if (generalLedgerId == null) {
            generalLedgerId = new FmsGeneralLedger();
        }
        return generalLedgerId;
    }

    public void setGeneralLedgerId(FmsGeneralLedger generalLedgerId) {
        this.generalLedgerId = generalLedgerId;
    }

    public HrEmployees getEmpId() {
        return empId;
    }

    public void setEmpId(HrEmployees empId) {
        this.empId = empId;
    }

    public PmsWorkAuthorization getJobid() {
        return jobid;
    }

    public void setJobid(PmsWorkAuthorization jobid) {
        this.jobid = jobid;
    }

    public IfrsFixedAsset getFixedassetList() {
        return fixedassetList;
    }

    public void setFixedassetList(IfrsFixedAsset fixedassetList) {
        this.fixedassetList = fixedassetList;
    }

    public FmsCasherAccount getFmsCasherAccount() {
        return fmsCasherAccount;
    }

    public void setFmsCasherAccount(FmsCasherAccount fmsCasherAccount) {
        this.fmsCasherAccount = fmsCasherAccount;
    }

    public FmsBankBranchAccounts getFmsBankBranchAccounts() {
        return fmsBankBranchAccounts;
    }

    public void setFmsBankBranchAccounts(FmsBankBranchAccounts fmsBankBranchAccounts) {
        this.fmsBankBranchAccounts = fmsBankBranchAccounts;
    }

    public BigDecimal getDebit() {
        return debit;
    }

    public void setDebit(BigDecimal debit) {
        this.debit = debit;
    }

    public BigDecimal getCredit() {
        return credit;
    }

    public void setCredit(BigDecimal credit) {
        this.credit = credit;
    }

    public FmsCostcSystemJunction getSsCcJunction() {
        return ssCcJunction;
    }

    public void setSsCcJunction(FmsCostcSystemJunction ssCcJunction) {
        this.ssCcJunction = ssCcJunction;
    }

    @XmlTransient
    public List<FmsBudgetControl> getFmsBudgetControlList() {
        return fmsBudgetControlList;
    }

    public void setFmsBudgetControlList(List<FmsBudgetControl> fmsBudgetControlList) {
        this.fmsBudgetControlList = fmsBudgetControlList;
    }

    @XmlTransient
    public List<FinancialInstrumentRegister> getFinancialInstrumentRegisterList() {
        return financialInstrumentRegisterList;
    }

    public void setFinancialInstrumentRegisterList(List<FinancialInstrumentRegister> financialInstrumentRegisterList) {
        this.financialInstrumentRegisterList = financialInstrumentRegisterList;
    }

    @XmlTransient
    public List<FmsBeginningBalance> getFmsBeginningBalanceList() {
        return fmsBeginningBalanceList;
    }

    public void setFmsBeginningBalanceList(List<FmsBeginningBalance> fmsBeginningBalanceList) {
        this.fmsBeginningBalanceList = fmsBeginningBalanceList;
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

    @XmlTransient
    public List<FmsOperatingBudgetTasks> getFmsOperatingBudgetTasksList() {
        return fmsOperatingBudgetTasksList;
    }

    public void setFmsOperatingBudgetTasksList(List<FmsOperatingBudgetTasks> fmsOperatingBudgetTasksList) {
        this.fmsOperatingBudgetTasksList = fmsOperatingBudgetTasksList;
    }

    @XmlTransient
    public List<FmsCapitalBudgetTasks> getFmsCapitalBudgetTasksList() {
        return fmsCapitalBudgetTasksList;
    }

    public void setFmsCapitalBudgetTasksList(List<FmsCapitalBudgetTasks> fmsCapitalBudgetTasksList) {
        this.fmsCapitalBudgetTasksList = fmsCapitalBudgetTasksList;
    }

    @XmlTransient
    public List<FmsLoan> getFmsLoanList() {
        return fmsLoanList;
    }

    public void setFmsLoanList(List<FmsLoan> fmsLoanList) {
        this.fmsLoanList = fmsLoanList;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (subsidiaryId != null ? subsidiaryId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof FmsSubsidiaryLedger)) {
            return false;
        }
        FmsSubsidiaryLedger other = (FmsSubsidiaryLedger) object;
        if ((this.subsidiaryId == null && other.subsidiaryId != null) || (this.subsidiaryId != null && !this.subsidiaryId.equals(other.subsidiaryId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return subsidiaryCode;
    }

}
