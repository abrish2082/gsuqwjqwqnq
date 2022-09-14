/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.entity;

import et.gov.eep.commonApplications.entity.WfPrmsProcessed;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author user
 */
@Entity
@Table(name = "PRMS_FINANCIAL_EVAL_RESULT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrmsFinancialEvalResult.findAll", query = "SELECT p FROM PrmsFinancialEvalResult p"),
    @NamedQuery(name = "PrmsFinancialEvalResult.findById", query = "SELECT p FROM PrmsFinancialEvalResult p WHERE p.id = :id"),
    @NamedQuery(name = "PrmsFinancialEvalResult.findFinancialResult", query = "SELECT p FROM PrmsFinancialEvalResult p WHERE p.financialResultNo LIKE :financialResult"),
    @NamedQuery(name = "PrmsFinancialEvalResult.findByIdAutoIncremnt", query = "SELECT p FROM PrmsFinancialEvalResult p WHERE p.id =(SELECT Max(p.id)  from PrmsFinancialEvalResult p)"),
    @NamedQuery(name = "PrmsFinancialEvalResult.findByDateReg", query = "SELECT p FROM PrmsFinancialEvalResult p WHERE p.dateReg = :dateReg"),
    @NamedQuery(name = "PrmsFinancialEvalResult.findByStatus", query = "SELECT p FROM PrmsFinancialEvalResult p WHERE p.status = 0"),
    @NamedQuery(name = "PrmsFinancialEvalResult.findByResultNum", query = "SELECT p FROM PrmsFinancialEvalResult p WHERE p.financialResultNo LIKE :financialResultNo"),
    @NamedQuery(name = "PrmsFinancialEvalResult.findByFnancialResultId", query = "SELECT p FROM PrmsFinancialEvlResultyDtl p WHERE p.fnancialResultId = :fnancialResultId AND p.resultRank = :rank"),
    @NamedQuery(name = "PrmsFinancialEvalResult.findByDateApproved", query = "SELECT p FROM PrmsFinancialEvalResult p WHERE p.dateApproved = :dateApproved"),
    @NamedQuery(name = "PrmsFinancialEvalResult.findByPreparedBy", query = "SELECT p FROM PrmsFinancialEvalResult p WHERE p.preparedBy = :preparedBy"),
    @NamedQuery(name = "PrmsFinancialEvalResult.findByRemark", query = "SELECT p FROM PrmsFinancialEvalResult p WHERE p.remark = :remark"),
    @NamedQuery(name = "PrmsFinancialEvalResult.findByApprovedBy", query = "SELECT p FROM PrmsFinancialEvalResult p WHERE p.approvedBy = :approvedBy")})
public class PrmsFinancialEvalResult implements Serializable {

