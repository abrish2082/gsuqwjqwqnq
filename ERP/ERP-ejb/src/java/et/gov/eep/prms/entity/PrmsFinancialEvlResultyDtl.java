/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.entity;

import et.gov.eep.mms.entity.MmsItemRegistration;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author user
 */
@Entity
@Table(name = "PRMS_FINANCIAL_EVL_RESULTY_DTL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrmsFinancialEvlResultyDtl.findAll", query = "SELECT p FROM PrmsFinancialEvlResultyDtl p"),
    @NamedQuery(name = "PrmsFinancialEvlResultyDtl.findById", query = "SELECT p FROM PrmsFinancialEvlResultyDtl p WHERE p.id = :id"),
    @NamedQuery(name = "PrmsFinancialEvlResultyDtl.findByFnancialResultId", query = "SELECT p FROM PrmsFinancialEvlResultyDtl p WHERE p.fnancialResultId=:fnancialResultId"),
    @NamedQuery(name = "PrmsFinancialEvlResultyDtl.findByResultId", query = "SELECT p FROM PrmsFinancialEvlResultyDtl p WHERE p.fnancialResultId=:fnancialResultId AND p.resultRank=:rank"),
    @NamedQuery(name = "PrmsFinancialEvlResultyDtl.findByResultRank", query = "SELECT p FROM PrmsFinancialEvlResultyDtl p WHERE p.resultRank = :resultRank"),
    @NamedQuery(name = "PrmsFinancialEvlResultyDtl.findByFnancialResultIdLotNoAndRank", query = "SELECT p FROM PrmsFinancialEvlResultyDtl p WHERE p.fnancialResultId = :fnancialResultId AND p.resultRank = :nextRank AND P.lotNo = :lotNo"),
    @NamedQuery(name = "PrmsFinancialEvlResultyDtl.findByFnancialResultIdMatCodeAndRank", query = "SELECT p FROM PrmsFinancialEvlResultyDtl p WHERE p.fnancialResultId = :fnancialResultId AND p.resultRank = :nextRank AND P.itemId = :matCode")})
public class PrmsFinancialEvlResultyDtl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Size(min = 1, max = 20)
    @Column(name = "ID", length = 20)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRMS_FINANCIAL_RESULT_DETL_SEQ")
    @SequenceGenerator(name = "PRMS_FINANCIAL_RESULT_DETL_SEQ", sequenceName = "PRMS_FINANCIAL_RESULT_DETL_SEQ", allocationSize = 1)
    private String id;

    @Column(name = "RESULT_RANK")
    private Integer resultRank;
    @Column(name = "TOTAL_PRICE")
    private double totalPrice;
    @Column(name = "UNIT_PRICE")
    private double unitPrice;
    @Column(name = "SUM")
    private double sum;

    @JoinColumn(name = "FNANCIAL_RESULT_ID", referencedColumnName = "ID")
    @ManyToOne(cascade = CascadeType.ALL)
    private PrmsFinancialEvalResult fnancialResultId;
    @JoinColumn(name = "ITEM_ID", referencedColumnName = "MATERIAL_ID")
    @ManyToOne
    private MmsItemRegistration itemId;
    @JoinColumn(name = "SERVICE_ID", referencedColumnName = "SERV_AND_WORK_ID")
    @ManyToOne
    private PrmsServiceAndWorkReg serviceId;
    @OneToMany(mappedBy = "finacialResutId")
    private List<PrmsAwardDetail> prmsAwardDetailsList;
    @JoinColumn(name = "SUPPLIER_ID", referencedColumnName = "ID")
    @ManyToOne
    private PrmsSupplyProfile supplierId;
    @JoinColumn(name = "FINANCIAL_EVALUATION_DTL_ID", referencedColumnName = "ID")
    @ManyToOne
    private PrmsFinancialEvaluaDetail financialEvaluaDetailId;
//    @JoinColumn(name = "PID", referencedColumnName = "POST_ID")
//    @ManyToOne
//    private PrmsPostQualification pid;

    @JoinColumn(name = "BID_DETAIL_ID", referencedColumnName = "ID")
    @ManyToOne
    private PrmsBidDetail bidDetailId;
    //    @JoinColumn(name = "ID", referencedColumnName = "ID")
