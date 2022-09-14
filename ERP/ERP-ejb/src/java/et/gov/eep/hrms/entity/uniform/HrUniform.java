/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.uniform;

import et.gov.eep.hrms.entity.organization.HrJobTypes;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Behailu
 */
@Entity
@Table(name = "HR_UNIFORM")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrUniform.findAll", query = "SELECT h FROM HrUniform h"),
    @NamedQuery(name = "HrUniform.findById", query = "SELECT h FROM HrUniform h WHERE h.id = :id"),
    @NamedQuery(name = "HrUniform.findByJobTypeId", query = "SELECT h FROM HrUniform h WHERE h.JobId.id = :jobTypeId"),
    @NamedQuery(name = "HrUniform.findBySex", query = "SELECT h FROM HrUniform h WHERE h.sex = :sex")})
public class HrUniform implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_UNIFORM_SEQ")
    @SequenceGenerator(name = "HR_UNIFORM_SEQ", sequenceName = "HR_UNIFORM_SEQ", allocationSize = 1)
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private BigDecimal id;
    @Column(name = "SEX")
    private int sex;
    @OneToMany(mappedBy = "uniformId", cascade = CascadeType.ALL)
    private List<HrUniformDetails> hrUniformDetailsList;

    @JoinColumn(name = "JOB_TYPE_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrJobTypes JobId;

    public HrUniform() {
    }

    public HrUniform(BigDecimal id) {
        this.id = id;
    }

//<editor-fold defaultstate="collapsed" desc="getters & setters">
    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public HrJobTypes getJobId() {
        return JobId;
    }

    public void setJobId(HrJobTypes JobId) {
        this.JobId = JobId;
    }
//</editor-fold>

    @XmlTransient
    public List<HrUniformDetails> getHrUniformDetailsList() {
        if(hrUniformDetailsList==null){
        hrUniformDetailsList = new ArrayList<>();
        }
        return hrUniformDetailsList;
    }
    public void setHrUniformDetailsList(List<HrUniformDetails> hrUniformDetailsList) {
        this.hrUniformDetailsList = hrUniformDetailsList;
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
        if (!(object instanceof HrUniform)) {
            return false;
        }
        HrUniform other = (HrUniform) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.hrms.entity.uniform.HrUniform[ id=" + id + " ]";
    }

    public void add(HrUniformDetails hrUniformDetails) {
        if (hrUniformDetails.getUniformId() != this) {
            this.getHrUniformDetailsList().add(hrUniformDetails);
            hrUniformDetails.setUniformId(this);
        }
    }
}
