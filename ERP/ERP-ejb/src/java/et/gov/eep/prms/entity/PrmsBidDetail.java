/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.entity;

import et.gov.eep.mms.entity.MmsItemRegistration;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
@Table(name = "PRMS_BID_DETAIL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrmsBidDetail.findAll", query = "SELECT p FROM PrmsBidDetail p"),
    @NamedQuery(name = "PrmsBidDetail.findById", query = "SELECT p FROM PrmsBidDetail p WHERE p.id = :id"),
    @NamedQuery(name = "PrmsBidDetail.findByBidID", query = "SELECT p FROM PrmsBidDetail p WHERE p.bidId = :bidId"),
    @NamedQuery(name = "PrmsBidDetail.findByRefNo", query = "SELECT p FROM PrmsBidDetail p WHERE p.bidId.refNo = :refNo"),
    @NamedQuery(name = "PrmsBidDetail.findByIdNo", query = "SELECT p FROM PrmsBidDetail p WHERE p.id = :refNo"),
    @NamedQuery(name = "PrmsBidDetail.findByQuantity", query = "SELECT p FROM PrmsBidDetail p WHERE p.quantity = :quantity"),
    @NamedQuery(name = "PrmsBidDetail.findByBidNo", query = "SELECT p FROM PrmsBidDetail p WHERE p.bidId.refNo = :bidNo"),
    @NamedQuery(name = "PrmsBidDetail.findByItemRegID", query = "SELECT p FROM PrmsBidDetail p WHERE p.itemRegId = :itemRegId"),

    @NamedQuery(name = "PrmsBidDetail.findByCreditNo", query = "SELECT p FROM PrmsBidDetail p WHERE p.creditNo = :creditNo")})
public class PrmsBidDetail implements Serializable {

    @Column(name = "PASS_LIMIT")
    private Double passLimit;
    @Column(name = "FINANCIAL")
    private Double financial;
    @Column(name = "TECHNICAL")
    private Double technical;
    @JoinColumn(name = "PLAN_ID", referencedColumnName = "ID")
    @ManyToOne
    private PrmsPurchasePlan planId;

    @Size(max = 20)
    @Column(name = "SOURCE_OF_INITAITION")
    private String sourceOfInitaition;
    @Size(max = 20)
    @Column(name = "SERVICE_TYPE")
    private String serviceType;
    @Size(max = 20)
    @Column(name = "PR_NO")
    private String prNo;
    @Size(max = 20)
    @Column(name = "FERM_SELTION_METHD")
    private String fermSeltionMethd;
    @Column(name = "LOAN_NO")
    private BigInteger loanNo;
    @Size(max = 20)
    @Column(name = "PROJECT_ID")
    private String projectId;
    @Size(max = 20)
    @Column(name = "SOURCE_OF_FINANCE")
    private String sourceOfFinance;
    @Size(max = 50)
    @Column(name = "LOT_NAME")
    private String lotName;
    @JoinColumn(name = "PR_ID", referencedColumnName = "ID")
    @ManyToOne
    private PrmsPurchaseRequest prId;
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bidDetId", fetch = FetchType.LAZY)
//    private List<PrmsThechnicalEvaluation> prmsThechnicalEvaluationList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bidDetId", fetch = FetchType.LAZY)
    private List<PrmsSpecification> prmsSpecifications;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bidDetailId", fetch = FetchType.LAZY)
    private List<PrmsSupplierSpecificationDt> prmsSuppSpecificationList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "BidDetailId", fetch = FetchType.LAZY)
    private List<PrmsFinancialEvaluaDetail> prmsFinancialEvaluaDetailList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bidDetailId", fetch = FetchType.LAZY)
    private List<PrmsFinancialEvlResultyDtl> prmsFinancialEvlResultyDtlList = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bidDetailId", fetch = FetchType.LAZY)
    private List<PrmsThechincalEvaluationDet> prmsThechincalEvaluationDets;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bidDetId", fetch = FetchType.LAZY)
    private List<PrmsAwardDetail> prmsAwardDetailsList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bidDitId", fetch = FetchType.LAZY)
    private List<PrmsCheckListDetailforlot> prmsCheckListDetailforlots;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bidDetId", fetch = FetchType.LAZY)
    private List<PrmsBidderRegDetail> prmsBidderRegDetails;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "ID", nullable = false, length = 20)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EEP_BID_DET_SEQ")
    @SequenceGenerator(name = "EEP_BID_DET_SEQ", sequenceName = "EEP_BID_DET_SEQ", allocationSize = 1)
    private String id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "QUANTITY", precision = 126)
    private Integer quantity;
    @Column(name = "CREDIT_NO", precision = 126)
    private Double creditNo;
    @JoinColumn(name = "ITEM_REG_ID", referencedColumnName = "MATERIAL_ID")
    @ManyToOne
    private MmsItemRegistration itemRegId;
    @JoinColumn(name = "SERVICE_ID", referencedColumnName = "SERV_AND_WORK_ID")
    @ManyToOne
    private PrmsServiceAndWorkReg serviceId;
    @JoinColumn(name = "BID_ID", referencedColumnName = "ID")
    @ManyToOne
    private PrmsBid bidId;
