/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.recruitment;

import et.gov.eep.commonApplications.entity.WfHrProcessed;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.hrms.entity.organization.HrJobTypes;
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author user
 */
@Entity
@Table(name = "HR_RECRUITMENT_REQUESTS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrRecruitmentRequests.findAll", query = "SELECT h FROM HrRecruitmentRequests h"),
    @NamedQuery(name = "HrRecruitmentRequests.findAllRequest", query = "SELECT h FROM HrRecruitmentRequests h WHERE h.status=0"),
    // @NamedQuery(name = "HrRecruitmentRequests.advertise", query = "SELECT sum(h.numOfEmpsRequested)numOfEmpsRequested,count(h.batchCode)batchCode FROM HrRecruitmentRequests h WHERE h.status = 1 and h.batchCode = :batchCode"  ),
    @NamedQuery(name = "HrRecruitmentRequests.theJobs", query = "SELECT h FROM HrRecruitmentRequests h WHERE h.deptId.depId=:deptId And h.jobId.id=:jobsId"),
    @NamedQuery(name = "HrRecruitmentRequests.findByRecruitBatchCode", query = "SELECT h FROM HrRecruitmentRequests h WHERE h.batchCode = :recruitBatchCode"),
    @NamedQuery(name = "HrRecruitmentRequests.batchCode", query = "SELECT distinct h.batchCode FROM HrRecruitmentRequests h"),
    @NamedQuery(name = "HrRecruitmentRequests.findById", query = "SELECT h FROM HrRecruitmentRequests h WHERE h.id = :id"),
    @NamedQuery(name = "HrRecruitmentRequests.findByIdNotApproved", query = "SELECT h FROM HrRecruitmentRequests h WHERE h.id = :id And h.status = 0"),
    @NamedQuery(name = "HrRecruitmentRequests.findByNumOfEmpsRequested", query = "SELECT h FROM HrRecruitmentRequests h WHERE h.numOfEmpsRequested = :numOfEmpsRequested"),
    @NamedQuery(name = "HrRecruitmentRequests.findByRecruitmentType", query = "SELECT h FROM HrRecruitmentRequests h WHERE h.recruitmentType = :recruitmentType"),
    @NamedQuery(name = "HrRecruitmentRequests.findByRequestDate", query = "SELECT h FROM HrRecruitmentRequests h WHERE h.requestDate = :requestDate"),
    @NamedQuery(name = "HrRecruitmentRequests.findByRequestDateAndDeptId", query = "SELECT h FROM HrRecruitmentRequests h WHERE h.requestDate = :requestDate and h.deptId.depId = :deptId AND h.status = 0"),
    @NamedQuery(name = "HrRecruitmentRequests.findByRequestDateStatusZero", query = "SELECT h FROM HrRecruitmentRequests h WHERE h.requestDate = :requestDate And h.status = 0"),
    @NamedQuery(name = "HrRecruitmentRequests.findByRequestDeptId", query = "SELECT h FROM HrRecruitmentRequests h WHERE h.deptId.depName = :deptId And h.status = 0"),
    @NamedQuery(name = "HrRecruitmentRequests.findByRequestDeptIdAndDate", query = "SELECT h FROM HrRecruitmentRequests h WHERE h.deptId.depName = :deptId And h.requestDate = :requestDate AND h.status = 0"),
    @NamedQuery(name = "HrRecruitmentRequests.findByRemark", query = "SELECT h FROM HrRecruitmentRequests h WHERE h.remark = :remark"),
    @NamedQuery(name = "HrRecruitmentRequests.findByBatchCode", query = "SELECT h FROM HrRecruitmentRequests h WHERE h.batchCode = :batchCode"),
    @NamedQuery(name = "HrRecruitmentRequests.findByBatchCodeDistinict", query = "SELECT DISTINCT(h.batchCode) FROM HrRecruitmentRequests h WHERE h.status = 1"),
    @NamedQuery(name = "HrRecruitmentRequests.findByStatus", query = "SELECT h FROM HrRecruitmentRequests h WHERE h.status = :status"),
    @NamedQuery(name = "HrRecruitmentRequests.findByWfToBeModify", query = "SELECT h FROM HrRecruitmentRequests h WHERE h.status =0 OR h.status =2 OR h.status =4"),
    @NamedQuery(name = "HrRecruitmentRequests.findByWfToBeApprove", query = "SELECT h FROM HrRecruitmentRequests h WHERE h.status =1 OR h.status =3"),
    @NamedQuery(name = "HrRecruitmentRequests.findByWfPrepared", query = "SELECT h FROM HrRecruitmentRequests h WHERE h.status = 0 ORDER BY h.requestDate DESC"),
    @NamedQuery(name = "HrRecruitmentRequests.findByWfChecked", query = "SELECT h FROM HrRecruitmentRequests h WHERE h.status = 1 ORDER BY h.requestDate DESC")})
