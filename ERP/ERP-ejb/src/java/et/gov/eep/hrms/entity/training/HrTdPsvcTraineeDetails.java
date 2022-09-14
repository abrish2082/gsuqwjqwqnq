/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.training;

import et.gov.eep.hrms.entity.address.HrAddresses;
import et.gov.eep.hrms.entity.lookup.HrLuEducLevels;
import et.gov.eep.hrms.entity.lookup.HrLuEducTypes;
import et.gov.eep.hrms.entity.organization.HrDepartments;
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
import javax.persistence.Lob;
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
 * @author insa
 */
@Entity
@Table(name = "HR_TD_PSVC_TRAINEE_DETAILS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrTdPsvcTraineeDetails.findAll", query = "SELECT h FROM HrTdPsvcTraineeDetails h"),
    @NamedQuery(name = "HrTdPsvcTraineeDetails.findById", query = "SELECT h FROM HrTdPsvcTraineeDetails h WHERE h.id = :id"),
    @NamedQuery(name = "HrTdPsvcTraineeDetails.findByTraineeNo", query = "SELECT h FROM HrTdPsvcTraineeDetails h WHERE h.traineeNo = :traineeNo"),
    @NamedQuery(name = "HrTdPsvcTraineeDetails.findByFirstNameEng", query = "SELECT h FROM HrTdPsvcTraineeDetails h WHERE h.firstNameEng = :firstNameEng"),
    @NamedQuery(name = "HrTdPsvcTraineeDetails.findByMiddleNameEng", query = "SELECT h FROM HrTdPsvcTraineeDetails h WHERE h.middleNameEng = :middleNameEng"),
    @NamedQuery(name = "HrTdPsvcTraineeDetails.findByLastNameEng", query = "SELECT h FROM HrTdPsvcTraineeDetails h WHERE h.lastNameEng = :lastNameEng"),
    @NamedQuery(name = "HrTdPsvcTraineeDetails.findByFirstNameAmh", query = "SELECT h FROM HrTdPsvcTraineeDetails h WHERE h.firstNameAmh = :firstNameAmh"),
    @NamedQuery(name = "HrTdPsvcTraineeDetails.findByMiddleNameAmh", query = "SELECT h FROM HrTdPsvcTraineeDetails h WHERE h.middleNameAmh = :middleNameAmh"),
    @NamedQuery(name = "HrTdPsvcTraineeDetails.findByLastNameAmh", query = "SELECT h FROM HrTdPsvcTraineeDetails h WHERE h.lastNameAmh = :lastNameAmh"),
    @NamedQuery(name = "HrTdPsvcTraineeDetails.findBySex", query = "SELECT h FROM HrTdPsvcTraineeDetails h WHERE h.sex = :sex"),
    @NamedQuery(name = "HrTdPsvcTraineeDetails.findByAchivement", query = "SELECT h FROM HrTdPsvcTraineeDetails h WHERE h.achivement = :achivement"),
    @NamedQuery(name = "HrTdPsvcTraineeDetails.findByTelephoneHome", query = "SELECT h FROM HrTdPsvcTraineeDetails h WHERE h.telephoneHome = :telephoneHome"),
    @NamedQuery(name = "HrTdPsvcTraineeDetails.findByMobilePhone", query = "SELECT h FROM HrTdPsvcTraineeDetails h WHERE h.mobilePhone = :mobilePhone"),
    @NamedQuery(name = "HrTdPsvcTraineeDetails.findByRegistrationDate", query = "SELECT h FROM HrTdPsvcTraineeDetails h WHERE h.registrationDate = :registrationDate"),
    @NamedQuery(name = "HrTdPsvcTraineeDetails.findByRemark", query = "SELECT h FROM HrTdPsvcTraineeDetails h WHERE h.remark = :remark"),
    @NamedQuery(name = "HrTdPsvcTraineeDetails.findByStatus", query = "SELECT h FROM HrTdPsvcTraineeDetails h WHERE h.status = :status")})
public class HrTdPsvcTraineeDetails implements Serializable {

    @Lob
    @Column(name = "PHOTO")
    private byte[] photo;
    @Column(name = "STATUS")
    private BigInteger status;
    @OneToMany(mappedBy = "traineeDetailId", cascade = CascadeType.ALL)
    private List<HrTdPsvcResults> hrTdPsvcResultsList;

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_TD_PSVC_TRAINEE_DETAILS_SEQ")
    @SequenceGenerator(name = "HR_TD_PSVC_TRAINEE_DETAILS_SEQ", sequenceName = "HR_TD_PSVC_TRAINEE_DETAILS_SEQ", allocationSize = 1)
    private Integer id;
    @Size(max = 20)
    @Column(name = "TRAINEE_NO")
    private String traineeNo;
    @Size(max = 20)
    @Column(name = "FIRST_NAME_ENG")
    private String firstNameEng;
    @Size(max = 20)
    @Column(name = "MIDDLE_NAME_ENG")
    private String middleNameEng;
    @Size(max = 20)
    @Column(name = "LAST_NAME_ENG")
    private String lastNameEng;
    @Size(max = 20)
    @Column(name = "FIRST_NAME_AMH")
    private String firstNameAmh;
    @Size(max = 20)
    @Column(name = "MIDDLE_NAME_AMH")
    private String middleNameAmh;
    @Size(max = 20)
    @Column(name = "LAST_NAME_AMH")
    private String lastNameAmh;

