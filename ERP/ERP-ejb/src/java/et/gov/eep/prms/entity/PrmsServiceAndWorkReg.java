/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.entity;

import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import et.gov.eep.fcms.entity.admin.FmsGeneralLedger;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

/**
 *
 * @author user
 */
@Entity
@Table(name = "PRMS_SERVICE_AND_WORK_REG")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrmsServiceAndWorkReg.findAll", query = "SELECT p FROM PrmsServiceAndWorkReg p"),
    @NamedQuery(name = "PrmsServiceAndWorkReg.findByServAndWorkId", query = "SELECT p FROM PrmsServiceAndWorkReg p WHERE p.servAndWorkId = :servAndWorkId"),
    @NamedQuery(name = "PrmsServiceAndWorkReg.findByUnitMeasures", query = "SELECT p FROM PrmsServiceAndWorkReg p WHERE p.unitMeasures = :unitMeasures"),
    @NamedQuery(name = "PrmsServiceAndWorkReg.findByServiceName", query = "SELECT p FROM PrmsServiceAndWorkReg p WHERE p.serviceName = :serviceName"),
    @NamedQuery(name = "PrmsServiceAndWorkReg.findByServiceNam", query = "SELECT p FROM PrmsServiceAndWorkReg p WHERE p.servAndWorkId = :serviceNam"),
    @NamedQuery(name = "PrmsServiceAndWorkReg.findByServiceNameLike", query = "SELECT p FROM PrmsServiceAndWorkReg p WHERE p.serviceName LIKE :serviceName and p.preparedBy=:preparedBy"),
    @NamedQuery(name = "PrmsServiceAndWorkReg.findByWorkNameLike", query = "SELECT p FROM PrmsServiceAndWorkReg p WHERE p.workName LIKE :workName and p.preparedBy=:preparedBy"),
    //@NamedQuery(name = "PrmsServiceAndWorkReg.findByServiceNameLike", query = "SELECT p FROM PrmsServiceAndWorkReg p WHERE p.serviceName LIKE :serviceName AND p.workName LIKE :workName"),
    @NamedQuery(name = "PrmsServiceAndWorkReg.generateSeqForServNo", query = "SELECT p FROM PrmsServiceAndWorkReg p WHERE p.servAndWorkId=(SELECT MAX(p.servAndWorkId)FROM PrmsServiceAndWorkReg p) AND p.registerationType='service'"),
    @NamedQuery(name = "PrmsServiceAndWorkReg.generateSeqForWorkNo", query = "SELECT p FROM PrmsServiceAndWorkReg p WHERE p.servAndWorkId=(SELECT MAX(p.servAndWorkId)FROM PrmsServiceAndWorkReg p) AND p.registerationType='work'"),
    @NamedQuery(name = "PrmsServiceAndWorkReg.findByRegistrationTypeAndGLID", query = "SELECT p FROM PrmsServiceAndWorkReg p WHERE p.generalLedgerId.generalLedgerId = :fmsGeneralLedger AND p.registerationType= :registerationType"),
    @NamedQuery(name = "PrmsServiceAndWorkReg.findByGlId", query = "SELECT p FROM PrmsServiceAndWorkReg p WHERE p.generalLedgerId = :fmsGeneralLedger"),
    @NamedQuery(name = "PrmsServiceAndWorkReg.findByPreparedBy", query = "SELECT p FROM PrmsServiceAndWorkReg p WHERE p.preparedBy = :preparedBy"),
    @NamedQuery(name = "PrmsServiceAndWorkReg.findByRegistrationDate", query = "SELECT p FROM PrmsServiceAndWorkReg p WHERE p.registrationDate = :registrationDate"),
    @NamedQuery(name = "PrmsServiceAndWorkReg.findByServiceNo", query = "SELECT p FROM PrmsServiceAndWorkReg p WHERE p.serviceNo = :serviceNo"),
    @NamedQuery(name = "PrmsServiceAndWorkReg.findByServiceSeqNo", query = "SELECT p FROM PrmsServiceAndWorkReg p WHERE p.serviceNo LIKE :serviceNo"),
    @NamedQuery(name = "PrmsServiceAndWorkReg.findByWorkSeqNo", query = "SELECT p FROM PrmsServiceAndWorkReg p WHERE p.workNo LIKE :workNo"),
    @NamedQuery(name = "PrmsServiceAndWorkReg.findBySpecificationRemark", query = "SELECT p FROM PrmsServiceAndWorkReg p WHERE p.specificationRemark = :specificationRemark"),
    @NamedQuery(name = "PrmsServiceAndWorkReg.findByRegisterationType", query = "SELECT p FROM PrmsServiceAndWorkReg p WHERE p.registerationType = :registerationType"),
    @NamedQuery(name = "PrmsServiceAndWorkReg.findByServiceType", query = "SELECT p FROM PrmsServiceAndWorkReg p WHERE p.serviceType = :serviceType"),
    @NamedQuery(name = "PrmsServiceAndWorkReg.findByWorkName", query = "SELECT p FROM PrmsServiceAndWorkReg p WHERE p.workName = :workName"),
    @NamedQuery(name = "PrmsServiceAndWorkReg.findByWorkNo", query = "SELECT p FROM PrmsServiceAndWorkReg p WHERE p.workNo = :workNo"),
    @NamedQuery(name = "PrmsServiceAndWorkReg.findByStatus", query = "SELECT p FROM PrmsServiceAndWorkReg p WHERE p.status=:status"),
    @NamedQuery(name = "PrmsServiceAndWorkReg.findByCurrentStatus", query = "SELECT p FROM PrmsServiceAndWorkReg p WHERE p.currentStatus=:currentStatus")})
