/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.entity;

import et.gov.eep.mms.entity.MmsItemRegistration;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author user
 */
@Entity
@Table(name = "PRMS_POST_DETAIL", catalog = "", schema = "ERP")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrmsPostDetail.findAll", query = "SELECT p FROM PrmsPostDetail p"),
    @NamedQuery(name = "PrmsPostDetail.findByPostdId", query = "SELECT p FROM PrmsPostDetail p WHERE p.postdId = :postdId"),
//    @NamedQuery(name = "PrmsPostDetail.findByBidderName", query = "SELECT p FROM PrmsPostDetail p WHERE p.bidderName = :bidderName"),
    @NamedQuery(name = "PrmsPostDetail.findByTechnicalCapacity", query = "SELECT p FROM PrmsPostDetail p WHERE p.technicalCapacity = :technicalCapacity"),
    @NamedQuery(name = "PrmsPostDetail.findByFinancialCapacity", query = "SELECT p FROM PrmsPostDetail p WHERE p.financialCapacity = :financialCapacity"),
    @NamedQuery(name = "PrmsPostDetail.findByResult", query = "SELECT p FROM PrmsPostDetail p WHERE p.results = :results"),
    @NamedQuery(name = "PrmsPostDetail.findByFinalDecision", query = "SELECT p FROM PrmsPostDetail p WHERE p.finalDecision = :finalDecision"),
    @NamedQuery(name = "PrmsPostDetail.findByRemark", query = "SELECT p FROM PrmsPostDetail p WHERE p.remark = :remark")})
public class PrmsPostDetail implements Serializable {

//    @Column(name = "POST_ID")
//    private Long postId;
    
    @JoinColumn(name = "MATERIAL_ID", referencedColumnName = "MATERIAL_ID")
    @ManyToOne
    private MmsItemRegistration materialId;
    
    @JoinColumn(name = "BIDDER_ID", referencedColumnName = "ID")
    @ManyToOne
    private PrmsSupplyProfile bidderId;
    
   
//    @Column(name = "POST_ID")
//    private Long postId;

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRMS_POST_DETAIL_SEQ")
    @SequenceGenerator(name = "PRMS_POST_DETAIL_SEQ", sequenceName = "PRMS_POST_DETAIL_SEQ", allocationSize = 1)
    @Column(name = "POSTD_ID", nullable = false, precision = 0, scale = -127)
    private BigDecimal postdId;

    @Size(max = 20)
    @Column(name = "TECHNICAL_CAPACITY", length = 20)
    private String technicalCapacity;
    @Size(max = 20)
    @Column(name = "FINANCIAL_CAPACITY", length = 20)
    private String financialCapacity;
    @Size(max = 20)
    @Column(name = "RESULTS", length = 20)
    private String results;
    @Size(max = 20)
    @Column(name = "LOTNAME", length = 20)
    private String lotName;
    @Size(max = 20)
    @Column(name = "FINAL_DECISION", length = 20)
    private String finalDecision;
    @Size(max = 500)
    @Column(name = "REMARK", length = 500)
    private String remark;

    @JoinColumn(name = "POST_ID", referencedColumnName = "POST_ID")
    @ManyToOne
    private PrmsPostQualification postId;

    public PrmsPostDetail() {
    }

    public PrmsPostDetail(BigDecimal postdId) {
        this.postdId = postdId;
    }

    public BigDecimal getPostdId() {
        return postdId;
    }

    public void setPostdId(BigDecimal postdId) {
        this.postdId = postdId;
    }

    public String getTechnicalCapacity() {
        return technicalCapacity;
    }

    public void setTechnicalCapacity(String technicalCapacity) {
        this.technicalCapacity = technicalCapacity;
    }

    public String getFinancialCapacity() {
        return financialCapacity;
    }

    public void setFinancialCapacity(String financialCapacity) {
        this.financialCapacity = financialCapacity;
    }

    public String getFinalDecision() {
        return finalDecision;
    }

    public void setFinalDecision(String finalDecision) {
        this.finalDecision = finalDecision;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getResults() {
        return results;
    }

    public void setResults(String results) {
        this.results = results;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (postdId != null ? postdId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PrmsPostDetail)) {
            return false;
        }
        PrmsPostDetail other = (PrmsPostDetail) object;
        if ((this.postdId == null && other.postdId != null) || (this.postdId != null && !this.postdId.equals(other.postdId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.prms.entity.PrmsPostDetail[ postdId=" + postdId + " ]";
    }

    /**
     * @return the postId
     */
    public PrmsPostQualification getPostId() {
        return postId;
    }

    /**
     * @param postId the postId to set
     */
    public void setPostId(PrmsPostQualification postId) {
        this.postId = postId;
    }

    public String getLotName() {
        return lotName;
    }

    public void setLotName(String lotName) {
        this.lotName = lotName;
    }

    public PrmsSupplyProfile getBidderId() {
        return bidderId;
    }

    public void setBidderId(PrmsSupplyProfile bidderId) {
        this.bidderId = bidderId;
    }

   

    

    

    /**
     * @return the materialId
     */
    public MmsItemRegistration getMaterialId() {
        return materialId;
    }

    /**
     * @param materialId the materialId to set
     */
    public void setMaterialId(MmsItemRegistration materialId) {
        this.materialId = materialId;
    }

}