    @Column(name = "SEX")
    private String sex;
    @Size(max = 500)
    @Column(name = "ACHIVEMENT")
    private String achivement;
    @Size(max = 20)
    @Column(name = "TELEPHONE_HOME")
    private String telephoneHome;

    @Size(max = 20)
    @Column(name = "MOBILE_PHONE")
    private String mobilePhone;

    @Size(max = 20)
    @Column(name = "REGISTRATION_DATE")
    private String registrationDate;

    @Size(max = 200)
    @Column(name = "REMARK")
    private String remark;

    @JoinColumn(name = "BIRTH_PLACE_ADDRESS_ID", referencedColumnName = "ADDRESS_ID")
    @ManyToOne
    private HrAddresses birthPlaceAddressId;

    @JoinColumn(name = "DEPT_ID", referencedColumnName = "DEP_ID")
    @ManyToOne
    private HrDepartments deptId;

    @JoinColumn(name = "EDUC_LEVEL_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrLuEducLevels educLevelId;

    @JoinColumn(name = "EDUC_QUAL_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrLuEducTypes educQualId;

    @JoinColumn(name = "TRAINEE_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrTdPsvcTrainees traineeId;

    public HrTdPsvcTraineeDetails() {
    }

    public HrTdPsvcTraineeDetails(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTraineeNo() {
        return traineeNo;
    }

    public void setTraineeNo(String traineeNo) {
        this.traineeNo = traineeNo;
    }

    public String getFirstNameEng() {
        return firstNameEng;
    }

    public void setFirstNameEng(String firstNameEng) {
        this.firstNameEng = firstNameEng;
    }

    public String getMiddleNameEng() {
        return middleNameEng;
    }

    public void setMiddleNameEng(String middleNameEng) {
        this.middleNameEng = middleNameEng;
    }

    public String getLastNameEng() {
        return lastNameEng;
    }

    public void setLastNameEng(String lastNameEng) {
        this.lastNameEng = lastNameEng;
    }

    public String getFirstNameAmh() {
        return firstNameAmh;
    }

    public void setFirstNameAmh(String firstNameAmh) {
        this.firstNameAmh = firstNameAmh;
    }

    public String getMiddleNameAmh() {
        return middleNameAmh;
    }

    public void setMiddleNameAmh(String middleNameAmh) {
        this.middleNameAmh = middleNameAmh;
    }

    public String getLastNameAmh() {
        return lastNameAmh;
    }

    public void setLastNameAmh(String lastNameAmh) {
        this.lastNameAmh = lastNameAmh;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAchivement() {
        return achivement;
    }

    public void setAchivement(String achivement) {
        this.achivement = achivement;
    }

    public String getTelephoneHome() {
        return telephoneHome;
    }

    public void setTelephoneHome(String telephoneHome) {
        this.telephoneHome = telephoneHome;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public HrAddresses getBirthPlaceAddressId() {
        return birthPlaceAddressId;
    }

    public void setBirthPlaceAddressId(HrAddresses birthPlaceAddressId) {
        this.birthPlaceAddressId = birthPlaceAddressId;
    }

    public HrDepartments getDeptId() {
        return deptId;
    }

    public void setDeptId(HrDepartments deptId) {
        this.deptId = deptId;
    }

    public HrLuEducLevels getEducLevelId() {
        return educLevelId;
    }

    public void setEducLevelId(HrLuEducLevels educLevelId) {
        this.educLevelId = educLevelId;
    }

    public HrLuEducTypes getEducQualId() {
        return educQualId;
    }

    public void setEducQualId(HrLuEducTypes educQualId) {
        this.educQualId = educQualId;
    }

    public HrTdPsvcTrainees getTraineeId() {
        return traineeId;
    }

    public void setTraineeId(HrTdPsvcTrainees traineeId) {
        this.traineeId = traineeId;
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
        if (!(object instanceof HrTdPsvcTraineeDetails)) {
            return false;
        }
        HrTdPsvcTraineeDetails other = (HrTdPsvcTraineeDetails) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.HrTdPsvcTraineeDetails[ id=" + id + " ]";
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public BigInteger getStatus() {
        return status;
    }

    public void setStatus(BigInteger status) {
        this.status = status;
    }

    @XmlTransient
    public List<HrTdPsvcResults> getHrTdPsvcResultsList() {
        if (hrTdPsvcResultsList == null) {
            hrTdPsvcResultsList = new ArrayList<>();
        }
        return hrTdPsvcResultsList;
    }

    public void setHrTdPsvcResultsList(List<HrTdPsvcResults> hrTdPsvcResultsList) {
        this.hrTdPsvcResultsList = hrTdPsvcResultsList;
    }

}
