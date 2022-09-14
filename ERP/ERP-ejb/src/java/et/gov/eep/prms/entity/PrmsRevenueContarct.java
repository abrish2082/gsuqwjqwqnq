/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.entity;

import et.gov.eep.fcms.entity.FmsLuCurrency;
import et.gov.eep.fcms.entity.admin.FmsGeneralLedger;
import et.gov.eep.fcms.entity.admin.FmsSubsidiaryLedger;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author mora1
 */
@Entity
@Table(name = "PRMS_REVENUE_CONTARCT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrmsRevenueContarct.findAll", query = "SELECT p FROM PrmsRevenueContarct p"),
    @NamedQuery(name = "PrmsRevenueContarct.findById", query = "SELECT p FROM PrmsRevenueContarct p WHERE p.id = :id"),
    @NamedQuery(name = "PrmsRevenueContarct.findByContractName", query = "SELECT p FROM PrmsRevenueContarct p WHERE p.contractName = :contractName"),
    @NamedQuery(name = "PrmsRevenueContarct.findByPerformanceObligationType", query = "SELECT p FROM PrmsRevenueContarct p WHERE p.performanceObligationType = :performanceObligationType"),
    @NamedQuery(name = "PrmsRevenueContarct.findByPerformanceObligationSatis", query = "SELECT p FROM PrmsRevenueContarct p WHERE p.performanceObligationSatis = :performanceObligationSatis"),
    @NamedQuery(name = "PrmsRevenueContarct.findByContractedWith", query = "SELECT p FROM PrmsRevenueContarct p WHERE p.contractedWith = :contractedWith"),
    @NamedQuery(name = "PrmsRevenueContarct.findByContractedDate", query = "SELECT p FROM PrmsRevenueContarct p WHERE p.contractedDate = :contractedDate"),
    @NamedQuery(name = "PrmsRevenueContarct.findByEstimatedTotalCost", query = "SELECT p FROM PrmsRevenueContarct p WHERE p.estimatedTotalCost = :estimatedTotalCost"),
    @NamedQuery(name = "PrmsRevenueContarct.findByContractDuration", query = "SELECT p FROM PrmsRevenueContarct p WHERE p.contractDuration = :contractDuration"),
    @NamedQuery(name = "PrmsRevenueContarct.findByPaymentPeriod", query = "SELECT p FROM PrmsRevenueContarct p WHERE p.paymentPeriod = :paymentPeriod"),
    @NamedQuery(name = "PrmsRevenueContarct.findByPaymentAmount", query = "SELECT p FROM PrmsRevenueContarct p WHERE p.paymentAmount = :paymentAmount"),
    @NamedQuery(name = "PrmsRevenueContarct.findByEffectiveDate", query = "SELECT p FROM PrmsRevenueContarct p WHERE p.effectiveDate = :effectiveDate"),
    @NamedQuery(name = "PrmsRevenueContarct.findByCurrencyId", query = "SELECT p FROM PrmsRevenueContarct p WHERE p.currencyId = :currencyId"),
    @NamedQuery(name = "PrmsRevenueContarct.findByStatus", query = "SELECT p FROM PrmsRevenueContarct p WHERE p.status = :status"),
    @NamedQuery(name = "PrmsRevenueContarct.findByRevNoLike", query = "SELECT p FROM PrmsRevenueContarct p WHERE p.revContractNo LIKE :revContractNo"),
    @NamedQuery(name = "PrmsRevenueContarct.findByContractDescription", query = "SELECT p FROM PrmsRevenueContarct p WHERE p.contractDescription = :contractDescription"),
    @NamedQuery(name = "PrmsRevenueContarct.findByContractType", query = "SELECT p FROM PrmsRevenueContarct p WHERE p.contractType = :contractType"),
    @NamedQuery(name = "PrmsRevenueContarct.findByFormOfContract", query = "SELECT p FROM PrmsRevenueContarct p WHERE p.formOfContract=:formOfContract")})
