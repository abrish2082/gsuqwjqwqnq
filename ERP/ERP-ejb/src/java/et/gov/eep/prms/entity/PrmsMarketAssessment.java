/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.entity;

import et.gov.eep.commonApplications.entity.WfPrmsProcessed;
import java.io.Serializable;
import java.util.ArrayList;
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
@Table(name = "PRMS_MARKET_ASSESSMENT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrmsMarketAssessment.findAll", query = "SELECT p FROM PrmsMarketAssessment p"),
    @NamedQuery(name = "PrmsMarketAssessment.findById", query = "SELECT p FROM PrmsMarketAssessment p WHERE p.id = :id"),
    @NamedQuery(name = "PrmsMarketAssessment.findByAutoIncr", query = "SELECT p FROM PrmsMarketAssessment p WHERE p.id = (SELECT Max(p.id)  from PrmsMarketAssessment p)"),
    @NamedQuery(name = "PrmsMarketAssessment.findByMarketAssNo", query = "SELECT p FROM PrmsMarketAssessment p WHERE p.marketAssNo LIKE :marketAssNo"),
    @NamedQuery(name = "PrmsMarketAssessment.findByMarketAssmentReq", query = "SELECT p FROM PrmsMarketAssessment p WHERE p.status=0"),

    @NamedQuery(name = "PrmsMarketAssessment.findByMarket", query = "SELECT p FROM PrmsMarketAssessment p WHERE p.marketAssNo = :market"),
    @NamedQuery(name = "PrmsMarketAssessment.findByMarketAssNos", query = "SELECT p FROM PrmsMarketAssessment p WHERE p.marketAssNo LIKE :marketAssNo and p.preparedby=:preparedby"),
    @NamedQuery(name = "PrmsMarketAssessment.findByRegistrationDate", query = "SELECT p FROM PrmsMarketAssessment p WHERE p.registrationDate = :registrationDate"),
    @NamedQuery(name = "PrmsMarketAssessment.findByPreparedby", query = "SELECT p FROM PrmsMarketAssessment p WHERE p.preparedby = :preparedby"),
    @NamedQuery(name = "PrmsMarketAssessment.findByRemark", query = "SELECT p FROM PrmsMarketAssessment p WHERE p.remark = :remark"),
    @NamedQuery(name = "PrmsMarketAssessment.findByApprovedDate", query = "SELECT p FROM PrmsMarketAssessment p WHERE p.approvedDate = :approvedDate"),
    @NamedQuery(name = "PrmsMarketAssessment.findByApprovedby", query = "SELECT p FROM PrmsMarketAssessment p WHERE p.approvedby = :approvedby")})
public class PrmsMarketAssessment implements Serializable {

