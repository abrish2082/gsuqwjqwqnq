/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.entity.budget;

import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Me
 */
@Entity
@Table(name = "FMS_OB_DISBURSEMENT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsObDisbursement.findAll", query = "SELECT f FROM FmsObDisbursement f"),
    @NamedQuery(name = "FmsObDisbursement.findByDisbursementId", query = "SELECT f FROM FmsObDisbursement f WHERE f.disbursementId = :disbursementId"),
    @NamedQuery(name = "FmsObDisbursement.findByTaskId", query = "SELECT f FROM FmsObDisbursement f WHERE f.obTaskId = :obTaskId"),
    @NamedQuery(name = "FmsObDisbursement.findByHamle", query = "SELECT f FROM FmsObDisbursement f WHERE f.hamle = :hamle"),
    @NamedQuery(name = "FmsObDisbursement.findByNehasie", query = "SELECT f FROM FmsObDisbursement f WHERE f.nehasie = :nehasie"),
    @NamedQuery(name = "FmsObDisbursement.findByMeskerem", query = "SELECT f FROM FmsObDisbursement f WHERE f.meskerem = :meskerem"),
    @NamedQuery(name = "FmsObDisbursement.findByTikemt", query = "SELECT f FROM FmsObDisbursement f WHERE f.tikemt = :tikemt"),
    @NamedQuery(name = "FmsObDisbursement.findByHidar", query = "SELECT f FROM FmsObDisbursement f WHERE f.hidar = :hidar"),
    @NamedQuery(name = "FmsObDisbursement.findByTahsas", query = "SELECT f FROM FmsObDisbursement f WHERE f.tahsas = :tahsas"),
    @NamedQuery(name = "FmsObDisbursement.findByTir", query = "SELECT f FROM FmsObDisbursement f WHERE f.tir = :tir"),
    @NamedQuery(name = "FmsObDisbursement.findByYekatit", query = "SELECT f FROM FmsObDisbursement f WHERE f.yekatit = :yekatit"),
    @NamedQuery(name = "FmsObDisbursement.findByMegabit", query = "SELECT f FROM FmsObDisbursement f WHERE f.megabit = :megabit"),
    @NamedQuery(name = "FmsObDisbursement.findByMiyaziya", query = "SELECT f FROM FmsObDisbursement f WHERE f.miyaziya = :miyaziya"),
    @NamedQuery(name = "FmsObDisbursement.findByGinbot", query = "SELECT f FROM FmsObDisbursement f WHERE f.ginbot = :ginbot"),
    @NamedQuery(name = "FmsObDisbursement.findBySene", query = "SELECT f FROM FmsObDisbursement f WHERE f.sene = :sene"),    
    @NamedQuery(name = "FmsObDisbursement.findByDescription", query = "SELECT f FROM FmsObDisbursement f WHERE f.description = :description"),
    @NamedQuery(name = "FmsObDisbursement.findByOperatingBudgetId", query = "SELECT f FROM FmsObDisbursement f WHERE f.obTaskId.oBDetailFk = :oBDetailFk")})
public class FmsObDisbursement implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "DISBURSEMENT_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FMS_OB_DISBURSEMENT_SEQ")
    @SequenceGenerator( name = "FMS_OB_DISBURSEMENT_SEQ", sequenceName = "FMS_OB_DISBURSEMENT_SEQ", allocationSize = 1)
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
    @JoinColumn(name = "OB_TASK_ID", referencedColumnName = "O_B_TASK_ID")
    @ManyToOne
    private FmsOperatingBudgetTasks obTaskId;

    public FmsObDisbursement() {
    }

    public FmsObDisbursement(Integer disbursementId) {
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

    public FmsOperatingBudgetTasks getObTaskId() {
        return obTaskId;
    }

    public void setObTaskId(FmsOperatingBudgetTasks obTaskId) {
        this.obTaskId = obTaskId;
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
        if (!(object instanceof FmsObDisbursement)) {
            return false;
        }
        FmsObDisbursement other = (FmsObDisbursement) object;
        if ((this.disbursementId == null && other.disbursementId != null) || (this.disbursementId != null && !this.disbursementId.equals(other.disbursementId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.fcms.entity.budget.FmsObDisbursement[ disbursementId=" + disbursementId + " ]";
    }
    
}
