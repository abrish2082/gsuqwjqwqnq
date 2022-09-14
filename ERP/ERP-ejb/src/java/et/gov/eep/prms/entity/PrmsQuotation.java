/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.entity;

import et.gov.eep.commonApplications.entity.PrmsLuDmArchive;
import et.gov.eep.commonApplications.entity.WfPrmsProcessed;
import et.gov.eep.hrms.entity.committee.HrCommittees;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.organization.HrDepartments;
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

@Entity
@Table(name = "PRMS_QUOTATION")//, catalog = "", schema = "EEP")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrmsQuotation.findAll", query = "SELECT p FROM PrmsQuotation p"),
    @NamedQuery(name = "PrmsQuotation.findAlls", query = "SELECT p FROM PrmsQuotation p WHERE p.status=3"),
    @NamedQuery(name = "PrmsQuotation.findByQuatationId", query = "SELECT p FROM PrmsQuotation p WHERE p.quatationId = :quatationId"),
    @NamedQuery(name = "PrmsQuotation.findByPreparedBy", query = "SELECT p FROM PrmsQuotation p WHERE p.preparedBy = :preparedBy"),
    @NamedQuery(name = "PrmsQuotation.findByMaxQuotationNum", query = "SELECT p FROM PrmsQuotation p WHERE p.quatationId = (SELECT Max(c.quatationId)  from PrmsQuotation c)"),
    @NamedQuery(name = "PrmsQuotation.findByQuotationNo", query = "SELECT p FROM PrmsQuotation p WHERE p.quotationNo = :quotationNo"),
    @NamedQuery(name = "PrmsQuotation.SearchfindByQuotationNo", query = "SELECT p FROM PrmsQuotation p WHERE p.quotationNo LIKE :quotationNo and p.preparedBy=:preparedBy"),
    @NamedQuery(name = "PrmsQuotation.SearchfindByQuotationNoStatus", query = "SELECT p FROM PrmsQuotation p WHERE p.status=0"),

    @NamedQuery(name = "PrmsQuotation.findByRegDate", query = "SELECT p FROM PrmsQuotation p WHERE p.regDate = :regDate"),
    @NamedQuery(name = "PrmsQuotation.findByRemark", query = "SELECT p FROM PrmsQuotation p WHERE p.remark = :remark"),
    @NamedQuery(name = "PrmsQuotation.findByStatus", query = "SELECT p FROM PrmsQuotation p WHERE p.status = :status"),
    @NamedQuery(name = "PrmsQuotation.findByQuotationName", query = "SELECT p FROM PrmsQuotation p WHERE p.quotationName = :quotationName"),
    //@NamedQuery(name = "PrmsQuotation.findByDocid", query = "SELECT p FROM PrmsQuotation p WHERE p.docid = :docid"),
    @NamedQuery(name = "PrmsQuotation.findByRequestedBy", query = "SELECT p FROM PrmsQuotation p WHERE p.requestedBy = :requestedBy")})

public class PrmsQuotation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRMS_QUATATION_SEQ")
    @SequenceGenerator(name = "PRMS_QUATATION_SEQ", sequenceName = "PRMS_QUATATION_SEQ", allocationSize = 1)
    @Column(name = "QUATATION_ID")
    private BigDecimal quatationId;
    @Size(max = 255)
    @Column(name = "PREPARED_BY")
    private String preparedBy;
    @Size(max = 255)
    @Column(name = "QUOTATION_NO")
    private String quotationNo;
    @Column(name = "REG_DATE")
    @Temporal(TemporalType.DATE)
    private Date regDate;
    @Size(max = 255)
    @Column(name = "REMARK")
    private String remark;
    @Column(name = "STATUS")
    private Integer status;
    @Size(max = 20)
    @Column(name = "QUOTATION_NAME")
    private String quotationName;

    @Size(max = 100)
    @Column(name = "PURCHASE_TYPE")
    private String purchaseType;

    //<editor-fold defaultstate="collapsed" desc="Declaring Variables for Dynamic Searching">
    @Transient
    private String columnName;

    @Transient
    private String columnValue;
    //</editor-fold>

    @JoinColumn(name = "COMMITTEE_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrCommittees committeeId;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "quotationId")
    private List<PrmsFinancialEvalResult> prmsFinancialEvalResultList;

    @JoinColumn(name = "PURCHASER", referencedColumnName = "ID")
    @ManyToOne
    private HrEmployees purchaser;

    @JoinColumn(name = "REQUESTED_BY", referencedColumnName = "DEP_ID")
    @ManyToOne
    private HrDepartments requestedBy;
    @JoinColumn(name = "DOCUMENT_ID", referencedColumnName = "DOCUMENT_ID")
    @ManyToOne
    private PrmsLuDmArchive documentId;

    @OneToMany(mappedBy = "quatationId", fetch = FetchType.LAZY)
    private List<PrmsPaymentRequisition> prmsPaymentRequisitionList;

    @OneToMany(mappedBy = "quotationId", fetch = FetchType.LAZY)
    private List<PrmsPurchaseOrder> prmsPurchaseOrderList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "quotationId", fetch = FetchType.LAZY)
    private List<PrmsQuotationDetail> prmsQuotationDetailList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "quotationId", fetch = FetchType.LAZY)
    private List<PrmsThechnicalEvaluation> prmsThechnicalEvaluationsList;

    @OneToMany(mappedBy = "quotationId", cascade = CascadeType.ALL)
    private List<PrmsSupplierPerformanceInfo> supplierPerformanceInfos;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "quotationId")
    private List<PrmsContract> prmsContractList;

    @OneToMany(mappedBy = "quatationId", cascade = CascadeType.ALL)
    private List<WfPrmsProcessed> prmsWorkFlowProccedList;

    @OneToMany(mappedBy = "quotationId", cascade = CascadeType.ALL)
    private List<PrmsQuotationFileUpload> prmsQuotationFileUploadList;