public class HrRecruitmentRequests implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_RECRUITMENT_REQUESTS_SEQ")
    @SequenceGenerator(name = "HR_RECRUITMENT_REQUESTS_SEQ", sequenceName = "HR_RECRUITMENT_REQUESTS_SEQ", allocationSize = 1)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "NUM_OF_EMPS_REQUESTED")
    private BigInteger numOfEmpsRequested;
    @Size(max = 20)
    @Column(name = "RECRUITMENT_TYPE")
    private String recruitmentType;
    @Column(name = "REQUEST_DATE")
    private String requestDate;
    @Size(max = 200)
    @Column(name = "REMARK")
    private String remark;
    @Size(max = 20)
    @Column(name = "BATCH_CODE")
    private String batchCode;
    @Column(name = "STATUS")
    private Integer status;
    @Column(name = "PREPARED_BY")
    private String preparedBy;
    @Column(name = "NUM_OF_EMPS_APPROVED")
    private BigInteger numOfEmpsApproved;
    @JoinColumn(name = "DEPT_ID", referencedColumnName = "DEP_ID")
    @ManyToOne
    private HrDepartments deptId;
    @JoinColumn(name = "REQUESTER_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrEmployees requesterId;
    @JoinColumn(name = "JOB_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrJobTypes jobId;

    @OneToMany(mappedBy = "recruitmentId", cascade = CascadeType.ALL)
    private List<WfHrProcessed> wfHrProcessedList = new ArrayList<>();

//    @OneToMany
//    private List<HrAdvertisements> hrAdvertisList;
    /**
     *
     */
    public HrRecruitmentRequests() {
    }

    /**
     *
     * @param id
     */
    public HrRecruitmentRequests(Integer id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public BigInteger getNumOfEmpsRequested() {
        return numOfEmpsRequested;
    }

    /**
     *
     * @param numOfEmpsRequested
     */
    public void setNumOfEmpsRequested(BigInteger numOfEmpsRequested) {
        this.numOfEmpsRequested = numOfEmpsRequested;
    }

    /**
     *
     * @return
     */
    public String getRecruitmentType() {
        return recruitmentType;
    }

    /**
     *
     * @param recruitmentType
     */
    public void setRecruitmentType(String recruitmentType) {
        this.recruitmentType = recruitmentType;
    }

    /**
     *
     * @return
     */
    public String getRequestDate() {
        return requestDate;
    }

    /**
     *
     * @param requestDate
     */
    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }

    /**
     *
     * @return
     */
    public String getRemark() {
        return remark;
    }

    /**
     *
     * @param remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     *
     * @return
     */
    public String getBatchCode() {
        return batchCode;
    }

    /**
     *
     * @param batchCode
     */
    public void setBatchCode(String batchCode) {
        this.batchCode = batchCode;
    }

    /**
     *
     * @return
     */
    public Integer getStatus() {
        return status;
    }

    /**
     *
     * @param status
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getPreparedBy() {
        return preparedBy;
    }

    public void setPreparedBy(String preparedBy) {
        this.preparedBy = preparedBy;
    }
    
    /**
     *
     * @return
     */
    public BigInteger getNumOfEmpsApproved() {
        return numOfEmpsApproved;
    }

    /**
     *
     * @param numOfEmpsApproved
     */
    public void setNumOfEmpsApproved(BigInteger numOfEmpsApproved) {
        this.numOfEmpsApproved = numOfEmpsApproved;
    }

    /**
     *
     * @return
     */
    public HrEmployees getRequesterId() {
        return requesterId;
    }

    /**
     *
     * @param requesterId
     */
    public void setRequesterId(HrEmployees requesterId) {
        this.requesterId = requesterId;
    }

    /**
     *
     * @return
     */
    public HrDepartments getDeptId() {
        return deptId;
    }

    /**
     *
     * @param deptId
     */
    public void setDeptId(HrDepartments deptId) {
        this.deptId = deptId;
    }

    /**
     *
     * @return
     */
    public HrJobTypes getJobId() {
        return jobId;
    }

    /**
     *
     * @param jobId
     */
    public void setJobId(HrJobTypes jobId) {
        this.jobId = jobId;
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
        if (!(object instanceof HrRecruitmentRequests)) {
            return false;
        }
        HrRecruitmentRequests other = (HrRecruitmentRequests) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        };
        return true;
    }

    @Override
    public String toString() {
        return id.toString() + "-" + deptId.getDepName();
    }

}
