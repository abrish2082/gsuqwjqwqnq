
package et.gov.eep.mms.entity;

import et.gov.eep.commonApplications.entity.WfMmsProcessed;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author w_station
 */
@Entity
@Table(name = "MMS_INSURANCE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MmsInsurance.findAll", query = "SELECT m FROM MmsInsurance m"),
    @NamedQuery(name = "MmsInsurance.findByInsId", query = "SELECT m FROM MmsInsurance m WHERE m.insId = :insId"),
    @NamedQuery(name = "MmsInsurance.findByAuthorizedBy", query = "SELECT m FROM MmsInsurance m WHERE m.authorizedBy = :authorizedBy"),
    @NamedQuery(name = "MmsInsurance.findByAuthorizedDate", query = "SELECT m FROM MmsInsurance m WHERE m.authorizedDate = :authorizedDate"),
    @NamedQuery(name = "MmsInsurance.findInsListByWfStatus", query = "SELECT m FROM MmsInsurance m WHERE m.insStatus =:insStatus"),
     @NamedQuery(name = "MmsInsurance.findAllByPreparerId", query = "SELECT m FROM MmsInsurance m WHERE m.authorizedBy = :authorizedBy"),
    @NamedQuery(name = "MmsInsurance.findByInsIdMaximum", query = "SELECT m FROM MmsInsurance m WHERE m.insId = (SELECT Max(c.insId) FROM MmsInsurance c)"),
    @NamedQuery(name = "MmsInsurance.findByInsNoLike", query = "SELECT m FROM MmsInsurance m WHERE m.insNo LIKE :insNo"),
    @NamedQuery(name = "MmsInsurance.findByAllParameters", query = "SELECT m FROM MmsInsurance m WHERE m.insNo LIKE :insNo"),
    @NamedQuery(name = "MmsInsurance.findByDepartment", query = "SELECT m FROM MmsInsurance m WHERE m.department = :department"),
    @NamedQuery(name = "MmsInsurance.findByInsNo", query = "SELECT m FROM MmsInsurance m WHERE m.insNo = :insNo"),
    @NamedQuery(name = "MmsInsurance.findByIssuedBy", query = "SELECT m FROM MmsInsurance m WHERE m.issuedBy = :issuedBy"),
    @NamedQuery(name = "MmsInsurance.findByIssuedDate", query = "SELECT m FROM MmsInsurance m WHERE m.issuedDate = :issuedDate"),
    @NamedQuery(name = "MmsInsurance.findByRegion", query = "SELECT m FROM MmsInsurance m WHERE m.region = :region"),
    @NamedQuery(name = "MmsInsurance.findByRegistrationDate", query = "SELECT m FROM MmsInsurance m WHERE m.registrationDate = :registrationDate"),
    @NamedQuery(name = "MmsInsurance.findByToBeIssued", query = "SELECT m FROM MmsInsurance m WHERE m.toBeIssued = :toBeIssued")})
