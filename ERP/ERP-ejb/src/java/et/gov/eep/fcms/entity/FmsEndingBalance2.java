/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.entity;

import et.gov.eep.fcms.entity.admin.FmsAccountingPeriod;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author AB
 */
@Entity
@Table(name = "fms_ending_balance2" )
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsEndingBalance2.findAll", query = "SELECT f FROM FmsEndingBalance2 f"),
    @NamedQuery(name = "FmsEndingBalance2.findByEndingBalanceId", query = "SELECT f FROM FmsEndingBalance2 f WHERE f.endingBalanceId = :endingBalanceId"),
    @NamedQuery(name = "FmsEndingBalance2.findByGLAMOUNT", query = "SELECT f FROM FmsEndingBalance2 f WHERE f.gLAMOUNT = :gLAMOUNT")})
public class FmsEndingBalance2 implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FMS_ENDING_BALANCE2_ENDING_SEQ")
    @SequenceGenerator(name = "FMS_ENDING_BALANCE2_ENDING_SEQ", sequenceName = "FMS_ENDING_BALANCE2_ENDING_SEQ", allocationSize = 1)
    @Basic(optional = false)
    @Column(name = "ENDING_BALANCE_ID", nullable = false)
    private Integer endingBalanceId;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "gLAMOUNT", precision = 18, scale = 0)
    private Double gLAMOUNT;
    @JoinColumn(name = "ACOUNTIG_PERIOD_ID", referencedColumnName = "ACOUNTIG_PERIOD_ID", nullable = false)
    @ManyToOne(optional = false)
    private FmsAccountingPeriod acountigPeriodId;
    @JoinColumn(name = "TB_ID", referencedColumnName = "TB_ID", nullable = false)
    @ManyToOne(optional = false)
    private FmsTrialBalance tbId;

    /**
     *
     */
    public FmsEndingBalance2() {
    }

    /**
     *
     * @param endingBalanceId
     */
    public FmsEndingBalance2(Integer endingBalanceId) {
        this.endingBalanceId = endingBalanceId;
    }

    /**
     *
     * @return
     */
    public Integer getEndingBalanceId() {
        return endingBalanceId;
    }

    /**
     *
     * @param endingBalanceId
     */
    public void setEndingBalanceId(Integer endingBalanceId) {
        this.endingBalanceId = endingBalanceId;
    }

    /**
     *
     * @return
     */
    public Double getGLAMOUNT() {
        return gLAMOUNT;
    }

    /**
     *
     * @param gLAMOUNT
     */
    public void setGLAMOUNT(Double gLAMOUNT) {
        this.gLAMOUNT = gLAMOUNT;
    }

    /**
     *
     * @return
     */
    public FmsAccountingPeriod getAcountigPeriodId() {
        return acountigPeriodId;
    }

    /**
     *
     * @param acountigPeriodId
     */
    public void setAcountigPeriodId(FmsAccountingPeriod acountigPeriodId) {
        this.acountigPeriodId = acountigPeriodId;
    }

    /**
     *
     * @return
     */
    public FmsTrialBalance getTbId() {
        return tbId;
    }

    /**
     *
     * @param tbId
     */
    public void setTbId(FmsTrialBalance tbId) {
        this.tbId = tbId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (endingBalanceId != null ? endingBalanceId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FmsEndingBalance2)) {
            return false;
        }
        FmsEndingBalance2 other = (FmsEndingBalance2) object;
        if ((this.endingBalanceId == null && other.endingBalanceId != null) || (this.endingBalanceId != null && !this.endingBalanceId.equals(other.endingBalanceId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.insa.erp.ibfms.entity.FmsEndingBalance2[ endingBalanceId=" + endingBalanceId + " ]";
    }
    
}
