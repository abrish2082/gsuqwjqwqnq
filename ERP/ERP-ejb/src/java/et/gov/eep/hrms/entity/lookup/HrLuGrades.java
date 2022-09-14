/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.lookup;

import et.gov.eep.fcms.entity.perDiem.FmsLuPerdimeRate;
import et.gov.eep.hrms.entity.organization.HrSalaryScaleRanges;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author user
 */
@Entity
@Table(name = "HR_LU_GRADES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrLuGrades.findAll", query = "SELECT h FROM HrLuGrades h"),
    @NamedQuery(name = "HrLuGrades.findById", query = "SELECT h FROM HrLuGrades h WHERE h.id = :id"),
    @NamedQuery(name = "HrLuGrades.findByGrade", query = "SELECT h FROM HrLuGrades h WHERE h.grade = :grade"),
    @NamedQuery(name = "HrLuGrades.findByGradeLike", query = "SELECT h FROM HrLuGrades h WHERE UPPER(h.grade) LIKE :grade"),
    @NamedQuery(name = "HrLuGrades.findByDescription", query = "SELECT h FROM HrLuGrades h WHERE h.description = :description")})
public class HrLuGrades implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_LU_GRADES_SEQ")
    @SequenceGenerator(name = "HR_LU_GRADES_SEQ", sequenceName = "HR_LU_GRADES_SEQ", allocationSize = 1)
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private BigDecimal id;
    @Size(max = 20)
    @Column(name = "GRADE")
    private String grade;
    @Size(max = 50)
    @Column(name = "DESCRIPTION")
    private String description;
    @OneToMany(mappedBy = "gradeId")
    private List<HrSalaryScaleRanges> hrSalaryScaleRangesList;

    /**
     *
     */
    public HrLuGrades() {
    }

    /**
     *
     * @param id
     */
    public HrLuGrades(BigDecimal id) {
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

    /**
     *
     * @return
     */
    public String getGrade() {
        return grade;
    }

    /**
     *
     * @param grade
     */
    public void setGrade(String grade) {
        this.grade = grade;
    }

    /**
     *
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     *
     * @return
     */
    @XmlTransient
    public List<HrSalaryScaleRanges> getHrSalaryScaleRangesList() {
        return hrSalaryScaleRangesList;
    }

    /**
     *
     * @param hrSalaryScaleRangesList
     */
    public void setHrSalaryScaleRangesList(List<HrSalaryScaleRanges> hrSalaryScaleRangesList) {
        this.hrSalaryScaleRangesList = hrSalaryScaleRangesList;
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
        if (!(object instanceof HrLuGrades)) {
            return false;
        }
        HrLuGrades other = (HrLuGrades) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return grade;
    }

}
