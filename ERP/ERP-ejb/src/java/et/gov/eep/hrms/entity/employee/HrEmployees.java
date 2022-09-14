 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.employee;

import et.gov.eep.fcms.entity.FmsTaxRegistration;
import et.gov.eep.fcms.entity.admin.FmsSubsidiaryLedger;
import et.gov.eep.hrms.entity.allowance.HrPayrollFiltEmForAll;
import et.gov.eep.hrms.entity.lookup.HrLuBanks;
import et.gov.eep.hrms.entity.lookup.HrLuTitles;
import et.gov.eep.hrms.entity.lookup.HrLuSalarySteps;
import et.gov.eep.hrms.entity.lookup.HrLuNationalities;
import et.gov.eep.hrms.entity.lookup.HrLuBankBranches;
import et.gov.eep.hrms.entity.lookup.HrLuReligions;
import et.gov.eep.hrms.entity.displine.HrDisciplineRequests;
import et.gov.eep.hrms.entity.insurance.HrInsurancePayment;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.hrms.entity.organization.HrJobTypes;
import et.gov.eep.hrms.entity.organization.HrSalaryScaleRanges;
import et.gov.eep.hrms.entity.payroll.HrPayrollMaintainBackPay;
import et.gov.eep.hrms.entity.payroll.HrPayrollMaintainEds;
import et.gov.eep.hrms.entity.payroll.HrPayrollMonTransactions;
import et.gov.eep.hrms.entity.recruitment.HrAdvertisements;
import et.gov.eep.hrms.entity.recruitment.HrRecruitmentRequests;
import et.gov.eep.hrms.entity.succession.HrSmSuccessorEvaluation;
import et.gov.eep.hrms.entity.training.HrTdAnnualTraParticipants;
import et.gov.eep.hrms.entity.transfer.HrEmpInternalHistory;
import et.gov.eep.mms.entity.MmsStoreInformation;
import et.gov.eep.pms.entity.PmsCreateProjects;

import et.gov.eep.prms.entity.PrmsBidSubmissionDetail;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FetchType;
import javax.persistence.FieldResult;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author user
 */
