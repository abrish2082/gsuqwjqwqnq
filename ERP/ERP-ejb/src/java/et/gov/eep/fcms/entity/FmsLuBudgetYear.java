/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.entity;

import et.gov.eep.fcms.entity.admin.FmsAccountingPeriod;
import et.gov.eep.fcms.entity.budget.FmsCapitalBudget1;
import et.gov.eep.fcms.entity.budget.FmsOperatingBudget1;
import et.gov.eep.hrms.entity.leave.HrLeaveBalance;
import et.gov.eep.hrms.entity.leave.HrLeaveHolidaySetup;
import et.gov.eep.hrms.entity.leave.HrLeaveSchedule;
import et.gov.eep.hrms.entity.leave.HrLeaveTransfer;
import et.gov.eep.hrms.entity.overtime.HrOvertimeRequests;
import java.io.Serializable;
import java.math.BigInteger;
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author AB
 */
@Entity
@Table(name = "FMS_LU_BUDGET_YEAR")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsLuBudgetYear.findAll", query = "SELECT f FROM FmsLuBudgetYear f"),
    @NamedQuery(name = "FmsLuBudgetYear.findByLuBudgetYearId", query = "SELECT f FROM FmsLuBudgetYear f WHERE f.luBudgetYearId = :luBudgetYearId"),
    @NamedQuery(name = "FmsLuBudgetYear.listByBudgetYear", query = "SELECT f FROM FmsLuBudgetYear f inner join f.fmsAccountingPeriodList b where b.status = 1"),
    @NamedQuery(name = "FmsLuBudgetYear.searchByBudgetYear", query = "SELECT f FROM FmsLuBudgetYear f inner join f.fmsAccountingPeriodList b where f.budgetYear LIKE :budgetYear AND b.status = 1"),
    @NamedQuery(name = "FmsLuBudgetYear.getByBudgetYearRequest", query = "SELECT f FROM FmsLuBudgetYear f inner join f.fmsAccountingPeriodList b where f.budgetYear = :budgetYear AND b.status = 1"),
    @NamedQuery(name = "FmsLuBudgetYear.findByBudgetYearList", query = "SELECT f FROM FmsLuBudgetYear f inner join f.fmsAccountingPeriodList b where b.status = 1"),
    @NamedQuery(name = "FmsLuBudgetYear.findByBudgetYear", query = "SELECT f FROM FmsLuBudgetYear f WHERE f.budgetYear = :budgetYear")

})
public class FmsLuBudgetYear implements Serializable {
    @Column(name = "YEAR_ID")
    private BigInteger yearId;
    @OneToMany(mappedBy = "leaveYearId", fetch = FetchType.LAZY)
    private List<HrLeaveBalance> hrLeaveBalanceList;

    @OneToMany(mappedBy = "bgtYear")
    private List<HrLeaveTransfer> hrLeaveTransferList;

    @OneToMany(mappedBy = "fmsLuBudgetYear")
    private List<HrLeaveHolidaySetup> hrLeaveHolidaySetupList;
//    @OneToOne(cascade = CascadeType.ALL, mappedBy = "budgetYear")
//    private FmsOperatingBudget1 fmsOperatingBudget1;

    @OneToMany(mappedBy = "leaveYearId", cascade = CascadeType.ALL)
    private List<HrLeaveSchedule> hrLeaveScheduleList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FMS_LU_BUDGET_YEAR_LU_BUDG_SEQ")
    @SequenceGenerator(name = "FMS_LU_BUDGET_YEAR_LU_BUDG_SEQ", sequenceName = "FMS_LU_BUDGET_YEAR_LU_BUDG_SEQ", allocationSize = 1)
    @Basic(optional = false)
    @Column(name = "LU_BUDGET_YEAR_ID", nullable = false)
    private Integer luBudgetYearId;

