/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.recruitment;

import et.gov.eep.hrms.entity.address.HrAddresses;
import et.gov.eep.hrms.entity.lookup.HrLuNationalities;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author user
 */
@Entity
@Table(name = "HR_CANDIDIATES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrCandidiates.findAll", query = "SELECT h FROM HrCandidiates h"),
    @NamedQuery(name = "HrCandidiates.findById", query = "SELECT h FROM HrCandidiates h WHERE h.id = :id"),
    @NamedQuery(name = "HrCandidiates.findByIdLike", query = "SELECT h FROM HrCandidiates h WHERE h.id Like :id"),
    @NamedQuery(name = "HrCandidiates.findByFirstName", query = "SELECT h FROM HrCandidiates h WHERE h.firstName = :firstName"),
    @NamedQuery(name = "HrCandidiates.findByFirstNameLike", query = "SELECT h FROM HrCandidiates h WHERE UPPER(h.firstName) Like :firstName"),
    @NamedQuery(name = "HrCandidiates.findByMiddleName", query = "SELECT h FROM HrCandidiates h WHERE h.middleName = :middleName"),
    @NamedQuery(name = "HrCandidiates.findByLastName", query = "SELECT h FROM HrCandidiates h WHERE h.lastName = :lastName"),
    @NamedQuery(name = "HrCandidiates.findBySex", query = "SELECT h FROM HrCandidiates h WHERE h.sex = :sex"),
    @NamedQuery(name = "HrCandidiates.findByDateOfBirth", query = "SELECT h FROM HrCandidiates h WHERE h.dateOfBirth = :dateOfBirth"),
    @NamedQuery(name = "HrCandidiates.findByMaritalStatus", query = "SELECT h FROM HrCandidiates h WHERE h.maritalStatus = :maritalStatus"),
    @NamedQuery(name = "HrCandidiates.findByHouseNumber", query = "SELECT h FROM HrCandidiates h WHERE h.houseNumber = :houseNumber"),
    @NamedQuery(name = "HrCandidiates.findByTelephoneResidence", query = "SELECT h FROM HrCandidiates h WHERE h.telephoneResidence = :telephoneResidence"),
    @NamedQuery(name = "HrCandidiates.findByTelephoneOffice", query = "SELECT h FROM HrCandidiates h WHERE h.telephoneOffice = :telephoneOffice"),
    @NamedQuery(name = "HrCandidiates.findByTelephoneMobile", query = "SELECT h FROM HrCandidiates h WHERE h.telephoneMobile = :telephoneMobile"),
    @NamedQuery(name = "HrCandidiates.findByEmail", query = "SELECT h FROM HrCandidiates h WHERE h.email = :email"),
    @NamedQuery(name = "HrCandidiates.findByPOBox", query = "SELECT h FROM HrCandidiates h WHERE h.pOBox = :pOBox"),
    @NamedQuery(name = "HrCandidiates.findBySkill", query = "SELECT h FROM HrCandidiates h WHERE h.skill = :skill"),
    @NamedQuery(name = "HrCandidiates.findByHobby", query = "SELECT h FROM HrCandidiates h WHERE h.hobby = :hobby"),
    @NamedQuery(name = "HrCandidiates.findByAdditionalInformation", query = "SELECT h FROM HrCandidiates h WHERE h.additionalInformation = :additionalInformation"),
    @NamedQuery(name = "HrCandidiates.findByApplicationDate", query = "SELECT h FROM HrCandidiates h WHERE h.applicationDate = :applicationDate"),
    @NamedQuery(name = "HrCandidiates.findByNoOfPage", query = "SELECT h FROM HrCandidiates h WHERE h.noOfPage = :noOfPage"),
//    @NamedQuery(name = "HrCandidiates.updateStatus", query = "UPDATE HrCandidiates h SET h.status = :status WHERE h.id = :id"),
    @NamedQuery(name = "HrCandidiates.findByStatus", query = "SELECT h FROM HrCandidiates h WHERE h.status = :status")})

@SqlResultSetMapping(name = "HrCandidiateResultSetMapping",
        entities = {
            @EntityResult(entityClass = HrCandidiates.class, fields = {
                @FieldResult(name = "id", column = "ID"),
                @FieldResult(name = "houseNumber", column = "SHORTLIST_BY"),
                @FieldResult(name = "telephoneResidence", column = "SHORTLIST_ON"),
                @FieldResult(name = "telephoneOffice", column = "SHORTLIST_RECOMMENDATION"),
                @FieldResult(name = "telephoneMobile", column = "SHORTLIST_REMARK"),
                @FieldResult(name = "email", column = "EXPERIENCE"),
                @FieldResult(name = "pOBox", column = "EDUC_RANK"),
                @FieldResult(name = "skill", column = "EDUC_QUAL"),
                @FieldResult(name = "hobby", column = "EXPERIENCE_DESC"),})})

