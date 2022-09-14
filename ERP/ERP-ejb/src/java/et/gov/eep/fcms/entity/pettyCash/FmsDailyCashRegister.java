package et.gov.eep.fcms.entity.pettyCash;

import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import et.gov.eep.fcms.entity.Vocher.FmsCashPaymentOrder;

/**
 *
 * @author userPCAdmin
 */
@Entity
@Table(name = "FMS_DAILY_CASH_REGISTER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsDailyCashRegister.findAll", query = "SELECT f FROM FmsDailyCashRegister f"),
    @NamedQuery(name = "FmsDailyCashRegister.findById", query = "SELECT f FROM FmsDailyCashRegister f WHERE f.id = :id"),
    @NamedQuery(name = "FmsDailyCashRegister.findByRegistrationDate", query = "SELECT f FROM FmsDailyCashRegister f WHERE f.registrationDate LIKE :registrationDate"),
    @NamedQuery(name = "FmsDailyCashRegister.findByStatus", query = "SELECT f FROM FmsDailyCashRegister f WHERE f.status = :status"),
    @NamedQuery(name = "FmsDailyCashRegister.findByChaser", query = "SELECT f FROM FmsDailyCashRegister f WHERE f.chasherId = :chasherId"),
    @NamedQuery(name = "FmsDailyCashRegister.findByCashierIdAndStatus", query = "SELECT f FROM FmsDailyCashRegister f WHERE f.chasherId = :chasherId AND f.status=:status"),
    @NamedQuery(name = "FmsDailyCashRegister.findByCashierIdAndWfStatus", query = "SELECT f FROM FmsDailyCashRegister f WHERE f.chasherId = :chasherId AND f.status IN(0,1,2,3,4,10,11,18)"),
    @NamedQuery(name = "FmsDailyCashRegister.findByChaserLIKE", query = "SELECT f FROM FmsDailyCashRegister f WHERE f.chasherId LIKE :chasherId"),
    @NamedQuery(name = "FmsDailyCashRegister.findByAllParameter", query = "SELECT f FROM FmsDailyCashRegister f WHERE f.chasherId.empCode LIKE :chasherId"),
    @NamedQuery(name = "FmsDailyCashRegister.findByAmount", query = "SELECT f FROM FmsDailyCashRegister f WHERE f.amount = :amount")})

public class FmsDailyCashRegister implements Serializable {

    private static final long serialVersionUID = 1L;
    //<editor-fold defaultstate="collapsed" desc="variable declaration">
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Daily_Cash_Register_SEQ")
    @SequenceGenerator(name = "Daily_Cash_Register_SEQ", sequenceName = "Daily_Cash_Register_SEQ", allocationSize = 1)
    @NotNull
    @Column(name = "ID")
    private Long id;
    @Column(name = "REGISTRATION_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date registrationDate;
    @Column(name = "STATUS")
    private Integer status;
    @Column(name = "AMOUNT")
    private BigDecimal amount;
    @JoinColumn(name = "CHASHER_ID", referencedColumnName = "ID")
    @ManyToOne
    private FmsCasherAccount chasherId;
    @JoinColumn(name = "PETTY_CASH_ID", referencedColumnName = "CASH_PAYMENT_ORDER_ID")
    @OneToOne
    private FmsCashPaymentOrder pettyCashId;
    @OneToOne(mappedBy = "dailyCashRegisterId")
    private FmsPettyCashReplenishDtl fmsPettyCashReplenishDtl;
    @Transient
    private String voucherCode;
    @Transient
    private Date voucherDate;
    @Transient
    private String empName;
    @Transient
    private String Purpose;
    @Transient
    private int amountOnhand;
    @Transient
    private String vocherNo;
    @Transient
    private int paidAmount;
    @Transient
    private Double reAmount;
//</editor-fold>

    public FmsDailyCashRegister() {
    }

    //<editor-fold defaultstate="collapsed" desc="getter and setter">

    public FmsDailyCashRegister(Long id) {
        this.id = id;
    }

    public String getVoucherCode() {
        return voucherCode;
    }

    public void setVoucherCode(String voucherCode) {
        this.voucherCode = voucherCode;
    }

    public Date getVoucherDate() {
        return voucherDate;
    }

    public void setVoucherDate(Date voucherDate) {
        this.voucherDate = voucherDate;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public Double getReAmount() {
        return reAmount;
    }

    public void setReAmount(Double reAmount) {
        this.reAmount = reAmount;
    }

    public String getPurpose() {
        return Purpose;
    }

    public void setPurpose(String Purpose) {
        this.Purpose = Purpose;
    }

    public int getAmountOnhand() {
        return amountOnhand;
    }

    public void setAmountOnhand(int amountOnhand) {
        this.amountOnhand = amountOnhand;
    }

    public String getVocherNo() {
        return vocherNo;
    }

    public void setVocherNo(String vocherNo) {
        this.vocherNo = vocherNo;
    }

    public int getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(int paidAmount) {
        this.paidAmount = paidAmount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public FmsCasherAccount getChasherId() {
        if (chasherId == null) {
            chasherId = new FmsCasherAccount();
        }
        return chasherId;
    }

    public void setChasherId(FmsCasherAccount chasherId) {
        this.chasherId = chasherId;
    }

    public FmsCashPaymentOrder getPettyCashId() {
        return pettyCashId;
    }

    public void setPettyCashId(FmsCashPaymentOrder pettyCashId) {
        this.pettyCashId = pettyCashId;
    }

    public FmsPettyCashReplenishDtl getFmsPettyCashReplenishDtl() {
        return fmsPettyCashReplenishDtl;
    }

    public void setFmsPettyCashReplenishDtl(FmsPettyCashReplenishDtl fmsPettyCashReplenishDtl) {
        this.fmsPettyCashReplenishDtl = fmsPettyCashReplenishDtl;
    }
//</editor-fold>

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof FmsDailyCashRegister)) {
            return false;
        }
        FmsDailyCashRegister other = (FmsDailyCashRegister) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.FmsDailyCashRegister[ id=" + id + " ]";
    }
}
