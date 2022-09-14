/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.entity.budget;

import java.io.Serializable;
import java.math.BigDecimal;
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
 * @author Me
 */
@Entity
@Table(name = "FMS_CB_DISBURSEMENT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsCbDisbursement.findAll", query = "SELECT f FROM FmsCbDisbursement f"),
    @NamedQuery(name = "FmsCbDisbursement.findByDisbursementId", query = "SELECT f FROM FmsCbDisbursement f WHERE f.disbursementId = :disbursementId"),
    @NamedQuery(name = "FmsCbDisbursement.findByTaskId", query = "SELECT f FROM FmsCbDisbursement f WHERE f.cbTaskId = :cbTaskId"),
    @NamedQuery(name = "FmsCbDisbursement.findByHamle", query = "SELECT f FROM FmsCbDisbursement f WHERE f.hamle = :hamle"),
    @NamedQuery(name = "FmsCbDisbursement.findByNehasie", query = "SELECT f FROM FmsCbDisbursement f WHERE f.nehasie = :nehasie"),
    @NamedQuery(name = "FmsCbDisbursement.findByMeskerem", query = "SELECT f FROM FmsCbDisbursement f WHERE f.meskerem = :meskerem"),
    @NamedQuery(name = "FmsCbDisbursement.findByTikemt", query = "SELECT f FROM FmsCbDisbursement f WHERE f.tikemt = :tikemt"),
    @NamedQuery(name = "FmsCbDisbursement.findByHidar", query = "SELECT f FROM FmsCbDisbursement f WHERE f.hidar = :hidar"),
    @NamedQuery(name = "FmsCbDisbursement.findByTahsas", query = "SELECT f FROM FmsCbDisbursement f WHERE f.tahsas = :tahsas"),
    @NamedQuery(name = "FmsCbDisbursement.findByTir", query = "SELECT f FROM FmsCbDisbursement f WHERE f.tir = :tir"),
    @NamedQuery(name = "FmsCbDisbursement.findByYekatit", query = "SELECT f FROM FmsCbDisbursement f WHERE f.yekatit = :yekatit"),
    @NamedQuery(name = "FmsCbDisbursement.findByMegabit", query = "SELECT f FROM FmsCbDisbursement f WHERE f.megabit = :megabit"),
    @NamedQuery(name = "FmsCbDisbursement.findByMiyaziya", query = "SELECT f FROM FmsCbDisbursement f WHERE f.miyaziya = :miyaziya"),
    @NamedQuery(name = "FmsCbDisbursement.findByGinbot", query = "SELECT f FROM FmsCbDisbursement f WHERE f.ginbot = :ginbot"),
    @NamedQuery(name = "FmsCbDisbursement.findBySene", query = "SELECT f FROM FmsCbDisbursement f WHERE f.sene = :sene"),
    @NamedQuery(name = "FmsCbDisbursement.findByDescription", query = "SELECT f FROM FmsCbDisbursement f WHERE f.description = :description"),
    @NamedQuery(name = "FmsCbDisbursement.findByBgtDtl", query = "SELECT f FROM FmsCbDisbursement f WHERE f.cbTaskId.cBDetailFk = :cBDetailFk"),
    @NamedQuery(name = "FmsCbDisbursement.fetchDisbursedBudget", query = "SELECT f FROM FmsCbDisbursement f WHERE f.cbTaskId.cBDetailFk.capitalBudgetId.jobNo.jobNo = :jobNo AND f.cbTaskId.cBDetailFk.capitalBudgetId.budgetYear.budgetYear = :budgetYear AND f.cbTaskId.cBDetailFk.capitalBudgetId.ccSsJunction.fmsCostCenter.costCenterId = :costCenterId AND f.cbTaskId.cBDetailFk.capitalBudgetId.status = 10 ORDER BY f.disbursementId ASC")})
