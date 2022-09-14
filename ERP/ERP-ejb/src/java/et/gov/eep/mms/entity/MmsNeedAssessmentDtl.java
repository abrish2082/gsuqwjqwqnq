/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.entity;

//import et.gov.eep.fcms.entity.budget.FmsOperatingBudget;
import et.gov.eep.fcms.entity.budget.FmsOperatingBudget1;
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
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Minab
 */
@Entity
@Table(name = "MMS_NEED_ASSESSMENT_DTL", catalog = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MmsNeedAssessmentDtl.findAll", query = "SELECT m FROM MmsNeedAssessmentDtl m"),
    @NamedQuery(name = "MmsNeedAssessmentDtl.findByAssessmetDtlId", query = "SELECT m FROM MmsNeedAssessmentDtl m WHERE m.assessmetDtlId = :assessmetDtlId"),
    @NamedQuery(name = "MmsNeedAssessmentDtl.findByQuantity", query = "SELECT m FROM MmsNeedAssessmentDtl m WHERE m.quantity = :quantity"),
    @NamedQuery(name = "MmsNeedAssessmentDtl.findByUnitPrice", query = "SELECT m FROM MmsNeedAssessmentDtl m WHERE m.unitPrice = :unitPrice"),
    @NamedQuery(name = "MmsNeedAssessmentDtl.findByYear", query = "SELECT m FROM MmsNeedAssessmentDtl m WHERE m.assessmetId.budgetYear = :bugdetYear AND m.assessmetId.purchaseType=:purchasetype"),
    @NamedQuery(name = "MmsNeedAssessmentDtl.findByRemark", query = "SELECT m FROM MmsNeedAssessmentDtl m WHERE m.remark = :remark"),
    @NamedQuery(name = "MmsNeedAssessmentDtl.findByStatus", query = "SELECT m FROM MmsNeedAssessmentDtl m WHERE m.status = :status")})

public class MmsNeedAssessmentDtl implements Serializable {

    @Column(name = "QUANTITY")
    private Integer quantity;
    @Column(name = "UNIT_PRICE")
    private BigDecimal unitPrice;
    @JoinColumn(name = "ITEM_ID", referencedColumnName = "MATERIAL_ID")
    @ManyToOne
    private MmsItemRegistration itemId;
//    @JoinColumn(name = "BUDGET_CODE", referencedColumnName = "OPERATING_BUDGET_ID")
//    @ManyToOne(fetch = FetchType.LAZY)
    //private FmsOperatingBudget1 budgetCode;

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MMS_NEED_ASS_DTL")
    @SequenceGenerator(name = "MMS_NEED_ASS_DTL", sequenceName = "MMS_NEED_ASS_DTL", allocationSize = 1)
    @Column(name = "ASSESSMET_DTL_ID", nullable = false, precision = 0, scale = -127)
    private BigDecimal assessmetDtlId;

    @Size(max = 100)
    @Column(name = "REMARK", length = 100)
    private String remark;

    @Column(name = "STATUS")
    private Integer status;

    @JoinColumn(name = "ASSESSMET_ID", referencedColumnName = "ASSESSMETN_ID")
    @ManyToOne
    private MmsNeedAssessment assessmetId;
    @Transient
    private int quantitycompiled;
    @Transient
    private String listOfDepartment;
    @Transient
    private double totalCompiledPrice;
    @Transient
    private String depNames;
    @Transient
    private Integer requestQuantity;

    public String getDepNames() {
        return depNames;
    }

    public void setDepNames(String depNames) {
        this.depNames = depNames;
    }

    public Integer getRequestQuantity() {
        return requestQuantity;
    }

    public void setRequestQuantity(Integer requestQuantity) {
        this.requestQuantity = requestQuantity;
    }

    public MmsNeedAssessmentDtl() {
    }

    public MmsNeedAssessmentDtl(BigDecimal assessmetDtlId) {
        this.assessmetDtlId = assessmetDtlId;
    }

    public BigDecimal getAssessmetDtlId() {
        return assessmetDtlId;
    }

    public void setAssessmetDtlId(BigDecimal assessmetDtlId) {
        this.assessmetDtlId = assessmetDtlId;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public MmsNeedAssessment getAssessmetId() {
        return assessmetId;
    }

    public void setAssessmetId(MmsNeedAssessment assessmetId) {
        this.assessmetId = assessmetId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (assessmetDtlId != null ? assessmetDtlId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MmsNeedAssessmentDtl)) {
            return false;
        }
        MmsNeedAssessmentDtl other = (MmsNeedAssessmentDtl) object;
        if ((this.assessmetDtlId == null && other.assessmetDtlId != null) || (this.assessmetDtlId != null && !this.assessmetDtlId.equals(other.assessmetDtlId))) {
            return false;
        }
        return true;
    }

    public int getQuantitycompiled() { 
        return quantitycompiled;
    }

    public void setQuantitycompiled(int quantitycompiled) {
        this.quantitycompiled = quantitycompiled;
    }

    public String getListOfDepartment() {
        return listOfDepartment;
    }

    public void setListOfDepartment(String listOfDepartment) {
        this.listOfDepartment = listOfDepartment;
    }

    public double getTotalCompiledPrice() {
        return totalCompiledPrice;
    }

    public void setTotalCompiledPrice(double totalCompiledPrice) {
        this.totalCompiledPrice = totalCompiledPrice;
    }

//    public FmsOperatingBudget1 getBudgetCode() {
//        return budgetCode;
//    }
//
//    public void setBudgetCode(FmsOperatingBudget1 budgetCode) {
//        this.budgetCode = budgetCode;
//    }
    @Override
    public String toString() {
        return "et.gov.eep.mms.entity.MmsNeedAssessmentDtl[ assessmetDtlId=" + assessmetDtlId + " ]";
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

//    public BigInteger getUnitPrice() {
//        return unitPrice;
//    }
//
//    public void setUnitPrice(BigInteger unitPrice) {
//        this.unitPrice = unitPrice;
//    }
    public MmsItemRegistration getItemId() {
        return itemId;
    }

    public void setItemId(MmsItemRegistration itemId) {
        this.itemId = itemId;
    }

}
