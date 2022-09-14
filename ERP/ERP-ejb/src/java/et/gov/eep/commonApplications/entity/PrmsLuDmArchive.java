/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.commonApplications.entity;

import et.gov.eep.prms.entity.PrmsAward;
import et.gov.eep.prms.entity.PrmsBankClearance;
import et.gov.eep.prms.entity.PrmsBid;
import et.gov.eep.prms.entity.PrmsBidAmend;
import et.gov.eep.prms.entity.PrmsBidOpeningCheckList;
import et.gov.eep.prms.entity.PrmsContract;
import et.gov.eep.prms.entity.PrmsContractAmendment;
import et.gov.eep.prms.entity.PrmsFinancialEvaluation;
import et.gov.eep.prms.entity.PrmsForeignExchange;
import et.gov.eep.prms.entity.PrmsImportShippingInstruct;
import et.gov.eep.prms.entity.PrmsInsuranceRequisition;
import et.gov.eep.prms.entity.PrmsLcRigistration;
import et.gov.eep.prms.entity.PrmsLcRigistrationAmend;
import et.gov.eep.prms.entity.PrmsPurchaseRequest;
import et.gov.eep.prms.entity.PrmsQuotation;
import et.gov.eep.prms.entity.PrmsSuppSpecification;
import et.gov.eep.prms.entity.PrmsThechnicalEvaluation;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
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
 * @author mora1
 */
@Entity
@Table(name = "PRMS_LU_DM_ARCHIVE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrmsLuDmArchive.findAll", query = "SELECT p FROM PrmsLuDmArchive p"),
    @NamedQuery(name = "PrmsLuDmArchive.findByDocumentId", query = "SELECT p FROM PrmsLuDmArchive p WHERE p.documentId = :documentId"),
    @NamedQuery(name = "PrmsLuDmArchive.findByFileName", query = "SELECT p FROM PrmsLuDmArchive p WHERE p.fileName = :fileName"),
    @NamedQuery(name = "PrmsLuDmArchive.findByFileType", query = "SELECT p FROM PrmsLuDmArchive p WHERE p.fileType = :fileType")})
