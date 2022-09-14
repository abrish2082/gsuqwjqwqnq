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
import javax.persistence.CascadeType;
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
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Binyam
 */
@Entity
@Table(name = "FMS_LOAN_DISBURSEMENT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsLoanDisbursement.findAll", query = "SELECT f FROM FmsLoanDisbursement f"),
    @NamedQuery(name = "FmsLoanDisbursement.findByDisbursementId", query = "SELECT f FROM FmsLoanDisbursement f WHERE f.disbursementId = :disbursementId"),
    @NamedQuery(name = "FmsLoanDisbursement.findByLoanId", query = "SELECT f FROM FmsLoanDisbursement f WHERE f.loanId = :loanId ORDER BY f.disbursementId"),
    @NamedQuery(name = "FmsLoanDisbursement.findByAmount", query = "SELECT f FROM FmsLoanDisbursement f WHERE f.amount = :amount"),
    @NamedQuery(name = "FmsLoanDisbursement.findByInterestRate", query = "SELECT f FROM FmsLoanDisbursement f WHERE f.interestRate = :interestRate"),
    @NamedQuery(name = "FmsLoanDisbursement.findByEpisode", query = "SELECT f FROM FmsLoanDisbursement f WHERE f.episode = :episode"),
    @NamedQuery(name = "FmsLoanDisbursement.findByDisbursementDate", query = "SELECT f FROM FmsLoanDisbursement f WHERE f.disbursementDate = :disbursementDate"),
    @NamedQuery(name = "FmsLoanDisbursement.findByInterestPayDate", query = "SELECT f FROM FmsLoanDisbursement f WHERE f.interestPayDate = :interestPayDate"),
    @NamedQuery(name = "FmsLoanDisbursement.findByInterestAmount", query = "SELECT f FROM FmsLoanDisbursement f WHERE f.interestAmount = :interestAmount"),
    @NamedQuery(name = "FmsLoanDisbursement.findByStatus", query = "SELECT f FROM FmsLoanDisbursement f WHERE f.status = :status")})
public class FmsLoanDisbursement implements Serializable {

    @Column(name = "AMOUNT")
    private BigDecimal amount;
    @Column(name = "INTEREST_RATE")
    private Float interestRate;
    @Column(name = "EPISODE")
    private Integer episode;
    @Column(name = "INTEREST_AMOUNT")
    private BigDecimal interestAmount;
    @JoinColumn(name = "PRINCIPAL_ID", referencedColumnName = "PRINCIPAL_PAY_ID")
    @ManyToOne
    private FmsPrincipalPayment principalId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "disbursementFk")
    private List<FmsLoanRepayment> fmsLoanRepaymentList;
    @OneToMany(mappedBy = "loanDisbFk")
    private List<FmsLoanDisbAccount> fmsLoanDisbAccountList;
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "DISBURSEMENT_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FMS_SEQ_LOAN_DISBURSEMENT")
    @SequenceGenerator(schema = "ERP", name = "FMS_SEQ_LOAN_DISBURSEMENT", sequenceName = "FMS_SEQ_LOAN_DISBURSEMENT", allocationSize = 1)
    private Integer disbursementId;
    @Column(name = "DISBURSEMENT_DATE")
    @Temporal(TemporalType.DATE)
    private Date disbursementDate;
    @Column(name = "INTEREST_PAY_DATE")
    @Temporal(TemporalType.DATE)
    private Date interestPayDate;
    @Column(name = "STATUS")
    private String status;
    @JoinColumn(name = "LOAN_ID", referencedColumnName = "LOAN_ID")
    @ManyToOne
    private FmsLoan loanId;

    /**
     *
     */
    public FmsLoanDisbursement() {
    }

    /**
     *
     * @param disbursementId
     */
    public FmsLoanDisbursement(Integer disbursementId) {
        this.disbursementId = disbursementId;
    }

    /**
     *
     * @return
     */
    public Integer getDisbursementId() {
        return disbursementId;
    }

    /**
     *
     * @param disbursementId
     */
    public void setDisbursementId(Integer disbursementId) {
        this.disbursementId = disbursementId;
    }

    /**
     *
     * @return
     */
    public Float getInterestRate() {
        return interestRate;
    }

    /**
     *
     * @param interestRate
     */
    public void setInterestRate(Float interestRate) {
        this.interestRate = interestRate;
    }

    /**
     *
     * @return
     */
    public Date getDisbursementDate() {
        return disbursementDate;
    }

    /**
     *
     * @param disbursementDate
     */
    public void setDisbursementDate(Date disbursementDate) {
        this.disbursementDate = disbursementDate;
    }

    /**
     *
     * @return
     */
    public Date getInterestPayDate() {
        return interestPayDate;
    }

    /**
     *
     * @param interestPayDate
     */
    public void setInterestPayDate(Date interestPayDate) {
        this.interestPayDate = interestPayDate;
    }

    /**
     *
     * @return
     */
    public BigDecimal getInterestAmount() {
        return interestAmount;
    }

    /**
     *
     * @param interestAmount
     */
    public void setInterestAmount(BigDecimal interestAmount) {
        this.interestAmount = interestAmount;
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

    public FmsLoan getLoanId() {
        return loanId;
    }

    public void setLoanId(FmsLoan loanId) {
        this.loanId = loanId;
    }

    public void addLoanRepaymentList(FmsLoanRepayment fmsLoanRepayment) {
        if (fmsLoanRepayment.getDisbursementFk() != this) {
            this.getFmsLoanRepaymentList().add(fmsLoanRepayment);
            fmsLoanRepayment.setDisbursementFk(this);
        }
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (disbursementId != null ? disbursementId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FmsLoanDisbursement)) {
            return false;
        }
        FmsLoanDisbursement other = (FmsLoanDisbursement) object;
        if ((this.disbursementId == null && other.disbursementId != null) || (this.disbursementId != null && !this.disbursementId.equals(other.disbursementId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.fcms.entity.FmsLoanDisbursement[ disbursementId=" + disbursementId + " ]";
    }

    @XmlTransient
    public List<FmsLoanDisbAccount> getFmsLoanDisbAccountList() {
        return fmsLoanDisbAccountList;
    }

    public void setFmsLoanDisbAccountList(List<FmsLoanDisbAccount> fmsLoanDisbAccountList) {
        this.fmsLoanDisbAccountList = fmsLoanDisbAccountList;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getEpisode() {
        return episode;
    }

    public void setEpisode(Integer episode) {
        this.episode = episode;
    }

    public FmsPrincipalPayment getPrincipalId() {
        return principalId;
    }

    public void setPrincipalId(FmsPrincipalPayment principalId) {
        this.principalId = principalId;
    }

    @XmlTransient
    public List<FmsLoanRepayment> getFmsLoanRepaymentList() {
        if (fmsLoanRepaymentList == null) {
            fmsLoanRepaymentList = new ArrayList<>();
        }
        return fmsLoanRepaymentList;
    }

    public void setFmsLoanRepaymentList(List<FmsLoanRepayment> fmsLoanRepaymentList) {
        this.fmsLoanRepaymentList = fmsLoanRepaymentList;
    }

}
