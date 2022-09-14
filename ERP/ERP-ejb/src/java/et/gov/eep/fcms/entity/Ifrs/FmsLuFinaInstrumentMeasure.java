/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.entity.Ifrs;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Basic;
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
 * @author mz
 */
@Entity
@Table(name = "FMS_LU_FINA_INSTRUMENT_MEASURE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsLuFinaInstrumentMeasure.findAll", query = "SELECT f FROM FmsLuFinaInstrumentMeasure f"),
    @NamedQuery(name = "FmsLuFinaInstrumentMeasure.findByInstMeasureId", query = "SELECT f FROM FmsLuFinaInstrumentMeasure f WHERE f.instMeasureId = :instMeasureId"),
    @NamedQuery(name = "FmsLuFinaInstrumentMeasure.findByName", query = "SELECT f FROM FmsLuFinaInstrumentMeasure f WHERE f.name = :name"),
    @NamedQuery(name = "FmsLuFinaInstrumentMeasure.findByDescription", query = "SELECT f FROM FmsLuFinaInstrumentMeasure f WHERE f.description = :description")})
public class FmsLuFinaInstrumentMeasure implements Serializable {

    private static final long serialVersionUID = 1L;
    //<editor-fold defaultstate="collapsed" desc="variable declaration">
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "INST_MEASURE_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FMS_LU_FINAL_INSTR_MEASURE_SEQ")
    @SequenceGenerator(name = "FMS_LU_FINAL_INSTR_MEASURE_SEQ", sequenceName = "FMS_LU_FINAL_INSTR_MEASURE_SEQ", allocationSize = 1)
    private BigDecimal instMeasureId;
    @Size(max = 50)
    @Column(name = "NAME")
    private String name;
    @Size(max = 50)
    @Column(name = "DESCRIPTION")
    private String description;
    @OneToMany(mappedBy = "financialMeasurement")
    private List<FinancialInstrumentRegister> financialInstrumentRegisterList;
//</editor-fold>

    public FmsLuFinaInstrumentMeasure() {
    }
//<editor-fold defaultstate="collapsed" desc="getter and setter">

    public FmsLuFinaInstrumentMeasure(BigDecimal instMeasureId) {
        this.instMeasureId = instMeasureId;
    }

    public BigDecimal getInstMeasureId() {
        return instMeasureId;
    }

    public void setInstMeasureId(BigDecimal instMeasureId) {
        this.instMeasureId = instMeasureId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
//</editor-fold>

    @XmlTransient
    public List<FinancialInstrumentRegister> getFinancialInstrumentRegisterList() {
        return financialInstrumentRegisterList;
    }

    public void setFinancialInstrumentRegisterList(List<FinancialInstrumentRegister> financialInstrumentRegisterList) {
        this.financialInstrumentRegisterList = financialInstrumentRegisterList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (instMeasureId != null ? instMeasureId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof FmsLuFinaInstrumentMeasure)) {
            return false;
        }
        FmsLuFinaInstrumentMeasure other = (FmsLuFinaInstrumentMeasure) object;
        if ((this.instMeasureId == null && other.instMeasureId != null) || (this.instMeasureId != null && !this.instMeasureId.equals(other.instMeasureId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return name;
    }

}