public class HrCandidiates implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_CANDIDIATES_SEQ")
    @SequenceGenerator(name = "HR_CANDIDIATES_SEQ", sequenceName = "HR_CANDIDIATES_SEQ", allocationSize = 1)
    @Column(name = "ID")
    private BigDecimal id;
    @Column(name = "FIRST_NAME")
    private String firstName;
    @Column(name = "MIDDLE_NAME")
    private String middleName;
    @Column(name = "LAST_NAME")
    private String lastName;
    @Column(name = "SEX")
    private String sex;
    @Column(name = "DATE_OF_BIRTH")
    private String dateOfBirth;
    @Column(name = "MARITAL_STATUS")
    private String maritalStatus;
    @Column(name = "HOUSE_NUMBER")
    private String houseNumber;
    @Column(name = "TELEPHONE_RESIDENCE")
    private String telephoneResidence;
    @Column(name = "TELEPHONE_OFFICE")
    private String telephoneOffice;
    @Column(name = "TELEPHONE_MOBILE")
    private String telephoneMobile;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "P_O_BOX")
    private String pOBox;
    @Column(name = "SKILL")
    private String skill;
    @Column(name = "HOBBY")
    private String hobby;
    @Column(name = "ADDITIONAL_INFORMATION")
    private String additionalInformation;
    @Column(name = "APPLICATION_DATE")
    private String applicationDate;
    @Column(name = "NO_OF_PAGE")
    private String noOfPage;
    @Column(name = "STATUS")
    private Integer status;
    @JoinColumn(name = "ADDRESS_ID", referencedColumnName = "ADDRESS_ID")
    @ManyToOne
    private HrAddresses addressId;
    @JoinColumn(name = "ADV_JOB_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private HrAdvertisedJobs advJobId;
    @JoinColumn(name = "NATIONALITY", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private HrLuNationalities nationality;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "candidiateId")
    private List<HrCandidiateEducations> hrCandidiateEducationsList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "candidateId")
    private List<HrCandidateTrainings> hrCandidateTrainingsList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "candidateId")
    private List<HrCandidateEmpHistories> hrCandidateEmpHistoriesList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "candidateId")
    private List<HrCandidateReferences> hrCandidateReferencesList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "candidateId")
    private List<HrCandidateLanguages> hrCandidateLanguagesList;
    
    @Transient
    private String Col_Value;

    public String getCol_Value() {
        return Col_Value;
    }

    public void setCol_Value(String Col_Value) {
        this.Col_Value = Col_Value;
    }


    public HrCandidiates() {
    }

    public HrCandidiates(BigDecimal id) {
        this.id = id;
    }

