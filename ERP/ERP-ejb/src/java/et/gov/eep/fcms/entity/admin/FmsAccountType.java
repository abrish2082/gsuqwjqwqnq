/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.entity.admin;

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
 * @author user
 */
@Entity
@Table(name = "FMS_ACCOUNT_TYPE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsAccountType.findAll", query = "SELECT f FROM FmsAccountType f"),
    @NamedQuery(name = "FmsAccountType.findById", query = "SELECT f FROM FmsAccountType f WHERE f.id = :id"),
    @NamedQuery(name = "FmsAccountType.findByType", query = "SELECT f FROM FmsAccountType f WHERE f.type = :type")})
public class FmsAccountType implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FMS_SEQ_BANK")
    @SequenceGenerator(name = "FMS_ACCOUNT_TYPE_SEQ", sequenceName = "FMS_ACCOUNT_TYPE_SEQ", allocationSize = 1)
    @Column(name = "ID")
    private BigDecimal id;
    @Size(max = 20)
    @Column(name = "TYPE")
    private String type;
    @OneToMany(mappedBy = "accountCategory")
    private List<FmsGeneralLedger> fmsGeneralLedgerList;

    public FmsAccountType() {
    }

    public FmsAccountType(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof FmsAccountType)) {
            return false;
        }
        FmsAccountType other = (FmsAccountType) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.hrms.entity.allowance.FmsAccountType[ id=" + id + " ]";
    }

    @XmlTransient
    public List<FmsGeneralLedger> getFmsGeneralLedgerList() {
        return fmsGeneralLedgerList;
    }

    public void setFmsGeneralLedgerList(List<FmsGeneralLedger> fmsGeneralLedgerList) {
        this.fmsGeneralLedgerList = fmsGeneralLedgerList;
    }

}
