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
@Table(name = "PRMS_PREMINILARY_EVALUATION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrmsPreminilaryEvaluation.findAll", query = "SELECT p FROM PrmsPreminilaryEvaluation p"),
    @NamedQuery(name = "PrmsPreminilaryEvaluation.findById", query = "SELECT p FROM PrmsPreminilaryEvaluation p WHERE p.id = :id"),
    @NamedQuery(name = "PrmsPreminilaryEvaluation.findByBidNo", query = "SELECT p FROM PrmsPreminilaryEvaluation p WHERE p.bidId.refNo = :refNo"),
    @NamedQuery(name = "PrmsPreminilaryEvaluation.findByPremlntyReq", query = "SELECT p FROM PrmsPreminilaryEvaluation p WHERE p.status = 0"),
    @NamedQuery(name = "PrmsPreminilaryEvaluation.findByLastId", query = "SELECT p FROM PrmsPreminilaryEvaluation p WHERE p.id = (SELECT Max(p.id)  from PrmsPreminilaryEvaluation p)"),
    @NamedQuery(name = "PrmsPreminilaryEvaluation.findByPrelimnryEvNo", query = "SELECT p FROM PrmsPreminilaryEvaluation p WHERE p.preliminarEvalNo LIKE :preliminaryEvNum and p.preparedBy=:preparedBy"),
    @NamedQuery(name = "PrmsPreminilaryEvaluation.findByPrelimnryEvNum", query = "SELECT p FROM PrmsPreminilaryEvaluation p WHERE p.preliminarEvalNo = :preliminarEvalNo"),
    @NamedQuery(name = "PrmsPreminilaryEvaluation.findByDateRigistered", query = "SELECT p FROM PrmsPreminilaryEvaluation p WHERE p.dateRigistered = :dateRigistered"),
    @NamedQuery(name = "PrmsPreminilaryEvaluation.findByPreparedBy", query = "SELECT p FROM PrmsPreminilaryEvaluation p WHERE p.preparedBy = :preparedBy"),
    @NamedQuery(name = "PrmsPreminilaryEvaluation.findByApprovedDate", query = "SELECT p FROM PrmsPreminilaryEvaluation p WHERE p.approvedDate = :approvedDate"),
    @NamedQuery(name = "PrmsPreminilaryEvaluation.findByRemark", query = "SELECT p FROM PrmsPreminilaryEvaluation p WHERE p.remark = :remark")})
public class PrmsPreminilaryEvaluation implements Serializable {

    @OneToMany(mappedBy = "preliminaryEvaluationId", fetch = FetchType.LAZY)
    private List<WfPrmsProcessed> wfPrmsProcessedLists;

    @OneToMany(mappedBy = "preminaryEvaId")
    private List<PrmsThechnicalEvaluation> prmsThechnicalEvaluationList;

    @Size(max = 50)
    @Column(name = "PRELIMINARY_EVAL_NO", length = 50)
    private String preliminarEvalNo;
    @Column(name = "STATUS")
    private Integer status;

    @Column(name = "PREPARED_BY")
    private Integer preparedBy;
    @Size(max = 20)
    @Column(name = "APPROVED_BY", length = 20)
    private String approvedBy;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "ID", nullable = false, length = 20)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRMS_PRELIMINARY_EVAL_SEQ")
    @SequenceGenerator(name = "PRMS_PRELIMINARY_EVAL_SEQ", sequenceName = "PRMS_PRELIMINARY_EVAL_SEQ", allocationSize = 1)
    private String id;
    @Column(name = "DATE_RIGISTERED")
    @Temporal(TemporalType.DATE)
    private Date dateRigistered;
    @Column(name = "APPROVED_DATE")
    @Temporal(TemporalType.DATE)
    private Date approvedDate;
    @Size(max = 100)
    @Column(name = "REMARK", length = 100)
    private String remark;
     @Transient
    private String columnName;
    @Transient
    private String columnValue;
    @JoinColumn(name = "BID_ID", referencedColumnName = "ID")
    @ManyToOne
    private PrmsBid bidId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "preminaryId")
    private List<PrmsPreminilaryEvalutionDt> prmsPreminilaryEvalutionDtList;

    public PrmsPreminilaryEvaluation() {
    }

    public PrmsPreminilaryEvaluation(String id) {
        this.id = id;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    @XmlTransient
    public List<PrmsPreminilaryEvalutionDt> getPrmsPreminilaryEvalutionDtList() {
        if (prmsPreminilaryEvalutionDtList == null) {
            prmsPreminilaryEvalutionDtList = new ArrayList<>();
        }
        return prmsPreminilaryEvalutionDtList;
    }

    public void add(PrmsPreminilaryEvalutionDt preminilaryEvalutionDt) {

        if (preminilaryEvalutionDt.getPreminaryId() != this) {
            this.getPrmsPreminilaryEvalutionDtList().add(preminilaryEvalutionDt);
            preminilaryEvalutionDt.setPreminaryId(this);

        }
    }

    public void setPrmsPreminilaryEvalutionDtList(List<PrmsPreminilaryEvalutionDt> prmsPreminilaryEvalutionDtList) {
        this.prmsPreminilaryEvalutionDtList = prmsPreminilaryEvalutionDtList;
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
        if (!(object instanceof PrmsPreminilaryEvaluation)) {
            return false;
        }
        PrmsPreminilaryEvaluation other = (PrmsPreminilaryEvaluation) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return preliminarEvalNo;
    }

    public Integer getPreparedBy() {
        return preparedBy;
    }

    public void setPreparedBy(Integer preparedBy) {
        this.preparedBy = preparedBy;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @XmlTransient
    public List<PrmsThechnicalEvaluation> getPrmsThechnicalEvaluationList() {
        return prmsThechnicalEvaluationList;
    }

    public void setPrmsThechnicalEvaluationList(List<PrmsThechnicalEvaluation> prmsThechnicalEvaluationList) {
        this.prmsThechnicalEvaluationList = prmsThechnicalEvaluationList;
    }

    @XmlTransient

    public String getPreliminarEvalNo() {
        return preliminarEvalNo;
    }

    public void setPreliminarEvalNo(String preliminarEvalNo) {
        this.preliminarEvalNo = preliminarEvalNo;
    }

    @XmlTransient
    public List<WfPrmsProcessed> getWfPrmsProcessedCollection() {
        return wfPrmsProcessedLists;
    }

    public void setWfPrmsProcessedCollection(List<WfPrmsProcessed> wfPrmsProcessedLists) {
        this.wfPrmsProcessedLists = wfPrmsProcessedLists;
    }

}
