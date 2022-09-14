/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.entity;

import et.gov.eep.commonApplications.entity.PrmsLuDmArchive;
import et.gov.eep.commonApplications.entity.WfPrmsProcessed;
import et.gov.eep.mms.entity.MmsItemRegistration;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author user
 */
@Entity
@Table(name = "PRMS_THECHNICAL_EVALUATION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrmsThechnicalEvaluation.findAll", query = "SELECT p FROM PrmsThechnicalEvaluation p"),
    @NamedQuery(name = "PrmsThechnicalEvaluation.findByEvaluationId", query = "SELECT p FROM PrmsThechnicalEvaluation p WHERE p.evaluationId = :evaluationId"),
//    @NamedQuery(name = "PrmsThechnicalEvaluation.findByLotItemPackage", query = "SELECT p FROM PrmsThechnicalEvaluation p WHERE p.bidDetId.bidId IS NOT NULL"),
//    @NamedQuery(name = "PrmsThechnicalEvaluation.findByProforma", query = "SELECT p FROM PrmsThechnicalEvaluation p WHERE p.quotationDetailId.quotationId.quotationNo IS NOT NULL"),
    @NamedQuery(name = "PrmsThechnicalEvaluation.findBySupplierCode", query = "SELECT p FROM PrmsThechnicalEvaluation p WHERE p.preparedBy=:supplierName"),
    @NamedQuery(name = "PrmsThechnicalEvaluation.findByDateRigistered", query = "SELECT p FROM PrmsThechnicalEvaluation p WHERE p.dateRigistered = :dateRigistered"),
    @NamedQuery(name = "PrmsThechnicalEvaluation.findByApprovedDate", query = "SELECT p FROM PrmsThechnicalEvaluation p WHERE p.approvedDate = :approvedDate"),
//    @NamedQuery(name = "PrmsThechnicalEvaluation.findByItemName", query = "SELECT p FROM PrmsThechnicalEvaluation p WHERE p.itemRegistraionId.matName = :itemName"),
    @NamedQuery(name = "PrmsThechnicalEvaluation.findByEvaluationNo", query = "SELECT p FROM PrmsThechnicalEvaluation p WHERE p.evaluationNo = :evaluationNo"),
    @NamedQuery(name = "PrmsThechnicalEvaluation.searchByEvaluationNo", query = "SELECT p FROM PrmsThechnicalEvaluation p WHERE p.evaluationNo LIKE :evaluationNo"),
    @NamedQuery(name = "PrmsThechnicalEvaluation.findByPrNumber", query = "SELECT p FROM PrmsThechnicalEvaluation p WHERE p.evaluationNo LIKE :evaluationNo and p.preparedBy=:preparedBy"),
    @NamedQuery(name = "PrmsThechnicalEvaluation.findByApprovedBy", query = "SELECT p FROM PrmsThechnicalEvaluation p WHERE p.approvedBy = :approvedBy"),
    @NamedQuery(name = "PrmsThechnicalEvaluation.findByRequestForApproval", query = "SELECT p FROM PrmsThechnicalEvaluation p WHERE p.status=0"),
    @NamedQuery(name = "PrmsThechnicalEvaluation.findByDescription", query = "SELECT p FROM PrmsThechnicalEvaluation p WHERE p.description = :description"),
    @NamedQuery(name = "PrmsThechnicalEvaluation.findByMaxCheckListNum", query = "SELECT p FROM PrmsThechnicalEvaluation p WHERE p.evaluationId = (SELECT Max(c.evaluationId)  from PrmsThechnicalEvaluation c)"),
    @NamedQuery(name = "PrmsThechnicalEvaluation.findByPreparedBy", query = "SELECT p FROM PrmsThechnicalEvaluation p WHERE p.preparedBy = :preparedBy"),
    @NamedQuery(name = "PrmsThechnicalEvaluation.findByRemark", query = "SELECT p FROM PrmsThechnicalEvaluation p WHERE p.remark = :remark"),
    @NamedQuery(name = "PrmsThechnicalEvaluation.findByEvaluationType", query = "SELECT p FROM PrmsThechnicalEvaluation p WHERE p.evaluationType = :evaluationType"),
    @NamedQuery(name = "PrmsThechnicalEvaluation.findByStatus", query = "SELECT p FROM PrmsThechnicalEvaluation p WHERE p.status = :status"),
    @NamedQuery(name = "PrmsThechnicalEvaluation.findByApprovedBy", query = "SELECT p FROM PrmsThechnicalEvaluation p WHERE p.approvedBy = :approvedBy"),
    @NamedQuery(name = "PrmsThechnicalEvaluation.findByPreparedDate", query = "SELECT p FROM PrmsThechnicalEvaluation p WHERE p.preparedDate = :preparedDate")})
