/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.leave;

//import et.gov.insa.erp.ibfms.entity.FmsLuBudgetYear;
import et.gov.eep.commonApplications.entity.WfHrProcessed;
import et.gov.eep.fcms.entity.FmsLuBudgetYear;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import java.io.Serializable;
import java.math.BigInteger;
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
 * @author user
 */
@Entity
@Table(name = "HR_LEAVE_TRANSFER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrLeaveTransfer.findAll", query = "SELECT h FROM HrLeaveTransfer h"),
    @NamedQuery(name = "HrLeaveTransfer.findByTransferId", query = "SELECT h FROM HrLeaveTransfer h WHERE h.id = :transferId"),
    @NamedQuery(name = "HrLeaveTransfer.findByRequester", query = "SELECT h FROM HrLeaveTransfer h WHERE h.empId = :empId AND h.bgtYear.luBudgetYearId = :budgetyearID"),
    @NamedQuery(name = "HrLeaveTransfer.findByStatus", query = "SELECT h FROM HrLeaveTransfer h WHERE h.status = :status")})
public class HrLeaveTransfer implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    //  @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_LEAVE_TRANSFER_SEQ")
    @SequenceGenerator(name = "HR_LEAVE_TRANSFER_SEQ", sequenceName = "HR_LEAVE_TRANSFER_SEQ", allocationSize = 1)
    private Integer id;

    @Column(name = "FROM_YR")
    private Long fromYr;
    @Column(name = "TO_YR")
    private Long toYr;
    @Column(name = "TRANSFERED_DAYS")
    private Long transferedDays;

    @Column(name = "STATUS")
    private Integer status;

    @Size(max = 200)
    @Column(name = "APPROVER_REMARK")
    private String approverRemark;

    @JoinColumn(name = "EMPID", referencedColumnName = "ID")
    @ManyToOne
    private HrEmployees empId;

    @JoinColumn(name = "BGT_YEAR", referencedColumnName = "LU_BUDGET_YEAR_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private FmsLuBudgetYear bgtYear;

    @OneToMany(mappedBy = "requestMasterId", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<HrLeaveTransferDetail> hrLeaveTransferDetailList;

    @OneToMany(mappedBy = "leaveTransferId", cascade = CascadeType.ALL)
    private List<WfHrProcessed> hrWorkFlowLeaveList;

    public HrLeaveTransfer() {
    }

    public String getApproverRemark() {
        return approverRemark;
    }

    public void setApproverRemark(String approverRemark) {
        this.approverRemark = approverRemark;
    }

    public FmsLuBudgetYear getBudgetYear() {
        return bgtYear;
    }

    public void setBudgetYear(FmsLuBudgetYear budgetYear) {
        this.bgtYear = budgetYear;
    }

    @XmlTransient
    public List<HrLeaveTransferDetail> getHrLeaveTransferDetailList() {
        if (hrLeaveTransferDetailList == null) {
            hrLeaveTransferDetailList = new ArrayList<>();
        }
        return hrLeaveTransferDetailList;
    }

    public void setHrLeaveTransferDetailList(List<HrLeaveTransferDetail> hrLeaveTransferDetailList) {
        this.hrLeaveTransferDetailList = hrLeaveTransferDetailList;
    }

    @XmlTransient
    public List<WfHrProcessed> getHrWorkFlowLeaveList() {
        return hrWorkFlowLeaveList;
    }

    public void setHrWorkFlowLeaveList(List<WfHrProcessed> hrWorkFlowLeaveList) {
        this.hrWorkFlowLeaveList = hrWorkFlowLeaveList;
    }

    public void addToRequest(HrLeaveTransferDetail detail) {
        if (detail.getRequestMasterId() != this) {
            this.getHrLeaveTransferDetailList().add(detail);
            detail.setRequestMasterId(this);
        }
    }

    public Long getFromYr() {
        return fromYr;
    }

    public void setFromYr(Long fromYr) {
        this.fromYr = fromYr;
    }

    public Long getToYr() {
        return toYr;
    }

    public void setToYr(Long toYr) {
        this.toYr = toYr;
    }

    public Long getTransferedDays() {
        return transferedDays;
    }

    public void setTransferedDays(Long transferedDays) {
        this.transferedDays = transferedDays;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public HrEmployees getEmpId() {
        return empId;
    }

    public void setEmpId(HrEmployees empId) {
        this.empId = empId;
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
        if (!(object instanceof HrLeaveTransfer)) {
            return false;
        }
        HrLeaveTransfer other = (HrLeaveTransfer) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return id.toString();
    }

}
