/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.payroll;

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
 * @author user
 */
@Entity
@Table(name = "HR_PAYROLL_PERIODS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrPayrollPeriods.loadPayrollDates", query = "SELECT h FROM HrPayrollPeriods h ORDER BY  SUBSTRING(h.paymentMadeForTheMonthOf,7,4) , SUBSTRING(h.paymentMadeForTheMonthOf,4,2) ASC"),
    @NamedQuery(name = "HrPayrollPeriods.activeOnlyOneMonth", query = "UPDATE HrPayrollPeriods h SET H.status='0' WHERE H.status='1'"),
    @NamedQuery(name = "HrPayrollPeriods.loadInactiveDates", query = "SELECT h FROM HrPayrollPeriods h where H.status='0'"),
    @NamedQuery(name = "HrPayrollPeriods.lastFinalPayrll", query = "SELECT h FROM HrPayrollPeriods h where H.status='2' AND  CAST(SUBSTRING( H.paymentMadeForTheMonthOf,7,4) AS NUMERIC(10,2))*360=(SELECT MAX(CAST(SUBSTRING( M.paymentMadeForTheMonthOf,7,4) AS NUMERIC(10,2)))*360 FROM HrPayrollPeriods M WHERE M.status='2')"),
    @NamedQuery(name = "HrPayrollPeriods.lastPayrollDate", query = "SELECT h FROM HrPayrollPeriods h where  CAST(SUBSTRING( H.paymentMadeForTheMonthOf,7,4) AS NUMERIC(10,2))=(SELECT MAX(CAST(SUBSTRING( H.paymentMadeForTheMonthOf,7,4) AS NUMERIC(10,2))) FROM HrPayrollPeriods S WHERE H.status='2' ) AND H.status='2'"),
    @NamedQuery(name = "HrPayrollPeriods.payrollFrom", query = "SELECT h FROM HrPayrollPeriods h where (SUBSTRING(h.paymentMadeForTheMonthOf,7,4)>='2008' AND SUBSTRING(h.paymentMadeForTheMonthOf,4,2)>='12') OR (SUBSTRING(h.paymentMadeForTheMonthOf,7,4)>='2008' AND SUBSTRING(h.paymentMadeForTheMonthOf,4,2)>='12')"),
    @NamedQuery(name = "HrPayrollPeriods.checkRepeatedDate", query = "SELECT h FROM HrPayrollPeriods h where SUBSTRING(h.paymentMadeForTheMonthOf,4,8)=:paymentMadeForTheMonthOf"),
    @NamedQuery(name = "HrPayrollPeriods.finalizedMaxPayrollDate", query = "SELECT MAX(H.paymentMadeForTheMonthOf) FROM HrPayrollPeriods h where h.finalStatus='1'"),
    @NamedQuery(name = "HrPayrollPeriods.finalizePayrollPeriod", query = "UPDATE HrPayrollPeriods h SET H.finalStatus='1',h.status='2' where h.id=:id"),
    @NamedQuery(name = "HrPayrollPeriods.update", query = "UPDATE HrPayrollPeriods h SET H.status='0'"),
    @NamedQuery(name = "HrPayrollPeriods.findActivePayrollDate", query = "SELECT h FROM HrPayrollPeriods h where h.status='1'"),//CORRECT IT
    @NamedQuery(name = "HrPayrollPeriods.findByMonth", query = "SELECT h FROM HrPayrollPeriods h"),//CORRECT IT
    @NamedQuery(name = "HrPayrollPeriods.findAll", query = "SELECT h FROM HrPayrollPeriods h"),
    @NamedQuery(name = "HrPayrollPeriods.findById", query = "SELECT h FROM HrPayrollPeriods h WHERE h.id = :id"),
    @NamedQuery(name = "HrPayrollPeriods.findByPaymentMadeForTheMonthOf", query = "SELECT h FROM HrPayrollPeriods h WHERE h.paymentMadeForTheMonthOf = :paymentMadeForTheMonthOf"),
    @NamedQuery(name = "HrPayrollPeriods.findByStatus", query = "SELECT h FROM HrPayrollPeriods h WHERE h.status = :status"),
    @NamedQuery(name = "HrPayrollPeriods.findByPreparedFor", query = "SELECT h FROM HrPayrollPeriods h WHERE h.preparedFor = :preparedFor"),
    @NamedQuery(name = "HrPayrollPeriods.findByFinalStatus", query = "SELECT h FROM HrPayrollPeriods h WHERE h.finalStatus = :finalStatus"),
    @NamedQuery(name = "HrPayrollPeriods.findByCurentPayrollCode", query = "SELECT h FROM HrPayrollPeriods h WHERE h.curentPayrollCode = :curentPayrollCode"),
    @NamedQuery(name = "HrPayrollPeriods.findByRemark", query = "SELECT h FROM HrPayrollPeriods h WHERE h.remark = :remark")})

