/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.compliantHandling;

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
@Table(name = "HR_APPEALS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrAppeals.findAll", query = "SELECT h FROM HrAppeals h"),
    @NamedQuery(name = "HrAppeals.findById", query = "SELECT h FROM HrAppeals h WHERE h.id = :id"),
    @NamedQuery(name = "HrAppeals.findByCaseId", query = "SELECT h FROM HrAppeals h WHERE h.caseId = :caseId"),
    @NamedQuery(name = "HrAppeals.findByCaseCategory", query = "SELECT h FROM HrAppeals h WHERE h.caseCategory = :caseCategory"),
    @NamedQuery(name = "HrAppeals.findByGround", query = "SELECT h FROM HrAppeals h WHERE h.ground = :ground"),
    @NamedQuery(name = "HrAppeals.findByAppealDate", query = "SELECT h FROM HrAppeals h WHERE h.appealDate = :appealDate"),
    @NamedQuery(name = "HrAppeals.findByStatus", query = "SELECT h FROM HrAppeals h WHERE h.status = :statusRequest"),
    @NamedQuery(name = "HrAppeals.findByAppeallant", query = "SELECT h FROM HrAppeals h WHERE UPPER(h.applicantId.firstName) LIKE :firstName"),
    @NamedQuery(name = "HrAppeals.findByAppeallantAndStatus", query = "SELECT h FROM HrAppeals h WHERE UPPER(h.applicantId.firstName) LIKE :firstName AND h.status = :status"),
    @NamedQuery(name = "HrAppeals.findByPreparedOn", query = "SELECT h FROM HrAppeals h WHERE h.preparedOn = :preparedOn")})
public class HrAppeals implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HrAppeals_SEQ_GENERATOR")
    @SequenceGenerator(name = "HrAppeals_SEQ_GENERATOR", sequenceName = "HrAppeals_SEQ", allocationSize = 1)
    private Integer id;
    @Size(max = 20)
    @Column(name = "CASE_ID")
    private String caseId;
    @Size(max = 20)
    @Column(name = "CASE_CATEGORY")
    private String caseCategory;
    @Size(max = 200)
    @Column(name = "GROUND")
    private String ground;
    @Size(max = 20)
    @Column(name = "APPEAL_DATE")
    private String appealDate;
    @Column(name = "APPEAL_DECISION_DATE")
    private String appealDecisionDate;
    @Size(max = 20)
    @Column(name = "PREPARED_ON")
    private String preparedOn;
    @JoinColumn(name = "APPLICANT_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrEmployees applicantId;
//    @JoinColumn(name = "PREPARED_BY", referencedColumnName = "ID")
//    @ManyToOne
    @Column(name = "PREPARED_BY")
    private String preparedBy;
    @Column(name = "STATUS")
    private Integer status;
    @OneToMany(mappedBy = "appealCaseId", cascade = CascadeType.ALL)
    private List<HrAppealRequestFileUpload> hrAppealRequestFileUploadList;
    @OneToMany(mappedBy = "appealId", cascade = CascadeType.ALL)
    private List<WfHrProcessed> hrWorkFlowProccedList;

    /**
     *
     */
    public HrAppeals() {
    }

    /**
     *
     * @param id
     */
    public HrAppeals(Integer id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public String getCaseId() {
        return caseId;
    }

    /**
     *
     * @param caseId
     */
    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    /**
     *
     * @return
     */
    public String getCaseCategory() {
        return caseCategory;
    }

    /**
     *
     * @param caseCategory
     */
    public void setCaseCategory(String caseCategory) {
        this.caseCategory = caseCategory;
    }

    /**
     *
     * @return
     */
    public String getGround() {
        return ground;
    }

    /**
     *
     * @param ground
     */
    public void setGround(String ground) {
        this.ground = ground;
    }

    /**
     *
     * @return
     */
    public String getAppealDate() {
        return appealDate;
    }

    /**
     *
     * @param appealDate
     */
    public void setAppealDate(String appealDate) {
        this.appealDate = appealDate;
    }

    public String getAppealDecisionDate() {
        return appealDecisionDate;
    }

    public void setAppealDecisionDate(String appealDecisionDate) {
        this.appealDecisionDate = appealDecisionDate;
    }

    /**
     *
     * @return
     */
    public String getPreparedOn() {
        return preparedOn;
    }

    /**
     *
     * @param preparedOn
     */
    public void setPreparedOn(String preparedOn) {
        this.preparedOn = preparedOn;
    }

    /**
     *
     * @return
     */
    public HrEmployees getApplicantId() {
        return applicantId;
    }

    /**
     *
     * @param applicantId
     */
    public void setApplicantId(HrEmployees applicantId) {
        this.applicantId = applicantId;
    }

    /**
     *
     * @return
     */
    public String getPreparedBy() {
        return preparedBy;
    }

    /**
     *
     * @param preparedBy
     */
    public void setPreparedBy(String preparedBy) {
        this.preparedBy = preparedBy;
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
        if (!(object instanceof HrAppeals)) {
            return false;
        }
        HrAppeals other = (HrAppeals) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.hrms.entity.compliantHandling.HrAppeals[ id=" + id + " ]";
    }

    //<editor-fold defaultstate="collapsed" desc="static variable">
    public static int PROMOTION = 1;
    public static int DISCIPLINE = 2;
    public static int OTHER = 10;
    @Transient
    String changedStatus;

    //</editor-fold>
    public String getChangedStatus() {
        return changedStatus;
    }

    public void setChangedStatus(String changedStatus) {
        this.changedStatus = changedStatus;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @XmlTransient
    public List<HrAppealRequestFileUpload> getHrAppealRequestFileUploadList() {
        if (hrAppealRequestFileUploadList == null) {
            hrAppealRequestFileUploadList = new ArrayList<>();
        }
        return hrAppealRequestFileUploadList;
    }

    public void setHrAppealRequestFileUploadList(List<HrAppealRequestFileUpload> hrAppealRequestFileUploadList) {
        this.hrAppealRequestFileUploadList = hrAppealRequestFileUploadList;
    }

    @XmlTransient
    public List<WfHrProcessed> getHrWorkFlowProccedList() {
        if (hrWorkFlowProccedList == null) {
            hrWorkFlowProccedList = new ArrayList<>();
        }
        return hrWorkFlowProccedList;
    }

    public void setHrWorkFlowProccedList(List<WfHrProcessed> hrWorkFlowProccedList) {
        this.hrWorkFlowProccedList = hrWorkFlowProccedList;
    }

    public void add(HrAppealRequestFileUpload hrAppealRequestFileUpload) {
        if (hrAppealRequestFileUpload.getAppealCaseId() != this) {
            this.getHrAppealRequestFileUploadList().add(hrAppealRequestFileUpload);
            hrAppealRequestFileUpload.setAppealCaseId(this);
        }
    }

}
