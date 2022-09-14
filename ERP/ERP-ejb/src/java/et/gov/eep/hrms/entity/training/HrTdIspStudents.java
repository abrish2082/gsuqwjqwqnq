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
 * @author Benin
 */
@Entity
@Table(name = "HR_TD_ISP_STUDENTS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrTdIspStudents.findAll", query = "SELECT DISTINCT (h.year) FROM HrTdIspStudents h"),
    @NamedQuery(name = "HrTdIspStudents.findById", query = "SELECT h FROM HrTdIspStudents h WHERE h.id = :id"),
    @NamedQuery(name = "HrTdIspStudents.findByYear", query = "SELECT h FROM HrTdIspStudents h WHERE h.year = :year"),
    @NamedQuery(name = "HrTdIspStudents.findByYearSemister", query = "SELECT h FROM HrTdIspStudents h WHERE h.year = :year and h.semister = :semister "),
    @NamedQuery(name = "HrTdIspStudents.findBySemister", query = "SELECT h FROM HrTdIspStudents h WHERE h.semister = :semister"),
    @NamedQuery(name = "HrTdIspStudents.findByUniversityId", query = "SELECT h FROM HrTdIspStudents h WHERE h.universityId = :universityId"),
    @NamedQuery(name = "HrTdIspStudents.findByStartDate", query = "SELECT h FROM HrTdIspStudents h WHERE h.startDate = :startDate"),
    @NamedQuery(name = "HrTdIspStudents.findByEndDate", query = "SELECT h FROM HrTdIspStudents h WHERE h.endDate = :endDate")})
public class HrTdIspStudents implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_TD_ISP_STUDENTS_SEQ")
    @SequenceGenerator(name = "HR_TD_ISP_STUDENTS_SEQ", sequenceName = "HR_TD_ISP_STUDENTS_SEQ", allocationSize = 1)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "YEAR")
    private Integer year;
    @Column(name = "SEMISTER")
    private Integer semister;
    @Size(max = 20)
    @Column(name = "START_DATE")
    private String startDate;
    @Size(max = 20)
    @Column(name = "END_DATE")
    private String endDate;
    @JoinColumn(name = "UNIVERSITY_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrTdUniversities universityId;
    @OneToMany(mappedBy = "internshipStudentId", cascade = CascadeType.ALL)
    private List<HrTdIspStudentDetails> hrTdIspStudentDetailsList = new ArrayList<>();

    public HrTdIspStudents() {
    }

    public HrTdIspStudents(Integer id) {
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

    public Integer getSemister() {
        return semister;
    }

    public void setSemister(Integer semister) {
        this.semister = semister;
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

    public HrTdUniversities getUniversityId() {
        return universityId;
    }

    public void setUniversityId(HrTdUniversities universityId) {
        this.universityId = universityId;
    }

    @XmlTransient
    public List<HrTdIspStudentDetails> getHrTdIspStudentDetailsList() {
        if (hrTdIspStudentDetailsList == null) {
            hrTdIspStudentDetailsList = new ArrayList<>();
        }
        return hrTdIspStudentDetailsList;
    }

    public void setHrTdIspStudentDetailsList(List<HrTdIspStudentDetails> hrTdIspStudentDetailsList) {
        this.hrTdIspStudentDetailsList = hrTdIspStudentDetailsList;
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
        if (!(object instanceof HrTdIspStudents)) {
            return false;
        }
        HrTdIspStudents other = (HrTdIspStudents) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.hrms.entity.training.HrTdIspStudents[ id=" + id + " ]";
    }

    public void addToStudentDetail(HrTdIspStudentDetails hrTdIspStudentDetails) {
        if (hrTdIspStudentDetails.getInternshipStudentId() != this) {
            this.hrTdIspStudentDetailsList.add(hrTdIspStudentDetails);
            hrTdIspStudentDetails.setInternshipStudentId(this);
        }
    }
}
