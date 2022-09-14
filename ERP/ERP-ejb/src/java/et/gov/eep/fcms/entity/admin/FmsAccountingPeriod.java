/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.entity.admin;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import et.gov.eep.fcms.entity.FmsBeginningBalance;
import et.gov.eep.fcms.entity.FmsEndingBalance2;
import et.gov.eep.fcms.entity.FmsLuBudgetYear;
import et.gov.eep.fcms.entity.FmsTrialBalance;
import et.gov.eep.fcms.entity.fixedasset.FmsDprBuilding;
import et.gov.eep.fcms.entity.fixedasset.FmsDprGeothermal;
import et.gov.eep.fcms.entity.fixedasset.FmsDprHydropower;
import et.gov.eep.fcms.entity.fixedasset.FmsDprOfficeAsset;
import et.gov.eep.fcms.entity.fixedasset.FmsDprSubstation;
import et.gov.eep.fcms.entity.fixedasset.FmsDprTransmisson;
import et.gov.eep.fcms.entity.fixedasset.FmsDprTransport;
import et.gov.eep.fcms.entity.fixedasset.FmsDprWind;

/**
 *
 * @author AB
 */
@Entity
@Table(name = "FMS_ACCOUNTING_PERIOD")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsAccountingPeriod.findAll", query = "SELECT f FROM FmsAccountingPeriod f"),
    @NamedQuery(name = "FmsAccountingPeriod.findAll_before", query = "SELECT f.startDate FROM FmsAccountingPeriod f"),
    @NamedQuery(name = "FmsAccountingPeriod.findByStatusUPDATE", query = " UPDATE     FmsAccountingPeriod f SET f.status = 0 WHERE f.status = 1 "),
    @NamedQuery(name = "FmsAccountingPeriod.findByAcountigPeriodId", query = "SELECT f FROM FmsAccountingPeriod f WHERE f.acountigPeriodId = :acountigPeriodId"),
    @NamedQuery(name = "FmsAccountingPeriod.findByAcountigPeriodId_after", query = "SELECT f.endDate FROM FmsAccountingPeriod f WHERE f.acountigPeriodId >= :acountigPeriodId"),
    @NamedQuery(name = "FmsAccountingPeriod.findByPeiod", query = "SELECT f FROM FmsAccountingPeriod f WHERE f.startDate >= :startDate and f.endDate <= :endDate"),
    @NamedQuery(name = "FmsAccountingPeriod.findByStartDate", query = "SELECT f FROM FmsAccountingPeriod f WHERE f.startDate = :startDate"),
    @NamedQuery(name = "FmsAccountingPeriod.findByEndDate", query = "SELECT f FROM FmsAccountingPeriod f WHERE f.endDate = :endDate"),
    @NamedQuery(name = "FmsAccountingPeriod.findByActivePeriod", query = "SELECT f FROM FmsAccountingPeriod f WHERE f.activePeriod = :activePeriod"),
    @NamedQuery(name = "FmsAccountingPeriod.findByStatusActivePeriod", query = "SELECT f FROM FmsAccountingPeriod f WHERE f.status = :status"),
    @NamedQuery(name = "FmsAccountingPeriod.findByStatus", query = "SELECT f FROM FmsAccountingPeriod f WHERE f.status LIKE :status"),
    @NamedQuery(name = "FmsAccountingPeriod.findByBudjetYear", query = "SELECT f FROM FmsAccountingPeriod f INNER JOIN F.luBudgetYearId AS b WHERE b.luBudgetYearId = :budgetYear"),})
public class FmsAccountingPeriod implements Serializable {

