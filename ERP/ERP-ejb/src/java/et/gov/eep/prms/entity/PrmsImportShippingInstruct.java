/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.entity;

import et.gov.eep.commonApplications.entity.PrmsLuDmArchive;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import et.gov.eep.commonApplications.entity.WfMmsProcessed;
import java.util.List;
import java.util.ArrayList;
import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author user
 */
@Entity
@Table(name = "PRMS_IMPORT_SHIPPING_INSTRUCT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrmsImportShippingInstruct.findAll", query = "SELECT p FROM PrmsImportShippingInstruct p"),
    @NamedQuery(name = "PrmsImportShippingInstruct.findByIsiNo", query = "SELECT p FROM PrmsImportShippingInstruct p WHERE p.isiNo = :isiNo"),
    @NamedQuery(name = "PrmsImportShippingInstruct.findByIsiNoLike", query = "SELECT p FROM PrmsImportShippingInstruct p WHERE p.isiNo like :isiNo and p.preparedBy=:preparedBy"),
    //@NamedQuery(name = "PrmsImportShippingInstruct.findByFromEep", query = "SELECT p FROM PrmsImportShippingInstruct p WHERE p.fromEep = :fromEep"),
    @NamedQuery(name = "PrmsImportShippingInstruct.findByReqForApprval", query = "SELECT p FROM PrmsImportShippingInstruct p WHERE p.status=0"),
    @NamedQuery(name = "PrmsImportShippingInstruct.findByAddress", query = "SELECT p FROM PrmsImportShippingInstruct p WHERE p.address = :address"),
    @NamedQuery(name = "PrmsImportShippingInstruct.findByInstructionToClear", query = "SELECT p FROM PrmsImportShippingInstruct p WHERE p.instructionToClear = :instructionToClear"),
    @NamedQuery(name = "PrmsImportShippingInstruct.findByDischargeAt", query = "SELECT p FROM PrmsImportShippingInstruct p WHERE p.dischargeAt = :dischargeAt"),
    @NamedQuery(name = "PrmsImportShippingInstruct.findByPerMv", query = "SELECT p FROM PrmsImportShippingInstruct p WHERE p.perMv = :perMv"),
    @NamedQuery(name = "PrmsImportShippingInstruct.findByPreparedDate", query = "SELECT p FROM PrmsImportShippingInstruct p WHERE p.preparedDate = :preparedDate"),
    @NamedQuery(name = "PrmsImportShippingInstruct.findByAcceptedDate", query = "SELECT p FROM PrmsImportShippingInstruct p WHERE p.acceptedDate = :acceptedDate"),
    @NamedQuery(name = "PrmsImportShippingInstruct.findBySupplierAddress", query = "SELECT p FROM PrmsImportShippingInstruct p WHERE p.supplierAddress = :supplierAddress"),
    @NamedQuery(name = "PrmsImportShippingInstruct.findByOn", query = "SELECT p FROM PrmsImportShippingInstruct p WHERE p.on = :on"),
    @NamedQuery(name = "PrmsImportShippingInstruct.findBySupplierInvoice", query = "SELECT p FROM PrmsImportShippingInstruct p WHERE p.supplierInvoice = :supplierInvoice"),
    @NamedQuery(name = "PrmsImportShippingInstruct.findByOblDutyEndorseToYou", query = "SELECT p FROM PrmsImportShippingInstruct p WHERE p.oblDutyEndorseToYou = :oblDutyEndorseToYou"),
    @NamedQuery(name = "PrmsImportShippingInstruct.findByInsuranceDebitNote", query = "SELECT p FROM PrmsImportShippingInstruct p WHERE p.insuranceDebitNote = :insuranceDebitNote"),
    @NamedQuery(name = "PrmsImportShippingInstruct.findByCertificateOfOriginNo", query = "SELECT p FROM PrmsImportShippingInstruct p WHERE p.certificateOfOriginNo = :certificateOfOriginNo"),
    @NamedQuery(name = "PrmsImportShippingInstruct.findByBankPermitNo", query = "SELECT p FROM PrmsImportShippingInstruct p WHERE p.bankPermitNo = :bankPermitNo"),
    @NamedQuery(name = "PrmsImportShippingInstruct.findByDutyExemotionLetterNo", query = "SELECT p FROM PrmsImportShippingInstruct p WHERE p.dutyExemotionLetterNo = :dutyExemotionLetterNo"),
    @NamedQuery(name = "PrmsImportShippingInstruct.findByPackageList", query = "SELECT p FROM PrmsImportShippingInstruct p WHERE p.packageList = :packageList"),
    @NamedQuery(name = "PrmsImportShippingInstruct.findByOceanFreightInvoiceNo", query = "SELECT p FROM PrmsImportShippingInstruct p WHERE p.oceanFreightInvoiceNo = :oceanFreightInvoiceNo"),
    @NamedQuery(name = "PrmsImportShippingInstruct.findByReliefOrGiftCertificateNo", query = "SELECT p FROM PrmsImportShippingInstruct p WHERE p.reliefOrGiftCertificateNo = :reliefOrGiftCertificateNo"),
    @NamedQuery(name = "PrmsImportShippingInstruct.findByImportLicenseNo", query = "SELECT p FROM PrmsImportShippingInstruct p WHERE p.importLicenseNo = :importLicenseNo"),
    @NamedQuery(name = "PrmsImportShippingInstruct.findByBondOrLetterOfGaranteeNo", query = "SELECT p FROM PrmsImportShippingInstruct p WHERE p.bondOrLetterOfGaranteeNo = :bondOrLetterOfGaranteeNo"),
    @NamedQuery(name = "PrmsImportShippingInstruct.findByContainerRelease", query = "SELECT p FROM PrmsImportShippingInstruct p WHERE p.containerRelease = :containerRelease"),
    @NamedQuery(name = "PrmsImportShippingInstruct.findByOtherDocument", query = "SELECT p FROM PrmsImportShippingInstruct p WHERE p.otherDocument = :otherDocument"),
    @NamedQuery(name = "PrmsImportShippingInstruct.findByCustomDutyToBeAssesedAs", query = "SELECT p FROM PrmsImportShippingInstruct p WHERE p.customDutyToBeAssesedAs = :customDutyToBeAssesedAs"),
    @NamedQuery(name = "PrmsImportShippingInstruct.findByPleaseSecure", query = "SELECT p FROM PrmsImportShippingInstruct p WHERE p.pleaseSecure = :pleaseSecure"),
    @NamedQuery(name = "PrmsImportShippingInstruct.findByGoodsAreToBeLoaded", query = "SELECT p FROM PrmsImportShippingInstruct p WHERE p.goodsAreToBeLoaded = :goodsAreToBeLoaded"),
    @NamedQuery(name = "PrmsImportShippingInstruct.findByTransportedBy", query = "SELECT p FROM PrmsImportShippingInstruct p WHERE p.transportedBy = :transportedBy"),
    @NamedQuery(name = "PrmsImportShippingInstruct.findByAaCustoms", query = "SELECT p FROM PrmsImportShippingInstruct p WHERE p.aaCustoms = :aaCustoms"),
    @NamedQuery(name = "PrmsImportShippingInstruct.findByPreparedBy", query = "SELECT p FROM PrmsImportShippingInstruct p WHERE p.preparedBy = :preparedBy"),
    @NamedQuery(name = "PrmsImportShippingInstruct.findByAcceptedBy", query = "SELECT p FROM PrmsImportShippingInstruct p WHERE p.acceptedBy = :acceptedBy"),
    @NamedQuery(name = "PrmsImportShippingInstruct.findBySignature", query = "SELECT p FROM PrmsImportShippingInstruct p WHERE p.signature = :signature"),
    @NamedQuery(name = "PrmsImportShippingInstruct.findByRemark", query = "SELECT p FROM PrmsImportShippingInstruct p WHERE p.remark = :remark"),
    @NamedQuery(name = "PrmsImportShippingInstruct.findByBl", query = "SELECT p FROM PrmsImportShippingInstruct p WHERE p.bl = :bl"),
    @NamedQuery(name = "PrmsImportShippingInstruct.findByCity", query = "SELECT p FROM PrmsImportShippingInstruct p WHERE p.city = :city"),
    @NamedQuery(name = "PrmsImportShippingInstruct.findById", query = "SELECT p FROM PrmsImportShippingInstruct p WHERE p.id = :id"),
    @NamedQuery(name = "PrmsImportShippingInstruct.findByMaxId", query = "SELECT p FROM PrmsImportShippingInstruct p WHERE p.id =(SELECT Max(p.id)  from PrmsImportShippingInstruct p)"),
    @NamedQuery(name = "PrmsImportShippingInstruct.findByReceiptNo", query = "SELECT p FROM PrmsImportShippingInstruct p WHERE p.receiptNo = :receiptNo"),
    @NamedQuery(name = "PrmsImportShippingInstruct.findByInCaseOfVehicles", query = "SELECT p FROM PrmsImportShippingInstruct p WHERE p.inCaseOfVehicles = :inCaseOfVehicles"),
    @NamedQuery(name = "PrmsImportShippingInstruct.findByLandFreightInvoiceNo", query = "SELECT p FROM PrmsImportShippingInstruct p WHERE p.landFreightInvoiceNo=:landFreightInvoiceNo"),
    @NamedQuery(name = "PrmsImportShippingInstruct.findByStatus", query = "SELECT p FROM PrmsImportShippingInstruct p WHERE p.status=:status"),
    @NamedQuery(name = "PrmsImportShippingInstruct.fiindByProcessedOn", query = "SELECT p FROM PrmsImportShippingInstruct p WHERE p.processedOn=:processedOn"),
    @NamedQuery(name = "PrmsImportShippingInstruct.findByFileReferenceNumber", query = "SELECT p FROM PrmsImportShippingInstruct p WHERE p.fileReferenceNumber=:fileReferenceNumber")})
