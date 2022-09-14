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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
 * @author Mac
 */
@Entity
@Table(name = "HR_LU_TRAINING_CATEGORY")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrLuTrainingCategory.findAll", query = "SELECT h FROM HrLuTrainingCategory h"),
    @NamedQuery(name = "HrLuTrainingCategory.findById", query = "SELECT h FROM HrLuTrainingCategory h WHERE h.id = :id"),
    @NamedQuery(name = "HrLuTrainingCategory.findByIdc", query = "SELECT h FROM HrTdCourses h WHERE h.categoryId = :id"),
    @NamedQuery(name = "HrLuTrainingCategory.findByCategoryName", query = "SELECT h FROM HrLuTrainingCategory h WHERE h.categoryName = :categoryName"),
    @NamedQuery(name = "HrLuTrainingCategory.findByDescription", query = "SELECT h FROM HrLuTrainingCategory h WHERE h.description = :description")})
public class HrLuTrainingCategory implements Serializable {

    @OneToMany(mappedBy = "categoryId")
    private List<HrTdCourses> hrTdCoursesList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_LU_TRAINING_CATEGORY_SEQ")
    @SequenceGenerator(name = "HR_LU_TRAINING_CATEGORY_SEQ", sequenceName = "HR_LU_TRAINING_CATEGORY_SEQ", allocationSize = 1)
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Integer id;
    @Size(max = 50)
    @Column(name = "CATEGORY_NAME")
    private String categoryName;
    @Size(max = 200)
    @Column(name = "DESCRIPTION")
    private String description;

//    @OneToMany(mappedBy = "categoryId")
//    private List<HrTdCourses> hrTdCoursesList;
    public HrLuTrainingCategory() {
    }

    public HrLuTrainingCategory(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        if (!(object instanceof HrLuTrainingCategory)) {
            return false;
        }
        HrLuTrainingCategory other = (HrLuTrainingCategory) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return categoryName;
    }

//    public void addToHrTrainingCourses(HrTdCourses hrTdCourses) {
//        if (hrTdCourses.getCategoryId() != this) {
//            this.hrTdCoursesList.add(hrTdCourses);
//            hrTdCourses.setCategoryId(this);
//        }
//    }
//    @XmlTransient
//    public List<HrTdCourses> getHrTdCoursesList() {
//        if (hrTdCoursesList == null) {
//            hrTdCoursesList = new ArrayList<>();
//        }
//        return hrTdCoursesList;
//    }
//
//    public void setHrTdCoursesList(List<HrTdCourses> hrTdCoursesList) {
//        this.hrTdCoursesList = hrTdCoursesList;
//    }
    @XmlTransient
    public List<HrTdCourses> getHrTdCoursesList() {
        return hrTdCoursesList;
    }

    public void setHrTdCoursesList(List<HrTdCourses> hrTdCoursesList) {
        this.hrTdCoursesList = hrTdCoursesList;
    }

}
