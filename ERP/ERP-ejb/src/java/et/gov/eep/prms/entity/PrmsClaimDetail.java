/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.entity;

import et.gov.eep.mms.entity.MmsItemRegistration;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author user
 */
@Entity
@Table(name = "PRMS_CLAIM_DETAIL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrmsClaimDetail.findAll", query = "SELECT p FROM PrmsClaimDetail p"),
    @NamedQuery(name = "PrmsClaimDetail.findById", query = "SELECT p FROM PrmsClaimDetail p WHERE p.id = :id"),
    @NamedQuery(name = "PrmsClaimDetail.findByItemSpec", query = "SELECT p FROM PrmsClaimDetail p WHERE p.itemSpec = :itemSpec"),
//    @NamedQuery(name = "PrmsClaimDetail.findByItemName", query = "SELECT p FROM PrmsClaimDetail p WHERE p.itemName = :itemName"),
    @NamedQuery(name = "PrmsClaimDetail.findByRequestedPartNo", query = "SELECT p FROM PrmsClaimDetail p WHERE p.requestedPartNo = :requestedPartNo"),
    @NamedQuery(name = "PrmsClaimDetail.findByDeliveredPartNo", query = "SELECT p FROM PrmsClaimDetail p WHERE p.deliveredPartNo = :deliveredPartNo"),
    @NamedQuery(name = "PrmsClaimDetail.findByUnitOfMeasure", query = "SELECT p FROM PrmsClaimDetail p WHERE p.unitOfMeasure = :unitOfMeasure"),
    @NamedQuery(name = "PrmsClaimDetail.findByQuantity", query = "SELECT p FROM PrmsClaimDetail p WHERE p.quantity = :quantity"),
    @NamedQuery(name = "PrmsClaimDetail.findByUnitPrice", query = "SELECT p FROM PrmsClaimDetail p WHERE p.unitPrice = :unitPrice"),
    @NamedQuery(name = "PrmsClaimDetail.findByRemark", query = "SELECT p FROM PrmsClaimDetail p WHERE p.remark = :remark"),
    @NamedQuery(name = "PrmsClaimDetail.findByDetailId", query = "SELECT p FROM PrmsClaimDetail p WHERE p.detailId = :detailId")})
public class PrmsClaimDetail implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID", nullable = false, precision = 0, scale = -127)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRMS_CLAIM_DETAIL_SEQ")
    @SequenceGenerator(name = "PRMS_CLAIM_DETAIL_SEQ", sequenceName = "PRMS_CLAIM_DETAIL_SEQ", allocationSize = 1)
    private BigDecimal id;
    @Size(max = 20)
    @Column(name = "ITEM_SPEC", length = 20)
    private String itemSpec;
     @JoinColumn(name = "MATERIAL_ID", referencedColumnName = "MATERIAL_ID")
    @ManyToOne
    private MmsItemRegistration materialId;
    @Size(max = 20)
    @Column(name = "REQUESTED_PART_NO", length = 20)
    private String requestedPartNo;
    @Size(max = 20)
    @Column(name = "DELIVERED_PART_NO", length = 20)
    private String deliveredPartNo;
    @Size(max = 20)
    @Column(name = "UNIT_OF_MEASURE", length = 20)
    private String unitOfMeasure;
    @Size(max = 20)
    @Column(name = "QUANTITY", length = 20)
    private String quantity;
    @Size(max = 20)
    @Column(name = "UNIT_PRICE", length = 20)
    private String unitPrice;
    @Size(max = 100)
    @Column(name = "REMARK", length = 100)
    private String remark;
    @Size(max = 100)
    @Column(name = "DETAIL_ID", length = 100)
    private String detailId;
    @JoinColumn(name = "CLAIM_ID", referencedColumnName = "CLAIM_ID")
    @ManyToOne
    private PrmsClaimRecordingForm claimId;
//    @OneToMany(mappedBy = "depId")
//    private List<PrmsClaimRecordingForm> prmsClaimRecordingFormList;

    public PrmsClaimDetail() {
    }

    public PrmsClaimDetail(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getItemSpec() {
        return itemSpec;
    }

    public void setItemSpec(String itemSpec) {
        this.itemSpec = itemSpec;
    }

    public MmsItemRegistration getMaterialId() {
        return materialId;
    }

    public void setMaterialId(MmsItemRegistration materialId) {
        this.materialId = materialId;
    }

  

    public String getRequestedPartNo() {
        return requestedPartNo;
    }

    public void setRequestedPartNo(String requestedPartNo) {
        this.requestedPartNo = requestedPartNo;
    }

    public String getDeliveredPartNo() {
        return deliveredPartNo;
    }

    public void setDeliveredPartNo(String deliveredPartNo) {
        this.deliveredPartNo = deliveredPartNo;
    }

    public String getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public void setUnitOfMeasure(String unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDetailId() {
        return detailId;
    }

    public void setDetailId(String detailId) {
        this.detailId = detailId;
    }

    public PrmsClaimRecordingForm getClaimId() {
        return claimId;
    }

    public void setClaimId(PrmsClaimRecordingForm claimId) {
        this.claimId = claimId;
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
        if (!(object instanceof PrmsClaimDetail)) {
            return false;
        }
        PrmsClaimDetail other = (PrmsClaimDetail) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.prms.entity.PrmsClaimDetail[ id=" + id + " ]";
    }

    }
