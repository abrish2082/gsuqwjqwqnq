/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.displine;

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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author user
 */
@Entity
@Table(name = "HR_DISCIPLINE_OFFENCE_TYPES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrDisciplineOffenceTypes.findAll", query = "SELECT h FROM HrDisciplineOffenceTypes h"),
    @NamedQuery(name = "HrDisciplineOffenceTypes.findById", query = "SELECT h FROM HrDisciplineOffenceTypes h WHERE h.id = :id"),
    @NamedQuery(name = "HrDisciplineOffenceTypes.findByOffenceCode", query = "SELECT h FROM HrDisciplineOffenceTypes h WHERE h.offenceCode = :offenceCode"),
    @NamedQuery(name = "HrDisciplineOffenceTypes.findByOffenceName", query = "SELECT h FROM HrDisciplineOffenceTypes h WHERE h.offenceName = :offenceName"),
    @NamedQuery(name = "HrDisciplineOffenceTypes.findDuplicationByOffenceName", query = "SELECT h FROM HrDisciplineOffenceTypes h WHERE h.offenceName = :offenceName"),
    @NamedQuery(name = "HrDisciplineOffenceTypes.findByDescription", query = "SELECT h FROM HrDisciplineOffenceTypes h WHERE h.description = :description"),
    @NamedQuery(name = "HrDisciplineOffenceTypes.findByPhaseoutPeriod", query = "SELECT h FROM HrDisciplineOffenceTypes h WHERE h.phaseoutPeriod = :phaseoutPeriod"),
    @NamedQuery(name = "HrDisciplineOffenceTypes.findByOffenceCodeLike", query = "SELECT h FROM HrDisciplineOffenceTypes h WHERE UPPER(h.offenceCode) LIKE :offenceCode"),
    @NamedQuery(name = "HrDisciplineOffenceTypes.findByOffenceNameLike", query = "SELECT h FROM HrDisciplineOffenceTypes h WHERE UPPER(h.offenceName) LIKE :offenceName"),
    @NamedQuery(name = "HrDisciplineOffenceTypes.findByWeghit", query = "SELECT h FROM HrDisciplineOffenceTypes h WHERE h.weghit = :weghit")})
public class HrDisciplineOffenceTypes implements Serializable {

    @OneToMany(mappedBy = "offenceTypeId", cascade = CascadeType.ALL)
    private List<HrDisciplineOffencePenality> hrDisciplineOffencePenalityList = new ArrayList<>();

    @OneToMany(mappedBy = "offenceId", cascade = CascadeType.ALL)
    private List<HrDisciplinePenlitys> hrDisciplineOffencePenlitysList = new ArrayList<>();

    @OneToMany(mappedBy = "offenceTypeId", cascade = CascadeType.ALL)
    private List<HrDisciplineRequests> hrDisciplineRequestsList = new ArrayList<>();

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HrDisciplineOffenceTypes_SEQ_GENERATOR")
    @SequenceGenerator(name = "HrDisciplineOffenceTypes_SEQ_GENERATOR", sequenceName = "HrDisciplineOffenceTypes_SEQ", allocationSize = 1)
    private Integer id;

    @Column(name = "OFFENCE_CODE")
    private String offenceCode;

    @Column(name = "OFFENCE_NAME")
    private String offenceName;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "PHASEOUT_PERIOD")
    private String phaseoutPeriod;
    @Column(name = "WEGHIT")
    private BigInteger weghit;
    @OneToMany(mappedBy = "offenceTypeId")
    private List<HrEmpDisciplines> hrEmpDisciplinesOffenceList;
//    @OneToMany(mappedBy = "offenceTypeId", cascade = CascadeType.ALL)
//    private List<HrDisciplineOffencePenality> hrDisciplineOffencePenaltyList = new ArrayList<>();

    public HrDisciplineOffenceTypes() {
    }

    public HrDisciplineOffenceTypes(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOffenceCode() {
        return offenceCode;
    }

    public void setOffenceCode(String offenceCode) {
        this.offenceCode = offenceCode;
    }

    public String getOffenceName() {
        return offenceName;
    }

    public void setOffenceName(String offenceName) {
        this.offenceName = offenceName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhaseoutPeriod() {
        return phaseoutPeriod;
    }

    public void setPhaseoutPeriod(String phaseoutPeriod) {
        this.phaseoutPeriod = phaseoutPeriod;
    }

    public BigInteger getWeghit() {
        return weghit;
    }

    public void setWeghit(BigInteger weghit) {
        this.weghit = weghit;
    }

    @Transient
    private String columnName;
    @Transient
    private String columnValue;

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnValue() {
        return columnValue;
    }

    public void setColumnValue(String columnValue) {
        this.columnValue = columnValue;
    }

    @XmlTransient
    public void addPenality(HrDisciplinePenlitys disciplinePenlitys) {
        if (disciplinePenlitys.getOffenceId() != this) {
            hrDisciplineOffencePenlitysList.remove(disciplinePenlitys);
            this.hrDisciplineOffencePenlitysList.add(disciplinePenlitys);
            disciplinePenlitys.setOffenceId(this);
        }
    }

    public List<HrDisciplineOffencePenality> getHrDisciplineOffencePenalityList() {
        return hrDisciplineOffencePenalityList;
    }

    public void setHrDisciplineOffencePenaltyList(List<HrDisciplineOffencePenality> hrDisciplineOffencePenalityList) {
        this.hrDisciplineOffencePenalityList = hrDisciplineOffencePenalityList;
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
        if (!(object instanceof HrDisciplineOffenceTypes)) {
            return false;
        }
        HrDisciplineOffenceTypes other = (HrDisciplineOffenceTypes) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return offenceName; //offenceCode;//"et.gov.eep.hrms.entity.displine.HrDisciplineOffenceTypes[ id=" + id + " ]";
    }

    @XmlTransient
    public List<HrDisciplineRequests> getHrDisciplineRequestsList() {
        return hrDisciplineRequestsList;
    }

    public void setHrDisciplineRequestsList(List<HrDisciplineRequests> hrDisciplineRequestsList) {
        this.hrDisciplineRequestsList = hrDisciplineRequestsList;
    }

    @XmlTransient
    public List<HrDisciplinePenlitys> getHrDisciplineOffencePenlitysList() {
        return hrDisciplineOffencePenlitysList;
    }

    public void setHrDisciplineOffencePenlitysList(List<HrDisciplinePenlitys> hrDisciplineOffencePenlitysList) {
        this.hrDisciplineOffencePenlitysList = hrDisciplineOffencePenlitysList;
    }

//
//    @XmlTransient
//    public List<HrDisciplineOffencePenality> getHrDisciplineOffencePenalityList() {
//        return hrDisciplineOffencePenalityList;
//    }
//
//    public void setHrDisciplineOffencePenalityList(List<HrDisciplineOffencePenality> hrDisciplineOffencePenalityList) {
//        this.hrDisciplineOffencePenalityList = hrDisciplineOffencePenalityList;
//    }
    public List<HrEmpDisciplines> getHrEmpDisciplinesOffenceList() {
        return hrEmpDisciplinesOffenceList;
    }

    public void setHrEmpDisciplinesOffenceList(List<HrEmpDisciplines> hrEmpDisciplinesOffenceList) {
        this.hrEmpDisciplinesOffenceList = hrEmpDisciplinesOffenceList;
    }

}
