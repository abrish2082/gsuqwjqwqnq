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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author insa
 */
@Entity
@Table(name = "HR_TD_PSVC_COURSES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrTdPsvcCourses.findAll", query = "SELECT h FROM HrTdPsvcCourses h"),
    @NamedQuery(name = "HrTdPsvcCourses.findById", query = "SELECT h FROM HrTdPsvcCourses h WHERE h.id = :id"),
    @NamedQuery(name = "HrTdPsvcCourses.findbycourse", query = "SELECT h FROM HrTdPsvcCourses h WHERE h.courseTypeId.id = :id"),
    @NamedQuery(name = "HrTdPsvcCourses.findByCourseName", query = "SELECT h FROM HrTdPsvcCourses h WHERE h.courseName = :courseName"),
    @NamedQuery(name = "HrTdPsvcCourses.findByDescription", query = "SELECT h FROM HrTdPsvcCourses h WHERE h.description = :description")})
public class HrTdPsvcCourses implements Serializable {

    @OneToMany(mappedBy = "trainingId", cascade = CascadeType.ALL)
    private List<HrTdPsvcResultDetails> hrTdPsvcResultDetailsList;
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_TD_PSVC_COURSES_SEQ")
    @SequenceGenerator(name = "HR_TD_PSVC_COURSES_SEQ", sequenceName = "HR_TD_PSVC_COURSES_SEQ", allocationSize = 1)
    @NotNull
    @Column(name = "ID")
    private Integer id;
    @Size(max = 100)
    @Column(name = "COURSE_NAME")
    private String courseName;
    @Size(max = 400)
    @Column(name = "DESCRIPTION")
    private String description;
    @JoinColumn(name = "COURSE_TYPE_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrLuTdPsvcCourseTypes courseTypeId;

    public HrTdPsvcCourses() {
    }

    public HrTdPsvcCourses(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public HrLuTdPsvcCourseTypes getCourseTypeId() {
        return courseTypeId;
    }

    public void setCourseTypeId(HrLuTdPsvcCourseTypes courseTypeId) {
        this.courseTypeId = courseTypeId;
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
        if (!(object instanceof HrTdPsvcCourses)) {
            return false;
        }
        HrTdPsvcCourses other = (HrTdPsvcCourses) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.HrTdPsvcCourses[ id=" + id + " ]";
    }

    @XmlTransient
    public List<HrTdPsvcResultDetails> getHrTdPsvcResultDetailsList() {
        if (hrTdPsvcResultDetailsList == null) {
            hrTdPsvcResultDetailsList = new ArrayList<>();
        }
        return hrTdPsvcResultDetailsList;
    }

    public void setHrTdPsvcResultDetailsList(List<HrTdPsvcResultDetails> hrTdPsvcResultDetailsList) {
        this.hrTdPsvcResultDetailsList = hrTdPsvcResultDetailsList;
    }

}