public class PrmsLuDmArchive implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @NotNull
    @Id
    @Basic(optional = false)
    @GeneratedValue(generator = "PRMS_LU_DM_ARCHIVE_SEQ", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "PRMS_LU_DM_ARCHIVE_SEQ", sequenceName = "PRMS_LU_DM_ARCHIVE_SEQ", allocationSize = 1)
    @Column(name = "DOCUMENT_ID", nullable = false, precision = 0, scale = -127)
    private BigDecimal documentId;
    @Size(max = 200)
    @Column(name = "FILE_NAME", length = 200)
    private String fileName;
    @Size(max = 35)
    @Column(name = "FILE_TYPE", length = 35)
    private String fileType;
    @Lob
    @Column(name = "UPLOAD_FILE")
    private byte[] uploadFile;
    @OneToMany(mappedBy = "documentId")
    private List<PrmsForeignExchange> prmsForeignExchangeList;
    @OneToMany(mappedBy = "documentId", fetch = FetchType.LAZY)
    private List<PrmsLcRigistration> prmsLcRigistrationList;
    @OneToMany(mappedBy = "documentId")
    private List<PrmsLcRigistrationAmend> prmsLcRigistrationAmendList;
    @OneToMany(mappedBy = "documentId")
    private List<PrmsPurchaseRequest> prmsPurchaseRequestList;
    @OneToMany(mappedBy = "fileRefNumber")
    private List<PrmsInsuranceRequisition> prmsInsuranceRequisitionList;
    @OneToMany(mappedBy = "attachmentRefNumber")
    private List<PrmsBankClearance> prmsBankClearancesList;
    @OneToMany(mappedBy = "fileReferenceNumber")
    private List<PrmsImportShippingInstruct> prmsImportShippingInstructLists;
    @OneToMany(mappedBy = "documentId")
    private List<PrmsSuppSpecification> prmsSuppSpecificationList;
    @OneToMany(mappedBy = "documentId")
    private List<PrmsAward> prmsAwardList;
    @OneToMany(mappedBy = "documentId")
    private List<PrmsBidOpeningCheckList> prmsBidOpeningCheckListLst;
    @OneToMany(mappedBy = "documentId")
    private List<PrmsQuotation> prmsQuotationLst;
    @OneToMany(mappedBy = "documentId")
    private List<PrmsBid> prmsBidList;

    @OneToMany(mappedBy = "documentId")
    private List<PrmsBidAmend> prmsBidAmendList;
    @OneToMany(mappedBy = "documentId")
    private List<PrmsFinancialEvaluation> prmsFinancialEvaluationList;
    @OneToMany(mappedBy = "documentId")
    private List<PrmsThechnicalEvaluation> prmsThechnicalEvaluationList;
    @OneToMany(mappedBy = "documentupId")
    private List<PrmsContract> prmsContractList;
    @OneToMany(mappedBy = "documentupId")
    private List<PrmsContractAmendment> prmsContractAmendmentList;

    public PrmsLuDmArchive() {
    }

    public PrmsLuDmArchive(BigDecimal documentId) {
        this.documentId = documentId;
    }

    public BigDecimal getDocumentId() {
        return documentId;
    }

    public void setDocumentId(BigDecimal documentId) {
        this.documentId = documentId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public byte[] getUploadFile() {
        return uploadFile;
    }

    public void setUploadFile(byte[] uploadFile) {
        this.uploadFile = uploadFile;
    }

    @XmlTransient
    public List<PrmsForeignExchange> getPrmsForeignExchangeList() {
        if (prmsForeignExchangeList == null) {
            prmsForeignExchangeList = new ArrayList<>();
        }
        return prmsForeignExchangeList;
    }

    public void setPrmsForeignExchangeList(List<PrmsForeignExchange> prmsForeignExchangeList) {
        this.prmsForeignExchangeList = prmsForeignExchangeList;
    }

    @XmlTransient
    public List<PrmsLcRigistration> getPrmsLcRigistrationList() {
        if (prmsLcRigistrationList == null) {
            prmsLcRigistrationList = new ArrayList<>();
        }
        return prmsLcRigistrationList;
    }

    public void setPrmsLcRigistrationList(List<PrmsLcRigistration> prmsLcRigistrationList) {
        this.prmsLcRigistrationList = prmsLcRigistrationList;
    }

    @XmlTransient
    public List<PrmsPurchaseRequest> getPrmsPurchaseRequestList() {
        if (prmsPurchaseRequestList == null) {
            prmsPurchaseRequestList = new ArrayList<>();
        }
        return prmsPurchaseRequestList;
    }

    public void setPrmsPurchaseRequestList(List<PrmsPurchaseRequest> prmsPurchaseRequestList) {
        this.prmsPurchaseRequestList = prmsPurchaseRequestList;
    }

    @XmlTransient
    public List<PrmsInsuranceRequisition> getPrmsInsuranceRequisitionList() {
        if (prmsInsuranceRequisitionList == null) {
            prmsInsuranceRequisitionList = new ArrayList<>();
        }
        return prmsInsuranceRequisitionList;
    }

    public void setPrmsInsuranceRequisitionList(List<PrmsInsuranceRequisition> prmsInsuranceRequisitionList) {
        this.prmsInsuranceRequisitionList = prmsInsuranceRequisitionList;
    }

    @XmlTransient
    public List<PrmsBankClearance> getPrmsBankClearancesList() {
        if (prmsBankClearancesList == null) {
            prmsBankClearancesList = new ArrayList<>();
        }
        return prmsBankClearancesList;
    }

    public void setPrmsBankClearancesList(List<PrmsBankClearance> prmsBankClearancesList) {
        this.prmsBankClearancesList = prmsBankClearancesList;
    }

    @XmlTransient
    public List<PrmsImportShippingInstruct> getPrmsImportShippingInstructLists() {
        if (prmsImportShippingInstructLists == null) {
            prmsImportShippingInstructLists = new ArrayList<>();
        }
        return prmsImportShippingInstructLists;
    }

    public void setPrmsImportShippingInstructLists(List<PrmsImportShippingInstruct> prmsImportShippingInstructLists) {
        this.prmsImportShippingInstructLists = prmsImportShippingInstructLists;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (documentId != null ? documentId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PrmsLuDmArchive)) {
            return false;
        }
        PrmsLuDmArchive other = (PrmsLuDmArchive) object;
        if ((this.documentId == null && other.documentId != null) || (this.documentId != null && !this.documentId.equals(other.documentId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.commonApplications.entity.PrmsLuDmArchive[ documentId=" + documentId + " ]";
    }

    /**
     * @return the prmsLcRigistrationAmendList
     */
    @XmlTransient

    public List<PrmsLcRigistrationAmend> getPrmsLcRigistrationAmendList() {
        if (prmsLcRigistrationAmendList == null) {
            prmsLcRigistrationAmendList = new ArrayList<>();
        }
        return prmsLcRigistrationAmendList;
    }

    /**
     * @param prmsLcRigistrationAmendList the prmsLcRigistrationAmendList to set
     */
    public void setPrmsLcRigistrationAmendList(List<PrmsLcRigistrationAmend> prmsLcRigistrationAmendList) {
        this.prmsLcRigistrationAmendList = prmsLcRigistrationAmendList;
    }

    @XmlTransient

    public List<PrmsSuppSpecification> getPrmsSuppSpecificationList() {
        if (prmsSuppSpecificationList == null) {
            prmsSuppSpecificationList = new ArrayList<>();
        }
        return prmsSuppSpecificationList;
    }

    public void setPrmsSuppSpecificationList(List<PrmsSuppSpecification> prmsSuppSpecificationList) {
        this.prmsSuppSpecificationList = prmsSuppSpecificationList;
    }

    @XmlTransient

    public List<PrmsAward> getPrmsAwardList() {
        if (prmsAwardList == null) {
            prmsAwardList = new ArrayList<>();
        }
        return prmsAwardList;
    }

    public void setPrmsAwardList(List<PrmsAward> prmsAwardList) {
        this.prmsAwardList = prmsAwardList;
    }

    @XmlTransient

    public List<PrmsBidOpeningCheckList> getPrmsBidOpeningCheckListLst() {
        if (prmsBidOpeningCheckListLst == null) {
            prmsBidOpeningCheckListLst = new ArrayList<>();
        }
        return prmsBidOpeningCheckListLst;
    }

    public void setPrmsBidOpeningCheckListLst(List<PrmsBidOpeningCheckList> prmsBidOpeningCheckListLst) {
        this.prmsBidOpeningCheckListLst = prmsBidOpeningCheckListLst;
    }

    @XmlTransient
    public List<PrmsQuotation> getPrmsQuotationLst() {
        if (prmsQuotationLst == null) {
            prmsQuotationLst = new ArrayList<>();
        }
        return prmsQuotationLst;
    }

    public void setPrmsQuotationLst(List<PrmsQuotation> prmsQuotationLst) {
        this.prmsQuotationLst = prmsQuotationLst;
    }

    @XmlTransient
    public List<PrmsBid> getPrmsBidList() {
        if (prmsBidList == null) {
            prmsBidList = new ArrayList<>();
        }
        return prmsBidList;
    }

    public void setPrmsBidList(List<PrmsBid> prmsBidList) {
        this.prmsBidList = prmsBidList;
    }

    @XmlTransient
    public List<PrmsBidAmend> getPrmsBidAmendList() {
        if (prmsBidAmendList == null) {
            prmsBidAmendList = new ArrayList<>();
        }
        return prmsBidAmendList;
    }

    public void setPrmsBidAmendList(List<PrmsBidAmend> prmsBidAmendList) {
        this.prmsBidAmendList = prmsBidAmendList;
    }

    @XmlTransient
    public List<PrmsThechnicalEvaluation> getPrmsThechnicalEvaluationList() {
        if (prmsThechnicalEvaluationList == null) {
            prmsThechnicalEvaluationList = new ArrayList<>();
        }
        return prmsThechnicalEvaluationList;
    }

    public void setPrmsThechnicalEvaluationList(List<PrmsThechnicalEvaluation> prmsThechnicalEvaluationList) {
        this.prmsThechnicalEvaluationList = prmsThechnicalEvaluationList;
    }

    @XmlTransient
    public List<PrmsFinancialEvaluation> getPrmsFinancialEvaluationList() {
        if (prmsFinancialEvaluationList == null) {
            prmsFinancialEvaluationList = new ArrayList<>();
        }
        return prmsFinancialEvaluationList;
    }

    public void setPrmsFinancialEvaluationList(List<PrmsFinancialEvaluation> prmsFinancialEvaluationList) {
        this.prmsFinancialEvaluationList = prmsFinancialEvaluationList;
    }

    @XmlTransient
    public List<PrmsContract> getPrmsContractList() {
        if (prmsContractList == null) {
            prmsContractList = new ArrayList<>();
        }
        return prmsContractList;
    }

    public void setPrmsContractList(List<PrmsContract> prmsContractList) {
        this.prmsContractList = prmsContractList;
    }

    @XmlTransient
    public List<PrmsContractAmendment> getPrmsContractAmendmentList() {
        if (prmsContractAmendmentList == null) {
            prmsContractAmendmentList = new ArrayList<>();
        }
        return prmsContractAmendmentList;
    }

    public void setPrmsContractAmendmentList(List<PrmsContractAmendment> prmsContractAmendmentList) {
        this.prmsContractAmendmentList = prmsContractAmendmentList;
    }

}
