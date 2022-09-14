/**
 * **********************************************************************************************************
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates and open the template
 * in the editor.
 * **********************************************************************************************************
 */
package et.gov.eep.prms.entity;

import et.gov.eep.mms.entity.MmsItemRegistration;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "PRMS_SUPPLIER_SPECIFICATION_DT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrmsSupplierSpecificationDt.findAll", query = "SELECT p FROM PrmsSupplierSpecificationDt p"),
    @NamedQuery(name = "PrmsSupplierSpecificationDt.findBySupplierDtId", query = "SELECT p FROM PrmsSupplierSpecificationDt p WHERE p.supplierDtId = :supplierDtId"),
    @NamedQuery(name = "PrmsSupplierSpecificationDt.findByMaterialId", query = "SELECT p FROM PrmsSupplierSpecificationDt p WHERE p.materialId.materialId = :id"),
    @NamedQuery(name = "PrmsSupplierSpecificationDt.findBySupplierSpcification", query = "SELECT p FROM PrmsSupplierSpecificationDt p WHERE p.supplierSpcification = :supplierSpcification"),
    @NamedQuery(name = "PrmsSupplierSpecificationDt.findByBidDetailId", query = "SELECT p FROM PrmsSupplierSpecificationDt p WHERE p.bidDetailId = :bidDetailId"),
    @NamedQuery(name = "PrmsSupplierSpecificationDt.findByFilename", query = "SELECT p FROM PrmsSupplierSpecificationDt p WHERE p.filename = :filename")})

public class PrmsSupplierSpecificationDt implements Serializable {
    
    @Size(max = 100)
    @Column(name = "UNIT_MEASURE")
    private String unitMeasure;
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRMS_SUPPLIER_SPEC_DTSEQ")
    @SequenceGenerator(name = "PRMS_SUPPLIER_SPEC_DTSEQ", sequenceName = "PRMS_SUPPLIER_SPEC_DTSEQ", allocationSize = 1)
    @Column(name = "SUPPLIER_DT_ID")
    private BigDecimal supplierDtId;
    @Size(max = 100)
    @Column(name = "SUPPLIER_SPCIFICATION")
    private String supplierSpcification;
    
    @Lob
    @Column(name = "SUPPLIER_SPCIFICATION_CONTENT")
    private byte[] supplierSpcificationContent;
    
    @Size(max = 100)
    @Column(name = "FILENAME")
    private String filename;
    
    @JoinColumn(name = "MATERIAL_ID", referencedColumnName = "MATERIAL_ID")
    @ManyToOne
    private MmsItemRegistration materialId;
    @JoinColumn(name = "SUPP_SPEC_ID", referencedColumnName = "SUPP_SPEC_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrmsSuppSpecification suppSpecId;
    @JoinColumn(name = "BID_DETAIL_ID", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrmsBidDetail bidDetailId;
    @OneToMany(mappedBy = "supplierDtId")
    private List<PrmsSupplierSpecDtUpload> prmsSupplierSpecDtUploadList;
    
    public PrmsSupplierSpecificationDt() {
    }

    /**
     *
     * @param supplierDtId
     */
    public PrmsSupplierSpecificationDt(BigDecimal supplierDtId) {
        this.supplierDtId = supplierDtId;
    }
    
    public byte[] getSupplierSpcificationContent() {
        return supplierSpcificationContent;
    }
    
    public void setSupplierSpcificationContent(byte[] supplierSpcificationContent) {
        this.supplierSpcificationContent = supplierSpcificationContent;
    }
    
    public String getFilename() {
        return filename;
    }
    
    public void setFilename(String filename) {
        this.filename = filename;
    }
    
    public BigDecimal getSupplierDtId() {
        return supplierDtId;
    }

    /**
     *
     * @param supplierDtId
     */
    public void setSupplierDtId(BigDecimal supplierDtId) {
        this.supplierDtId = supplierDtId;
    }
    
    public PrmsBidDetail getBidDetailId() {
        return bidDetailId;
    }
    
    public void setBidDetailId(PrmsBidDetail bidDetailId) {
        this.bidDetailId = bidDetailId;
    }
    
    public String getUnitMeasure() {
        return unitMeasure;
    }
    
    public void setUnitMeasure(String unitMeasure) {
        this.unitMeasure = unitMeasure;
    }

    /**
     *
     * @return
     */
    /**
     *
     * @return
     */
    public String getSupplierSpcification() {
        return supplierSpcification;
    }

    /**
     *
     * @param supplierSpcification
     */
    public void setSupplierSpcification(String supplierSpcification) {
        this.supplierSpcification = supplierSpcification;
    }

    /**
     *
     * @return
     */
    public PrmsSuppSpecification getSuppSpecId() {
        return suppSpecId;
    }

    /**
     *
     * @param suppSpecId
     */
    public void setSuppSpecId(PrmsSuppSpecification suppSpecId) {
        this.suppSpecId = suppSpecId;
    }
    
    public MmsItemRegistration getMaterialId() {
        return materialId;
    }
    
    public void setMaterialId(MmsItemRegistration materialId) {
        this.materialId = materialId;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (supplierDtId != null ? supplierDtId.hashCode() : 0);
        return hash;
    }
    
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PrmsSupplierSpecificationDt)) {
            return false;
        }
        PrmsSupplierSpecificationDt other = (PrmsSupplierSpecificationDt) object;
        if ((this.supplierDtId == null && other.supplierDtId != null) || (this.supplierDtId != null && !this.supplierDtId.equals(other.supplierDtId))) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return "et.gov.eep.prms.entity.PrmsSupplierSpecificationDt[ supplierDtId=" + supplierDtId + " ]";
    }
    
    public void setMaterialId(String matName) {
        throw new UnsupportedOperationException("Not supported yet.");
        //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * *************************************************************************
     *
     * @return
     * ***************************************************************************
     */
    @XmlTransient
    public List<PrmsSupplierSpecDtUpload> getPrmsSupplierSpecDtUploadList() {
        
        if (prmsSupplierSpecDtUploadList == null) {
            prmsSupplierSpecDtUploadList = new ArrayList<>();
        }
        
        return prmsSupplierSpecDtUploadList;
    }
    
    public void setPrmsSupplierSpecDtUploadList(
            List<PrmsSupplierSpecDtUpload> prmsSupplierSpecDtUploadList) {
        this.prmsSupplierSpecDtUploadList = prmsSupplierSpecDtUploadList;
    }    
    
    public void add(PrmsSupplierSpecDtUpload prmsSupplierSpecDtFileUpload) {
        
        if (prmsSupplierSpecDtFileUpload.getSupplierDtId() != this) {
            this.getPrmsSupplierSpecDtUploadList().add(prmsSupplierSpecDtFileUpload);
            prmsSupplierSpecDtFileUpload.getSupplierDtId();
            System.out.println("===add---" + 
                    prmsSupplierSpecDtFileUpload.getSupplierDtId());
        }
    }
}
