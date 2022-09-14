/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.entity;

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
@Table(name = "PRMS_REVENUE_CONTRACT_DETAIL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrmsRevenueContractDetail.findAll", query = "SELECT p FROM PrmsRevenueContractDetail p"),
    @NamedQuery(name = "PrmsRevenueContractDetail.findById", query = "SELECT p FROM PrmsRevenueContractDetail p WHERE p.id = :id"),
    @NamedQuery(name = "PrmsRevenueContractDetail.findByDiscount", query = "SELECT p FROM PrmsRevenueContractDetail p WHERE p.discount = :discount"),
    @NamedQuery(name = "PrmsRevenueContractDetail.findByCosiderationAmount", query = "SELECT p FROM PrmsRevenueContractDetail p WHERE p.cosiderationAmount = :cosiderationAmount"),
    @NamedQuery(name = "PrmsRevenueContractDetail.findByInterest", query = "SELECT p FROM PrmsRevenueContractDetail p WHERE p.interest = :interest"),
    @NamedQuery(name = "PrmsRevenueContractDetail.findByStandAloneSellingPrice", query = "SELECT p FROM PrmsRevenueContractDetail p WHERE p.standAloneSellingPrice = :standAloneSellingPrice"),
    @NamedQuery(name = "PrmsRevenueContractDetail.findByStatus", query = "SELECT p FROM PrmsRevenueContractDetail p WHERE p.status = :status"),
    @NamedQuery(name = "PrmsRevenueContractDetail.findByTransactionDate", query = "SELECT p FROM PrmsRevenueContractDetail p WHERE p.transactionDate = :transactionDate"),
    @NamedQuery(name = "PrmsRevenueContractDetail.findByPerformanceObligation", query = "SELECT p FROM PrmsRevenueContractDetail p WHERE p.performanceObligation=:performanceObligation")})
public class PrmsRevenueContractDetail implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(generator = "PRMS_REVCONTRACTDETAIL_SEQ", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(sequenceName = "PRMS_REVCONTRACTDETAIL_SEQ", name = "PRMS_REVCONTRACTDETAIL_SEQ", allocationSize = 1)
    @Column(name = "ID", nullable = false, precision = 0, scale = -127)
    private BigDecimal id;
    @Column(name = "DISCOUNT")
    private BigDecimal discount;
    @Size(max = 35)
    @Column(name = "COSIDERATION_AMOUNT", length = 35)
    private String cosiderationAmount;
    @Size(max = 35)
    @Column(name = "INTEREST", length = 35)
    private String interest;
    @Column(name = "STAND_ALONE_SELLING_PRICE")
    private BigDecimal standAloneSellingPrice;
    @Size(max = 35)
    @Column(name = "STATUS", length = 35)
    private String status;
    @Column(name = "TRANSACTION_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date transactionDate;
    @Size(max = 100)
    @Column(name = "PERFORMANCE_OBLIGATION", length = 100)
    private String performanceObligation;
    @JoinColumn(name = "REVENUE_CONTRACT_ID", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrmsRevenueContarct revenueContractId;

    public PrmsRevenueContractDetail() {
    }

    public PrmsRevenueContractDetail(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public String getCosiderationAmount() {
        return cosiderationAmount;
    }

    public void setCosiderationAmount(String cosiderationAmount) {
        this.cosiderationAmount = cosiderationAmount;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public BigDecimal getStandAloneSellingPrice() {
        return standAloneSellingPrice;
    }

    public void setStandAloneSellingPrice(BigDecimal standAloneSellingPrice) {
        this.standAloneSellingPrice = standAloneSellingPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getPerformanceObligation() {
        return performanceObligation;
    }

    public void setPerformanceObligation(String performanceObligation) {
        this.performanceObligation = performanceObligation;
    }

    public PrmsRevenueContarct getRevenueContractId() {
        return revenueContractId;
    }

    public void setRevenueContractId(PrmsRevenueContarct revenueContractId) {
        this.revenueContractId = revenueContractId;
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
        if (!(object instanceof PrmsRevenueContractDetail)) {
            return false;
        }
        PrmsRevenueContractDetail other = (PrmsRevenueContractDetail) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.prms.entity.PrmsRevenueContractDetail[ id=" + id + " ]";
    }

}
