/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.entity;

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
@Table(name = "PRMS_BID_SUBMISSION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrmsBidSubmission.findAll", query = "SELECT p FROM PrmsBidSubmission p"),
    @NamedQuery(name = "PrmsBidSubmission.findByBidSubId", query = "SELECT p FROM PrmsBidSubmission p WHERE p.bidSubId = :bidSubId ORDER BY P.bidSubId ASC"),
    @NamedQuery(name = "PrmsBidSubmission.findByBidId", query = "SELECT p FROM PrmsBidSubmission p WHERE p.bidId.refNo = :bidId"),
    @NamedQuery(name = "PrmsBidSubmission.findByPreparedBy", query = "SELECT p FROM PrmsBidSubmission p WHERE p.preparedBy = :preparedBy"),
    @NamedQuery(name = "PrmsBidSubmission.findAllByStatus", query = "SELECT p FROM PrmsBidSubmission p WHERE p.status=0"),

    @NamedQuery(name = "PrmsBidSubmission.findByMaxBidOfferNum", query = "SELECT p FROM PrmsBidSubmission p WHERE p.bidSubId = (SELECT Max(c.bidSubId)  from PrmsBidSubmission c)"),
    @NamedQuery(name = "PrmsBidSubmission.findByBidSubmNo", query = "SELECT p FROM PrmsBidSubmission p WHERE p.bidSubmNo = :bidSubmNo"),
    @NamedQuery(name = "PrmsBidSubmission.SearchByBidSubmNo", query = "SELECT p FROM PrmsBidSubmission p WHERE p.bidSubmNo LIKE :bidSubmNo  ORDER BY P.bidSubId ASC"),
    @NamedQuery(name = "PrmsBidSubmission.findByDateRigistered", query = "SELECT p FROM PrmsBidSubmission p WHERE p.dateRigistered = :dateRigistered")})
public class PrmsBidSubmission implements Serializable {

    @Column(name = "STATUS")
    private Integer status;
    @Column(name = "CURRENT_STATUS")
    private Integer currentStatus;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BID_SUBMISSION_SEQ")
    @SequenceGenerator(name = "BID_SUBMISSION_SEQ", sequenceName = "BID_SUBMISSION_SEQ", allocationSize = 1)
    @Column(name = "BID_SUB_ID")
    private BigDecimal bidSubId;

    @Column(name = "PREPARED_BY")
    private Integer preparedBy;
    @Size(max = 100)
    @Column(name = "BID_SUBM_NO")
    private String bidSubmNo;
    @Column(name = "DATE_RIGISTERED")
    @Temporal(TemporalType.DATE)
    private Date dateRigistered;
    @JoinColumn(name = "BID_ID", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrmsBid bidId;
    @Transient
    private String columnName;
    @Transient
    private String columnValue;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bidSubFid", fetch = FetchType.LAZY)
    private List<PrmsBidSubmissionDetail> prmsBidSubmissionDetailList;
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bidSumId", fetch = FetchType.LAZY)
//    private List<PrmsBidOpeningCheckList> prmsBidOpeningCheckLists;
    @OneToMany(mappedBy = "bidSubId", cascade = CascadeType.ALL)
    private List<WfPrmsProcessed> prmsWorkFlowProccedList;

    public PrmsBidSubmission() {
    }

    public PrmsBidSubmission(BigDecimal bidSubId) {
        this.bidSubId = bidSubId;
    }

    public BigDecimal getBidSubId() {
        return bidSubId;
    }

    public void setBidSubId(BigDecimal bidSubId) {
        this.bidSubId = bidSubId;
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

    public Integer getPreparedBy() {
        return preparedBy;
    }

    public void setPreparedBy(Integer preparedBy) {
        this.preparedBy = preparedBy;
    }

    public Date getDateRigistered() {
        return dateRigistered;
    }

    public void setDateRigistered(Date dateRigistered) {
        this.dateRigistered = dateRigistered;
    }

    public PrmsBid getBidId() {
        return bidId;
    }

    public void setBidId(PrmsBid bidId) {
        this.bidId = bidId;
    }

    public String getBidSubmNo() {
        return bidSubmNo;
    }

    public void setBidSubmNo(String bidSubmNo) {
        this.bidSubmNo = bidSubmNo;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(Integer currentStatus) {
        this.currentStatus = currentStatus;
    }

    @XmlTransient
    public List<PrmsBidSubmissionDetail> getPrmsBidSubmissionDetailList() {
        if (prmsBidSubmissionDetailList == null) {
            prmsBidSubmissionDetailList = new ArrayList<>();
        }
        return prmsBidSubmissionDetailList;
    }

    public void setPrmsBidSubmissionDetailList(List<PrmsBidSubmissionDetail> prmsBidSubmissionDetailList) {
        this.prmsBidSubmissionDetailList = prmsBidSubmissionDetailList;
    }

    public List<WfPrmsProcessed> getPrmsWorkFlowProccedList() {
        if (prmsWorkFlowProccedList == null) {
            prmsWorkFlowProccedList = new ArrayList<>();
        }
        return prmsWorkFlowProccedList;
    }

    public void setPrmsWorkFlowProccedList(List<WfPrmsProcessed> prmsWorkFlowProccedList) {
        this.prmsWorkFlowProccedList = prmsWorkFlowProccedList;
    }

//    @XmlTransient
//    public List<PrmsBidOpeningCheckList> getPrmsBidOpeningCheckLists() {
//        if (prmsBidOpeningCheckLists == null) {
//            prmsBidOpeningCheckLists = new ArrayList<>();
//        }
//        return prmsBidOpeningCheckLists;
//    }
//
//    public void setPrmsBidOpeningCheckLists(List<PrmsBidOpeningCheckList> prmsBidOpeningCheckLists) {
//        this.prmsBidOpeningCheckLists = prmsBidOpeningCheckLists;
//    }
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (bidSubId != null ? bidSubId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {

        if (!(object instanceof PrmsBidSubmission)) {
            return false;
        }
        PrmsBidSubmission other = (PrmsBidSubmission) object;
        if ((this.bidSubId == null && other.bidSubId != null) || (this.bidSubId != null && !this.bidSubId.equals(other.bidSubId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return bidSubId.toString();
    }

    public void addSubmissionDetail(PrmsBidSubmissionDetail prmsBidSubmissionDetail) {
        if (prmsBidSubmissionDetail.getBidSubFid() != this) {
            this.getPrmsBidSubmissionDetailList().add(prmsBidSubmissionDetail);
            prmsBidSubmissionDetail.setBidSubFid(this);
        }

    }

}
