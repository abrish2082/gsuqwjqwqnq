/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.payroll;

import java.io.Serializable;
import java.util.Collection;
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
import et.gov.eep.hrms.entity.allowance.HrAllowanceInJobTitle;
import et.gov.eep.hrms.entity.allowance.HrAllowanceInLevels;
import et.gov.eep.hrms.entity.allowance.HrAllowanceInLocations;

/**
 *
 * @author munir
 */
@Entity
@Table(name = "HR_PAYROLL_PL_PG")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrPayrollPgPl.findAll", query = "SELECT h FROM HrPayrollPlPg h order by h.payLocation ASC"),
    @NamedQuery(name = "HrPayrollPgPl.findById", query = "SELECT h FROM HrPayrollPlPg h WHERE h.id = :id"),
    @NamedQuery(name = "HrPayrollPgPl.findByPayLocation", query = "SELECT h FROM HrPayrollPlPg h WHERE h.payLocation = :payLocation"),
    @NamedQuery(name = "HrPayrollPgPl.findByPayGroup", query = "SELECT h FROM HrPayrollPlPg h WHERE h.payGroup = :payGroup"),
    @NamedQuery(name = "HrPayrollPgPl.findByRemark", query = "SELECT h FROM HrPayrollPlPg h WHERE h.remark = :remark"),
    @NamedQuery(name = "HrPayrollPgPl.findByDescription", query = "SELECT h FROM HrPayrollPlPg h WHERE h.description = :description"),})
public class HrPayrollPlPg implements Serializable {

    @Column(name = "PAY_LOCATION")
    private Integer payLocation;
    @Column(name = "PAY_GROUP")
    private Integer payGroup;
    @OneToMany(mappedBy = "plPg")
    private Collection<HrPayrollCourtCaseInfo> hrPayrollCourtCaseInfoCollection;

    @OneToMany(mappedBy = "plPg")
    private Collection<HrPayrollMorgageInfo> hrPayrollMorgageInfoCollection;

    @OneToMany(mappedBy = "plPg")
    private Collection<HrPayrollFamiliesInfo> hrPayrollFamiliesInfoCollection;
    
    @OneToMany(mappedBy = "payLocationPayGroupId")
    private List<HrPayrollPlPgDept> hrPayrollPlPgDeptList;
    @OneToMany(mappedBy = "jlJl")
    private Collection<HrAllowanceInLocations> hrAllowanceInLocationsCollection;
    @OneToMany(mappedBy = "jpJl")
    private Collection<HrAllowanceInJobTitle> hrAllowanceInJobTitleCollection;
    @OneToMany(mappedBy = "plPg")
    private Collection<HrAllowanceInLevels> hrAllowanceInLevelsCollection;

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation

    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_PAYROLL_PL_PG_SEQ")
    @SequenceGenerator(name = "HR_PAYROLL_PL_PG_SEQ", sequenceName = "HR_PAYROLL_PL_PG_SEQ", allocationSize = 1)
    @Column(name = "ID")
    private Integer id;

    @Size(max = 200)
    @Column(name = "REMARK")
    private String remark;

    @Size(max = 200)
    @Column(name = "DESCRIPTION")
    private String description;
    //<editor-fold defaultstate="collapsed" desc="static variables">
    public static final String ALLDEPARTMENTS = "0";
    public static final String INCLUDED = "1";
    public static final String NON_INCLUDED = "2";
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="getters & setters">

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPayLocation() {
        return payLocation;
    }

    public void setPayLocation(Integer payLocation) {
        this.payLocation = payLocation;
    }

    public Integer getPayGroup() {
        return payGroup;
    }

    public void setPayGroup(Integer payGroup) {
        this.payGroup = payGroup;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public HrPayrollPlPg() {
    }

    public HrPayrollPlPg(Integer id) {
        this.id = id;
    }
//</editor-fold>

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrPayrollPlPg)) {
            return false;
        }
        HrPayrollPlPg other = (HrPayrollPlPg) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.hrms.entity.payroll.HrPayrollPgPl[ id=" + id + " ]";
    }

    @XmlTransient
    public List<HrPayrollPlPgDept> getHrPayrollPlPgDeptList() {
        return hrPayrollPlPgDeptList;
    }

    public void setHrPayrollPlPgDeptList(List<HrPayrollPlPgDept> hrPayrollPlPgDeptList) {
        this.hrPayrollPlPgDeptList = hrPayrollPlPgDeptList;
    }

    @XmlTransient
    public Collection<HrAllowanceInLocations> getHrAllowanceInLocationsCollection() {
        return hrAllowanceInLocationsCollection;
    }

    public void setHrAllowanceInLocationsCollection(Collection<HrAllowanceInLocations> hrAllowanceInLocationsCollection) {
        this.hrAllowanceInLocationsCollection = hrAllowanceInLocationsCollection;
    }

    @XmlTransient
    public Collection<HrAllowanceInJobTitle> getHrAllowanceInJobTitleCollection() {
        return hrAllowanceInJobTitleCollection;
    }

    public void setHrAllowanceInJobTitleCollection(Collection<HrAllowanceInJobTitle> hrAllowanceInJobTitleCollection) {
        this.hrAllowanceInJobTitleCollection = hrAllowanceInJobTitleCollection;
    }

    @XmlTransient
    public Collection<HrAllowanceInLevels> getHrAllowanceInLevelsCollection() {
        return hrAllowanceInLevelsCollection;
    }

    public void setHrAllowanceInLevelsCollection(Collection<HrAllowanceInLevels> hrAllowanceInLevelsCollection) {
        this.hrAllowanceInLevelsCollection = hrAllowanceInLevelsCollection;
    }

    @XmlTransient
    public Collection<HrPayrollMorgageInfo> getHrPayrollMorgageInfoCollection() {
        return hrPayrollMorgageInfoCollection;
    }

    public void setHrPayrollMorgageInfoCollection(Collection<HrPayrollMorgageInfo> hrPayrollMorgageInfoCollection) {
        this.hrPayrollMorgageInfoCollection = hrPayrollMorgageInfoCollection;
    }

    @XmlTransient
    public Collection<HrPayrollFamiliesInfo> getHrPayrollFamiliesInfoCollection() {
        return hrPayrollFamiliesInfoCollection;
    }

    public void setHrPayrollFamiliesInfoCollection(Collection<HrPayrollFamiliesInfo> hrPayrollFamiliesInfoCollection) {
        this.hrPayrollFamiliesInfoCollection = hrPayrollFamiliesInfoCollection;
    }

    @XmlTransient
    public Collection<HrPayrollCourtCaseInfo> getHrPayrollCourtCaseInfoCollection() {
        return hrPayrollCourtCaseInfoCollection;
    }

    public void setHrPayrollCourtCaseInfoCollection(Collection<HrPayrollCourtCaseInfo> hrPayrollCourtCaseInfoCollection) {
        this.hrPayrollCourtCaseInfoCollection = hrPayrollCourtCaseInfoCollection;
    }
}
