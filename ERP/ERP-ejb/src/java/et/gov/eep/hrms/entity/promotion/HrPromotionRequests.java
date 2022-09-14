/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.promotion;

import et.gov.eep.commonApplications.entity.WfHrProcessed;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.recruitment.HrAdvertisedJobs;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author user
 */
@Entity
@Table(name = "HR_PROMOTION_REQUESTS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrPromotionRequests.findAll", query = "SELECT h FROM HrPromotionRequests h"),
    @NamedQuery(name = "HrPromotionRequests.findAllByDistinictJobTitle", query = "SELECT DISTINCT h.advertJobId.jobId FROM HrPromotionRequests h"),
    @NamedQuery(name = "HrPromotionRequests.findByDistinictJobsId", query = "SELECT h FROM HrPromotionRequests h WHERE h.advertJobId.jobId = :jobId"),

    @NamedQuery(name = "HrPromotionRequests.findByJobsId", query = "SELECT h FROM HrPromotionRequests h WHERE h.advertJobId.jobId.jobTitle = :jobTitle"),
    @NamedQuery(name = "HrPromotionRequests.findByEmpIdAndJobId", query = "SELECT h FROM HrPromotionRequests h WHERE h.requesterId.id = :empId AND h.advertJobId.id = :advertJobId"),

    @NamedQuery(name = "HrPromotionRequests.findById", query = "SELECT h FROM HrPromotionRequests h WHERE h.id = :id"),
    @NamedQuery(name = "HrPromotionRequests.findByEducResult", query = "SELECT h FROM HrPromotionRequests h WHERE h.educResult = :educResult"),
    @NamedQuery(name = "HrPromotionRequests.findByExprResult", query = "SELECT h FROM HrPromotionRequests h WHERE h.exprResult = :exprResult"),
    @NamedQuery(name = "HrPromotionRequests.findByPrfmResult", query = "SELECT h FROM HrPromotionRequests h WHERE h.prfmResult = :prfmResult"),
    @NamedQuery(name = "HrPromotionRequests.findByDocuResult", query = "SELECT h FROM HrPromotionRequests h WHERE h.docuResult = :docuResult"),
    @NamedQuery(name = "HrPromotionRequests.findByComtResult", query = "SELECT h FROM HrPromotionRequests h WHERE h.comtResult = :comtResult"),
    @NamedQuery(name = "HrPromotionRequests.findByBonusResult", query = "SELECT h FROM HrPromotionRequests h WHERE h.bonusResult = :bonusResult"),
    @NamedQuery(name = "HrPromotionRequests.findByExamResult", query = "SELECT h FROM HrPromotionRequests h WHERE h.examResult = :examResult"),
    @NamedQuery(name = "HrPromotionRequests.findByRemark", query = "SELECT h FROM HrPromotionRequests h WHERE h.remark = :remark"),
    @NamedQuery(name = "HrPromotionRequests.findByStatus", query = "SELECT h FROM HrPromotionRequests h WHERE h.status = :status"),
    @NamedQuery(name = "HrPromotionRequests.findByPass", query = "SELECT h FROM HrPromotionRequests h WHERE h.pass = :pass")})
public class HrPromotionRequests implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_PROMOTION_REQUESTS_SEQ")
    @SequenceGenerator(name = "HR_PROMOTION_REQUESTS_SEQ", sequenceName = "HR_PROMOTION_REQUESTS_SEQ", allocationSize = 1)
    private Integer id;

    @Column(name = "EDUC_RESULT")
    private double educResult;

    @Column(name = "EXPR_RESULT")
    private double exprResult;

    @Column(name = "PRFM_RESULT")
    private double prfmResult;

    @Column(name = "DOCU_RESULT")
    private double docuResult;

    @Column(name = "COMT_RESULT")
    private double comtResult;

    @Column(name = "BONUS_RESULT")
    private double bonusResult;

    @Column(name = "EXAM_RESULT")
    private double examResult;

    @Size(max = 400)
    @Column(name = "REMARK")
    private String remark;

    @Column(name = "STATUS")
    private Integer status;

    @Column(name = "PASS")
    private BigInteger pass;

    @OneToMany(mappedBy = "promotionId", cascade = CascadeType.ALL)
    private List<WfHrProcessed> hrWorkFlowPromList;

    @Column(name = "TRAINING_RESULT")
    private double trainingResult;

    @JoinColumn(name = "ADVERT_JOB_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrAdvertisedJobs advertJobId;

    @JoinColumn(name = "REQUESTER_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrEmployees requesterId;

    public HrPromotionRequests() {
    }

    public HrPromotionRequests(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public double getEducResult() {
        return educResult;
    }

    public void setEducResult(double educResult) {
        this.educResult = educResult;
    }

    public double getExprResult() {
        return exprResult;
    }

    public void setExprResult(double exprResult) {
        this.exprResult = exprResult;
    }

    public double getPrfmResult() {
        return prfmResult;
    }

    public void setPrfmResult(double prfmResult) {
        this.prfmResult = prfmResult;
    }

    public double getDocuResult() {
        return docuResult;
    }

    public void setDocuResult(double docuResult) {
        this.docuResult = docuResult;
    }

    public double getComtResult() {
        return comtResult;
    }

    public void setComtResult(double comtResult) {
        this.comtResult = comtResult;
    }

    public double getTrainingResult() {
        return trainingResult;
    }

    public void setTrainingResult(double trainingResult) {
        this.trainingResult = trainingResult;
    }

    public double getBonusResult() {
        return bonusResult;
    }

    public void setBonusResult(double bonusResult) {
        this.bonusResult = bonusResult;
    }

    public double getExamResult() {
        return examResult;
    }

    public void setExamResult(double examResult) {
        this.examResult = examResult;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    

    public BigInteger getPass() {
        return pass;
    }

    public void setPass(BigInteger pass) {
        this.pass = pass;
    }

    public HrAdvertisedJobs getAdvertJobId() {
        return advertJobId;
    }

    public void setAdvertJobId(HrAdvertisedJobs advertJobId) {
        this.advertJobId = advertJobId;
    }

    public HrEmployees getRequesterId() {
        return requesterId;
    }

    public void setRequesterId(HrEmployees requesterId) {
        this.requesterId = requesterId;
    }

    @XmlTransient
    public List<WfHrProcessed> getHrWorkFlowPromList() {
        return hrWorkFlowPromList;
    }

    public void setHrWorkFlowPromList(List<WfHrProcessed> hrWorkFlowPromList) {
        this.hrWorkFlowPromList = hrWorkFlowPromList;
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
        if (!(object instanceof HrPromotionRequests)) {
            return false;
        }
        HrPromotionRequests other = (HrPromotionRequests) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return id.toString();//"et.gov.eep.hrms.entity.promotion.HrPromotionRequests[ id=" + id + " ]";
    }

    @Transient
    public double total = 0;

    public double getTotal() {
        total = prfmResult + educResult + examResult + exprResult + trainingResult;
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

}
