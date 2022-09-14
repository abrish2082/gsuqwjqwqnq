/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.entity;

import et.gov.eep.mms.entity.MmsItemRegistration;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.SequenceGenerator;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author user
 */
@Entity
@Table(name = "PRMS_FOREIGN_EX_DETAIL1")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrmsForeignExDetail1.findAll", query = "SELECT p FROM PrmsForeignExDetail1 p"),
    @NamedQuery(name = "PrmsForeignExDetail1.findByQuality", query = "SELECT p FROM PrmsForeignExDetail1 p WHERE p.quality = :quality"),
    @NamedQuery(name = "PrmsForeignExDetail1.findByRemark", query = "SELECT p FROM PrmsForeignExDetail1 p WHERE p.remark = :remark"),
    @NamedQuery(name = "PrmsForeignExDetail1.findById", query = "SELECT p FROM PrmsForeignExDetail1 p WHERE p.id = :id")})
public class PrmsForeignExDetail1 implements Serializable {

    private static final long serialVersionUID = 1L;
    @Size(max = 50)
    @Column(name = "QUALITY", length = 50)
    private String quality;
    @Size(max = 100)
    @Column(name = "REMARK", length = 100)
    private String remark;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(generator ="PRMS_FOREIGN_EXCHANGE_DT1_SEQ", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "PRMS_FOREIGN_EXCHANGE_DT1_SEQ", sequenceName = "PRMS_FOREIGN_EXCHANGE_DT1_SEQ", allocationSize = 1)
    @Column(name = "ID", nullable = false, precision = 0, scale = -127)
    private BigDecimal id;
    @JoinColumn(name = "MATERIAL_ID", referencedColumnName = "MATERIAL_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private MmsItemRegistration materialId;
    @JoinColumn(name = "FOREIGN_APP_ID", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrmsForeignExchange foreignAppId;

    public PrmsForeignExDetail1() {
    }

    public PrmsForeignExDetail1(BigDecimal id) {
        this.id = id;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
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

    public MmsItemRegistration getMaterialId() {
        return materialId;
    }

    public void setMaterialId(MmsItemRegistration materialId) {
        this.materialId = materialId;
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
        if (!(object instanceof PrmsForeignExDetail1)) {
            return false;
        }
        PrmsForeignExDetail1 other = (PrmsForeignExDetail1) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        //return String.valueOf(id);
        return "et.gov.eep.prms.entity.PrmsForeignExDetail1[ id=" + id + " ]";
    }

}