    @OneToMany(mappedBy = "marketId", fetch = FetchType.LAZY)
    private List<WfPrmsProcessed> wfPrmsProcessedLists;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "marketAssmntId", fetch = FetchType.LAZY)
    private List<PrmsMarketAssmntService> prmsMarketAssmntServiceList = new ArrayList<>();

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "ID", nullable = false, length = 20)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EEP_MRKETASESMTINFO_SEQ_GENERATOR")
    @SequenceGenerator(name = "EEP_MRKETASESMTINFO_SEQ_GENERATOR", sequenceName = "EEP_MRKETASESMTINFO_SEQ", allocationSize = 1)
    private String id;
    @Size(max = 20)
    @Column(name = "MARKET_ASS_NO", length = 20)
    private String marketAssNo;

    @Column(name = "REGISTRATION_DATE")
    @Temporal(TemporalType.DATE)
    private Date registrationDate;
    @Column(name = "PREPAREDBY")
    private Integer preparedby;
    @Size(max = 20)
    @Column(name = "PURCHASE_TYPE", length = 20)
    private String purchaseType;
    @Size(max = 200)
    @Column(name = "REMARK", length = 200)
    private String remark;
    @Column(name = "APPROVED_DATE")
    @Temporal(TemporalType.DATE)
    private Date approvedDate;
    @Size(max = 20)
    @Column(name = "APPROVEDBY", length = 20)
    private String approvedby;
    @Column(name = "STATUS")
    private Integer status;

    // <editor-fold defaultstate="collapsed" desc="Declare Variable for Dynamic Searching">
    @Transient
    private String columnName;

    @Transient
    private String columnValue;
    // </editor-fold>

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "marketAssmntId", fetch = FetchType.LAZY)
    private List<PrmsMarketAssessmentDetail> prmsMarketAssessmentDetailList = new ArrayList<>();

    @JoinColumn(name = "LU_SUPPLIER_NAME_ID", referencedColumnName = "ID")
    @ManyToOne
    private PrmsSupplyProfile luSupplierNameId;

    /**
     *
     */
    public PrmsMarketAssessment() {
    }

    /**
     *
     * @param id
     */
    public PrmsMarketAssessment(String id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public String getMarketAssNo() {
        return marketAssNo;
    }

    /**
     *
     * @param marketAssNo
     */
    public void setMarketAssNo(String marketAssNo) {
        this.marketAssNo = marketAssNo;
    }

    /**
     *
     * @return
     */
    public Date getRegistrationDate() {
        return registrationDate;
    }

    /**
     *
     * @param registrationDate
     */
    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    /**
     *
     * @return
     */
    public Integer getPreparedby() {
        return preparedby;
    }

    /**
     *
     * @param preparedby
     */
    public void setPreparedby(Integer preparedby) {
        this.preparedby = preparedby;
    }

    /**
     *
     * @return
     */
    public String getRemark() {
        return remark;
    }

    /**
     *
     * @param remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     *
     * @return
     */
    public Date getApprovedDate() {
        return approvedDate;
    }

    /**
     *
     * @param approvedDate
     */
    public void setApprovedDate(Date approvedDate) {
        this.approvedDate = approvedDate;
    }

    public String getPurchaseType() {
        return purchaseType;
    }

    public void setPurchaseType(String purchaseType) {
        this.purchaseType = purchaseType;
    }

    /**
     *
     * @return
     */
    public String getApprovedby() {
        return approvedby;
    }

    /**
     *
     * @param approvedby
     */
    public void setApprovedby(String approvedby) {
        this.approvedby = approvedby;
    }

    /**
     *
     * @return
     */
    @XmlTransient
    public List<PrmsMarketAssessmentDetail> getPrmsMarketAssessmentDetailList() {

        return prmsMarketAssessmentDetailList;
    }

    /**
     *
     * @param prmsMarketAssessmentDetailList
     */
    public void setPrmsMarketAssessmentDetailList(List<PrmsMarketAssessmentDetail> prmsMarketAssessmentDetailList) {
        this.prmsMarketAssessmentDetailList = prmsMarketAssessmentDetailList;
    }

    /**
     *
     * @param prmsMarketAssessmentDetail
     */
    public void add(PrmsMarketAssessmentDetail prmsMarketAssessmentDetail) {

        if (prmsMarketAssessmentDetail.getMarketAssmntId() != this) {
            this.prmsMarketAssessmentDetailList.add(prmsMarketAssessmentDetail);
            prmsMarketAssessmentDetail.setMarketAssmntId(this);
        }
    }

    /**
     *
     * @return
     */
    public PrmsSupplyProfile getLuSupplierNameId() {
        return luSupplierNameId;
    }

    /**
     *
     * @param luSupplierNameId
     */
    public void setLuSupplierNameId(PrmsSupplyProfile luSupplierNameId) {
        this.luSupplierNameId = luSupplierNameId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
    // <editor-fold defaultstate="collapsed" desc="getter and setter declared Variables for Dynamic Searching">

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
// </editor-fold>

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PrmsMarketAssessment)) {
            return false;
        }
        PrmsMarketAssessment other = (PrmsMarketAssessment) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return marketAssNo;
    }

    @XmlTransient
    public List<PrmsMarketAssmntService> getPrmsMarketAssmntServiceList() {
        return prmsMarketAssmntServiceList;
    }

    public void setPrmsMarketAssmntServiceList(List<PrmsMarketAssmntService> prmsMarketAssmntServiceList) {
        this.prmsMarketAssmntServiceList = prmsMarketAssmntServiceList;
    }

    public void add(PrmsMarketAssmntService prmsMarketAssmntService) {

        if (prmsMarketAssmntService.getMarketAssmntId() != this) {
            this.prmsMarketAssmntServiceList.add(prmsMarketAssmntService);
            prmsMarketAssmntService.setMarketAssmntId(this);
        }
    }

    @XmlTransient
    public List<WfPrmsProcessed> getWfPrmsProcessedLists() {
        if (wfPrmsProcessedLists == null) {
            wfPrmsProcessedLists = new ArrayList<>();
        }
        return wfPrmsProcessedLists;
    }

    public void setWfPrmsProcessedLists(List<WfPrmsProcessed> wfPrmsProcessedLists) {
        this.wfPrmsProcessedLists = wfPrmsProcessedLists;
    }

}
