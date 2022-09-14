/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.entity.Bond;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import et.gov.eep.fcms.entity.FmsLuCurrency;

/**
 *
 * @author mora
 */
@Entity
@Table(name = "FMS_BOND_LIBOR")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsBondLibor.findAll", query = "SELECT f FROM FmsBondLibor f"),
    @NamedQuery(name = "FmsBondLibor.findByLiborNo", query = "SELECT f FROM FmsBondLibor f WHERE f.liborNo = :liborNo"),
    @NamedQuery(name = "FmsBondLibor.findByStartDate", query = "SELECT f FROM FmsBondLibor f WHERE f.startDate = :startDate"),
    @NamedQuery(name = "FmsBondLibor.findByEndDate", query = "SELECT f FROM FmsBondLibor f WHERE f.endDate = :endDate"),
    @NamedQuery(name = "FmsBondLibor.findByEndDateAndStart", query = "SELECT f FROM FmsBondLibor f WHERE (:day between f.startDate and f.endDate)  and  f.fmsLuCurrency = :BondCurrency"),
    @NamedQuery(name = "FmsBondLibor.findByEndDateAndStartDate", query = "SELECT f FROM FmsBondLibor f WHERE (:day between f.startDate and f.endDate) "),
    @NamedQuery(name = "FmsBondLibor.findLibor", query = "SELECT f FROM FmsBondLibor f WHERE (:day between f.startDate and f.endDate) or f.fmsLuCurrency = :BondCurrency "),
    @NamedQuery(name = "FmsBondLibor.findByCurrency", query = "SELECT f FROM FmsBondLibor f WHERE f.fmsLuCurrency = :BondCurrency"),
    @NamedQuery(name = "FmsBondLibor.findByLiborRate", query = "SELECT f FROM FmsBondLibor f WHERE f.liborRate = :liborRate")})
public class FmsBondLibor implements Serializable {

    private static final long serialVersionUID = 1L;

    public FmsBondLibor() {
    }

    //<editor-fold defaultstate="collapsed" desc="variable declaration">
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "LIBOR_NO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FMS_BOND_LIBOR_SEQ")
    @SequenceGenerator(name = "FMS_BOND_LIBOR_SEQ", sequenceName = "FMS_BOND_LIBOR_SEQ", allocationSize = 1)
    private Integer liborNo;
    @Column(name = "START_DATE")
    @Temporal(TemporalType.DATE)
    private Date startDate;
    @Column(name = "END_DATE")
    @Temporal(TemporalType.DATE)
    private Date endDate;
    @Column(name = "LIBOR_RATE")
    private Double liborRate;
    @JoinColumn(name = "CURRENCY", referencedColumnName = "CURRENCY_ID")
    @ManyToOne
    private FmsLuCurrency fmsLuCurrency;
    @Transient
    Date day;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="variable declaration">
    public FmsBondLibor(Integer liborNo) {
        this.liborNo = liborNo;
    }

    public Integer getLiborNo() {
        return liborNo;
    }

    public void setLiborNo(Integer liborNo) {
        this.liborNo = liborNo;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public FmsLuCurrency getFmsLuCurrency() {
        return fmsLuCurrency;
    }

    public void setFmsLuCurrency(FmsLuCurrency fmsLuCurrency) {
        this.fmsLuCurrency = fmsLuCurrency;
    }

    public Double getLiborRate() {
        return liborRate;
    }

    public void setLiborRate(Double liborRate) {
        this.liborRate = liborRate;
    }

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }
//</editor-fold>

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (liborNo != null ? liborNo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof FmsBondLibor)) {
            return false;
        }
        FmsBondLibor other = (FmsBondLibor) object;
        if ((this.liborNo == null && other.liborNo != null) || (this.liborNo != null && !this.liborNo.equals(other.liborNo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return liborNo + "";
    }

}
