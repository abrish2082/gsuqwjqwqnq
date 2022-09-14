/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.entity;

import java.io.Serializable;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author beharuh
 */
@Entity
@Table(name = "FMS_MONTHLY_IMPOORT_COST")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsMonthlyImpoortCost.findAll", query = "SELECT f FROM FmsMonthlyImpoortCost f"),
    @NamedQuery(name = "FmsMonthlyImpoortCost.findAllSelected", query = "SELECT f FROM FmsMonthlyImpoortCost f where f.product =:product and f.monthName =:monthName"),
    @NamedQuery(name = "FmsMonthlyImpoortCost.findById", query = "SELECT f FROM FmsMonthlyImpoortCost f WHERE f.id = :id"),
    @NamedQuery(name = "FmsMonthlyImpoortCost.findByBank", query = "SELECT f FROM FmsMonthlyImpoortCost f WHERE f.bank = :bank"),
    @NamedQuery(name = "FmsMonthlyImpoortCost.findByInsurance", query = "SELECT f FROM FmsMonthlyImpoortCost f WHERE f.insurance = :insurance"),
    @NamedQuery(name = "FmsMonthlyImpoortCost.findByStorageHandling", query = "SELECT f FROM FmsMonthlyImpoortCost f WHERE f.storageHandling = :storageHandling"),
    @NamedQuery(name = "FmsMonthlyImpoortCost.findByAnalysis", query = "SELECT f FROM FmsMonthlyImpoortCost f WHERE f.analysis = :analysis"),
    @NamedQuery(name = "FmsMonthlyImpoortCost.findByPort", query = "SELECT f FROM FmsMonthlyImpoortCost f WHERE f.port = :port"),
    @NamedQuery(name = "FmsMonthlyImpoortCost.findByTotal", query = "SELECT f FROM FmsMonthlyImpoortCost f WHERE f.total = :total"),
    @NamedQuery(name = "FmsMonthlyImpoortCost.findByLit20", query = "SELECT f FROM FmsMonthlyImpoortCost f WHERE f.lit20 = :lit20"),
    @NamedQuery(name = "FmsMonthlyImpoortCost.findByCostPerLit", query = "SELECT f FROM FmsMonthlyImpoortCost f WHERE f.costPerLit = :costPerLit"),
    @NamedQuery(name = "FmsMonthlyImpoortCost.findByInventoryCost", query = "SELECT f FROM FmsMonthlyImpoortCost f WHERE f.inventoryCost = :inventoryCost")})
public class FmsMonthlyImpoortCost implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FMS_MONTHLY_IMPOORT_COST_SEQ")
    @SequenceGenerator( name = "FMS_MONTHLY_IMPOORT_COST_SEQ", sequenceName = "FMS_MONTHLY_IMPOORT_COST_SEQ", allocationSize = 1)
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private Integer id;
    private double bank;
    private double insurance;
    @Column(name = "STORAGE_HANDLING")
    private double storageHandling;
    private double analysis;
    private double port;
    private double total;
    @Column(name = "LIT_20")
    private double lit20;
    @Column(name = "COST_PER_LIT")
    private double costPerLit;
    @Column(name = "INVENTORY_COST")
    private double inventoryCost;
    @Column(name = "product")
    private String product;
    @Column(name = "MONTH_name")
    private String monthName;

    /**
     *
     */
    public FmsMonthlyImpoortCost() {
    }

    /**
     *
     * @param id
     */
    public FmsMonthlyImpoortCost(Integer id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public double getBank() {
        return bank;
    }

    /**
     *
     * @param bank
     */
    public void setBank(double bank) {
        this.bank = bank;
    }

    /**
     *
     * @return
     */
    public double getInsurance() {
        return insurance;
    }

    /**
     *
     * @param insurance
     */
    public void setInsurance(double insurance) {
        this.insurance = insurance;
    }

    /**
     *
     * @return
     */
    public double getStorageHandling() {
        return storageHandling;
    }

    /**
     *
     * @param storageHandling
     */
    public void setStorageHandling(double storageHandling) {
        this.storageHandling = storageHandling;
    }

    /**
     *
     * @return
     */
    public double getAnalysis() {
        return analysis;
    }

    /**
     *
     * @param analysis
     */
    public void setAnalysis(double analysis) {
        this.analysis = analysis;
    }

    /**
     *
     * @return
     */
    public double getPort() {
        return port;
    }

    /**
     *
     * @param port
     */
    public void setPort(double port) {
        this.port = port;
    }

    /**
     *
     * @return
     */
    public double getTotal() {
        return total;
    }

    /**
     *
     * @param total
     */
    public void setTotal(double total) {
        this.total = total;
    }

    /**
     *
     * @return
     */
    public double getLit20() {
        return lit20;
    }

    /**
     *
     * @param lit20
     */
    public void setLit20(double lit20) {
        this.lit20 = lit20;
    }

    /**
     *
     * @return
     */
    public double getCostPerLit() {
        return costPerLit;
    }

    /**
     *
     * @param costPerLit
     */
    public void setCostPerLit(double costPerLit) {
        this.costPerLit = costPerLit;
    }

    /**
     *
     * @return
     */
    public double getInventoryCost() {
        return inventoryCost;
    }

    /**
     *
     * @param inventoryCost
     */
    public void setInventoryCost(double inventoryCost) {
        this.inventoryCost = inventoryCost;
    }

    /**
     *
     * @return
     */
    public String getProduct() {
        return product;
    }

    /**
     *
     * @param product
     */
    public void setProduct(String product) {
        this.product = product;
    }

    /**
     *
     * @return
     */
    public String getMonthName() {
        return monthName;
    }

    /**
     *
     * @param monthName
     */
    public void setMonthName(String monthName) {
        this.monthName = monthName;
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
        if (!(object instanceof FmsMonthlyImpoortCost)) {
            return false;
        }
        FmsMonthlyImpoortCost other = (FmsMonthlyImpoortCost) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.insa.erp.ibfms.entity.FmsMonthlyImpoortCost[ id=" + id + " ]";
    }

}
