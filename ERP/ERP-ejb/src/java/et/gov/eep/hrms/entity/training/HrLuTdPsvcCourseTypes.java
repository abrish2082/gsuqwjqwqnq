/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.training;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
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
@Table(name = "HR_LU_TD_PSVC_COURSE_TYPES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrLuTdPsvcCourseTypes.findAll", query = "SELECT h FROM HrLuTdPsvcCourseTypes h"),
    @NamedQuery(name = "HrLuTdPsvcCourseTypes.findById", query = "SELECT h FROM HrLuTdPsvcCourseTypes h WHERE h.id = :id"),
    @NamedQuery(name = "HrLuTdPsvcCourseTypes.findByCourseTypes", query = "SELECT h FROM HrLuTdPsvcCourseTypes h WHERE h.courseTypes = :courseTypes"),
    @NamedQuery(name = "HrLuTdPsvcCourseTypes.findByDescription", query = "SELECT h FROM HrLuTdPsvcCourseTypes h WHERE h.description = :description")})
public class HrLuTdPsvcCourseTypes implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Integer id;
    @Size(max = 20)
    @Column(name = "COURSE_TYPES")
    private String courseTypes;
    @Size(max = 100)
    @Column(name = "DESCRIPTION")
    private String description;
    @OneToMany(mappedBy = "courseTypeId")
    private List<HrTdPsvcCourses> hrTdPsvcCoursesList;

    public HrLuTdPsvcCourseTypes() {
    }

    public HrLuTdPsvcCourseTypes(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCourseTypes() {
        return courseTypes;
    }

    public void setCourseTypes(String courseTypes) {
        this.courseTypes = courseTypes;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @XmlTransient
    public List<HrTdPsvcCourses> getHrTdPsvcCoursesList() {
        return hrTdPsvcCoursesList;
    }

    public void setHrTdPsvcCoursesList(List<HrTdPsvcCourses> hrTdPsvcCoursesList) {
        this.hrTdPsvcCoursesList = hrTdPsvcCoursesList;
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
        if (!(object instanceof HrLuTdPsvcCourseTypes)) {
            return false;
        }
        HrLuTdPsvcCourseTypes other = (HrLuTdPsvcCourseTypes) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return id.toString();
    }

}