    @Column(name = "STATUS")
    private Integer status;
    @OneToMany(mappedBy = "accountPeriod")
    private List<FmsDprOfficeAsset> fmsDprOfficeAssetList;
    @OneToMany(mappedBy = "accountPeriod")
    private List<FmsDprGeothermal> fmsDprGeothermalList;
    @OneToMany(mappedBy = "accountPeriod")
    private List<FmsDprTransport> fmsDprTransportList;
    @OneToMany(mappedBy = "accountPeriod")
    private List<FmsDprHydropower> fmsDprHydropowerList;
    @OneToMany(mappedBy = "accountPeriod")
    private List<FmsDprBuilding> fmsDprBuildingList;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FMS_ACCOUNTING_PERIOD_SEQ")
    @SequenceGenerator(name = "FMS_ACCOUNTING_PERIOD_SEQ", sequenceName = "FMS_ACCOUNTING_PERIOD_SEQ", allocationSize = 1)
    @Basic(optional = false)
    @Column(name = "ACOUNTIG_PERIOD_ID", nullable = false)
    private Integer acountigPeriodId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "START_DATE", length = 30)
    private String startDate;
    @Size(max = 30)
    @Column(name = "END_DATE", length = 30)
    private String endDate;
    @Size(max = 30)
    @Column(name = "ACTIVE_PERIOD", length = 30)
    private String activePeriod;
    @JoinColumn(name = "LU_BUDGET_YEAR_ID", referencedColumnName = "LU_BUDGET_YEAR_ID")
    @ManyToOne(optional = false)
    private FmsLuBudgetYear luBudgetYearId;
    @OneToMany(mappedBy = "acountigPeriodId")
    private List<FmsTrialBalance> fmsTrialBalanceList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "acountigPeriodId")
    private List<FmsEndingBalance2> fmsEndingBalance2List;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "accountigPeriodId")
    private List<FmsBeginningBalance> fmsBeginningBalanceList;
    @OneToMany(mappedBy = "fiscalYearId")
    private List<FmsVouchersNoRange> fmsVouchersNoRangeList;
    @OneToMany(mappedBy = "accountingPeriod")
    private List<FmsDprTransmisson> fmsDprTransmissonList;
    @OneToMany(mappedBy = "accountPeriod")
    private List<FmsDprSubstation> fmsDprSubstationList;
    @OneToMany(mappedBy = "accountPeriod")
    private List<FmsDprWind> fmsDprWindList;

    /**
     *
     * @return
     */
    @XmlTransient
    public List<FmsVouchersNoRange> getFmsVouchersNoRangeList() {
        return fmsVouchersNoRangeList;
    }

    /**
     *
     * @param fmsVouchersNoRangeList
     */
    public void setFmsVouchersNoRangeList(List<FmsVouchersNoRange> fmsVouchersNoRangeList) {
        this.fmsVouchersNoRangeList = fmsVouchersNoRangeList;
    }

    /**
     *
     */
    public FmsAccountingPeriod() {
    }

    /**
     *
     * @param acountigPeriodId
     */
    public FmsAccountingPeriod(Integer acountigPeriodId) {
        this.acountigPeriodId = acountigPeriodId;
    }

    /**
     *
     * @param acountigPeriodId
     * @param startDate
     */
    public FmsAccountingPeriod(Integer acountigPeriodId, String startDate) {
        this.acountigPeriodId = acountigPeriodId;
        this.startDate = startDate;
    }

    @XmlTransient
    public List<FmsDprTransmisson> getFmsDprTransmissonList() {
        return fmsDprTransmissonList;
    }

    public void setFmsDprTransmissonList(List<FmsDprTransmisson> fmsDprTransmissonList) {
        this.fmsDprTransmissonList = fmsDprTransmissonList;
    }

    @XmlTransient
    public List<FmsDprSubstation> getFmsDprSubstationList() {
        return fmsDprSubstationList;
    }

    public void setFmsDprSubstationList(List<FmsDprSubstation> fmsDprSubstationList) {
        this.fmsDprSubstationList = fmsDprSubstationList;
    }

    public Integer getAcountigPeriodId() {
        return acountigPeriodId;
    }

    /**
     *
     * @param acountigPeriodId
     */
    public void setAcountigPeriodId(Integer acountigPeriodId) {
        this.acountigPeriodId = acountigPeriodId;
    }

    /**
     *
     * @return
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     *
     * @param startDate
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    /**
     *
     * @return
     */
    public String getEndDate() {
        return endDate;
    }

    /**
     *
     * @param endDate
     */
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     *
     * @return
     */
    public String getActivePeriod() {
        return activePeriod;
    }

    /**
     *
     * @param activePeriod
     */
    public void setActivePeriod(String activePeriod) {
        this.activePeriod = activePeriod;
    }

    /**
     *
     * @return
     */
    public FmsLuBudgetYear getLuBudgetYearId() {
        if (luBudgetYearId == null) {
            luBudgetYearId = new FmsLuBudgetYear();
        }
        return luBudgetYearId;
    }

    /**
     *
     * @param luBudgetYearId
     */
    public void setLuBudgetYearId(FmsLuBudgetYear luBudgetYearId) {
        this.luBudgetYearId = luBudgetYearId;
    }

    /**
     *
     * @return
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (acountigPeriodId != null ? acountigPeriodId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FmsAccountingPeriod)) {
            return false;
        }
        FmsAccountingPeriod other = (FmsAccountingPeriod) object;
        if ((this.acountigPeriodId == null && other.acountigPeriodId != null) || (this.acountigPeriodId != null && !this.acountigPeriodId.equals(other.acountigPeriodId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "[" + startDate + "]" + " To " + "[" + endDate + "]";
    }

    @XmlTransient
    public List<FmsTrialBalance> getFmsTrialBalanceList() {
        return fmsTrialBalanceList;
    }

    /**
     *
     * @param fmsTrialBalanceList
     */
    public void setFmsTrialBalanceList(List<FmsTrialBalance> fmsTrialBalanceList) {
        this.fmsTrialBalanceList = fmsTrialBalanceList;
    }

    /**
     *
     * @return
     */
    @XmlTransient
    public List<FmsEndingBalance2> getFmsEndingBalance2List() {
        return fmsEndingBalance2List;
    }

    /**
     *
     * @param fmsEndingBalance2List
     */
    public void setFmsEndingBalance2List(List<FmsEndingBalance2> fmsEndingBalance2List) {
        this.fmsEndingBalance2List = fmsEndingBalance2List;
    }

    /**
     *
     * @return
     */
    @XmlTransient
    public List<FmsBeginningBalance> getFmsBeginningBalanceList() {
        return fmsBeginningBalanceList;
    }

    /**
     *
     * @param fmsBeginningBalanceList
     */
    public void setFmsBeginningBalanceList(List<FmsBeginningBalance> fmsBeginningBalanceList) {
        this.fmsBeginningBalanceList = fmsBeginningBalanceList;
    }

    @XmlTransient
    public List<FmsDprGeothermal> getFmsDprGeothermalList() {
        return fmsDprGeothermalList;
    }

    public void setFmsDprGeothermalList(List<FmsDprGeothermal> fmsDprGeothermalList) {
        this.fmsDprGeothermalList = fmsDprGeothermalList;
    }

    @XmlTransient
    public List<FmsDprTransport> getFmsDprTransportList() {
        return fmsDprTransportList;
    }

    public void setFmsDprTransportList(List<FmsDprTransport> fmsDprTransportList) {
        this.fmsDprTransportList = fmsDprTransportList;
    }

    @XmlTransient
    public List<FmsDprHydropower> getFmsDprHydropowerList() {
        return fmsDprHydropowerList;
    }

    public void setFmsDprHydropowerList(List<FmsDprHydropower> fmsDprHydropowerList) {
        this.fmsDprHydropowerList = fmsDprHydropowerList;
    }

    @XmlTransient
    public List<FmsDprBuilding> getFmsDprBuildingList() {
        return fmsDprBuildingList;
    }

    public void setFmsDprBuildingList(List<FmsDprBuilding> fmsDprBuildingList) {
        this.fmsDprBuildingList = fmsDprBuildingList;
    }

    @XmlTransient
    public List<FmsDprWind> getFmsDprWindList() {
        return fmsDprWindList;
    }

    public void setFmsDprWindList(List<FmsDprWind> fmsDprWindList) {
        this.fmsDprWindList = fmsDprWindList;
    }

    @XmlTransient
    public List<FmsDprOfficeAsset> getFmsDprOfficeAssetList() {
        return fmsDprOfficeAssetList;
    }

    public void setFmsDprOfficeAssetList(List<FmsDprOfficeAsset> fmsDprOfficeAssetList) {
        this.fmsDprOfficeAssetList = fmsDprOfficeAssetList;
    }

}
