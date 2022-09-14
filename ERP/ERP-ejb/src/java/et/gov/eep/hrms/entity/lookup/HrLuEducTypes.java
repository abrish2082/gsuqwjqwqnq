/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.lookup;

import et.gov.eep.hrms.entity.employee.HrEmpEducations;
import et.gov.eep.hrms.entity.organization.HrJobEducQualifications;
import et.gov.eep.hrms.entity.recruitment.HrCandidiateEducations;
import et.gov.eep.hrms.entity.training.HrTdPsvcTraineeDetails;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
 * @author Administrator
 */
@Entity
@Table(name = "HR_LU_EDUC_TYPES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrLuEducTypes.findAll", query = "SELECT h FROM HrLuEducTypes h"),
    @NamedQuery(name = "HrLuEducTypes.findById", query = "SELECT h FROM HrLuEducTypes h WHERE h.id = :id"),
    @NamedQuery(name = "HrLuEducTypes.findByEducType", query = "SELECT h FROM HrLuEducTypes h WHERE h.educType = :educType")})
public class HrLuEducTypes implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private BigDecimal id;
    @Column(name = "EDUC_TYPE")
    private String educType;
    @OneToMany(mappedBy = "educQualId")
    private List<HrTdPsvcTraineeDetails> hrTdPsvcTraineeDetailsList;
    @OneToMany(mappedBy = "educTypeId")
    private List<HrCandidiateEducations> hrCandidiateEducationsList;
    @OneToMany(mappedBy = "educTypeId")
    private List<HrEmpEducations> hrEmpEducationsList;
    @OneToMany(mappedBy = "educQualId")
    private List<HrJobEducQualifications> hrJobEducQualificationsList;

    /**
     *
     */
    public HrLuEducTypes() {
    }

    /**
     *
     * @param id
     */
    public HrLuEducTypes(BigDecimal id) {
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
    public String getEducType() {
        return educType;
    }

    /**
     *
     * @param educType
     */
    public void setEducType(String educType) {
        this.educType = educType;
    }

    @XmlTransient
    public List<HrTdPsvcTraineeDetails> getHrTdPsvcTraineeDetailsList() {
        if (hrTdPsvcTraineeDetailsList == null) {
            hrTdPsvcTraineeDetailsList = new ArrayList<>();
        }
        return hrTdPsvcTraineeDetailsList;
    }

    public void setHrTdPsvcTraineeDetailsList(List<HrTdPsvcTraineeDetails> hrTdPsvcTraineeDetailsList) {
        this.hrTdPsvcTraineeDetailsList = hrTdPsvcTraineeDetailsList;
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
        if (!(object instanceof HrLuEducTypes)) {
            return false;
        }
        HrLuEducTypes other = (HrLuEducTypes) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return educType;
    }

    /**
     *
     * @return
     */
    @XmlTransient
    public List<HrJobEducQualifications> getHrJobEducQualificationsList() {
        return hrJobEducQualificationsList;
    }

    /**
     *
     * @param hrJobEducQualificationsList
     */
    public void setHrJobEducQualificationsList(List<HrJobEducQualifications> hrJobEducQualificationsList) {
        this.hrJobEducQualificationsList = hrJobEducQualificationsList;
    }

    /**
     *
     * @return
     */
    @XmlTransient
    public List<HrEmpEducations> getHrEmpEducationsList() {
        return hrEmpEducationsList;
    }

    /**
     *
     * @param hrEmpEducationsList
     */
    public void setHrEmpEducationsList(List<HrEmpEducations> hrEmpEducationsList) {
        this.hrEmpEducationsList = hrEmpEducationsList;
    }

    @XmlTransient
    public List<HrCandidiateEducations> getHrCandidiateEducationsList() {
        return hrCandidiateEducationsList;
    }

    public void setHrCandidiateEducationsList(List<HrCandidiateEducations> hrCandidiateEducationsList) {
        this.hrCandidiateEducationsList = hrCandidiateEducationsList;
    }

}