public class PrmsServiceAndWorkReg implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(generator = "PRMS_SERVANDWORK_REGIST_SEQ", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "PRMS_SERVANDWORK_REGIST_SEQ", sequenceName = "PRMS_SERVANDWORK_REGIST_SEQ", allocationSize = 1)
    @Column(name = "SERV_AND_WORK_ID", nullable = false, precision = 38, scale = 0)
    private BigDecimal servAndWorkId;
    @Size(max = 50)
    @Column(name = "UNIT_MEASURES", length = 50)
    private String unitMeasures;
    @Size(max = 255)
    @Column(name = "SERVICE_NAME", length = 255)
    private String serviceName;
//    @Size(max = 255)
    @Column(name = "PREPARED_BY", length = 255)
    private Integer preparedBy;
    @Size(max = 35)
    @Column(name = "REGISTRATION_DATE")
//    @Temporal(TemporalType.TIMESTAMP)
    private String registrationDate;
    @Size(max = 100)
    @Column(name = "SERVICE_NO", length = 100)
    private String serviceNo;
    @Size(max = 255)
    @Column(name = "SPECIFICATION_REMARK", length = 255)
    private String specificationRemark;
    @Size(max = 100)
    @Column(name = "REGISTERATION_TYPE", length = 100)
    private String registerationType;
    @Size(max = 50)
    @Column(name = "SERVICE_TYPE", length = 50)
    private String serviceType;
    @Size(max = 255)
    @Column(name = "WORK_NAME", length = 255)
    private String workName;
    @Size(max = 100)
    @Column(name = "WORK_NO", length = 100)
    private String workNo;
    @Column(name = "STATUS")
    private Integer status;
    @Column(name = "CURRENT_STATUS")
    private Integer currentStatus;
    @Transient
    private String columnName;
    @Transient
    private String columnValue;
    @JoinColumn(name = "GENERAL_LEDGER_ID", referencedColumnName = "GENERAL_LEDGER_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private FmsGeneralLedger generalLedgerId;
    @OneToMany(mappedBy = "serviceId")
    private List<PrmsMarketAssmntService> prmsMarketAssmntServiceList;
    @OneToMany(mappedBy = "serviceId")
    private List<PrmsPurchaseReqService> prmsPurchaseReqServiceList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "serviceId")
    private List<PrmsBidDetail> prmsBidDetailList;
    @OneToMany(mappedBy = "serviceId")
    private List<PrmsBidDetailAmend> prmsBidDetailAmendList;
    @OneToMany(mappedBy = "serviceId")
    private List<PrmsAnnualPlanService> prmsAnnualPlanServiceList;
    @OneToMany(mappedBy = "serviceId")
    private List<PrmsPurOrderDetail> prmsPurOrderDetailList;
    @OneToMany(mappedBy = "serviceWorkId")
    private List<PrmsContractDetail> prmsContractDetailList;
    @OneToMany(mappedBy = "serviceWorkId")
    private List<PrmsAwardDetail> PrmsAwardDetailLists;
    @OneToMany(mappedBy = "serviceId")
    private List<PrmsFinancialEvlResultyDtl> prmsFinancialEvlResultyDtlsLists;
    @OneToMany(mappedBy = "serviceId")
    private List<PrmsThechincalEvaluationDet> prmsThechincalEvaluationDetList;

    public PrmsServiceAndWorkReg() {
    }

    public PrmsServiceAndWorkReg(BigDecimal servAndWorkId) {
        this.servAndWorkId = servAndWorkId;
    }

    public BigDecimal getServAndWorkId() {
        return servAndWorkId;
    }

    public void setServAndWorkId(BigDecimal servAndWorkId) {
        this.servAndWorkId = servAndWorkId;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnValue() {
        return columnValue;
    }

    public void setColumnValue(String columnValue) {
        this.columnValue = columnValue;
    }

    public String getUnitMeasures() {
        return unitMeasures;
    }

    public void setUnitMeasures(String unitMeasures) {
        this.unitMeasures = unitMeasures;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public Integer getPreparedBy() {
        return preparedBy;
    }

    public void setPreparedBy(Integer preparedBy) {
        this.preparedBy = preparedBy;
    }

    public String getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getServiceNo() {
        return serviceNo;
    }

    public void setServiceNo(String serviceNo) {
        this.serviceNo = serviceNo;
    }

    public String getSpecificationRemark() {
        return specificationRemark;
    }

    public void setSpecificationRemark(String specificationRemark) {
        this.specificationRemark = specificationRemark;
    }

    public String getRegisterationType() {
        return registerationType;
    }

    public void setRegisterationType(String registerationType) {
        this.registerationType = registerationType;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getWorkName() {
        return workName;
    }

    public void setWorkName(String workName) {
        this.workName = workName;
    }

    public String getWorkNo() {
        return workNo;
    }

    public void setWorkNo(String workNo) {
        this.workNo = workNo;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(Integer currentStatus) {
        this.currentStatus = currentStatus;
    }

    public FmsGeneralLedger getGeneralLedgerId() {
        return generalLedgerId;
    }

    public void setGeneralLedgerId(FmsGeneralLedger generalLedgerId) {
        this.generalLedgerId = generalLedgerId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (servAndWorkId != null ? servAndWorkId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PrmsServiceAndWorkReg)) {
            return false;
        }
        PrmsServiceAndWorkReg other = (PrmsServiceAndWorkReg) object;
        if ((this.servAndWorkId == null && other.servAndWorkId != null) || (this.servAndWorkId != null && !this.servAndWorkId.equals(other.servAndWorkId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.prms.entity.PrmsServiceAndWorkReg[servAndWorkId=" + servAndWorkId + "]";
//        return getServAndWorkId();
    }

    @XmlTransient

    public List<PrmsMarketAssmntService> getPrmsMarketAssmntServiceList() {
        if (prmsMarketAssmntServiceList == null) {
            prmsMarketAssmntServiceList = new ArrayList<>();
        }
        return prmsMarketAssmntServiceList;
    }

    public void setPrmsMarketAssmntServiceList(List<PrmsMarketAssmntService> prmsMarketAssmntServiceList) {
        this.prmsMarketAssmntServiceList = prmsMarketAssmntServiceList;
    }

    @XmlTransient

    public List<PrmsPurchaseReqService> getPrmsPurchaseReqServiceList() {
        if (prmsPurchaseReqServiceList == null) {
            prmsPurchaseReqServiceList = new ArrayList<PrmsPurchaseReqService>();
        }
        return prmsPurchaseReqServiceList;
    }

    public void setPrmsPurchaseReqServiceList(List<PrmsPurchaseReqService> prmsPurchaseReqServiceList) {
        this.prmsPurchaseReqServiceList = prmsPurchaseReqServiceList;
    }

    @XmlTransient

    public List<PrmsBidDetail> getPrmsBidDetailList() {
        if (prmsBidDetailList == null) {
            prmsBidDetailList = new ArrayList<>();
        }
        return prmsBidDetailList;
    }

    public void setPrmsBidDetailList(List<PrmsBidDetail> prmsBidDetailList) {
        this.prmsBidDetailList = prmsBidDetailList;
    }

    @XmlTransient

    public List<PrmsBidDetailAmend> getPrmsBidDetailAmendList() {
        return prmsBidDetailAmendList;
    }

    public void setPrmsBidDetailAmendList(List<PrmsBidDetailAmend> prmsBidDetailAmendList) {
        this.prmsBidDetailAmendList = prmsBidDetailAmendList;
    }

    @XmlTransient

    public List<PrmsAnnualPlanService> getPrmsAnnualPlanServiceList() {
        return prmsAnnualPlanServiceList;
    }

    public void setPrmsAnnualPlanServiceList(List<PrmsAnnualPlanService> prmsAnnualPlanServiceList) {
        this.prmsAnnualPlanServiceList = prmsAnnualPlanServiceList;
    }

    @XmlTransient

    public List<PrmsPurOrderDetail> getPrmsPurOrderDetailList() {
        return prmsPurOrderDetailList;
    }

    public void setPrmsPurOrderDetailList(List<PrmsPurOrderDetail> prmsPurOrderDetailList) {
        this.prmsPurOrderDetailList = prmsPurOrderDetailList;
    }

    @XmlTransient

    public List<PrmsContractDetail> getPrmsContractDetailList() {
        return prmsContractDetailList;
    }

    public void setPrmsContractDetailList(List<PrmsContractDetail> prmsContractDetailList) {
        this.prmsContractDetailList = prmsContractDetailList;
    }

    public List<PrmsAwardDetail> getPrmsAwardDetailLists() {
        return PrmsAwardDetailLists;
    }

    public void setPrmsAwardDetailLists(List<PrmsAwardDetail> PrmsAwardDetailLists) {
        this.PrmsAwardDetailLists = PrmsAwardDetailLists;
    }

    @XmlTransient

    public List<PrmsFinancialEvlResultyDtl> getPrmsFinancialEvlResultyDtlsLists() {
        if (prmsFinancialEvlResultyDtlsLists == null) {
            prmsFinancialEvlResultyDtlsLists = new ArrayList<>();
        }
        return prmsFinancialEvlResultyDtlsLists;
    }

    public void setPrmsFinancialEvlResultyDtlsLists(List<PrmsFinancialEvlResultyDtl> prmsFinancialEvlResultyDtlsLists) {
        this.prmsFinancialEvlResultyDtlsLists = prmsFinancialEvlResultyDtlsLists;
    }

    @XmlTransient

    public List<PrmsThechincalEvaluationDet> getPrmsThechincalEvaluationDetList() {
        return prmsThechincalEvaluationDetList;
    }

    public void setPrmsThechincalEvaluationDetList(List<PrmsThechincalEvaluationDet> prmsThechincalEvaluationDetList) {
        this.prmsThechincalEvaluationDetList = prmsThechincalEvaluationDetList;
    }

}
