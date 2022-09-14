/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.succession;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
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
 * @author meles
 */
@Entity
@Table(name = "HR_SM_SKILL_COMPETENCY")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrSmSkillCompetency.findAll", query = "SELECT h FROM HrSmSkillCompetency h"),
    @NamedQuery(name = "HrSmSkillCompetency.findById", query = "SELECT h FROM HrSmSkillCompetency h WHERE h.id = :id"),
    @NamedQuery(name = "HrSmSkillCompetency.searchduplicatecompetency", query = "SELECT h FROM HrSmSkillCompetency h WHERE h.kmpId= :Kmpid AND h.competencySubtypeId =:competencyId"),
    @NamedQuery(name = "HrSmSkillCompetency.findByKmpid", query = "SELECT h FROM HrSmSkillCompetency h WHERE h.kmpId.id = :Kmpid"),
    @NamedQuery(name = "HrSmSkillCompetency.findByKmpId", query = "SELECT h FROM HrSmSkillCompetency h WHERE h.kmpId.id =:kmpId"),
    @NamedQuery(name = "HrSmSkillCompetency.findByNotApplicable", query = "SELECT h FROM HrSmSkillCompetency h WHERE h.notApplicable = :notApplicable"),
    @NamedQuery(name = "HrSmSkillCompetency.findByNiceToHave", query = "SELECT h FROM HrSmSkillCompetency h WHERE h.niceToHave = :niceToHave"),
    @NamedQuery(name = "HrSmSkillCompetency.findByImportantToHave", query = "SELECT h FROM HrSmSkillCompetency h WHERE h.importantToHave = :importantToHave"),
    @NamedQuery(name = "HrSmSkillCompetency.findByMustHave", query = "SELECT h FROM HrSmSkillCompetency h WHERE h.mustHave = :mustHave")})
public class HrSmSkillCompetency implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_SM_SKILL_COMPETENCY_SEQ")
    @SequenceGenerator(name = "HR_SM_SKILL_COMPETENCY_SEQ", sequenceName = "HR_SM_SKILL_COMPETENCY_SEQ", allocationSize = 1)
    private BigDecimal id;
    @Column(name = "NOT_APPLICABLE")
    private boolean notApplicable;
    @Column(name = "NICE_TO_HAVE")
    private boolean niceToHave;
    @Column(name = "IMPORTANT_TO_HAVE")
    private boolean importantToHave;
    @Column(name = "MUST_HAVE")
    private boolean mustHave;
    @Size(max = 20)
    @Column(name = "PREPARED_ON")
    private String preparedon;
    @Size(max = 20)
    @Column(name = "PREPARED_BY")
    private String preparedby;

    @JoinColumn(name = "COMPETENCY_SUBTYPE_ID", referencedColumnName = "ID")
    @ManyToOne()
    private HrSmCompetencySubtypes competencySubtypeId;
    @JoinColumn(name = "KMP_ID", referencedColumnName = "ID")
    @ManyToOne()
    private HrSmKmp kmpId;

    public HrSmSkillCompetency() {
    }

    public HrSmSkillCompetency(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public boolean getNotApplicable() {
        return notApplicable;
    }

    public void setNotApplicable(boolean notApplicable) {
        this.notApplicable = notApplicable;
    }

    public boolean getNiceToHave() {
        return niceToHave;
    }

    public void setNiceToHave(boolean niceToHave) {
        this.niceToHave = niceToHave;
    }

    public boolean getImportantToHave() {
        return importantToHave;
    }

    public void setImportantToHave(boolean importantToHave) {
        this.importantToHave = importantToHave;
    }

    public boolean getMustHave() {
        return mustHave;
    }

    public void setMustHave(boolean mustHave) {
        this.mustHave = mustHave;
    }

    public HrSmCompetencySubtypes getCompetencySubtypeId() {
        return competencySubtypeId;
    }

    public void setCompetencySubtypeId(HrSmCompetencySubtypes competencySubtypeId) {
        this.competencySubtypeId = competencySubtypeId;
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
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrSmSkillCompetency)) {
            return false;
        }
        HrSmSkillCompetency other = (HrSmSkillCompetency) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return id.toString();
    }

    public HrSmKmp getKmpId() {
        return kmpId;
    }

    public void setKmpId(HrSmKmp kmpId) {
        this.kmpId = kmpId;
    }

    public void addMessage2() {
        String summary = notApplicable ? "UNChecked" : "Checked";
        System.out.println("------------cvalue2  " + notApplicable);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(summary));
    }

    public void addMessage3() {
        String summary = niceToHave ? "UNChecked" : "Checked";
        System.out.println("------------cvalue3 " + niceToHave);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(summary));
    }

    public void addMessage4() {
        String summary = mustHave ? "UNChecked" : "Checked";
        System.out.println("------------cvalue4  " + mustHave);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(summary));
    }

    public void addMessage5() {
        String summary = importantToHave ? "UNChecked" : "Checked";
        System.out.println("------------cvalue5 " + importantToHave);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(summary));
    }

    }
