/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.entity;

import et.gov.eep.commonApplications.entity.ComLuProduct;
import java.io.Serializable;
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
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author beharu
 */
@Entity
@Table(name = "scm_daily_fob")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ScmDailyFob.findAll", query = "SELECT s FROM ScmDailyFob s"),
    @NamedQuery(name = "ScmDailyFob.findById", query = "SELECT s FROM ScmDailyFob s WHERE s.id = :id"),
//    @NamedQuery(name = "ScmDailyFob.findByPId", query = "SELECT s FROM ScmDailyFob s WHERE s.pId = :pId"),
    @NamedQuery(name = "ScmDailyFob.findByRegDate", query = "SELECT s FROM ScmDailyFob s WHERE s.regDate = :regDate"),
    @NamedQuery(name = "ScmDailyFob.findByMarcketPlaceSelected", query = "SELECT DISTINCT s.marcketPlace FROM ScmDailyFob s WHERE upper(s.marcketPlace) like :marcketPlace or lower(s.marcketPlace) like :marcketPlace"),
    @NamedQuery(name = "ScmDailyFob.findBybYearSelected", query = "SELECT DISTINCT s.budgetYear FROM ScmDailyFob s WHERE s.budgetYear like :budgetYear"),
    @NamedQuery(name = "ScmDailyFob.findByMarcketPlace", query = "SELECT s FROM ScmDailyFob s WHERE s.marcketPlace = :marcketPlace"),
    @NamedQuery(name = "ScmDailyFob.findByBYear", query = "SELECT s FROM ScmDailyFob s WHERE s.marcketPlace = :marcketPlace"),
    @NamedQuery(name = "ScmDailyFob.findByMonthNameSelected", query = "SELECT DISTINCT upper(s.monthName) FROM ScmDailyFob s WHERE s.monthName Like :monthName"),
    @NamedQuery(name = "ScmDailyFob.findByMonthNameAndPid", query = "SELECT s FROM ScmDailyFob s WHERE UPPER(s.monthName) =:monthName AND s.productId =:productId"),
    @NamedQuery(name = "ScmDailyFob.findByLowPrice", query = "SELECT s FROM ScmDailyFob s WHERE s.lowPrice = :lowPrice"),
    @NamedQuery(name = "ScmDailyFob.findByHighPrice", query = "SELECT s FROM ScmDailyFob s WHERE s.highPrice = :highPrice"),
    @NamedQuery(name = "ScmDailyFob.findByMeanPrice", query = "SELECT s FROM ScmDailyFob s WHERE s.meanPrice = :meanPrice"),
    @NamedQuery(name = "ScmDailyFob.findByTotalPrice", query = "SELECT s FROM ScmDailyFob s WHERE s.totalPrice = :totalPrice")})
public class ScmDailyFob implements Serializable {//b_year

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer id;
//    @Column(name = "p_id")
//    private Integer pId;
    @Size(max = 45)
    @Column(name = "reg_date", length = 45)
    private String regDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "low_price", nullable = false)
    private double lowPrice;
    @Basic(optional = false)
    @NotNull
    @Column(name = "high_price", nullable = false)
    private double highPrice;
    @Basic(optional = false)
    @NotNull
    @Column(name = "mean_price", nullable = false)
    private double meanPrice;
    @Basic(optional = false)
    @NotNull
    @Column(name = "total_price", nullable = false)
    private double totalPrice;

    @Size(max = 45)
    @Column(name = "marcket_place", length = 45)
    private String marcketPlace;
    @Transient
    String week;

    @Size(max = 45)
    @Column(name = "budget_year", length = 45)
    private String budgetYear;

    @Size(max = 45)
    @Column(name = "month_name", length = 45)
    private String monthName;
    @JoinColumn(name = "P_ID", referencedColumnName = "PRODUCT_ID")
    @ManyToOne
    private ComLuProduct productId;
//     @JoinColumn(name = "PRODUCT_TYPE_ID", referencedColumnName = "PRODUCT_TYPE_ID")
//    @ManyToOne
//    private ComLuProductType productTypeId;

    /**
     *
     */
    public ScmDailyFob() {//marcket_place
    }

    /**
     *
     * @param id
     */
    public ScmDailyFob(Integer id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public String getWeek() {
        return week;
    }

    /**
     *
     * @param week
     */
    public void setWeek(String week) {
        this.week = week;
    }

    /**
     *
     * @param id
     * @param lowPrice
     * @param highPrice
     * @param meanPrice
     * @param totalPrice
     */
    public ScmDailyFob(Integer id, double lowPrice, double highPrice, double meanPrice, double totalPrice) {
        this.id = id;
        this.lowPrice = lowPrice;
        this.highPrice = highPrice;
        this.meanPrice = meanPrice;
        this.totalPrice = totalPrice;
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
//
//    public Integer getPId() {
//        return pId;
//    }
//
//    public void setPId(Integer pId) {
//        this.pId = pId;
//    }

    /**
     *
     * @return
     */
    public String getRegDate() {
        return regDate;
    }

    /**
     *
     * @param regDate
     */
    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    /**
     *
     * @return
     */
    public double getLowPrice() {
        return lowPrice;
    }

    /**
     *
     * @param lowPrice
     */
    public void setLowPrice(double lowPrice) {
        this.lowPrice = lowPrice;
    }

    /**
     *
     * @return
     */
    public double getHighPrice() {
        return highPrice;
    }

    /**
     *
     * @param highPrice
     */
    public void setHighPrice(double highPrice) {
        this.highPrice = highPrice;
    }

    /**
     *
     * @return
     */
    public double getMeanPrice() {
        return meanPrice;
    }

    /**
     *
     * @param meanPrice
     */
    public void setMeanPrice(double meanPrice) {
        this.meanPrice = meanPrice;
    }

    /**
     *
     * @return
     */
    public double getTotalPrice() {
        return totalPrice;
    }

    /**
     *
     * @param totalPrice
     */
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    /**
     *
     * @return
     */
    public String getMarcketPlace() {
        return marcketPlace;
    }

    /**
     *
     * @param marcketPlace
     */
    public void setMarcketPlace(String marcketPlace) {
        this.marcketPlace = marcketPlace;
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

    /**
     *
     * @return
     */
    public ComLuProduct getProductId() {
        return productId;
    }

    /**
     *
     * @param productId
     */
    public void setProductId(ComLuProduct productId) {
        this.productId = productId;
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
        if (!(object instanceof ScmDailyFob)) {
            return false;
        }
        ScmDailyFob other = (ScmDailyFob) object;
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
