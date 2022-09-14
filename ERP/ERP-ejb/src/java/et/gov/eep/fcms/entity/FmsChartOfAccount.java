/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.entity;

import et.gov.eep.fcms.entity.admin.FmsGeneralLedger;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author AB
 */
@Entity

@Table(name = "fms_chart_of_account", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"GENERAL_LEDGER_ID"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsChartOfAccount.findAll", query = "SELECT f FROM FmsChartOfAccount f"),
    @NamedQuery(name = "FmsChartOfAccount.findByChartOfAccountId", query = "SELECT f FROM FmsChartOfAccount f WHERE f.chartOfAccountId = :chartOfAccountId"),
    @NamedQuery(name = "FmsChartOfAccount.findByPreparedBy", query = "SELECT f FROM FmsChartOfAccount f WHERE f.preparedBy = :preparedBy"),
    @NamedQuery(name = "FmsChartOfAccount.findByPreparedDate", query = "SELECT f FROM FmsChartOfAccount f WHERE f.preparedDate = :preparedDate")})
public class FmsChartOfAccount implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FMS_CHART_OF_ACCOUNT__CHAR_SEQ")
    @SequenceGenerator( name = "FMS_CHART_OF_ACCOUNT__CHAR_SEQ", sequenceName = "FMS_CHART_OF_ACCOUNT__CHAR_SEQ", allocationSize = 1)
    @Basic(optional = false)
    @Column(name = "CHART_OF_ACCOUNT_ID", nullable = false)
    private Long chartOfAccountId;
    @Column(name = "STATUS")
    private String status;
    @Column(name = "TYPE")
    private String type;
    @Column(name = "PREPARED_BY", length = 35)
    private String preparedBy;
    @Column(name = "PREPARED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date preparedDate;
    @JoinColumn(name = "GENERAL_LEDGER_ID", referencedColumnName = "GENERAL_LEDGER_ID")
    @OneToOne
    private FmsGeneralLedger generalLedgerId;

    /**
     *
     */
    public FmsChartOfAccount() {
    }

    /**
     *
     * @param chartOfAccountId
     */
    public FmsChartOfAccount(Long chartOfAccountId) {
        this.chartOfAccountId = chartOfAccountId;
    }

    /**
     *
     * @return
     */
    public Long getChartOfAccountId() {
        return chartOfAccountId;
    }

    /**
     *
     * @param chartOfAccountId
     */
    public void setChartOfAccountId(Long chartOfAccountId) {
        this.chartOfAccountId = chartOfAccountId;
    }

    /**
     *
     * @return
     */
    public String getStatus() {
        return status;
    }

    /**
     *
     * @param status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     *
     * @return
     */
    public String getType() {
        return type;
    }

    /**
     *
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     *
     * @return
     */
    public String getPreparedBy() {
        return preparedBy;
    }

    /**
     *
     * @param preparedBy
     */
    public void setPreparedBy(String preparedBy) {
        this.preparedBy = preparedBy;
    }

    /**
     *
     * @return
     */
    public Date getPreparedDate() {
        return preparedDate;
    }

    /**
     *
     * @param preparedDate
     */
    public void setPreparedDate(Date preparedDate) {
        this.preparedDate = preparedDate;
    }

    /**
     *
     * @return
     */
    public FmsGeneralLedger getGeneralLedgerId() {
        return generalLedgerId;
    }

    /**
     *
     * @param generalLedgerId
     */
    public void setGeneralLedgerId(FmsGeneralLedger generalLedgerId) {
        this.generalLedgerId = generalLedgerId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (chartOfAccountId != null ? chartOfAccountId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FmsChartOfAccount)) {
            return false;
        }
        FmsChartOfAccount other = (FmsChartOfAccount) object;
        if ((this.chartOfAccountId == null && other.chartOfAccountId != null) || (this.chartOfAccountId != null && !this.chartOfAccountId.equals(other.chartOfAccountId))) {
            return false;
        }
        return true;
    }

//    @Override
//    public String toString() {
//        return  chartOfAccountId.toString() ;
//    }
}
