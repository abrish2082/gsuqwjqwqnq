/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.displine;

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
 * @author user
 */
@Entity
@Table(name = "HR_DISCIPLINE_PENALTY_TYPES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrDisciplinePenaltyTypes.findAll", query = "SELECT h FROM HrDisciplinePenaltyTypes h"),
    @NamedQuery(name = "HrDisciplinePenaltyTypes.findById", query = "SELECT h FROM HrDisciplinePenaltyTypes h WHERE h.id = :id"),
    @NamedQuery(name = "HrDisciplinePenaltyTypes.findByPenaltyCode", query = "SELECT h FROM HrDisciplinePenaltyTypes h WHERE h.penaltyCode = :penaltyCode"),
    @NamedQuery(name = "HrDisciplinePenaltyTypes.findByPenaltyCodes", query = "SELECT h FROM HrDisciplinePenaltyTypes h WHERE h.id = :penaltyCode"),
    @NamedQuery(name = "HrDisciplinePenaltyTypes.findByPenaltyClassification", query = "SELECT h FROM HrDisciplinePenaltyTypes h WHERE h.penaltyClassification = :penaltyClassification"),
    @NamedQuery(name = "HrDisciplinePenaltyTypes.findByPenaltyName", query = "SELECT h FROM HrDisciplinePenaltyTypes h WHERE h.penaltyName = :penaltyName"),
    @NamedQuery(name = "HrDisciplinePenaltyTypes.findDuplicationByPenaltyName", query = "SELECT h FROM HrDisciplinePenaltyTypes h WHERE h.penaltyName = :penaltyName"),
    @NamedQuery(name = "HrDisciplinePenaltyTypes.findByDescription", query = "SELECT h FROM HrDisciplinePenaltyTypes h WHERE h.description = :description"),
    @NamedQuery(name = "HrDisciplinePenaltyTypes.findByPenaltyCodesLike", query = "SELECT h FROM HrDisciplinePenaltyTypes h WHERE UPPER(h.penaltyCode) LIKE :penaltyCode"),
    @NamedQuery(name = "HrDisciplinePenaltyTypes.findByPenaltyNameLike", query = "SELECT h FROM HrDisciplinePenaltyTypes h WHERE UPPER(h.penaltyName) LIKE :penaltyName"),
    @NamedQuery(name = "HrDisciplinePenaltyTypes.findByActionTaker", query = "SELECT h FROM HrDisciplinePenaltyTypes h WHERE h.actionTaker = :actionTaker")})
public class HrDisciplinePenaltyTypes implements Serializable {

    @OneToMany(mappedBy = "penaltyId", cascade = CascadeType.ALL)
    private List<HrDisciplineOffencePenality> hrDisciplineOffencePenalityList = new ArrayList<>();

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HrDisciplinePenaltyTypes_SEQ_GENERATOR")
    @SequenceGenerator(name = "HrDisciplinePenaltyTypes_SEQ_GENERATOR", sequenceName = "HrDisciplinePenaltyTypes_SEQ", allocationSize = 1)
    private BigDecimal id;

    @Column(name = "PENALTY_CODE")
    private String penaltyCode;

    @Column(name = "PENALTY_CLASSIFICATION")
    private String penaltyClassification;

    @Column(name = "PENALTY_NAME")
    private String penaltyName;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "ACTION_TAKER")
    private String actionTaker;

//    @OneToMany(mappedBy = "penaltyId", cascade = CascadeType.ALL)
//    private List<HrDisciplineOffencePenalty> hrDisciplineOffencePenaltyList = new ArrayList<>();
    public HrDisciplinePenaltyTypes() {
    }

    public HrDisciplinePenaltyTypes(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getPenaltyCode() {
        return penaltyCode;
    }

    public void setPenaltyCode(String penaltyCode) {
        this.penaltyCode = penaltyCode;
    }

    public String getPenaltyClassification() {
        return penaltyClassification;
    }

    public void setPenaltyClassification(String penaltyClassification) {
        this.penaltyClassification = penaltyClassification;
    }

    public String getPenaltyName() {
        return penaltyName;
    }

    public void setPenaltyName(String penaltyName) {
        this.penaltyName = penaltyName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getActionTaker() {
        return actionTaker;
    }

    public void setActionTaker(String actionTaker) {
        this.actionTaker = actionTaker;
    }

    @Transient
    private String columnValue;

    public String getColumnValue() {
        return columnValue;
    }

    public void setColumnValue(String columnValue) {
        this.columnValue = columnValue;
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
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrDisciplinePenaltyTypes)) {
            return false;
        }
        HrDisciplinePenaltyTypes other = (HrDisciplinePenaltyTypes) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
//        return penaltyName; 
        return "et.gov.eep.hrms.entity.displine.HrDisciplinePenaltyTypes[ id=" + id + " ]";
    }

    @XmlTransient
    public List<HrDisciplineOffencePenality> getHrDisciplineOffencePenalityList() {
        return hrDisciplineOffencePenalityList;
    }

    public void setHrDisciplineOffencePenalityList(List<HrDisciplineOffencePenality> hrDisciplineOffencePenalityList) {
        this.hrDisciplineOffencePenalityList = hrDisciplineOffencePenalityList;
    }

}
