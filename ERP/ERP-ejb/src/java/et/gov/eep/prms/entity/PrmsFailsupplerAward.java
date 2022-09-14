/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.entity;

import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "PRMS_FAILSUPPLER_AWARD")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrmsFailsupplerAward.findAll", query = "SELECT p FROM PrmsFailsupplerAward p"),
    @NamedQuery(name = "PrmsFailsupplerAward.findByFailsuppAwardId", query = "SELECT p FROM PrmsFailsupplerAward p WHERE p.failsuppAwardId = :failsuppAwardId"),
    @NamedQuery(name = "PrmsFailsupplerAward.findByReasonForFail", query = "SELECT p FROM PrmsFailsupplerAward p WHERE p.reasonForFail = :reasonForFail")})
public class PrmsFailsupplerAward implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRMS_FAILEDSUPP_AWARD_ID_SEQ")
    @SequenceGenerator(name = "PRMS_FAILEDSUPP_AWARD_ID_SEQ", sequenceName = "PRMS_FAILEDSUPP_AWARD_ID_SEQ", allocationSize = 1)
    @Column(name = "FAILSUPP_AWARD_ID")
    private BigDecimal failsuppAwardId;
    @Size(max = 1000)
    @Column(name = "REASON_FOR_FAIL")
    private String reasonForFail;
    @Size(max = 100)
    @Column(name = "FIALED_AT")
    private String fialedAt;
   
    @Size(max = 100)
    @Column(name = "SUPPLIR_NAME")
    private String supplirName;

    @JoinColumn(name = "AWARD_ID", referencedColumnName = "AWARD_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrmsAward awardId;
    @JoinColumn(name = "SUPP_ID", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrmsSupplyProfile suppId;

    public PrmsFailsupplerAward() {
    }

    public PrmsFailsupplerAward(BigDecimal failsuppAwardId) {
        this.failsuppAwardId = failsuppAwardId;
    }

    public BigDecimal getFailsuppAwardId() {
        return failsuppAwardId;
    }

    public void setFailsuppAwardId(BigDecimal failsuppAwardId) {
        this.failsuppAwardId = failsuppAwardId;
    }

    public String getReasonForFail() {
        return reasonForFail;
    }

    public void setReasonForFail(String reasonForFail) {
        this.reasonForFail = reasonForFail;
    }

    public PrmsAward getAwardId() {
        return awardId;
    }

    public void setAwardId(PrmsAward awardId) {
        this.awardId = awardId;
    }

    public PrmsSupplyProfile getSuppId() {
        return suppId;
    }

    public void setSuppId(PrmsSupplyProfile suppId) {
        this.suppId = suppId;
    }

    public String getFialedAt() {
        return fialedAt;
    }

    public void setFialedAt(String fialedAt) {
        this.fialedAt = fialedAt;
    }

    public String getSupplirName() {
        return supplirName;
    }

    public void setSupplirName(String supplirName) {
        this.supplirName = supplirName;
    }

  
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (failsuppAwardId != null ? failsuppAwardId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PrmsFailsupplerAward)) {
            return false;
        }
        PrmsFailsupplerAward other = (PrmsFailsupplerAward) object;
        if ((this.failsuppAwardId == null && other.failsuppAwardId != null) || (this.failsuppAwardId != null && !this.failsuppAwardId.equals(other.failsuppAwardId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return failsuppAwardId.toString();
    }

}
