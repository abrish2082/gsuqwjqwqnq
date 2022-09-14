/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.recruitment;

import et.gov.eep.hrms.entity.organization.HrJobTypes;
import et.gov.eep.hrms.entity.promotion.HrPromotionRequests;
import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "HR_ADVERTISED_JOBS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrAdvertisedJobs.findAll", query = "SELECT h FROM HrAdvertisedJobs h"),
    @NamedQuery(name = "HrAdvertisedJobs.findById", query = "SELECT h FROM HrAdvertisedJobs h WHERE h.id = :id"),
    @NamedQuery(name = "HrAdvertisedJobs.findByRecruitmentType", query = "SELECT h FROM HrAdvertisedJobs h WHERE h.recruitmentType = :recruitmentType"),
    @NamedQuery(name = "HrAdvertisedJobs.findByEmpNeeded", query = "SELECT h FROM HrAdvertisedJobs h WHERE h.empNeeded = :empNeeded"),
    @NamedQuery(name = "HrAdvertisedJobs.findByRecruitRequestId", query = "SELECT h FROM HrAdvertisedJobs h WHERE h.recruitRequestId = :recruitRequestId"),
    @NamedQuery(name = "HrAdvertisedJobs.findByAgeLimit", query = "SELECT h FROM HrAdvertisedJobs h WHERE h.ageLimit = :ageLimit"),
    @NamedQuery(name = "HrAdvertisedJobs.findByHrAdvertisements", query = "SELECT h FROM HrAdvertisedJobs h WHERE h.advertId = :advertId"),
    @NamedQuery(name = "HrAdvertisedJobs.findByDutyStation", query = "SELECT h FROM HrAdvertisedJobs h WHERE h.dutyStation = :dutyStation"),
    @NamedQuery(name = "HrAdvertisedJobs.findByAdvertId", query = "SELECT h FROM HrAdvertisedJobs h WHERE h.advertId.id = :advertId")})
public class HrAdvertisedJobs implements Serializable {
    
    @OneToMany(mappedBy = "advertJobId",cascade = CascadeType.ALL)
    private List<HrPromotionRequests> hrPromotionRequestsList = new ArrayList<>();

    @Size(max = 100)
    @Column(name = "RECRUIT_REQUEST_ID")
    private String recruitRequestId;

    @OneToMany(mappedBy = "advJobId")
    private List<HrCandidiates> hrCandidiatesList;
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_ADVERTISED_JOBS_SEQ")
    @SequenceGenerator(name = "HR_ADVERTISED_JOBS_SEQ", sequenceName = "HR_ADVERTISED_JOBS_SEQ", allocationSize = 1)
    @Column(name = "ID")
    private BigDecimal id;

    @Size(max = 100)
    @Column(name = "RECRUITMENT_TYPE")
    private String recruitmentType;

    @Size(max = 100)
    @Column(name = "EMP_NEEDED")
    private String empNeeded;

    @Size(max = 100)
    @Column(name = "AGE_LIMIT")
    private String ageLimit;

    @Size(max = 100)
    @Column(name = "DUTY_STATION")
    private String dutyStation;

    @JoinColumn(name = "ADVERT_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrAdvertisements advertId;
    
    @JoinColumn(name = "JOB_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrJobTypes jobId;

    /**
     *
     */
    public HrAdvertisedJobs() {
    }

    /**
     *
     * @param id
     */
    public HrAdvertisedJobs(BigDecimal id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public BigDecimal getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getRecruitmentType() {
        return recruitmentType;
    }

    public void setRecruitmentType(String recruitmentType) {
        this.recruitmentType = recruitmentType;
    }

    /**
     *
     * @return
     */
    public String getEmpNeeded() {
        return empNeeded;
    }

    /**
     *
     * @param empNeeded
     */
    public void setEmpNeeded(String empNeeded) {
        this.empNeeded = empNeeded;
    }

    /**
     *
     * @return
     */
    public String getRecruitRequestId() {
        return recruitRequestId;
    }

    /**
     *
     * @param recruitRequestId
     */
    public void setRecruitRequestId(String recruitRequestId) {
        this.recruitRequestId = recruitRequestId;
    }

    /**
     *
     * @return
     */
    public String getAgeLimit() {
        return ageLimit;
    }

    /**
     *
     * @param ageLimit
     */
    public void setAgeLimit(String ageLimit) {
        this.ageLimit = ageLimit;
    }

    /**
     *
     * @return
     */
    public String getDutyStation() {
        return dutyStation;
    }

    /**
     *
     * @param dutyStation
     */
    public void setDutyStation(String dutyStation) {
        this.dutyStation = dutyStation;
    }

    /**
     *
     * @return
     */
    public HrAdvertisements getAdvertId() {
        return advertId;
    }

    /**
     *
     * @param advertId
     */
    public void setAdvertId(HrAdvertisements advertId) {
        this.advertId = advertId;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrAdvertisedJobs)) {
            return false;
        }
        HrAdvertisedJobs other = (HrAdvertisedJobs) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return id.toString();
    }

    @XmlTransient
    public List<HrCandidiates> getHrCandidiatesList() {
        return hrCandidiatesList;
    }

    public void setHrCandidiatesList(List<HrCandidiates> hrCandidiatesList) {
        this.hrCandidiatesList = hrCandidiatesList;
    }

    @XmlTransient
    public List<HrPromotionRequests> getHrPromotionRequestsList() {
        return hrPromotionRequestsList;
    }

    public void setHrPromotionRequestsList(List<HrPromotionRequests> hrPromotionRequestsList) {
        this.hrPromotionRequestsList = hrPromotionRequestsList;
    }

}