@Entity
@Table(name = "HR_EMPLOYEES")
@XmlRootElement
@NamedQueries//<editor-fold defaultstate="collapsed" desc="">
        ({
            @NamedQuery(name = "HrEmployees.findAll", query = "SELECT h FROM HrEmployees h"),
            @NamedQuery(name = "HrEmployees.findById", query = "SELECT h FROM HrEmployees h WHERE h.id = :id"),
            @NamedQuery(name = "HrEmployees.findActiveEmployees", query = "SELECT h FROM HrEmployees h WHERE h.empStatus =1 or h.empStatus = 2"),
            @NamedQuery(name = "HrEmployees.findActiveEmployeesByDeptJobs", query = "SELECT h FROM HrEmployees h WHERE h.empStatus !=0 AND h.empStatus != 6 AND h.deptId.depId=:depId and h.jobId.id=:jobid"),
            @NamedQuery(name = "HrEmployees.findByEmpId", query = "SELECT h FROM HrEmployees h WHERE h.empId = :empId"),
            @NamedQuery(name = "HrEmployees.findByEmpIdLike", query = "SELECT h FROM HrEmployees h WHERE UPPER(h.empId) LIKE :empId"),
            @NamedQuery(name = "HrEmployees.findByEmpIdLikeCont", query = "SELECT h FROM HrEmployees h WHERE UPPER(h.empId) LIKE :empId AND h.employmentType = :employmentType"),
            @NamedQuery(name = "HrEmployees.findByFnameEmpIdLike", query = "SELECT h FROM HrEmployees h WHERE UPPER(h.empId) LIKE :empId and UPPER(h.firstName) LIKE :firstName "),
            @NamedQuery(name = "HrEmployees.findByFirstName", query = "SELECT h FROM HrEmployees h WHERE h.firstName = :firstName"),
            @NamedQuery(name = "HrEmployees.findByMiddleName", query = "SELECT h FROM HrEmployees h WHERE h.middleName = :middleName"),
            @NamedQuery(name = "HrEmployees.findByLastName", query = "SELECT h FROM HrEmployees h WHERE h.lastName = :lastName"),
            @NamedQuery(name = "HrEmployees.findBySex", query = "SELECT h FROM HrEmployees h WHERE h.sex = :sex"),
            @NamedQuery(name = "HrEmployees.findByDob", query = "SELECT h FROM HrEmployees h WHERE h.dob = :dob"),
            @NamedQuery(name = "HrEmployees.findByMaritalStatus", query = "SELECT h FROM HrEmployees h WHERE h.maritalStatus = :maritalStatus"),
            @NamedQuery(name = "HrEmployees.findByTitleId", query = "SELECT h FROM HrEmployees h WHERE h.titleId = :titleId"),
            @NamedQuery(name = "HrEmployees.findByNationId", query = "SELECT h FROM HrEmployees h WHERE h.nationId = :nationId"),
            @NamedQuery(name = "HrEmployees.findByNationalityId", query = "SELECT h FROM HrEmployees h WHERE h.nationalityId = :nationalityId"),
            @NamedQuery(name = "HrEmployees.findByReligionId", query = "SELECT h FROM HrEmployees h WHERE h.religionId = :religionId"),
            @NamedQuery(name = "HrEmployees.findByHireDate", query = "SELECT h FROM HrEmployees h WHERE h.hireDate = :hireDate"),
            @NamedQuery(name = "HrEmployees.findByEmploymentType", query = "SELECT h FROM HrEmployees h WHERE h.employmentType = :employmentType"),
            @NamedQuery(name = "HrEmployees.findBySalaryStep", query = "SELECT h FROM HrEmployees h WHERE h.salaryStep = :salaryStep"),
            @NamedQuery(name = "HrEmployees.findByBasicSalary", query = "SELECT h FROM HrEmployees h WHERE h.basicSalary = :basicSalary"),
            @NamedQuery(name = "HrEmployees.findByGradeId", query = "SELECT h FROM HrEmployees h WHERE h.gradeId = :gradeId"),
            @NamedQuery(name = "HrEmployees.findByPensionRegNo", query = "SELECT h FROM HrEmployees h WHERE h.pensionRegNo = :pensionRegNo"),
            @NamedQuery(name = "HrEmployees.findByTinNo", query = "SELECT h FROM HrEmployees h WHERE h.tinNo = :tinNo"),
            @NamedQuery(name = "HrEmployees.findByBankBranch", query = "SELECT h FROM HrEmployees h WHERE h.bankBranch = :bankBranch"),
            @NamedQuery(name = "HrEmployees.findByBankName", query = "SELECT h FROM HrEmployees h WHERE h.bankName = :bankName"),
            @NamedQuery(name = "HrEmployees.findByAccountNo", query = "SELECT h FROM HrEmployees h WHERE h.accountNo = :accountNo"),
            @NamedQuery(name = "HrEmployees.findByHeight", query = "SELECT h FROM HrEmployees h WHERE h.height = :height"),
            @NamedQuery(name = "HrEmployees.findByWeight", query = "SELECT h FROM HrEmployees h WHERE h.weight = :weight"),
            @NamedQuery(name = "HrEmployees.findByEmpStatus", query = "SELECT h FROM HrEmployees h WHERE h.empStatus = :empStatus"),
            @NamedQuery(name = "HrEmployees.findByEmpDeptId", query = "SELECT h FROM HrEmployees h WHERE h.deptId.depId = :deptId"),
            @NamedQuery(name = "HrEmployees.findBySalaryRangeId", query = "SELECT h FROM HrEmployees h WHERE h.salaryStep = :salaryStep"),
            @NamedQuery(name = "HrEmployees.findByFirstNameLikeCont", query = "SELECT h FROM HrEmployees h WHERE UPPER(h.firstName) LIKE :firstName AND h.employmentType = :employmentType "),
            @NamedQuery(name = "HrEmployees.findByFirstNameLike", query = "SELECT h FROM HrEmployees h WHERE UPPER(h.firstName) LIKE :firstName")})
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="sqlResultSetMapping">
//sql result set mapping for mass salary increment
@SqlResultSetMapping(name = "HrSalaryIncrementDetailsResultSetMapping",
        entities = {
            @EntityResult(entityClass = HrEmployees.class, fields = {
                @FieldResult(name = "id", column = "ID"),
                @FieldResult(name = "empId", column = "EMP_ID"),
                @FieldResult(name = "firstName", column = "FULL_NAME"),
                @FieldResult(name = "middleName", column = "DEP_NAME"),
                @FieldResult(name = "lastName", column = "JOB_TITLE"),
                @FieldResult(name = "maritalStatus", column = "GRADE"),
                @FieldResult(name = "salaryStep", column = "PREV_SALARY_STEP"),
                @FieldResult(name = "basicSalary", column = "PREV_SALARY"),
                @FieldResult(name = "height", column = "SAL_INC_DETAIL_ID"),
                @FieldResult(name = "weight", column = "INCREMENT_BY"),

//                @FieldResult(name = "nationId", column = "SALARY_INCREMENT_ID"),
                @FieldResult(name = "isActing", column = "NEW_SALARY_STEP"),
                @FieldResult(name = "labourUnion", column = "NEW_SALARY"),
                @FieldResult(name = "pensionRegNo", column = "STATUS"),})})
//</editor-fold>

public class HrEmployees implements Serializable {
//<editor-fold defaultstate="collapsed" desc="Entity Attributes">

