/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.entity;

import java.io.Serializable;
import java.math.BigInteger;
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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Hanoc
 */
@Entity
@Table(name = "FMS_OT_RATE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsOtRate.findAll", query = "SELECT f FROM FmsOtRate f"),
    @NamedQuery(name = "FmsOtRate.findByOtrateId", query = "SELECT f FROM FmsOtRate f WHERE f.otrateId = :otrateId"),
    @NamedQuery(name = "FmsOtRate.findByOtType", query = "SELECT f FROM FmsOtRate f WHERE f.otType = :otType"),
    @NamedQuery(name = "FmsOtRate.findByOtRate", query = "SELECT f FROM FmsOtRate f WHERE f.otRate = :otRate"),
    @NamedQuery(name = "FmsOtRate.findByDescription", query = "SELECT f FROM FmsOtRate f WHERE f.description = :description")})
public class FmsOtRate implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FMS_SEQ_OVERTIME_RATE")
    @SequenceGenerator(name = "FMS_SEQ_OVERTIME_RATE", sequenceName = "FMS_SEQ_OVERTIME_RATE", allocationSize = 1)
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "OTRATE_ID")
    private String otrateId;
    @Size(max = 20)
    @Column(name = "OT_TYPE")
    private String otType;
    @Column(name = "OT_RATE")
    private BigInteger otRate;
    @Size(max = 100)
    @Column(name = "DESCRIPTION")
    private String description;

    /**
     *
     */
    public FmsOtRate() {
    }

    /**
     *
     * @param otrateId
     */
    public FmsOtRate(String otrateId) {
        this.otrateId = otrateId;
    }

    /**
     *
     * @return
     */
    public String getOtrateId() {
        return otrateId;
    }

    /**
     *
     * @param otrateId
     */
    public void setOtrateId(String otrateId) {
        this.otrateId = otrateId;
    }

    /**
     *
     * @return
     */
    public String getOtType() {
        return otType;
    }

    /**
     *
     * @param otType
     */
    public void setOtType(String otType) {
        this.otType = otType;
    }

    /**
     *
     * @return
     */
    public BigInteger getOtRate() {
        return otRate;
    }

    /**
     *
     * @param otRate
     */
    public void setOtRate(BigInteger otRate) {
        this.otRate = otRate;
    }

    /**
     *
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (otrateId != null ? otrateId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FmsOtRate)) {
            return false;
        }
        FmsOtRate other = (FmsOtRate) object;
        if ((this.otrateId == null && other.otrateId != null) || (this.otrateId != null && !this.otrateId.equals(other.otrateId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "folder.FmsOtRate[ otrateId=" + otrateId + " ]";
    }
      @Transient
    private String otTypeVar;

    /**
     *
     * @return
     */
    public String getOtTypeVar() {
        if (otType != null) {
            switch (otType) {
                case "1":
                    otTypeVar = "Normal";
                    break;
                case "2":
                    otTypeVar = "Night";
                    break;
                case "3":
                    otTypeVar = "Holy Day";
                    break;

                default:
                    otTypeVar = null;
                    break;
            }
        }
        return otTypeVar;
    }

    /**
     *
     * @param otTypeVar
     */
    public void setOtTypeVar(String otTypeVar) {
        this.otTypeVar = otTypeVar;
    }
    
}