public class MmsInsurance implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "INSURANCE_SEQ")
    @SequenceGenerator(name = "INSURANCE_SEQ", sequenceName = "INSURANCE_SEQ", allocationSize = 1)
    @Column(name = "INS_ID", nullable = false, precision = 0, scale = -127)
    private Integer insId;
    @Column(name = "AUTHORIZED_BY")
    private Integer authorizedBy;
    @Column(name = "AUTHORIZED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date authorizedDate;
    @Size(max = 30)
    @Column(name = "INS_NO", length = 30)
    private String insNo;
    @Size(max = 30)
    @Column(name = "ISSUED_BY", length = 30)
    private String issuedBy;
    @Column(name = "ISSUED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date issuedDate;
    @Size(max = 30)
    @Column(name = "REGION", length = 30)
    private String region;
    @Column(name = "REGISTRATION_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date registrationDate;
    @Size(max = 30)
    @Column(name = "TO_BE_ISSUED", length = 30)
    private String toBeIssued;
    @Size(max = 30)
    @Column(name = "REG_DATE")
    private String regDate;
    @Size(max = 30)
    @Column(name = "ISSUED2_DATE")
    private String issued2Date;
    @Size(max = 30)
    @Column(name = "AUTHORIZED2_DATE")
    private String authorized2Date;
    @Column(name = "INS_STATUS")
    private Integer insStatus;
    @JoinColumn(name = "DEPARTMENT", referencedColumnName = "DEP_ID")
    @ManyToOne
    private HrDepartments department;
    @OneToMany(mappedBy = "insId", cascade = CascadeType.ALL)
    private List<MmsInsuranceDetail> mmsInsuranceDetailList;
    @OneToMany(mappedBy = "insId", cascade = CascadeType.ALL)
    private List<WfMmsProcessed> insuranceList;
   @Transient
    private String columnName;
    @Transient
    private String columnValue;
    public MmsInsurance() {
    }

    public MmsInsurance(Integer insId) {
        this.insId = insId;
    }

    public Integer getInsId() {
        return insId;
    }

    public void setInsId(Integer insId) {
        this.insId = insId;
    }

    public Integer getAuthorizedBy() {
        return authorizedBy;
    }

    public void setAuthorizedBy(Integer authorizedBy) {
        this.authorizedBy = authorizedBy;
    }

    public Date getAuthorizedDate() {
        return authorizedDate;
    }

    public void setAuthorizedDate(Date authorizedDate) {
        this.authorizedDate = authorizedDate;
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

    public HrDepartments getDepartment() {
        return department;
    }

    public void setDepartment(HrDepartments department) {
        this.department = department;
    }

    public String getInsNo() {
        return insNo;
    }

    public void setInsNo(String insNo) {
        this.insNo = insNo;
    }

    public String getIssuedBy() {
        return issuedBy;
    }

    public void setIssuedBy(String issuedBy) {
        this.issuedBy = issuedBy;
    }

    public Integer getInsStatus() {
        return insStatus;
    }

    public void setInsStatus(Integer insStatus) {
        this.insStatus = insStatus;
    }

    public Date getIssuedDate() {
        return issuedDate;
    }

    public void setIssuedDate(Date issuedDate) {
        this.issuedDate = issuedDate;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public String getIssued2Date() {
        return issued2Date;
    }

    public void setIssued2Date(String issued2Date) {
        this.issued2Date = issued2Date;
    }

    public String getAuthorized2Date() {
        return authorized2Date;
    }

    public void setAuthorized2Date(String authorized2Date) {
        this.authorized2Date = authorized2Date;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getToBeIssued() {
        return toBeIssued;
    }

    public void setToBeIssued(String toBeIssued) {
        this.toBeIssued = toBeIssued;
    }

    @XmlTransient
    public List<WfMmsProcessed> getInsuranceList() {
        if (insuranceList == null) {
            insuranceList = new ArrayList<>();
        }
        return insuranceList;
    }

    public void setInsuranceList(List<WfMmsProcessed> insuranceList) {
        this.insuranceList = insuranceList;
    }

    @XmlTransient
    public List<MmsInsuranceDetail> getMmsInsuranceDetailList() {
        if (mmsInsuranceDetailList == null) {
            mmsInsuranceDetailList = new ArrayList<>();
        }
        return mmsInsuranceDetailList;
    }

    public void setMmsInsuranceDetailList(List<MmsInsuranceDetail> mmsInsuranceDetailList) {
        this.mmsInsuranceDetailList = mmsInsuranceDetailList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (insId != null ? insId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof MmsInsurance)) {
            return false;
        }
        MmsInsurance other = (MmsInsurance) object;
        if ((this.insId == null && other.insId != null) || (this.insId != null && !this.insId.equals(other.insId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return insNo;
    }

   public void addInsuranceDetail(MmsInsuranceDetail mmsInsuranceDetail) {
        System.out.println("-------------Inside Entity-------addInsuranceDetail");
        if (mmsInsuranceDetail.getInsId() != this) {
            this.getMmsInsuranceDetailList().add(mmsInsuranceDetail);
            mmsInsuranceDetail.setInsId(this);
        }

    }
   public void addInsuranceDetails(MmsInsuranceDetail insuranceDetailEntity) {
        System.out.println("-------------Inside Entity-------addInsuranceDetail");
        if (insuranceDetailEntity.getInsId() != this) {
            this.getMmsInsuranceDetailList().add(insuranceDetailEntity);
            insuranceDetailEntity.setInsId(this);
        }

    }

}
