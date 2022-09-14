/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.entity;


//import et.gov.eep.mms.entity.PapmsMaterialInformation;
import et.gov.eep.fcms.entity.admin.FmsGeneralLedger;
import et.gov.eep.fcms.entity.admin.FmsSubsidiaryLedger;
import java.io.Serializable;
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author user
 */
@Entity
@Table(name = "FMS_MATERIAL_MAPPING")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsMaterialMapping.findAll", query = "SELECT f FROM FmsMaterialMapping f"),
    @NamedQuery(name = "FmsMaterialMapping.findById", query = "SELECT f FROM FmsMaterialMapping f WHERE f.id = :id")})
public class FmsMaterialMapping implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FMS_SEQ_MATERIAL_MAPPING")
    @SequenceGenerator(name = "FMS_SEQ_MATERIAL_MAPPING", sequenceName = "FMS_SEQ_MATERIAL_MAPPING", allocationSize = 1)
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "ID")
    private String id;
    @JoinColumn(name = "SUB_ID", referencedColumnName = "SUBSIDIARY_ID")
    @ManyToOne
    private FmsSubsidiaryLedger subId;
    @JoinColumn(name = "MAT_ID", referencedColumnName = "MATERIAL_ID")
//    @ManyToOne
//    private PapmsMaterialInformation matId = new PapmsMaterialInformation();
    @Size(max = 20)
    @Column(name = "TRAN_TYPE")
    private String tranType;
    @JoinColumn(name = "GL_ID", referencedColumnName = "GENERAL_LEDGER_ID")
    @ManyToOne
    private FmsGeneralLedger glId;

    /**
     *
     */
    public FmsMaterialMapping() {
    }

    /**
     *
     * @param id
     */
    public FmsMaterialMapping(String id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public FmsSubsidiaryLedger getSubId() {
        return subId;
    }

    /**
     *
     * @param subId
     */
    public void setSubId(FmsSubsidiaryLedger subId) {
        this.subId = subId;
    }

//    public PapmsMaterialInformation getMatId() {
//        return matId;
//    }
//
//    public void setMatId(PapmsMaterialInformation matId) {
//        this.matId = matId;
//    }

    /**
     *
     * @return
     */
    
    public String getTranType() {
        return tranType;
    }

    /**
     *
     * @param tranType
     */
    public void setTranType(String tranType) {
        this.tranType = tranType;
    }

    /**
     *
     * @return
     */
    public FmsGeneralLedger getGlId() {
        return glId;
    }

    /**
     *
     * @param glId
     */
    public void setGlId(FmsGeneralLedger glId) {
        this.glId = glId;
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
        if (!(object instanceof FmsMaterialMapping)) {
            return false;
        }
        FmsMaterialMapping other = (FmsMaterialMapping) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.insa.erp.ibfms.entity.FmsMaterialMapping[ id=" + id + " ]";
    }

}
