/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.actingAssignment;

import et.gov.eep.commonApplications.entity.WfHrProcessed;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.organization.HrJobTypes;
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
@Table(name = "HR_ACTING_ASSIGNMENTS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrActingAssignments.findAll", query = "SELECT h FROM HrActingAssignments h WHERE h.status = 0"),
    @NamedQuery(name = "HrActingAssignments.findById", query = "SELECT h FROM HrActingAssignments h WHERE h.id = :id"),

    @NamedQuery(name = "HrActingAssignments.findByTwo", query = "SELECT h FROM HrActingAssignments h WHERE h.requestDate LIKE :requestDate and UPPER(h.actingType) LIKE :actingType and h.status =0"),
    @NamedQuery(name = "HrActingAssignments.checkDuplicate", query = "SELECT h FROM HrActingAssignments h WHERE h.assignedBy.empId = :empId and h.status = 0"),
    @NamedQuery(name = "HrActingAssignments.findByStartingDate", query = "SELECT h FROM HrActingAssignments h WHERE h.startingDate = :startingDate"),
    @NamedQuery(name = "HrActingAssignments.findByEndingDate", query = "SELECT h FROM HrActingAssignments h WHERE h.endingDate = :endingDate"),
    @NamedQuery(name = "HrActingAssignments.findByActingType", query = "SELECT h FROM HrActingAssignments h WHERE UPPER(h.actingType) LIKE :actingType and h.status =0"),
    @NamedQuery(name = "HrActingAssignments.findByRequestDate", query = "SELECT h FROM HrActingAssignments h WHERE h.requestDate LIKE :requestDate and h.status =0"),
    @NamedQuery(name = "HrActingAssignments.findByStatus", query = "SELECT h FROM HrActingAssignments h WHERE h.status = :status"),
    @NamedQuery(name = "HrActingAssignments.findByStatusApprove", query = "SELECT h FROM HrActingAssignments h WHERE h.status != :status"),
    @NamedQuery(name = "HrActingAssignments.findByWfPrepared", query = "SELECT h FROM HrActingAssignments h WHERE h.status = 0 ORDER BY h.requestDate DESC"),
    @NamedQuery(name = "HrActingAssignments.findByRemark", query = "SELECT h FROM HrActingAssignments h WHERE h.remark = :remark")})
public class HrActingAssignments implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_ACTING_ASSIGNMENTS_SEQ")
    @SequenceGenerator(name = "HR_ACTING_ASSIGNMENTS_SEQ", sequenceName = "HR_ACTING_ASSIGNMENTS_SEQ", allocationSize = 1)
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Integer id;
    @Column(name = "STARTING_DATE")
    private String startingDate;
    @Column(name = "ENDING_DATE")
    private String endingDate;
    @Column(name = "ACTING_TYPE")
    private String actingType;
    @Column(name = "REQUEST_DATE")
    private String requestDate;
    @Column(name = "STATUS")
    private Integer status;
    @Column(name = "REMARK")
    private String remark;
    @Column(name = "PREPARED_BY")
    private String preparedBy;
    
    @JoinColumn(name = "ASSIGNED_BY", referencedColumnName = "ID")
    @ManyToOne
    private HrEmployees assignedBy;
    
    @JoinColumn(name = "ASSIGNED_PERSON", referencedColumnName = "ID")
    @ManyToOne
    private HrEmployees assignedPerson;
    
//    @JoinColumn(name = "PERMANENT_POSITION", referencedColumnName = "ID")
//    @ManyToOne
//    private HrJobTypes permanentPosition;
    
    @JoinColumn(name = "ACTING_POSITION", referencedColumnName = "ID")
    @ManyToOne
    private HrJobTypes actingPosition;

    @OneToMany(mappedBy = "actingId", cascade = CascadeType.ALL)
    private List<WfHrProcessed> wfHrProcessedList = new ArrayList<>();

    public HrActingAssignments() {
    }

    public HrActingAssignments(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(String startingDate) {
        this.startingDate = startingDate;
    }

    public String getEndingDate() {
        return endingDate;
    }

    public void setEndingDate(String endingDate) {
        this.endingDate = endingDate;
    }

    public String getActingType() {
        return actingType;
    }

    public void setActingType(String actingType) {
        this.actingType = actingType;
    }

    public String getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPreparedBy() {
        return preparedBy;
    }

    public void setPreparedBy(String preparedBy) {
        this.preparedBy = preparedBy;
    }

    public HrEmployees getAssignedBy() {
        return assignedBy;
    }

    public void setAssignedBy(HrEmployees assignedBy) {
        this.assignedBy = assignedBy;
    }

    public HrEmployees getAssignedPerson() {
        return assignedPerson;
    }

    public void setAssignedPerson(HrEmployees assignedPerson) {
        this.assignedPerson = assignedPerson;
    }

//    public HrJobTypes getPermanentPosition() {
//        return permanentPosition;
//    }
//
//    public void setPermanentPosition(HrJobTypes permanentPosition) {
//        this.permanentPosition = permanentPosition;
//    }

    public HrJobTypes getActingPosition() {
        return actingPosition;
    }

    public void setActingPosition(HrJobTypes actingPosition) {
        this.actingPosition = actingPosition;
    }

    @XmlTransient
    public List<WfHrProcessed> getWfHrProcessedList() {
        if (wfHrProcessedList == null) {
            wfHrProcessedList = new ArrayList<>();
        }
        return wfHrProcessedList;
    }

    public void setWfHrProcessedList(List<WfHrProcessed> wfHrProcessedList) {
        this.wfHrProcessedList = wfHrProcessedList;
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
        if (!(object instanceof HrActingAssignments)) {
            return false;
        }
        HrActingAssignments other = (HrActingAssignments) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.hrms.entity.actingAssignment.HrActingAssignments[ id=" + id + " ]";
    }

}
