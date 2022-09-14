/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.entity;

import et.gov.eep.mms.entity.MmsLuWareHouse;
import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "PRMS_CONTAINER_AGREEMENT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrmsContainerAgreement.findAll", query = "SELECT p FROM PrmsContainerAgreement p"),
    @NamedQuery(name = "PrmsContainerAgreement.findByContainerId", query = "SELECT p FROM PrmsContainerAgreement p WHERE p.containerId = :containerId"),
    @NamedQuery(name = "PrmsContainerAgreement.findByContainerNo", query = "SELECT p FROM PrmsContainerAgreement p WHERE p.containerNo = :containerNo"),
    @NamedQuery(name = "PrmsContainerAgreement.findByReqForApprval", query = "SELECT p FROM PrmsContainerAgreement p WHERE p.status=0"),
    @NamedQuery(name = "PrmsContainerAgreement.findByFormNo", query = "SELECT p FROM PrmsContainerAgreement p WHERE p.formNo = :formNo"),
    @NamedQuery(name = "PrmsContainerAgreement.findByFormListNo", query = "SELECT p FROM PrmsContainerAgreement p WHERE p.formNo = :formNo"),
    @NamedQuery(name = "PrmsContainerAgreement.searchfindByFormNo", query = "SELECT p FROM PrmsContainerAgreement p WHERE p.formNo LIKE :formNo and p.preparedBy=:preparedBy"),
    @NamedQuery(name = "PrmsContainerAgreement.findByFormNumber", query = "SELECT p FROM PrmsContainerAgreement p WHERE p.formNo LIKE :formNo AND p.revisionNo LIKE :revisionNo"),
    @NamedQuery(name = "PrmsContainerAgreement.searchByFormNo", query = "SELECT p FROM PrmsContainerAgreement p WHERE p.containerNo LIKE :containerNo"),
    @NamedQuery(name = "PrmsContainerAgreement.findByMaxFormNo", query = "SELECT p FROM PrmsContainerAgreement p WHERE p.containerId = (SELECT Max(c.containerId)  from PrmsContainerAgreement c)"),
    @NamedQuery(name = "PrmsContainerAgreement.findByImporter", query = "SELECT p FROM PrmsContainerAgreement p WHERE p.importer = :importer"),
    @NamedQuery(name = "PrmsContainerAgreement.findByUnderBillOfLoadingNo", query = "SELECT p FROM PrmsContainerAgreement p WHERE p.underBillOfLoadingNo = :underBillOfLoadingNo"),
    @NamedQuery(name = "PrmsContainerAgreement.findByRevisionNo", query = "SELECT p FROM PrmsContainerAgreement p WHERE p.revisionNo = :revisionNo"),
    @NamedQuery(name = "PrmsContainerAgreement.findByVessel", query = "SELECT p FROM PrmsContainerAgreement p WHERE p.vessel = :vessel"),
    @NamedQuery(name = "PrmsContainerAgreement.findByPreparedBy", query = "SELECT p FROM PrmsContainerAgreement p WHERE p.preparedBy = :preparedBy"),
    @NamedQuery(name = "PrmsContainerAgreement.findByPreparedDate", query = "SELECT p FROM PrmsContainerAgreement p WHERE p.preparedDate = :preparedDate"),
    @NamedQuery(name = "PrmsContainerAgreement.findByProcessedOn", query = "SELECT p FROM PrmsImportShippingInstruct p WHERE p.processedOn=:processedOn"),
    @NamedQuery(name = "PrmsContainerAgreement.findByApprovedBy", query = "SELECT p FROM PrmsContainerAgreement p WHERE p.approvedBy = :approvedBy"),
    @NamedQuery(name = "PrmsContainerAgreement.findByApprovedDate", query = "SELECT p FROM PrmsContainerAgreement p WHERE p.approvedDate = :approvedDate"),
    @NamedQuery(name = "PrmsContainerAgreement.findByAttachment", query = "SELECT p FROM PrmsContainerAgreement p WHERE p.attachment = :attachment"),
    @NamedQuery(name = "PrmsContainerAgreement.findByContEntranceDate", query = "SELECT p FROM PrmsContainerAgreement p WHERE p.contEntranceDate = :contEntranceDate"),
    @NamedQuery(name = "PrmsContainerAgreement.findByContReturnDate", query = "SELECT p FROM PrmsContainerAgreement p WHERE p.contReturnDate = :contReturnDate"),
    @NamedQuery(name = "PrmsContainerAgreement.findByPlateNumber", query = "SELECT p FROM PrmsContainerAgreement p WHERE p.plateNumber = :plateNumber"),
    @NamedQuery(name = "PrmsContainerAgreement.findByStatus", query = "SELECT p FROM PrmsContainerAgreement p WHERE p.status=:status")})