public class PrmsRevenueContarct implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(generator = "PRMS_REVCONTRACT_SEQ", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "PRMS_REVCONTRACT_SEQ", sequenceName = "PRMS_REVCONTRACT_SEQ", allocationSize = 1)
    @Column(name = "ID", nullable = false, precision = 0, scale = -127)
    private BigDecimal id;
    @Size(max = 50)
    @Column(name = "REV_CONTRACT_NO", length = 50)
    private String revContractNo;
    @Size(max = 35)
    @Column(name = "CONTRACT_NAME", length = 35)
    private String contractName;
    @Size(max = 35)
    @Column(name = "PERFORMANCE_OBLIGATION_TYPE", length = 35)
    private String performanceObligationType;
    @Size(max = 50)
    @Column(name = "PERFORMANCE_OBLIGATION_SATIS", length = 50)
    private String performanceObligationSatis;
    @Size(max = 35)
    @Column(name = "CONTRACTED_WITH", length = 35)
    private String contractedWith;
    @Column(name = "CONTRACTED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date contractedDate;

    @Column(name = "ESTIMATED_TOTAL_COST")
    private BigDecimal estimatedTotalCost;
    @Size(max = 35)
    @Column(name = "CONTRACT_DURATION", length = 35)
    private String contractDuration;
    @Size(max = 35)
    @Column(name = "PAYMENT_PERIOD", length = 35)
    private String paymentPeriod;
    @Column(name = "PAYMENT_AMOUNT")
    private BigInteger paymentAmount;
    @Column(name = "EFFECTIVE_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date effectiveDate;
    @Size(max = 35)
    @Column(name = "STATUS", length = 35)
    private String status;
    @Size(max = 100)
    @Column(name = "CONTRACT_DESCRRIPTION", length = 100)
    private String contractDescription;
    @Size(max = 35)
    @Column(name = "CONTRACT_TYPE", length = 35)
    private String contractType;
    @Size(max = 35)
    @Column(name = "FORM_OF_CONTRACT", length = 35)
    private String formOfContract;
    @JoinColumn(name = "CURRENCY_ID", referencedColumnName = "CURRENCY_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private FmsLuCurrency currencyId;
    @JoinColumn(name = "GL_ID", referencedColumnName = "GENERAL_LEDGER_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private FmsGeneralLedger glId;
    @JoinColumn(name = "SUBSIDIARY_ID", referencedColumnName = "SUBSIDIARY_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private FmsSubsidiaryLedger subsidiaryIds;
    @OneToMany(mappedBy = "revenueContractId", fetch = FetchType.LAZY)
    private List<PrmsRevenueContractDetail> prmsRevenueContractDetailList;

    public PrmsRevenueContarct() {
    }

    public PrmsRevenueContarct(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getRevContractNo() {
        return revContractNo;
    }

    public void setRevContractNo(String revContractNo) {
        this.revContractNo = revContractNo;
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public String getPerformanceObligationType() {
        return performanceObligationType;
    }

    public void setPerformanceObligationType(String performanceObligationType) {
        this.performanceObligationType = performanceObligationType;
    }

    public String getPerformanceObligationSatis() {
        return performanceObligationSatis;
    }

    public void setPerformanceObligationSatis(String performanceObligationSatis) {
        this.performanceObligationSatis = performanceObligationSatis;
    }

    public String getContractedWith() {
        return contractedWith;
    }

    public void setContractedWith(String contractedWith) {
        this.contractedWith = contractedWith;
    }

    public Date getContractedDate() {
        return contractedDate;
    }

    public void setContractedDate(Date contractedDate) {
        this.contractedDate = contractedDate;
    }

    public BigDecimal getEstimatedTotalCost() {
        return estimatedTotalCost;
    }

    public void setEstimatedTotalCost(BigDecimal estimatedTotalCost) {
        this.estimatedTotalCost = estimatedTotalCost;
    }

    public String getContractDuration() {
        return contractDuration;
    }

    public void setContractDuration(String contractDuration) {
        this.contractDuration = contractDuration;
    }

    public String getPaymentPeriod() {
        return paymentPeriod;
    }

    public void setPaymentPeriod(String paymentPeriod) {
        this.paymentPeriod = paymentPeriod;
    }

    public BigInteger getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(BigInteger paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getContractDescription() {
        return contractDescription;
    }

    public void setContractDescription(String contractDescription) {
        this.contractDescription = contractDescription;
    }

    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }

    public String getFormOfContract() {
        return formOfContract;
    }

    public void setFormOfContract(String formOfContract) {
        this.formOfContract = formOfContract;
    }

    public FmsLuCurrency getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(FmsLuCurrency currencyId) {
        this.currencyId = currencyId;
    }

    public FmsGeneralLedger getGlId() {
        return glId;
    }

    public void setGlId(FmsGeneralLedger glId) {
        this.glId = glId;
    }

    public FmsSubsidiaryLedger getSubsidiaryIds() {
        return subsidiaryIds;
    }

    public void setSubsidiaryIds(FmsSubsidiaryLedger subsidiaryIds) {
        this.subsidiaryIds = subsidiaryIds;
    }

    @XmlTransient
    public List<PrmsRevenueContractDetail> getPrmsRevenueContractDetailList() {
        if (prmsRevenueContractDetailList == null) {
            prmsRevenueContractDetailList = new ArrayList<>();
        }
        return prmsRevenueContractDetailList;
    }

    public void setPrmsRevenueContractDetailList(List<PrmsRevenueContractDetail> prmsRevenueContractDetailList) {
        this.prmsRevenueContractDetailList = prmsRevenueContractDetailList;
    }

    @Transient
    private String columnValue;

    public String getColumnValue() {
        return columnValue;
    }

    public void setColumnValue(String columnValue) {
        this.columnValue = columnValue;
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
        if (!(object instanceof PrmsRevenueContarct)) {
            return false;
        }
        PrmsRevenueContarct other = (PrmsRevenueContarct) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.prms.entity.PrmsRevenueContarct[ id=" + id + " ]";
    }

    public void addDetailData(PrmsRevenueContractDetail prmsRevenueContractDetail) {
        if (prmsRevenueContractDetail.getRevenueContractId() != this) {
            this.getPrmsRevenueContractDetailList().add(prmsRevenueContractDetail);
            prmsRevenueContractDetail.setRevenueContractId(this);
        }
    }

}
