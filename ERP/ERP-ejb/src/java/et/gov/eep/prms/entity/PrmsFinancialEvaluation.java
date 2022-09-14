/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.entity;

import et.gov.eep.commonApplications.entity.PrmsLuDmArchive;
import et.gov.eep.commonApplications.entity.WfPrmsProcessed;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author user
 */
@Entity
@Table(name = "PRMS_FINANCIAL_EVALUATION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrmsFinancialEvaluation.findAll", query = "SELECT p FROM PrmsFinancialEvaluation p"),
    @NamedQuery(name = "PrmsFinancialEvaluation.findById", query = "SELECT p FROM PrmsFinancialEvaluation p WHERE p.id = :id"),
    @NamedQuery(name = "PrmsFinancialEvaluation.findFinancialNoAuto", query = "SELECT p FROM PrmsFinancialEvaluation p WHERE p.id = (SELECT Max(p.id)  from PrmsFinancialEvaluation p)"),
    @NamedQuery(name = "PrmsFinancialEvaluation.findByFinancialNo", query = "SELECT p FROM PrmsFinancialEvaluation p WHERE p.financialNo LIKE :financialNo AND p.preparedBy=:preparedBy"),
    @NamedQuery(name = "PrmsFinancialEvaluation.findByFinancialNum", query = "SELECT p FROM PrmsFinancialEvaluation p WHERE p.financialNo LIKE :financialNum"),
    @NamedQuery(name = "PrmsFinancialEvaluation.findByBidId", query = "SELECT p FROM PrmsFinancialEvaluation p WHERE p.bidId = :bidId"),
    @NamedQuery(name = "PrmsFinancialEvaluation.findByPreparedBy", query = "SELECT p FROM PrmsFinancialEvaluation p WHERE p.preparedBy = :preparedBy"),
    @NamedQuery(name = "PrmsFinancialEvaluation.findByEvaluationReq", query = "SELECT p FROM PrmsFinancialEvaluation p WHERE p.status = 0"),
    @NamedQuery(name = "PrmsFinancialEvaluation.findByDateReg", query = "SELECT p FROM PrmsFinancialEvaluation p WHERE p.dateReg = :dateReg"),
    @NamedQuery(name = "PrmsFinancialEvaluation.findByKindOfShipment", query = "SELECT p FROM PrmsFinancialEvaluation p WHERE p.kindOfShipment = :kindOfShipment"),
    @NamedQuery(name = "PrmsFinancialEvaluation.findByTechnicalEvaluationId", query = "SELECT p FROM PrmsFinancialEvaluation p WHERE p.technicalEvaluationId = :technicalEvaluationId"),
    @NamedQuery(name = "PrmsFinancialEvaluation.findByDescription", query = "SELECT p FROM PrmsFinancialEvaluation p WHERE p.description = :description"),
    @NamedQuery(name = "PrmsFinancialEvaluation.findByRemark", query = "SELECT p FROM PrmsFinancialEvaluation p WHERE p.remark = :remark")})
public class PrmsFinancialEvaluation implements Serializable {