public class PrmsThechnicalEvaluation implements Serializable {

    @JoinColumn(name = "BID_ID", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrmsBid bidId;
    @JoinColumn(name = "QUOTATION_ID", referencedColumnName = "QUATATION_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrmsQuotation quotationId;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRMS_TECHNICAL_EVALUATION_SEQ")
    @SequenceGenerator(name = "PRMS_TECHNICAL_EVALUATION_SEQ", sequenceName = "PRMS_TECHNICAL_EVALUATION_SEQ", allocationSize = 1)
    @Column(name = "EVALUATION_ID")
    private BigDecimal evaluationId;
    @Column(name = "DATE_RIGISTERED")
    @Temporal(TemporalType.DATE)
    private Date dateRigistered;
    @Column(name = "APPROVED_DATE")
    @Temporal(TemporalType.DATE)
    private Date approvedDate;
    @Size(max = 100)
    @Column(name = "EVALUATION_NO")
    private String evaluationNo;
    @Size(max = 100)
    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "PREPARED_BY")
    private Integer preparedBy;
    @Size(max = 100)
    @Column(name = "REMARK")
    private String remark;
    @Size(max = 50)
    @Column(name = "EVALUATION_TYPE")
    private String evaluationType;
    @Size(max = 100)
    @Column(name = "PURCHASE_TYPE")
    private String purchaseType;
    @Size(max = 100)
    @Column(name = "SERVICE_TYPE")
    private String serviceType;

