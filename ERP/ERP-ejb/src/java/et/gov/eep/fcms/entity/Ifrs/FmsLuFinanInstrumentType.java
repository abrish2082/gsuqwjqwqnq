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
@Table(name = "FMS_LU_FINAN_INSTRUMENT_TYPE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsLuFinanInstrumentType.findAll", query = "SELECT f FROM FmsLuFinanInstrumentType f"),
    @NamedQuery(name = "FmsLuFinanInstrumentType.findByInstTypeId", query = "SELECT f FROM FmsLuFinanInstrumentType f WHERE f.instTypeId = :instTypeId"),
    @NamedQuery(name = "FmsLuFinanInstrumentType.findByName", query = "SELECT f FROM FmsLuFinanInstrumentType f WHERE f.name = :name"),
    @NamedQuery(name = "FmsLuFinanInstrumentType.findByDescription", query = "SELECT f FROM FmsLuFinanInstrumentType f WHERE f.description = :description")})
public class FmsLuFinanInstrumentType implements Serializable {

    private static final long serialVersionUID = 1L;
    //<editor-fold defaultstate="collapsed" desc="variable declaration">
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "INST_TYPE_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FMS_LU_FINAN_INSTRUM_TYPE_SEQ")
    @SequenceGenerator(name = "FMS_LU_FINAN_INSTRUM_TYPE_SEQ", sequenceName = "FMS_LU_FINAN_INSTRUM_TYPE_SEQ", allocationSize = 1)
    private BigDecimal instTypeId;
    @Size(max = 50)
    @Column(name = "NAME")
    private String name;
    @Size(max = 50)
    @Column(name = "DESCRIPTION")
    private String description;
    @OneToMany(mappedBy = "financialInstrumentType")
    private List<FinancialInstrumentRegister> financialInstrumentRegisterList;
//</editor-fold>
    public FmsLuFinanInstrumentType() {
    }
//<editor-fold defaultstate="collapsed" desc="getter and setter">
    public FmsLuFinanInstrumentType(BigDecimal instTypeId) {
        this.instTypeId = instTypeId;
    }

    public BigDecimal getInstTypeId() {
        return instTypeId;
    }

    public void setInstTypeId(BigDecimal instTypeId) {
        this.instTypeId = instTypeId;
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
//</editor-fold>+
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
        hash += (instTypeId != null ? instTypeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof FmsLuFinanInstrumentType)) {
            return false;
        }
        FmsLuFinanInstrumentType other = (FmsLuFinanInstrumentType) object;
        if ((this.instTypeId == null && other.instTypeId != null) || (this.instTypeId != null && !this.instTypeId.equals(other.instTypeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return name;
    }

}
