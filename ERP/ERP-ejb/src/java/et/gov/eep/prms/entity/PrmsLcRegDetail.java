/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
@Table(name = "PRMS_LC_REG_DETAIL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrmsLcRegDetail.findAll", query = "SELECT p FROM PrmsLcRegDetail p"),
    @NamedQuery(name = "PrmsLcRegDetail.findByDetId", query = "SELECT p FROM PrmsLcRegDetail p WHERE p.detId = :detId"),
    @NamedQuery(name = "PrmsLcRegDetail.findByLcAmount", query = "SELECT p FROM PrmsLcRegDetail p WHERE p.lcAmount = :lcAmount"),
    @NamedQuery(name = "PrmsLcRegDetail.findByDeliveredAmount", query = "SELECT p FROM PrmsLcRegDetail p WHERE p.deliveredAmount = :deliveredAmount"),
    @NamedQuery(name = "PrmsLcRegDetail.findByUndeliveredAmount", query = "SELECT p FROM PrmsLcRegDetail p WHERE p.undeliveredAmount = :undeliveredAmount"),
    @NamedQuery(name = "PrmsLcRegDetail.findByDeliveredGrnAmount", query = "SELECT p FROM PrmsLcRegDetail p WHERE p.deliveredGrnAmount = :deliveredGrnAmount"),
    @NamedQuery(name = "PrmsLcRegDetail.findByUndeliveredGrnAmount", query = "SELECT p FROM PrmsLcRegDetail p WHERE p.undeliveredGrnAmount = :undeliveredGrnAmount"),
    @NamedQuery(name = "PrmsLcRegDetail.findByCurrency", query = "SELECT p FROM PrmsLcRegDetail p WHERE p.currency = :currency"),
    @NamedQuery(name = "PrmsLcRegDetail.findByServiceCharege", query = "SELECT p FROM PrmsLcRegDetail p WHERE p.serviceCharege = :serviceCharege"),
    @NamedQuery(name = "PrmsLcRegDetail.findByBankAdviceNo", query = "SELECT p FROM PrmsLcRegDetail p WHERE p.bankAdviceNo = :bankAdviceNo")})
public class PrmsLcRegDetail implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRMS_LC_REGISTDETAIL_SEQ")
    @SequenceGenerator(name = "PRMS_LC_REGISTDETAIL_SEQ", sequenceName = "PRMS_LC_REGISTDETAIL_SEQ", allocationSize = 1)
    @Basic(optional = false)

    @Column(name = "DET_ID")
    private Integer detId;
    @Column(name = "LC_AMOUNT")
    private Integer lcAmount;
    @Column(name = "DELIVERED_AMOUNT")
    private Integer deliveredAmount;
    @Column(name = "UNDELIVERED_AMOUNT")
    private Integer undeliveredAmount;
    @Column(name = "DELIVERED_GRN_AMOUNT")
    private Integer deliveredGrnAmount;
    @Column(name = "UNDELIVERED_GRN_AMOUNT")
    private Integer undeliveredGrnAmount;
    
    @Column(name = "CURRENCY")
    private String currency;
    
    @Column(name = "SERVICE_CHAREGE")
    private String serviceCharege;
    
    @Column(name = "BANK_ADVICE_NO")
    private String bankAdviceNo;
    @JoinColumn(name = "LC_ID", referencedColumnName = "LC_ID")
    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    private PrmsLcRigistration lcId;

    /**
     *
     */
    public PrmsLcRegDetail() {
    }

    /**
     *
     * @param detId
     */
    public PrmsLcRegDetail(Integer detId) {
        this.detId = detId;
    }

    /**
     *
     * @return
     */
    public Integer getDetId() {
        return detId;
    }

    /**
     *
     * @param detId
     */
    public void setDetId(Integer detId) {
        this.detId = detId;
    }

    /**
     *
     * @return
     */
    public Integer getLcAmount() {
        return lcAmount;
    }

    /**
     *
     * @param lcAmount
     */
    public void setLcAmount(Integer lcAmount) {
        this.lcAmount = lcAmount;
    }

    /**
     *
     * @return
     */
    public Integer getDeliveredAmount() {
        return deliveredAmount;
    }

    /**
     *
     * @param deliveredAmount
     */
    public void setDeliveredAmount(Integer deliveredAmount) {
        this.deliveredAmount = deliveredAmount;
    }

    /**
     *
     * @return
     */
    public Integer getUndeliveredAmount() {
        return undeliveredAmount;
    }

    /**
     *
     * @param undeliveredAmount
     */
    public void setUndeliveredAmount(Integer undeliveredAmount) {
        this.undeliveredAmount = undeliveredAmount;
    }

    /**
     *
     * @return
     */
    public Integer getDeliveredGrnAmount() {
        return deliveredGrnAmount;
    }

    /**
     *
     * @param deliveredGrnAmount
     */
    public void setDeliveredGrnAmount(Integer deliveredGrnAmount) {
        this.deliveredGrnAmount = deliveredGrnAmount;
    }

    /**
     *
     * @return
     */
    public Integer getUndeliveredGrnAmount() {
        return undeliveredGrnAmount;
    }

    /**
     *
     * @param undeliveredGrnAmount
     */
    public void setUndeliveredGrnAmount(Integer undeliveredGrnAmount) {
        this.undeliveredGrnAmount = undeliveredGrnAmount;
    }

    /**
     *
     * @return
     */
    public String getCurrency() {
        return currency;
    }

    /**
     *
     * @param currency
     */
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    /**
     *
     * @return
     */
    public String getServiceCharege() {
        return serviceCharege;
    }

    /**
     *
     * @param serviceCharege
     */
    public void setServiceCharege(String serviceCharege) {
        this.serviceCharege = serviceCharege;
    }

    /**
     *
     * @return
     */
    public String getBankAdviceNo() {
        return bankAdviceNo;
    }

    /**
     *
     * @param bankAdviceNo
     */
    public void setBankAdviceNo(String bankAdviceNo) {
        this.bankAdviceNo = bankAdviceNo;
    }

    /**
     *
     * @return
     */
    public PrmsLcRigistration getLcId() {
        return lcId;
    }

    /**
     *
     * @param lcId
     */
    
    public void setLcId(PrmsLcRigistration lcId) {
        this.lcId = lcId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (detId != null ? detId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PrmsLcRegDetail)) {
            return false;
        }
        PrmsLcRegDetail other = (PrmsLcRegDetail) object;
        if ((this.detId == null && other.detId != null) || (this.detId != null && !this.detId.equals(other.detId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.prms.entity.PrmsLcRegDetail[ detId=" + detId + " ]";
    }

}