    @Size(max = 30)
    @Column(name = "BUDGET_YEAR", length = 30)
    private String budgetYear;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "luBudgetYearId")
    private List<FmsAccountingPeriod> fmsAccountingPeriodList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "budgetYear")
    private List<FmsOperatingBudget1> fmsOperatingBudget1List;
    @OneToMany(mappedBy = "budgetYear")
    private List<FmsCapitalBudget1> fmsCapitalBudget1List;
    @OneToMany(mappedBy = "year", cascade = CascadeType.ALL)
    private List<HrOvertimeRequests> hrOvertimeRequestsCollection;
    @OneToMany(mappedBy = "yearId")
    private List<FmsTaxRegistration> fmsTaxRegistrationList;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "leaveYearId")
    private List<HrLeaveBalance> HrLeaveBalanceList;

    /**
     *
     */
    public FmsLuBudgetYear() {
    }

    /**
     *
     * @param luBudgetYearId
     */
    public FmsLuBudgetYear(Integer luBudgetYearId) {
        this.luBudgetYearId = luBudgetYearId;
    }

    /**
     *
     * @return
     */
    public Integer getLuBudgetYearId() {
        return luBudgetYearId;
    }

    /**
     *
     * @param luBudgetYearId
     */
    public void setLuBudgetYearId(Integer luBudgetYearId) {
        this.luBudgetYearId = luBudgetYearId;
    }

    /**
     *
     * @return
     */
    public String getBudgetYear() {

        return budgetYear;
    }

    /**
     *
     * @param budgetYear
     */
    public void setBudgetYear(String budgetYear) {
        this.budgetYear = budgetYear;
    }

    /**
     *
     * @return
     */
    @XmlTransient
    public List<FmsAccountingPeriod> getFmsAccountingPeriodList() {
        if (fmsAccountingPeriodList == null) {
            fmsAccountingPeriodList = new ArrayList<>();
        }
        return fmsAccountingPeriodList;
    }

    /**
     *
     * @param fmsAccountingPeriodList
     */
    public void setFmsAccountingPeriodList(List<FmsAccountingPeriod> fmsAccountingPeriodList) {
        this.fmsAccountingPeriodList = fmsAccountingPeriodList;
    }

    @XmlTransient
    public List<FmsOperatingBudget1> getFmsOperatingBudget1List() {
        return fmsOperatingBudget1List;
    }

    public void setFmsOperatingBudget1List(List<FmsOperatingBudget1> fmsOperatingBudget1List) {
        this.fmsOperatingBudget1List = fmsOperatingBudget1List;
    }

    @XmlTransient
    public List<FmsCapitalBudget1> getFmsCapitalBudget1List() {
        return fmsCapitalBudget1List;
    }

    public void setFmsCapitalBudget1List(List<FmsCapitalBudget1> fmsCapitalBudget1List) {
        this.fmsCapitalBudget1List = fmsCapitalBudget1List;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (luBudgetYearId != null ? luBudgetYearId.hashCode() : 0);
        return hash;
    }

//    public HrLeaveBalance getHrLeaveBalance() {
//        return hrLeaveBalance;
//    }
//
//    public void setHrLeaveBalance(HrLeaveBalance hrLeaveBalance) {
//        this.hrLeaveBalance = hrLeaveBalance;
//    }
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FmsLuBudgetYear)) {
            return false;
        }
        FmsLuBudgetYear other = (FmsLuBudgetYear) object;
        if ((this.luBudgetYearId == null && other.luBudgetYearId != null) || (this.luBudgetYearId != null && !this.luBudgetYearId.equals(other.luBudgetYearId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return budgetYear;
    }

    @XmlTransient
    public List<HrLeaveSchedule> getHrLeaveScheduleList() {
        return hrLeaveScheduleList;
    }

    public void setHrLeaveScheduleList(List<HrLeaveSchedule> hrLeaveScheduleList) {
        this.hrLeaveScheduleList = hrLeaveScheduleList;
    }

    @XmlTransient
    public List<HrLeaveHolidaySetup> getHrLeaveHolidaySetupList() {
        return hrLeaveHolidaySetupList;
    }

    public void setHrLeaveHolidaySetupList(List<HrLeaveHolidaySetup> hrLeaveHolidaySetupList) {
        this.hrLeaveHolidaySetupList = hrLeaveHolidaySetupList;
    }

    @XmlTransient

    public List<HrOvertimeRequests> getHrOvertimeRequestsCollection() {
        return hrOvertimeRequestsCollection;
    }

    public void setHrOvertimeRequestsCollection(List<HrOvertimeRequests> hrOvertimeRequestsCollection) {
        this.hrOvertimeRequestsCollection = hrOvertimeRequestsCollection;
    }

    @XmlTransient
    public List<HrLeaveTransfer> getHrLeaveTransferList() {
        return hrLeaveTransferList;
    }

    public void setHrLeaveTransferList(List<HrLeaveTransfer> hrLeaveTransferList) {
        this.hrLeaveTransferList = hrLeaveTransferList;
    }

    @XmlTransient

    public List<FmsTaxRegistration> getFmsTaxRegistrationList() {
        if (fmsTaxRegistrationList == null) {
            fmsTaxRegistrationList = new ArrayList<>();
        }
        return fmsTaxRegistrationList;
    }

    public void setFmsTaxRegistrationList(List<FmsTaxRegistration> fmsTaxRegistrationList) {
        this.fmsTaxRegistrationList = fmsTaxRegistrationList;
    }

    @XmlTransient
    public List<HrLeaveBalance> getHrLeaveBalanceList() {
        return HrLeaveBalanceList;
    }

    public void setHrLeaveBalanceList(List<HrLeaveBalance> HrLeaveBalanceList) {
        this.HrLeaveBalanceList = HrLeaveBalanceList;
    }

}