public class PrmsImportShippingInstruct implements Serializable {

    @Size(max = 20)
    @Column(name = "IN_CASE_OF_VEHICLES", length = 20)
    private String inCaseOfVehicles;
    @Size(max = 20)
    @Column(name = "RECEIPT_NO", length = 20)
    private String receiptNo;
    private static final long serialVersionUID = 1L;
    @Size(max = 20)
    @Column(name = "ISI_NO", length = 20)
    private String isiNo;
    @Size(max = 100)
    @Column(name = "ADDRESS", length = 100)
    private String address;
    @Size(max = 20)
    @Column(name = "INSTRUCTION_TO_CLEAR", length = 20)
    private String instructionToClear;
    @Size(max = 20)
    @Column(name = "DISCHARGE_AT", length = 20)
    private String dischargeAt;
    @Size(max = 20)
    @Column(name = "PER_MV", length = 20)
    private String perMv;
    @Column(name = "PREPARED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date preparedDate;
    @Column(name = "ACCEPTED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date acceptedDate;
    @Size(max = 50)
    @Column(name = "SUPPLIER_ADDRESS", length = 50)
    private String supplierAddress;
    @Size(max = 20)
    @Column(name = "ON_", length = 20)
    private String on;
    @Size(max = 20)
    @Column(name = "SUPPLIER_INVOICE", length = 20)
    private String supplierInvoice;
    @Size(max = 20)
    @Column(name = "OBL_DUTY_ENDORSE_TO_YOU", length = 20)
    private String oblDutyEndorseToYou;
    @Size(max = 20)
    @Column(name = "INSURANCE_DEBIT_NOTE", length = 20)
    private String insuranceDebitNote;
    @Size(max = 20)
    @Column(name = "CERTIFICATE_OF_ORIGIN_NO", length = 20)
    private String certificateOfOriginNo;
    @Size(max = 20)
    @Column(name = "BANK_PERMIT_NO", length = 20)
    private String bankPermitNo;
    @Size(max = 20)
    @Column(name = "DUTY_EXEMOTION_LETTER_NO", length = 20)
    private String dutyExemotionLetterNo;
    @Size(max = 20)
    @Column(name = "PACKAGE_LIST", length = 20)
    private String packageList;
    @Size(max = 35)
    @Column(name = "OCEAN_FREIGHT_INVOICE_NO", length = 35)
    private String oceanFreightInvoiceNo;
    @Size(max = 35)
    @Column(name = "LAND_FREIGHT_INVOICE_NO", length = 35)
    private String landFreightInvoiceNo;
    @JoinColumn(name = "FILE_REFERENCE_NUMBER", referencedColumnName = "DOCUMENT_ID")
    @ManyToOne
    private PrmsLuDmArchive fileReferenceNumber;
    @Size(max = 20)
    @Column(name = "RELIEF_OR_GIFT_CERTIFICATE_NO", length = 20)
    private String reliefOrGiftCertificateNo;
    @Size(max = 20)
    @Column(name = "IMPORT_LICENSE_NO", length = 20)
    private String importLicenseNo;
    @Size(max = 20)
    @Column(name = "BOND_OR_LETTER_OF_GARANTEE_NO", length = 20)
    private String bondOrLetterOfGaranteeNo;
    @Size(max = 20)
    @Column(name = "CONTAINER_RELEASE", length = 20)
    private String containerRelease;
    @Size(max = 20)
    @Column(name = "OTHER_DOCUMENT", length = 20)
    private String otherDocument;
    @Size(max = 20)
    @Column(name = "CUSTOM_DUTY_TO_BE_ASSESED_AS_", length = 20)
    private String customDutyToBeAssesedAs;
    @Size(max = 20)
    @Column(name = "PLEASE_SECURE", length = 20)
    private String pleaseSecure;
    @Size(max = 20)
    @Column(name = "GOODS_ARE_TO_BE_LOADED", length = 20)
    private String goodsAreToBeLoaded;
    @Size(max = 20)
    @Column(name = "TRANSPORTED_BY", length = 20)
    private String transportedBy;
    @Size(max = 20)
    @Column(name = "AA_CUSTOMS", length = 20)
    private String aaCustoms;

    @Column(name = "PREPARED_BY", length = 20)
    private Integer preparedBy;
    @Size(max = 20)
    @Column(name = "ACCEPTED_BY", length = 20)
    private String acceptedBy;
    @Size(max = 20)
    @Column(name = "SIGNATURE", length = 20)
    private String signature;
    @Size(max = 100)
    @Column(name = "REMARK", length = 100)
    private String remark;
    @Size(max = 20)
    @Column(name = "BL", length = 20)
    private String bl;
    @Size(max = 20)
    @Column(name = "CITY", length = 20)
    private String city;
    @Column(name = "STATUS")
    private Integer status;
    @Size(max = 50)
    @Column(name = "PROCESSED_ON")
    private String processedOn;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "ID", nullable = false, length = 20)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRMS_IMPRT_SHIP_INS_SEQ_GENERATOR")
    @SequenceGenerator(name = "PRMS_IMPRT_SHIP_INS_SEQ_GENERATOR", sequenceName = "PRMS_IMPRT_SHIP_INS_SEQ", allocationSize = 1)
    private String id;
    @JoinColumn(name = "FROM_DEP_ID", referencedColumnName = "DEP_ID")
    @OneToOne(fetch = FetchType.LAZY)
    private HrDepartments fromDepId;
    @JoinColumn(name = "LC_ID", referencedColumnName = "LC_ID")
    @OneToOne(fetch = FetchType.LAZY)
    private PrmsLcRigistration lcId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "isId")
    private List<WfMmsProcessed> wfMmsProcessedList;
   @Transient
    private String columnName;
    @Transient
    private String columnValue;
    public PrmsImportShippingInstruct() {
    }

