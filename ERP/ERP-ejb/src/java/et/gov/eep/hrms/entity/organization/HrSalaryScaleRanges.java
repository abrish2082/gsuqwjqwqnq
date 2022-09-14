/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.organization;

import et.gov.eep.fcms.entity.perDiem.FmsLuPerdimeRate;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.lookup.HrLuJobLevels;
import et.gov.eep.hrms.entity.lookup.HrLuGrades;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
 * @author Ob
 */
@Entity
@Table(name = "HR_SALARY_SCALE_RANGES")
@XmlRootElement
@NamedQueries//<editor-fold defaultstate="collapsed" desc="">
        ({
            @NamedQuery(name = "HrSalaryScaleRanges.findAll", query = "SELECT h FROM HrSalaryScaleRanges h"),
            @NamedQuery(name = "HrSalaryScaleRanges.findById", query = "SELECT h FROM HrSalaryScaleRanges h WHERE h.id = :id"),
            @NamedQuery(name = "HrSalaryScaleRanges.findByJobLevelId", query = "SELECT h FROM HrSalaryScaleRanges h WHERE h.levelId = :levelId"),
            @NamedQuery(name = "HrSalaryScaleRanges.findByGrade", query = "SELECT h FROM HrSalaryScaleRanges h WHERE h.gradeId = :hrLuGrades"),
            @NamedQuery(name = "HrSalaryScaleRanges.findByMinSalary", query = "SELECT h FROM HrSalaryScaleRanges h WHERE h.minSalary = :minSalary"),
            @NamedQuery(name = "HrSalaryScaleRanges.findByMaxSalary", query = "SELECT h FROM HrSalaryScaleRanges h WHERE h.maxSalary = :maxSalary"),
            @NamedQuery(name = "HrSalaryScaleRanges.findByDescription", query = "SELECT h FROM HrSalaryScaleRanges h WHERE h.description = :description"),
            @NamedQuery(name = "HrSalaryScaleRanges.findByGradeRank", query = "SELECT h FROM HrSalaryScaleRanges h WHERE h.gradeRank = :gradeRank"),
            @NamedQuery(name = "HrSalaryScaleRanges.findByLevel", query = "SELECT DISTINCT(h.gradeId.description) FROM HrSalaryScaleRanges h  where h.levelId.id=:levelId"),
            @NamedQuery(name = "HrSalaryScaleRanges.findGrades", query = "SELECT h FROM HrSalaryScaleRanges h where h.gradeId.grade=:grade")})
//</editor-fold>
public class HrSalaryScaleRanges implements Serializable {

//<editor-fold defaultstate="collapsed" desc="Entity Attributes">
    @Column(name = "MIN_SALARY")
    private Double minSalary;
    @Column(name = "MAX_SALARY")
    private Double maxSalary;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "INCREMENT_RATE")
    private float incrementRate;
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_SALARY_SCALE_RANGES_SEQ")
    @SequenceGenerator(name = "HR_SALARY_SCALE_RANGES_SEQ", sequenceName = "HR_SALARY_SCALE_RANGES_SEQ", allocationSize = 1)
    private Integer id;
    @Size(max = 50)
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "GRADE_RANK")
    private BigInteger gradeRank;
    @JoinColumn(name = "GRADE_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrLuGrades gradeId;
    @JoinColumn(name = "LEVEL_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrLuJobLevels levelId;
    @OneToMany(mappedBy = "salaryRangeId", cascade = CascadeType.ALL)
    private List<HrSalaryScales> hrSalaryScalesList;
    @OneToMany(mappedBy = "gradeId", fetch = FetchType.LAZY)
    private List<HrEmployees> hrEmployeesList;
    @OneToMany(mappedBy = "jobGradeId", fetch = FetchType.LAZY)
    private List<HrJobTypes> hrJobTypesList;
//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="Getter and setter">
    public HrSalaryScaleRanges() {
    }
    
    public HrSalaryScaleRanges(Integer id) {
        this.id = id;
    }
    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public Double getMinSalary() {
        return minSalary;
    }
    
    public void setMinSalary(Double minSalary) {
        this.minSalary = minSalary;
    }
    
    public Double getMaxSalary() {
        return maxSalary;
    }
    
    public void setMaxSalary(Double maxSalary) {
        this.maxSalary = maxSalary;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public BigInteger getGradeRank() {
        return gradeRank;
    }
    
    public void setGradeRank(BigInteger gradeRank) {
        this.gradeRank = gradeRank;
    }
    
    public HrLuGrades getGradeId() {
        return gradeId;
    }
    
    public void setGradeId(HrLuGrades gradeId) {
        this.gradeId = gradeId;
    }
    
    public HrLuJobLevels getLevelId() {
        return levelId;
    }
    
    public void setLevelId(HrLuJobLevels levelId) {
        this.levelId = levelId;
    }
    
    @XmlTransient
    public List<HrSalaryScales> getHrSalaryScalesList() {
        if (hrSalaryScalesList == null) {
            hrSalaryScalesList = new ArrayList<>();
        }
        return hrSalaryScalesList;
    }
    
    public void setHrSalaryScalesList(List<HrSalaryScales> hrSalaryScalesList) {
        this.hrSalaryScalesList = hrSalaryScalesList;
    }
     public float getIncrementRate() {
        return incrementRate;
    }

    public void setIncrementRate(float incrementRate) {
        this.incrementRate = incrementRate;
    }

    @XmlTransient
    public List<HrEmployees> getHrEmployeesList() {
        return hrEmployeesList;
    }

    public void setHrEmployeesList(List<HrEmployees> hrEmployeesList) {
        this.hrEmployeesList = hrEmployeesList;
    }

    @XmlTransient
    public List<HrJobTypes> getHrJobTypesList() {
        return hrJobTypesList;
    }

    public void setHrJobTypesList(List<HrJobTypes> hrJobTypesList) {
        this.hrJobTypesList = hrJobTypesList;
    }

//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="other Methods">
    
    public void addPayGrade(HrSalaryScales hrSalaryScales) {
        if (hrSalaryScales.getSalaryRangeId() != this) {
            this.getHrSalaryScalesList().add(hrSalaryScales);
            hrSalaryScales.setSalaryRangeId(this);
        }
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
        if (!(object instanceof HrSalaryScaleRanges)) {
            return false;
        }
        HrSalaryScaleRanges other = (HrSalaryScaleRanges) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return id.toString();
    }
//</editor-fold>
   

    }

    //}
