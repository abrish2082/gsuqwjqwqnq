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
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author user
 */
@Entity
@Table(name = "PRMS_CONTRACT_AMENDMENT_DT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrmsContractAmendmentDt.findAll", query = "SELECT p FROM PrmsContractAmendmentDt p"),
    @NamedQuery(name = "PrmsContractAmendmentDt.findByContractDetailId", query = "SELECT p FROM PrmsContractAmendmentDt p WHERE p.contractDetailId = :contractDetailId"),
//    @NamedQuery(name = "PrmsContractAmendmentDt.findByMaterialName", query = "SELECT p FROM PrmsContractAmendmentDt p WHERE p.materialName = :materialName"),
//    @NamedQuery(name = "PrmsContractAmendmentDt.findByMaterialCode", query = "SELECT p FROM PrmsContractAmendmentDt p WHERE p.materialCode = :materialCode"),
//    @NamedQuery(name = "PrmsContractAmendmentDt.findByUnitMeasure", query = "SELECT p FROM PrmsContractAmendmentDt p WHERE p.unitMeasure = :unitMeasure"),
    @NamedQuery(name = "PrmsContractAmendmentDt.findByMaterialId", query = "SELECT p FROM PrmsContractAmendmentDt p WHERE p.materialId=:materialId"),
    @NamedQuery(name = "PrmsContractAmendmentDt.findByQuantity", query = "SELECT p FROM PrmsContractAmendmentDt p WHERE p.quantity = :quantity"),
    @NamedQuery(name = "PrmsContractAmendmentDt.findByUnitPrice", query = "SELECT p FROM PrmsContractAmendmentDt p WHERE p.unitPrice = :unitPrice"),
    @NamedQuery(name = "PrmsContractAmendmentDt.findByTotalPrice", query = "SELECT p FROM PrmsContractAmendmentDt p WHERE p.totalPrice = :totalPrice")})
public class PrmsContractAmendmentDt implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRMS_CONTRACT_AMEND_DET_SEQ")
    @SequenceGenerator(name = "PRMS_CONTRACT_AMEND_DET_SEQ", sequenceName = "PRMS_CONTRACT_AMEND_DET_SEQ", allocationSize = 1)
    @Column(name = "CONTRACT_DETAIL_ID")
    private BigDecimal contractDetailId;
//    @Size(max = 50)
//    @Column(name = "MATERIAL_NAME")
//    private String materialName;
//    @Size(max = 50)
//    @Column(name = "MATERIAL_CODE")
//    private String materialCode;
//    @Size(max = 50)
//    @Column(name = "UNIT_MEASURE")
//    private String unitMeasure;
    @Column(name = "QUANTITY")
    private Long quantity;
    @Column(name = "UNIT_PRICE")
    private Long unitPrice;
    @Column(name = "TOTAL_PRICE")
    private double totalPrice;
    @JoinColumn(name = "CONTRACT_AMEND_ID", referencedColumnName = "CONTRACT_AMEND_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrmsContractAmendment contractAmendId;
    @JoinColumn(name = "MATERIAL_ID", referencedColumnName = "MATERIAL_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private MmsItemRegistration materialId;
    @Transient
    Double totals;

    public PrmsContractAmendmentDt() {
    }

    public PrmsContractAmendmentDt(BigDecimal contractDetailId) {
        this.contractDetailId = contractDetailId;
    }

    public BigDecimal getContractDetailId() {
        return contractDetailId;
    }

    public void setContractDetailId(BigDecimal contractDetailId) {
        this.contractDetailId = contractDetailId;
    }
//    public String getMaterialName() {
//        return materialName;
//    }
//    public void setMaterialName(String materialName) {
//        this.materialName = materialName;
//    }
//    public String getMaterialCode() {
//        return materialCode;
//    }
//    public void setMaterialCode(String materialCode) {
//        this.materialCode = materialCode;
//    }

//    public String getUnitMeasure() {
//        return unitMeasure;
//    }
//    public void setUnitMeasure(String unitMeasure) {
//        this.unitMeasure = unitMeasure;
//    }
    public Double getTotals() {
        return totals;
    }

    public void setTotals(Double totals) {
        this.totals = totals;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Long getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Long unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public PrmsContractAmendment getContractAmendId() {
        return contractAmendId;
    }

    public void setContractAmendId(PrmsContractAmendment contractAmendId) {
        this.contractAmendId = contractAmendId;
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
        hash += (contractDetailId != null ? contractDetailId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PrmsContractAmendmentDt)) {
            return false;
        }
        PrmsContractAmendmentDt other = (PrmsContractAmendmentDt) object;
        if ((this.contractDetailId == null && other.contractDetailId != null) || (this.contractDetailId != null && !this.contractDetailId.equals(other.contractDetailId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.prms.entity.PrmsContractAmendmentDt[ contractDetailId=" + contractDetailId + " ]";
    }

}
