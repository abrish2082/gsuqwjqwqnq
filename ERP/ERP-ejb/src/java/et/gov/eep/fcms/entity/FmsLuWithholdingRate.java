/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.entity;

import et.gov.eep.fcms.entity.Vocher.FmsChequePaymentVoucher;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author AB
 */
@Entity
@Table(name = "fms_lu_withholding_rate" )
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsLuWithholdingRate.findAll", query = "SELECT f FROM FmsLuWithholdingRate f"),
    @NamedQuery(name = "FmsLuWithholdingRate.findByLuWithholdingRateId", query = "SELECT f FROM FmsLuWithholdingRate f WHERE f.luWithholdingRateId = :luWithholdingRateId"),
    @NamedQuery(name = "FmsLuWithholdingRate.findByWithholdingRate", query = "SELECT f FROM FmsLuWithholdingRate f WHERE f.withholdingRate = :withholdingRate")})
public class FmsLuWithholdingRate implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FMS_LU_WITHHOLDING_RATE_LU_SEQ")
    @SequenceGenerator(name = "FMS_LU_WITHHOLDING_RATE_LU_SEQ", sequenceName = "FMS_LU_WITHHOLDING_RATE_LU_SEQ", allocationSize = 1)
    @Basic(optional = false)
    @Column(name = "LU_WITHHOLDING_RATE_ID", nullable = false)
    private Integer luWithholdingRateId;
    @Size(max = 30)
    @Column(name = "WITHHOLDING_RATE", length = 30)
    private String withholdingRate;
    @OneToMany(mappedBy = "luWithholdingRateId")
    private List<FmsChequePaymentVoucher> fmsChequePaymentVoucherList;
    @Column(name = "RATE")
    private Integer rate;   
     @Column(name = "RATE_CODE")
    private Integer rateCode;  

    /**
     *
     */
    public FmsLuWithholdingRate() {
    }

    /**
     *
     * @param luWithholdingRateId
     */
    public FmsLuWithholdingRate(Integer luWithholdingRateId) {
        this.luWithholdingRateId = luWithholdingRateId;
    }

    /**
     *
     * @return
     */
    public Integer getLuWithholdingRateId() {
        return luWithholdingRateId;
    }

    /**
     *
     * @param luWithholdingRateId
     */
    public void setLuWithholdingRateId(Integer luWithholdingRateId) {
        this.luWithholdingRateId = luWithholdingRateId;
    }

    /**
     *
     * @return
     */
    public String getWithholdingRate() {
        return withholdingRate;
    }

    /**
     *
     * @param withholdingRate
     */
    public void setWithholdingRate(String withholdingRate) {
        this.withholdingRate = withholdingRate;
    }

    /**
     *
     * @return
     */
    @XmlTransient
    public List<FmsChequePaymentVoucher> getFmsChequePaymentVoucherList() {
        return fmsChequePaymentVoucherList;
    }

    /**
     *
     * @param fmsChequePaymentVoucherList
     */
    public void setFmsChequePaymentVoucherList(List<FmsChequePaymentVoucher> fmsChequePaymentVoucherList) {
        this.fmsChequePaymentVoucherList = fmsChequePaymentVoucherList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (luWithholdingRateId != null ? luWithholdingRateId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FmsLuWithholdingRate)) {
            return false;
        }
        FmsLuWithholdingRate other = (FmsLuWithholdingRate) object;
        if ((this.luWithholdingRateId == null && other.luWithholdingRateId != null) || (this.luWithholdingRateId != null && !this.luWithholdingRateId.equals(other.luWithholdingRateId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return withholdingRate;
    }

    /**
     * @return the rate
     */
    public Integer getRate() {
        return rate;
    }

    /**
     * @param rate the rate to set
     */
    public void setRate(Integer rate) {
        this.rate = rate;
    }

    /**
     * @return the rateCode
     */
    public Integer getRateCode() {
        return rateCode;
    }

    /**
     * @param rateCode the rateCode to set
     */
    public void setRateCode(Integer rateCode) {
        this.rateCode = rateCode;
    }
    
}
