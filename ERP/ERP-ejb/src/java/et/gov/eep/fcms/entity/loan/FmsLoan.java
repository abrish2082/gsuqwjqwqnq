/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.entity.loan;

import et.gov.eep.fcms.entity.FmsLuCurrency;
import et.gov.eep.fcms.entity.admin.FmsSubsidiaryLedger;
import et.gov.eep.prms.entity.PrmsForeignExchange;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Binyam
 */
@Entity
@Table(name = "FMS_LOAN")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsLoan.findAll", query = "SELECT f FROM FmsLoan f"),
    @NamedQuery(name = "FmsLoan.findByLoanId", query = "SELECT f FROM FmsLoan f WHERE f.loanId = :loanId"),
    @NamedQuery(name = "FmsLoan.findByLoanGroup", query = "SELECT f FROM FmsLoan f WHERE f.loanGroup = :loanGroup"),
    @NamedQuery(name = "FmsLoan.findByLoanDate", query = "SELECT f FROM FmsLoan f WHERE f.loanDate = :loanDate"),
    @NamedQuery(name = "FmsLoan.findByPrincipalRepaymentDate", query = "SELECT f FROM FmsLoan f WHERE f.principalRepaymentDate = :principalRepaymentDate"),
    @NamedQuery(name = "FmsLoan.findByNoInstallment", query = "SELECT f FROM FmsLoan f WHERE f.noInstallment = :noInstallment"),
    @NamedQuery(name = "FmsLoan.findByGracePeriod", query = "SELECT f FROM FmsLoan f WHERE f.gracePeriodEndDate = :gracePeriodEndDate"),
    @NamedQuery(name = "FmsLoan.findByPrincipalDueDate", query = "SELECT f FROM FmsLoan f WHERE f.principalDueDate = :principalDueDate"),
    @NamedQuery(name = "FmsLoan.findByLender", query = "SELECT f FROM FmsLoan f WHERE f.lender = :lender"),
    @NamedQuery(name = "FmsLoan.findByStatus", query = "SELECT f FROM FmsLoan f WHERE f.status = :status"),
    @NamedQuery(name = "FmsLoan.findByFrequency", query = "SELECT f FROM FmsLoan f WHERE f.frequency = :frequency"),
    @NamedQuery(name = "FmsLoan.findByPayMethod", query = "SELECT f FROM FmsLoan f WHERE f.payMethod = :payMethod"),
    @NamedQuery(name = "FmsLoan.findByLoanAmount", query = "SELECT f FROM FmsLoan f WHERE f.loanAmount = :loanAmount"),
    @NamedQuery(name = "FmsLoan.findByLoanNo", query = "SELECT f FROM FmsLoan f WHERE f.loanNo = :loanNo"),
    @NamedQuery(name = "FmsLoan.findByLoanNoLIKE", query = "SELECT f FROM FmsLoan f WHERE UPPER(f.loanNo) LIKE :loanNo"),
    @NamedQuery(name = "FmsLoan.findByInterestRepaymentDate", query = "SELECT f FROM FmsLoan f WHERE f.interestRepaymentDate = :interestRepaymentDate"),
    @NamedQuery(name = "FmsLoan.findByInterestDueDate", query = "SELECT f FROM FmsLoan f WHERE f.interestDueDate = :interestDueDate")})
public class FmsLoan implements Serializable {