public class PrmsContainerAgreement implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRMS_CONTAINER_AGREEMENT_SEQ")
    @SequenceGenerator(name = "PRMS_CONTAINER_AGREEMENT_SEQ", sequenceName = "PRMS_CONTAINER_AGREEMENT_SEQ", allocationSize = 1)
    @Column(name = "CONTAINER_ID", nullable = false, precision = 0, scale = -127)
    private BigDecimal containerId;
    @Size(max = 100)
    @Column(name = "CONTAINER_NO", length = 100)
    private String containerNo;
    @Size(max = 50)
    @Column(name = "FORM_NO", length = 50)
    private String formNo;
    @Size(max = 100)
    @Column(name = "IMPORTER", length = 100)
    private String importer;
    @Size(max = 75)
    @Column(name = "UNDER_BILL_OF_LOADING_NO", length = 75)
    private String underBillOfLoadingNo;
    @Size(max = 50)
    @Column(name = "REVISION_NO", length = 50)
    private String revisionNo;
    @Size(max = 50)
    @Column(name = "VESSEL", length = 50)
    private String vessel;
    @Column(name = "PREPARED_BY", length = 30)
    private Integer preparedBy;
    @Column(name = "PREPARED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date preparedDate;
    @Size(max = 50)
    @Column(name = "PROCESSED_ON")
    private String processedOn;
    @Size(max = 30)
    @Column(name = "APPROVED_BY", length = 30)
    private String approvedBy;
    @Column(name = "APPROVED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date approvedDate;
    @Size(max = 100)
    @Column(name = "ATTACHMENT", length = 100)
    private String attachment;
    @Column(name = "CONT_ENTRANCE_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date contEntranceDate;
    @Column(name = "CONT_RETURN_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date contReturnDate;
    @Size(max = 35)
    @Column(name = "PLATE_NUMBER", length = 35)
    private String plateNumber;
    @Column(name = "STATUS")
    private Integer status;
    @JoinColumn(name = "WAREHOUSE_LOCATION", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private MmsLuWareHouse warehouseLocation;
    @JoinColumn(name = "DRY_PORT", referencedColumnName = "PORT_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrmsPortEntry dryPort;
    @JoinColumn(name = "PORTFK_ID", referencedColumnName = "PORT_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrmsPortEntry portfkId;
    @JoinColumn(name = "PORT_TO", referencedColumnName = "PORT_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrmsPortEntry portTo;
    @JoinColumn(name = "PORT_VOYAGE", referencedColumnName = "PORT_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrmsPortEntry portVoyage;
    @JoinColumn(name = "GOOD_ENTRANCE_ID", referencedColumnName = "ID")
    @ManyToOne
    private PrmsGoodsEntrance goodEntranceId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "containerId")
    private List<WfMmsProcessed> wfMmsProcessedList;
    @Transient
    private String columnName;
    @Transient
    private String columnValue;

    public PrmsContainerAgreement() {
    }

    public PrmsContainerAgreement(BigDecimal containerId) {
        this.containerId = containerId;
    }

    public BigDecimal getContainerId() {
        return containerId;
    }

    public void setContainerId(BigDecimal containerId) {
        this.containerId = containerId;
    }

    public String getContainerNo() {
        return containerNo;
    }

    public void setContainerNo(String containerNo) {
        this.containerNo = containerNo;
    }

    public String getFormNo() {
        return formNo;
    }

    public void setFormNo(String formNo) {
        this.formNo = formNo;
    }

    public String getImporter() {
        return importer;
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

    public void setImporter(String importer) {
        this.importer = importer;
    }

    public String getUnderBillOfLoadingNo() {
        return underBillOfLoadingNo;
    }

    public void setUnderBillOfLoadingNo(String underBillOfLoadingNo) {
        this.underBillOfLoadingNo = underBillOfLoadingNo;
    }

    public String getRevisionNo() {
        return revisionNo;
    }

    public void setRevisionNo(String revisionNo) {
        this.revisionNo = revisionNo;
    }

    public String getVessel() {
        return vessel;
    }

    public void setVessel(String vessel) {
        this.vessel = vessel;
    }

    public Integer getPreparedBy() {
        return preparedBy;
    }

    public void setPreparedBy(Integer preparedBy) {
        this.preparedBy = preparedBy;
    }

    public Date getPreparedDate() {
        return preparedDate;
    }

    public void setPreparedDate(Date preparedDate) {
        this.preparedDate = preparedDate;
    }

    public String getProcessedOn() {
        return processedOn;
    }

    public void setProcessedOn(String processedOn) {
        this.processedOn = processedOn;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    public Date getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(Date approvedDate) {
        this.approvedDate = approvedDate;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public Date getContEntranceDate() {
        return contEntranceDate;
    }

    public void setContEntranceDate(Date contEntranceDate) {
        this.contEntranceDate = contEntranceDate;
    }

    public Date getContReturnDate() {
        return contReturnDate;
    }

    public void setContReturnDate(Date contReturnDate) {
        this.contReturnDate = contReturnDate;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public MmsLuWareHouse getWarehouseLocation() {
        return warehouseLocation;
    }

    public void setWarehouseLocation(MmsLuWareHouse warehouseLocation) {
        this.warehouseLocation = warehouseLocation;
    }

    public PrmsPortEntry getDryPort() {
        return dryPort;
    }

    public void setDryPort(PrmsPortEntry dryPort) {
        this.dryPort = dryPort;
    }

    public PrmsPortEntry getPortfkId() {
        return portfkId;
    }

    public void setPortfkId(PrmsPortEntry portfkId) {
        this.portfkId = portfkId;
    }

    public PrmsPortEntry getPortTo() {
        return portTo;
    }

    public void setPortTo(PrmsPortEntry portTo) {
        this.portTo = portTo;
    }

    public PrmsPortEntry getPortVoyage() {
        return portVoyage;
    }

    public void setPortVoyage(PrmsPortEntry portVoyage) {
        this.portVoyage = portVoyage;
    }

    public PrmsGoodsEntrance getGoodEntranceId() {
        if (goodEntranceId == null) {
            goodEntranceId = new PrmsGoodsEntrance();
        }
        return goodEntranceId;
    }

    public void setGoodEntranceId(PrmsGoodsEntrance goodEntranceId) {
        this.goodEntranceId = goodEntranceId;
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
        hash += (containerId != null ? containerId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PrmsContainerAgreement)) {
            return false;
        }
        PrmsContainerAgreement other = (PrmsContainerAgreement) object;
        if ((this.containerId == null && other.containerId != null) || (this.containerId != null && !this.containerId.equals(other.containerId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.prms.entity.PrmsContainerAgreement[ containerId=" + containerId + " ]";
    }

}