public class FmsCbDisbursement implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "DISBURSEMENT_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FMS_SEQ_CB_DISBURSEMENT")
    @SequenceGenerator( name = "FMS_SEQ_CB_DISBURSEMENT", sequenceName = "FMS_SEQ_CB_DISBURSEMENT", allocationSize = 1)
    private Integer disbursementId;
    @Column(name = "HAMLE")
    private BigDecimal hamle;
    @Column(name = "NEHASIE")
    private BigDecimal nehasie;
    @Column(name = "MESKEREM")
    private BigDecimal meskerem;
    @Column(name = "TIKEMT")
    private BigDecimal tikemt;
    @Column(name = "HIDAR")
    private BigDecimal hidar;
    @Column(name = "TAHSAS")
    private BigDecimal tahsas;
    @Column(name = "TIR")
    private BigDecimal tir;
    @Column(name = "YEKATIT")
    private BigDecimal yekatit;
    @Column(name = "MEGABIT")
    private BigDecimal megabit;
    @Column(name = "MIYAZIYA")
    private BigDecimal miyaziya;
    @Column(name = "GINBOT")
    private BigDecimal ginbot;
    @Column(name = "SENE")
    private BigDecimal sene;
    @Column(name = "DESCRIPTION")
    private String description;
    @JoinColumn(name = "CB_TASK_ID", referencedColumnName = "C_B_TASKS_ID")
    @ManyToOne
    private FmsCapitalBudgetTasks cbTaskId;

    public FmsCbDisbursement() {
    }

    public FmsCbDisbursement(Integer disbursementId) {
        this.disbursementId = disbursementId;
    }

    public Integer getDisbursementId() {
        return disbursementId;
    }

    public void setDisbursementId(Integer disbursementId) {
        this.disbursementId = disbursementId;
    }

    public BigDecimal getHamle() {
        return hamle;
    }

    public void setHamle(BigDecimal hamle) {
        this.hamle = hamle;
    }

    public BigDecimal getNehasie() {
        return nehasie;
    }

    public void setNehasie(BigDecimal nehasie) {
        this.nehasie = nehasie;
    }

    public BigDecimal getMeskerem() {
        return meskerem;
    }

    public void setMeskerem(BigDecimal meskerem) {
        this.meskerem = meskerem;
    }

    public BigDecimal getTikemt() {
        return tikemt;
    }

    public void setTikemt(BigDecimal tikemt) {
        this.tikemt = tikemt;
    }

    public BigDecimal getHidar() {
        return hidar;
    }

    public void setHidar(BigDecimal hidar) {
        this.hidar = hidar;
    }

    public BigDecimal getTahsas() {
        return tahsas;
    }

    public void setTahsas(BigDecimal tahsas) {
        this.tahsas = tahsas;
    }

    public BigDecimal getTir() {
        return tir;
    }

    public void setTir(BigDecimal tir) {
        this.tir = tir;
    }

    public BigDecimal getYekatit() {
        return yekatit;
    }

    public void setYekatit(BigDecimal yekatit) {
        this.yekatit = yekatit;
    }

    public BigDecimal getMegabit() {
        return megabit;
    }

    public void setMegabit(BigDecimal megabit) {
        this.megabit = megabit;
    }

    public BigDecimal getMiyaziya() {
        return miyaziya;
    }

    public void setMiyaziya(BigDecimal miyaziya) {
        this.miyaziya = miyaziya;
    }

    public BigDecimal getGinbot() {
        return ginbot;
    }

    public void setGinbot(BigDecimal ginbot) {
        this.ginbot = ginbot;
    }

    public BigDecimal getSene() {
        return sene;
    }

    public void setSene(BigDecimal sene) {
        this.sene = sene;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public FmsCapitalBudgetTasks getCbTaskId() {
        return cbTaskId;
    }

    public void setCbTaskId(FmsCapitalBudgetTasks cbTaskId) {
        this.cbTaskId = cbTaskId;
    }
        
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (disbursementId != null ? disbursementId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FmsCbDisbursement)) {
            return false;
        }
        FmsCbDisbursement other = (FmsCbDisbursement) object;
        if ((this.disbursementId == null && other.disbursementId != null) || (this.disbursementId != null && !this.disbursementId.equals(other.disbursementId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "capbudgt.FmsCbDisbursement[ disbursementId=" + disbursementId + " ]";
    }
    
}
