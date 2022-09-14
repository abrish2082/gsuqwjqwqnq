/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author user
 */

@Entity
@Table(name = "FMS_FA_DEPRECIATION")
@XmlRootElement
@NamedQueries({
//    @NamedQuery(name = "FmsFaDepreciation.findAllForReport", query = "SELECT f FROM FmsFaDepreciation f join  f.fixedAssetId fa join fa.sivFixMatId siv join siv.sivDetailId sivd join sivd.srDetId did join did.materialId mat join mat.luMatSubCatId sub join sub.luCatId matcat"),
//    @NamedQuery(name = "FmsFaDepreciation.serchForReport", query = "SELECT  f FROM FmsFaDepreciation f join  f.fixedAssetId fa join fa.sivFixMatId siv join siv.sivDetailId sivd join sivd.srDetId did join did.materialId mat join mat.luMatSubCatId sub join sub.luCatId matcat WHERE matcat.luMatCatId = :luMatCatId"),
//    @NamedQuery(name = "FmsFaDepreciation.returnBalance", query = "SELECT f FROM FmsFaDepreciation f join  f.fixedAssetId fa join fa.sivFixMatId siv join siv.sivDetailId sivd join sivd.srDetId did join did.materialId mat join mat.luMatSubCatId sub join sub.luCatId matcat WHERE matcat.luMatCatId = :luMatCatId group by matcat.luMatCatId"),
    @NamedQuery(name = "FmsFaDepreciation.showAll", query = "SELECT f FROM FmsFaDepreciation f group by f order by f.fixedAssetId"),
    @NamedQuery(name = "FmsFaDepreciation.findAll", query = "SELECT f FROM FmsFaDepreciation f"),
    @NamedQuery(name = "FmsFaDepreciation.findByAccountingPeriod", query = "SELECT f FROM FmsFaDepreciation f WHERE f.accountingPeriod = :accountingPeriod"),
    @NamedQuery(name = "FmsFaDepreciation.findByDepAmount", query = "SELECT f FROM FmsFaDepreciation f WHERE f.depAmount = :depAmount"),
    @NamedQuery(name = "FmsFaDepreciation.findByBalance", query = "SELECT f FROM FmsFaDepreciation f WHERE f.balance = :balance"),
    @NamedQuery(name = "FmsFaDepreciation.findById", query = "SELECT f FROM FmsFaDepreciation f WHERE f.id = :id"),
    @NamedQuery(name = "FmsFaDepreciation.findByRate", query = "SELECT f FROM FmsFaDepreciation f WHERE f.rate = :rate"),
    @NamedQuery(name = "FmsFaDepreciation.findByCost", query = "SELECT f FROM FmsFaDepreciation f WHERE f.cost = :cost"),
    @NamedQuery(name = "FmsFaDepreciation.findByPullingRate", query = "SELECT f FROM FmsFaDepreciation f WHERE f.pullingRate = :pullingRate"),
    @NamedQuery(name = "FmsFaDepreciation.findByPullingDepAmt", query = "SELECT f FROM FmsFaDepreciation f WHERE f.pullingDepAmt = :pullingDepAmt"),
    @NamedQuery(name = "FmsFaDepreciation.findByPullingBalance", query = "SELECT f FROM FmsFaDepreciation f WHERE f.pullingBalance = :pullingBalance")})
public class FmsFaDepreciation implements Serializable {
    private static final long serialVersionUID = 1L;
    @Size(max = 100)
    @Column(name = "ACCOUNTING_PERIOD")
    private String accountingPeriod;
    @Column(name = "DEP_AMOUNT")
    private Long depAmount;
    @Column(name = "BALANCE")
    private Long balance;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private BigDecimal id;
    @Column(name = "RATE")
    private BigInteger rate;
    @Column(name = "COST")
    private BigInteger cost;
    @Column(name = "PULLING_RATE")
    private BigInteger pullingRate;
    @Column(name = "PULLING_DEP_AMT")
    private BigInteger pullingDepAmt;
    @Column(name = "PULLING_BALANCE")
    private BigInteger pullingBalance;
    @JoinColumn(name = "FIXED_ASSET_ID", referencedColumnName = "FIXED_ASSET_ID")
    @ManyToOne
    private FmsFixedAsset fixedAssetId;

    /**
     *
     */
    public FmsFaDepreciation() {
    }

    /**
     *
     * @param id
     */
    public FmsFaDepreciation(BigDecimal id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public String getAccountingPeriod() {
        return accountingPeriod;
    }

    /**
     *
     * @param accountingPeriod
     */
    public void setAccountingPeriod(String accountingPeriod) {
        this.accountingPeriod = accountingPeriod;
    }

    /**
     *
     * @return
     */
    public Long getDepAmount() {
        return depAmount;
    }

    /**
     *
     * @param depAmount
     */
    public void setDepAmount(Long depAmount) {
        this.depAmount = depAmount;
    }

    /**
     *
     * @return
     */
    public Long getBalance() {
        return balance;
    }

    /**
     *
     * @param balance
     */
    public void setBalance(Long balance) {
        this.balance = balance;
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
    public BigInteger getRate() {
        return rate;
    }

    /**
     *
     * @param rate
     */
    public void setRate(BigInteger rate) {
        this.rate = rate;
    }

    /**
     *
     * @return
     */
    public BigInteger getCost() {
        return cost;
    }

    /**
     *
     * @param cost
     */
    public void setCost(BigInteger cost) {
        this.cost = cost;
    }

    /**
     *
     * @return
     */
    public BigInteger getPullingRate() {
        return pullingRate;
    }

    /**
     *
     * @param pullingRate
     */
    public void setPullingRate(BigInteger pullingRate) {
        this.pullingRate = pullingRate;
    }

    /**
     *
     * @return
     */
    public BigInteger getPullingDepAmt() {
        return pullingDepAmt;
    }

    /**
     *
     * @param pullingDepAmt
     */
    public void setPullingDepAmt(BigInteger pullingDepAmt) {
        this.pullingDepAmt = pullingDepAmt;
    }

    /**
     *
     * @return
     */
    public BigInteger getPullingBalance() {
        return pullingBalance;
    }

    /**
     *
     * @param pullingBalance
     */
    public void setPullingBalance(BigInteger pullingBalance) {
        this.pullingBalance = pullingBalance;
    }

    /**
     *
     * @return
     */
    public FmsFixedAsset getFixedAssetId() {
        return fixedAssetId;
    }

    /**
     *
     * @param fixedAssetId
     */
    public void setFixedAssetId(FmsFixedAsset fixedAssetId) {
        this.fixedAssetId = fixedAssetId;
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
        if (!(object instanceof FmsFaDepreciation)) {
            return false;
        }
        FmsFaDepreciation other = (FmsFaDepreciation) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.insa.erp.ibfms.entity.FmsFaDepreciation[ id=" + id + " ]";
    }
    
}
