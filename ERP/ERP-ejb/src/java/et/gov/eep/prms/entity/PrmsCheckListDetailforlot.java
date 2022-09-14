/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.entity;

import et.gov.eep.fcms.entity.FmsLuCurrency;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author user
 */
@Entity
@Table(name = "PRMS_CHECK_LIST_DETAILFORLOT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrmsCheckListDetailforlot.findAll", query = "SELECT p FROM PrmsCheckListDetailforlot p"),
    @NamedQuery(name = "PrmsCheckListDetailforlot.findByCheckListDetLid", query = "SELECT p FROM PrmsCheckListDetailforlot p WHERE p.checkListDetLid = :checkListDetLid"),
    @NamedQuery(name = "PrmsCheckListDetailforlot.findByLotNumber", query = "SELECT p FROM PrmsCheckListDetailforlot p WHERE p.lotNumber = :lotNumber"),
    @NamedQuery(name = "PrmsCheckListDetailforlot.findByPrice", query = "SELECT p FROM PrmsCheckListDetailforlot p WHERE p.price = :price"),
    @NamedQuery(name = "PrmsCheckListDetailforlot.findByMaxCheckListNum", query = "SELECT p FROM PrmsCheckListDetailforlot p WHERE p.checkListDetLid = (SELECT Max(c.checkListDetLid)  from PrmsCheckListDetailforlot c)"),

    @NamedQuery(name = "PrmsCheckListDetailforlot.findByCurrency", query = "SELECT p FROM PrmsCheckListDetailforlot p WHERE p.currency = :currency")})
public class PrmsCheckListDetailforlot implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRMS_BID_OPENING_LOT_SEQ")
    @SequenceGenerator(name = "PRMS_BID_OPENING_LOT_SEQ", sequenceName = "PRMS_BID_OPENING_LOT_SEQ", allocationSize = 1)
    @Column(name = "CHECK_LIST_DET_LID")
    private BigDecimal checkListDetLid;
    @Size(max = 50)
    @Column(name = "LOT_NUMBER")
    private String lotNumber;
    @Column(name = "PRICE")
    private BigInteger price;
    @Size(max = 50)
    @Column(name = "CURRENCY")
    private String currency;
    @JoinColumn(name = "CHECK_LIST_ID", referencedColumnName = "BID_CHECK_DT_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrmsBidOpeningChecklstDt checkListId;
    @JoinColumn(name = "SUP_ID", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrmsSupplyProfile supId;
    @JoinColumn(name = "CURRENCY_ID", referencedColumnName = "CURRENCY_ID")
    @ManyToOne
    private FmsLuCurrency currencyId;
    @JoinColumn(name = "BID_DIT_ID", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrmsBidDetail bidDitId;

    public PrmsCheckListDetailforlot() {
    }

    public PrmsCheckListDetailforlot(BigDecimal checkListDetLid) {
        this.checkListDetLid = checkListDetLid;
    }

    public BigDecimal getCheckListDetLid() {
        return checkListDetLid;
    }

    public void setCheckListDetLid(BigDecimal checkListDetLid) {
        this.checkListDetLid = checkListDetLid;
    }

    public String getLotNumber() {
        return lotNumber;
    }

    public void setLotNumber(String lotNumber) {
        this.lotNumber = lotNumber;
    }

    public BigInteger getPrice() {
        return price;
    }

    public void setPrice(BigInteger price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public PrmsBidOpeningChecklstDt getCheckListId() {
        return checkListId;
    }

    public void setCheckListId(PrmsBidOpeningChecklstDt checkListId) {
        this.checkListId = checkListId;
    }

    public PrmsSupplyProfile getSupId() {
        return supId;
    }

    public void setSupId(PrmsSupplyProfile supId) {
        this.supId = supId;
    }

    public FmsLuCurrency getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(FmsLuCurrency currencyId) {
        this.currencyId = currencyId;
    }

    public PrmsBidDetail getBidDitId() {
        return bidDitId;
    }

    public void setBidDitId(PrmsBidDetail bidDitId) {
        this.bidDitId = bidDitId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (checkListDetLid != null ? checkListDetLid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PrmsCheckListDetailforlot)) {
            return false;
        }
        PrmsCheckListDetailforlot other = (PrmsCheckListDetailforlot) object;
        if ((this.checkListDetLid == null && other.checkListDetLid != null) || (this.checkListDetLid != null && !this.checkListDetLid.equals(other.checkListDetLid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.prms.entity.PrmsCheckListDetailforlot[ checkListDetLid=" + checkListDetLid + " ]";
    }

}
