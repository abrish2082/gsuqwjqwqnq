/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.medical;

import et.gov.eep.commonApplications.entity.WfHrProcessed;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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
 * @author INSA
 */
@Entity
@Table(name = "HR_LOCAL_MED_SETTLEMENTS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrLocalMedSettlements.findAll", query = "SELECT h FROM HrLocalMedSettlements h WHERE h.status = '0'"),
    @NamedQuery(name = "HrLocalMedSettlements.findById", query = "SELECT h FROM HrLocalMedSettlements h WHERE h.id = :id"),

    @NamedQuery(name = "HrLocalMedSettlements.findByName", query = "SELECT h FROM HrLocalMedSettlements h WHERE UPPER(h.requesterId.firstName) LIKE :firstName and h.status ='0'"),

    @NamedQuery(name = "HrLocalMedSettlements.findByReferenceNo", query = "SELECT h FROM HrLocalMedSettlements h WHERE h.referenceNo = :referenceNo"),
    @NamedQuery(name = "HrLocalMedSettlements.findByRequestDate", query = "SELECT h FROM HrLocalMedSettlements h WHERE h.requestDate = :requestDate"),
    @NamedQuery(name = "HrLocalMedSettlements.findByRemark", query = "SELECT h FROM HrLocalMedSettlements h WHERE h.remark = :remark"),
    @NamedQuery(name = "HrLocalMedSettlements.findByPreparedBy", query = "SELECT h FROM HrLocalMedSettlements h WHERE h.preparedBy = :preparedBy"),
    @NamedQuery(name = "HrLocalMedSettlements.findByApprovedDate", query = "SELECT h FROM HrLocalMedSettlements h WHERE h.approvedDate = :approvedDate"),
    @NamedQuery(name = "HrLocalMedSettlements.findPreparedList", query = "SELECT h FROM HrLocalMedSettlements h WHERE h.status = '0' ORDER BY h.requestDate"),
    @NamedQuery(name = "HrLocalMedSettlements.findByStatus", query = "SELECT h FROM HrLocalMedSettlements h WHERE h.status = :status")})
public class HrLocalMedSettlements implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_LOCAL_MED_SETTLEMENTS_SEQ")
    @SequenceGenerator(name = "HR_LOCAL_MED_SETTLEMENTS_SEQ", sequenceName = "HR_LOCAL_MED_SETTLEMENTS_SEQ", allocationSize = 1)
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Integer id;
    @Size(max = 20)
    @Column(name = "REQUEST_DATE")
    private String requestDate;
    @Size(max = 200)
    @Column(name = "REMARK")
    private String remark;
    @Size(max = 20)
    @Column(name = "APPROVED_DATE")
    private String approvedDate;
    @Column(name = "STATUS")
    private String status;
    @Column(name = "REFERENCE_NO")
    private String referenceNo;
    @Column(name = "PREPARED_BY")
    private int preparedBy;

//    @JoinColumn(name = "PREPARED_BY", referencedColumnName = "ID")
//    @ManyToOne
//    private HrEmployees preparedBy;
    @JoinColumn(name = "REQUESTER_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrEmployees requesterId;

    @OneToMany(mappedBy = "settlementId", cascade = CascadeType.ALL)
    private List<HrLocalMedSettlementDetail> hrLocalMedSettlementDetailList = new ArrayList<>();

    @OneToMany(mappedBy = "medicalCashRefundId", cascade = CascadeType.ALL)
    private List<WfHrProcessed> wfHrProcessedList = new ArrayList<>();

    public HrLocalMedSettlements() {
    }

    public HrLocalMedSettlements(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getPreparedBy() {
        return preparedBy;
    }

    public void setPreparedBy(int preparedBy) {
        this.preparedBy = preparedBy;
    }

//    public HrEmployees getPreparedBy() {
//        return preparedBy;
//    }
//
//    public void setPreparedBy(HrEmployees preparedBy) {
//        this.preparedBy = preparedBy;
//    }
    public String getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(String approvedDate) {
        this.approvedDate = approvedDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public HrEmployees getRequesterId() {
        return requesterId;
    }

    public void setRequesterId(HrEmployees requesterId) {
        this.requesterId = requesterId;
    }

    public String getReferenceNo() {
        return referenceNo;
    }

    public void setReferenceNo(String referenceNo) {
        this.referenceNo = referenceNo;
    }

    @XmlTransient
    public List<HrLocalMedSettlementDetail> getHrLocalMedSettlementDetailList() {
        return hrLocalMedSettlementDetailList;
    }

    public void setHrLocalMedSettlementDetailList(List<HrLocalMedSettlementDetail> hrLocalMedSettlementDetailList) {
        this.hrLocalMedSettlementDetailList = hrLocalMedSettlementDetailList;
    }

    @XmlTransient
    public List<WfHrProcessed> getWfHrProcessedList() {
        return wfHrProcessedList;
    }

    public void setWfHrProcessedList(List<WfHrProcessed> wfHrProcessedList) {
        this.wfHrProcessedList = wfHrProcessedList;
    }

    public void addSettlementDetail(HrLocalMedSettlementDetail hrLocalMedSettlementDetail) {
        if (hrLocalMedSettlementDetail.getSettlementId() != this) {
            this.getHrLocalMedSettlementDetailList().add(hrLocalMedSettlementDetail);
            hrLocalMedSettlementDetail.setSettlementId(this);
        }
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
        if (!(object instanceof HrLocalMedSettlements)) {
            return false;
        }
        HrLocalMedSettlements other = (HrLocalMedSettlements) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.HrLocalMedSettlements[ id=" + id + " ]";
    }
}
