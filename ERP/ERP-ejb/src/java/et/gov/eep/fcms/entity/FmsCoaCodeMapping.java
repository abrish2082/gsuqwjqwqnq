/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.entity;

import et.gov.eep.fcms.entity.admin.FmsGeneralLedger;
import et.gov.eep.fcms.entity.admin.FmsSubsidiaryLedger;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Hanoc
 */
@Entity
@Table(name = "FMS_COA_CODE_MAPPING")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsCoaCodeMapping.findAll", query = "SELECT f FROM FmsCoaCodeMapping f"),
    @NamedQuery(name = "FmsCoaCodeMapping.findById", query = "SELECT f FROM FmsCoaCodeMapping f WHERE f.id = :id"),
    @NamedQuery(name = "FmsCoaCodeMapping.findByGlCode", query = "SELECT f FROM FmsCoaCodeMapping f WHERE f.glCode = :glCode"),
    @NamedQuery(name = "FmsCoaCodeMapping.findBySubCode", query = "SELECT f FROM FmsCoaCodeMapping f WHERE f.subCode = :subCode"),
    @NamedQuery(name = "FmsCoaCodeMapping.findByAllowanceType", query = "SELECT f FROM FmsCoaCodeMapping f WHERE f.allowanceType = :allowanceType"),
    @NamedQuery(name = "FmsCoaCodeMapping.findByType", query = "SELECT f FROM FmsCoaCodeMapping f WHERE f.type = :type"),
    @NamedQuery(name = "FmsCoaCodeMapping.findByLocation", query = "SELECT f FROM FmsCoaCodeMapping f WHERE f.location = :location")})
public class FmsCoaCodeMapping implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FMS_SEQ_COA_CODE_MAPPING")
    @SequenceGenerator(name = "FMS_SEQ_COA_CODE_MAPPING", sequenceName = "FMS_SEQ_COA_CODE_MAPPING", allocationSize = 1)
    @Size(min = 1, max = 20)
    @Column(name = "ID")
    private String id;
    @Size(max = 200)
    @Column(name = "GL_CODE")
    private String glCode;
    @Size(max = 200)
    @Column(name = "SUB_CODE")
    private String subCode;
    @Size(max = 20)
    @Column(name = "ALLOWANCE_TYPE")
    private String allowanceType;
    @Size(max = 20)
    @Column(name = "TYPE")
    private String type;
    @Size(max = 20)
    @Column(name = "LOCATION")
    private String location;
    @JoinColumn(name = "GL_CODE_ID", referencedColumnName = "GENERAL_LEDGER_ID")
    @ManyToOne
    private FmsGeneralLedger glCodeId;
    @JoinColumn(name = "SUB_CODE_ID", referencedColumnName = "SUBSIDIARY_ID")
    @ManyToOne
    private FmsSubsidiaryLedger subCodeId;
    @JoinColumn(name = "DEP_ID", referencedColumnName = "DEP_ID")
    @ManyToOne
    private HrDepartments depId;
    @Column(name = "DEPARTMENT_ID")
    private Integer departmentId;

    /**
     *
     */
    public FmsCoaCodeMapping() {
    }

    /**
     *
     * @param id
     */
    public FmsCoaCodeMapping(String id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public String getGlCode() {
        return glCode;
    }

    /**
     *
     * @param glCode
     */
    public void setGlCode(String glCode) {
        this.glCode = glCode;
    }

    /**
     *
     * @return
     */
    public String getSubCode() {
        return subCode;
    }

    /**
     *
     * @param subCode
     */
    public void setSubCode(String subCode) {
        this.subCode = subCode;
    }

    /**
     *
     * @return
     */
    public String getAllowanceType() {
        return allowanceType;
    }

    /**
     *
     * @param allowanceType
     */
    public void setAllowanceType(String allowanceType) {
        this.allowanceType = allowanceType;
    }

    /**
     *
     * @return
     */
    public String getType() {
        return type;
    }

    /**
     *
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     *
     * @return
     */
    public String getLocation() {
        return location;
    }

    /**
     *
     * @param location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     *
     * @return
     */
    public FmsGeneralLedger getGlCodeId() {
        return glCodeId;
    }

    /**
     *
     * @param glCodeId
     */
    public void setGlCodeId(FmsGeneralLedger glCodeId) {
        this.glCodeId = glCodeId;
    }

    /**
     *
     * @return
     */
    public FmsSubsidiaryLedger getSubCodeId() {
        return subCodeId;
    }

    /**
     *
     * @param subCodeId
     */
    public void setSubCodeId(FmsSubsidiaryLedger subCodeId) {
        this.subCodeId = subCodeId;
    }

    /**
     *
     * @return
     */
    public HrDepartments getDepId() {
        return depId;
    }

    /**
     *
     * @param depId
     */
    public void setDepId(HrDepartments depId) {
        this.depId = depId;
    }

    /**
     *
     * @return
     */
    public Integer getDepartmentId() {
        return departmentId;
    }

    /**
     *
     * @param departmentId
     */
    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
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
        if (!(object instanceof FmsCoaCodeMapping)) {
            return false;
        }
        FmsCoaCodeMapping other = (FmsCoaCodeMapping) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.insa.erp.ibfms.entity.FmsCoaCodeMapping[ id=" + id + " ]";
    }

}
