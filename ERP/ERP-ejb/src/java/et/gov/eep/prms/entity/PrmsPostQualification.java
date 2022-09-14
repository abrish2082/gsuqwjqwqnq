/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.entity;

import et.gov.eep.commonApplications.entity.WfPrmsProcessed;
import java.io.Serializable;
import java.math.BigInteger;
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
@Table(name = "PRMS_POST_QUALIFICATION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrmsPostQualification.findAll", query = "SELECT p FROM PrmsPostQualification p"),
    @NamedQuery(name = "PrmsPostQualification.findByPqNo", query = "SELECT p FROM PrmsPostQualification p WHERE p.pqNo = :pqNo"),
    @NamedQuery(name = "PrmsPostQualification.findByReqForApproval", query = "SELECT p FROM PrmsPostQualification  p WHERE p.status=0"),
    @NamedQuery(name = "PrmsPostQualification.findByPqNos", query = "SELECT p FROM PrmsPostQualification p WHERE p.pqNo LIKE :pqNo"),
    @NamedQuery(name = "PrmsPostQualification.findByRemark", query = "SELECT p FROM PrmsPostQualification p WHERE p.remark = :remark"),
    @NamedQuery(name = "PrmsPostQualification.findByPostId", query = "SELECT p FROM PrmsPostQualification p WHERE p.postId = :postId"),
    @NamedQuery(name = "PrmsPostQualification.findByStatus", query = "SELECT p FROM PrmsPostQualification p WHERE p.status = :status"),
    @NamedQuery(name = "PrmsPostQualification.findByPreparedBy", query = "SELECT p FROM PrmsPostQualification p WHERE p.preparedBy = :preparedBy"),
    @NamedQuery(name = "PrmsPostQualification.findByMaxPostId", query = "SELECT p FROM PrmsPostQualification p WHERE p.postId =(SELECT Max(p.postId)  from PrmsPostQualification p)"),
    @NamedQuery(name = "PrmsPostQualification.findByBidId", query = "SELECT p FROM PrmsPostQualification p WHERE p.bidId = :bidId")})

public class PrmsPostQualification implements Serializable {

    @Column(name = "PROCESSED_ON")
    @Temporal(TemporalType.TIMESTAMP)
    private Date processedOn;

    @Column(name = "STATUS")
    private Integer status;
    @Column(name = "PREPARED_BY")
    private Integer preparedBy;

    @OneToMany(mappedBy = "postId", cascade = CascadeType.ALL)
    private List<WfPrmsProcessed> prmsWorkFlowProccedList;

    private static final long serialVersionUID = 1L;
    @Size(max = 20)
    @Column(name = "PQ_NO", length = 20)
    private String pqNo;
    @Size(max = 100)
    @Column(name = "REMARK", length = 100)
    private String remark;
    @Transient
    private String columnName;
    @Transient
    private String columnValue;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = " PRMS_POST_QUALIFICATION_SEQ")
    @SequenceGenerator(name = " PRMS_POST_QUALIFICATION_SEQ", sequenceName = " PRMS_POST_QUALIFICATION_SEQ", allocationSize = 1)
    @NotNull
    @Column(name = "POST_ID", nullable = false, precision = 0, scale = -127)
    private Integer postId;

    @JoinColumn(name = "FINANC_ID", referencedColumnName = "ID")
    @ManyToOne()
    private PrmsFinancialEvalResult financId;

    @JoinColumn(name = "BID_ID", referencedColumnName = "ID")
    @ManyToOne
    private PrmsBid bidId;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "postId", fetch = FetchType.LAZY)
    private List<PrmsPostDetail> prmsPostDetailList;

    public PrmsPostQualification() {
    }

    public PrmsPostQualification(Integer postId) {
        this.postId = postId;
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

    public String getPqNo() {
        return pqNo;
    }

    public void setPqNo(String pqNo) {
        this.pqNo = pqNo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public PrmsBid getBidId() {
        return bidId;
    }

    public void setBidId(PrmsBid bidId) {
        this.bidId = bidId;
    }

    public PrmsFinancialEvalResult getFinancId() {
        return financId;
    }

    public void setFinancId(PrmsFinancialEvalResult financId) {
        this.financId = financId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (postId != null ? postId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PrmsPostQualification)) {
            return false;
        }
        PrmsPostQualification other = (PrmsPostQualification) object;
        if ((this.postId == null && other.postId != null) || (this.postId != null && !this.postId.equals(other.postId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return pqNo;
    }

    @XmlTransient
    public List<PrmsPostDetail> getPrmsPostDetailList() {
        if (prmsPostDetailList == null) {
            prmsPostDetailList = new ArrayList<>();
        }
        return prmsPostDetailList;
    }

    public void setPrmsPostDetailList(List<PrmsPostDetail> prmsPostDetailList) {
        this.prmsPostDetailList = prmsPostDetailList;
    }

    /**
     * @return the wfPrmsProcessedCollection
     */
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

    /**
     * @return the preparedBy
     */
    public Integer getPreparedBy() {
        return preparedBy;
    }

    /**
     * @param preparedBy the preparedBy to set
     */
    public void setPreparedBy(Integer preparedBy) {
        this.preparedBy = preparedBy;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * @return the processedOn
     */
    public Date getProcessedOn() {
        return processedOn;
    }

    /**
     * @param processedOn the processedOn to set
     */
    public void setProcessedOn(Date processedOn) {
        this.processedOn = processedOn;
    }

}
