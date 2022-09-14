/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.displine;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
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
 * @author user
 */  
@Entity
@Table(name = "HR_DISCIPLINE_OFFENCE_PENALITY")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrDisciplineOffencePenality.findAll", query = "SELECT h FROM HrDisciplineOffencePenality h"),
    @NamedQuery(name = "HrDisciplineOffencePenality.findById", query = "SELECT h FROM HrDisciplineOffencePenality h WHERE h.id = :id"),
    @NamedQuery(name = "HrDisciplineOffencePenality.findByRepetition", query = "SELECT h FROM HrDisciplineOffencePenality h WHERE h.repetition = :repetition"),
    @NamedQuery(name = "HrDisciplineOffencePenality.findByOffenceLevel", query = "SELECT h FROM HrDisciplineOffencePenality h WHERE h.offenceLevel = :offenceLevel"),
    @NamedQuery(name = "HrDisciplineOffencePenality.findByOffenceTypeId", query = "SELECT h FROM HrDisciplineOffencePenality h WHERE h.offenceTypeId = :offenceTypeId"),
    
    @NamedQuery(name = "HrDisciplineOffencePenality.findByOffenceTypeAndRepition", query = "SELECT h FROM HrDisciplineOffencePenality h WHERE h.offenceTypeId = :offenceTypeId AND h.repetition=:repetition"),
    
    @NamedQuery(name = "HrDisciplineOffencePenality.findByOffenceNameLike", query = "SELECT h FROM HrDisciplineOffencePenality h WHERE UPPER(h.offenceTypeId.offenceName) LIKE :offenceName"),
    @NamedQuery(name = "HrDisciplineOffencePenality.findByDecider", query = "SELECT h FROM HrDisciplineOffencePenality h WHERE h.decider = :decider")})
public class HrDisciplineOffencePenality implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HrDisciplineOffencePenalty_SEQ_GENERATOR")
    @SequenceGenerator(name = "HrDisciplineOffencePenalty_SEQ_GENERATOR", sequenceName = "HrDisciplineOffencePenalty_SEQ", allocationSize = 1)
    private BigDecimal id;

    @Size(max = 20)
    @Column(name = "OFFENCE_LEVEL")
    private String offenceLevel;

    @Column(name = "REPETITION")
    private BigInteger repetition;

    @Size(max = 50)
    @Column(name = "DECIDER")
    private String decider;

    @JoinColumn(name = "OFFENCE_TYPE_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrDisciplineOffenceTypes offenceTypeId;

    @JoinColumn(name = "PENALTY_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrDisciplinePenaltyTypes penaltyId;

    public HrDisciplineOffencePenality() {
    }

    public HrDisciplineOffencePenality(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public BigInteger getRepetition() {
        return repetition;
    }

    public void setRepetition(BigInteger repetition) {
        this.repetition = repetition;
    }

    public String getDecider() {
        return decider;
    }

    public void setDecider(String decider) {
        this.decider = decider;
    }

    public HrDisciplineOffenceTypes getOffenceTypeId() {
        return offenceTypeId;
    }

    public void setOffenceTypeId(HrDisciplineOffenceTypes offenceTypeId) {
        this.offenceTypeId = offenceTypeId;
    }

    public String getOffenceLevel() {
        return offenceLevel;
    }

    public void setOffenceLevel(String offenceLevel) {
        this.offenceLevel = offenceLevel;
    }

    public HrDisciplinePenaltyTypes getPenaltyId() {
        return penaltyId;
    }

    public void setPenaltyId(HrDisciplinePenaltyTypes penaltyId) {
        this.penaltyId = penaltyId;
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
        if (!(object instanceof HrDisciplineOffencePenality)) {
            return false;
        }
        HrDisciplineOffencePenality other = (HrDisciplineOffencePenality) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
// public void addPenality(HrDisciplinePenaltyTypes disciplineLevel) {
//        if (disciplineLevel.getTypeId() != this) {
//            this.getHrDisciplineLevelList().add(disciplineLevel);
//            disciplineLevel.setTypeId(this);
//        }
//
//    }
//       @XmlTransient
//  public void addPenality(HrDisciplinePenaltyTypes penaltyTypes) {
//        if (penaltyTypes.get() != this) {
//            this.getHrDisciplineLevelList().add(disciplineLevel);
//            penaltyTypes.setTypeId(this);
//        }
//
//    }

    @Override
    public String toString() {
        return "et.gov.eep.hrms.entity.displine.HrDisciplineOffencePenalty[ id=" + id + " ]";
    }

}