    @Lob
    @Column(name = "PHOTO")
    private byte[] photo;
    @Column(name = "NATION_ID")
    private Integer nationId;
    @Column(name = "BASIC_SALARY")
    private Double basicSalary;
    @Column(name = "EMP_STATUS")
    private Integer empStatus;
    @Column(name = "IS_ACTING")
    private Integer isActing;
    @Column(name = "LABOUR_UNION")
    private Integer labourUnion;
    @Column(name = "IS_OFF_DUTY")
    private Integer isOffDuty;
    @OneToMany(mappedBy = "empId")
    private List<HrPayrollFiltEmForAll> hrPayrollFiltEmForAllList;
    @OneToMany(mappedBy = "prepardby", cascade = CascadeType.ALL)
    private List<HrInsurancePayment> hrInsurancePaymentList;
    @OneToMany(mappedBy = "evaluatorId")
    private List<HrSmSuccessorEvaluation> hrSmSuccessorEvaluationList;
    @OneToMany(mappedBy = "empId")
    private List<HrSmSuccessorEvaluation> hrSmSuccessorEvaluationList1;
    @OneToMany(mappedBy = "empId", cascade = CascadeType.ALL)
    private List<HrTdAnnualTraParticipants> hrTdAnnualTraParticipantsList;
    @OneToMany(mappedBy = "storeAdmin")
    private List<MmsStoreInformation> mmsStoreInformationList;
    @OneToMany(mappedBy = "empId")
    private List<HrPayrollMaintainEds> hrPayrollMaintainEdsList;
    @OneToMany(mappedBy = "empId")
    private List<HrPayrollMonTransactions> hrPayrollMonTransactionsList;
    @Column(name = "CONTRACT_END_DATE")
    private String contractEndDate;
    @OneToMany(mappedBy = "id", cascade = CascadeType.ALL)
    private List<PmsCreateProjects> pmsCreateProjectsList;
    @OneToMany(mappedBy = "preparedBy", cascade = CascadeType.ALL)
    private List<HrAdvertisements> hrAdvertisementsList;
    @OneToMany(mappedBy = "empId", cascade = CascadeType.ALL)
    private List<HrEmpEducations> hrEmpEducationsList;
    @OneToMany(mappedBy = "empId", cascade = CascadeType.ALL)
    private List<FmsSubsidiaryLedger> fmsSubsidiaryLedgerList;
    @OneToMany(mappedBy = "empId", cascade = CascadeType.ALL)
    private List<HrEmpInternalHistory> hrEmpInternalHistory;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_EMPLOYEES_SEQ")
    @SequenceGenerator(name = "HR_EMPLOYEES_SEQ", sequenceName = "HR_EMPLOYEES_SEQ", allocationSize = 1)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "EMP_ID")
    private String empId;
    @Column(name = "FIRST_NAME")
    private String firstName;
    @Column(name = "MIDDLE_NAME")
    private String middleName;
    @Column(name = "LAST_NAME")
    private String lastName;
    @Column(name = "SEX")
    private String sex;
    @Column(name = "DOB")
    private String dob;
    @Column(name = "HIRE_DATE")
    private String hireDate;
    @Column(name = "HEIGHT")
    private Integer height;
    @Column(name = "WEIGHT")
    private Integer weight;
    @Column(name = "MARITAL_STATUS")
    private String maritalStatus;
    @JoinColumn(name = "TITLE_ID", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private HrLuTitles titleId;
    @JoinColumn(name = "NATIONALITY_ID", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private HrLuNationalities nationalityId;
    @JoinColumn(name = "RELIGION_ID", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private HrLuReligions religionId;
    @Column(name = "EMPLOYMENT_TYPE")
    private String employmentType;
    @JoinColumn(name = "SALARY_STEP", referencedColumnName = "ID")
    @OneToOne
    private HrLuSalarySteps salaryStep;
    @JoinColumn(name = "GRADE_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrSalaryScaleRanges gradeId;
    @Column(name = "PENSION_REG_NO")
    private String pensionRegNo;
    @Column(name = "TIN_NO")
    private String tinNo;
    @JoinColumn(name = "BANK_BRANCH", referencedColumnName = "ID")
    @ManyToOne
    private HrLuBankBranches bankBranch;
    @JoinColumn(name = "BANK_NAME", referencedColumnName = "ID")
    @ManyToOne
    private HrLuBanks bankName;
    @Column(name = "ACCOUNT_NO")
    private String accountNo;
    @JoinColumn(name = "DEPT_ID", referencedColumnName = "DEP_ID")
    @ManyToOne
    private HrDepartments deptId;
    @JoinColumn(name = "JOB_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrJobTypes jobId;
    @OneToMany(mappedBy = "empId", cascade = CascadeType.ALL)
    private List<HrEmpLanguages> hrEmpLanguagesList;
    @OneToMany(mappedBy = "empId", cascade = CascadeType.ALL)
    private List<HrEmpFamilies> hrEmpFamiliesList = new ArrayList<>();
    @OneToMany(mappedBy = "empId", cascade = CascadeType.ALL)
    private List<HrEmpAddresses> hrEmpAddressesList;
    @OneToMany(mappedBy = "empId", cascade = CascadeType.ALL)
    private List<HrEmpTrainings> hrEmpTrainingsList;
    @OneToMany(mappedBy = "empId", cascade = CascadeType.ALL)
    private List<HrEmpExperiences> hrEmpExperiencesList;
    @OneToMany(mappedBy = "empId", cascade = CascadeType.ALL)
    private List<HrEmpSkill> hrEmpSkillList;
    @OneToMany(mappedBy = "empId", cascade = CascadeType.ALL)
    private List<HrEmpReferences> hrEmpReferencesList;
    @OneToMany(mappedBy = "empId", cascade = CascadeType.ALL)
    private List<HrEmpFiles> hrEmpFilesList;
    @OneToMany(mappedBy = "empId", cascade = CascadeType.ALL)
    private List<HrEmpMemberships> hrEmpMembershipsesList;
    @OneToMany(mappedBy = "empId")
    private List<HrEmpReferencess> hrEmpReferencessList;
    @OneToMany(mappedBy = "empId", cascade = CascadeType.ALL)
    private List<HrEmpContracts> hrEmpContractsList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employeeId", fetch = FetchType.LAZY)
    private List<PrmsBidSubmissionDetail> prmsBidSubmissionDetails;
    @OneToMany(mappedBy = "empId")
    private List<HrPayrollMaintainBackPay> hrPayrollMaintainBackPayList;
    @OneToMany(mappedBy = "requesterId")
    private List<HrRecruitmentRequests> hrRecruitmentRequestsList;
    @OneToMany(mappedBy = "requesterId")
    private List<HrDisciplineRequests> hrDisciplineRequestsList;
    @OneToMany(mappedBy = "offenderId")
    private List<HrDisciplineRequests> hrDisciplineRequestsList1;
    @OneToMany(mappedBy = "casherId")
    private List<FmsTaxRegistration> fmsTaxRegistrationList;
//</editor-fold>
    @Transient
    private String Col_Value;

    public String getCol_Value() {
        return Col_Value;
    }

    public void setCol_Value(String Col_Value) {
        this.Col_Value = Col_Value;
    }

    /**
     *
     */
    public HrEmployees() {
    }

    /**
     *
     * @param id
     */
    public HrEmployees(Integer id) {
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
    public String getEmpId() {
        return empId;
    }

    /**
     *
     * @param empId
     */
    public void setEmpId(String empId) {
        this.empId = empId;
    }

    /**
     *
     * @return
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     *
     * @param firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     *
     * @return
     */
    public String getMiddleName() {
        return middleName;
    }

    /**
     *
     * @param middleName
     */
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    /**
     *
     * @return
     */
    public String getLastName() {
        return lastName;
    }

    /**
     *
     * @param lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     *
     * @return
     */
    public String getSex() {
        return sex;
    }

    /**
     *
     * @param sex
     */
    public void setSex(String sex) {
        this.sex = sex;
    }

    /**
     *
     * @return
     */
    public String getMaritalStatus() {
        return maritalStatus;
    }

    /**
     *
     * @param maritalStatus
     */
    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    /**
     *
     * @return
     */
    public HrLuTitles getTitleId() {
        return titleId;
    }

    /**
     *
     * @param titleId
     */
    public void setTitleId(HrLuTitles titleId) {
        this.titleId = titleId;
    }

    /**
     *
     * @return
     */
    public Integer getNationId() {
        return nationId;
    }

    /**
     *
     * @param nationId
     */
    public void setNationId(Integer nationId) {
        this.nationId = nationId;
    }

    /**
     *
     * @return
     */
    public HrLuNationalities getNationalityId() {
        return nationalityId;
    }

    /**
     *
     * @param nationalityId
     */
    public void setNationalityId(HrLuNationalities nationalityId) {
        this.nationalityId = nationalityId;
    }

    /**
     *
     * @return
     */
    public HrLuReligions getReligionId() {
        return religionId;
    }

    /**
     *
     * @param religionId
     */
    public void setReligionId(HrLuReligions religionId) {
        this.religionId = religionId;
    }

    /**
     *
     * @return
     */
    public String getHireDate() {
        return hireDate;
    }

    /**
     *
     * @param hireDate
     */
    public void setHireDate(String hireDate) {
        this.hireDate = hireDate;
    }

    /**
     *
     * @return
     */
    public String getEmploymentType() {
        return employmentType;
    }

    /**
     *
     * @param employmentType
     */
    public void setEmploymentType(String employmentType) {
        this.employmentType = employmentType;
    }

    /**
     *
     * @return
     */
    public HrLuSalarySteps getSalaryStep() {
//        if (salaryStep == null) {
//            salaryStep = new HrLuSalarySteps();
//        }
        return salaryStep;
    }

    /**
     *
     * @param salaryStep
     */
    public void setSalaryStep(HrLuSalarySteps salaryStep) {
        this.salaryStep = salaryStep;
    }

    /**
     *
     * @return
     */
    public Double getBasicSalary() {
        return basicSalary;
    }

    /**
     *
     * @param basicSalary
     */
    public void setBasicSalary(Double basicSalary) {
        this.basicSalary = basicSalary;
    }

    /**
     *
     * @return
     */
    public HrSalaryScaleRanges getGradeId() {
        return gradeId;
    }

    /**
     *
     * @param gradeId
     */
    public void setGradeId(HrSalaryScaleRanges gradeId) {
        this.gradeId = gradeId;
    }

    /**
     *
     * @return
     */
    public String getPensionRegNo() {
        return pensionRegNo;
    }

    /**
     *
     * @param pensionRegNo
     */
    public void setPensionRegNo(String pensionRegNo) {
        this.pensionRegNo = pensionRegNo;
    }

    /**
     *
     * @return
     */
    public String getTinNo() {
        return tinNo;
    }

    /**
     *
     * @param tinNo
     */
    public void setTinNo(String tinNo) {
        this.tinNo = tinNo;
    }

    /**
     *
     * @return
     */
    public HrLuBankBranches getBankBranch() {
        return bankBranch;
    }

    /**
     *
     * @param bankBranch
     */
    public void setBankBranch(HrLuBankBranches bankBranch) {
        this.bankBranch = bankBranch;
    }

    /**
     *
     * @return
     */
    public Integer getHeight() {
        return height;
    }

    /**
     *
     * @param height
     */
    public void setHeight(Integer height) {
        this.height = height;
    }

    /**
     *
     * @return
     */
    public Integer getWeight() {
        return weight;
    }

    /**
     *
     * @param weight
     */
    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    /**
     *
     * @return
     */
    public Integer getIsActing() {
        return isActing;
    }

    /**
     *
     * @param isActing
     */
    public void setIsActing(Integer isActing) {
        this.isActing = isActing;
    }

    /**
     *
     * @return
     */
    public HrLuBanks getBankName() {
        return bankName;
    }

    /**
     *
     * @param bankName
     */
    public void setBankName(HrLuBanks bankName) {
        this.bankName = bankName;
    }

    /**
     *
     * @return
     */
    public String getAccountNo() {
        return accountNo;
    }

    /**
     *
     * @param accountNo
     */
    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    /**
     *
     * @return
     */
    public Integer getEmpStatus() {
        return empStatus;
    }

    /**
     *
     * @param empStatus
     */
    public void setEmpStatus(Integer empStatus) {
        this.empStatus = empStatus;
    }

    /**
     *
     * @return
     */
    public HrDepartments getDeptId() {
        return deptId;
    }

    /**
     *
     * @param deptId
     */
    public void setDeptId(HrDepartments deptId) {
        this.deptId = deptId;
    }

    /**
     *
     * @return
     */
    public HrJobTypes getJobId() {
        return jobId;
    }

    /**
     *
     * @param jobId
     */
    public void setJobId(HrJobTypes jobId) {
        this.jobId = jobId;
    }

    public Integer getLabourUnion() {
        return labourUnion;
    }

    public void setLabourUnion(Integer labourUnion) {
        this.labourUnion = labourUnion;
    }

    public String getContractEndDate() {
        return contractEndDate;
    }

    public void setContractEndDate(String contractEndDate) {
        this.contractEndDate = contractEndDate;
    }

    public List<PmsCreateProjects> getPmsCreateProjectsList() {
        return pmsCreateProjectsList;
    }

    public void setPmsCreateProjectsList(List<PmsCreateProjects> pmsCreateProjectsList) {
        this.pmsCreateProjectsList = pmsCreateProjectsList;
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
        if (!(object instanceof HrEmployees)) {
            return false;
        }
        HrEmployees other = (HrEmployees) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return firstName;
    }

    /**
     *
     * @return
     */
    @XmlTransient
    public List<HrDisciplineRequests> getHrDisciplineRequestsList() {
        return hrDisciplineRequestsList;
    }

    /**
     *
     * @param hrDisciplineRequestsList
     */
    public void setHrDisciplineRequestsList(List<HrDisciplineRequests> hrDisciplineRequestsList) {
        this.hrDisciplineRequestsList = hrDisciplineRequestsList;
    }

    /**
     *
     * @return
     */
    @XmlTransient
    public List<HrDisciplineRequests> getHrDisciplineRequestsList1() {
        return hrDisciplineRequestsList1;
    }

    /**
     *
     * @param hrDisciplineRequestsList1
     */
    public void setHrDisciplineRequestsList1(List<HrDisciplineRequests> hrDisciplineRequestsList1) {
        this.hrDisciplineRequestsList1 = hrDisciplineRequestsList1;
    }

    /**
     *
     * @return
     */
    /**
     *
     * @return
     */
    @XmlTransient
    public List<HrEmpLanguages> getHrEmpLanguagesList() {
        if (hrEmpLanguagesList == null) {
            hrEmpLanguagesList = new ArrayList<>();
        }
        return hrEmpLanguagesList;
    }

    /**
     *
     * @param hrEmpLanguagesList
     */
    public void setHrEmpLanguagesList(List<HrEmpLanguages> hrEmpLanguagesList) {
        this.hrEmpLanguagesList = hrEmpLanguagesList;
    }

    /**
     *
     * @param hrEmpLanguages
     */
    public void addToEmpLang(HrEmpLanguages hrEmpLanguages) {
        if (hrEmpLanguages.getEmpId() != this) {
            this.getHrEmpLanguagesList().add(hrEmpLanguages);
            hrEmpLanguages.setEmpId(this);
        }
    }

    /**
     *
     * @return
     */
    /**
     *
     * @param hrEmpEducations
     */
    public void addToEmpEducation(HrEmpEducations hrEmpEducations) {
        if (hrEmpEducations.getEmpId() != this) {
            this.getHrEmpEducationsList().add(hrEmpEducations);
            hrEmpEducations.setEmpId(this);
        }
    }

    /**
     *
     * @return
     */
    @XmlTransient
    public List<HrEmpFamilies> getHrEmpFamiliesList() {
//         if (hrEmpFamiliesList == null) {
//            hrEmpFamiliesList = new ArrayList<>();
//        }
        return hrEmpFamiliesList;
    }

    /**
     *
     * @param hrEmpFamiliesList
     */
    public void setHrEmpFamiliesList(List<HrEmpFamilies> hrEmpFamiliesList) {
        this.hrEmpFamiliesList = hrEmpFamiliesList;
    }

    /**
     *
     * @param hrEmpFamilies
     */
    public void addToEmpFamily(HrEmpFamilies hrEmpFamilies) {
        if (hrEmpFamilies.getEmpId() != this) {
            this.getHrEmpFamiliesList().add(hrEmpFamilies);
            hrEmpFamilies.setEmpId(this);
        }
    }

    /**
     *
     * @param hrEmpTrainingList
     */
    public void setHrEmpTrainingList(List<HrEmpTrainings> hrEmpTrainingList) {
        this.hrEmpTrainingsList = hrEmpTrainingList;
    }

    /**
     *
     * @param hrEmpTraining
     */
    public void addToEmpTrain(HrEmpTrainings hrEmpTraining) {
        if (hrEmpTraining.getEmpId() != this) {
            this.getHrEmpTrainingsList().add(hrEmpTraining);
            hrEmpTraining.setEmpId(this);
        }
    }

    //Education
    /**
     *
     * @return
     */
    //Expriance
    /**
     *
     * @return
     */
    @XmlTransient
    public List<FmsTaxRegistration> getFmsTaxRegistrationList() {
        if (fmsTaxRegistrationList == null) {
            fmsTaxRegistrationList = new ArrayList<>();
        }
        return fmsTaxRegistrationList;
    }

    public void setFmsTaxRegistrationList(List<FmsTaxRegistration> fmsTaxRegistrationList) {
        this.fmsTaxRegistrationList = fmsTaxRegistrationList;
    }

    @XmlTransient
    public List<HrEmpExperiences> getHrEmpExpriancesList() {
        if (hrEmpExperiencesList == null) {
            hrEmpExperiencesList = new ArrayList<>();
        }
        return hrEmpExperiencesList;
    }

    /**
     *
     * @param hrEmpExperiencesList
     */
    public void setHrEmpExpriancesList(List<HrEmpExperiences> hrEmpExperiencesList) {
        this.hrEmpExperiencesList = hrEmpExperiencesList;
    }

    /**
     *
     * @param hrEmpExpriances
     */
    public void addToEmpExpriance(HrEmpExperiences hrEmpExpriances) {
        if (hrEmpExpriances.getEmpId() != this) {
            this.getHrEmpExperiencesList().add(hrEmpExpriances);
            hrEmpExpriances.setEmpId(this);
        }
    }
    //Skill

    /**
     *
     * @return
     */
    @XmlTransient
    public List<HrEmpSkill> getHrEmpSkillList() {
        if (hrEmpSkillList == null) {
            hrEmpSkillList = new ArrayList<>();
        }
        return hrEmpSkillList;
    }

    /**
     *
     * @param hrEmpSkillList
     */
    public void setHrEmpSkillList(List<HrEmpSkill> hrEmpSkillList) {
        this.hrEmpSkillList = hrEmpSkillList;
    }

    /**
     *
     * @param hrEmpSkill
     */
    public void addToEmpSkill(HrEmpSkill hrEmpSkill) {
        if (hrEmpSkill.getEmpId() != this) {
            this.getHrEmpSkillList().add(hrEmpSkill);
            hrEmpSkill.setEmpId(this);
        }
    }

    //memberships
    /**
     *
     * @return hrEmpMembershipsesList
     */
    public List<HrEmpMemberships> getHrEmpMembershipsesList() {
        if (hrEmpMembershipsesList == null) {
            hrEmpMembershipsesList = new ArrayList<>();
        }
        return hrEmpMembershipsesList;
    }

    public void setHrEmpMembershipsesList(List<HrEmpMemberships> hrEmpMembershipsesList) {
        this.hrEmpMembershipsesList = hrEmpMembershipsesList;
    }

    /**
     *
     * @param hrEmpMemberships
     */
    public void addToEmpMembershipe(HrEmpMemberships hrEmpMemberships) {
        if (hrEmpMemberships.getEmpId() != this) {
            this.getHrEmpMembershipsesList().add(hrEmpMemberships);
            hrEmpMemberships.setEmpId(this);
        }
    }

    //contrats
    /**
     *
     * @return hrEmpContractsList
     */
    public List<HrEmpContracts> getHrEmpContractsList() {

        return hrEmpContractsList;
    }

    public void setHrEmpContractsList(List<HrEmpContracts> hrEmpContractsList) {
        this.hrEmpContractsList = hrEmpContractsList;
    }

    /**
     *
     * @param hrEmpContracts
     */
    public void addToEmpContrat(HrEmpContracts hrEmpContracts) {
        if (hrEmpContracts.getEmpId() != this) {
            this.getHrEmpContractsList().add(hrEmpContracts);
            hrEmpContracts.setEmpId(this);
        }
    }

    //Reference
    /**
     *
     * @return
     */
    @XmlTransient
    public List<HrEmpReferences> getHrEmpReferencesList() {
        if (hrEmpReferencesList == null) {
            hrEmpReferencesList = new ArrayList<>();
        }
        return hrEmpReferencesList;
    }

    /**
     *
     * @param hrEmpReferencesList
     */
    public void setHrEmpReferencesList(List<HrEmpReferences> hrEmpReferencesList) {
        this.hrEmpReferencesList = hrEmpReferencesList;
    }

    /**
     *
     * @param hrEmpReferences
     */
    public void addToEmpReference(HrEmpReferences hrEmpReferences) {
        if (hrEmpReferences.getEmpId() != this) {
            this.getHrEmpReferencesList().add(hrEmpReferences);
            hrEmpReferences.setEmpId(this);
        }
    }

    /**
     *
     * @return
     */
    @XmlTransient
    public List<HrRecruitmentRequests> getHrRecruitmentRequestsList() {
        return hrRecruitmentRequestsList;
    }

    /**
     *
     * @param hrRecruitmentRequestsList
     */
    public void setHrRecruitmentRequestsList(List<HrRecruitmentRequests> hrRecruitmentRequestsList) {
        this.hrRecruitmentRequestsList = hrRecruitmentRequestsList;
    }

    /**
     *
     * @return
     */
    @XmlTransient
    public List<HrEmpFiles> getHrEmpFilesList() {
        return hrEmpFilesList;
    }

    /**
     *
     * @param hrEmpFilesList
     */
    public void setHrEmpFilesList(List<HrEmpFiles> hrEmpFilesList) {
        this.hrEmpFilesList = hrEmpFilesList;
    }

    /**
     *
     * @return
     */
    @XmlTransient
    public List<HrEmpAddresses> getHrEmpAddressesList() {
        if (hrEmpAddressesList == null) {
            hrEmpAddressesList = new ArrayList<>();
        }
        return hrEmpAddressesList;
    }

    /**
     *
     * @param hrEmpAddressesList
     */
    public void setHrEmpAddressesList(List<HrEmpAddresses> hrEmpAddressesList) {
        this.hrEmpAddressesList = hrEmpAddressesList;
    }

    /**
     *
     * @param hrEmpAddresses
     */
    public void addToEmpAdress(HrEmpAddresses hrEmpAddresses) {
        if (hrEmpAddresses.getEmpId() != this) {
            this.getHrEmpAddressesList().add(hrEmpAddresses);
            hrEmpAddresses.setEmpId(this);
        }
    }

    /**
     *
     * @return
     */
    @XmlTransient
    public List<HrEmpReferencess> getHrEmpReferencessList() {
        if (hrEmpReferencessList == null) {
            hrEmpReferencessList = new ArrayList<>();
        }
        return hrEmpReferencessList;
    }

    public void setHrEmpReferencessList(List<HrEmpReferencess> hrEmpReferencessList) {
        this.hrEmpReferencessList = hrEmpReferencessList;
    }

    @XmlTransient
    public List<HrSmSuccessorEvaluation> getHrSmSuccessorEvaluationList() {
        return hrSmSuccessorEvaluationList;
    }

    public void setHrSmSuccessorEvaluationList(List<HrSmSuccessorEvaluation> hrSmSuccessorEvaluationList) {
        this.hrSmSuccessorEvaluationList = hrSmSuccessorEvaluationList;
    }

    @XmlTransient
    public List<HrSmSuccessorEvaluation> getHrSmSuccessorEvaluationList1() {
        return hrSmSuccessorEvaluationList1;
    }

    public void setHrSmSuccessorEvaluationList1(List<HrSmSuccessorEvaluation> hrSmSuccessorEvaluationList1) {
        this.hrSmSuccessorEvaluationList1 = hrSmSuccessorEvaluationList1;
    }

    @XmlTransient
    public List<FmsSubsidiaryLedger> getFmsSubsidiaryLedgerList() {
        return fmsSubsidiaryLedgerList;
    }

    public void setFmsSubsidiaryLedgerList(List<FmsSubsidiaryLedger> fmsSubsidiaryLedgerList) {
        this.fmsSubsidiaryLedgerList = fmsSubsidiaryLedgerList;
    }

    @XmlTransient
    public List<HrEmpExperiences> getHrEmpExperiencesList() {
        if (hrEmpExperiencesList == null) {
            hrEmpExperiencesList = new ArrayList<>();
        }
        return hrEmpExperiencesList;
    }

    public void setHrEmpExperiencesList(List<HrEmpExperiences> hrEmpExperiencesList) {
        this.hrEmpExperiencesList = hrEmpExperiencesList;
    }

    @XmlTransient
    public List<HrEmpTrainings> getHrEmpTrainingsList() {
        if (hrEmpTrainingsList == null) {
            hrEmpTrainingsList = new ArrayList<>();
        }
        return hrEmpTrainingsList;
    }

    public void setHrEmpTrainingsList(List<HrEmpTrainings> hrEmpTrainingsList) {
        this.hrEmpTrainingsList = hrEmpTrainingsList;
    }

    public void addToEmpEducation(HrEmpTrainings hrEmpTrainings) {
        System.out.println("this is from addToCandidateTrainings");
        if (hrEmpTrainings.getEmpId() != this) {
            this.getHrEmpTrainingsList().add(hrEmpTrainings);
            hrEmpTrainings.setEmpId(this);
        }
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    @XmlTransient
    public List<HrPayrollMaintainBackPay> getHrPayrollMaintainBackPayList() {
        return hrPayrollMaintainBackPayList;
    }

    public void setHrPayrollMaintainBackPayList(List<HrPayrollMaintainBackPay> hrPayrollMaintainBackPayList) {
        this.hrPayrollMaintainBackPayList = hrPayrollMaintainBackPayList;
    }

    @XmlTransient
    public List<HrEmpEducations> getHrEmpEducationsList() {
        if (hrEmpEducationsList == null) {
            hrEmpEducationsList = new ArrayList<>();
        }
        return hrEmpEducationsList;
    }

    public void setHrEmpEducationsList(List<HrEmpEducations> hrEmpEducationsList) {
        this.hrEmpEducationsList = hrEmpEducationsList;
    }
//

    @XmlTransient
    public List<HrPayrollMonTransactions> getHrPayrollMonTransactionsList() {
        return hrPayrollMonTransactionsList;
    }

    public void setHrPayrollMonTransactionsList(List<HrPayrollMonTransactions> hrPayrollMonTransactionsList) {
        this.hrPayrollMonTransactionsList = hrPayrollMonTransactionsList;
    }

    @XmlTransient
    public List<HrPayrollMaintainEds> getHrPayrollMaintainEdsList() {
        return hrPayrollMaintainEdsList;
    }

    public void setHrPayrollMaintainEdsList(List<HrPayrollMaintainEds> hrPayrollMaintainEdsList) {
        this.hrPayrollMaintainEdsList = hrPayrollMaintainEdsList;
    }

    @XmlTransient
    public List<MmsStoreInformation> getMmsStoreInformationList() {
        return mmsStoreInformationList;
    }

    public void setMmsStoreInformationList(List<MmsStoreInformation> mmsStoreInformationList) {
        this.mmsStoreInformationList = mmsStoreInformationList;
    }

    @XmlTransient
    public List<HrTdAnnualTraParticipants> getHrTdAnnualTraParticipantsList() {
        return hrTdAnnualTraParticipantsList;
    }

    public void setHrTdAnnualTraParticipantsList(List<HrTdAnnualTraParticipants> hrTdAnnualTraParticipantsList) {
        this.hrTdAnnualTraParticipantsList = hrTdAnnualTraParticipantsList;
    }

    @XmlTransient
    public List<PrmsBidSubmissionDetail> getPrmsBidSubmissionDetails() {
        if (prmsBidSubmissionDetails == null) {
            prmsBidSubmissionDetails = new ArrayList<>();
        }
        return prmsBidSubmissionDetails;
    }

    public void setPrmsBidSubmissionDetails(List<PrmsBidSubmissionDetail> prmsBidSubmissionDetails) {
        this.prmsBidSubmissionDetails = prmsBidSubmissionDetails;
    }

    @XmlTransient

    //<editor-fold defaultstate="collapsed" desc="transient">
    @Transient
    boolean approved;

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public Integer getIsOffDuty() {
        return isOffDuty;
    }

    public void setIsOffDuty(Integer isOffDuty) {
        this.isOffDuty = isOffDuty;
    }

    @XmlTransient
    public List<HrInsurancePayment> getHrInsurancePaymentList() {
        return hrInsurancePaymentList;
    }

    public void setHrInsurancePaymentList(List<HrInsurancePayment> hrInsurancePaymentList) {
        this.hrInsurancePaymentList = hrInsurancePaymentList;
    }

    @XmlTransient
    public List<HrEmpInternalHistory> getHrEmpInternalHistory() {
        return hrEmpInternalHistory;
    }

    public void setHrEmpInternalHistory(List<HrEmpInternalHistory> hrEmpInternalHistory) {
        this.hrEmpInternalHistory = hrEmpInternalHistory;
    }

    @Transient
    private String Experiance;

    @Transient
    private double Bonus;
    @Transient
    private String BudgetYear;
    @Transient
    private double amount;

    public double getBonus() {
        return Bonus;
    }

    public void setBonus(double Bonus) {
        this.Bonus = Bonus;
    }

    public String getExperiance() {
        return Experiance;
    }

    public void setExperiance(String Experiance) {
        this.Experiance = Experiance;
    }

    public String getBudgetYear() {
        return BudgetYear;
    }

    public void setBudgetYear(String BudgetYear) {
        this.BudgetYear = BudgetYear;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    @XmlTransient
    public List<HrPayrollFiltEmForAll> getHrPayrollFiltEmForAllList() {
        return hrPayrollFiltEmForAllList;
    }

    public void setHrPayrollFiltEmForAllList(List<HrPayrollFiltEmForAll> hrPayrollFiltEmForAllList) {
        this.hrPayrollFiltEmForAllList = hrPayrollFiltEmForAllList;
    }

}
