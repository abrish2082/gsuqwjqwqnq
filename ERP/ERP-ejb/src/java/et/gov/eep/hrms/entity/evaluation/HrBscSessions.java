/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.evaluation;

import java.io.Serializable;
import java.math.BigInteger;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author INSA
 */
@Entity
@Table(name = "HR_BSC_SESSIONS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrBscSessions.findAll", query = "SELECT h FROM HrBscSessions h"),
    @NamedQuery(name = "HrBscSessions.findById", query = "SELECT h FROM HrBscSessions h WHERE h.id = :id"),
    @NamedQuery(name = "HrBscSessions.findByBscYear", query = "SELECT h FROM HrBscSessions h WHERE h.bscYear = :bscYear"),
    @NamedQuery(name = "HrBscSessions.findByTerm", query = "SELECT h FROM HrBscSessions h WHERE h.term = :term"),
    @NamedQuery(name = "HrBscSessions.findByStartDate", query = "SELECT h FROM HrBscSessions h WHERE h.startDate = :startDate"),
    @NamedQuery(name = "HrBscSessions.findByEndDate", query = "SELECT h FROM HrBscSessions h WHERE h.endDate = :endDate"),
    @NamedQuery(name = "HrBscSessions.findByPreparedBy", query = "SELECT h FROM HrBscSessions h WHERE h.preparedBy = :preparedBy"),
    @NamedQuery(name = "HrBscSessions.findByPreparedOn", query = "SELECT h FROM HrBscSessions h WHERE h.preparedOn = :preparedOn"),
    @NamedQuery(name = "HrBscSessions.findByRemark", query = "SELECT h FROM HrBscSessions h WHERE h.remark = :remark"),
    @NamedQuery(name = "HrBscSessions.findActiveSession", query = "SELECT h FROM HrBscSessions h WHERE h.startDate <= :toDayInEC and h.endDate >= :toDayInEC")})
public class HrBscSessions implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_BSC_SESSIONS_SEQ")
    @SequenceGenerator(name = "HR_BSC_SESSIONS_SEQ", sequenceName = "HR_BSC_SESSIONS_SEQ", allocationSize = 1)
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Integer id;
    @Column(name = "BSC_YEAR")
    private BigInteger bscYear;
    @Column(name = "TERM")
    private String term;
    @Size(max = 20)
    @Column(name = "START_DATE")
    private String startDate;
    @Size(max = 20)
    @Column(name = "END_DATE")
    private String endDate;
    @Column(name = "PREPARED_BY")
    private BigInteger preparedBy;
    @Size(max = 20)
    @Column(name = "PREPARED_ON")
    private String preparedOn;
    @Size(max = 200)
    @Column(name = "REMARK")
    private String remark;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sessionId")
    private List<HrBsc> hrBscList;

    public HrBscSessions() {
    }

    public HrBscSessions(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigInteger getBscYear() {
        return bscYear;
    }

    public void setBscYear(BigInteger bscYear) {
        this.bscYear = bscYear;
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

    public BigInteger getPreparedBy() {
        return preparedBy;
    }

    public void setPreparedBy(BigInteger preparedBy) {
        this.preparedBy = preparedBy;
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

    @XmlTransient
    public List<HrBsc> getHrBscList() {
        return hrBscList;
    }

    public void setHrBscList(List<HrBsc> hrBscList) {
        this.hrBscList = hrBscList;
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
        if (!(object instanceof HrBscSessions)) {
            return false;
        }
        HrBscSessions other = (HrBscSessions) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.HrBscSessions[ id=" + id + " ]";
    }

}
