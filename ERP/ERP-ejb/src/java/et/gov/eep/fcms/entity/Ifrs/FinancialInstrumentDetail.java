/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.entity.Ifrs;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author mora1
 */
@Entity
@Table(name = "FINANCIAL_INSTRUMENT_DETAIL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FinancialInstrumentDetail.findAll", query = "SELECT f FROM FinancialInstrumentDetail f"),
    @NamedQuery(name = "FinancialInstrumentDetail.findByDetailId", query = "SELECT f FROM FinancialInstrumentDetail f WHERE f.detailId = :detailId"),
    @NamedQuery(name = "FinancialInstrumentDetail.findByTransactionDate", query = "SELECT f FROM FinancialInstrumentDetail f WHERE f.transactionDate = :transactionDate"),
    @NamedQuery(name = "FinancialInstrumentDetail.findByCarryingAmountBeginning", query = "SELECT f FROM FinancialInstrumentDetail f WHERE f.carryingAmountBeginning = :carryingAmountBeginning"),
    @NamedQuery(name = "FinancialInstrumentDetail.findByInterestRate", query = "SELECT f FROM FinancialInstrumentDetail f WHERE f.interestRate = :interestRate"),
    @NamedQuery(name = "FinancialInstrumentDetail.findByCashflow", query = "SELECT f FROM FinancialInstrumentDetail f WHERE f.cashflow = :cashflow"),
    @NamedQuery(name = "FinancialInstrumentDetail.findByCarryingAmountEnding", query = "SELECT f FROM FinancialInstrumentDetail f WHERE f.carryingAmountEnding = :carryingAmountEnding"),
    @NamedQuery(name = "FinancialInstrumentDetail.findByStatus", query = "SELECT f FROM FinancialInstrumentDetail f WHERE f.status = :status"),
    @NamedQuery(name = "FinancialInstrumentDetail.findByFinancialInstrumentId", query = "SELECT f FROM FinancialInstrumentDetail f WHERE f.financialInstrumentId = :financialInstrumentId")})
public class FinancialInstrumentDetail implements Serializable {

