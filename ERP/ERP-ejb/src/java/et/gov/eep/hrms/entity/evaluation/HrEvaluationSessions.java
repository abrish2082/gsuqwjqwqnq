/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.evaluation;

import et.gov.eep.hrms.entity.employee.HrEmployees;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author INSA
 */
@Entity
@Table(name = "HR_EVALUATION_SESSIONS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrEvaluationSessions.findAll", query = "SELECT h FROM HrEvaluationSessions h"),
    @NamedQuery(name = "HrEvaluationSessions.findById", query = "SELECT h FROM HrEvaluationSessions h WHERE h.id = :id"),
    @NamedQuery(name = "HrEvaluationSessions.findBySessionYear", query = "SELECT h FROM HrEvaluationSessions h WHERE h.sessionYear = :sessionYear"),
    @NamedQuery(name = "HrEvaluationSessions.checkDuplicate", query = "SELECT h FROM HrEvaluationSessions h WHERE h.sessionYear = :year and h.term = :term"),
    @NamedQuery(name = "HrEvaluationSessions.findByTerm", query = "SELECT h FROM HrEvaluationSessions h WHERE UPPER(h.term) LIKE :term"),
    @NamedQuery(name = "HrEvaluationSessions.findByStartDate", query = "SELECT h FROM HrEvaluationSessions h WHERE h.startDate = :startDate"),
    @NamedQuery(name = "HrEvaluationSessions.findByEndDate", query = "SELECT h FROM HrEvaluationSessions h WHERE h.endDate = :endDate"),
    @NamedQuery(name = "HrEvaluationSessions.findByPreparedOn", query = "SELECT h FROM HrEvaluationSessions h WHERE h.preparedOn = :preparedOn"),
    @NamedQuery(name = "HrEvaluationSessions.findByRemark", query = "SELECT h FROM HrEvaluationSessions h WHERE h.remark = :remark"),
    @NamedQuery(name = "HrEvaluationSessions.findActiveSession", query = "SELECT h FROM HrEvaluationSessions h WHERE h.startDate <= :toDayInEC and h.endDate >= :toDayInEC")})

public class HrEvaluationSessions implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_EVALUATION_SESSIONS_SEQ")
    @SequenceGenerator(name = "HR_EVALUATION_SESSIONS_SEQ", sequenceName = "HR_EVALUATION_SESSIONS_SEQ", allocationSize = 1)
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Integer id;
    @Column(name = "SESSION_YEAR")
    private BigInteger sessionYear;
    @Size(max = 20)
    @Column(name = "TERM")
    private String term;
    @Size(max = 20)
    @Column(name = "START_DATE")
    private String startDate;
    @Size(max = 20)
    @Column(name = "END_DATE")
    private String endDate;
    @Size(max = 20)
    @Column(name = "PREPARED_ON")
    private String preparedOn;
    @Size(max = 20)
    @Column(name = "REMARK")
    private String remark;
    @JoinColumn(name = "PREPARED_BY", referencedColumnName = "ID")
    @ManyToOne
    private HrEmployees preparedBy;
    @OneToMany(mappedBy = "sessionId")
    private List<HrEvaluationResults> hrEvaluationResultsList;

    public HrEvaluationSessions() {
    }

    public HrEvaluationSessions(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigInteger getSessionYear() {
        return sessionYear;
    }

    public void setSessionYear(BigInteger sessionYear) {
        this.sessionYear = sessionYear;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getPreparedOn() {
        return preparedOn;
    }

    public void setPreparedOn(String preparedOn) {
        this.preparedOn = preparedOn;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public HrEmployees getPreparedBy() {
        return preparedBy;
    }

    public void setPreparedBy(HrEmployees preparedBy) {
        this.preparedBy = preparedBy;
    }

    @XmlTransient
    public List<HrEvaluationResults> getHrEvaluationResultsList() {
        return hrEvaluationResultsList;
    }

    public void setHrEvaluationResultList(List<HrEvaluationResults> hrEvaluationResultsList) {
        this.hrEvaluationResultsList = hrEvaluationResultsList;
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
        if (!(object instanceof HrEvaluationSessions)) {
            return false;
        }
        HrEvaluationSessions other = (HrEvaluationSessions) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return sessionYear.toString();
    }

}
