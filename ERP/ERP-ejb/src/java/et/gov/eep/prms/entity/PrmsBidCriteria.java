/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.entity;

import java.io.Serializable;
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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author user
 */
@Entity
@Table(name = "PRMS_BID_CRITERIA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrmsBidCriteria.findAll", query = "SELECT p FROM PrmsBidCriteria p"),
    @NamedQuery(name = "PrmsBidCriteria.findById", query = "SELECT p FROM PrmsBidCriteria p WHERE p.id = :id"),
    @NamedQuery(name = "PrmsBidCriteria.findCriteriaNoAuto", query = "SELECT p FROM PrmsBidCriteria p WHERE p.id = (SELECT Max(p.id)  from PrmsBidCriteria p)"),
    @NamedQuery(name = "PrmsBidCriteria.findByCriteriaNo", query = "SELECT p FROM PrmsBidCriteria p WHERE p.criteriaNo LIKE :criteriaNo"),
    @NamedQuery(name = "PrmsBidCriteria.findByCriteria", query = "SELECT p FROM PrmsBidCriteria p WHERE p.criteria = :criteria"),
    @NamedQuery(name = "PrmsBidCriteria.findByDateReg", query = "SELECT p FROM PrmsBidCriteria p WHERE p.dateReg = :dateReg"),
    @NamedQuery(name = "PrmsBidCriteria.findByPreparedBy", query = "SELECT p FROM PrmsBidCriteria p WHERE p.preparedBy = :preparedBy"),
    @NamedQuery(name = "PrmsBidCriteria.findByReamark", query = "SELECT p FROM PrmsBidCriteria p WHERE p.reamark = :reamark")})
public class PrmsBidCriteria implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "ID", nullable = false, length = 20)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRMS_BID_CRITRIA_SEQ_GENERATOR")
    @SequenceGenerator(name = "PRMS_BID_CRITRIA_SEQ_GENERATOR", sequenceName = "PRMS_BID_CRITRIA_SEQ", allocationSize = 1)
    private String id;
    @Size(max = 100)
    @Column(name = "CRITERIA_NO", length = 100)
    private String criteriaNo;
    @Size(max = 50)
    @Column(name = "CRITERIA")
    private String criteria;
    @Column(name = "STATUS")
    private Integer status;
    @Column(name = "DATE_REG")

    private String dateReg;
    @Column(name = "PREPARED_BY")
    private Integer preparedBy;
    @Size(max = 300)
    @Column(name = "REAMARK")
    private String reamark;

    //<editor-fold defaultstate="collapsed" desc="Declaring Variables for Dynamic Searching">
    @Transient
    private String columnName;
    @Transient
    private String columnValue;
    //</editor-fold>

    @JoinColumn(name = "BID_ID", referencedColumnName = "ID")
    @ManyToOne
    private PrmsBid bidId;

    /**
     *
     */
    public PrmsBidCriteria() {
    }

    /**
     *
     * @param id
     */
    public PrmsBidCriteria(String id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public String getCriteriaNo() {
        return criteriaNo;
    }

    /**
     *
     * @param criteriaNo
     */
    public void setCriteriaNo(String criteriaNo) {
        this.criteriaNo = criteriaNo;
    }

    /**
     *
     * @return
     */
    public String getCriteria() {
        return criteria;
    }

    /**
     *
     * @param criteria
     */
    public void setCriteria(String criteria) {
        this.criteria = criteria;
    }

    /**
     *
     * @return
     */
    public String getDateReg() {
        return dateReg;
    }

    /**
     *
     * @param dateReg
     */
    public void setDateReg(String dateReg) {
        this.dateReg = dateReg;
    }

    /**
     *
     * @return
     */
    public Integer getPreparedBy() {
        return preparedBy;
    }

    /**
     *
     * @param preparedBy
     */
    public void setPreparedBy(Integer preparedBy) {
        this.preparedBy = preparedBy;
    }

    /**
     *
     * @return
     */
    public String getReamark() {
        return reamark;
    }

    /**
     *
     * @param reamark
     */
    public void setReamark(String reamark) {
        this.reamark = reamark;
    }

    //<editor-fold defaultstate="collapsed" desc="getter and setter of Variables for Dynamic Searching">
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
    //</editor-fold>

    /**
     *
     * @return
     */
    public PrmsBid getBidId() {
        return bidId;
    }

    /**
     *
     * @param bidId
     */
    public void setBidId(PrmsBid bidId) {
        this.bidId = bidId;
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
        if (!(object instanceof PrmsBidCriteria)) {
            return false;
        }
        PrmsBidCriteria other = (PrmsBidCriteria) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return criteria;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
