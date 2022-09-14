/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.entity;

import et.gov.eep.mms.entity.MmsItemRegistration;
import java.io.Serializable;
import java.math.BigInteger;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author user
 */
@Entity
@Table(name = "PRMS_BID_DETAIL_AMEND")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrmsBidDetailAmend.findAll", query = "SELECT p FROM PrmsBidDetailAmend p"),
    @NamedQuery(name = "PrmsBidDetailAmend.findById", query = "SELECT p FROM PrmsBidDetailAmend p WHERE p.id = :id"),
    @NamedQuery(name = "PrmsBidDetailAmend.findBidAmendNo", query = "SELECT p FROM PrmsBidDetailAmend p WHERE p.id = (SELECT Max(p.id)  from PrmsBidDetailAmend p)"),
    @NamedQuery(name = "PrmsBidDetailAmend.findByBidId", query = "SELECT p FROM PrmsBidDetailAmend p WHERE p.bidId = :bidId"),
    @NamedQuery(name = "PrmsBidDetailAmend.findByQuantity", query = "SELECT p FROM PrmsBidDetailAmend p WHERE p.quantity = :quantity"),
    @NamedQuery(name = "PrmsBidDetailAmend.findByBidAmendId", query = "SELECT p FROM PrmsBidDetailAmend p WHERE p.bidAmendId.refNo = :amendId "),
    @NamedQuery(name = "PrmsBidDetailAmend.findByBidAmendBidNum", query = "SELECT p FROM PrmsBidDetailAmend p WHERE p.bidAmendId.id = :amendId  "),
    @NamedQuery(name = "PrmsBidDetailAmend.findByDescrption", query = "SELECT p FROM PrmsBidDetailAmend p WHERE p.descrption = :descrption"),
    @NamedQuery(name = "PrmsBidDetailAmend.findByCreditNo", query = "SELECT p FROM PrmsBidDetailAmend p WHERE p.creditNo = :creditNo"),
    @NamedQuery(name = "PrmsBidDetailAmend.findByFermSeltionMethd", query = "SELECT p FROM PrmsBidDetailAmend p WHERE p.fermSeltionMethd = :fermSeltionMethd"),
    @NamedQuery(name = "PrmsBidDetailAmend.findByLoanNo", query = "SELECT p FROM PrmsBidDetailAmend p WHERE p.loanNo = :loanNo"),
    @NamedQuery(name = "PrmsBidDetailAmend.findByProjectId", query = "SELECT p FROM PrmsBidDetailAmend p WHERE p.projectId = :projectId"),
    @NamedQuery(name = "PrmsBidDetailAmend.findByPrNo", query = "SELECT p FROM PrmsBidDetailAmend p WHERE p.prNo = :prNo"),
    @NamedQuery(name = "PrmsBidDetailAmend.findByPlanDetailId", query = "SELECT p FROM PrmsBidDetailAmend p WHERE p.planDetailId = :planDetailId")})
public class PrmsBidDetailAmend implements Serializable {

    @Size(max = 20)
    @Column(name = "SOURCE_OF_INITATION", length = 20)
    private String sourceOfInitation;
    @Size(max = 20)
    @Column(name = "SOURCE_OF_FINANCE", length = 20)
    private String sourceOfFinance;
    @Size(max = 20)
    @Column(name = "LOT_NAME", length = 20)
    private String lotName;

