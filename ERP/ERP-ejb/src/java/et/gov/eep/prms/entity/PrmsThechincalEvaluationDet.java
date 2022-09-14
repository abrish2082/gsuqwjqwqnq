/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.entity;

import et.gov.eep.mms.entity.MmsItemRegistration;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author user
 */
@Entity
@Table(name = "PRMS_THECHINCAL_EVALUATION_DET")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrmsThechincalEvaluationDet.findAll", query = "SELECT p FROM PrmsThechincalEvaluationDet p"),
    @NamedQuery(name = "PrmsThechincalEvaluationDet.findByEvaluationDetailId", query = "SELECT p FROM PrmsThechincalEvaluationDet p WHERE p.evaluationDetailId = :evaluationDetailId"),
    @NamedQuery(name = "PrmsThechincalEvaluationDet.findBySupplierCode", query = "SELECT p FROM PrmsThechincalEvaluationDet p WHERE p.supplierCode = :supplierCode"),
//    @NamedQuery(name = "PrmsThechincalEvaluationDet.findByBidNum", query = "SELECT p FROM PrmsThechincalEvaluationDet p WHERE p.thechnicalId.bidDetId.bidId.refNo IS NOT NULL"),
//    @NamedQuery(name = "PrmsThechincalEvaluationDet.findByItemLotPack", query = "SELECT p FROM PrmsThechincalEvaluationDet p WHERE p.thechnicalId.itemRegistraionId IS NOT NULL"),
    @NamedQuery(name = "PrmsThechincalEvaluationDet.findByScore", query = "SELECT p FROM PrmsThechincalEvaluationDet p WHERE p.score = :score"),
    @NamedQuery(name = "PrmsThechincalEvaluationDet.findByRemark", query = "SELECT p FROM PrmsThechincalEvaluationDet p WHERE p.remark = :remark")})
public class PrmsThechincalEvaluationDet implements Serializable {

    @JoinColumn(name = "ITEM_REGISTRATION_ID", referencedColumnName = "MATERIAL_ID")
    @ManyToOne
    private MmsItemRegistration itemRegistrationId;

    @JoinColumn(name = "SERVICE_ID", referencedColumnName = "SERV_AND_WORK_ID")
    @ManyToOne
    private PrmsServiceAndWorkReg serviceId;

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRMS_TECHNICAL_EVALUN_DET_SEQ")
    @SequenceGenerator(name = "PRMS_TECHNICAL_EVALUN_DET_SEQ", sequenceName = "PRMS_TECHNICAL_EVALUN_DET_SEQ", allocationSize = 1)
    @Column(name = "EVALUATION_DETAIL_ID")
    private BigDecimal evaluationDetailId;
    @Size(max = 100)
    @Column(name = "SUPPLIER_CODE")
    private String supplierCode;
//    @Size(max = 100)
    @Column(name = "SCORE")
    private double score;
    @Size(max = 100)
    @Column(name = "REMARK")
    private String remark;
    @JoinColumn(name = "PRELIMINARY_DET_ID", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrmsPreminilaryEvalutionDt preliminaryDetId;
    @JoinColumn(name = "BID_DETAIL_ID", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrmsBidDetail bidDetailId;
    @JoinColumn(name = "QUOTATION_DETAIL_ID", referencedColumnName = "QUAT_DET_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrmsQuotationDetail quotationDetailId;
    @JoinColumn(name = "THECHNICAL_ID", referencedColumnName = "EVALUATION_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrmsThechnicalEvaluation thechnicalId;
    @JoinColumn(name = "SUPPLIER_ID", referencedColumnName = "ID")
    @ManyToOne
    private PrmsSupplyProfile supplierId;

    @Size(max = 100)
    @Column(name = "EVALUATION")
    private String evaluation;

    public PrmsThechincalEvaluationDet() {
    }

    public PrmsThechincalEvaluationDet(BigDecimal evaluationDetailId) {
        this.evaluationDetailId = evaluationDetailId;
    }

    public BigDecimal getEvaluationDetailId() {
        return evaluationDetailId;
    }

    public void setEvaluationDetailId(BigDecimal evaluationDetailId) {
        this.evaluationDetailId = evaluationDetailId;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public PrmsPreminilaryEvalutionDt getPreliminaryDetId() {
        return preliminaryDetId;
    }

    public void setPreliminaryDetId(PrmsPreminilaryEvalutionDt preliminaryDetId) {
        this.preliminaryDetId = preliminaryDetId;
    }

    public PrmsThechnicalEvaluation getThechnicalId() {
        return thechnicalId;
    }

    public void setThechnicalId(PrmsThechnicalEvaluation thechnicalId) {
        this.thechnicalId = thechnicalId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (evaluationDetailId != null ? evaluationDetailId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PrmsThechincalEvaluationDet)) {
            return false;
        }
        PrmsThechincalEvaluationDet other = (PrmsThechincalEvaluationDet) object;
        if ((this.evaluationDetailId == null && other.evaluationDetailId != null) || (this.evaluationDetailId != null && !this.evaluationDetailId.equals(other.evaluationDetailId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return evaluationDetailId + "";//.toString();
    }

    public MmsItemRegistration getItemRegistrationId() {
        return itemRegistrationId;
    }

    public void setItemRegistrationId(MmsItemRegistration itemRegistrationId) {
        this.itemRegistrationId = itemRegistrationId;
    }

    public PrmsServiceAndWorkReg getServiceId() {
        return serviceId;
    }

    public void setServiceId(PrmsServiceAndWorkReg serviceId) {
        this.serviceId = serviceId;
    }

    public PrmsBidDetail getBidDetailId() {
        return bidDetailId;
    }

    public void setBidDetailId(PrmsBidDetail bidDetailId) {
        this.bidDetailId = bidDetailId;
    }

    public PrmsQuotationDetail getQuotationDetailId() {
        return quotationDetailId;
    }

    public void setQuotationDetailId(PrmsQuotationDetail quotationDetailId) {
        this.quotationDetailId = quotationDetailId;
    }

    public PrmsSupplyProfile getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(PrmsSupplyProfile supplierId) {
        this.supplierId = supplierId;
    }

    public String getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(String evaluation) {
        this.evaluation = evaluation;
    }

}
