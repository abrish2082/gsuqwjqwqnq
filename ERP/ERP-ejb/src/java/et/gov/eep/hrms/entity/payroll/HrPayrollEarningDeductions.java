/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.payroll;

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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import et.gov.eep.fcms.entity.admin.FmsGeneralLedger;
import et.gov.eep.hrms.entity.allowance.HrAllowanceInLevels;
import et.gov.eep.hrms.entity.allowance.HrPayrollFiltEmForAll;

/**
 *
 * @author user
 */
@Entity
@Table(name = "HR_PAYROLL_EARNING_DEDUCTIONS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrPayrollEarningDeductions.findAll", query = "SELECT h FROM HrPayrollEarningDeductions h"),
    @NamedQuery(name = "HrPayrollEarningDeductions.findByCode", query = "SELECT h FROM HrPayrollEarningDeductions h WHERE h.code = :code"),
    @NamedQuery(name = "HrPayrollEarningDeductions.findByDescription", query = "SELECT h FROM HrPayrollEarningDeductions h WHERE h.description = :description"),
    @NamedQuery(name = "HrPayrollEarningDeductions.findByTaxable", query = "SELECT h FROM HrPayrollEarningDeductions h WHERE h.taxable = :taxable"),
    @NamedQuery(name = "HrPayrollEarningDeductions.findByPercentage", query = "SELECT h FROM HrPayrollEarningDeductions h WHERE h.percentage = :percentage"),
    @NamedQuery(name = "HrPayrollEarningDeductions.findByCriterias", query = "SELECT h FROM HrPayrollEarningDeductions h WHERE h.criterias = :criterias"),
    @NamedQuery(name = "HrPayrollEarningDeductions.findByForcedDeduction", query = "SELECT h FROM HrPayrollEarningDeductions h WHERE h.forcedDeduction = :forcedDeduction"),
    @NamedQuery(name = "HrPayrollEarningDeductions.findBySubsidaryId", query = "SELECT h FROM HrPayrollEarningDeductions h WHERE h.subsidaryId = :subsidaryId"),
    @NamedQuery(name = "HrPayrollEarningDeductions.findBySalaryBased", query = "SELECT h FROM HrPayrollEarningDeductions h WHERE h.salaryBased = :salaryBased"),
    @NamedQuery(name = "HrPayrollEarningDeductions.findByUsedForLeaveAdvPymt", query = "SELECT h FROM HrPayrollEarningDeductions h WHERE h.usedForLeaveAdvPymt = :usedForLeaveAdvPymt"),
    @NamedQuery(name = "HrPayrollEarningDeductions.findByPercentileEDedUsingSal", query = "SELECT h FROM HrPayrollEarningDeductions h WHERE h.percentileEDedUsingSal = :percentileEDedUsingSal"),
    @NamedQuery(name = "HrPayrollEarningDeductions.findByItemCode", query = "SELECT h FROM HrPayrollEarningDeductions h WHERE h.itemCode = :itemCode"),
    @NamedQuery(name = "HrPayrollEarningDeductions.searchByItemCode", query = "SELECT h FROM HrPayrollEarningDeductions h WHERE h.itemCode = 'hhhhhh'"),
    @NamedQuery(name = "HrPayrollEarningDeductions.searchByItemCode11", query = "SELECT h FROM HrPayrollEarningDeductions h where h.itemCode = :itemCode"),
    @NamedQuery(name = "HrPayrollEarningDeductions.searchByItemCodeForUpdate", query = "SELECT h FROM HrPayrollEarningDeductions h where h.itemCode = :itemCode and h.code <> :code"),
    @NamedQuery(name = "HrPayrollEarningDeductions.findByFirstDeductFromSal", query = "SELECT h FROM HrPayrollEarningDeductions h WHERE h.firstDeductFromSal = :firstDeductFromSal"),
    @NamedQuery(name = "HrPayrollEarningDeductions.findByDedOrder", query = "SELECT h FROM HrPayrollEarningDeductions h WHERE h.dedOrder = :dedOrder"),
    @NamedQuery(name = "HrPayrollEarningDeductions.findByDrcr", query = "SELECT h FROM HrPayrollEarningDeductions h WHERE h.drcr = :drcr"),
    @NamedQuery(name = "HrPayrollEarningDeductions.findBySysCs", query = "SELECT h FROM HrPayrollEarningDeductions h WHERE h.sysCs = :sysCs"),
    @NamedQuery(name = "HrPayrollEarningDeductions.findByCounterLedDesc", query = "SELECT h FROM HrPayrollEarningDeductions h WHERE h.counterLedDesc = :counterLedDesc"),
    @NamedQuery(name = "HrPayrollEarningDeductions.findByCounterDr", query = "SELECT h FROM HrPayrollEarningDeductions h WHERE h.counterDr = :counterDr"),
    //user defined
    @NamedQuery(name = "HrPayrollEarningDeductions.loadOnlyAllowanceEar", query = "SELECT h FROM HrPayrollEarningDeductions h WHERE h.type = :type and (h.criterias ='Allowance in JL') ORDER BY H.description ASC"),
    @NamedQuery(name = "HrPayrollEarningDeductions.loadAllowacesLocation", query = "SELECT h FROM HrPayrollEarningDeductions h WHERE h.type = :type and (h.criterias ='Allowance in JL' or h.criterias ='Allowance in JT' or h.criterias ='Allowance in Loc' ) ORDER BY H.description ASC"),
    @NamedQuery(name = "HrPayrollEarningDeductions.loadEDForAllowanceLocation", query = "SELECT h FROM HrPayrollEarningDeductions h WHERE h.type = :type and (h.criterias ='Allowance in Loc') ORDER BY H.description ASC"),
    @NamedQuery(name = "HrPayrollEarningDeductions.loadEDForAllowanceJobTitle", query = "SELECT h FROM HrPayrollEarningDeductions h WHERE h.type = :type and ( h.criterias ='Allowance in JT' ) ORDER BY H.description ASC"),
    //i have changed to disply for report on Monthly Earning/Deduction Report on the interface
    @NamedQuery(name = "HrPayrollEarningDeductions.loadOnlyEarnings", query = "SELECT h FROM HrPayrollEarningDeductions h WHERE h.type = :type and h.criterias <>'All Employees Earning Deduction'  and h.criterias <>'Total Earnings' and h.criterias <>'Total Deduction' and h.criterias <>'Total Taxable Earnings' ORDER BY H.description ASC"),