//    @ManyToOne
//    private PrmsFinancialEvlResultyDtl financialEvaluationDtlId;
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "postid", fetch = FetchType.LAZY)
//    private List<PrmsPostQualification> prmsPostQualificationList;

    @Column(name = "VAT")
    private double vat;

    @Column(name = "WITH_HOLD")
    private double withHold;

    @Size(min = 1, max = 20)
    @Column(name = "LOT_NO", length = 20)
    private String lotNo;

    @Column(name = "FINANCIALRESULT")
    private double financialResult;
    @Column(name = "TECHINCALRESULT")
    private double technicalResult;

    public double getFinancialResult() {
        return financialResult;
    }

    public void setFinancialResult(double financialResult) {
        this.financialResult = financialResult;
    }

    public double getTechnicalResult() {
        return technicalResult;
    }

    public void setTechnicalResult(double technicalResult) {
        this.technicalResult = technicalResult;
    }

    public PrmsFinancialEvlResultyDtl() {
    }

    public PrmsFinancialEvlResultyDtl(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getResultRank() {
        return resultRank;
    }

    public void setResultRank(Integer resultRank) {
        this.resultRank = resultRank;
    }

    public PrmsSupplyProfile getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(PrmsSupplyProfile supplierId) {
        this.supplierId = supplierId;
    }

    public PrmsFinancialEvalResult getFnancialResultId() {
        if (fnancialResultId == null) {
            fnancialResultId = new PrmsFinancialEvalResult();
        }
        return fnancialResultId;
    }

    public void setFnancialResultId(PrmsFinancialEvalResult fnancialResultId) {
        this.fnancialResultId = fnancialResultId;
    }

    public String getLotNo() {
        return lotNo;
    }

    public void setLotNo(String lotNo) {
        this.lotNo = lotNo;
    }

    public PrmsFinancialEvaluaDetail getFinancialEvaluaDetailId() {
        return financialEvaluaDetailId;
    }

    public void setFinancialEvaluaDetailId(PrmsFinancialEvaluaDetail financialEvaluaDetailId) {
        this.financialEvaluaDetailId = financialEvaluaDetailId;
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
        if (!(object instanceof PrmsFinancialEvlResultyDtl)) {
            return false;
        }
        PrmsFinancialEvlResultyDtl other = (PrmsFinancialEvlResultyDtl) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return id;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public PrmsBidDetail getBidDetailId() {
        if (bidDetailId == null) {
            bidDetailId = new PrmsBidDetail();
        }
        return bidDetailId;
    }

    public void setBidDetailId(PrmsBidDetail bidDetailId) {
        this.bidDetailId = bidDetailId;
    }

    public double getVat() {
        return vat;
    }

    public void setVat(double vat) {
        this.vat = vat;
    }

    public double getWithHold() {
        return withHold;
    }

    public void setWithHold(double withHold) {
        this.withHold = withHold;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

//    public PrmsFinancialEvlResultyDtl getFinancialEvaluationDtlId() {
//        return financialEvaluationDtlId;
//    }
//
//    public void setFinancialEvaluationDtlId(PrmsFinancialEvlResultyDtl financialEvaluationDtlId) {
//        this.financialEvaluationDtlId = financialEvaluationDtlId;
//    }
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

    public MmsItemRegistration getItemId() {
        return itemId;
    }

    public void setItemId(MmsItemRegistration itemId) {
        this.itemId = itemId;
    }

    public PrmsServiceAndWorkReg getServiceId() {
        return serviceId;
    }

    public void setServiceId(PrmsServiceAndWorkReg serviceId) {
        this.serviceId = serviceId;
    }

//    @XmlTransient
//
//    public List<PrmsPostQualification> getPrmsPostQualificationList() {
//        return prmsPostQualificationList;
//    }
//
//    public void setPrmsPostQualificationList(List<PrmsPostQualification> prmsPostQualificationList) {
//        this.prmsPostQualificationList = prmsPostQualificationList;
//    }
//    public PrmsPostQualification getPid() {
//        return pid;
//    }
//
//    public void setPid(PrmsPostQualification pid) {
//        this.pid = pid;
//    }
}
