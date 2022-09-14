/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.entity;

import et.gov.eep.commonApplications.entity.PrmsLuDmArchive;
import et.gov.eep.commonApplications.entity.WfPrmsProcessed;
import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "PRMS_SUPP_SPECIFICATION")//, catalog = "", schema = "EEP")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrmsSuppSpecification.findAll", query = "SELECT p FROM PrmsSuppSpecification p"),
    @NamedQuery(name = "PrmsSuppSpecification.findBySuppSpecId", query = "SELECT p FROM PrmsSuppSpecification p WHERE p.suppSpecId = :suppSpecId"),
    @NamedQuery(name = "PrmsSuppSpecification.findByRemark", query = "SELECT p FROM PrmsSuppSpecification p WHERE p.remark = :remark"),
    @NamedQuery(name = "PrmsSuppSpecification.findBySuppSpecNo", query = "SELECT p FROM PrmsSuppSpecification p WHERE p.suppSpecNo  = :suppSpecNo"),
    @NamedQuery(name = "PrmsSuppSpecification.findAllByStatus", query = "SELECT p FROM PrmsSuppSpecification p WHERE p.status=0"),

    @NamedQuery(name = "PrmsSuppSpecification.SearchBySuppSpecNo", query = "SELECT p FROM PrmsSuppSpecification p WHERE p.suppSpecNo  LIKE :suppSpecNo and p.preparedBy=:preparedBy ORDER BY P.suppSpecId ASC"),

    @NamedQuery(name = "PrmsSuppSpecification.findByMaxSpecficationNo", query = "SELECT p FROM PrmsSuppSpecification p WHERE p.suppSpecId = (SELECT Max(c.suppSpecId)  from PrmsSuppSpecification c)"),
    @NamedQuery(name = "PrmsSuppSpecification.findByPreparedBy", query = "SELECT p FROM PrmsSuppSpecification p WHERE p.preparedBy = :preparedBy"),
    @NamedQuery(name = "PrmsSuppSpecification.findByStatus", query = "SELECT p FROM PrmsSuppSpecification p WHERE p.status = :status"),
    @NamedQuery(name = "PrmsSuppSpecification.findByDateRigistered", query = "SELECT p FROM PrmsSuppSpecification p WHERE p.dateRigistered = :dateRigistered")})
public class PrmsSuppSpecification implements Serializable {

