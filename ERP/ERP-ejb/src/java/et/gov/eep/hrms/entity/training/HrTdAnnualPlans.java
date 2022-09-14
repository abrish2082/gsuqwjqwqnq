/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.training;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Benin
 */
@Entity
@Table(name = "HR_TD_ANNUAL_PLANS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrTdAnnualPlans.findAll", query = "SELECT h FROM HrTdAnnualPlans h"),
    @NamedQuery(name = "HrTdAnnualPlans.findById", query = "SELECT h FROM HrTdAnnualPlans h WHERE h.id = :id"),
    @NamedQuery(name = "HrTdAnnualPlans.findByYear", query = "SELECT h FROM HrTdAnnualPlans h WHERE h.year = :year"),
    @NamedQuery(name = "HrTdAnnualPlans.findByMaximumId", query = "SELECT h FROM HrTdAnnualPlans h WHERE h.id = (SELECT Max(p.id) FROM HrTdAnnualPlans p)"),
    @NamedQuery(name = "HrTdAnnualPlans.findByTotalParticipant", query = "SELECT h FROM HrTdAnnualPlans h WHERE h.totalParticipant = :totalParticipant"),
    @NamedQuery(name = "HrTdAnnualPlans.findByPreparedBy", query = "SELECT h FROM HrTdAnnualPlans h WHERE h.preparedBy = :preparedBy"),
    @NamedQuery(name = "HrTdAnnualPlans.findByPreparedOn", query = "SELECT h FROM HrTdAnnualPlans h WHERE h.preparedOn = :preparedOn")})
public class HrTdAnnualPlans implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Integer id;
    @Column(name = "YEAR")
    private Integer year;
    @Column(name = "TOTAL_PARTICIPANT")
    private Integer totalParticipant;
    @Column(name = "PREPARED_BY")
    private String preparedBy;
    @Size(max = 20)
    @Column(name = "ANN_TRA_NEED_ID")
    private String annTreNeedId;
    @Size(max = 20)
    @Column(name = "PREPARED_ON")
    private String preparedOn;
    @Column(name = "DEPT")
    private String dept;
    @JoinColumn(name = "TRAINING_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrTdCourses trainingId;

    @OneToMany(mappedBy = "annPlanId", cascade = CascadeType.ALL)
    private List<HrTdAnnualPlanGroups> hrTdAnnualPlanGroupsList;

    public HrTdAnnualPlans() {
    }

    public HrTdAnnualPlans(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getTotalParticipant() {
        return totalParticipant;
    }

    public void setTotalParticipant(Integer totalParticipant) {
        this.totalParticipant = totalParticipant;
    }

    public String getPreparedBy() {
        return preparedBy;
    }

    public void setPreparedBy(String preparedBy) {
        this.preparedBy = preparedBy;
    }

    public String getPreparedOn() {
        return preparedOn;
    }

    public void setPreparedOn(String preparedOn) {
        this.preparedOn = preparedOn;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public HrTdCourses getTrainingId() {
        return trainingId;
    }

    public void setTrainingId(HrTdCourses trainingId) {
        this.trainingId = trainingId;
    }

    @XmlTransient
    public List<HrTdAnnualPlanGroups> getHrTdAnnualPlanGroupsList() {
        if (hrTdAnnualPlanGroupsList == null) {
            hrTdAnnualPlanGroupsList = new ArrayList<>();
        }
        return hrTdAnnualPlanGroupsList;
    }

    public void setHrTdAnnualPlanGroupsList(List<HrTdAnnualPlanGroups> hrTdAnnualPlanGroupsList) {
        this.hrTdAnnualPlanGroupsList = hrTdAnnualPlanGroupsList;
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
        if (!(object instanceof HrTdAnnualPlans)) {
            return false;
        }
        HrTdAnnualPlans other = (HrTdAnnualPlans) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.hrms.entity.training.HrTdAnnualPlans[ id=" + id + " ]";
    }
    @Transient
    private int numberOfRerquest;

    public int getNumberOfRerquest() {
        return numberOfRerquest;
    }

    public void setNumberOfRerquest(int numberOfRerquest) {
        this.numberOfRerquest = numberOfRerquest;
    }

    public String getAnnTreNeedId() {
        return annTreNeedId;
    }

    public void setAnnTreNeedId(String annTreNeedId) {
        this.annTreNeedId = annTreNeedId;
    }

    public void addPlanGroup(HrTdAnnualPlanGroups hrTdAnnualPlanGroup) {
        if (hrTdAnnualPlanGroup.getAnnPlanId() != this) {
            this.getHrTdAnnualPlanGroupsList().add(hrTdAnnualPlanGroup);
            hrTdAnnualPlanGroup.setAnnPlanId(this);
        }
    }

}
