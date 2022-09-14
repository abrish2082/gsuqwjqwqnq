/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.training;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Benin
 */
@Entity
@Table(name = "HR_TD_COURSES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrTdCourses.findAll", query = "SELECT h FROM HrTdCourses h"),
    @NamedQuery(name = "HrTdCourses.findById", query = "SELECT h FROM HrTdCourses h WHERE h.id = :id"),
    @NamedQuery(name = "HrTdCourses.findByCatagoyId", query = "SELECT h FROM HrTdCourses h WHERE h.categoryId.id = :categoryId"),
    @NamedQuery(name = "HrTdCourses.findByCatagoy", query = "SELECT h FROM HrTdCourses h WHERE h.categoryId = :categoryName"),
    @NamedQuery(name = "HrTdCourses.findByCatagoyIdLike", query = "SELECT h FROM HrTdCourses h WHERE h.categoryId.id Like :categoryId"),
    @NamedQuery(name = "HrTdCourses.findByCourseName", query = "SELECT h FROM HrTdCourses h WHERE h.courseName = :courseName"),
    @NamedQuery(name = "HrTdCourses.findByCourseNameLike", query = "SELECT h FROM HrTdCourses h WHERE UPPER(h.courseName) Like :courseName"),
    @NamedQuery(name = "HrTdCourses.findByDescription", query = "SELECT h FROM HrTdCourses h WHERE h.description = :description")})

@SqlResultSetMapping(name = "traning", entities = {
    @EntityResult(entityClass = HrTdCourses.class, fields = {
        @FieldResult(name = "id", column = "ID"),
        @FieldResult(name = "courseName", column = "COURSE_NAME"),
        @FieldResult(name = "description", column = "DESCRIPTION"),
        @FieldResult(name = "categoryId", column = "CATEGORY_ID"),})})
public class HrTdCourses implements Serializable {
    @OneToMany(mappedBy = "trainingId")
    private List<HrTdUnplanTrainingRequest> hrTdUnplanTrainingRequestList;

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_TD_COURSES_SEQ")
    @SequenceGenerator(name = "HR_TD_COURSES_SEQ", sequenceName = "HR_TD_COURSES_SEQ", allocationSize = 1)
    @Basic(optional = false)
    @Column(name = "ID")
    private BigDecimal id;
    @Column(name = "COURSE_NAME")
    private String courseName;
    @Column(name = "DESCRIPTION")
    private String description;

    @OneToMany(mappedBy = "trainingId")
    private List<HrTdAnnualPlans> hrTdAnnualPlansList;

    @OneToMany(mappedBy = "trainingId")
    private List<HrTdAnnualTrainingNeeds> hrTdAnnualTrainingNeedsList;

    @JoinColumn(name = "CATEGORY_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrLuTrainingCategory categoryId;

    public HrTdCourses() {
    }

    public HrTdCourses(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
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

    public HrLuTrainingCategory getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(HrLuTrainingCategory categoryId) {
        this.categoryId = categoryId;
    }

    @XmlTransient
    public List<HrTdAnnualTrainingNeeds> getHrTdAnnualTrainingNeedsList() {
        return hrTdAnnualTrainingNeedsList;
    }

    public void setHrTdAnnualTrainingNeedsList(List<HrTdAnnualTrainingNeeds> hrTdAnnualTrainingNeedsList) {
        this.hrTdAnnualTrainingNeedsList = hrTdAnnualTrainingNeedsList;
    }

    @XmlTransient
    public List<HrTdAnnualPlans> getHrTdAnnualPlansList() {
        return hrTdAnnualPlansList;
    }

    public void setHrTdAnnualPlansList(List<HrTdAnnualPlans> hrTdAnnualPlansList) {
        this.hrTdAnnualPlansList = hrTdAnnualPlansList;
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
        if (!(object instanceof HrTdCourses)) {
            return false;
        }
        HrTdCourses other = (HrTdCourses) object;
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
    public List<HrTdUnplanTrainingRequest> getHrTdUnplanTrainingRequestList() {
        return hrTdUnplanTrainingRequestList;
    }

    public void setHrTdUnplanTrainingRequestList(List<HrTdUnplanTrainingRequest> hrTdUnplanTrainingRequestList) {
        this.hrTdUnplanTrainingRequestList = hrTdUnplanTrainingRequestList;
    }

}
