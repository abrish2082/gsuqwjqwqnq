/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.entity;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;





@Entity
@Table(name = "PRMS_LC_REG_DETAIL_AMENDMENT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrmsLcRegDetailAmendment.findAll", query = "SELECT p FROM PrmsLcRegDetailAmendment p"),
    @NamedQuery(name = "PrmsLcRegDetailAmendment.findById", query = "SELECT p FROM PrmsLcRegDetailAmendment p WHERE p.id = :id"),
    @NamedQuery(name = "PrmsLcRegDetailAmendment.findByLcAmount", query = "SELECT p FROM PrmsLcRegDetailAmendment p WHERE p.lcAmount = :lcAmount"),
    @NamedQuery(name = "PrmsLcRegDetailAmendment.findByDeliveredAmount", query = "SELECT p FROM PrmsLcRegDetailAmendment p WHERE p.deliveredAmount = :deliveredAmount"),
    @NamedQuery(name = "PrmsLcRegDetailAmendment.findByUndeliveredAmount", query = "SELECT p FROM PrmsLcRegDetailAmendment p WHERE p.undeliveredAmount = :undeliveredAmount"),
    @NamedQuery(name = "PrmsLcRegDetailAmendment.findByDeliveredGrnAmount", query = "SELECT p FROM PrmsLcRegDetailAmendment p WHERE p.deliveredGrnAmount = :deliveredGrnAmount"),
    @NamedQuery(name = "PrmsLcRegDetailAmendment.findByUndeliveredGrnAmount", query = "SELECT p FROM PrmsLcRegDetailAmendment p WHERE p.undeliveredGrnAmount = :undeliveredGrnAmount"),
    @NamedQuery(name = "PrmsLcRegDetailAmendment.findByCurrency", query = "SELECT p FROM PrmsLcRegDetailAmendment p WHERE p.currency = :currency"),
    @NamedQuery(name = "PrmsLcRegDetailAmendment.findByServiceCharege", query = "SELECT p FROM PrmsLcRegDetailAmendment p WHERE p.serviceCharege = :serviceCharege"),
    @NamedQuery(name = "PrmsLcRegDetailAmendment.findByLcId", query = "SELECT p FROM PrmsLcRegDetailAmendment p WHERE p.lcId = :lcId"),
    @NamedQuery(name = "PrmsLcRegDetailAmendment.findByBankAdviceNo", query = "SELECT p FROM PrmsLcRegDetailAmendment p WHERE p.bankAdviceNo = :bankAdviceNo")})
public class PrmsLcRegDetailAmendment implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "ID", nullable = false, length = 20)
    private Integer id;
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
    @Size(max = 20)
    @Column(name = "CURRENCY", length = 20)
    private String currency;
    @Size(max = 20)
    @Column(name = "SERVICE_CHAREGE", length = 20)
    private String serviceCharege;
    @Column(name = "LC_ID")
    private BigInteger lcId;
    @Size(max = 20)
    @Column(name = "BANK_ADVICE_NO", length = 20)
    private String bankAdviceNo;

    public PrmsLcRegDetailAmendment() {
    }

    public PrmsLcRegDetailAmendment(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLcAmount() {
        return lcAmount;
    }

    public void setLcAmount(Integer lcAmount) {
        this.lcAmount = lcAmount;
    }

    public Integer getDeliveredAmount() {
        return deliveredAmount;
    }

    public void setDeliveredAmount(Integer deliveredAmount) {
        this.deliveredAmount = deliveredAmount;
    }

    public Integer getUndeliveredAmount() {
        return undeliveredAmount;
    }

    public void setUndeliveredAmount(Integer undeliveredAmount) {
        this.undeliveredAmount = undeliveredAmount;
    }

    public Integer getDeliveredGrnAmount() {
        return deliveredGrnAmount;
    }

    public void setDeliveredGrnAmount(Integer deliveredGrnAmount) {
        this.deliveredGrnAmount = deliveredGrnAmount;
    }

    public Integer getUndeliveredGrnAmount() {
        return undeliveredGrnAmount;
    }

    public void setUndeliveredGrnAmount(Integer undeliveredGrnAmount) {
        this.undeliveredGrnAmount = undeliveredGrnAmount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getServiceCharege() {
        return serviceCharege;
    }

    public void setServiceCharege(String serviceCharege) {
        this.serviceCharege = serviceCharege;
    }

    public BigInteger getLcId() {
        return lcId;
    }

    public void setLcId(BigInteger lcId) {
        this.lcId = lcId;
    }

    public String getBankAdviceNo() {
        return bankAdviceNo;
    }

    public void setBankAdviceNo(String bankAdviceNo) {
        this.bankAdviceNo = bankAdviceNo;
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
        if (!(object instanceof PrmsLcRegDetailAmendment)) {
            return false;
        }
        PrmsLcRegDetailAmendment other = (PrmsLcRegDetailAmendment) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.prms.entity.PrmsLcRegDetailAmendment[ id=" + id + " ]";
    }

}