//<editor-fold defaultstate="collapsed" desc="getters & setters">
    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getTelephoneResidence() {
        return telephoneResidence;
    }

    public void setTelephoneResidence(String telephoneResidence) {
        this.telephoneResidence = telephoneResidence;
    }

    public String getTelephoneOffice() {
        return telephoneOffice;
    }

    public void setTelephoneOffice(String telephoneOffice) {
        this.telephoneOffice = telephoneOffice;
    }

    public String getTelephoneMobile() {
        return telephoneMobile;
    }

    public void setTelephoneMobile(String telephoneMobile) {
        this.telephoneMobile = telephoneMobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getpOBox() {
        return pOBox;
    }

    public void setpOBox(String pOBox) {
        this.pOBox = pOBox;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public String getAdditionalInformation() {
        return additionalInformation;
    }

    public void setAdditionalInformation(String additionalInformation) {
        this.additionalInformation = additionalInformation;
    }

    public String getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(String applicationDate) {
        this.applicationDate = applicationDate;
    }

    public String getNoOfPage() {
        return noOfPage;
    }

    public void setNoOfPage(String noOfPage) {
        this.noOfPage = noOfPage;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public HrAddresses getAddressId() {
        return addressId;
    }

    public void setAddressId(HrAddresses addressId) {
        this.addressId = addressId;
    }

    public HrAdvertisedJobs getAdvJobId() {
        if(advJobId==null){
            advJobId= new HrAdvertisedJobs();
        }
        return advJobId;
    }

    public void setAdvJobId(HrAdvertisedJobs advJobId) {
        this.advJobId = advJobId;
    }

    public HrLuNationalities getNationality() {
        return nationality;
    }

    public void setNationality(HrLuNationalities nationality) {
        this.nationality = nationality;
    }

//</editor-fold>
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrCandidiates)) {
            return false;
        }
        HrCandidiates other = (HrCandidiates) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return firstName;
    }

    @XmlTransient
    public List<HrCandidiateEducations> getHrCandidiateEducationsList() {
        return hrCandidiateEducationsList;
    }

    public void setHrCandidiateEducationsList(List<HrCandidiateEducations> hrCandidiateEducationsList) {
        this.hrCandidiateEducationsList = hrCandidiateEducationsList;
    }

    @XmlTransient
    public List<HrCandidateTrainings> getHrCandidateTrainingsList() {
        if (hrCandidateTrainingsList == null) {
            hrCandidateTrainingsList = new ArrayList<>();
        }
        return hrCandidateTrainingsList;
    }

    public void setHrCandidateTrainingsList(List<HrCandidateTrainings> hrCandidateTrainingsList) {
        this.hrCandidateTrainingsList = hrCandidateTrainingsList;
    }

    public void addToCandidateEducation(HrCandidiateEducations hrCandidiateEducations) {
        if (hrCandidiateEducations.getCandidiateId() != this) {
            this.getHrCandidiateEducationsList().add(hrCandidiateEducations);
            hrCandidiateEducations.setCandidiateId(this);
        }
    }

    public void addToCandidateTrainings(HrCandidateTrainings hrCandidateTrainings) {
        System.out.println("this is from addToCandidateTrainings");
        if (hrCandidateTrainings.getCandidateId() != this) {
            this.getHrCandidateTrainingsList().add(hrCandidateTrainings);
            hrCandidateTrainings.setCandidateId(this);
        }
    }

    public void addToCandidateEmpHistories(HrCandidateEmpHistories hrCandidateEmpHistories) {
        System.out.println("this is from addToCandidateEmpHistories");
        if (hrCandidateEmpHistories.getCandidateId() != this) {
            this.getHrCandidateEmpHistoriesList().add(hrCandidateEmpHistories);
            hrCandidateEmpHistories.setCandidateId(this);
        }
    }

    public void addToCandidateReferences(HrCandidateReferences hrCandidateReferences) {
        System.out.println("this is from addToCandidateReferences");
        if (hrCandidateReferences.getCandidateId() != this) {
            this.getHrCandidateReferencesList().add(hrCandidateReferences);
            hrCandidateReferences.setCandidateId(this);
        }
    }

    public void addToCandidateLanguages(HrCandidateLanguages hrCandidateLanguages) {
        System.out.println("this is from addToCandidateLanguages");
        if (hrCandidateLanguages.getCandidateId() != this) {
            this.getHrCandidateLanguagesList().add(hrCandidateLanguages);
            hrCandidateLanguages.setCandidateId(this);
        }
    }

    @XmlTransient
    public List<HrCandidateEmpHistories> getHrCandidateEmpHistoriesList() {
        if (hrCandidateEmpHistoriesList == null) {
            hrCandidateEmpHistoriesList = new ArrayList<>();
        }
        return hrCandidateEmpHistoriesList;
    }

    public void setHrCandidateEmpHistoriesList(List<HrCandidateEmpHistories> hrCandidateEmpHistoriesList) {
        this.hrCandidateEmpHistoriesList = hrCandidateEmpHistoriesList;
    }

    @XmlTransient
    public List<HrCandidateReferences> getHrCandidateReferencesList() {
        if (hrCandidateReferencesList == null) {
            hrCandidateReferencesList = new ArrayList<>();
        }
        return hrCandidateReferencesList;
    }

    public void setHrCandidateReferencesList(List<HrCandidateReferences> hrCandidateReferencesList) {
        this.hrCandidateReferencesList = hrCandidateReferencesList;
    }

    @XmlTransient
    public List<HrCandidateLanguages> getHrCandidateLanguagesList() {
        if (hrCandidateLanguagesList == null) {
            hrCandidateLanguagesList = new ArrayList<>();
        }
        return hrCandidateLanguagesList;
    }

    public void setHrCandidateLanguagesList(List<HrCandidateLanguages> hrCandidateLanguagesList) {
        this.hrCandidateLanguagesList = hrCandidateLanguagesList;
    }

    //<editor-fold defaultstate="collapsed" desc="status static variables">
    public static final int REGISTERED = 0;
    public static final int SHORTLIST_REQUEST = 1;//request to be on shortlisted list
    public static final int SHORTLISTED = 2;
    public static final int FILTERING_REQUEST = 3;//request to be on filtered list
    public static final int FILTERED = 4;
    public static final int SELECTED_FOR_RECRUITMENT = 5;
    public static final int EMPLOYEE = 6;
    public static final int REGISTERED_EMPLOYEE = 7;// all data transfers form candidate related tabels to employee relaed tables is completed
//</editor-fold>

    // <editor-fold defaultstate="collapsed" desc="transient">
    @Transient
    private int age;

    @Transient
    private boolean approved;

    @Transient
    String statusLabel;

    @Transient
    Integer exprience;

    //<editor-fold defaultstate="collapsed" desc="getters & setters">
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public String getStatusLabel() {
        return statusLabel;
    }

    public void setStatusLabel(String statusLabel) {
        this.statusLabel = statusLabel;
    }

    public Integer getExprience() {
        return exprience;
    }

    public void setExprience(Integer exprience) {
        this.exprience = exprience;
    }

}
//</editor-fold>

    // </editor-fold>

