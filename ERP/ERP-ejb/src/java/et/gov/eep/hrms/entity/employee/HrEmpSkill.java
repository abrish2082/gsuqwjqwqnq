/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.employee;

import et.gov.eep.hrms.entity.employee.HrEmployees;
import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author user
 */
@Entity
@Table(name = "HR_EMP_SKILL")
@XmlRootElement
@NamedQueries//<editor-fold defaultstate="collapsed" desc="">
        ({
            @NamedQuery(name = "HrEmpSkill.findAll", query = "SELECT h FROM HrEmpSkill h"),
            @NamedQuery(name = "HrEmpSkill.findById", query = "SELECT h FROM HrEmpSkill h WHERE h.id = :id"),
            @NamedQuery(name = "HrEmpSkill.findBySkillType", query = "SELECT h FROM HrEmpSkill h WHERE h.skillType = :skillType"),
            @NamedQuery(name = "HrEmpSkill.findBySkillLevel", query = "SELECT h FROM HrEmpSkill h WHERE h.skillLevel = :skillLevel"),
            @NamedQuery(name = "HrEmpSkill.findByDescription", query = "SELECT h FROM HrEmpSkill h WHERE h.description = :description")})
//</editor-fold>
public class HrEmpSkill implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="Entity Attributes">
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private BigDecimal id;
    @Size(max = 100)
    @Column(name = "SKILL_TYPE")
    private String skillType;
    @Size(max = 20)
    @Column(name = "SKILL_LEVEL")
    private String skillLevel;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "DESCRIPTION")
    private String description;
    @JoinColumn(name = "EMP_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrEmployees empId;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="getter and setter">
    /**
     *
     */
    public HrEmpSkill() {
    }
    
    /**
     *
     * @param id
     */
    public HrEmpSkill(BigDecimal id) {
        this.id = id;
    }
    
    /**
     *
     * @param id
     * @param description
     */
    public HrEmpSkill(BigDecimal id, String description) {
        this.id = id;
        this.description = description;
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
    public String getSkillType() {
        return skillType;
    }
    
    /**
     *
     * @param skillType
     */
    public void setSkillType(String skillType) {
        this.skillType = skillType;
    }
    
    /**
     *
     * @return
     */
    public String getSkillLevel() {
        return skillLevel;
    }
    
    /**
     *
     * @param skillLevel
     */
    public void setSkillLevel(String skillLevel) {
        this.skillLevel = skillLevel;
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
    public HrEmployees getEmpId() {
        return empId;
    }
    
    /**
     *
     * @param empId
     */
    public void setEmpId(HrEmployees empId) {
        this.empId = empId;
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="other Methods">
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }
    
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrEmpSkill)) {
            return false;
        }
        HrEmpSkill other = (HrEmpSkill) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return skillType;
    }
//</editor-fold>
}
