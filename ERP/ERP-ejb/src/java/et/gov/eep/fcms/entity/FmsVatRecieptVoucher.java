/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.entity;

import et.gov.eep.fcms.entity.Vocher.FmsVoucher;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "Fms_Vat_Reciept_Voucher")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsVatRecieptVoucher.findAll", query = "SELECT f FROM FmsVatRecieptVoucher f"),
    @NamedQuery(name = "FmsVatRecieptVoucher.findByPreparedBy", query = "SELECT f FROM FmsVatRecieptVoucher f WHERE f.preparedBy = :preparedBy"),
    @NamedQuery(name = "FmsVatRecieptVoucher.findByCheckeBy", query = "SELECT f FROM FmsVatRecieptVoucher f WHERE f.checkeBy = :checkeBy"),
    @NamedQuery(name = "FmsVatRecieptVoucher.findByApprovedBy", query = "SELECT f FROM FmsVatRecieptVoucher f WHERE f.approvedBy = :approvedBy"),
    @NamedQuery(name = "FmsVatRecieptVoucher.findByPreparedDate", query = "SELECT f FROM FmsVatRecieptVoucher f WHERE f.preparedDate = :preparedDate"),
    @NamedQuery(name = "FmsVatRecieptVoucher.findByCheckedDate", query = "SELECT f FROM FmsVatRecieptVoucher f WHERE f.checkedDate = :checkedDate"),
    @NamedQuery(name = "FmsVatRecieptVoucher.findByApprovedDate", query = "SELECT f FROM FmsVatRecieptVoucher f WHERE f.approvedDate = :approvedDate"),
    @NamedQuery(name = "FmsVatRecieptVoucher.findByReferenceNumber", query = "SELECT f FROM FmsVatRecieptVoucher f WHERE f.referenceNumber = :referenceNumber"),
    @NamedQuery(name = "FmsVatRecieptVoucher.findByTaxPayer", query = "SELECT f FROM FmsVatRecieptVoucher f WHERE f.taxPayer = :taxPayer"),})
public class FmsVatRecieptVoucher implements Serializable {

    private static final long serialVersionUID = 1L;

    @Basic(optional = false)
    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FMS_VAT_RECIEPT_VOUCHER_SEQ")
    @SequenceGenerator(name = "FMS_VAT_RECIEPT_VOUCHER_SEQ", sequenceName = "FMS_VAT_RECIEPT_VOUCHER_SEQ", allocationSize = 1)
    @Column(name = "VAT_ID", nullable = false)
    private Long vatId;

    @Size(max = 50)
    @Column(name = "SYSTEM_NO", length = 50)
    private String systemno;
    @Column(name = "AMOUNT_IN_FIGURE")
    private Long amountInFigure;
    @Size(max = 50)
    @Column(name = "PREPARED_BY", length = 50)
    private String preparedBy;
    @Size(max = 50)
    @Column(name = "CHECKE_BY", length = 50)
    private String checkeBy;
    @Size(max = 50)
    @Column(name = "APPROVED_BY", length = 50)
    private String approvedBy;
    @Column(name = "PREPARED_DATE")
    @Temporal(TemporalType.DATE)
    private Date preparedDate;
    @Column(name = "CHECKED_DATE")
    @Temporal(TemporalType.DATE)
    private Date checkedDate;
    @Column(name = "APPROVED_DATE")
    @Temporal(TemporalType.DATE)
    private Date approvedDate;
    @Size(max = 50)
    @Column(name = "REFERENCE_NUMBER", length = 50)
    private String referenceNumber;
    @Column(name = "STATUS_")
    private String status;
    @Column(name = "VAT_RECIEPT_VALUE")
    private double vatRecieptValue;
    @Size(max = 100)
    @Column(name = "TAXPAYER", length = 100)
    private String taxPayer;
    @JoinColumn(name = "VOUCHER_ID", referencedColumnName = "VOUCHER_ID")
    @OneToOne(optional = false, cascade = CascadeType.PERSIST)
    private FmsVoucher fmsVOUCHERID;

    public FmsVatRecieptVoucher() {
    }

    public FmsVatRecieptVoucher(Long vatId) {
        this.vatId = vatId;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FmsVatRecieptVoucher)) {
            return false;
        }
        FmsVatRecieptVoucher other = (FmsVatRecieptVoucher) object;
        if ((this.vatId == null && other.vatId != null) || (this.vatId != null && !this.vatId.equals(other.vatId))) {
            return false;
        }
        return true;
    }

//    public double getChequPaymentID() {
//        return chequPaymentID;
//    }
//    public FmsChequePaymentVoucher getChequePayemtID() {
//        return chequePayemtID;
//    }
//
//    /**
//     *
//     * @param chequePayemtID
//     */
//    public void setChequePayemtID(FmsChequePaymentVoucher chequePayemtID) {
//        this.chequePayemtID = chequePayemtID;
//    }
//    public void setChequPaymentID(double chequPaymentID) {
//        this.chequPaymentID = chequPaymentID;
//    }
    public void setSystemno(String systemno) {
        this.systemno = systemno;
    }

    public String getSystemno() {

        return systemno;
    }

    public Long getAmountInFigure() {
        return amountInFigure;
    }

    public void setAmountInFigure(Long amountInFigure) {
        this.amountInFigure = amountInFigure;
    }

    public String getPreparedBy() {
        return preparedBy;
    }

    public void setPreparedBy(String preparedBy) {
        this.preparedBy = preparedBy;
    }

    public String getCheckeBy() {
        return checkeBy;
    }

    public void setCheckeBy(String checkeBy) {
        this.checkeBy = checkeBy;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    public Date getPreparedDate() {
        return preparedDate;
    }

    public void setPreparedDate(Date preparedDate) {
        this.preparedDate = preparedDate;
    }

    public Date getCheckedDate() {
        return checkedDate;
    }

    public void setCheckedDate(Date checkedDate) {
        this.checkedDate = checkedDate;
    }

    public Date getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(Date approvedDate) {
        this.approvedDate = approvedDate;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getvatId() {
        return vatId;
    }

    public void setId(Long vatId) {
        this.vatId = vatId;
    }

    public double getVatRecieptValue() {
        return vatRecieptValue;
    }

    public void setVatRecieptValue(double vatRecieptValue) {
        this.vatRecieptValue = vatRecieptValue;
    }

    public String getTaxPayer() {
        return taxPayer;
    }

    public void setTaxPayer(String taxPayer) {
        this.taxPayer = taxPayer;
    }

    public Long getVatId() {
        return vatId;
    }

    public void setVatId(Long vatId) {
        this.vatId = vatId;
    }

    public FmsVoucher getFmsVOUCHERID() {
        return fmsVOUCHERID;
    }

    public void setFmsVOUCHERID(FmsVoucher fmsVOUCHERID) {
        this.fmsVOUCHERID = fmsVOUCHERID;
    }

    @Override
    public String toString() {
        return fmsVOUCHERID.getVoucherId();
    }

}