//    @OneToMany(mappedBy = "quotationId", cascade = CascadeType.ALL)
//    private List<PrmsQuotationFileUpload> prmsQuotationFileUploadList;
//    @Size(max = 20)
//    @Column(name = "PURCHASER")
//    private String purchaser;
    public PrmsQuotation() {
    }

    public String getPurchaseType() {
        return purchaseType;
    }

    public void setPurchaseType(String purchaseType) {
        this.purchaseType = purchaseType;
    }

    public PrmsQuotation(BigDecimal quatationId) {
        this.quatationId = quatationId;
    }

    public BigDecimal getQuatationId() {
        return quatationId;
    }

    public void setQuatationId(BigDecimal quatationId) {
        this.quatationId = quatationId;
    }

    public String getPreparedBy() {
        return preparedBy;
    }

    public void setPreparedBy(String preparedBy) {
        this.preparedBy = preparedBy;
    }

    /**
     *
     * @return
     */
    public String getQuotationNo() {
        return quotationNo;
    }

    /**
     *
     * @param quotationNo
     */
    public void setQuotationNo(String quotationNo) {
        this.quotationNo = quotationNo;
    }

    /**
     *
     * @return
     */
    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getQuotationName() {
        return quotationName;
    }

    public void setQuotationName(String quotationName) {
        this.quotationName = quotationName;
    }

    @XmlTransient
    public List<PrmsQuotationDetail> getPrmsQuotationDetailList() {
        if (prmsQuotationDetailList == null) {
            prmsQuotationDetailList = new ArrayList<>();
        }
        return prmsQuotationDetailList;
    }

    public void setPrmsQuotationDetailList(List<PrmsQuotationDetail> prmsQuotationDetailList) {
        this.prmsQuotationDetailList = prmsQuotationDetailList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (quatationId != null ? quatationId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PrmsQuotation)) {
            return false;
        }
        PrmsQuotation other = (PrmsQuotation) object;
        if ((this.quatationId == null && other.quatationId != null) || (this.quatationId != null && !this.quatationId.equals(other.quatationId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return quotationNo;
    }

    public void addQuotationDetail(PrmsQuotationDetail prmsQuotationDetail) {

        if (prmsQuotationDetail.getQuotationId() != this) {

            this.getPrmsQuotationDetailList().add(prmsQuotationDetail);
            prmsQuotationDetail.setQuotationId(this);
        }
    }

    public HrEmployees getPurchaser() {
        return purchaser;
    }

    public void setPurchaser(HrEmployees purchaser) {
        this.purchaser = purchaser;
    }

    public HrDepartments getRequestedBy() {
        return requestedBy;
    }

    public void setRequestedBy(HrDepartments requestedBy) {
        this.requestedBy = requestedBy;
    }

    public HrCommittees getCommitteeId() {
        return committeeId;
    }

    public void setCommitteeId(HrCommittees committeeId) {
        this.committeeId = committeeId;
    }

    //<editor-fold defaultstate="collapsed" desc="getter and setter of Variables for Dynamic Searching">
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
    //</editor-fold>

//    @XmlTransient
//    public List<PrmsPostQualification> getPrmsPostQualificationList() {
//        return prmsPostQualificationList;
//    }
//
//    public void setPrmsPostQualificationList(List<PrmsPostQualification> prmsPostQualificationList) {
//        this.prmsPostQualificationList = prmsPostQualificationList;
//    }
    @XmlTransient
    public List<PrmsThechnicalEvaluation> getPrmsThechnicalEvaluationsList() {
        if (prmsThechnicalEvaluationsList == null) {
            prmsThechnicalEvaluationsList = new ArrayList<>();
        }
        return prmsThechnicalEvaluationsList;
    }

    public void setPrmsThechnicalEvaluationsList(List<PrmsThechnicalEvaluation> prmsThechnicalEvaluationsList) {
        this.prmsThechnicalEvaluationsList = prmsThechnicalEvaluationsList;
    }
//    @XmlTransient
//
//    public List<PrmsContractAmendment> getPrmsContractAmendmentList() {
//        return prmsContractAmendmentList;
//    }
//
//    public void setPrmsContractAmendmentList(List<PrmsContractAmendment> prmsContractAmendmentList) {
//        this.prmsContractAmendmentList = prmsContractAmendmentList;
//    }

    @XmlTransient
    public List<PrmsSupplierPerformanceInfo> getSupplierPerformanceInfos() {
        if (supplierPerformanceInfos == null) {
            supplierPerformanceInfos = new ArrayList<>();
        }
        return supplierPerformanceInfos;
    }

    public void setSupplierPerformanceInfos(List<PrmsSupplierPerformanceInfo> supplierPerformanceInfos) {
        this.supplierPerformanceInfos = supplierPerformanceInfos;
    }

    @XmlTransient
    public List<PrmsContract> getPrmsContractList() {
        return prmsContractList;
    }

    public void setPrmsContractList(List<PrmsContract> prmsContractList) {
        this.prmsContractList = prmsContractList;
    }

    @XmlTransient
    public List<WfPrmsProcessed> getPrmsWorkFlowProccedList() {
        if (prmsWorkFlowProccedList == null) {
            prmsWorkFlowProccedList = new ArrayList<>();
        }
        return prmsWorkFlowProccedList;
    }

    public void setHrWorkFlowProccedList(List<WfPrmsProcessed> prmsWorkFlowProccedList) {
        this.prmsWorkFlowProccedList = prmsWorkFlowProccedList;
    }

    @XmlTransient

    public List<PrmsFinancialEvalResult> getPrmsFinancialEvalResultList() {
        if (prmsFinancialEvalResultList == null) {
            prmsFinancialEvalResultList = new ArrayList<>();
        }
        return prmsFinancialEvalResultList;
    }

    public void setPrmsFinancialEvalResultList(List<PrmsFinancialEvalResult> prmsFinancialEvalResultList) {
        this.prmsFinancialEvalResultList = prmsFinancialEvalResultList;
    }

    @XmlTransient
    public List<PrmsQuotationFileUpload> getPrmsQuotationFileUploadList() {
        if (prmsQuotationFileUploadList == null) {
            prmsQuotationFileUploadList = new ArrayList<>();
        }
        return prmsQuotationFileUploadList;
    }

    public void setPrmsQuotationFileUploadList(List<PrmsQuotationFileUpload> prmsQuotationFileUploadList) {
        this.prmsQuotationFileUploadList = prmsQuotationFileUploadList;
    }

//    public void add(PrmsQuotationFileUpload quotationLocal) {
//        if (quotationLocal.getQuotationId() != this) {
//            this.getPrmsQuotationFileUploadList().add(quotationLocal);
//            quotationLocal.setQuotationId(this);
//        }
//    }
    /**
     ***************************************************************************
     *
     * @param prmsQuotationUpload
     * **************************************************************************
     */
    public void add(PrmsQuotationFileUpload prmsQuotationUpload) {

        if (prmsQuotationUpload.getQuotationId() != this) {

            this.getPrmsQuotationFileUploadList().add(prmsQuotationUpload);
            prmsQuotationUpload.setQuotationId(this);
        }
    }

    @XmlTransient
    public List<PrmsPaymentRequisition> getPrmsPaymentRequisitionList() {
        if (prmsPaymentRequisitionList == null) {
            prmsPaymentRequisitionList = new ArrayList<>();
        }
        return prmsPaymentRequisitionList;
    }

    public void setPrmsPaymentRequisitionList(List<PrmsPaymentRequisition> prmsPaymentRequisitionList) {
        this.prmsPaymentRequisitionList = prmsPaymentRequisitionList;
    }

    @XmlTransient
    public List<PrmsPurchaseOrder> getPrmsPurchaseOrderList() {
        if (prmsPurchaseOrderList == null) {
            prmsPurchaseOrderList = new ArrayList<>();
        }
        return prmsPurchaseOrderList;
    }

    public void setPrmsPurchaseOrderList(List<PrmsPurchaseOrder> prmsPurchaseOrderList) {
        this.prmsPurchaseOrderList = prmsPurchaseOrderList;
    }

    public PrmsLuDmArchive getDocumentId() {
        return documentId;
    }

    public void setDocumentId(PrmsLuDmArchive documentId) {
        this.documentId = documentId;
    }

}
