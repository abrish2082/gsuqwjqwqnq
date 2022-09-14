/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.entity.budget;

import et.gov.eep.commonApplications.entity.WfFcmsProcessed;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author PO
 */
@Entity
@Table(name = "FMS_CB_TRANSFER_DETAIL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsCbTransferDetail.findAll", query = "SELECT f FROM FmsCbTransferDetail f"),
    @NamedQuery(name = "FmsCbTransferDetail.findByTransferDtlId", query = "SELECT f FROM FmsCbTransferDetail f WHERE f.transferDtlId = :transferDtlId"),
    @NamedQuery(name = "FmsCbTransferDetail.findByAmount", query = "SELECT f FROM FmsCbTransferDetail f WHERE f.amount = :amount"),
    @NamedQuery(name = "FmsCbTransferDetail.findByFromCbId", query = "SELECT f FROM FmsCbTransferDetail f WHERE f.fromCbId = :fromCbId"),
    @NamedQuery(name = "FmsCbTransferDetail.findReqList", query = "SELECT f FROM FmsCbTransferDetail f WHERE f.status <> 3"),
    @NamedQuery(name = "FmsCbTransferDetail.findByStatus", query = "SELECT f FROM FmsCbTransferDetail f WHERE f.status = :status"),
    @NamedQuery(name = "FmsCbTransferDetail.findByPrepareRemark", query = "SELECT f FROM FmsCbTransferDetail f WHERE f.prepareRemark = :prepareRemark"),
    @NamedQuery(name = "FmsCbTransferDetail.findByPrepareDate", query = "SELECT f FROM FmsCbTransferDetail f WHERE f.prepareDate = :prepareDate")})
public class FmsCbTransferDetail implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "TRANSFER_DTL_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FMS_CB_TRANSFER_DETAIL_SEQ")
    @SequenceGenerator(name = "FMS_CB_TRANSFER_DETAIL_SEQ", sequenceName = "FMS_CB_TRANSFER_DETAIL_SEQ", allocationSize = 1)
    private Integer transferDtlId;
    @Column(name = "AMOUNT")
    private BigDecimal amount;
    @Column(name = "STATUS")
    private Integer status;
    @Size(max = 100)
    @Column(name = "PREPARE_REMARK")
    private String prepareRemark;
    @Size(max = 20)
    @Column(name = "PREPARE_DATE")
    private String prepareDate;
    @Column(name = "PREPARED_BY")
    private Integer preparedBy;
    @Size(max = 20)
    @Column(name = "REFERENCE_NO")
    private String referenceNo;
    @JoinColumn(name = "FROM_CB_ID", referencedColumnName = "C_B_TASKS_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private FmsCapitalBudgetTasks fromCbId;
    @JoinColumn(name = "TO_CB_ID", referencedColumnName = "C_B_TASKS_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private FmsCapitalBudgetTasks toCbId;
    @OneToMany(mappedBy = "cbTransferDtlId")
    private List<WfFcmsProcessed> wfFcmsProcessedList;

    public FmsCbTransferDetail() {
    }

    public FmsCbTransferDetail(Integer transferDtlId) {
        this.transferDtlId = transferDtlId;
    }

    public Integer getTransferDtlId() {
        return transferDtlId;
    }

    public void setTransferDtlId(Integer transferDtlId) {
        this.transferDtlId = transferDtlId;
    }

    public Integer getPreparedBy() {
        return preparedBy;
    }

    public void setPreparedBy(Integer preparedBy) {
        this.preparedBy = preparedBy;
    }
    
    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
    
    public String getPrepareRemark() {
        return prepareRemark;
    }

    public void setPrepareRemark(String prepareRemark) {
        this.prepareRemark = prepareRemark;
    }

    public String getPrepareDate() {
        return prepareDate;
    }

    public void setPrepareDate(String prepareDate) {
        this.prepareDate = prepareDate;
    }

    public String getReferenceNo() {
        return referenceNo;
    }

    public void setReferenceNo(String referenceNo) {
        this.referenceNo = referenceNo;
    }

    public FmsCapitalBudgetTasks getFromCbId() {
        return fromCbId;
    }

    public void setFromCbId(FmsCapitalBudgetTasks fromCbId) {
        this.fromCbId = fromCbId;
    }

    public FmsCapitalBudgetTasks getToCbId() {
        return toCbId;
    }

    public void setToCbId(FmsCapitalBudgetTasks toCbId) {
        this.toCbId = toCbId;
    }
    
    @XmlTransient
    public List<WfFcmsProcessed> getWfFcmsProcessedList() {
        return wfFcmsProcessedList;
    }

    public void setWfFcmsProcessedList(List<WfFcmsProcessed> wfFcmsProcessedList) {
        this.wfFcmsProcessedList = wfFcmsProcessedList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (transferDtlId != null ? transferDtlId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FmsCbTransferDetail)) {
            return false;
        }
        FmsCbTransferDetail other = (FmsCbTransferDetail) object;
        if ((this.transferDtlId == null && other.transferDtlId != null) || (this.transferDtlId != null && !this.transferDtlId.equals(other.transferDtlId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.fcms.entity.budget.FmsCbTransferDetail[ transferDtlId=" + transferDtlId + " ]";
    }

}