//    @JoinColumn(name = "PR_ID", referencedColumnName = "ID")
//    @ManyToOne
//    private PrmsPurchaseRequest prId;

    /**
     *
     */
    public PrmsBidDetail() {
    }

    /**
     *
     * @param id
     */
    public PrmsBidDetail(String id) {
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
    public Integer getQuantity() {
        return quantity;
    }

    /**
     *
     * @param quantity
     */
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    /**
     *
     * @return
     */
    /**
     *
     * @return
     */
    public Double getCreditNo() {
        return creditNo;
    }

    /**
     *
     * @param creditNo
     */
    public void setCreditNo(Double creditNo) {
        this.creditNo = creditNo;
    }

    /**
     *
     * @return
     */
    /**
     *
     * @return
     */
    public MmsItemRegistration getItemRegId() {
        return itemRegId;
    }

    /**
     *
     * @param itemRegId
     */
    public void setItemRegId(MmsItemRegistration itemRegId) {
        this.itemRegId = itemRegId;
    }

    /**
     *
     * @return
     */
    public PrmsBid getBidId() {
        if (bidId == null) {
            bidId = new PrmsBid();
        }
        return bidId;
    }

    /**
     *
     * @param bidId
     */
    public void setBidId(PrmsBid bidId) {
        this.bidId = bidId;
    }

//    o
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PrmsBidDetail)) {
            return false;
        }
        PrmsBidDetail other = (PrmsBidDetail) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getId();
    }

    public PrmsPurchaseRequest getPrId() {
        return prId;
    }

    public void setPrId(PrmsPurchaseRequest prId) {
        this.prId = prId;
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

    public PrmsPurchasePlan getPlanId() {
        return planId;
    }

    public void setPlanId(PrmsPurchasePlan planId) {
        this.planId = planId;
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

    @XmlTransient
    public List<PrmsSpecification> getPrmsSpecifications() {
        if (prmsSpecifications == null) {
            prmsSpecifications = new ArrayList<>();
        }
        return prmsSpecifications;
    }

    @XmlTransient

    public List<PrmsSupplierSpecificationDt> getPrmsSuppSpecificationList() {
        if (prmsSuppSpecificationList == null) {
            prmsSuppSpecificationList = new ArrayList<>();
        }
        return prmsSuppSpecificationList;
    }

    public void setPrmsSuppSpecificationList(List<PrmsSupplierSpecificationDt> prmsSuppSpecificationList) {
        this.prmsSuppSpecificationList = prmsSuppSpecificationList;
    }

    @XmlTransient
    public List<PrmsThechincalEvaluationDet> getPrmsThechincalEvaluationDets() {
        if (prmsThechincalEvaluationDets == null) {
            prmsThechincalEvaluationDets = new ArrayList<>();
        }
        return prmsThechincalEvaluationDets;

    }

    public void setPrmsThechincalEvaluationDets(List<PrmsThechincalEvaluationDet> prmsThechincalEvaluationDets) {
        this.prmsThechincalEvaluationDets = prmsThechincalEvaluationDets;
    }

    public void setPrmsSpecifications(List<PrmsSpecification> prmsSpecifications) {
        this.prmsSpecifications = prmsSpecifications;
    }

    @XmlTransient
    public List<PrmsAwardDetail> getPrmsAwardDetailsList() {
        if (prmsAwardDetailsList == null) {
            prmsAwardDetailsList = new ArrayList<>();
        }
        return prmsAwardDetailsList;
    }

    public void setPrmsAwardDetailsList(List<PrmsAwardDetail> prmsAwardDetailsList) {
        this.prmsAwardDetailsList = prmsAwardDetailsList;
    }

    @XmlTransient
    public List<PrmsFinancialEvaluaDetail> getPrmsFinancialEvaluaDetailList() {
        if (prmsFinancialEvaluaDetailList == null) {
            prmsFinancialEvaluaDetailList = new ArrayList<>();
        }
        return prmsFinancialEvaluaDetailList;
    }

    public void setPrmsFinancialEvaluaDetailList(List<PrmsFinancialEvaluaDetail> prmsFinancialEvaluaDetailList) {
        this.prmsFinancialEvaluaDetailList = prmsFinancialEvaluaDetailList;
    }

    @XmlTransient
    public List<PrmsCheckListDetailforlot> getPrmsCheckListDetailforlots() {
        if (prmsCheckListDetailforlots == null) {
            prmsCheckListDetailforlots = new ArrayList<>();
        }
        return prmsCheckListDetailforlots;
    }

    public void setPrmsCheckListDetailforlots(List<PrmsCheckListDetailforlot> prmsCheckListDetailforlots) {
        this.prmsCheckListDetailforlots = prmsCheckListDetailforlots;
    }

    public Double getPassLimit() {
        return passLimit;
    }

    public void setPassLimit(Double passLimit) {
        this.passLimit = passLimit;
    }

    public PrmsServiceAndWorkReg getServiceId() {
        return serviceId;
    }

    public void setServiceId(PrmsServiceAndWorkReg serviceId) {
        this.serviceId = serviceId;
    }

    public String getSourceOfInitaition() {
        return sourceOfInitaition;
    }

    public void setSourceOfInitaition(String sourceOfInitaition) {
        this.sourceOfInitaition = sourceOfInitaition;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getSourceOfFinance() {
        return sourceOfFinance;
    }

    public void setSourceOfFinance(String sourceOfFinance) {
        this.sourceOfFinance = sourceOfFinance;
    }

    public String getLotName() {
        return lotName;
    }

    public void setLotName(String lotName) {
        this.lotName = lotName;
    }

    @XmlTransient
    public List<PrmsBidderRegDetail> getPrmsBidderRegDetails() {
        if (prmsBidderRegDetails == null) {
            prmsBidderRegDetails = new ArrayList<>();
        }
        return prmsBidderRegDetails;
    }

    public void setPrmsBidderRegDetails(List<PrmsBidderRegDetail> prmsBidderRegDetails) {
        this.prmsBidderRegDetails = prmsBidderRegDetails;
    }

    @XmlTransient

    public List<PrmsFinancialEvlResultyDtl> getPrmsFinancialEvlResultyDtlList() {
        return prmsFinancialEvlResultyDtlList;
    }

    public void setPrmsFinancialEvlResultyDtlList(List<PrmsFinancialEvlResultyDtl> prmsFinancialEvlResultyDtlList) {
        this.prmsFinancialEvlResultyDtlList = prmsFinancialEvlResultyDtlList;
    }

}
