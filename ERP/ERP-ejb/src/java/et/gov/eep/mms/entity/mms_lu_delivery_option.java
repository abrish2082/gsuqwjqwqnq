/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Sadik
 */
@Entity
public class mms_lu_delivery_option implements Serializable {
//    @OneToMany(mappedBy = "deliveryOption")
//    private List<MmsGrn> mmsGrnList;

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MMS_LU_DELIVERY_OP_SEQ")
    @SequenceGenerator(name = "MMS_LU_DELIVERY_OP_SEQ", sequenceName = "MMS_LU_DELIVERY_OP_SEQ", allocationSize = 1)
    @Column(name = "ID", nullable = false, precision = 0, scale = -127)
    private Integer id;
    @Size(max = 50)
    @Column(name = "NAME", length = 50)
    private String name;
    @Size(max = 50)
    @Column(name = "DESCRIPTION", length = 50)
    private String description;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof mms_lu_delivery_option)) {
            return false;
        }
        mms_lu_delivery_option other = (mms_lu_delivery_option) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return name;
    }

    public mms_lu_delivery_option() {
    }
}
//    @XmlTransient
//    public List<MmsGrn> getMmsGrnList() {
//        return mmsGrnList;
//    }
//
//    public void setMmsGrnList(List<MmsGrn> mmsGrnList) {
//        this.mmsGrnList = mmsGrnList;
//    }
//    }