    public PrmsImportShippingInstruct(String id) {
        this.id = id;
    }

    public String getIsiNo() {
        return isiNo;
    }

    public void setIsiNo(String isiNo) {
        this.isiNo = isiNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getInstructionToClear() {
        return instructionToClear;
    }

    public void setInstructionToClear(String instructionToClear) {
        this.instructionToClear = instructionToClear;
    }

    public String getDischargeAt() {
        return dischargeAt;
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

    public void setDischargeAt(String dischargeAt) {
        this.dischargeAt = dischargeAt;
    }

    public String getPerMv() {
        return perMv;
    }

    public void setPerMv(String perMv) {
        this.perMv = perMv;
    }

    public Date getPreparedDate() {
        return preparedDate;
    }

    public void setPreparedDate(Date preparedDate) {
        this.preparedDate = preparedDate;
    }

    public Date getAcceptedDate() {
        return acceptedDate;
    }

    public void setAcceptedDate(Date acceptedDate) {
        this.acceptedDate = acceptedDate;
    }

    public String getSupplierAddress() {
        return supplierAddress;
    }

    public void setSupplierAddress(String supplierAddress) {
        this.supplierAddress = supplierAddress;
    }

    public String getOn() {
        return on;
    }

    public void setOn(String on) {
        this.on = on;
    }

    public String getSupplierInvoice() {
        return supplierInvoice;
    }

    public void setSupplierInvoice(String supplierInvoice) {
        this.supplierInvoice = supplierInvoice;
    }

    public String getOblDutyEndorseToYou() {
        return oblDutyEndorseToYou;
    }

    public void setOblDutyEndorseToYou(String oblDutyEndorseToYou) {
        this.oblDutyEndorseToYou = oblDutyEndorseToYou;
    }

    public String getInsuranceDebitNote() {
        return insuranceDebitNote;
    }

    public void setInsuranceDebitNote(String insuranceDebitNote) {
        this.insuranceDebitNote = insuranceDebitNote;
    }

    public String getCertificateOfOriginNo() {
        return certificateOfOriginNo;
    }

    public void setCertificateOfOriginNo(String certificateOfOriginNo) {
        this.certificateOfOriginNo = certificateOfOriginNo;
    }

    public String getBankPermitNo() {
        return bankPermitNo;
    }

    public void setBankPermitNo(String bankPermitNo) {
        this.bankPermitNo = bankPermitNo;
    }

    public String getDutyExemotionLetterNo() {
        return dutyExemotionLetterNo;
    }

    public void setDutyExemotionLetterNo(String dutyExemotionLetterNo) {
        this.dutyExemotionLetterNo = dutyExemotionLetterNo;
    }

    public String getPackageList() {
        return packageList;
    }

    public void setPackageList(String packageList) {
        this.packageList = packageList;
    }

    public String getOceanFreightInvoiceNo() {
        return oceanFreightInvoiceNo;
    }

    public void setOceanFreightInvoiceNo(String oceanFreightInvoiceNo) {
        this.oceanFreightInvoiceNo = oceanFreightInvoiceNo;
    }

    public String getLandFreightInvoiceNo() {
        return landFreightInvoiceNo;
    }

    public void setLandFreightInvoiceNo(String landFreightInvoiceNo) {
        this.landFreightInvoiceNo = landFreightInvoiceNo;
    }

    public PrmsLuDmArchive getFileReferenceNumber() {
        return fileReferenceNumber;
    }

    public void setFileReferenceNumber(PrmsLuDmArchive fileReferenceNumber) {
        this.fileReferenceNumber = fileReferenceNumber;
    }

    public String getReliefOrGiftCertificateNo() {
        return reliefOrGiftCertificateNo;
    }

    public void setReliefOrGiftCertificateNo(String reliefOrGiftCertificateNo) {
        this.reliefOrGiftCertificateNo = reliefOrGiftCertificateNo;
    }

    public String getImportLicenseNo() {
        return importLicenseNo;
    }

    public void setImportLicenseNo(String importLicenseNo) {
        this.importLicenseNo = importLicenseNo;
    }

    public String getBondOrLetterOfGaranteeNo() {
        return bondOrLetterOfGaranteeNo;
    }

    public void setBondOrLetterOfGaranteeNo(String bondOrLetterOfGaranteeNo) {
        this.bondOrLetterOfGaranteeNo = bondOrLetterOfGaranteeNo;
    }

    public String getContainerRelease() {
        return containerRelease;
    }

    public void setContainerRelease(String containerRelease) {
        this.containerRelease = containerRelease;
    }

    public String getOtherDocument() {
        return otherDocument;
    }

    public void setOtherDocument(String otherDocument) {
        this.otherDocument = otherDocument;
    }

    public String getCustomDutyToBeAssesedAs() {
        return customDutyToBeAssesedAs;
    }

    public void setCustomDutyToBeAssesedAs(String customDutyToBeAssesedAs) {
        this.customDutyToBeAssesedAs = customDutyToBeAssesedAs;
    }

    public String getPleaseSecure() {
        return pleaseSecure;
    }

    public void setPleaseSecure(String pleaseSecure) {
        this.pleaseSecure = pleaseSecure;
    }

    public String getGoodsAreToBeLoaded() {
        return goodsAreToBeLoaded;
    }

    public void setGoodsAreToBeLoaded(String goodsAreToBeLoaded) {
        this.goodsAreToBeLoaded = goodsAreToBeLoaded;
    }

    public String getTransportedBy() {
        return transportedBy;
    }

    public void setTransportedBy(String transportedBy) {
        this.transportedBy = transportedBy;
    }

    public String getAaCustoms() {
        return aaCustoms;
    }

    public void setAaCustoms(String aaCustoms) {
        this.aaCustoms = aaCustoms;
    }

    public Integer getPreparedBy() {
        return preparedBy;
    }

    public void setPreparedBy(Integer preparedBy) {
        this.preparedBy = preparedBy;
    }

    public String getAcceptedBy() {
        return acceptedBy;
    }

    public void setAcceptedBy(String acceptedBy) {
        this.acceptedBy = acceptedBy;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getBl() {
        return bl;
    }

    public void setBl(String bl) {
        this.bl = bl;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getProcessedOn() {
        return processedOn;
    }

    public void setProcessedOn(String processedOn) {
        this.processedOn = processedOn;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public HrDepartments getFromDepId() {
        return fromDepId;
    }

    public void setFromDepId(HrDepartments fromDepId) {
        this.fromDepId = fromDepId;
    }

    public PrmsLcRigistration getLcId() {
        return lcId;
    }

    public void setLcId(PrmsLcRigistration lcId) {
        this.lcId = lcId;
    }

    @XmlTransient
    public List<WfMmsProcessed> getWfMmsProcessedList() {
        if (wfMmsProcessedList == null) {
            wfMmsProcessedList = new ArrayList<>();
        }
        return wfMmsProcessedList;
    }

    public void setWfMmsProcessedList(List<WfMmsProcessed> wfMmsProcessedList) {
        this.wfMmsProcessedList = wfMmsProcessedList;
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
        if (!(object instanceof PrmsImportShippingInstruct)) {
            return false;
        }
        PrmsImportShippingInstruct other = (PrmsImportShippingInstruct) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.prms.entity.PrmsImportShippingInstruct[ id=" + id + " ]";
    }

    public String getReceiptNo() {
        return receiptNo;
    }

    public void setReceiptNo(String receiptNo) {
        this.receiptNo = receiptNo;
    }

    public String getInCaseOfVehicles() {
        return inCaseOfVehicles;
    }

    public void setInCaseOfVehicles(String inCaseOfVehicles) {
        this.inCaseOfVehicles = inCaseOfVehicles;
    }

    public void getIsiNo(String newISINo) {
        this.isiNo = isiNo;
    }

}
