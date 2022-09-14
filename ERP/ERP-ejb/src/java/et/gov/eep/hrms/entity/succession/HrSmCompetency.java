/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.succession;

import et.gov.eep.hrms.entity.succession.HrSmCompetency;
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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author meles
 */
@Entity
@Table(name = "HR_SM_COMPETENCY")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrSmCompetency.findAll", query = "SELECT h FROM HrSmCompetency h"),
    @NamedQuery(name = "HrSmCompetency.findById", query = "SELECT h FROM HrSmCompetency h WHERE h.id = :id"),
    @NamedQuery(name = "HrSmCompetency.findByCompetencyName", query = "SELECT h FROM HrSmCompetency h WHERE h.competencyName = :competencyName"),
    @NamedQuery(name = "HrSmCompetency.findByCompetencyNameLike", query = "SELECT h FROM HrSmCompetency h WHERE UPPER(h.competencyName) LIKE :competencyName"),
    @NamedQuery(name = "HrSmCompetency.searchbyduplicate", query = "SELECT h FROM HrSmCompetency h WHERE  h.competencyName = :competencyName"),
    @NamedQuery(name = "HrSmCompetency.findByDescription", query = "SELECT h FROM HrSmCompetency h WHERE h.description = :description")})
public class HrSmCompetency implements Serializable {

    @OneToMany(mappedBy = "competencyId", cascade = CascadeType.ALL)
    private List<HrSmCompetencyTypes> hrSmCompetencyTypesList = new ArrayList<>();

    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_SM_COMPETENCY_SEQ")
    @SequenceGenerator(name = "HR_SM_COMPETENCY_SEQ", sequenceName = "HR_SM_COMPETENCY_SEQ", allocationSize = 1)
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private BigDecimal id;

    @Size(max = 20)
    @Column(name = "COMPETENCY_NAME")
    private String competencyName;

    @Size(max = 200)
    @Column(name = "DESCRIPTION")
    private String description;
    @Size(max = 20)
    @Column(name = "PREPARED_BY")
    private String preparedby;
    @Size(max = 20)
    @Column(name = "PREPARED_ON")
    private String preparedon;

    public HrSmCompetency() {
    }

    public HrSmCompetency(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getCompetencyName() {
        return competencyName;
    }

    public void setCompetencyName(String competencyName) {
        this.competencyName = competencyName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPreparedby() {
        return preparedby;
    }

    public void setPreparedby(String preparedby) {
        this.preparedby = preparedby;
    }

    public String getPreparedon() {
        return preparedon;
    }

    public void setPreparedon(String preparedon) {
        this.preparedon = preparedon;
    }

    @Transient
    private String columnValue;

    public String getColumnValue() {
        return columnValue;
    }

    public void setColumnValue(String columnValue) {
        this.columnValue = columnValue;
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
        if (!(object instanceof HrSmCompetency)) {
            return false;
        }
        HrSmCompetency other = (HrSmCompetency) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.hrms.entity.competencyReg.HrSmCompetency[ id=" + id + " ]";
    }

    @XmlTransient
    public List<HrSmCompetencyTypes> getHrSmCompetencyTypesList() {
        return hrSmCompetencyTypesList;
    }

    public void setHrSmCompetencyTypesList(List<HrSmCompetencyTypes> hrSmCompetencyTypesList) {
        this.hrSmCompetencyTypesList = hrSmCompetencyTypesList;
    }

    public void setCompetencyName(HrSmCompetency hrSmCompetency) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

//    public void setCompetencyName(HrSmCompetency hrSmCompetency) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
}
