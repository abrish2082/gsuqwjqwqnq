/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.entity;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author user
 */
@Entity
@Table(name = "FMS_TAX_REGISTRATION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsTaxRegistration.findAll", query = "SELECT f FROM FmsTaxRegistration f"),
    @NamedQuery(name = "FmsTaxRegistration.findByTaxId", query = "SELECT f FROM FmsTaxRegistration f WHERE f.taxId = :taxId"),
    @NamedQuery(name = "FmsTaxRegistration.findByTaxType", query = "SELECT f FROM FmsTaxRegistration f WHERE f.taxType = :taxType"),
    @NamedQuery(name = "FmsTaxRegistration.findByMonth", query = "SELECT f FROM FmsTaxRegistration f WHERE f.month = :month and f.yearId.luBudgetYearId=:luBudgetYearId "),
    @NamedQuery(name = "FmsTaxRegistration.findByCaherId", query = "SELECT f FROM FmsTaxRegistration f WHERE f.casherId = :casherId"),
    @NamedQuery(name = "FmsTaxRegistration.findByTaxCenter", query = "SELECT f FROM FmsTaxRegistration f WHERE f.taxCenter = :taxCenter"),
    @NamedQuery(name = "FmsTaxRegistration.findByCpoDate", query = "SELECT f FROM FmsTaxRegistration f WHERE f.cpoDate = :cpoDate"),
    @NamedQuery(name = "FmsTaxRegistration.findByCpoNo", query = "SELECT f FROM FmsTaxRegistration f WHERE f.cpoNo = :cpoNo"),
    @NamedQuery(name = "FmsTaxRegistration.findByAmount", query = "SELECT f FROM FmsTaxRegistration f WHERE f.amount = :amount"),
    @NamedQuery(name = "FmsTaxRegistration.findByReceiptNo", query = "SELECT f FROM FmsTaxRegistration f WHERE f.receiptNo = :receiptNo"),
    @NamedQuery(name = "FmsTaxRegistration.findByYearId", query = "SELECT distinct(f.month) FROM FmsTaxRegistration f WHERE f.yearId = :yearId"),
    @NamedQuery(name = "FmsTaxRegistration.findByPaymentDate", query = "SELECT f FROM FmsTaxRegistration f WHERE f.paymentDate = :paymentDate")})
public class FmsTaxRegistration implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "TAX_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TAX_REGISTRATION_Seq")
    @SequenceGenerator(name = "TAX_REGISTRATION_Seq", sequenceName = "TAX_REGISTRATION_Seq", allocationSize = 1)
    private Integer taxId;
    @Column(name = "TAX_TYPE")
    private String taxType;
    @Column(name = "MONTH")
    private String month;
    @Column(name = "TAX_CENTER")
    private String taxCenter;
    @Column(name = "CPO_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date cpoDate;
    @Column(name = "CPO_NO")
    private String cpoNo;
    @Column(name = "AMOUNT")
    private BigInteger amount;
    @Column(name = "RECEIPT_NO")
    private String receiptNo;
    @Column(name = "PAYMENT_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date paymentDate;
    @JoinColumn(name = "YEAR_ID", referencedColumnName = "LU_BUDGET_YEAR_ID")
    @ManyToOne
    private FmsLuBudgetYear yearId;
    @JoinColumn(name = "CASHER_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrEmployees casherId;

    /**
     *
     */
    public FmsTaxRegistration() {
    }

    /**
     *
     * @param taxId
     */
    public FmsTaxRegistration(Integer taxId) {
        this.taxId = taxId;
    }

    /**
     *
     * @return
     */
    public Integer getTaxId() {
        return taxId;
    }

    /**
     *
     * @param taxId
     */
    public void setTaxId(Integer taxId) {
        this.taxId = taxId;
    }

    /**
     *
     * @return
     */
    public String getTaxType() {
        return taxType;
    }

    /**
     *
     * @param taxType
     */
    public void setTaxType(String taxType) {
        this.taxType = taxType;
    }

    /**
     *
     * @return
     */
    public String getMonth() {
        return month;
    }

    /**
     *
     * @param month
     */
    public void setMonth(String month) {
        this.month = month;
    }

    /**
     *
     * @return
     */
    public String getTaxCenter() {
        return taxCenter;
    }

    /**
     *
     * @param taxCenter
     */
    public void setTaxCenter(String taxCenter) {
        this.taxCenter = taxCenter;
    }

    /**
     *
     * @return
     */
    public Date getCpoDate() {
        return cpoDate;
    }

    /**
     *
     * @param cpoDate
     */
    public void setCpoDate(Date cpoDate) {
        this.cpoDate = cpoDate;
    }

    /**
     *
     * @return
     */
    public String getCpoNo() {
        return cpoNo;
    }

    /**
     *
     * @param cpoNo
     */
    public void setCpoNo(String cpoNo) {
        this.cpoNo = cpoNo;
    }

    /**
     *
     * @return
     */
    public BigInteger getAmount() {
        return amount;
    }

    /**
     *
     * @param amount
     */
    public void setAmount(BigInteger amount) {
        this.amount = amount;
    }

    /**
     *
     * @return
     */
    public String getReceiptNo() {
        return receiptNo;
    }

    /**
     *
     * @param receiptNo
     */
    public void setReceiptNo(String receiptNo) {
        this.receiptNo = receiptNo;
    }

    /**
     *
     * @return
     */
    public Date getPaymentDate() {
        return paymentDate;
    }

    /**
     *
     * @param paymentDate
     */
    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public FmsLuBudgetYear getYearId() {
        return yearId;
    }

    public void setYearId(FmsLuBudgetYear yearId) {
        this.yearId = yearId;
    }

    /**
     *
     * @return
     */
    public HrEmployees getCasherId() {
        return casherId;
    }

    /**
     *
     * @param casherId
     */
    public void setCasherId(HrEmployees casherId) {
        this.casherId = casherId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (taxId != null ? taxId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FmsTaxRegistration)) {
            return false;
        }
        FmsTaxRegistration other = (FmsTaxRegistration) object;
        if ((this.taxId == null && other.taxId != null) || (this.taxId != null && !this.taxId.equals(other.taxId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getMonth();
    }
    
}