    private static final long serialVersionUID = 1L;
    //<editor-fold defaultstate="collapsed" desc="variable declaration">
    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FINANCIAL_INSTRUMENT_DETAL_SEQ")
    @SequenceGenerator(name = "FINANCIAL_INSTRUMENT_DETAL_SEQ", sequenceName = "FINANCIAL_INSTRUMENT_DETAL_SEQ", allocationSize = 1)
    @Column(name = "DETAIL_ID", nullable = false, precision = 0, scale = -127)
    private BigDecimal detailId;
    @Column(name = "TRANSACTION_DATE")
    @Temporal(TemporalType.DATE)
    private Date transactionDate;
    @Column(name = "CARRYING_AMOUNT_BEGINNING")
    private Double carryingAmountBeginning;
    @Column(name = "INTEREST_RATE")
    private Double interestRate;
    @Column(name = "CASHFLOW")
    private Double cashflow;
    @Column(name = "CARRYING_AMOUNT_ENDING")
    private Double carryingAmountEnding;
    @Size(max = 20)
    @Column(name = "STATUS", length = 20)
    private String status;
    @Column(name = "FAIR_VALUE")
    private Double fairValue;
    @Column(name = "EFFECTIVE_INTEREST")
    private Double effectiveInterest;
    @Column(name = "TRANSACTION_COST")
    private Double transactionCost;
    @Column(name = "PREMIUM_RATE")
    private Double premiumRate;
    @Column(name = "MATURITY")
    private Integer maturity;
    @Column(name = "BOND_TYPE")
    private String BondType;
    @Column(name = "INITIAL_AMOUNT")
    private Double initialAmount;
    @Column(name = "REDEMPTION")
    private Double redemption;
    @Column(name = "FAIR_VALUE_AT_PL")
    private Double fairValueAtPL;
    @Column(name = "FAIR_VALUE_AT_OCI")
    private Double fairValueAtOCI;
    @Column(name = "INTEREST_RECEIVED")
    private Double interestReceived;
    @Column(name = "CASH_RECEIVED")
    private Double cashReceived;
    @Column(name = "INITIAL_RECOGNITION")
    private Double initialRecognition;
    @Column(name = "INTEREST_PAYMENT_MODE")
    private String interestPaymentMode;
    @Column(name = "SINGLE_COST")
    private Double singleCost;
    @Column(name = "ISSUED_DATE")
    @Temporal(TemporalType.DATE)
    private Date issuedDate;
    @JoinColumn(name = "FINANCIAL_INSTRUMENT_ID", referencedColumnName = "REGISTER_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private FinancialInstrumentRegister financialInstrumentId;
//</editor-fold>

    public FinancialInstrumentDetail() {
    }
//<editor-fold defaultstate="collapsed" desc="getter and setter">

    public FinancialInstrumentDetail(BigDecimal detailId) {
        this.detailId = detailId;
    }

    public BigDecimal getDetailId() {
        return detailId;
    }

    public void setDetailId(BigDecimal detailId) {
        this.detailId = detailId;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Double getCarryingAmountBeginning() {
        return carryingAmountBeginning;
    }

    public void setCarryingAmountBeginning(Double carryingAmountBeginning) {
        this.carryingAmountBeginning = carryingAmountBeginning;
    }

    public Double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(Double interestRate) {
        this.interestRate = interestRate;
    }

    public Double getCashflow() {
        return cashflow;
    }

    public void setCashflow(Double cashflow) {
        this.cashflow = cashflow;
    }

    public Double getCarryingAmountEnding() {
        return carryingAmountEnding;
    }

    public void setCarryingAmountEnding(Double carryingAmountEnding) {
        this.carryingAmountEnding = carryingAmountEnding;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getInitialAmount() {
        return initialAmount;
    }

    public void setInitialAmount(Double initialAmount) {
        this.initialAmount = initialAmount;
    }

    public Double getRedemption() {
        return redemption;
    }

    public void setRedemption(Double redemption) {
        this.redemption = redemption;
    }

    public Double getFairValueAtPL() {
        return fairValueAtPL;
    }

    public void setFairValueAtPL(Double fairValueAtPL) {
        this.fairValueAtPL = fairValueAtPL;
    }

    public Double getFairValueAtOCI() {
        return fairValueAtOCI;
    }

    public void setFairValueAtOCI(Double fairValueAtOCI) {
        this.fairValueAtOCI = fairValueAtOCI;
    }

    public Double getInterestReceived() {
        return interestReceived;
    }

    public void setInterestReceived(Double interestReceived) {
        this.interestReceived = interestReceived;
    }

    public Double getCashReceived() {
        return cashReceived;
    }

    public void setCashReceived(Double cashReceived) {
        this.cashReceived = cashReceived;
    }

    public Double getInitialRecognition() {
        return initialRecognition;
    }

    public void setInitialRecognition(Double initialRecognition) {
        this.initialRecognition = initialRecognition;
    }

    public Double getFairValue() {
        return fairValue;
    }

    public void setFairValue(Double fairValue) {
        this.fairValue = fairValue;
    }

    public Double getEffectiveInterest() {
        return effectiveInterest;
    }

    public void setEffectiveInterest(Double effectiveInterest) {
        this.effectiveInterest = effectiveInterest;
    }

    public Double getTransactionCost() {
        return transactionCost;
    }

    public void setTransactionCost(Double transactionCost) {
        this.transactionCost = transactionCost;
    }

    public Double getPremiumRate() {
        return premiumRate;
    }

    public void setPremiumRate(Double premiumRate) {
        this.premiumRate = premiumRate;
    }

    public Integer getMaturity() {
        return maturity;
    }

    public void setMaturity(Integer maturity) {
        this.maturity = maturity;
    }

    public String getBondType() {
        return BondType;
    }

    public void setBondType(String BondType) {
        this.BondType = BondType;
    }

    public String getInterestPaymentMode() {
        return interestPaymentMode;
    }

    public void setInterestPaymentMode(String interestPaymentMode) {
        this.interestPaymentMode = interestPaymentMode;
    }

    public Double getSingleCost() {
        return singleCost;
    }

    public void setSingleCost(Double singleCost) {
        this.singleCost = singleCost;
    }

    public Date getIssuedDate() {
        return issuedDate;
    }

    public void setIssuedDate(Date issuedDate) {
        this.issuedDate = issuedDate;
    }

//</editor-fold>
    public FinancialInstrumentRegister getFinancialInstrumentId() {
        return financialInstrumentId;
    }

    public void setFinancialInstrumentId(FinancialInstrumentRegister financialInstrumentId) {
        this.financialInstrumentId = financialInstrumentId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (detailId != null ? detailId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof FinancialInstrumentDetail)) {
            return false;
        }
        FinancialInstrumentDetail other = (FinancialInstrumentDetail) object;
        if ((this.detailId == null && other.detailId != null) || (this.detailId != null && !this.detailId.equals(other.detailId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.fcms.entity.Ifrs.FinancialInstrumentDetail[ detailId=" + detailId + " ]";
    }

}
