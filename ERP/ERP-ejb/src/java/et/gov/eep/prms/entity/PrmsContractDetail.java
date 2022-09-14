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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author user
 */
@Entity
@Table(name = "PRMS_CONTRACT_DETAIL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrmsContractDetail.findAll", query = "SELECT p FROM PrmsContractDetail p"),
    @NamedQuery(name = "PrmsContractDetail.findByContractDetailId", query = "SELECT p FROM PrmsContractDetail p WHERE p.contractDetailId = :contractDetailId"),
    @NamedQuery(name = "PrmsContractDetail.findByContractNo", query = "SELECT p FROM PrmsContractDetail p WHERE p.contractId.contractNo = :contractNo"),
    @NamedQuery(name = "PrmsContractDetail.findByMaterialId", query = "SELECT p FROM PrmsContractDetail p WHERE p.itemId = :itemId"),
    @NamedQuery(name = "PrmsContractDetail.findByServiceWorkId", query = "SELECT p FROM PrmsContractDetail p WHERE p.serviceWorkId = :serviceWorkId"),
    @NamedQuery(name = "PrmsContractDetail.findByContractNo", query = "SELECT p FROM PrmsContractDetail p WHERE p.contractId.contractNo = :contractNo"),
    @NamedQuery(name = "PrmsContractDetail.findByQuantity", query = "SELECT p FROM PrmsContractDetail p WHERE p.quantity = :quantity"),
    @NamedQuery(name = "PrmsContractDetail.findByUnitPrice", query = "SELECT p FROM PrmsContractDetail p WHERE p.unitPrice = :unitPrice")
})
public class PrmsContractDetail implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRMS_CONTRACT_DTSEQ")
    @SequenceGenerator(name = "PRMS_CONTRACT_DTSEQ", sequenceName = "PRMS_CONTRACT_DTSEQ", allocationSize = 1)
    @Column(name = "CONTRACT_DETAIL_ID")
    private BigDecimal contractDetailId;
    @Column(name = "QUANTITY")
    private Long quantity;
    @Column(name = "UNIT_PRICE")
    private Long unitPrice;
    @Column(name = "TOTAL_PRICE")
    private double totalPrice;
    @JoinColumn(name = "ITEM_ID", referencedColumnName = "MATERIAL_ID")
    @ManyToOne
    private MmsItemRegistration itemId;
    @JoinColumn(name = "SERVICE_WORK_ID", referencedColumnName = "SERV_AND_WORK_ID")
    @ManyToOne
    private PrmsServiceAndWorkReg serviceWorkId;
    @JoinColumn(name = "CONTRACT_ID", referencedColumnName = "CONTRACT_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrmsContract contractId;
    @Transient
    Double totals;

    /**
     *
     */
    public PrmsContractDetail() {
    }

    /**
     *
     * @param contractDetailId
     */
    public PrmsContractDetail(BigDecimal contractDetailId) {
        this.contractDetailId = contractDetailId;
    }

    /**
     *
     * @return
     */
    public BigDecimal getContractDetailId() {
        return contractDetailId;
    }

    /**
     *
     * @param contractDetailId
     */
    public void setContractDetailId(BigDecimal contractDetailId) {
        this.contractDetailId = contractDetailId;
    }

    public Double getTotals() {
        return totals;
    }

    public void setTotals(Double totals) {
        this.totals = totals;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    /**
     *
     * @return
     */
    public Long getQuantity() {
        return quantity;
    }

    /**
     *
     * @param quantity
     */
    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    /**
     *
     * @return
     */
    public Long getUnitPrice() {
        return unitPrice;
    }

    /**
     *
     * @param unitPrice
     */
    public void setUnitPrice(Long unitPrice) {
        this.unitPrice = unitPrice;
    }

    public PrmsContract getContractId() {
        return contractId;
    }

    public void setContractId(PrmsContract contractId) {
        this.contractId = contractId;
    }

    public MmsItemRegistration getItemId() {
        return itemId;
    }

    public void setItemId(MmsItemRegistration itemId) {
        this.itemId = itemId;
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
        if (!(object instanceof PrmsContractDetail)) {
            return false;
        }
        PrmsContractDetail other = (PrmsContractDetail) object;
        if ((this.contractDetailId == null && other.contractDetailId != null) || (this.contractDetailId != null && !this.contractDetailId.equals(other.contractDetailId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return contractId.getContractNo();
    }

    public PrmsServiceAndWorkReg getServiceWorkId() {
        return serviceWorkId;
    }

    public void setServiceWorkId(PrmsServiceAndWorkReg serviceWorkId) {
        this.serviceWorkId = serviceWorkId;
    }

    public PrmsServiceAndWorkReg getServiceId() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
  }
