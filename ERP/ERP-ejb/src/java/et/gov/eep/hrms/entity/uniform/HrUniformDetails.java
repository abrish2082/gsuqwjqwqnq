/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.uniform;

import et.gov.eep.mms.entity.MmsItemRegistration;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
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
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Behailu
 */
@Entity
@Table(name = "HR_UNIFORM_DETAILS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrUniformDetails.findAll", query = "SELECT h FROM HrUniformDetails h"),
    @NamedQuery(name = "HrUniformDetails.findById", query = "SELECT h FROM HrUniformDetails h WHERE h.id = :id"),
    @NamedQuery(name = "HrUniformDetails.findByQuantity", query = "SELECT h FROM HrUniformDetails h WHERE h.quantity = :quantity"),
    @NamedQuery(name = "HrUniformDetails.findByYear", query = "SELECT h FROM HrUniformDetails h WHERE h.year = :year"),
    @NamedQuery(name = "HrUniformDetails.findByStatus", query = "SELECT h FROM HrUniformDetails h WHERE h.status = :status")})
public class HrUniformDetails implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id   
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_UNIFORM_DETAILS_SEQ")
    @SequenceGenerator(name = "HR_UNIFORM_DETAILS_SEQ", sequenceName = "HR_UNIFORM_DETAILS_SEQ", allocationSize = 1)
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private BigDecimal id;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "QUANTITY")
    private BigInteger quantity;
    @Column(name = "YEAR")
    private BigInteger year;
    @Column(name = "STATUS")
    private BigInteger status;
    @JoinColumn(name = "UNIFORM_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrUniform uniformId;

    @JoinColumn(name = "UNIFORM_TYPE_ID", referencedColumnName = "MATERIAL_ID")
    @ManyToOne
    private MmsItemRegistration uniformTypeId;

    //<editor-fold defaultstate="collapsed" desc="getters & setters">
    public HrUniformDetails() {
    }

    public HrUniformDetails(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public BigInteger getQuantity() {
        return quantity;
    }

    public void setQuantity(BigInteger quantity) {
        this.quantity = quantity;
    }

    public BigInteger getYear() {
        return year;
    }

    public void setYear(BigInteger year) {
        this.year = year;
    }

    public BigInteger getStatus() {
        return status;
    }

    public void setStatus(BigInteger status) {
        this.status = status;
    }

    public HrUniform getUniformId() {
        return uniformId;
    }

    public void setUniformId(HrUniform uniformId) {
        this.uniformId = uniformId;
    }

    public MmsItemRegistration getUniformTypeId() {
        return uniformTypeId;
    }

    public void setUniformTypeId(MmsItemRegistration uniformTypeId) {
        this.uniformTypeId = uniformTypeId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
//</editor-fold>

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrUniformDetails)) {
            return false;
        }
        HrUniformDetails other = (HrUniformDetails) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.hrms.entity.uniform.HrUniformDetails[ id=" + id + " ]";
    }


}