    @JoinColumn(name = "PREMINARY_EVA_ID", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrmsPreminilaryEvalutionDt preminaryEvaId;
    @Size(max = 100)
    @Column(name = "SUPP_SPEC_NO")
    private String suppSpecNo;

    @JoinColumn(name = "DOCUMENT_ID", referencedColumnName = "DOCUMENT_ID")
    @ManyToOne
    private PrmsLuDmArchive documentId;

//    @Lob
//    @Column(name = "SUPPLIER_SPCIFICATION")
//    private byte[] supplierSpcification;
    @JoinColumn(name = "SUPP_ID", referencedColumnName = "ID")
    @ManyToOne
    private PrmsSupplyProfile suppId;
    @JoinColumn(name = "BID_ID", referencedColumnName = "ID")
    @ManyToOne
    private PrmsBid bidId;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRMS_SUPPLIER_SPECFICATION_SEQ")
    @SequenceGenerator(name = "PRMS_SUPPLIER_SPECFICATION_SEQ", sequenceName = "PRMS_SUPPLIER_SPECFICATION_SEQ", allocationSize = 1)
    @Column(name = "SUPP_SPEC_ID")
    private BigDecimal suppSpecId;
    @Size(max = 100)
    @Column(name = "REMARK")
    private String remark;
    @Size(max = 20)
    @Column(name = "PREPARED_BY")
    private String preparedBy;
    @Column(name = "DATE_RIGISTERED")
    @Temporal(TemporalType.DATE)
    private Date dateRigistered;
    @Column(name = "FILENAME")
    private String filename;
    @Column(name = "STATUS")
    private Integer status;
    @Transient
    private String columnName;
    @Transient
    private String columnValue;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "suppSpecId", fetch = FetchType.LAZY)
    private List<PrmsSupplierSpecificationDt> prmsSupplierSpecificationDtList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "suppSpecId")
    private List<PrmsSuppSpecificationUpload> prmsSuppSpecificationUploadList;
    @OneToMany(mappedBy = "suppSpecId", cascade = CascadeType.ALL)
    private List<WfPrmsProcessed> prmsWorkFlowProccedList;

    public PrmsSuppSpecification() {
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public PrmsSuppSpecification(BigDecimal suppSpecId) {
        this.suppSpecId = suppSpecId;
    }

    public BigDecimal getSuppSpecId() {
        return suppSpecId;
    }

    public void setSuppSpecId(BigDecimal suppSpecId) {
        this.suppSpecId = suppSpecId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public PrmsBid getBidId() {
        return bidId;
    }

    public void setBidId(PrmsBid bidId) {
        this.bidId = bidId;
    }

    public String getPreparedBy() {
        return preparedBy;
    }

    public void setPreparedBy(String preparedBy) {
        this.preparedBy = preparedBy;
    }

    public Date getDateRigistered() {
        return dateRigistered;
    }

    /**
     * *************************************************************************
     *
     * @param dateRigistered
     * ************************************************************************
     */
    public void setDateRigistered(Date dateRigistered) {
        this.dateRigistered = dateRigistered;
    }

    @XmlTransient
    public List<PrmsSupplierSpecificationDt> getPrmsSupplierSpecificationDtList() {

        if (prmsSupplierSpecificationDtList == null) {
            prmsSupplierSpecificationDtList = new ArrayList<>();
        }

        return prmsSupplierSpecificationDtList;
    }

    /**
     *
     * @param prmsSupplierSpecificationDtList
     */
    public void setPrmsSupplierSpecificationDtList(
            List<PrmsSupplierSpecificationDt> prmsSupplierSpecificationDtList) {
        this.prmsSupplierSpecificationDtList = prmsSupplierSpecificationDtList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (suppSpecId != null ? suppSpecId.hashCode() : 0);
        return hash;
    }

    /**
     *
     * @param object
     * @return
     */
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PrmsSuppSpecification)) {
            return false;
        }

        PrmsSuppSpecification other = (PrmsSuppSpecification) object;

        if ((this.suppSpecId == null && other.suppSpecId != null)
                || (this.suppSpecId != null && !this.suppSpecId.equals(other.suppSpecId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.prms.entity.PrmsSuppSpecification[ suppSpecId=" + suppSpecId + " ]";
    }

    /**
     * *************************************************************************
     *
     * @return
     * ************************************************************************
     */
    public String getSuppSpecNo() {
        return suppSpecNo;
    }

    /**
     * *************************************************************************
     *
     * @param suppSpecNo
     * ************************************************************************
     */
    public void setSuppSpecNo(String suppSpecNo) {
        this.suppSpecNo = suppSpecNo;
    }

//    public byte[] getSupplierSpcification() {
//        return supplierSpcification;
//    }
//
//    public void setSupplierSpcification(byte[] supplierSpcification) {
//        this.supplierSpcification = supplierSpcification;
//    }
    /**
     * *************************************************************************
     *
     * @param prmsSupplierSpecificationDt
     * ************************************************************************
     */
    public void addSuppSpecDetail(
            PrmsSupplierSpecificationDt prmsSupplierSpecificationDt) {

        if (prmsSupplierSpecificationDt.getSuppSpecId() != this) {
            this.getPrmsSupplierSpecificationDtList().add(prmsSupplierSpecificationDt);
            prmsSupplierSpecificationDt.setSuppSpecId(this);
        }
    }

    public PrmsPreminilaryEvalutionDt getPreminaryEvaId() {
        return preminaryEvaId;
    }

    public void setPreminaryEvaId(PrmsPreminilaryEvalutionDt preminaryEvaId) {
        this.preminaryEvaId = preminaryEvaId;
    }

    public PrmsSupplyProfile getSuppId() {
        return suppId;
    }

    public void setSuppId(PrmsSupplyProfile suppId) {
        this.suppId = suppId;
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
    public List<PrmsSuppSpecificationUpload> getPrmsSuppSpecificationUploadList() {

        if (prmsSuppSpecificationUploadList == null) {
            prmsSuppSpecificationUploadList = new ArrayList<>();
        }

        return prmsSuppSpecificationUploadList;
    }

    public void setPrmsSuppSpecificationUploadList(
            List<PrmsSuppSpecificationUpload> prmsSuppSpecificationUploadList) {
        this.prmsSuppSpecificationUploadList = prmsSuppSpecificationUploadList;
    }

    public void add(PrmsSuppSpecificationUpload prmsSuppSpecifiicationFileUpload) {

        if (prmsSuppSpecifiicationFileUpload.getSuppSpecId() != this) {
            this.getPrmsSuppSpecificationUploadList().add(prmsSuppSpecifiicationFileUpload);
            prmsSuppSpecifiicationFileUpload.setSuppSpecId(this);
            System.out.println("===add---" + prmsSuppSpecifiicationFileUpload.getSuppSpecId());
        }
    }

    public PrmsLuDmArchive getDocumentId() {
        return documentId;
    }

    public void setDocumentId(PrmsLuDmArchive documentId) {
        this.documentId = documentId;
    }

}
