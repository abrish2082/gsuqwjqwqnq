/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.entity;

import java.io.Serializable;
import java.util.List;
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
@Table(name = "PRMS_PREMINILARY_EVALUTION_DT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrmsPreminilaryEvalutionDt.findAll", query = "SELECT p FROM PrmsPreminilaryEvalutionDt p"),
    @NamedQuery(name = "PrmsPreminilaryEvalutionDt.findById", query = "SELECT p FROM PrmsPreminilaryEvalutionDt p WHERE p.id = :id"),
    @NamedQuery(name = "PrmsPreminilaryEvalutionDt.findByBidNo", query = "SELECT p FROM PrmsPreminilaryEvalutionDt p WHERE p.preminaryId.bidId.refNo = :refNo"),
    @NamedQuery(name = "PrmsPreminilaryEvalutionDt.findByRemark", query = "SELECT p FROM PrmsPreminilaryEvalutionDt p WHERE p.remark = :remark")})
public class PrmsPreminilaryEvalutionDt implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "preminaryEvaId", fetch = FetchType.LAZY)
    private List<PrmsSuppSpecification> prmsSuppSpecificationList;
    @OneToMany(mappedBy = "preliminaryDetId")
    private List<PrmsThechincalEvaluationDet> prmsThechincalEvaluationDetList;

    @Size(max = 20)
    @Column(name = "CRITERIA_RESULT")
    private String criteriaResult;
    @Size(max = 500)
    @Column(name = "BID_CRITERIA")
    private String bidCriteria;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "ID", nullable = false, length = 20)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRMS_PRELIMINRY_EVAL_DTL_SEQ")
    @SequenceGenerator(name = "PRMS_PRELIMINRY_EVAL_DTL_SEQ", sequenceName = "PRMS_PRELIMINRY_EVAL_DTL_SEQ", allocationSize = 1)
    private String id;

    @Size(max = 100)
    @Column(name = "REMARK", length = 100)
    private String remark;
    @Size(max = 40)
    @Column(name = "SUPPLIER_CODE", length = 40)
    private String supplierCode;

    @JoinColumn(name = "PREMINARY_ID", referencedColumnName = "ID")
    @ManyToOne
    private PrmsPreminilaryEvaluation preminaryId;
    @JoinColumn(name = "SUPPLIER_ID", referencedColumnName = "ID")
    @ManyToOne
    private PrmsSupplyProfile supplierId;

    public PrmsPreminilaryEvalutionDt() {
    }

    public PrmsPreminilaryEvalutionDt(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public PrmsPreminilaryEvaluation getPreminaryId() {
        return preminaryId;
    }

    public void setPreminaryId(PrmsPreminilaryEvaluation preminaryId) {
        this.preminaryId = preminaryId;
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
        if (!(object instanceof PrmsPreminilaryEvalutionDt)) {
            return false;
        }
        PrmsPreminilaryEvalutionDt other = (PrmsPreminilaryEvalutionDt) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return supplierId.getVendorName();
    }

    public String getCriteriaResult() {
        return criteriaResult;
    }

    public void setCriteriaResult(String criteriaResult) {
        this.criteriaResult = criteriaResult;
    }

    public String getBidCriteria() {
        return bidCriteria;
    }

    public void setBidCriteria(String bidCriteria) {
        this.bidCriteria = bidCriteria;
    }

    @XmlTransient
    public List<PrmsThechincalEvaluationDet> getPrmsThechincalEvaluationDetList() {
        return prmsThechincalEvaluationDetList;
    }

    public void setPrmsThechincalEvaluationDetList(List<PrmsThechincalEvaluationDet> prmsThechincalEvaluationDetList) {
        this.prmsThechincalEvaluationDetList = prmsThechincalEvaluationDetList;
    }

    public PrmsSupplyProfile getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(PrmsSupplyProfile supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public List<PrmsSuppSpecification> getPrmsSuppSpecificationList() {
        return prmsSuppSpecificationList;
    }

    public void setPrmsSuppSpecificationList(List<PrmsSuppSpecification> prmsSuppSpecificationList) {
        this.prmsSuppSpecificationList = prmsSuppSpecificationList;
    }

}
