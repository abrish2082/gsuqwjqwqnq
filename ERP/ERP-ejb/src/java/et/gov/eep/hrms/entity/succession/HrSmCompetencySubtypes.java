/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.succession;

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
@Table(name = "HR_SM_COMPETENCY_SUBTYPES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrSmCompetencySubtypes.findAll", query = "SELECT h FROM HrSmCompetencySubtypes h"),
    @NamedQuery(name = "HrSmCompetencySubtypes.findById", query = "SELECT h FROM HrSmCompetencySubtypes h WHERE h.id = :id"),
    @NamedQuery(name = "HrSmCompetencySubtypes.searchbyduplicate", query = "SELECT h FROM HrSmCompetencySubtypes h WHERE h.competencyTypeId.competencyType = :competencyId AND h.competencySubtype=:comptype"),
    @NamedQuery(name = "HrSmCompetencySubtypes.findbyskill", query = "SELECT h FROM HrSmCompetencySubtypes h WHERE h.competencyTypeId.competencyId.competencyName = :competencyId"),
    @NamedQuery(name = "HrSmCompetencySubtypes.findByCompetencySubtype", query = "SELECT h FROM HrSmCompetencySubtypes h WHERE UPPER(h.competencySubtype)LIKE :competencySubtype")})
public class HrSmCompetencySubtypes implements Serializable {

    @OneToMany(mappedBy = "competencySubtypeId", cascade = CascadeType.ALL)
    private List<HrSmSkillCompetency> hrSmSkillCompetencyList = new ArrayList();
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "COMPETENECT_SUB_TYPE_SEQ")
    @SequenceGenerator(name = "COMPETENECT_SUB_TYPE_SEQ", sequenceName = "COMPETENECT_SUB_TYPE_SEQ", allocationSize = 1)
    private BigDecimal id;
    @Size(max = 20)
    @Column(name = "PREPARED_ON")
    private String preparedon;
    @Size(max = 100)
    @Column(name = "COMPETENCY_SUBTYPE")
    private String competencySubtype;
    @Size(max = 20)
    @Column(name = "PREPARED_BY")
    private String preparedby;

    @JoinColumn(name = "COMPETENCY_TYPE_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrSmCompetencyTypes competencyTypeId;

    public HrSmCompetencySubtypes() {
    }

    public HrSmCompetencySubtypes(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getCompetencySubtype() {
        return competencySubtype;
    }

    public void setCompetencySubtype(String competencySubtype) {
        this.competencySubtype = competencySubtype;
    }

    public HrSmCompetencyTypes getCompetencyTypeId() {
        return competencyTypeId;
    }

    public void setCompetencyTypeId(HrSmCompetencyTypes competencyTypeId) {
        this.competencyTypeId = competencyTypeId;
    }

    public boolean isApllicable() {
        return apllicable;
    }

    public void setApllicable(boolean apllicable) {
        this.apllicable = apllicable;
    }

    public boolean isNicetohave() {
        return nicetohave;
    }

    public void setNicetohave(boolean nicetohave) {
        this.nicetohave = nicetohave;
    }

    public boolean isImportanttohave() {
        return importanttohave;
    }

    public void setImportanttohave(boolean importanttohave) {
        this.importanttohave = importanttohave;
    }

    public boolean isMusthave() {
        return musthave;
    }

    public void setMusthave(boolean musthave) {
        this.musthave = musthave;
    }

    public String getPreparedon() {
        return preparedon;
    }

    public void setPreparedon(String preparedon) {
        this.preparedon = preparedon;
    }

    public String getPreparedby() {
        return preparedby;
    }

    public void setPreparedby(String preparedby) {
        this.preparedby = preparedby;
    }
    
    

    @Transient
    boolean apllicable;
    @Transient
    boolean nicetohave;
    @Transient
    boolean importanttohave;
    @Transient
    boolean musthave;

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrSmCompetencySubtypes)) {
            return false;
        }
        HrSmCompetencySubtypes other = (HrSmCompetencySubtypes) object;
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
    public List<HrSmSkillCompetency> getHrSmSkillCompetencyList() {
        return hrSmSkillCompetencyList;
    }

    public void setHrSmSkillCompetencyList(List<HrSmSkillCompetency> hrSmSkillCompetencyList) {
        this.hrSmSkillCompetencyList = hrSmSkillCompetencyList;
    }

    public List<HrSmCompetencySubtypes> createlisty() {
        this.createlisty();
        return null;
    }

}
