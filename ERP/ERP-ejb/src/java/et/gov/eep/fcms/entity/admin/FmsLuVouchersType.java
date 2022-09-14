

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.entity.admin;

import java.io.Serializable;
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
 * @author userPCAdmin
 */
@Entity
@Table(name = "FMS_LU_VOUCHERS_TYPE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsLuVouchersType.findAll", query = "SELECT f FROM FmsLuVouchersType f"),
    @NamedQuery(name = "FmsLuVouchersType.findById", query = "SELECT f FROM FmsLuVouchersType f WHERE f.id = :id"),
    @NamedQuery(name = "FmsLuVouchersType.findByTypeName", query = "SELECT f FROM FmsLuVouchersType f WHERE f.typeName = :typeName")})
public class FmsLuVouchersType implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FMS_VOUCHERS_NO_RANGE_SEQ")
    @SequenceGenerator(name = "FMS_VOUCHERS_NO_RANGE_SEQ", sequenceName = "FMS_VOUCHERS_NO_RANGE_SEQ", allocationSize = 1)
    @NotNull
    @Column(nullable = false, precision = 0, scale = -127)
    private Integer id;
    @Size(max = 100)
    @Column(name = "TYPE_NAME", length = 100)
    private String typeName;
    @OneToMany(mappedBy = "typeId")
    private List<FmsVouchersNoRange> fmsVouchersNoRangeList;

    public FmsLuVouchersType() {
    }

    /**
     *
     * @param id
     */
    public FmsLuVouchersType(Integer id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public String getTypeName() {
        return typeName;
    }

    /**
     *
     * @param typeName
     */
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    /**
     *
     * @return
     */
    @XmlTransient
    public List<FmsVouchersNoRange> getFmsVouchersNoRangeList() {
        return fmsVouchersNoRangeList;
    }

    /**
     *
     * @param fmsVouchersNoRangeList
     */
    public void setFmsVouchersNoRangeList(List<FmsVouchersNoRange> fmsVouchersNoRangeList) {
        this.fmsVouchersNoRangeList = fmsVouchersNoRangeList;
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
        if (!(object instanceof FmsLuVouchersType)) {
            return false;
        }
        FmsLuVouchersType other = (FmsLuVouchersType) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return typeName;
    }
    
}
