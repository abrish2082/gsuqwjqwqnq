/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author user
 */
@Entity
@Table(name = "PRMS_FOREIGN_EX_DETAIL2")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrmsForeignExDetail2.findAll", query = "SELECT p FROM PrmsForeignExDetail2 p"),
    @NamedQuery(name = "PrmsForeignExDetail2.findByUtilizedAmount", query = "SELECT p FROM PrmsForeignExDetail2 p WHERE p.utilizedAmount = :utilizedAmount"),
    @NamedQuery(name = "PrmsForeignExDetail2.findByDatee", query = "SELECT p FROM PrmsForeignExDetail2 p WHERE p.datee = :datee"),
    @NamedQuery(name = "PrmsForeignExDetail2.findByRemark", query = "SELECT p FROM PrmsForeignExDetail2 p WHERE p.remark = :remark"),
    @NamedQuery(name = "PrmsForeignExDetail2.findById", query = "SELECT p FROM PrmsForeignExDetail2 p WHERE p.id = :id")})
public class PrmsForeignExDetail2 implements Serializable {

    private static final long serialVersionUID = 1L;
    @Size(max = 35)
    @Column(name = "UTILIZED_AMOUNT", length = 35)
    private String utilizedAmount;
    @Column(name = "DATEE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datee;
    @Size(max = 100)
    @Column(name = "REMARK", length = 100)
    private String remark;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(generator = "PRMS_FOREIGN_EXCHANGE_DT2_SEQ", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "PRMS_FOREIGN_EXCHANGE_DT2_SEQ", sequenceName = "PRMS_FOREIGN_EXCHANGE_DT2_SEQ", allocationSize = 1)
    @Column(name = "ID", nullable = false, precision = 0, scale = -127)
    private BigDecimal id;
    @JoinColumn(name = "FOREIGN_APP_ID", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrmsForeignExchange foreignAppId;

    public PrmsForeignExDetail2() {
    }

    public PrmsForeignExDetail2(BigDecimal id) {
        this.id = id;
    }

    public String getUtilizedAmount() {
        return utilizedAmount;
    }

    public void setUtilizedAmount(String utilizedAmount) {
        this.utilizedAmount = utilizedAmount;
    }

    public Date getDatee() {
        return datee;
    }

    public void setDatee(Date datee) {
        this.datee = datee;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public PrmsForeignExchange getForeignAppId() {
        return foreignAppId;
    }

    public void setForeignAppId(PrmsForeignExchange foreignAppId) {
        this.foreignAppId = foreignAppId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PrmsForeignExDetail2)) {
            return false;
        }
        PrmsForeignExDetail2 other = (PrmsForeignExDetail2) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.prms.entity.PrmsForeignExDetail2[ id=" + id + " ]";
    }

}
