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
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author beharuh
 */
@Entity
@Table(name = "fms_forcasted_fob_price" )
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsForcastedFobPrice.findAll", query = "SELECT f FROM FmsForcastedFobPrice f"),
    @NamedQuery(name = "FmsForcastedFobPrice.findById", query = "SELECT f FROM FmsForcastedFobPrice f WHERE f.id = :id"),
    @NamedQuery(name = "FmsForcastedFobPrice.findByPId", query = "SELECT f FROM FmsForcastedFobPrice f WHERE f.pId = :pId"),
    @NamedQuery(name = "FmsForcastedFobPrice.findByBudgetYear", query = "SELECT f FROM FmsForcastedFobPrice f WHERE f.budgetYear = :budgetYear"),
    @NamedQuery(name = "FmsForcastedFobPrice.findByRegistrDate", query = "SELECT f FROM FmsForcastedFobPrice f WHERE f.registrDate = :registrDate"),
    @NamedQuery(name = "FmsForcastedFobPrice.findByDateFrom", query = "SELECT f FROM FmsForcastedFobPrice f WHERE f.dateFrom = :dateFrom"),
    @NamedQuery(name = "FmsForcastedFobPrice.findByBYearSelected", query = "SELECT f FROM FmsForcastedFobPrice f WHERE f.budgetYear like :budgetYear"),
    @NamedQuery(name = "FmsForcastedFobPrice.findByDateTo", query = "SELECT f FROM FmsForcastedFobPrice f WHERE f.dateTo = :dateTo"),
    @NamedQuery(name = "FmsForcastedFobPrice.findByAverageFobPrice", query = "SELECT f FROM FmsForcastedFobPrice f WHERE f.averageFobPrice = :averageFobPrice")})
public class FmsForcastedFobPrice implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer id;
    @Column(name = "p_id")
    private Integer pId;
    @Size(max = 45)
    @Column(name = "date_from", length = 45)
    private String dateFrom;
    @Size(max = 45)
    @Column(name = "date_to", length = 45)
    private String dateTo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "average_fob_price", nullable = false)
    private double averageFobPrice;

    @Size(max = 45)
    @Column(name = "budget_year", length = 45)
    private String budgetYear;

    @Size(max = 45)
    @Column(name = "registr_date", length = 45)
    private String registrDate;

    /**
     *
     */
    public FmsForcastedFobPrice() {//registrDate
    }

    /**
     *
     * @param id
     */
    public FmsForcastedFobPrice(Integer id) {
        this.id = id;
    }

    /**
     *
     * @param id
     * @param averageFobPrice
     */
    public FmsForcastedFobPrice(Integer id, double averageFobPrice) {
        this.id = id;
        this.averageFobPrice = averageFobPrice;
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
    public Integer getPId() {
        return pId;
    }

    /**
     *
     * @param pId
     */
    public void setPId(Integer pId) {
        this.pId = pId;
    }

    /**
     *
     * @return
     */
    public String getDateFrom() {
        return dateFrom;
    }

    /**
     *
     * @param dateFrom
     */
    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    /**
     *
     * @return
     */
    public String getDateTo() {
        return dateTo;
    }

    /**
     *
     * @param dateTo
     */
    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }

    /**
     *
     * @return
     */
    public double getAverageFobPrice() {
        return averageFobPrice;
    }

    /**
     *
     * @param averageFobPrice
     */
    public void setAverageFobPrice(double averageFobPrice) {
        this.averageFobPrice = averageFobPrice;
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
    public String getRegistrDate() {
        return registrDate;
    }

    /**
     *
     * @param regDate
     */
    public void setRegistrDate(String regDate) {
        this.registrDate = regDate;
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
        if (!(object instanceof FmsForcastedFobPrice)) {
            return false;
        }
        FmsForcastedFobPrice other = (FmsForcastedFobPrice) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return budgetYear;
    }

}