//    @NamedQuery(name = "HrPayrollEarningDeductions.loadOnlyEarnings", query = "SELECT h FROM HrPayrollEarningDeductions h WHERE h.type = :type and h.criterias <>'All Employees Earning Deduction'  and h.criterias <>'Total Earnings' and h.criterias <>'Total Deduction' and h.criterias <>'Total Taxable Earnings' and h.criterias <>'Allowance in Loc' and h.criterias <>'Allowance in JT' and h.criterias <>'Allowance in JL' ORDER BY H.description ASC"),
//    @NamedQuery(name = "HrPayrollEarningDeductions.listOfEarningForAllEmp", query = "SELECT h FROM HrPayrollEarningDeductions h WHERE h.type = :type and h.criterias ='All Employees Earning Deduction' ORDER BY H.description ASC"),
    @NamedQuery(name = "HrPayrollEarningDeductions.listOfEarningForAllEmp", query = "SELECT h FROM HrPayrollEarningDeductions h WHERE h.type = :type and h.criterias ='Allowance in JT' or h.criterias ='Allowance in JL' or h.criterias ='Allowance in Loc'  ORDER BY H.description ASC"),
    @NamedQuery(name = "HrPayrollEarningDeductions.listOtTypes", query = "SELECT h FROM HrPayrollEarningDeductions h WHERE h.type = :type and (h.criterias ='A type overtime' OR h.criterias ='B type overtime'  OR h.criterias ='C type overtime' OR h.criterias ='D type overtime' ) ORDER BY H.description ASC"),
    @NamedQuery(name = "HrPayrollEarningDeductions.taxReportCriterias", query = "SELECT h.criterias FROM HrPayrollEarningDeductions h WHERE h.criterias = 'Tax' or "
            + "h.criterias='Pension Addition'  or h.criterias='cost Sharing'"),
    @NamedQuery(name = "HrPayrollEarningDeductions.findByType", query = "SELECT h FROM HrPayrollEarningDeductions h WHERE h.type = :type ORDER BY H.description ASC"),
    @NamedQuery(name = "HrPayrollEarningDeductions.loadEarningAndDeductions", query = "SELECT h FROM HrPayrollEarningDeductions h WHERE (h.type = 'Earning' or h.type = 'Deduction') and h.criterias <>'All Employees Earning Deduction' and h.criterias <>'Total Earnings' and h.criterias <>'Total Deduction' and h.criterias <>'Total Taxable Earnings' ORDER BY H.description ASC"),