    @Size(max = 50)
    @Column(name = "PASS_LIMIT")
    private String passLimit;
    @Size(max = 20)
    @Column(name = "UNIT_MEASURE")
    private String unitMeasure;
    @Size(max = 50)
    @Column(name = "APPROVED_BY")
    private String approvedBy;
    @Column(name = "PREPARED_DATE")
    @Temporal(TemporalType.DATE)
    private Date preparedDate;
    @Column(name = "STATUS")
    private Integer status;
    @Transient
    private String columnName;
    @Transient
    private String columnValue;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "thechEvalId", fetch = FetchType.LAZY)
    private List<WfPrmsProcessed> wfprmsTechnicalEvaluationList;
    @OneToMany(mappedBy = "techEvId")
    private List<PrmsTechnicalFileUpload> prmsTechnicalFileUploadList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "thechnicalId", fetch = FetchType.LAZY)
    private List<PrmsThechincalEvaluationDet> prmsThechincalEvaluationDetList;
    @OneToMany(mappedBy = "technicalEvaluationId", fetch = FetchType.LAZY)
    private List<PrmsFinancialEvaluation> prmsFinancialEvaluationList;
    @JoinColumn(name = "PREMINARY_EVA_ID", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.LAZY)

    private PrmsPreminilaryEvaluation preminaryEvaId;
    @Column(name = "FEXFILEREFNUMBER")
    private BigInteger fexfilerefnumber;
    @JoinColumn(name = "DOCUMENT_ID", referencedColumnName = "DOCUMENT_ID")
    @ManyToOne
    private PrmsLuDmArchive documentId;

    public PrmsThechnicalEvaluation() {
    }

    public PrmsThechnicalEvaluation(BigDecimal evaluationId) {
        this.evaluationId = evaluationId;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnValue() {
        return columnValue;
    }

    public void setColumnValue(String columnValue) {
        this.columnValue = columnValue;
    }

    public String getPassLimit() {
        return passLimit;
    }

    public void setPassLimit(String passLimit) {
        this.passLimit = passLimit;
    }

    public String getUnitMeasure() {
        return unitMeasure;
    }

    public void setUnitMeasure(String unitMeasure) {
        this.unitMeasure = unitMeasure;
    }

    public BigDecimal getEvaluationId() {
        return evaluationId;
    }

    public void setEvaluationId(BigDecimal evaluationId) {
        this.evaluationId = evaluationId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getDateRigistered() {
        return dateRigistered;
    }

    public void setDateRigistered(Date dateRigistered) {
        this.dateRigistered = dateRigistered;
    }

    public Date getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(Date approvedDate) {
        this.approvedDate = approvedDate;
    }

    public String getEvaluationNo() {
        return evaluationNo;
    }

    public void setEvaluationNo(String evaluationNo) {
        this.evaluationNo = evaluationNo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPreparedBy() {
        return preparedBy;
    }

    public void setPreparedBy(Integer preparedBy) {
        this.preparedBy = preparedBy;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getEvaluationType() {
        return evaluationType;
    }

    public void setEvaluationType(String evaluationType) {
        this.evaluationType = evaluationType;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    public Date getPreparedDate() {
        return preparedDate;
    }

    public void setPreparedDate(Date preparedDate) {
        this.preparedDate = preparedDate;
    }

    public void addThechincalEvaluationDet(PrmsThechincalEvaluationDet prmsThechincalEvaluationDet) {
        if (prmsThechincalEvaluationDet.getThechnicalId() != this) {
            this.getPrmsThechincalEvaluationDetList().add(prmsThechincalEvaluationDet);
            prmsThechincalEvaluationDet.setThechnicalId(this);
        }

    }

    @XmlTransient
    public List<PrmsThechincalEvaluationDet> getPrmsThechincalEvaluationDetList() {
        if (prmsThechincalEvaluationDetList == null) {
            prmsThechincalEvaluationDetList = new ArrayList<>();
        }
        return prmsThechincalEvaluationDetList;
    }

    public void setPrmsThechincalEvaluationDetList(List<PrmsThechincalEvaluationDet> prmsThechincalEvaluationDetList) {
        this.prmsThechincalEvaluationDetList = prmsThechincalEvaluationDetList;
    }

    @XmlTransient

    public PrmsPreminilaryEvaluation getPreminaryEvaId() {
        return preminaryEvaId;
    }

    public List<PrmsFinancialEvaluation> getPrmsFinancialEvaluationList() {
        return prmsFinancialEvaluationList;
    }

    public void setPrmsFinancialEvaluationList(List<PrmsFinancialEvaluation> prmsFinancialEvaluationList) {
        this.prmsFinancialEvaluationList = prmsFinancialEvaluationList;
    }

    public void setPreminaryEvaId(PrmsPreminilaryEvaluation preminaryEvaId) {
        this.preminaryEvaId = preminaryEvaId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (evaluationId != null ? evaluationId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PrmsThechnicalEvaluation)) {
            return false;
        }
        PrmsThechnicalEvaluation other = (PrmsThechnicalEvaluation) object;
        if ((this.evaluationId == null && other.evaluationId != null) || (this.evaluationId != null && !this.evaluationId.equals(other.evaluationId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return evaluationNo;
    }

    public PrmsBid getBidId() {
        return bidId;
    }

    public void setBidId(PrmsBid bidId) {
        this.bidId = bidId;
    }

    public PrmsQuotation getQuotationId() {
        return quotationId;
    }

    public void setQuotationId(PrmsQuotation quotationId) {
        this.quotationId = quotationId;
    }

    public String getPurchaseType() {
        return purchaseType;
    }

    public void setPurchaseType(String purchaseType) {
        this.purchaseType = purchaseType;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public BigInteger getFexfilerefnumber() {
        return fexfilerefnumber;
    }

    public void setFexfilerefnumber(BigInteger fexfilerefnumber) {
        this.fexfilerefnumber = fexfilerefnumber;
    }

    public PrmsLuDmArchive getDocumentId() {
        return documentId;
    }

    public void setDocumentId(PrmsLuDmArchive documentId) {
        this.documentId = documentId;
    }

    @XmlTransient
    public List<WfPrmsProcessed> getWfprmsTechnicalEvaluationList() {
        if (wfprmsTechnicalEvaluationList == null) {
            wfprmsTechnicalEvaluationList = new ArrayList<>();
        }
        return wfprmsTechnicalEvaluationList;
    }

    public void setWfprmsTechnicalEvaluationList(List<WfPrmsProcessed> wfprmsTechnicalEvaluationList) {
        this.wfprmsTechnicalEvaluationList = wfprmsTechnicalEvaluationList;

    }

    @XmlTransient
    public List<PrmsTechnicalFileUpload> getPrmsTechnicalFileUploadList() {
        if (prmsTechnicalFileUploadList == null) {
            prmsTechnicalFileUploadList = new ArrayList<>();
        }
        return prmsTechnicalFileUploadList;
    }

    public void setPrmsTechnicalFileUploadList(List<PrmsTechnicalFileUpload> prmsTechnicalFileUploadList) {
        this.prmsTechnicalFileUploadList = prmsTechnicalFileUploadList;
    }

    public void add(PrmsTechnicalFileUpload hrDisciplineLocal) {
        if (hrDisciplineLocal.getTechEvId() != this) {
            this.getPrmsTechnicalFileUploadList().add(hrDisciplineLocal);
            hrDisciplineLocal.setTechEvId(this);

        }
    }
}
