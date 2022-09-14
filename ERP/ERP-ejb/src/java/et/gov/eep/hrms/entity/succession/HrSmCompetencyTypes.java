/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.succession;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
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
@Table(name = "HR_SM_COMPETENCY_TYPES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrSmCompetencyTypes.findAll", query = "SELECT h FROM HrSmCompetencyTypes h"),
    @NamedQuery(name = "HrSmCompetencyTypes.findById", query = "SELECT h FROM HrSmCompetencyTypes h WHERE h.id = :id"),
    @NamedQuery(name = "HrSmCompetencyTypes.findByKnowledge", query = "SELECT h FROM HrSmCompetencyTypes h WHERE h.competencyId.competencyName = :competencyId"),
    @NamedQuery(name = "HrSmCompetencyTypes.searchbyduplicate", query = "SELECT h FROM HrSmCompetencyTypes h WHERE h.competencyId.competencyName = :competencyId AND h.competencyType=:comptype"),
    @NamedQuery(name = "HrSmCompetencyTypes.findByCompetencyType", query = "SELECT h FROM HrSmCompetencyTypes h WHERE UPPER(h.competencyType)LIKE :competencyType")})
public class HrSmCompetencyTypes implements Serializable {

    @OneToMany(mappedBy = "competencyTypeId", cascade = CascadeType.ALL)
    private List<HrSmKnowledgeCompetency> hrSmKnowledgeCompetencyList = new ArrayList<>();

    @OneToMany(mappedBy = "competencyTypeId", cascade = CascadeType.ALL)
    private List<HrSmCompetencySubtypes> hrSmCompetencySubtypesList = new ArrayList<>();

    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_SM_COMPETENCY_TYPES_SEQ")
    @SequenceGenerator(name = "HR_SM_COMPETENCY_TYPES_SEQ", sequenceName = "HR_SM_COMPETENCY_TYPES_SEQ", allocationSize = 1)
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Integer id;
    @Size(max = 100)
    @Column(name = "COMPETENCY_TYPE")
    private String competencyType;
    @Size(max = 20)
    @Column(name = "PREPARED_ON")
    private String preparedon;
    @Size(max = 20)
    @Column(name = "PREPARED_BY")
    private String peparedby;
    @JoinColumn(name = "COMPETENCY_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrSmCompetency competencyId;

    @Transient
    boolean applicapble;
    @Transient
    String naturalLevel;
    @Transient
    BigInteger inMonth;

    public HrSmCompetencyTypes() {
    }

    public HrSmCompetencyTypes(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCompetencyType() {
        return competencyType;
    }

    public void setCompetencyType(String competencyType) {
        this.competencyType = competencyType;
    }

    public HrSmCompetency getCompetencyId() {
        return competencyId;
    }

    public void setCompetencyId(HrSmCompetency competencyId) {
        this.competencyId = competencyId;
    }

    public boolean isApplicapble() {
        return applicapble;
    }

    public void setApplicapble(boolean applicapble) {
        this.applicapble = applicapble;
    }

    public String getNaturalLevel() {
        return naturalLevel;
    }

    public void setNaturalLevel(String naturalLevel) {
        this.naturalLevel = naturalLevel;
    }

    public BigInteger getInMonth() {
        return inMonth;
    }

    public void setInMonth(BigInteger inMonth) {
        this.inMonth = inMonth;
    }

    public String getPreparedon() {
        return preparedon;
    }

    public void setPreparedon(String preparedon) {
        this.preparedon = preparedon;
    }

    public String getPeparedby() {
        return peparedby;
    }

    public void setPeparedby(String peparedby) {
        this.peparedby = peparedby;
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
        if (!(object instanceof HrSmCompetencyTypes)) {
            return false;
        }
        HrSmCompetencyTypes other = (HrSmCompetencyTypes) object;
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
    public List<HrSmCompetencySubtypes> getHrSmCompetencySubtypesList() {
        return hrSmCompetencySubtypesList;
    }

    public void setHrSmCompetencySubtypesList(List<HrSmCompetencySubtypes> hrSmCompetencySubtypesList) {
        this.hrSmCompetencySubtypesList = hrSmCompetencySubtypesList;
    }

    @XmlTransient
    public List<HrSmKnowledgeCompetency> getHrSmKnowledgeCompetencyList() {
        return hrSmKnowledgeCompetencyList;
    }

    public void setHrSmKnowledgeCompetencyList(List<HrSmKnowledgeCompetency> hrSmKnowledgeCompetencyList) {
        this.hrSmKnowledgeCompetencyList = hrSmKnowledgeCompetencyList;
    }


    }
