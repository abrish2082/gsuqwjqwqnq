/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.entity;

import et.gov.eep.fcms.entity.Vocher.FmsCashReceiptVoucher;
import et.gov.eep.prms.entity.PrmsBid;
import et.gov.eep.prms.entity.PrmsSupplyProfile;
import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author user
 */
@Entity
@Table(name = "FMS_BID_SALE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsBidSale.findAll", query = "SELECT f FROM FmsBidSale f"),
    @NamedQuery(name = "FmsBidSale.findByBidSaleId", query = "SELECT f FROM FmsBidSale f WHERE f.bidSaleId = :bidSaleId")})
public class FmsBidSale implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FMS_BID_SALE_SEQUENCE")
    @SequenceGenerator(name = "FMS_BID_SALE_SEQUENCE", sequenceName = "FMS_BID_SALE_SEQUENCE", allocationSize = 1)
    @Column(name = "BID_SALE_ID")
    private BigDecimal bidSaleId;
    @JoinColumn(name = "CASH_RECEIPT", referencedColumnName = "CASH_RECEIPT_VOUCHER_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private FmsCashReceiptVoucher cashReceipt;
    @JoinColumn(name = "BID_ID", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrmsBid bidId;
    @JoinColumn(name = "BIDDER_ID", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrmsSupplyProfile bidderId;

    public FmsBidSale() {
    }

    public FmsBidSale(BigDecimal bidSaleId) {
        this.bidSaleId = bidSaleId;
    }

    public BigDecimal getBidSaleId() {
        return bidSaleId;
    }

    public void setBidSaleId(BigDecimal bidSaleId) {
        this.bidSaleId = bidSaleId;
    }

    public FmsCashReceiptVoucher getCashReceipt() {
        return cashReceipt;
    }

    public void setCashReceipt(FmsCashReceiptVoucher cashReceipt) {
        this.cashReceipt = cashReceipt;
    }

    public PrmsBid getBidId() {
        return bidId;
    }

    public void setBidId(PrmsBid bidId) {
        this.bidId = bidId;
    }

    public PrmsSupplyProfile getBidderId() {
        return bidderId;
    }

    public void setBidderId(PrmsSupplyProfile bidderId) {
        this.bidderId = bidderId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (bidSaleId != null ? bidSaleId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FmsBidSale)) {
            return false;
        }
        FmsBidSale other = (FmsBidSale) object;
        if ((this.bidSaleId == null && other.bidSaleId != null) || (this.bidSaleId != null && !this.bidSaleId.equals(other.bidSaleId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.fcms.entity.FmsBidSale[ bidSaleId=" + bidSaleId + " ]";
    }

    }
