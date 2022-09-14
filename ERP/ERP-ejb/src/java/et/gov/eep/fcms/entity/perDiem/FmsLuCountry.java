/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.entity.perDiem;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
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
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import et.gov.eep.commonApplications.entity.ComLuCountry;
import et.gov.eep.hrms.entity.address.HrAddresses;

/**
 *
 * @author muller
 */
@Entity
@Table(name = "FMS_LU_COUNTRY")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsLuCountry.findByCountryNameLike", query = "SELECT f FROM FmsLuCountry f INNER JOIN F.comLuCountryId comLuCountry WHERE comLuCountry.country LIKE :country"),
    @NamedQuery(name = "FmsLuCountry.findAll", query = "SELECT f FROM FmsLuCountry f"),
    @NamedQuery(name = "FmsLuCountry.findByCountryId", query = "SELECT f FROM FmsLuCountry f WHERE f.countryId = :countryId"),
    @NamedQuery(name = "FmsLuCountry.findByCountryName", query = "SELECT f FROM FmsLuCountry f WHERE f.countryName = :countryName"),
    @NamedQuery(name = "FmsLuCountry.findByCountry", query = "SELECT f FROM FmsLuCountry f WHERE f.countryName = :countryName"),
    @NamedQuery(name = "FmsLuCountry.findByLodgingBreakfastAmount", query = "SELECT f FROM FmsLuCountry f WHERE f.lodgingBreakfastAmount = :lodgingBreakfastAmount"),
    @NamedQuery(name = "FmsLuCountry.findByLunchDinnerAmount", query = "SELECT f FROM FmsLuCountry f WHERE f.lunchDinnerAmount = :lunchDinnerAmount"),
    @NamedQuery(name = "FmsLuCountry.findByComLuCountryId", query = "SELECT f FROM FmsLuCountry f WHERE f.comLuCountryId = :comLuCountryId"),
    @NamedQuery(name = "FmsLuCountry.findByAllParameters", query = "SELECT f FROM FmsLuCountry f WHERE f.countryName LIKE :countryName")})
public class FmsLuCountry implements Serializable {

    private static final long serialVersionUID = 1L;
    //<editor-fold defaultstate="collapsed" desc="variable declaration">
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FMS_COUNTRY_SEQ")
    @SequenceGenerator(name = "FMS_COUNTRY_SEQ", sequenceName = "FMS_COUNTRY_SEQ", allocationSize = 1)
    @Column(name = "COUNTRY_ID")
    private BigDecimal countryId;
    @Size(max = 50)
    @Column(name = "COUNTRY_NAME")
    private String countryName;
    @Column(name = "LODGING_BREAKFAST_AMOUNT")
    private Double lodgingBreakfastAmount;
    @Column(name = "LUNCH_DINNER_AMOUNT")
    private Double lunchDinnerAmount;
    @JoinColumn(name = "ADDRESS_ID", referencedColumnName = "ADDRESS_ID")
    @ManyToOne
    private HrAddresses addressId;
    @JoinColumn(name = "COM_LU_COUNTRY_ID", referencedColumnName = "ID")
    @ManyToOne
    private ComLuCountry comLuCountryId;
    @Transient
    List<FmsLuCountry> countryList = new ArrayList<>();
//</editor-fold>

    public FmsLuCountry() {
    }
//<editor-fold defaultstate="collapsed" desc="getter and setter">

    public FmsLuCountry(BigDecimal countryId) {
        this.countryId = countryId;
    }

    public BigDecimal getCountryId() {
        return countryId;
    }

    public void setCountryId(BigDecimal countryId) {
        this.countryId = countryId;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public Double getLodgingBreakfastAmount() {
        return lodgingBreakfastAmount;
    }

    public void setLodgingBreakfastAmount(Double lodgingBreakfastAmount) {
        this.lodgingBreakfastAmount = lodgingBreakfastAmount;
    }

    public Double getLunchDinnerAmount() {
        return lunchDinnerAmount;
    }

    public void setLunchDinnerAmount(Double lunchDinnerAmount) {
        this.lunchDinnerAmount = lunchDinnerAmount;
    }

    public void setCountryList(List<FmsLuCountry> countryList) {
        this.countryList = countryList;
    }

    public HrAddresses getAddressId() {
        return addressId;
    }

    public void setAddressId(HrAddresses addressId) {
        this.addressId = addressId;
    }

    public ComLuCountry getComLuCountryId() {
        return comLuCountryId;
    }

    public void setComLuCountryId(ComLuCountry comLuCountryId) {
        this.comLuCountryId = comLuCountryId;
    }
//</editor-fold>

    @XmlTransient
    public List<FmsLuCountry> getCountryList() {

        if (countryList == null) {
            countryList = new ArrayList<>();
        }
        return countryList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (countryId != null ? countryId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof FmsLuCountry)) {
            return false;
        }
        FmsLuCountry other = (FmsLuCountry) object;
        if ((this.countryId == null && other.countryId != null) || (this.countryId != null && !this.countryId.equals(other.countryId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return countryName;
    }

    public void addToFmsLuCountry(FmsLuCountry countryDetail) {
        this.getCountryList().add(countryDetail);

    }

}