public class HrPayrollPeriods implements Serializable {

    @OneToMany(mappedBy = "payrolFrom")
    private List<HrPayrollBackPaymentGroups> hrPayrollBackPaymentGroupsList;
    @OneToMany(mappedBy = "payrollTo")
    private List<HrPayrollBackPaymentGroups> hrPayrollBackPaymentGroupsList1;
    @OneToMany(mappedBy = "startOfCarryForward")
    private List<HrPayrollMaintainEds> hrPayrollMaintainEdsList;
    @OneToMany(mappedBy = "transactionBeginsOn")
    private List<HrPayrollMaintainEds> hrPayrollMaintainEdsList1;
    @OneToMany(mappedBy = "payrollPeriodId")
    private List<HrPayrollMonTransactions> hrPayrollMonTransactionsList;
    @OneToMany(mappedBy = "prevPayrllId")
    private List<HrPayrollMaintainBackPay> hrPayrollMaintainBackPayList;
    @OneToMany(mappedBy = "payrollFrom")
    private List<HrPayrollMaintanBackPymt> hrPayrollMaintanBackPymtList;
    @OneToMany(mappedBy = "backPayrollId")
    private List<HrPayrollMaintanBackPymt> hrPayrollMaintanBackPymtList1;
    @OneToMany(mappedBy = "payrollTo")
    private List<HrPayrollMaintanBackPymt> hrPayrollMaintanBackPymtList2;

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_PAYROLL_PERIODS_SEQ") //
    @SequenceGenerator(name = "HR_PAYROLL_PERIODS_SEQ", sequenceName = "HR_PAYROLL_PERIODS_SEQ", allocationSize = 1)
    private BigDecimal id;
    @NotNull
    @Size(max = 20)
    @Column(name = "PAYMENT_MADE_FOR_THE_MONTH_OF")
    private String paymentMadeForTheMonthOf;
    @Size(max = 20)
    @Column(name = "STATUS")
    private String status;
    @Size(max = 20)
    @Column(name = "PREPARED_FOR")
    private String preparedFor;
    @Size(max = 20)
    @Column(name = "FINAL_STATUS")
    private String finalStatus;
    @Size(max = 20)
    @Column(name = "CURENT_PAYROLL_CODE")
    private String curentPayrollCode;
    @Size(max = 20)
    @Column(name = "REMARK")
    private String remark;
    @Size(max = 20)
    @Column(name = "IS_ORDERED")
    private String isOrdered;

    /**
     *
     */
    public HrPayrollPeriods() {
    }