    @Column(name = "NO_INSTALLMENT")
    private Integer noInstallment;
    @Column(name = "LOAN_AMOUNT")
    private BigDecimal loanAmount;
    @Column(name = "LOAN_AMOUNT_IN_BIRR")
    private BigDecimal loanAmountInBirr;
    @Column(name = "EXCHANGE_RATE")
    private BigDecimal exchangeRate;
    @Column(name = "INTEREST_RULE")
    private BigDecimal interestRule;
    @OneToMany(mappedBy = "loanFk")
    private List<FmsLoanPaymentSummary> fmsLoanPaymentSummaryList;
    @Column(name = "CONTRACT_AGREEMENT_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date contractAgreementDate;
    @Size(max = 20)
    @Column(name = "LOAN_TYPE")
    private String loanType;
    @JoinColumn(name = "SUBSIDIARY_LEDGER", referencedColumnName = "SUBSIDIARY_ID")
    @ManyToOne
    private FmsSubsidiaryLedger subsidiaryLedger;
    @Column(name = "GRACE_PERIOD_END_DATE")
    @Temporal(TemporalType.DATE)
    private Date gracePeriodEndDate;
    @Column(name = "STATUS")
    private BigInteger status;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "loanIdFk")
    private List<FmsLoanDetail> fmsLoanDetailList;
    @OneToMany(mappedBy = "loanNo", fetch = FetchType.LAZY)
    private List<PrmsForeignExchange> prmsForeignExchangeList;
    @OneToMany(mappedBy = "methodOfPayment", fetch = FetchType.LAZY)
    private List<PrmsForeignExchange> prmsForeignExchangeList1;
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "LOAN_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FMS_SEQ_LOAN")
    @SequenceGenerator( name = "FMS_SEQ_LOAN", sequenceName = "FMS_SEQ_LOAN", allocationSize = 1)
    private Integer loanId;
    @Column(name = "LOAN_GROUP")
    private String loanGroup;
    @Column(name = "LOAN_DATE")
    @Temporal(TemporalType.DATE)
    private Date loanDate;
    @Column(name = "PRINCIPAL_REPAYMENT_DATE")
    @Temporal(TemporalType.DATE)
    private Date principalRepaymentDate;
    @Column(name = "PRINCIPAL_DUE_DATE")
    @Temporal(TemporalType.DATE)
    private Date principalDueDate;
    @Column(name = "LENDER")
    private String lender;
    @Column(name = "FREQUENCY")
    private String frequency;
    @Column(name = "PAY_METHOD")
    private String payMethod;
    @Column(name = "LOAN_NO")
    private String loanNo;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "INTEREST_REPAYMENT_DATE")
    @Temporal(TemporalType.DATE)
    private Date interestRepaymentDate;
    @Column(name = "INTEREST_DUE_DATE")
    @Temporal(TemporalType.DATE)
    private Date interestDueDate;
    @OneToMany(mappedBy = "loanId")
    private List<FmsLoanDisbursement> fmsLoanDisbursementList;
    @OneToMany(mappedBy = "loanId")
    private List<FmsLoanCommitment> fmsLoanCommitmentList;
    @OneToMany(mappedBy = "loanId")
    private List<FmsPrincipalPayment> fmsPrincipalPaymentList;
    @JoinColumn(name = "CURRENCY", referencedColumnName = "CURRENCY_ID")
    @ManyToOne
    private FmsLuCurrency currencyId;

    /**
     *
     */
    public FmsLoan() {
    }

    /**
     *
     * @param loanId
     */
    public FmsLoan(Integer loanId) {
        this.loanId = loanId;
    }

    /**
     *
     * @return
     */
    public Integer getLoanId() {
        return loanId;
    }

    /**
     *
     * @param loanId
     */
    public void setLoanId(Integer loanId) {
        this.loanId = loanId;
    }

    /**
     *
     * @return
     */
    public String getLoanGroup() {
        return loanGroup;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLoanGroup(String loanGroup) {
        this.loanGroup = loanGroup;
    }

    /**
     *
     * @return
     */
    public Date getLoanDate() {
        return loanDate;
    }

    /**
     *
     * @param loanDate
     */
    public void setLoanDate(Date loanDate) {
        this.loanDate = loanDate;
    }

    /**
     *
     * @return
     */
    public Date getPrincipalRepaymentDate() {
        return principalRepaymentDate;
    }

    /**
     *
     * @param principalRepaymentDate
     */
    public void setPrincipalRepaymentDate(Date principalRepaymentDate) {
        this.principalRepaymentDate = principalRepaymentDate;
    }

    /**
     *
     * @return
     */
    public Integer getNoInstallment() {
        return noInstallment;
    }

    /**
     *
     * @param noInstallment
     */
    public void setNoInstallment(Integer noInstallment) {
        this.noInstallment = noInstallment;
    }

    public Date getGracePeriodEndDate() {
        return gracePeriodEndDate;
    }

    public void setGracePeriodEndDate(Date gracePeriodEndDate) {
        this.gracePeriodEndDate = gracePeriodEndDate;
    }
    
    public Date getPrincipalDueDate() {
        return principalDueDate;
    }

    /**
     *
     * @param principalDueDate
     */
    public void setPrincipalDueDate(Date principalDueDate) {
        this.principalDueDate = principalDueDate;
    }

    /**
     *
     * @return
     */
    public String getLender() {
        return lender;
    }

    /**
     *
     * @param lender
     */
    public void setLender(String lender) {
        this.lender = lender;
    }

    /**
     *
     * @return
     */
    public String getFrequency() {
        return frequency;
    }

    /**
     *
     * @param frequency
     */
    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    /**
     *
     * @return
     */
    public String getPayMethod() {
        return payMethod;
    }

    /**
     *
     * @param payMethod
     */
    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod;
    }

    public BigDecimal getLoanAmountInBirr() {
        return loanAmountInBirr;
    }

    public void setLoanAmountInBirr(BigDecimal loanAmountInBirr) {
        this.loanAmountInBirr = loanAmountInBirr;
    }

    public BigDecimal getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(BigDecimal exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public BigDecimal getLoanAmount() {
        return loanAmount;
    }

    /**
     *
     * @param loanAmount
     */
    public void setLoanAmount(BigDecimal loanAmount) {
        this.loanAmount = loanAmount;
    }

    /**
     *
     * @return
     */
    public String getLoanNo() {
        return loanNo;
    }

    /**
     *
     * @param loanNo
     */
    public void setLoanNo(String loanNo) {
        this.loanNo = loanNo;
    }

    public BigDecimal getInterestRule() {
        return interestRule;
    }

    public void setInterestRule(BigDecimal interestRule) {
        this.interestRule = interestRule;
    }


    
    public Date getInterestRepaymentDate() {
        return interestRepaymentDate;
    }

    /**
     *
     * @param interestRepaymentDate
     */
    public void setInterestRepaymentDate(Date interestRepaymentDate) {
        this.interestRepaymentDate = interestRepaymentDate;
    }

    /**
     *
     * @return
     */
    public Date getInterestDueDate() {
        return interestDueDate;
    }

    /**
     *
     * @param interestDueDate
     */
    public void setInterestDueDate(Date interestDueDate) {
        this.interestDueDate = interestDueDate;
    }

    /**
     *
     * @return
     */
    @XmlTransient
    public List<FmsLoanDisbursement> getFmsLoanDisbursementList() {
        return fmsLoanDisbursementList;
    }

    /**
     *
     * @param fmsLoanDisbursementList
     */
    public void setFmsLoanDisbursementList(List<FmsLoanDisbursement> fmsLoanDisbursementList) {
        this.fmsLoanDisbursementList = fmsLoanDisbursementList;
    }

    /**
     *
     * @return
     */
    @XmlTransient
    public List<FmsLoanCommitment> getFmsLoanCommitmentList() {
        return fmsLoanCommitmentList;
    }

    /**
     *
     * @param fmsLoanCommitmentList
     */
    public void setFmsLoanCommitmentList(List<FmsLoanCommitment> fmsLoanCommitmentList) {
        this.fmsLoanCommitmentList = fmsLoanCommitmentList;
    }

    public FmsLuCurrency getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(FmsLuCurrency currencyId) {
        this.currencyId = currencyId;
    }

    @XmlTransient
    public List<FmsPrincipalPayment> getFmsPrincipalPaymentList() {
        return fmsPrincipalPaymentList;
    }

    public void setFmsPrincipalPaymentList(List<FmsPrincipalPayment> fmsPrincipalPaymentList) {
        this.fmsPrincipalPaymentList = fmsPrincipalPaymentList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (loanId != null ? loanId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FmsLoan)) {
            return false;
        }
        FmsLoan other = (FmsLoan) object;
        if ((this.loanId == null && other.loanId != null) || (this.loanId != null && !this.loanId.equals(other.loanId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return loanNo;
    }

    @XmlTransient
    public List<PrmsForeignExchange> getPrmsForeignExchangeList() {
        if (prmsForeignExchangeList == null) {
            prmsForeignExchangeList = new ArrayList<>();
        }
        return prmsForeignExchangeList;
    }

    public void setPrmsForeignExchangeList(List<PrmsForeignExchange> prmsForeignExchangeList) {
        this.prmsForeignExchangeList = prmsForeignExchangeList;
    }

    @XmlTransient
    public List<PrmsForeignExchange> getPrmsForeignExchangeList1() {
        if (prmsForeignExchangeList1 == null) {
            prmsForeignExchangeList1 = new ArrayList<>();
        }
        return prmsForeignExchangeList1;
    }

    public void setPrmsForeignExchangeList1(List<PrmsForeignExchange> prmsForeignExchangeList1) {
        this.prmsForeignExchangeList1 = prmsForeignExchangeList1;
    }

    public BigInteger getStatus() {
        return status;
    }

    public void setStatus(BigInteger status) {
        this.status = status;
    }

    @XmlTransient
    public List<FmsLoanDetail> getFmsLoanDetailList() {
        if (fmsLoanDetailList == null) {
            fmsLoanDetailList = new ArrayList<>();
        }
        return fmsLoanDetailList;
    }

    public void setFmsLoanDetailList(List<FmsLoanDetail> fmsLoanDetailList) {
        this.fmsLoanDetailList = fmsLoanDetailList;
    }

    public void addProjectList(FmsLoanDetail fmsLoanDetail) {
        if (fmsLoanDetail.getLoanIdFk() != this) {
            this.getFmsLoanDetailList().add(fmsLoanDetail);
            fmsLoanDetail.setLoanIdFk(this);
        }
    }

    public Date getContractAgreementDate() {
        return contractAgreementDate;
    }

    public void setContractAgreementDate(Date contractAgreementDate) {
        this.contractAgreementDate = contractAgreementDate;
    }

    public String getLoanType() {
        return loanType;
    }

    public void setLoanType(String loanType) {
        this.loanType = loanType;
    }

    public FmsSubsidiaryLedger getSubsidiaryLedger() {
        return subsidiaryLedger;
    }

    public void setSubsidiaryLedger(FmsSubsidiaryLedger subsidiaryLedger) {
        this.subsidiaryLedger = subsidiaryLedger;
    }

    @XmlTransient
    public List<FmsLoanPaymentSummary> getFmsLoanPaymentSummaryList() {
        return fmsLoanPaymentSummaryList;
    }

    public void setFmsLoanPaymentSummaryList(List<FmsLoanPaymentSummary> fmsLoanPaymentSummaryList) {
        this.fmsLoanPaymentSummaryList = fmsLoanPaymentSummaryList;
    }
}