    @Size(max = 20)
    @Column(name = "BID_TYPE", length = 20)
    private String bidType;
    @Column(name = "FILE_UPLOAD_REFNO")
    private Integer fileUploadRefno;
    @OneToMany(mappedBy = "financialevaId", fetch = FetchType.LAZY)
    private List<PrmsFinancialEvalResult> prmsFinancialEvalResultCollection;
    @OneToMany(mappedBy = "financialEvaluationId", fetch = FetchType.LAZY)
    private List<WfPrmsProcessed> wfPrmsProcessedLists;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRMS_FINANCIAL_EVALUATION_SEQ_GENERATOR")
    @SequenceGenerator(name = "PRMS_FINANCIAL_EVALUATION_SEQ_GENERATOR", sequenceName = "PRMS_FINANCIAL_EVALUATION_SEQ", allocationSize = 1)
    private String id;
    @Size(max = 20)
    @Column(name = "FINANCIAL_NO")
    private String financialNo;
    @Column(name = "DATE_REG")
    @Temporal(TemporalType.DATE)
    private Date dateReg;
    @Size(max = 60)
    @Column(name = "KIND_OF_SHIPMENT")
    private String kindOfShipment;
    @Column(name = "DESCRIPTION")
    private String description;
    @Size(max = 200)
    @Column(name = "REMARK")
    private String remark;
    @Size(max = 20)
    @Column(name = "APPROVED_BY", length = 20)
    private String approvedBy;
    @Column(name = "STATUS")
    private Integer status;
    @Column(name = "PREPARED_BY")
    private Integer preparedBy;
    @Transient
    private String columnName;
    @Transient
    private String columnValue;
    @JoinColumn(name = "TECHNICAL_EVALUATION_ID", referencedColumnName = "EVALUATION_ID")
    @ManyToOne
    private PrmsThechnicalEvaluation technicalEvaluationId;
    @JoinColumn(name = "DOCUMENT_ID", referencedColumnName = "DOCUMENT_ID")
    @ManyToOne
    private PrmsLuDmArchive documentId;
    @JoinColumn(name = "BID_ID", referencedColumnName = "ID")
    @ManyToOne
    private PrmsBid bidId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "financialEvaluationId", fetch = FetchType.LAZY)
    private List<PrmsFinancialEvaluaDetail> prmsFinancialEvaluaDetailList = new ArrayList<>();

    public PrmsFinancialEvaluation() {
    }

    public PrmsFinancialEvaluation(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
    public String getFinancialNo() {
        return financialNo;
    }

    public void setFinancialNo(String financialNo) {
        this.financialNo = financialNo;
    }

    public Date getDateReg() {
        return dateReg;
    }

    public void setDateReg(Date dateReg) {
        this.dateReg = dateReg;
    }

    public String getKindOfShipment() {
        return kindOfShipment;
    }

    public void setKindOfShipment(String kindOfShipment) {
        this.kindOfShipment = kindOfShipment;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
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

    @XmlTransient
    public List<PrmsFinancialEvaluaDetail> getPrmsFinancialEvaluaDetailList() {

        return prmsFinancialEvaluaDetailList;
    }

    public void setPrmsFinancialEvaluaDetailList(List<PrmsFinancialEvaluaDetail> prmsFinancialEvaluaDetailList) {
        this.prmsFinancialEvaluaDetailList = prmsFinancialEvaluaDetailList;
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
        if (!(object instanceof PrmsFinancialEvaluation)) {
            return false;
        }
        PrmsFinancialEvaluation other = (PrmsFinancialEvaluation) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.prms.entity.PrmsFinancialEvaluation[ id=" + id + " ]";
    }

    public PrmsThechnicalEvaluation getTechnicalEvaluationId() {
        return technicalEvaluationId;
    }

    public void setTechnicalEvaluationId(PrmsThechnicalEvaluation technicalEvaluationId) {
        this.technicalEvaluationId = technicalEvaluationId;
    }

    public PrmsBid getBidId() {
        return bidId;
    }

    public void setBidId(PrmsBid bidId) {
        this.bidId = bidId;
    }

    public String getBidType() {
        return bidType;
    }

    public void setBidType(String bidType) {
        this.bidType = bidType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getFileUploadRefno() {
        return fileUploadRefno;
    }

    public void setFileUploadRefno(Integer fileUploadRefno) {
        this.fileUploadRefno = fileUploadRefno;
    }

    public PrmsLuDmArchive getDocumentId() {
        return documentId;
    }

    public void setDocumentId(PrmsLuDmArchive documentId) {
        this.documentId = documentId;
    }

    @XmlTransient
    public List<PrmsFinancialEvalResult> getPrmsFinancialEvalResultCollection() {
        return prmsFinancialEvalResultCollection;
    }

    public void setPrmsFinancialEvalResultCollection(List<PrmsFinancialEvalResult> prmsFinancialEvalResultCollection) {
        this.prmsFinancialEvalResultCollection = prmsFinancialEvalResultCollection;
    }

    @XmlTransient
    public List<WfPrmsProcessed> getWfPrmsProcessedCollection() {
        if (wfPrmsProcessedLists == null) {
            wfPrmsProcessedLists = new ArrayList<>();
        }
        return wfPrmsProcessedLists;
    }

    public void setWfPrmsProcessedCollection(List<WfPrmsProcessed> wfPrmsProcessedLists) {
        this.wfPrmsProcessedLists = wfPrmsProcessedLists;
    }

}