    /**
     *
     * @param id
     */
    public HrPayrollPeriods(BigDecimal id) {
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
    public String getPaymentMadeForTheMonthOf() {

        return paymentMadeForTheMonthOf;
    }

    /**
     *
     * @param paymentMadeForTheMonthOf
     */
    public void setPaymentMadeForTheMonthOf(String paymentMadeForTheMonthOf) {
        this.paymentMadeForTheMonthOf = paymentMadeForTheMonthOf;
    }

    /**
     *
     * @return
     */
    public String getStatus() {

        if (status != null) {
            if (status.equalsIgnoreCase("0")) {
                status = "[Not Active]";
            } else if (status.equalsIgnoreCase("1")) {
                status = "[Active]";
            } else if (status.equalsIgnoreCase("2")) {
                status = "[Closed]";
            }
        }
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
    public String getPreparedFor() {
        return preparedFor;
    }

    /**
     *
     * @param preparedFor
     */
    public void setPreparedFor(String preparedFor) {
        this.preparedFor = preparedFor;
    }

    /**
     *
     * @return
     */
    public String getFinalStatus() {
        return finalStatus;
    }

    /**
     *
     * @param finalStatus
     */
    public void setFinalStatus(String finalStatus) {
        this.finalStatus = finalStatus;
    }

    /**
     *
     * @return
     */
    public String getCurentPayrollCode() {
        return curentPayrollCode;
    }

    /**
     *
     * @param curentPayrollCode
     */
    public void setCurentPayrollCode(String curentPayrollCode) {
        this.curentPayrollCode = curentPayrollCode;
    }

    /**
     *
     * @return
     */
    public String getRemark() {
        return remark;
    }

    /**
     *
     * @param remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     *
     * @return
     */
    public String getIsOrdered() {
        return isOrdered;
    }

    /**
     *
     * @param isOrdered
     */
    public void setIsOrdered(String isOrdered) {
        this.isOrdered = isOrdered;
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
        if (!(object instanceof HrPayrollPeriods)) {
            return false;
        }
        HrPayrollPeriods other = (HrPayrollPeriods) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.hrms.entity.payroll.HrPayrollPeriods[ id=" + id + " ]";
    }

    /**
     *
     * @return
     */
    @XmlTransient
    public List<HrPayrollMaintanBackPymt> getHrPayrollMaintanBackPymtList() {
        return hrPayrollMaintanBackPymtList;
    }

    /**
     *
     * @param hrPayrollMaintanBackPymtList
     */
    public void setHrPayrollMaintanBackPymtList(List<HrPayrollMaintanBackPymt> hrPayrollMaintanBackPymtList) {
        this.hrPayrollMaintanBackPymtList = hrPayrollMaintanBackPymtList;
    }

    /**
     *
     * @return
     */
    @XmlTransient
    public List<HrPayrollMaintanBackPymt> getHrPayrollMaintanBackPymtList1() {
        return hrPayrollMaintanBackPymtList1;
    }

    /**
     *
     * @param hrPayrollMaintanBackPymtList1
     */
    public void setHrPayrollMaintanBackPymtList1(List<HrPayrollMaintanBackPymt> hrPayrollMaintanBackPymtList1) {
        this.hrPayrollMaintanBackPymtList1 = hrPayrollMaintanBackPymtList1;
    }

    /**
     *
     * @return
     */
    @XmlTransient
    public List<HrPayrollMaintanBackPymt> getHrPayrollMaintanBackPymtList2() {
        return hrPayrollMaintanBackPymtList2;
    }

    /**
     *
     * @param hrPayrollMaintanBackPymtList2
     */
    public void setHrPayrollMaintanBackPymtList2(List<HrPayrollMaintanBackPymt> hrPayrollMaintanBackPymtList2) {
        this.hrPayrollMaintanBackPymtList2 = hrPayrollMaintanBackPymtList2;
    }

    /**
     *
     * @return
     */
    @XmlTransient
    public List<HrPayrollMaintainBackPay> getHrPayrollMaintainBackPayList() {
        return hrPayrollMaintainBackPayList;
    }

    /**
     *
     * @param hrPayrollMaintainBackPayList
     */
    public void setHrPayrollMaintainBackPayList(List<HrPayrollMaintainBackPay> hrPayrollMaintainBackPayList) {
        this.hrPayrollMaintainBackPayList = hrPayrollMaintainBackPayList;
    }

    @XmlTransient
    public List<HrPayrollMonTransactions> getHrPayrollMonTransactionsList() {
        return hrPayrollMonTransactionsList;
    }

    public void setHrPayrollMonTransactionsList(List<HrPayrollMonTransactions> hrPayrollMonTransactionsList) {
        this.hrPayrollMonTransactionsList = hrPayrollMonTransactionsList;
    }

    @XmlTransient
    public List<HrPayrollMaintainEds> getHrPayrollMaintainEdsList() {
        return hrPayrollMaintainEdsList;
    }

    public void setHrPayrollMaintainEdsList(List<HrPayrollMaintainEds> hrPayrollMaintainEdsList) {
        this.hrPayrollMaintainEdsList = hrPayrollMaintainEdsList;
    }

    @XmlTransient
    public List<HrPayrollMaintainEds> getHrPayrollMaintainEdsList1() {
        return hrPayrollMaintainEdsList1;
    }

    public void setHrPayrollMaintainEdsList1(List<HrPayrollMaintainEds> hrPayrollMaintainEdsList1) {
        this.hrPayrollMaintainEdsList1 = hrPayrollMaintainEdsList1;
    }

    @XmlTransient
    public List<HrPayrollBackPaymentGroups> getHrPayrollBackPaymentGroupsList() {
        return hrPayrollBackPaymentGroupsList;
    }

    public void setHrPayrollBackPaymentGroupsList(List<HrPayrollBackPaymentGroups> hrPayrollBackPaymentGroupsList) {
        this.hrPayrollBackPaymentGroupsList = hrPayrollBackPaymentGroupsList;
    }

    @XmlTransient
    public List<HrPayrollBackPaymentGroups> getHrPayrollBackPaymentGroupsList1() {
        return hrPayrollBackPaymentGroupsList1;
    }

    public void setHrPayrollBackPaymentGroupsList1(List<HrPayrollBackPaymentGroups> hrPayrollBackPaymentGroupsList1) {
        this.hrPayrollBackPaymentGroupsList1 = hrPayrollBackPaymentGroupsList1;
    }

}