//    @NamedQuery(name = "HrPayrollEarningDeductions.loadOnlyDeductions", query = "SELECT h FROM HrPayrollEarningDeductions h WHERE h.type = :type and h.criterias <>'All Employees Earning Deduction' and h.criterias <>'Total Earnings' and h.criterias <>'Total Deduction' and h.criterias <>'Total Taxable Earnings' and h.criterias <>'Allowance in Loc' and h.criterias <>'Allowance in JT' and h.criterias <>'Allowance in JL' ORDER BY H.description ASC"),
    @NamedQuery(name = "HrPayrollEarningDeductions.loadOnlyDeductions", query = "SELECT h FROM HrPayrollEarningDeductions h WHERE h.type = :type and h.criterias <>'All Employees Earning Deduction' and h.criterias <>'Total Earnings' and h.criterias ='Third Parties' ORDER BY H.description ASC"),
    @NamedQuery(name = "HrPayrollEarningDeductions.loadAllEmpDeductions", query = "SELECT h FROM HrPayrollEarningDeductions h WHERE h.type = :type and h.criterias = 'All Employees Earning Deduction' ORDER BY H.description ASC"),
    @NamedQuery(name = "HrPayrollEarningDeductions.loadAllEmpEarnings", query = "SELECT h FROM HrPayrollEarningDeductions h WHERE h.type = :type and h.criterias = 'All Employees Earning Deduction' ORDER BY H.description ASC"),
    @NamedQuery(name = "HrPayrollEarningDeductions.checkRepationCriterias", query = "SELECT h FROM HrPayrollEarningDeductions h WHERE h.criterias = :criterias"),
    @NamedQuery(name = "HrPayrollEarningDeductions.findByForcedDeduction", query = "SELECT h FROM HrPayrollEarningDeductions h WHERE h.forcedDeduction = :forcedDeduction"),})
public class HrPayrollEarningDeductions implements Serializable {