    @Column(name = "PASSLIMIT")
    private Double passlimit;
    @Column(name = "FINANCIAL")
    private Double financial;
    @Column(name = "TECHNICAL")
    private Double technical;
    @Size(max = 20)
    @Column(name = "SERVICE_TYPE", length = 20)
    private String serviceType;
    @Size(max = 20)
    @Column(name = "BID_AMEND_NO", length = 40)
    private String bidAmendNo;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRMS_BID_DTL_AMEND_SEQ")
    @SequenceGenerator(name = "PRMS_BID_DTL_AMEND_SEQ", sequenceName = "PRMS_BID_DTL_AMEND_SEQ", allocationSize = 1)
    private String id;
    @Size(max = 20)
    @Column(name = "BID_ID")
    private String bidId;

    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "QUANTITY")
    private Integer quantity;
    @Size(max = 20)
    @Column(name = "DESCRPTION")
    private String descrption;
    @Column(name = "CREDIT_NO")
    private Double creditNo;
    @Size(max = 20)
    @Column(name = "FERM_SELTION_METHD")
    private String fermSeltionMethd;

    @Column(name = "LOAN_NO")
    private BigInteger loanNo;
    @Size(max = 20)
    @Column(name = "PROJECT_ID")
    private String projectId;

    @Size(max = 20)
    @Column(name = "PR_NO")
    private String prNo;
    @Size(max = 20)
    @Column(name = "PLAN_DETAIL_ID")
    private String planDetailId;
    @JoinColumn(name = "BID_AMEND_ID", referencedColumnName = "ID")
    @ManyToOne
    private PrmsBidAmend bidAmendId;
    @JoinColumn(name = "ITEM_REG_ID", referencedColumnName = "MATERIAL_ID")
    @ManyToOne
    private MmsItemRegistration itemRegId;
    @JoinColumn(name = "PR_ID", referencedColumnName = "ID")
    @ManyToOne
    private PrmsPurchaseRequest prId;
    @JoinColumn(name = "PLAN_ID", referencedColumnName = "ID")
    @ManyToOne
    private PrmsPurchasePlan planId;
    @JoinColumn(name = "SERVICE_ID", referencedColumnName = "SERV_AND_WORK_ID")
    @ManyToOne
    private PrmsServiceAndWorkReg serviceId;

    public PrmsBidDetailAmend() {
    }

    public PrmsBidDetailAmend(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBidId() {
        return bidId;
    }

    public void setBidId(String bidId) {
        this.bidId = bidId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getDescrption() {
        return descrption;
    }

    public void setDescrption(String descrption) {
        this.descrption = descrption;
    }

    public Double getCreditNo() {
        return creditNo;
    }

    public void setCreditNo(Double creditNo) {
        this.creditNo = creditNo;
    }

    public String getFermSeltionMethd() {
        return fermSeltionMethd;
    }

    public void setFermSeltionMethd(String fermSeltionMethd) {
        this.fermSeltionMethd = fermSeltionMethd;
    }

    public BigInteger getLoanNo() {
        return loanNo;
    }

    public void setLoanNo(BigInteger loanNo) {
        this.loanNo = loanNo;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getPrNo() {
        return prNo;
    }

    public void setPrNo(String prNo) {
        this.prNo = prNo;
    }

    public String getPlanDetailId() {
        return planDetailId;
    }

    public void setPlanDetailId(String planDetailId) {
        this.planDetailId = planDetailId;
    }

    public PrmsBidAmend getBidAmendId() {
        return bidAmendId;
    }

    public void setBidAmendId(PrmsBidAmend bidAmendId) {
        this.bidAmendId = bidAmendId;
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
        if (!(object instanceof PrmsBidDetailAmend)) {
            return false;
        }
        PrmsBidDetailAmend other = (PrmsBidDetailAmend) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return id;
    }

    public String getBidAmendNo() {
        return bidAmendNo;
    }

    public void setBidAmendNo(String bidAmendNo) {
        this.bidAmendNo = bidAmendNo;
    }

    public MmsItemRegistration getItemRegId() {
        return itemRegId;
    }

    public void setItemRegId(MmsItemRegistration itemRegId) {
        this.itemRegId = itemRegId;
    }

    public String getSourceOfInitation() {
        return sourceOfInitation;
    }

    public void setSourceOfInitation(String sourceOfInitation) {
        this.sourceOfInitation = sourceOfInitation;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public Double getPasslimit() {
        return passlimit;
    }

    public void setPasslimit(Double passlimit) {
        this.passlimit = passlimit;
    }

    public Double getFinancial() {
        return financial;
    }

    public void setFinancial(Double financial) {
        this.financial = financial;
    }

    public Double getTechnical() {
        return technical;
    }

    public void setTechnical(Double technical) {
        this.technical = technical;
    }

    public String getLotName() {
        return lotName;
    }

    public void setLotName(String lotName) {
        this.lotName = lotName;
    }

    public PrmsPurchaseRequest getPrId() {
        return prId;
    }

    public void setPrId(PrmsPurchaseRequest prId) {
        this.prId = prId;
    }

    public PrmsServiceAndWorkReg getServiceId() {
        return serviceId;
    }

    public void setServiceId(PrmsServiceAndWorkReg serviceId) {
        this.serviceId = serviceId;
    }

    public String getSourceOfFinance() {
        return sourceOfFinance;
    }

    public void setSourceOfFinance(String sourceOfFinance) {
        this.sourceOfFinance = sourceOfFinance;
    }

    public PrmsPurchasePlan getPlanId() {
        return planId;
    }

    public void setPlanId(PrmsPurchasePlan planId) {
        this.planId = planId;
    }

}
