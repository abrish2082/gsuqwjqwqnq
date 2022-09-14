/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.entity;

import java.io.Serializable;
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
import et.gov.eep.prms.entity.PrmsServiceAndWorkReg;
import et.gov.eep.prms.entity.PrmsFinancialEvlResultyDtl;
import et.gov.eep.mms.entity.MmsItemRegistration;
import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author user
 */
@Entity
@Table(name = "PRMS_AWARD_DETAIL"  )
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrmsAwardDetail.findAll", query = "SELECT p FROM PrmsAwardDetail p"),
    @NamedQuery(name = "PrmsAwardDetail.findByDetailId", query = "SELECT p FROM PrmsAwardDetail p WHERE p.detailId = :detailId"),
  
    @NamedQuery(name = "PrmsAwardDetail.findByUnitMeasure", query = "SELECT p FROM PrmsAwardDetail p WHERE p.unitMeasure = :unitMeasure"),
     @NamedQuery(name = "PrmsAwardDetail.findByAwardId", query = "SELECT p FROM PrmsAwardDetail p WHERE p.awardId = :awardId"),
    @NamedQuery(name = "PrmsAwardDetail.findByQuantity", query = "SELECT p FROM PrmsAwardDetail p WHERE p.quantity = :quantity"),
     @NamedQuery(name = "PrmsAwardDetail.findByAwardNo", query = "SELECT p FROM PrmsAwardDetail p WHERE p.awardId.awardNo = :awardNo"),
    @NamedQuery(name = "PrmsAwardDetail.findByUnitPrice", query = "SELECT p FROM PrmsAwardDetail p WHERE p.unitPrice = :unitPrice"),
    @NamedQuery(name = "PrmsAwardDetail.findByTotalPrice", query = "SELECT p FROM PrmsAwardDetail p WHERE p.totalPrice = :totalPrice")})
public class PrmsAwardDetail implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
       @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRMS_AWARD_DTSEQ")
    @SequenceGenerator(name = "PRMS_AWARD_DTSEQ", sequenceName = "PRMS_AWARD_DTSEQ", allocationSize = 1)
    @Size(min = 1, max = 100)
    @Column(name = "DETAIL_ID")
    private String detailId;
   
    @Size(max = 45)
    @Column(name = "UNIT_MEASURE")
    private String unitMeasure;
    @Column(name = "QUANTITY")
    private Integer quantity;
    @Column(name = "UNIT_PRICE")
    private double unitPrice;
    @Column(name = "TOTAL_PRICE")
    private Long totalPrice;
    @JoinColumn(name = "AWARD_ID", referencedColumnName = "AWARD_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrmsAward awardId;
    @JoinColumn(name = "MATERIAL_ID", referencedColumnName = "MATERIAL_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private MmsItemRegistration materialId;
    @JoinColumn(name = "SERVICE_WORK_ID", referencedColumnName = "SERV_AND_WORK_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrmsServiceAndWorkReg serviceWorkId;
    @JoinColumn(name = "FINACIAL_RESUT_ID", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrmsFinancialEvlResultyDtl finacialResutId;
    
      @JoinColumn(name = "BID_DET_ID", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrmsBidDetail bidDetId;
    @Transient
    Double totals;
    

    
    public PrmsAwardDetail() {
    }

    public Double getTotals() {
        return totals;
    }

    public void setTotals(Double totals) {
        this.totals = totals;
    }

    
    public PrmsAwardDetail(String detailId) {
        this.detailId = detailId;
    }

 
    public String getDetailId() {
        return detailId;
    }

    
    public void setDetailId(String detailId) {
        this.detailId = detailId;
    }

 
    public String getUnitMeasure() {
        return unitMeasure;
    }

  
    public void setUnitMeasure(String unitMeasure) {
        this.unitMeasure = unitMeasure;
    }

    public Integer getQuantity() {
        return quantity;
    }

  
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

   
    public double getUnitPrice() {
        return unitPrice;
    }

  
    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    
    public Long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Long totalPrice) {
        this.totalPrice = totalPrice;
    }

  
    public PrmsAward getAwardId() {
        return awardId;
    }

   
    public void setAwardId(PrmsAward awardId) {
        this.awardId = awardId;
    }

    public MmsItemRegistration getMaterialId() {
        return materialId;
    }

    public void setMaterialId(MmsItemRegistration materialId) {
        this.materialId = materialId;
    }

    public PrmsServiceAndWorkReg getServiceWorkId() {
        return serviceWorkId;
    }

    public void setServiceWorkId(PrmsServiceAndWorkReg serviceWorkId) {
        this.serviceWorkId = serviceWorkId;
    }

    public PrmsFinancialEvlResultyDtl getFinacialResutId() {
        return finacialResutId;
    }

    public void setFinacialResutId(PrmsFinancialEvlResultyDtl finacialResutId) {
        this.finacialResutId = finacialResutId;
    }

    public PrmsBidDetail getBidDetId() {
        return bidDetId;
    }

    public void setBidDetId(PrmsBidDetail bidDetId) {
        this.bidDetId = bidDetId;
    }

    
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (detailId != null ? detailId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
    
        if (!(object instanceof PrmsAwardDetail)) {
            return false;
        }
        PrmsAwardDetail other = (PrmsAwardDetail) object;
        if ((this.detailId == null && other.detailId != null) || (this.detailId != null && !this.detailId.equals(other.detailId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.prms.entity.PrmsAwardDetail[ detailId=" + detailId + " ]";
    }
    
}
