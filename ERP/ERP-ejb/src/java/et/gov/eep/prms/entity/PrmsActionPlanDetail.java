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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author user
 */
@Entity
@Table(name = "PRMS_ACTION_PLAN_DETAIL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrmsActionPlanDetail.findAll", query = "SELECT p FROM PrmsActionPlanDetail p"),
    @NamedQuery(name = "PrmsActionPlanDetail.findById", query = "SELECT p FROM PrmsActionPlanDetail p WHERE p.id = :id"),
    @NamedQuery(name = "PrmsActionPlanDetail.findByLuDescrId", query = "SELECT p FROM PrmsActionPlanDetail p WHERE p.luDescrId = :luDescrId"),
    @NamedQuery(name = "PrmsActionPlanDetail.findByStartWeek", query = "SELECT p FROM PrmsActionPlanDetail p WHERE p.startWeek = :startWeek"),
    @NamedQuery(name = "PrmsActionPlanDetail.findByEndWeek", query = "SELECT p FROM PrmsActionPlanDetail p WHERE p.endWeek = :endWeek"),
    @NamedQuery(name = "PrmsActionPlanDetail.findByRemark", query = "SELECT p FROM PrmsActionPlanDetail p WHERE p.remark = :remark")})
public class PrmsActionPlanDetail implements Serializable {
    @JoinColumn(name = "LU_DESCR_ID", referencedColumnName = "ID")
    @ManyToOne
    private PrmsLuTaskDescription luDescrId;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "ID")
     @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRMS_ACTIN_PLN_DET_SEQ_GENERATOR")
    @SequenceGenerator(name = "PRMS_ACTIN_PLN_DET_SEQ_GENERATOR", sequenceName = "PRMS_ACTIN_PLN_DET_SEQ", allocationSize = 1)
    private String id;
  
    @Size(max = 20)
    @Column(name = "START_WEEK")
    private String startWeek;
    @Size(max = 20)
    @Column(name = "END_WEEK")
    private String endWeek;
    @Size(max = 20)
    @Column(name = "REMARK")
    private String remark;
    @JoinColumn(name = "ACTION_PLAN_ID", referencedColumnName = "ID")
    @ManyToOne
    private PrmsActionPlan actionPlanId;

    /**
     *
     */
    public PrmsActionPlanDetail() {
    }

    /**
     *
     * @param id
     */
    public PrmsActionPlanDetail(String id) {
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
    public String getStartWeek() {
        return startWeek;
    }

    /**
     *
     * @param startWeek
     */
    public void setStartWeek(String startWeek) {
        this.startWeek = startWeek;
    }

    /**
     *
     * @return
     */
    public String getEndWeek() {
        return endWeek;
    }

    /**
     *
     * @param endWeek
     */
    public void setEndWeek(String endWeek) {
        this.endWeek = endWeek;
    }

    /**
     *
     * @return
     */
    public String getRemark() {
        return remark;
    }

    /**
     *
     * @param remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    public PrmsActionPlan getActionPlanId() {
        return actionPlanId;
    }

    public void setActionPlanId(PrmsActionPlan actionPlanId) {
        this.actionPlanId = actionPlanId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    /**
     *
     * @param object
     * @return
     */
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PrmsActionPlanDetail)) {
            return false;
        }
        PrmsActionPlanDetail other = (PrmsActionPlanDetail) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.prms.entity.PrmsActionPlanDetail[ id=" + id + " ]";
    }

    public PrmsLuTaskDescription getLuDescrId() {
        return luDescrId;
    }

    public void setLuDescrId(PrmsLuTaskDescription luDescrId) {
        this.luDescrId = luDescrId;
    }
    
}