    @OneToMany(mappedBy = "earningDedCode")
    private List<HrPayrollFiltEmForAll> hrPayrollFiltEmForAllList;
    @OneToMany(mappedBy = "earningDeductionId")
    private List<HrAllowanceInLevels> hrAllowanceInLevelsList;

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "CODE")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_PAYROLL_ED_SEQ")
    @SequenceGenerator(name = "HR_PAYROLL_ED_SEQ", sequenceName = "HR_PAYROLL_ED_SEQ", allocationSize = 1)
    private BigDecimal code;
    @Size(max = 50)
    @Column(name = "DESCRIPTION")
    private String description;
    @Size(max = 20)
    @Column(name = "TYPE")
    private String type;
    @Size(max = 20)
    @Column(name = "TAXABLE")
    private String taxable;
    @Size(max = 20)
    @Column(name = "PERCENTAGE")
    private String percentage;
    @Size(max = 60)
    @Column(name = "CRITERIAS")
    private String criterias;
    @Size(max = 4000)
    @Column(name = "FORCED_DEDUCTION")
    private String forcedDeduction;
    @Column(name = "SUBSIDARY_ID")
    private BigInteger subsidaryId;
    @Size(max = 60)
    @Column(name = "SALARY_BASED")
    private String salaryBased;
    @Size(max = 60)
    @Column(name = "USED_FOR_LEAVE_ADV_PYMT")
    private String usedForLeaveAdvPymt;
    @Size(max = 60)
    @Column(name = "PERCENTILE_E_DED_USING_SAL")
    private String percentileEDedUsingSal;
    @Size(max = 60)
    @Column(name = "ITEM_CODE")
    private String itemCode;
    @Size(max = 20)
    @Column(name = "FIRST_DEDUCT_FROM_SAL")
    private String firstDeductFromSal;
    @Size(max = 20)
    @Column(name = "DED_ORDER")
    private String dedOrder;
    @Size(max = 20)
    @Column(name = "DRCR")
    private String drcr;
    @Size(max = 20)
    @Column(name = "SYS_CS")
    private String sysCs;
    @Size(max = 20)
    @Column(name = "COUNTER_LED_DESC")
    private String counterLedDesc;
    @Size(max = 20)
    @Column(name = "COUNTER_DR")
    private String counterDr;
    @JoinColumn(name = "GENERAL_LEDGER_ID", referencedColumnName = "GENERAL_LEDGER_ID")
    @ManyToOne
    private FmsGeneralLedger generalLedgerId;
    @JoinColumn(name = "COUNTER_LEDER_ID", referencedColumnName = "GENERAL_LEDGER_ID")
    @ManyToOne
    private FmsGeneralLedger counterLederId;

    public HrPayrollEarningDeductions() {
    }

    public HrPayrollEarningDeductions(BigDecimal code) {
        this.code = code;
    }

    public BigDecimal getCode() {
        return code;
    }

    public void setCode(BigDecimal code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTaxable() {
        return taxable;
    }

    public void setTaxable(String taxable) {
        this.taxable = taxable;
    }

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }

    public String getCriterias() {
        return criterias;
    }

    public void setCriterias(String criterias) {
        this.criterias = criterias;
    }

    public String getForcedDeduction() {
        return forcedDeduction;
    }

    public void setForcedDeduction(String forcedDeduction) {
        this.forcedDeduction = forcedDeduction;
    }

    public BigInteger getSubsidaryId() {
        return subsidaryId;
    }

    public void setSubsidaryId(BigInteger subsidaryId) {
        this.subsidaryId = subsidaryId;
    }

    public String getSalaryBased() {
        return salaryBased;
    }

    public void setSalaryBased(String salaryBased) {
        this.salaryBased = salaryBased;
    }

    public String getUsedForLeaveAdvPymt() {
        return usedForLeaveAdvPymt;
    }

    public void setUsedForLeaveAdvPymt(String usedForLeaveAdvPymt) {
        this.usedForLeaveAdvPymt = usedForLeaveAdvPymt;
    }

    public String getPercentileEDedUsingSal() {
        return percentileEDedUsingSal;
    }

    public void setPercentileEDedUsingSal(String percentileEDedUsingSal) {
        this.percentileEDedUsingSal = percentileEDedUsingSal;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getFirstDeductFromSal() {
        return firstDeductFromSal;
    }

    public void setFirstDeductFromSal(String firstDeductFromSal) {
        this.firstDeductFromSal = firstDeductFromSal;
    }

    public String getDedOrder() {
        return dedOrder;
    }

    public void setDedOrder(String dedOrder) {
        this.dedOrder = dedOrder;
    }

    public String getDrcr() {
        return drcr;
    }

    public void setDrcr(String drcr) {
        this.drcr = drcr;
    }

    public String getSysCs() {
        return sysCs;
    }

    public void setSysCs(String sysCs) {
        this.sysCs = sysCs;
    }

    public String getCounterLedDesc() {
        return counterLedDesc;
    }

    public void setCounterLedDesc(String counterLedDesc) {
        this.counterLedDesc = counterLedDesc;
    }

    public String getCounterDr() {
        return counterDr;
    }

    public void setCounterDr(String counterDr) {
        this.counterDr = counterDr;
    }

    public FmsGeneralLedger getGeneralLedgerId() {
        return generalLedgerId;
    }

    public void setGeneralLedgerId(FmsGeneralLedger generalLedgerId) {
        this.generalLedgerId = generalLedgerId;
    }

    public FmsGeneralLedger getCounterLederId() {
        return counterLederId;
    }

    public void setCounterLederId(FmsGeneralLedger counterLederId) {
        this.counterLederId = counterLederId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (code != null ? code.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrPayrollEarningDeductions)) {
            return false;
        }
        HrPayrollEarningDeductions other = (HrPayrollEarningDeductions) object;
        if ((this.code == null && other.code != null) || (this.code != null && !this.code.equals(other.code))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.hrms.entity.payroll.HrPayrollEarningDeductions[ code=" + code + " ]";
    }

    @XmlTransient
    public List<HrAllowanceInLevels> getHrAllowanceInLevelsList() {
        return hrAllowanceInLevelsList;
    }

    public void setHrAllowanceInLevelsList(List<HrAllowanceInLevels> hrAllowanceInLevelsList) {
        this.hrAllowanceInLevelsList = hrAllowanceInLevelsList;
    }

    @XmlTransient
    public List<HrPayrollFiltEmForAll> getHrPayrollFiltEmForAllList() {
        return hrPayrollFiltEmForAllList;
    }

    public void setHrPayrollFiltEmForAllList(List<HrPayrollFiltEmForAll> hrPayrollFiltEmForAllList) {
        this.hrPayrollFiltEmForAllList = hrPayrollFiltEmForAllList;
    }

}
