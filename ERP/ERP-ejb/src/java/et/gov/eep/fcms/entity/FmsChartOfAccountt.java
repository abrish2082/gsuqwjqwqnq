/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author AB
 */
@Entity
@Table(name = "fms_chart_of_accountt")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsChartOfAccountt.findAll", query = "SELECT f FROM FmsChartOfAccountt f"),
    @NamedQuery(name = "FmsChartOfAccountt.findByFmsChartOfAccountId", query = "SELECT f FROM FmsChartOfAccountt f WHERE f.fmsChartOfAccountId = :fmsChartOfAccountId"),
    @NamedQuery(name = "FmsChartOfAccountt.findByGlcode", query = "SELECT f FROM FmsChartOfAccountt f WHERE f.glcode = :glcode")})
public class FmsChartOfAccountt implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FMS_CHART_OF_ACCOUNT_CHART_SEQ")
    @SequenceGenerator(name = "FMS_CHART_OF_ACCOUNT_CHART_SEQ", sequenceName = "FMS_CHART_OF_ACCOUNT_CHART_SEQ", allocationSize = 1)
    @Basic(optional = false)
    @Column(name = "fms_chart_of_account_id", nullable = false)
    private Integer fmsChartOfAccountId;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private int glcode;

    /**
     *
     */
    public FmsChartOfAccountt() {
    }

    /**
     *
     * @param fmsChartOfAccountId
     */
    public FmsChartOfAccountt(Integer fmsChartOfAccountId) {
        this.fmsChartOfAccountId = fmsChartOfAccountId;
    }

    /**
     *
     * @param fmsChartOfAccountId
     * @param glcode
     */
    public FmsChartOfAccountt(Integer fmsChartOfAccountId, int glcode) {
        this.fmsChartOfAccountId = fmsChartOfAccountId;
        this.glcode = glcode;
    }

    /**
     *
     * @return
     */
    public Integer getFmsChartOfAccountId() {
        return fmsChartOfAccountId;
    }

    /**
     *
     * @param fmsChartOfAccountId
     */
    public void setFmsChartOfAccountId(Integer fmsChartOfAccountId) {
        this.fmsChartOfAccountId = fmsChartOfAccountId;
    }

    /**
     *
     * @return
     */
    public int getGlcode() {
        return glcode;
    }

    /**
     *
     * @param glcode
     */
    public void setGlcode(int glcode) {
        this.glcode = glcode;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (fmsChartOfAccountId != null ? fmsChartOfAccountId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FmsChartOfAccountt)) {
            return false;
        }
        FmsChartOfAccountt other = (FmsChartOfAccountt) object;
        if ((this.fmsChartOfAccountId == null && other.fmsChartOfAccountId != null) || (this.fmsChartOfAccountId != null && !this.fmsChartOfAccountId.equals(other.fmsChartOfAccountId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.insa.erp.ibfms.entity.FmsChartOfAccountt[ fmsChartOfAccountId=" + fmsChartOfAccountId + " ]";
    }

}
