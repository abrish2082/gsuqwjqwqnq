/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.displine;

import et.gov.eep.commonApplications.entity.WfHrProcessed;
import et.gov.eep.commonApplications.utility.date.StringDateManipulation;
import java.util.Date;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
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
@Table(name = "HR_DISCIPLINE_REQUESTS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrDisciplineRequests.findAll", query = "SELECT h FROM HrDisciplineRequests h"),
    @NamedQuery(name = "HrDisciplineRequests.findById", query = "SELECT h FROM HrDisciplineRequests h WHERE h.id = :id"),
    @NamedQuery(name = "HrDisciplineRequests.findByIdLIKE", query = "SELECT (h.offenceDate) FROM HrDisciplineRequests h WHERE h.id = :id"),
    @NamedQuery(name = "HrDisciplineRequests.findByOffenceDate", query = "SELECT h FROM HrDisciplineRequests h WHERE h.offenceDate = :offenceDate"),
    @NamedQuery(name = "HrDisciplineRequests.findByReportedDate", query = "SELECT h FROM HrDisciplineRequests h WHERE h.reportedDate = :reportedDate"),
    @NamedQuery(name = "HrDisciplineRequests.findByDescriptionOfOffence", query = "SELECT h FROM HrDisciplineRequests h WHERE h.descriptionOfOffence = :descriptionOfOffence"),
    @NamedQuery(name = "HrDisciplineRequests.findByRepititionOfOffence", query = "SELECT h FROM HrDisciplineRequests h WHERE h.repititionOfOffence = :repititionOfOffence"),
    @NamedQuery(name = "HrDisciplineRequests.findByOffenceTypeId", query = "SELECT h FROM HrDisciplineRequests h WHERE h.offenceTypeId = :offenceTypeId"),
    @NamedQuery(name = "HrDisciplineRequests.findByDecisionOnPenality", query = "SELECT h FROM HrDisciplineRequests h WHERE h.decisionOnPenality = :decisionOnPenality"),
    @NamedQuery(name = "HrDisciplineRequests.findByRequestRemark", query = "SELECT h FROM HrDisciplineRequests h WHERE h.requestRemark = :requestRemark"),
    @NamedQuery(name = "HrDisciplineRequests.findByPreparedOn", query = "SELECT h FROM HrDisciplineRequests h WHERE h.preparedOn = :preparedOn"),
    @NamedQuery(name = "HrDisciplineRequests.findByPreparedBy", query = "SELECT h FROM HrDisciplineRequests h WHERE h.preparedBy = :preparedBy"),
    @NamedQuery(name = "HrDisciplineRequests.findByRequesterName", query = "SELECT h FROM HrDisciplineRequests h WHERE UPPER(h.requesterId.firstName) LIKE :firstName"),
    @NamedQuery(name = "HrDisciplineRequests.findByRequsterAndOffenderName", query = "SELECT h FROM HrDisciplineRequests h WHERE UPPER(h.requesterId.firstName) LIKE :reqName AND UPPER(h.offenderId.firstName) LIKE :OffenderName"),
    @NamedQuery(name = "HrDisciplineRequests.findByApprovedOn", query = "SELECT h FROM HrDisciplineRequests h WHERE h.approvedOn = :approvedOn"),
    @NamedQuery(name = "HrDisciplineRequests.findByStatus", query = "SELECT h FROM HrDisciplineRequests h WHERE h.status = :status"),
    @NamedQuery(name = "HrDisciplineRequests.findByoffenderId", query = "SELECT h FROM HrDisciplineRequests h WHERE h.offenderId = :offenderId"),
    @NamedQuery(name = "HrDisciplineRequests.findByoffenderIdAndOffenceType", query = "SELECT h FROM HrDisciplineRequests h WHERE h.offenderId = :offenderId AND h.offenceTypeId = :offenceType AND h.status = 1"),
    @NamedQuery(name = "HrDisciplineRequests.findDisciplineReqRecordByOffenderIdAndOffenceType", query = "SELECT h FROM HrDisciplineRequests h WHERE h.offenderId = :offenderId AND h.offenceTypeId = :offenceType AND h.status = 0")})
public class HrDisciplineRequests implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")   //
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_DISCIPLINE_REQUESTS_SEQ")
    @SequenceGenerator(name = "HR_DISCIPLINE_REQUESTS_SEQ", sequenceName = "HR_DISCIPLINE_REQUESTS_SEQ", allocationSize = 1)
    private Integer id;

    @Column(name = "OFFENCE_DATE")
    //@Temporal(TemporalType.TIMESTAMP)
    private String offenceDate;

    @Column(name = "REPORTED_DATE")
    //@Temporal(TemporalType.TIMESTAMP)
    private String reportedDate;

//    @Column(name = "APPROVED_DATE")
//    private String approvedDate;
    @Size(max = 200)
    @Column(name = "DESCRIPTION_OF_OFFENCE")
    private String descriptionOfOffence;

    @Column(name = "REPITITION_OF_OFFENCE")
    private Integer repititionOfOffence;

    @Size(max = 200)
    @Column(name = "DECISION_ON_PENALITY")
    private String decisionOnPenality;

