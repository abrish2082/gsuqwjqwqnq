package et.gov.eep.hrms.entity.insurance;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import et.gov.eep.commonApplications.entity.WfHrProcessed;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
 * @author meles
 */
@Entity
@Table(name = "HR_INSURANCE_PAYMENT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrInsurancePayment.findAll", query = "SELECT h FROM HrInsurancePayment h"),
    @NamedQuery(name = "HrInsurancePayment.findbystatus2", query = "SELECT h FROM HrInsuranceDiagnosisResult h WHERE h.hrInsuranceInjuredEmployee.type = :fname AND h.status = :status3"),
    @NamedQuery(name = "HrInsurancePayment.findById", query = "SELECT h FROM HrInsurancePayment h WHERE h.id = :id"),
    @NamedQuery(name = "HrInsurancePayment.findByCheckNo", query = "SELECT h FROM HrInsurancePayment h WHERE h.checkNo = :checkNo"),
    @NamedQuery(name = "HrInsurancePayment.findByAccontTitle", query = "SELECT h FROM HrInsurancePayment h WHERE h.accontTitle = :accontTitle"),
    @NamedQuery(name = "HrInsurancePayment.findByRecivedDate", query = "SELECT h FROM HrInsurancePayment h WHERE h.recivedDate = :recivedDate"),
    @NamedQuery(name = "HrInsurancePayment.findByNameOfBank", query = "SELECT h FROM HrInsurancePayment h WHERE h.nameOfBank = :nameOfBank"),
    @NamedQuery(name = "HrInsurancePayment.findByBankBranch", query = "SELECT h FROM HrInsurancePayment h WHERE h.bankBranch = :bankBranch"),
    @NamedQuery(name = "HrInsurancePayment.findByAccontNo", query = "SELECT h FROM HrInsurancePayment h WHERE h.accontNo = :accontNo"),
    @NamedQuery(name = "HrInsurancePayment.findByStatus", query = "SELECT h FROM HrInsurancePayment h WHERE h.status = :status"),
    @NamedQuery(name = "HrInsurancePayment.findByPreparddate", query = "SELECT h FROM HrInsurancePayment h WHERE h.preparddate = :preparddate"),
    @NamedQuery(name = "HrInsurancePayment.findByRemark", query = "SELECT h FROM HrInsurancePayment h WHERE h.remark = :remark")})
public class HrInsurancePayment implements Serializable {

    @Column(name = "STATUS")
    private Integer status;
    @OneToMany(mappedBy = "insurancePaymentId")
    private List<WfHrProcessed> wfHrProcessedList;
    @OneToMany(mappedBy = "paymentRequestId", cascade = CascadeType.ALL)
    private List<HrInsurancePaymentDetail> hrInsurancePaymentDetailList;
    @OneToMany(mappedBy = "insurancePaymentId", cascade = CascadeType.ALL)
    private List<WfHrProcessed> WfHrProcessedList;
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_INSURANCE_PAYMENT_SEQ")
    @SequenceGenerator(name = "HR_INSURANCE_PAYMENT_SEQ", sequenceName = "HR_INSURANCE_PAYMENT_SEQ", allocationSize = 1)
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Integer id;
    @Column(name = "CHECK_NO")
    private BigInteger checkNo;
    @Size(max = 20)
    @Column(name = "ACCONT_TITLE")
    private String accontTitle;
    @Size(max = 20)
    @Column(name = "RECIVED_DATE")
    private String recivedDate;

    @Size(max = 20)
    @Column(name = "APPROVED_DATE")
    private String approveddate;

    @Size(max = 20)
    @Column(name = "NAME_OF_BANK")
    private String nameOfBank;
    @Size(max = 20)
    @Column(name = "BANK_BRANCH")
    private String bankBranch;
    @Column(name = "ACCONT_NO")
    private BigInteger accontNo;
    @Size(max = 20)
    @Column(name = "PREPARDDATE")
    private String preparddate;
    @Size(max = 20)
    @Column(name = "REMARK")
    private String remark;
    @JoinColumn(name = "PREPARDBY", referencedColumnName = "ID")
    @ManyToOne
    private HrEmployees prepardby;
    @JoinColumn(name = "APPROVED_BY", referencedColumnName = "ID")
    @ManyToOne
    private HrEmployees approvedBy;
    @JoinColumn(name = "DIAGONASIS_RESULT_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrInsuranceDiagnosisResult diagonasisResultId;

    public HrInsurancePayment() {
    }

    public HrInsurancePayment(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigInteger getCheckNo() {
        return checkNo;
    }

    public void setCheckNo(BigInteger checkNo) {
        this.checkNo = checkNo;
    }

    public String getAccontTitle() {
        return accontTitle;
    }

    public void setAccontTitle(String accontTitle) {
        this.accontTitle = accontTitle;
    }

    public String getRecivedDate() {
        return recivedDate;
    }

    public void setRecivedDate(String recivedDate) {
        this.recivedDate = recivedDate;
    }

    public String getNameOfBank() {
        return nameOfBank;
    }

    public void setNameOfBank(String nameOfBank) {
        this.nameOfBank = nameOfBank;
    }

    public String getBankBranch() {
        return bankBranch;
    }

    public void setBankBranch(String bankBranch) {
        this.bankBranch = bankBranch;
    }

    public BigInteger getAccontNo() {
        return accontNo;
    }

    public void setAccontNo(BigInteger accontNo) {
        this.accontNo = accontNo;
    }

    public String getPreparddate() {
        return preparddate;
    }

    public void setPreparddate(String preparddate) {
        this.preparddate = preparddate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public HrEmployees getPrepardby() {
        return prepardby;
    }

    public void setPrepardby(HrEmployees prepardby) {
        this.prepardby = prepardby;
    }

    public HrEmployees getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(HrEmployees approvedBy) {
        this.approvedBy = approvedBy;
    }

    public HrInsuranceDiagnosisResult getDiagonasisResultId() {
        return diagonasisResultId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getApproveddate() {
        return approveddate;
    }

    public void setApproveddate(String approveddate) {
        this.approveddate = approveddate;
    }

    public void setDiagonasisResultId(HrInsuranceDiagnosisResult diagonasisResultId) {
        this.diagonasisResultId = diagonasisResultId;
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
        if (!(object instanceof HrInsurancePayment)) {
            return false;
        }
        HrInsurancePayment other = (HrInsurancePayment) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.hrms.entity.address.HrInsurancePayment[ id=" + id + " ]";
    }

    @XmlTransient
    public List<HrInsurancePaymentDetail> getHrInsurancePaymentDetailList() {
        if (hrInsurancePaymentDetailList == null) {
            hrInsurancePaymentDetailList = new ArrayList<>();
        }
        return hrInsurancePaymentDetailList;
    }

    public void setHrInsurancePaymentDetailList(List<HrInsurancePaymentDetail> hrInsurancePaymentDetailList) {
        this.hrInsurancePaymentDetailList = hrInsurancePaymentDetailList;
    }

    public void addInvoiceDetail(HrInsurancePaymentDetail hrInsurancePaymentDetail) {
        if (hrInsurancePaymentDetail.getPaymentRequestId() != this) {
            this.getHrInsurancePaymentDetailList().add(hrInsurancePaymentDetail);
            hrInsurancePaymentDetail.setPaymentRequestId(this);
        }
    }

    @XmlTransient
    public List<WfHrProcessed> getWfHrProcessedList() {
        return WfHrProcessedList;
    }

    public void setWfHrProcessedList(List<WfHrProcessed> WfHrProcessedList) {
        this.WfHrProcessedList = WfHrProcessedList;
    }

}
