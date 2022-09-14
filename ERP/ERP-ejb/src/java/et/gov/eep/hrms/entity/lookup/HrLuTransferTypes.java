/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.lookup;

import et.gov.eep.hrms.entity.transfer.HrTransferRequests;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author INSA
 */
@Entity
@Table(name = "HR_LU_TRANSFER_TYPES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrLuTransferTypes.findAll", query = "SELECT h FROM HrLuTransferTypes h"),
    @NamedQuery(name = "HrLuTransferTypes.findById", query = "SELECT h FROM HrLuTransferTypes h WHERE h.id = :id"),
    @NamedQuery(name = "HrLuTransferTypes.findByTransferType", query = "SELECT h FROM HrLuTransferTypes h WHERE h.transferType = :transferType"),
    @NamedQuery(name = "HrLuTransferTypes.findByDescription", query = "SELECT h FROM HrLuTransferTypes h WHERE h.description = :description")})
public class HrLuTransferTypes implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private BigDecimal id;
    @Size(max = 100)
    @Column(name = "TRANSFER_TYPE")
    private String transferType;
    @Size(max = 200)
    @Column(name = "DESCRIPTION")
    private String description;
    @OneToMany(mappedBy = "transferTypeId")
    private List<HrTransferRequests> hrTransferRequestsList;

    /**
     *
     */
    public HrLuTransferTypes() {
    }

    /**
     *
     * @param id
     */
    public HrLuTransferTypes(BigDecimal id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public BigDecimal getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(BigDecimal id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public String getTransferType() {
        return transferType;
    }

    /**
     *
     * @param transferType
     */
    public void setTransferType(String transferType) {
        this.transferType = transferType;
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

    /**
     *
     * @return
     */
    @XmlTransient
    public List<HrTransferRequests> getHrTransferRequestsList() {
        return hrTransferRequestsList;
    }

    /**
     *
     * @param hrTransferRequestsList
     */
    public void setHrTransferRequestsList(List<HrTransferRequests> hrTransferRequestsList) {
        this.hrTransferRequestsList = hrTransferRequestsList;
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
        if (!(object instanceof HrLuTransferTypes)) {
            return false;
        }
        HrLuTransferTypes other = (HrLuTransferTypes) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return transferType;
    }
    
}