//    @Size(max = 200)
//    @Column(name = "APPLOAD_FILE")
//    private byte[] apploadFile;
    @Size(max = 200)
    @Column(name = "REQUEST_REMARK")
    private String requestRemark;

    @Size(max = 20)
    @Column(name = "PREPARED_ON")
    private String preparedOn;

    @Size(max = 20)
    @Column(name = "APPROVED_ON")
    //@Temporal(TemporalType.TIMESTAMP)
    private String approvedOn;

    @Size(max = 20)
    @Column(name = "STATUS")
    private String status;

    @Size(max = 20)
    @Column(name = "PREPARED_BY")
    private String preparedBy;

    @JoinColumn(name = "OFFENCE_TYPE_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrDisciplineOffenceTypes offenceTypeId;

//    @JoinColumn(name = "PENALITY_TYPE_ID", referencedColumnName = "ID")
//    @ManyToOne
//    private HrDisciplinePenaltyTypes penalityTypeId;
    @JoinColumn(name = "REQUESTER_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrEmployees requesterId;

    @JoinColumn(name = "OFFENDER_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrEmployees offenderId;
//    @JoinColumn(name = "PREPARED_BY", referencedColumnName = "ID")
//    @ManyToOne
//    private HrEmployees preparedBy;
    @OneToMany(mappedBy = "disciplineCaseId", cascade = CascadeType.ALL)
    private List<HrDisciplineFileUpload> hrDisciplineFileUploadList;
    @OneToMany(mappedBy = "disciplineId")
    private List<WfHrProcessed> hrWorkFlowProccedList;
    @OneToMany(mappedBy = "requestId")
    private List<HrEmpDisciplines> hrEmpDisciplinesList;

    public HrDisciplineRequests() {
    }

    public HrDisciplineRequests(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOffenceDate() {
        return offenceDate;
    }

    public void setOffenceDate(String offenceDate) {
        this.offenceDate = offenceDate;
    }

    public String getReportedDate() {
        return reportedDate;
    }

    public void setReportedDate(String reportedDate) {
        this.reportedDate = reportedDate;
    }

//    public String getApprovedDate() {
//        return approvedDate;
//    }
//
//    public void setApprovedDate(String approvedDate) {
//        this.approvedDate = approvedDate;
//    }
    public String getDescriptionOfOffence() {
        return descriptionOfOffence;
    }

    public void setDescriptionOfOffence(String descriptionOfOffence) {
        this.descriptionOfOffence = descriptionOfOffence;
    }

    public Integer getRepititionOfOffence() {
        return repititionOfOffence;
    }

    public void setRepititionOfOffence(Integer repititionOfOffence) {
        this.repititionOfOffence = repititionOfOffence;
    }

    public String getDecisionOnPenality() {
        return decisionOnPenality;
    }

    public void setDecisionOnPenality(String decisionOnPenality) {
        this.decisionOnPenality = decisionOnPenality;
    }

    public String getRequestRemark() {
        return requestRemark;
    }

    public void setRequestRemark(String requestRemark) {
        this.requestRemark = requestRemark;
    }

    public String getPreparedOn() {
        return preparedOn;
    }

    public void setPreparedOn(String preparedOn) {
        this.preparedOn = preparedOn;
    }

    public String getApprovedOn() {
        return approvedOn;
    }

    public void setApprovedOn(String approvedOn) {
        this.approvedOn = approvedOn;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public HrDisciplineOffenceTypes getOffenceTypeId() {
        return offenceTypeId;
    }

    public void setOffenceTypeId(HrDisciplineOffenceTypes offenceTypeId) {
        this.offenceTypeId = offenceTypeId;
    }

    public HrEmployees getRequesterId() {
        return requesterId;
    }

    public void setRequesterId(HrEmployees requesterId) {
        this.requesterId = requesterId;
    }

    public HrEmployees getOffenderId() {
        return offenderId;
    }

    public void setOffenderId(HrEmployees offenderId) {
        this.offenderId = offenderId;
    }

    public String getPreparedBy() {
        return preparedBy;
    }

    public void setPreparedBy(String preparedBy) {
        this.preparedBy = preparedBy;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @XmlTransient
    public List<HrEmpDisciplines> getHrEmpDisciplinesList() {
        if (hrEmpDisciplinesList == null) {
            hrEmpDisciplinesList = new ArrayList<>();
        }
        return hrEmpDisciplinesList;
    }

    public void setHrEmpDisciplinesList(List<HrEmpDisciplines> hrEmpDisciplinesList) {
        this.hrEmpDisciplinesList = hrEmpDisciplinesList;
    }

    @XmlTransient
    public List<HrDisciplineFileUpload> getHrDisciplineFileUploadList() {
        if (hrDisciplineFileUploadList == null) {
            hrDisciplineFileUploadList = new ArrayList<>();
        }
        return hrDisciplineFileUploadList;
    }

    public void setHrDisciplineFileUploadList(List<HrDisciplineFileUpload> hrDisciplineFileUploadList) {
        this.hrDisciplineFileUploadList = hrDisciplineFileUploadList;
    }

    @XmlTransient
    public List<WfHrProcessed> getHrWorkFlowProccedList() {
        if (hrWorkFlowProccedList == null) {
            hrWorkFlowProccedList = new ArrayList<>();
        }
        return hrWorkFlowProccedList;
    }

    public void setHrWorkFlowProccedList(List<WfHrProcessed> hrWorkFlowProccedList) {
        this.hrWorkFlowProccedList = hrWorkFlowProccedList;
    }
    @Transient
    String changedStatus;

    public String getChangedStatus() {
        return changedStatus;
    }

    public void setChangedStatus(String changedStatus) {
        this.changedStatus = changedStatus;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrDisciplineRequests)) {
            return false;
        }
        HrDisciplineRequests other = (HrDisciplineRequests) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    public void add(HrDisciplineFileUpload hrDisciplineFileUpload) {
        if (hrDisciplineFileUpload.getDisciplineCaseId() != this) {
            this.getHrDisciplineFileUploadList().add(hrDisciplineFileUpload);
            hrDisciplineFileUpload.setDisciplineCaseId(this);
        }
    }

    @Override
    public String toString() {
        // return "et.gov.eep.hrms.entity.displine.HrDisciplineRequests[ id=" + id + " ]";
//        return getOffenderId().getFirstName();
        return offenceDate;
    }

}