    @OneToMany(mappedBy = "financId", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PrmsPostQualification> prmsPostQualificationCollection;
    @OneToMany(mappedBy = "financialEvaluationResultId", fetch = FetchType.LAZY)
    private List<WfPrmsProcessed> wfPrmsProcessedLists;
//    @OneToMany(mappedBy = "financialId")
//    private List<PrmsPostQualification> prmsPostQualificationList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRMS_FINANCIAL_EVAL_RESULT_SEQ")
    @SequenceGenerator(name = "PRMS_FINANCIAL_EVAL_RESULT_SEQ", sequenceName = "PRMS_FINANCIAL_EVAL_RESULT_SEQ", allocationSize = 1)
    @Size(min = 1, max = 20)
    @Column(name = "ID", length = 20)
    private String id;
    @Size(min = 1, max = 20)
    @Column(name = "AWARD_TYPE", length = 20)
    private String awardType;
    @Size(min = 1, max = 20)
    @Column(name = "RESULT_FROM", length = 20)
    private String resultFrom;
    @Column(name = "DATE_APPROVED")
    @Temporal(TemporalType.DATE)
    private Date dateApproved;
    @Column(name = "DATE_REG")
    @Temporal(TemporalType.DATE)
    private Date dateReg;
    @Column(name = "PREPARED_BY")
    private Integer preparedBy;
    @Size(max = 20)
    @Column(name = "REMARK", length = 20)
    private String remark;
    @Size(max = 20)
    @Column(name = "PURCHASE_TYPE", length = 20)
    private String purchaseType;
    @Column(name = "STATUS")
    private Integer status;
    @Size(max = 20)
    @Column(name = "APPROVED_BY", length = 20)
    private String approvedBy;
    @Size(max = 20)
    @Column(name = "FINANCIAL_RESULT_NO", length = 20)
    private String financialResultNo;
    @Transient
    private String columnName;
    @Transient
    private String columnValue;
    @JoinColumn(name = "FINANCIALEVA_ID", referencedColumnName = "ID")
    @ManyToOne
    private PrmsFinancialEvaluation financialevaId;
    @JoinColumn(name = "QUOTATION_ID", referencedColumnName = "QUATATION_ID")
    @ManyToOne
    private PrmsQuotation quotationId;
    @JoinColumn(name = "BID_ID", referencedColumnName = "ID")
    @ManyToOne
    private PrmsBid bidId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fnancialResultId", fetch = FetchType.LAZY)
    private List<PrmsFinancialEvlResultyDtl> prmsFinancialEvlResultyDtlList;

    public PrmsFinancialEvalResult() {
    }

    public PrmsFinancialEvalResult(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Date getDateReg() {
        return dateReg;
    }

    public void setDateReg(Date dateReg) {
        this.dateReg = dateReg;
    }

   
    public Date getDateApproved() {
        return dateApproved;
    }

    public void setDateApproved(Date dateApproved) {
        this.dateApproved = dateApproved;
    }

    public String getPurchaseType() {
        return purchaseType;
    }

    public void setPurchaseType(String purchaseType) {
        this.purchaseType = purchaseType;
    }

    public Integer getPreparedBy() {
        return preparedBy;
    }

    public void setPreparedBy(Integer preparedBy) {
        this.preparedBy = preparedBy;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getResultFrom() {
        return resultFrom;
    }

    public void setResultFrom(String resultFrom) {
        this.resultFrom = resultFrom;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    public PrmsFinancialEvaluation getFinancialevaId() {
        return financialevaId;
    }

    public void setFinancialevaId(PrmsFinancialEvaluation financialevaId) {
        this.financialevaId = financialevaId;
    }

    @XmlTransient
    public List<PrmsFinancialEvlResultyDtl> getPrmsFinancialEvlResultyDtlList() {
        if (prmsFinancialEvlResultyDtlList == null) {
            prmsFinancialEvlResultyDtlList = new ArrayList<>();
        }
        return prmsFinancialEvlResultyDtlList;
    }

    public void setPrmsFinancialEvlResultyDtlList(List<PrmsFinancialEvlResultyDtl> prmsFinancialEvlResultyDtlList) {
        this.prmsFinancialEvlResultyDtlList = prmsFinancialEvlResultyDtlList;
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
        if (!(object instanceof PrmsFinancialEvalResult)) {
            return false;
        }
        PrmsFinancialEvalResult other = (PrmsFinancialEvalResult) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return financialResultNo;
    }

    public String getFinancialResultNo() {
        return financialResultNo;
    }

    public void setFinancialResultNo(String financialResultNo) {
        this.financialResultNo = financialResultNo;
    }

//    @XmlTransient
//    public List<PrmsPostQualification> getPrmsPostQualificationList() {
//        return prmsPostQualificationList;
//    }
//
//    public void setPrmsPostQualificationList(List<PrmsPostQualification> prmsPostQualificationList) {
//        this.prmsPostQualificationList = prmsPostQualificationList;
//    }
    public void addEvalResultDtl(PrmsFinancialEvlResultyDtl evlResultyDtl) {
        System.out.println("..........add..............");
        if (evlResultyDtl.getFnancialResultId() != this) {
            System.out.println("..... trying to addd.........");
            evlResultyDtl.setFnancialResultId(this);
            this.getPrmsFinancialEvlResultyDtlList().add(evlResultyDtl);

        }
    }

    public PrmsQuotation getQuotationId() {
        return quotationId;
    }

    public void setQuotationId(PrmsQuotation quotationId) {
        this.quotationId = quotationId;
    }

    public PrmsBid getBidId() {
        return bidId;
    }

    public void setBidId(PrmsBid bidId) {
        this.bidId = bidId;
    }

    public String getAwardType() {
        return awardType;
    }

    public void setAwardType(String awardType) {
        this.awardType = awardType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @XmlTransient
    public List<PrmsPostQualification> getPrmsPostQualificationCollection() {
        if (prmsPostQualificationCollection == null) {
            prmsPostQualificationCollection = new ArrayList<>();
        }
        return prmsPostQualificationCollection;
    }

    public void setPrmsPostQualificationCollection(List<PrmsPostQualification> prmsPostQualificationCollection) {
        this.prmsPostQualificationCollection = prmsPostQualificationCollection;
    }

    @XmlTransient
    public List<WfPrmsProcessed> getWfPrmsProcessedCollection() {
        return wfPrmsProcessedLists;
    }

    public void setWfPrmsProcessedCollection(List<WfPrmsProcessed> wfPrmsProcessedLists) {
        this.wfPrmsProcessedLists = wfPrmsProcessedLists;
    }

}
