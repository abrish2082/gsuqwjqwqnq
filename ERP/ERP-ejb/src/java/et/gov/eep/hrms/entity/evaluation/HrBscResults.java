/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.evaluation;

import et.gov.eep.hrms.entity.employee.HrEmployees;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author INSA
 */
@Entity
@Table(name = "HR_BSC_RESULTS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrBscResults.findAll", query = "SELECT h FROM HrBscResults h"),
    @NamedQuery(name = "HrBscResults.findById", query = "SELECT h FROM HrBscResults h WHERE h.id = :id"),
    @NamedQuery(name = "HrBscResults.findByBscResult", query = "SELECT h FROM HrBscResults h WHERE h.bscResult = :bscResult"),
    @NamedQuery(name = "HrBscResults.findByRemark", query = "SELECT h FROM HrBscResults h WHERE h.remark = :remark")})
public class HrBscResults implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_BSC_RESULTS_SEQ")
    @SequenceGenerator(name = "HR_BSC_RESULTS_SEQ", sequenceName = "HR_BSC_RESULTS_SEQ", allocationSize = 1)
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private BigDecimal id;
    @Column(name = "BSC_RESULT")
    private Integer bscResult;
    @Size(max = 200)
    @Column(name = "REMARK")
    private String remark;
    @JoinColumn(name = "BSC_ID", referencedColumnName = "ID")
    @ManyToOne(cascade = CascadeType.ALL)
    private HrBsc bscId;
    @JoinColumn(name = "EMP_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrEmployees empId;

    public HrBscResults() {
    }

    public HrBscResults(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public Integer getBscResult() {
        return bscResult;
    }

    public void setBscResult(Integer bscResult) {
        this.bscResult = bscResult;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public HrBsc getBscId() {
        return bscId;
    }

    public void setBscId(HrBsc bscId) {
        this.bscId = bscId;
    }

    public HrEmployees getEmpId() {
        return empId;
    }

    public void setEmpId(HrEmployees empId) {
        this.empId = empId;
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
        if (!(object instanceof HrBscResults)) {
            return false;
        }
        HrBscResults other = (HrBscResults) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.HrBscResults[ id=" + id + " ]";
    }
    
}
