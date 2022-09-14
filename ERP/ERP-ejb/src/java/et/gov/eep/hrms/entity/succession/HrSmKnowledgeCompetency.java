/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.succession;

import et.gov.eep.hrms.entity.organization.HrJobTypes;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
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
@Table(name = "HR_SM_KNOWLEDGE_COMPETENCY")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrSmKnowledgeCompetency.findAll", query = "SELECT h FROM HrSmKnowledgeCompetency h"),
    @NamedQuery(name = "HrSmKnowledgeCompetency.findById", query = "SELECT h FROM HrSmKnowledgeCompetency h WHERE h.id =:id"),
    @NamedQuery(name = "HrSmKnowledgeCompetency.findByKmpId", query = "SELECT h FROM HrSmKnowledgeCompetency h WHERE h.kmpId.id =:kmpId"),
    @NamedQuery(name = "HrSmKnowledgeCompetency.searchduplicatecompetency", query = "SELECT h FROM HrSmKnowledgeCompetency h WHERE h.kmpId =:kmpId AND h.competencyTypeId =:competencyId"),
    @NamedQuery(name = "HrSmKnowledgeCompetency.findBykmpIdLike", query = "SELECT DISTINCT(h.kmpId) FROM HrSmKnowledgeCompetency h WHERE UPPER(h.kmpId.jobId.jobTitle) LIKE :jobTitle"),
    @NamedQuery(name = "HrSmKnowledgeCompetency.findByNotApplicable", query = "SELECT h FROM HrSmKnowledgeCompetency h WHERE h.notApplicable = :notApplicable"),
    @NamedQuery(name = "HrSmKnowledgeCompetency.findByNatureLevelAttend", query = "SELECT h FROM HrSmKnowledgeCompetency h WHERE h.natureLevelAttend = :natureLevelAttend"),
    @NamedQuery(name = "HrSmKnowledgeCompetency.findByInmonth", query = "SELECT h FROM HrSmKnowledgeCompetency h WHERE h.inmonth = :inmonth")})
public class HrSmKnowledgeCompetency implements Serializable {
   
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_SM_KNOWLEDGE_COMPETENCY_SEQ")
    @SequenceGenerator(name = "HR_SM_KNOWLEDGE_COMPETENCY_SEQ", sequenceName = "HR_SM_KNOWLEDGE_COMPETENCY_SEQ", allocationSize = 1)
    private BigDecimal id;
    @Column(name = "NOT_APPLICABLE")
    private boolean notApplicable = false;
    @Size(max = 20)
    @Column(name = "PREPARED_ON")
    private String preparedon;
    @Size(max = 20)
    @Column(name = "PREPARED_BY")
    private String preparedby;
    @Size(max = 100)
    @Column(name = "NATURE_LEVEL_ATTEND")
    private String natureLevelAttend;
    @Column(name = "INMONTH")
    private BigInteger inmonth;
    @JoinColumn(name = "COMPETENCY_TYPE_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrSmCompetencyTypes competencyTypeId;

    @JoinColumn(name = "KMP_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrSmKmp kmpId;

    public HrSmKnowledgeCompetency() {
    }

    public HrSmKnowledgeCompetency(BigDecimal id) {
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

    public String getNatureLevelAttend() {
        return natureLevelAttend;
    }

    public void setNatureLevelAttend(String natureLevelAttend) {
        this.natureLevelAttend = natureLevelAttend;
    }

    public BigInteger getInmonth() {
        return inmonth;
    }

    public void setInmonth(BigInteger inmonth) {
        this.inmonth = inmonth;
    }

    public HrSmCompetencyTypes getCompetencyTypeId() {
        return competencyTypeId;
    }

    public void setCompetencyTypeId(HrSmCompetencyTypes competencyTypeId) {
        this.competencyTypeId = competencyTypeId;
    }

    public HrSmKmp getKmpId() {
        return kmpId;
    }

    public void setKmpId(HrSmKmp kmpId) {
        this.kmpId = kmpId;
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
        if (!(object instanceof HrSmKnowledgeCompetency)) {
            return false;
        }
        HrSmKnowledgeCompetency other = (HrSmKnowledgeCompetency) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return id.toString();
    }

    public void addMessage() {

        String summary = notApplicable ? "unChecked" : "Checked";
        System.out.println("------------cvalue1  " + notApplicable);
//        HrSmKnowledgeCompetency dd=new HrSmKnowledgeCompetency();
//        dd.setNotApplicable(notApplicable);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(summary));

    }
    
    }
