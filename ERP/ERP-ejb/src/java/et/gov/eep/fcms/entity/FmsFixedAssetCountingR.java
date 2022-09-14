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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author muller
 */
@Entity
@Table(name = "FMS_FIXED_ASSET_COUNTING_R")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsFixedAssetCountingR.findAll", query = "SELECT f FROM FmsFixedAssetCountingR f"),
    @NamedQuery(name = "FmsFixedAssetCountingR.findByFaCountingId", query = "SELECT f FROM FmsFixedAssetCountingR f WHERE f.faCountingId = :faCountingId"),
    @NamedQuery(name = "FmsFixedAssetCountingR.findByBudgetYear", query = "SELECT f FROM FmsFixedAssetCountingR f WHERE f.budgetYear = :budgetYear"),
    @NamedQuery(name = "FmsFixedAssetCountingR.findByIsfound", query = "SELECT f FROM FmsFixedAssetCountingR f WHERE f.isfound = :isfound"),
    @NamedQuery(name = "FmsFixedAssetCountingR.findByCost", query = "SELECT f FROM FmsFixedAssetCountingR f WHERE f.cost = :cost"),
    @NamedQuery(name = "FmsFixedAssetCountingR.findByDescription", query = "SELECT f FROM FmsFixedAssetCountingR f WHERE f.description = :description"),
    @NamedQuery(name = "FmsFixedAssetCountingR.findByStorename", query = "SELECT f FROM FmsFixedAssetCountingR f WHERE f.storename = :storename"),
    @NamedQuery(name = "FmsFixedAssetCountingR.findByCurrentBalance", query = "SELECT f FROM FmsFixedAssetCountingR f WHERE f.currentBalance = :currentBalance"),
    @NamedQuery(name = "FmsFixedAssetCountingR.findByCountingBalance", query = "SELECT f FROM FmsFixedAssetCountingR f WHERE f.countingBalance = :countingBalance"),
    @NamedQuery(name = "FmsFixedAssetCountingR.findByAdjestmentAmount", query = "SELECT f FROM FmsFixedAssetCountingR f WHERE f.adjestmentAmount = :adjestmentAmount"),
    @NamedQuery(name = "FmsFixedAssetCountingR.findByMaterialCode", query = "SELECT f FROM FmsFixedAssetCountingR f WHERE f.materialCode = :materialCode")})
public class FmsFixedAssetCountingR implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "FA_COUNTING_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FMS_FIXED_ASSET_COUNTING_R_SEQ")
    @SequenceGenerator( name = "FMS_FIXED_ASSET_COUNTING_R_SEQ", sequenceName = "FMS_FIXED_ASSET_COUNTING_R_SEQ", allocationSize = 1)
    private Integer faCountingId;
    @Size(max = 20)
    @Column(name = "BUDGET_YEAR")
    private String budgetYear;
    @Column(name = "ISFOUND")
    private Integer isfound;
    @Column(name = "COST")
    private BigInteger cost;
    @Size(max = 255)
    @Column(name = "DESCRIPTION")
    private String description;
    @Size(max = 255)
    @Column(name = "STORENAME")
    private String storename;
    @Column(name = "CURRENT_BALANCE")
    private BigInteger currentBalance;
    @Column(name = "COUNTING_BALANCE")
    private BigInteger countingBalance;
    @Column(name = "ADJESTMENT_AMOUNT")
    private BigInteger adjestmentAmount;
    @Size(max = 255)
    @Column(name = "MATERIAL_CODE")
    private String materialCode;

    /**
     *
     */
    public FmsFixedAssetCountingR() {
    }

    /**
     *
     * @param faCountingId
     */
    public FmsFixedAssetCountingR(Integer faCountingId) {
        this.faCountingId = faCountingId;
    }

    /**
     *
     * @return
     */
    public Integer getFaCountingId() {
        return faCountingId;
    }

    /**
     *
     * @param faCountingId
     */
    public void setFaCountingId(Integer faCountingId) {
        this.faCountingId = faCountingId;
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
    public Integer getIsfound() {
        return isfound;
    }

    /**
     *
     * @param isfound
     */
    public void setIsfound(Integer isfound) {
        this.isfound = isfound;
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
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     *
     * @return
     */
    public String getStorename() {
        return storename;
    }

    /**
     *
     * @param storename
     */
    public void setStorename(String storename) {
        this.storename = storename;
    }

    /**
     *
     * @return
     */
    public BigInteger getCurrentBalance() {
        return currentBalance;
    }

    /**
     *
     * @param currentBalance
     */
    public void setCurrentBalance(BigInteger currentBalance) {
        this.currentBalance = currentBalance;
    }

    /**
     *
     * @return
     */
    public BigInteger getCountingBalance() {
        return countingBalance;
    }

    /**
     *
     * @param countingBalance
     */
    public void setCountingBalance(BigInteger countingBalance) {
        this.countingBalance = countingBalance;
    }

    /**
     *
     * @return
     */
    public BigInteger getAdjestmentAmount() {
        return adjestmentAmount;
    }

    /**
     *
     * @param adjestmentAmount
     */
    public void setAdjestmentAmount(BigInteger adjestmentAmount) {
        this.adjestmentAmount = adjestmentAmount;
    }

    /**
     *
     * @return
     */
    public String getMaterialCode() {
        return materialCode;
    }

    /**
     *
     * @param materialCode
     */
    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (faCountingId != null ? faCountingId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FmsFixedAssetCountingR)) {
            return false;
        }
        FmsFixedAssetCountingR other = (FmsFixedAssetCountingR) object;
        if ((this.faCountingId == null && other.faCountingId != null) || (this.faCountingId != null && !this.faCountingId.equals(other.faCountingId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.insa.erp.ibfms.entity.FmsFixedAssetCountingR[ faCountingId=" + faCountingId + " ]";
    }
    
}
