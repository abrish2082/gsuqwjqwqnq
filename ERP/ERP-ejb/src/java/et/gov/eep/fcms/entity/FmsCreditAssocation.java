/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.entity;


import et.gov.eep.hrms.entity.employee.HrEmployees;
import java.io.Serializable;
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
@Table(name = "FMS_CREDIT_ASSOCATION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsCreditAssocation.findAll", query = "SELECT f FROM FmsCreditAssocation f"),
    @NamedQuery(name = "FmsCreditAssocation.findById", query = "SELECT f FROM FmsCreditAssocation f WHERE f.id = :id"),
    @NamedQuery(name = "FmsCreditAssocation.findByStartPayDate", query = "SELECT f FROM FmsCreditAssocation f WHERE f.startPayDate = :startPayDate"),
    @NamedQuery(name = "FmsCreditAssocation.findByAdditionAmount", query = "SELECT f FROM FmsCreditAssocation f WHERE f.additionAmount = :additionAmount"),
    @NamedQuery(name = "FmsCreditAssocation.findByStatus", query = "SELECT f FROM FmsCreditAssocation f WHERE f.status = :status"),
    @NamedQuery(name = "FmsCreditAssocation.findByEmployee", query = "SELECT f FROM FmsCreditAssocation f WHERE LOWER(f.empId.empId) LIKE :empId"),
    @NamedQuery(name = "FmsCreditAssocation.findByCreditAssocationRate", query = "SELECT f FROM FmsCreditAssocation f WHERE f.creditAssocationRate = :creditAssocationRate"),
    @NamedQuery(name = "FmsCreditAssocation.findByCardPaidAmount", query = "SELECT f FROM FmsCreditAssocation f WHERE f.cardPaidAmount = :cardPaidAmount")})
public class FmsCreditAssocation implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FMS_CREDIT_ASS_SEQ")//
    @SequenceGenerator(name = "FMS_CREDIT_ASS_SEQ", sequenceName = "FMS_CREDIT_ASS_SEQ", allocationSize = 1)
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "ID")
    private String id;
    @Size(max = 20)
    @Column(name = "START_PAY_DATE")
    private String startPayDate;
    @Column(name = "ADDITION_AMOUNT")
    private Integer additionAmount;
    @Column(name = "STATUS")
    private Integer status;
    @Column(name = "CREDIT_ASSOCATION_RATE")
    private Integer creditAssocationRate;
    @Column(name = "CARD_PAID_AMOUNT")
    private Integer cardPaidAmount;
    @JoinColumn(name = "EMP_ID", referencedColumnName = "EMP_ID")
    @ManyToOne
    private HrEmployees empId;
    @JoinColumn(name = "ASSOCIATION_TYPE", referencedColumnName = "ID")
    @ManyToOne
    private FmsLuAssociationType associationType;
    @Column(name = "SHARED_ORDER")
    private Integer sharedOrder;
    @OneToMany(mappedBy = "creditAssocationId")
    private List<FmsCreditAssociationLoan> fmsCreditAssociationLoanList;

    /**
     *
     */
    public FmsCreditAssocation() {
    }

    /**
     *
     * @param id
     */
    public FmsCreditAssocation(String id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public String getStartPayDate() {
        return startPayDate;
    }

    /**
     *
     * @param startPayDate
     */
    public void setStartPayDate(String startPayDate) {
        this.startPayDate = startPayDate;
    }

    /**
     *
     * @return
     */
    public Integer getAdditionAmount() {
        return additionAmount;
    }

    /**
     *
     * @param additionAmount
     */
    public void setAdditionAmount(Integer additionAmount) {
        this.additionAmount = additionAmount;
    }

    /**
     *
     * @return
     */
    public Integer getStatus() {
        return status;
    }

    /**
     *
     * @param status
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     *
     * @return
     */
    public Integer getCreditAssocationRate() {
        return creditAssocationRate;
    }

    /**
     *
     * @param creditAssocationRate
     */
    public void setCreditAssocationRate(Integer creditAssocationRate) {
        this.creditAssocationRate = creditAssocationRate;
    }

    /**
     *
     * @return
     */
    public Integer getCardPaidAmount() {
        return cardPaidAmount;
    }

    /**
     *
     * @param cardPaidAmount
     */
    public void setCardPaidAmount(Integer cardPaidAmount) {
        this.cardPaidAmount = cardPaidAmount;
    }

    /**
     *
     * @return
     */
    public HrEmployees getEmpId() {
        return empId;
    }

    /**
     *
     * @param empId
     */
    public void setEmpId(HrEmployees empId) {
        this.empId = empId;
    }

    /**
     *
     * @return
     */
    public FmsLuAssociationType getAssociationType() {
        return associationType;
    }

    /**
     *
     * @param associationType
     */
    public void setAssociationType(FmsLuAssociationType associationType) {
        this.associationType = associationType;
    }

    /**
     *
     * @return
     */
    public Integer getSharedOrder() {
        return sharedOrder;
    }

    /**
     *
     * @param sharedOrder
     */
    public void setSharedOrder(Integer sharedOrder) {
        this.sharedOrder = sharedOrder;
    }

    /**
     *
     * @return
     */
    @XmlTransient
    public List<FmsCreditAssociationLoan> getFmsCreditAssociationLoanList() {
        return fmsCreditAssociationLoanList;
    }

    /**
     *
     * @param fmsCreditAssociationLoanList
     */
    public void setFmsCreditAssociationLoanList(List<FmsCreditAssociationLoan> fmsCreditAssociationLoanList) {
        this.fmsCreditAssociationLoanList = fmsCreditAssociationLoanList;
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
        if (!(object instanceof FmsCreditAssocation)) {
            return false;
        }
        FmsCreditAssocation other = (FmsCreditAssocation) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return empId.getEmpId();
    }

}
