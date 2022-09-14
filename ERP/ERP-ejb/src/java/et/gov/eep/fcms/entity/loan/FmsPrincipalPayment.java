/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.entity.loan;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Binyam
 */
@Entity
@Table(name = "FMS_PRINCIPAL_PAYMENT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsPrincipalPayment.findAll", query = "SELECT f FROM FmsPrincipalPayment f"),
    @NamedQuery(name = "FmsPrincipalPayment.findByLoanId", query = "SELECT f FROM FmsPrincipalPayment f WHERE f.loanId = :loanId ORDER BY f.principalPayId"),
    @NamedQuery(name = "FmsPrincipalPayment.findByPrincipalPayId", query = "SELECT f FROM FmsPrincipalPayment f WHERE f.principalPayId = :principalPayId"),
    @NamedQuery(name = "FmsPrincipalPayment.findByInstallment", query = "SELECT f FROM FmsPrincipalPayment f WHERE f.installment = :installment"),
    @NamedQuery(name = "FmsPrincipalPayment.findByRemainingBalance", query = "SELECT f FROM FmsPrincipalPayment f WHERE f.remainingBalance = :remainingBalance"),
    @NamedQuery(name = "FmsPrincipalPayment.findByPaymentDate", query = "SELECT f FROM FmsPrincipalPayment f WHERE f.paymentDate = :paymentDate"),
    @NamedQuery(name = "FmsPrincipalPayment.findByScheduleDate", query = "SELECT f FROM FmsPrincipalPayment f WHERE f.scheduleDate = :scheduleDate"),
    @NamedQuery(name = "FmsPrincipalPayment.findByStatus", query = "SELECT f FROM FmsPrincipalPayment f WHERE f.status = :status"),
    @NamedQuery(name = "FmsPrincipalPayment.findByLiquidatedDamage", query = "SELECT f FROM FmsPrincipalPayment f WHERE f.liquidatedDamage = :liquidatedDamage")})
public class FmsPrincipalPayment implements Serializable {

    @Column(name = "INSTALLMENT")
    private BigDecimal installment;
    @Column(name = "REMAINING_BALANCE")
    private BigDecimal remainingBalance;
    @Column(name = "LIQUIDATED_DAMAGE")
    private BigDecimal liquidatedDamage;
    @Column(name = "LIQUIDATION_RATE")
    private Float liquidationRate;
    @OneToMany(mappedBy = "principalId")
    private List<FmsLoanDisbursement> fmsLoanDisbursementList;
    @OneToMany(mappedBy = "principalPayIdFk")
    private List<FmsPrincipalPayAccounts> fmsPrincipalPayAccountsList;
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "PRINCIPAL_PAY_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FMS_SEQ_PRINCIPAL_PAYMENT")
    @SequenceGenerator( name = "FMS_SEQ_PRINCIPAL_PAYMENT", sequenceName = "FMS_SEQ_PRINCIPAL_PAYMENT", allocationSize = 1)
    private Integer principalPayId;
    @Column(name = "PAYMENT_DATE")
    @Temporal(TemporalType.DATE)
    private Date paymentDate;
    @Column(name = "SCHEDULE_DATE")
    @Temporal(TemporalType.DATE)
    private Date scheduleDate;
    @Column(name = "STATUS")
    private String status;
    @JoinColumn(name = "LOAN_ID", referencedColumnName = "LOAN_ID")
    @ManyToOne
    private FmsLoan loanId;

    /**
     *
     */
    public FmsPrincipalPayment() {
    }

    /**
     *
     * @param principalPayId
     */
    public FmsPrincipalPayment(Integer principalPayId) {
        this.principalPayId = principalPayId;
    }

    /**
     *
     * @return
     */
    public Integer getPrincipalPayId() {
        return principalPayId;
    }

    /**
     *
     * @param principalPayId
     */
    public void setPrincipalPayId(Integer principalPayId) {
        this.principalPayId = principalPayId;
    }

    /**
     *
     * @return
     */
    public BigDecimal getRemainingBalance() {
        return remainingBalance;
    }

    /**
     *
     * @param remainingBalance
     */
    public void setRemainingBalance(BigDecimal remainingBalance) {
        this.remainingBalance = remainingBalance;
    }

    /**
     *
     * @return
     */
    public Date getPaymentDate() {
        return paymentDate;
    }

    /**
     *
     * @param paymentDate
     */
    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    /**
     *
     * @return
     */
    public Date getScheduleDate() {
        return scheduleDate;
    }

    /**
     *
     * @param scheduleDate
     */
    public void setScheduleDate(Date scheduleDate) {
        this.scheduleDate = scheduleDate;
    }

    /**
     *
     * @return
     */
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
    public BigDecimal getLiquidatedDamage() {
        return liquidatedDamage;
    }

    /**
     *
     * @param liquidatedDamage
     */
    public void setLiquidatedDamage(BigDecimal liquidatedDamage) {
        this.liquidatedDamage = liquidatedDamage;
    }

    public Float getLiquidationRate() {
        return liquidationRate;
    }

    public void setLiquidationRate(Float liquidationRate) {
        this.liquidationRate = liquidationRate;
    }


    public FmsLoan getLoanId() {
        return loanId;
    }

    /**
     *
     * @param loanId
     */
    public void setLoanId(FmsLoan loanId) {
        this.loanId = loanId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (principalPayId != null ? principalPayId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FmsPrincipalPayment)) {
            return false;
        }
        FmsPrincipalPayment other = (FmsPrincipalPayment) object;
        if ((this.principalPayId == null && other.principalPayId != null) || (this.principalPayId != null && !this.principalPayId.equals(other.principalPayId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return loanId.toString();
    }

    @XmlTransient
    public List<FmsPrincipalPayAccounts> getFmsPrincipalPayAccountsList() {
        if (fmsPrincipalPayAccountsList == null) {
            fmsPrincipalPayAccountsList = new ArrayList<>();
        }
        return fmsPrincipalPayAccountsList;
    }

    public void setFmsPrincipalPayAccountsList(List<FmsPrincipalPayAccounts> fmsPrincipalPayAccountsList) {
        this.fmsPrincipalPayAccountsList = fmsPrincipalPayAccountsList;
    }

    public void addPrincipalPayAccountsList(FmsPrincipalPayAccounts fmsPrincipalPayAccounts) {
        if (fmsPrincipalPayAccounts.getPrincipalPayIdFk() != this) {
            this.getFmsPrincipalPayAccountsList().add(fmsPrincipalPayAccounts);
            fmsPrincipalPayAccounts.setPrincipalPayIdFk(this);
        }
    }   

    public BigDecimal getInstallment() {
        return installment;
    }

    public void setInstallment(BigDecimal installment) {
        this.installment = installment;
    }

    @XmlTransient
    public List<FmsLoanDisbursement> getFmsLoanDisbursementList() {
        return fmsLoanDisbursementList;
    }

    public void setFmsLoanDisbursementList(List<FmsLoanDisbursement> fmsLoanDisbursementList) {
        this.fmsLoanDisbursementList = fmsLoanDisbursementList;
    }
}
