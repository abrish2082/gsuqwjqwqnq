/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.entity;

//import et.gov.eep.fcms.entity.budget.FmsOperatingBudget;
import et.gov.eep.fcms.entity.budget.FmsOperatingBudget1;
import et.gov.eep.prms.entity.PrmsServiceAndWorkReg;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author minab
 */
@Entity
@Table(name = "MMS_NEED_ASSESSMENT_SERVICE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MmsNeedAssessmentService.findAll", query = "SELECT m FROM MmsNeedAssessmentService m"),
    @NamedQuery(name = "MmsNeedAssessmentService.findByYear", query = "SELECT m FROM MmsNeedAssessmentService m WHERE m.assessmentId.budgetYear =:budgetYear AND m.assessmentId.purchaseType =:purchaseType"),
    @NamedQuery(name = "MmsNeedAssessmentService.findByAssessmentDtlId", query = "SELECT m FROM MmsNeedAssessmentService m WHERE m.assessmentDtlId = :assessmentDtlId"),
    @NamedQuery(name = "MmsNeedAssessmentService.findByQuantity", query = "SELECT m FROM MmsNeedAssessmentService m WHERE m.quantity = :quantity")})
public class MmsNeedAssessmentService implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MMS_NEED_ASS_SERVICE_SEQ")
    @SequenceGenerator(name = "MMS_NEED_ASS_SERVICE_SEQ", sequenceName = "MMS_NEED_ASS_SERVICE_SEQ", allocationSize = 1)
    @Column(name = "ASSESSMENT_DTL_ID")
    private Integer assessmentDtlId;
    @Column(name = "QUANTITY")
    private Integer quantity;
    @JoinColumn(name = "ASSESSMENT_ID", referencedColumnName = "ASSESSMETN_ID")
    @ManyToOne
    private MmsNeedAssessment assessmentId;
    @JoinColumn(name = "SERVICE_ID", referencedColumnName = "SERV_AND_WORK_ID")
    @ManyToOne
    private PrmsServiceAndWorkReg serviceId;
//    @JoinColumn(name = "BUDGET_CODE", referencedColumnName = "OPERATING_BUDGET_ID")
//    @ManyToOne(fetch = FetchType.LAZY)
//    private FmsOperatingBudget1 budgetCode;
    @Column(name = "UNIT_PRICE")
    private BigDecimal unitPrice;
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

    public MmsNeedAssessmentService() {
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
    

    public MmsNeedAssessmentService(Integer assessmentDtlId) {
        this.assessmentDtlId = assessmentDtlId;
    }

    public Integer getAssessmentDtlId() {
        return assessmentDtlId;
    }

    public void setAssessmentDtlId(Integer assessmentDtlId) {
        this.assessmentDtlId = assessmentDtlId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (assessmentDtlId != null ? assessmentDtlId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MmsNeedAssessmentService)) {
            return false;
        }
        MmsNeedAssessmentService other = (MmsNeedAssessmentService) object;
        if ((this.assessmentDtlId == null && other.assessmentDtlId != null) || (this.assessmentDtlId != null && !this.assessmentDtlId.equals(other.assessmentDtlId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.mms.entity.MmsNeedAssessmentService[ assessmentDtlId=" + assessmentDtlId + " ]";
    }

    public MmsNeedAssessment getAssessmentId() {
        return assessmentId;
    }

    public void setAssessmentId(MmsNeedAssessment assessmentId) {
        this.assessmentId = assessmentId;
    }

    public PrmsServiceAndWorkReg getServiceId() {
        return serviceId;
    }

    public void setServiceId(PrmsServiceAndWorkReg serviceId) {
        this.serviceId = serviceId;
    }

//    public FmsOperatingBudget1 getBudgetCode() {
//        return budgetCode;
//    }
//
//    public void setBudgetCode(FmsOperatingBudget1 budgetCode) {
//        this.budgetCode = budgetCode;
//    }



    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

}
