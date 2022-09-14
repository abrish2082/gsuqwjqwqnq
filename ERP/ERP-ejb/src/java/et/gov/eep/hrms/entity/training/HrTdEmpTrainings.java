/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.training;

import et.gov.eep.hrms.entity.employee.HrEmployees;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Abdi_Pc
 */
@Entity
@Table(name = "HR_TD_EMP_TRAININGS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrTdEmpTrainings.findAll", query = "SELECT h FROM HrTdEmpTrainings h"),
    @NamedQuery(name = "HrTdEmpTrainings.findById", query = "SELECT h FROM HrTdEmpTrainings h WHERE h.id = :id"),
    @NamedQuery(name = "HrTdEmpTrainings.findByType", query = "SELECT h FROM HrTdEmpTrainings h WHERE h.type = :type"),

    @NamedQuery(name = "HrTdEmpTrainings.findByPlanned", query = "SELECT h FROM HrTdEmpTrainings h WHERE h.type = 1"),
    @NamedQuery(name = "HrTdEmpTrainings.findByUnplanned", query = "SELECT h FROM HrTdEmpTrainings h WHERE h.type = 2"),
    @NamedQuery(name = "HrTdEmpTrainings.findByEmpId", query = "SELECT h FROM HrTdEmpTrainings h WHERE h.empId = :empId"),
    @NamedQuery(name = "HrTdEmpTrainings.findByEmpIds", query = "SELECT h FROM HrTdEmpTrainings h WHERE h.empId = :empIds"),

    @NamedQuery(name = "HrTdEmpTrainings.findByStatus", query = "SELECT h FROM HrTdEmpTrainings h WHERE h.status = :status"),
    @NamedQuery(name = "HrTdEmpTrainings.findByStartDate", query = "SELECT h FROM HrTdEmpTrainings h WHERE h.startDate = :startDate"),
    @NamedQuery(name = "HrTdEmpTrainings.findByEndDate", query = "SELECT h FROM HrTdEmpTrainings h WHERE h.endDate = :endDate"),
    @NamedQuery(name = "HrTdEmpTrainings.findBySponsoredBy", query = "SELECT h FROM HrTdEmpTrainings h WHERE h.sponsoredBy = :sponsoredBy"),
    @NamedQuery(name = "HrTdEmpTrainings.findByRemark", query = "SELECT h FROM HrTdEmpTrainings h WHERE h.remark = :remark"),
    @NamedQuery(name = "HrTdEmpTrainings.findByTraParId", query = "SELECT h FROM HrTdEmpTrainings h WHERE h.traParId = :traParId")})
public class HrTdEmpTrainings implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_TD_EMP_TRAINING_SEQ")
    @SequenceGenerator(name = "HR_TD_EMP_TRAINING_SEQ", sequenceName = "HR_TD_EMP_TRAINING_SEQ", allocationSize = 1)
    @NotNull
    @Column(name = "ID")
    private BigDecimal id;
    @Column(name = "TYPE")
    private Integer type;
    @Column(name = "STATUS")
    private Integer status;
    @Size(max = 20)
    @Column(name = "START_DATE")
    private String startDate;
    @Size(max = 20)
    @Column(name = "END_DATE")
    private String endDate;
    @Size(max = 20)
    @Column(name = "SPONSORED_BY")
    private String sponsoredBy;
    @Size(max = 200)
    @Column(name = "REMARK")
    private String remark;
    @Size(max = 20)
    @Column(name = "TRA_PAR_ID")
    private String traParId;
    @JoinColumn(name = "EMP_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrEmployees empId;
    @JoinColumn(name = "TRAINING_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrTdCourses trainingId;
    @JoinColumn(name = "INS_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrTdTrainerProfiles insId;
//    @OneToMany(mappedBy = "empTraId", cascade = CascadeType.ALL)
//    private List<HrEmpTrainings> hrTrainingList;

    public HrTdEmpTrainings() {
    }

    public HrTdEmpTrainings(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getSponsoredBy() {
        return sponsoredBy;
    }

    public void setSponsoredBy(String sponsoredBy) {
        this.sponsoredBy = sponsoredBy;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTraParId() {
        return traParId;
    }

    public void setTraParId(String traParId) {
        this.traParId = traParId;
    }

    public HrEmployees getEmpId() {
        return empId;
    }

    public void setEmpId(HrEmployees empId) {
        this.empId = empId;
    }

    public HrTdCourses getTrainingId() {
        return trainingId;
    }

    public void setTrainingId(HrTdCourses trainingId) {
        this.trainingId = trainingId;
    }

    public HrTdTrainerProfiles getInsId() {
        return insId;
    }

    public void setInsId(HrTdTrainerProfiles insId) {
        this.insId = insId;
    }
    @Transient
    String ChangedStstus;
    @Transient
    String ChangedType;

    public String getChangedStstus() {
        return ChangedStstus;
    }

    public void setChangedStstus(String ChangedStstus) {
        this.ChangedStstus = ChangedStstus;
    }

//    @XmlTransient
//    public List<HrEmpTrainings> getHrTrainingList() {
//        if (hrTrainingList==null) {
//            hrTrainingList = new ArrayList<>();
//        }
//        return hrTrainingList;
//    }
//
//    public void setHrTrainingList(List<HrEmpTrainings> hrTrainingList) {
//        this.hrTrainingList = hrTrainingList;
//    }

    public String getChangedType() {
        return ChangedType;
    }

    public void setChangedType(String ChangedType) {
        this.ChangedType = ChangedType;
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
        if (!(object instanceof HrTdEmpTrainings)) {
            return false;
        }
        HrTdEmpTrainings other = (HrTdEmpTrainings) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entit.HrTdEmpTrainings[ id=" + id + " ]";
    }

    public void addToDataTable() {
//        if (hrTdUnplanTraParticipant.getUnpTraReqId() != this) {
//            this.getHrTdUnplanTraParticipantList().add(hrTdUnplanTraParticipant);
//            hrTdUnplanTraParticipant.setUnpTraReqId(this);
//        }
    }

}
