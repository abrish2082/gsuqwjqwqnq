/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.displine;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "HR_DISCIPLINE_PENLITYS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrDisciplinePenlitys.findAll", query = "SELECT h FROM HrDisciplinePenlitys h"),
    @NamedQuery(name = "HrDisciplinePenlitys.findById", query = "SELECT h FROM HrDisciplinePenlitys h WHERE h.id = :id"),
    @NamedQuery(name = "HrDisciplinePenlitys.findByRound", query = "SELECT h FROM HrDisciplinePenlitys h WHERE h.round = :round"),
    @NamedQuery(name = "HrDisciplinePenlitys.findByPenalityDescription", query = "SELECT h FROM HrDisciplinePenlitys h WHERE h.penalityDescription = :penalityDescription"),
    @NamedQuery(name = "HrDisciplinePenlitys.findByOffenceID", query = "SELECT h FROM HrDisciplinePenlitys h WHERE h.offenceId.id = :id"),
    @NamedQuery(name = "HrDisciplinePenlitys.findByReferanceNumber", query = "SELECT h FROM HrDisciplinePenlitys h WHERE h.referanceNumber = :referanceNumber")})
public class HrDisciplinePenlitys implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_DISCIPLINE_PENLITYS_SEQ_GENERATOR")
    @SequenceGenerator(name = "HR_DISCIPLINE_PENLITYS_SEQ_GENERATOR", sequenceName = "HR_DISCIPLINE_PENLITYS_SEQ", allocationSize = 1)
    private Integer id;

    @Size(max = 30)
    @Column(name = "ROUND")
    private String round;
    @Size(max = 200)
    @Column(name = "PENALITY_DESCRIPTION")
    private String penalityDescription;
    @Size(max = 20)
    @Column(name = "REFERANCE_NUMBER")
    private String referanceNumber;
    @JoinColumn(name = "OFFENCE_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrDisciplineOffenceTypes offenceId;

    public HrDisciplinePenlitys() {
    }

    public HrDisciplinePenlitys(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRound() {
        return round;
    }

    public void setRound(String round) {
        this.round = round;
    }

    public String getPenalityDescription() {
        return penalityDescription;
    }

    public void setPenalityDescription(String penalityDescription) {
        this.penalityDescription = penalityDescription;
    }

    public String getReferanceNumber() {
        return referanceNumber;
    }

    public void setReferanceNumber(String referanceNumber) {
        this.referanceNumber = referanceNumber;
    }

    public HrDisciplineOffenceTypes getOffenceId() {
        return offenceId;
    }

    public void setOffenceId(HrDisciplineOffenceTypes offenceId) {
        this.offenceId = offenceId;
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
        if (!(object instanceof HrDisciplinePenlitys)) {
            return false;
        }
        HrDisciplinePenlitys other = (HrDisciplinePenlitys) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return penalityDescription;//"et.gov.eep.hrms.entity.displine.HrDisciplineOffencePenlitys[ id=" + id + " ]";
    }

}
