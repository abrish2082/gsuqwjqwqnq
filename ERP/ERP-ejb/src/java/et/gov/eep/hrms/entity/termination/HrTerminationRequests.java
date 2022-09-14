/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.termination;

import et.gov.eep.commonApplications.entity.WfHrProcessed;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import java.io.Serializable;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author INSA
 */
@Entity
@Table(name = "HR_TERMINATION_REQUESTS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrTerminationRequests.findAll", query = "SELECT h FROM HrTerminationRequests h"),
    @NamedQuery(name = "HrTerminationRequests.findByTerminationType", query = "SELECT h FROM HrTerminationRequests h WHERE h.status = 1 AND h.terminationType.terminationName = 'Resignation' OR h.status = 1 AND h.terminationType.terminationName = 'Disappear'"),
    @NamedQuery(name = "HrTerminationRequests.findById", query = "SELECT h FROM HrTerminationRequests h WHERE h.id = :id"),
    @NamedQuery(name = "HrTerminationRequests.findByRequestDate", query = "SELECT h FROM HrTerminationRequests h WHERE h.requestDate = :requestDate"),
    @NamedQuery(name = "HrTerminationRequests.findByTerminationDate", query = "SELECT h FROM HrTerminationRequests h WHERE h.terminationDate = :terminationDate"),
    @NamedQuery(name = "HrTerminationRequests.findByDescription", query = "SELECT h FROM HrTerminationRequests h WHERE h.description = :description"),
    @NamedQuery(name = "HrTerminationRequests.findByStatus", query = "SELECT h FROM HrTerminationRequests h WHERE h.status = :status"),
    @NamedQuery(name = "HrTerminationRequests.findPreparedList", query = "SELECT h FROM HrTerminationRequests h WHERE h.status = 0 ORDER BY h.requestDate"),
    @NamedQuery(name = "HrTerminationRequests.findByAdditionalWaitingDays", query = "SELECT h FROM HrTerminationRequests h WHERE h.additionalWaitingDays = :additionalWaitingDays")})
public class HrTerminationRequests implements Serializable {

    @OneToMany(mappedBy = "terminationId", cascade = CascadeType.ALL)
    private List<HrTerminationRequestUpload> hrTerminationRequestUploadList;

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_TERMINATION_REQUESTS_SEQ")
    @SequenceGenerator(name = "HR_TERMINATION_REQUESTS_SEQ", sequenceName = "HR_TERMINATION_REQUESTS_SEQ", allocationSize = 1)
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Integer id;
    @Size(max = 20)
    @Column(name = "REQUEST_DATE")
    private String requestDate;
    @Size(max = 20)
    @Column(name = "TERMINATION_DATE")
    private String terminationDate;
    @Size(max = 400)
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "STATUS")
    private Integer status;
    @Column(name = "PREPARED_BY")
    private String preparedBy;
    @Column(name = "ADDITIONAL_WAITING_DAYS")
    private BigInteger additionalWaitingDays;

    @JoinColumn(name = "EMP_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrEmployees empId;

    @JoinColumn(name = "TERMINATION_TYPE", referencedColumnName = "ID")
    @ManyToOne
    private HrTerminationTypes terminationType;

    @OneToMany(mappedBy = "terminationId", cascade = CascadeType.ALL)
    private List<WfHrProcessed> WfHrProcessedList = new ArrayList<>();

    public HrTerminationRequests() {
    }

    public HrTerminationRequests(Integer id) {
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

    public String getTerminationDate() {
        return terminationDate;
    }

    public void setTerminationDate(String terminationDate) {
        this.terminationDate = terminationDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getPreparedBy() {
        return preparedBy;
    }

    public void setPreparedBy(String preparedBy) {
        this.preparedBy = preparedBy;
    }

    public BigInteger getAdditionalWaitingDays() {
        return additionalWaitingDays;
    }

    public void setAdditionalWaitingDays(BigInteger additionalWaitingDays) {
        this.additionalWaitingDays = additionalWaitingDays;
    }

    public HrEmployees getEmpId() {
        return empId;
    }

    public void setEmpId(HrEmployees empId) {
        this.empId = empId;
    }

    public HrTerminationTypes getTerminationType() {
        return terminationType;
    }

    public void setTerminationType(HrTerminationTypes terminationType) {
        this.terminationType = terminationType;
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
        if (!(object instanceof HrTerminationRequests)) {
            return false;
        }
        HrTerminationRequests other = (HrTerminationRequests) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @XmlTransient
    public List<HrTerminationRequestUpload> getHrTerminationRequestUploadList() {
        if (hrTerminationRequestUploadList == null) {
            hrTerminationRequestUploadList = new ArrayList<>();
        }
        return hrTerminationRequestUploadList;
    }

    public void setHrTerminationRequestUploadList(List<HrTerminationRequestUpload> hrTerminationRequestUploadList) {
        this.hrTerminationRequestUploadList = hrTerminationRequestUploadList;
    }

    @XmlTransient
    public List<WfHrProcessed> getWfHrProcessedList() {
        if (WfHrProcessedList == null) {
            WfHrProcessedList = new ArrayList<>();
        }
        return WfHrProcessedList;
    }

    public void setWfHrProcessedList(List<WfHrProcessed> WfHrProcessedList) {
        this.WfHrProcessedList = WfHrProcessedList;
    }

    public void Add(HrTerminationRequestUpload hrTerminationRequestUpload) {
        if (hrTerminationRequestUpload.getTerminationId() != this) {
            this.getHrTerminationRequestUploadList().add(hrTerminationRequestUpload);
            hrTerminationRequestUpload.setTerminationId(this);
        }
    }

    @Override
    public String toString() {
        return id + " -- " + empId.getFirstName() + " " + empId.getMiddleName();
    }
}
