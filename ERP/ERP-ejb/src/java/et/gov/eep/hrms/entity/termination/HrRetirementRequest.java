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
@Table(name = "HR_RETIREMENT_REQUEST")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrRetirementRequest.findAll", query = "SELECT h FROM HrRetirementRequest h"),
    @NamedQuery(name = "HrRetirementRequest.findById", query = "SELECT h FROM HrRetirementRequest h WHERE h.id = :id"),
    @NamedQuery(name = "HrRetirementRequest.findByRetirementType", query = "SELECT h FROM HrRetirementRequest h WHERE h.retirementType = :retirementType"),
    @NamedQuery(name = "HrRetirementRequest.findByRequestDate", query = "SELECT h FROM HrRetirementRequest h WHERE h.requestDate = :requestDate"),
    @NamedQuery(name = "HrRetirementRequest.findByRetirementDate", query = "SELECT h FROM HrRetirementRequest h WHERE h.retirementDate = :retirementDate"),
    @NamedQuery(name = "HrRetirementRequest.findByStatus", query = "SELECT h FROM HrRetirementRequest h WHERE h.status = :status"),
    @NamedQuery(name = "HrRetirementRequest.findPreparedList", query = "SELECT h FROM HrRetirementRequest h WHERE h.status = 0 ORDER BY h.requestDate"),
    @NamedQuery(name = "HrRetirementRequest.findByDescription", query = "SELECT h FROM HrRetirementRequest h WHERE h.description = :description")})
public class HrRetirementRequest implements Serializable {

    @OneToMany(mappedBy = "retirementId", cascade = CascadeType.ALL)
    private List<HrRetirementRequestUpload> hrRetirementRequestUploadList;

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_RETIREMENT_REQUEST_SEQ")
    @SequenceGenerator(name = "HR_RETIREMENT_REQUEST_SEQ", sequenceName = "HR_RETIREMENT_REQUEST_SEQ", allocationSize = 1)
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Integer id;
    @Size(max = 50)
    @Column(name = "RETIREMENT_TYPE")
    private String retirementType;
    @Size(max = 20)
    @Column(name = "REQUEST_DATE")
    private String requestDate;
    @Size(max = 20)
    @Column(name = "RETIREMENT_DATE")
    private String retirementDate;
    @Column(name = "STATUS")
    private Integer status;
    @Size(max = 100)
    @Column(name = "DESCRIPTION")
    private String description;
    @Size(max = 100)
    @Column(name = "REMARK")
    private String remrak;
    @Column(name = "PREPARED_BY")
    private String preparedBy;

    @JoinColumn(name = "EMP_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrEmployees empId;

    @OneToMany(mappedBy = "retirementId", cascade = CascadeType.ALL)
    private List<WfHrProcessed> wfHrProcessedList = new ArrayList<>();

    public HrRetirementRequest() {
    }

    public HrRetirementRequest(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRetirementType() {
        return retirementType;
    }

    public void setRetirementType(String retirementType) {
        this.retirementType = retirementType;
    }

    public String getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }

    public String getRetirementDate() {
        return retirementDate;
    }

    public void setRetirementDate(String retirementDate) {
        this.retirementDate = retirementDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRemrak() {
        return remrak;
    }

    public void setRemrak(String remrak) {
        this.remrak = remrak;
    }

    public HrEmployees getEmpId() {
        return empId;
    }

    public void setEmpId(HrEmployees empId) {
        this.empId = empId;
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

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrRetirementRequest)) {
            return false;
        }
        HrRetirementRequest other = (HrRetirementRequest) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return id + " -- " + " " + empId.getFirstName() + " " + empId.getMiddleName();
    }

    @XmlTransient
    public List<HrRetirementRequestUpload> getHrRetirementRequestUploadList() {
        if (hrRetirementRequestUploadList == null) {
            hrRetirementRequestUploadList = new ArrayList<>();
        }
        return hrRetirementRequestUploadList;
    }

    public void setHrRetirementRequestUploadList(List<HrRetirementRequestUpload> hrRetirementRequestUploadList) {
        this.hrRetirementRequestUploadList = hrRetirementRequestUploadList;
    }

    @XmlTransient
    public List<WfHrProcessed> getWfHrProcessedList() {
        if (wfHrProcessedList == null) {
            wfHrProcessedList = new ArrayList<>();
        }
        return wfHrProcessedList;
    }

    public void setWfHrProcessedList(List<WfHrProcessed> WfHrProcessedList) {
        this.wfHrProcessedList = WfHrProcessedList;
    }

    public void Add(HrRetirementRequestUpload hrRetirementRequestUpload) {
        if (hrRetirementRequestUpload.getRetirementId() != this) {
            this.getHrRetirementRequestUploadList().add(hrRetirementRequestUpload);
            hrRetirementRequestUpload.setRetirementId(this);
        }
    }
}
