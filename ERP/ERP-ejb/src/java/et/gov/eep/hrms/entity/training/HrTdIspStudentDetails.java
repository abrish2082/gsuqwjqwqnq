/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.training;

import et.gov.eep.hrms.entity.address.HrAddresses;
import java.io.Serializable;
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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Benin
 */
@Entity
@Table(name = "HR_TD_ISP_STUDENT_DETAILS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrTdIspStudentDetails.findAll", query = "SELECT h FROM HrTdIspStudentDetails h"),
    @NamedQuery(name = "HrTdIspStudentDetails.findByStatusAllplaced", query = "SELECT h FROM HrTdIspStudentDetails h WHERE h.status = 1"),
    @NamedQuery(name = "HrTdIspStudentDetails.findByStatusAll", query = "SELECT h FROM HrTdIspStudentDetails h WHERE h.status = 1"),
    @NamedQuery(name = "HrTdIspStudentDetails.findByStatusZero", query = "SELECT h FROM HrTdIspStudentDetails h WHERE h.status = 0"),
    @NamedQuery(name = "HrTdIspStudentDetails.findById", query = "SELECT h FROM HrTdIspStudentDetails h WHERE h.id = :id"),
    @NamedQuery(name = "HrTdIspStudentDetails.findByFirstName", query = "SELECT h FROM HrTdIspStudentDetails h WHERE h.firstName = :firstName"),
    @NamedQuery(name = "HrTdIspStudentDetails.findByFirstNameLike", query = "SELECT h FROM HrTdIspStudentDetails h WHERE UPPER(h.firstName) Like :firstName and h.status = 2"),
    @NamedQuery(name = "HrTdIspStudentDetails.findByMiddleName", query = "SELECT h FROM HrTdIspStudentDetails h WHERE h.middleName = :middleName"),
    @NamedQuery(name = "HrTdIspStudentDetails.findByLastName", query = "SELECT h FROM HrTdIspStudentDetails h WHERE h.lastName = :lastName"),
    @NamedQuery(name = "HrTdIspStudentDetails.findByStudentId", query = "SELECT h FROM HrTdIspStudentDetails h WHERE h.studentId = :studentId"),
    @NamedQuery(name = "HrTdIspStudentDetails.findByAddressId", query = "SELECT h FROM HrTdIspStudentDetails h WHERE h.addressId.addressId = :addressId"),
    @NamedQuery(name = "HrTdIspStudentDetails.findByPhoneNumber", query = "SELECT h FROM HrTdIspStudentDetails h WHERE h.phoneNumber = :phoneNumber"),
    @NamedQuery(name = "HrTdIspStudentDetails.findByStream", query = "SELECT h FROM HrTdIspStudentDetails h WHERE h.stream = :stream"),
    @NamedQuery(name = "HrTdIspStudentDetails.findByInternshipStudentId", query = "SELECT h FROM HrTdIspStudentDetails h WHERE h.internshipStudentId.id = :stream"),
    @NamedQuery(name = "HrTdIspStudentDetails.findByParentID", query = "SELECT h FROM HrTdIspStudentDetails h WHERE h.internshipStudentId = :hrTdIspStudents"),
    @NamedQuery(name = "HrTdIspStudentDetails.findByDuplicate", query = "SELECT h FROM HrTdIspStudentDetails h WHERE h.studentId = :studentId AND h.internshipStudentId = :internId"),
    @NamedQuery(name = "HrTdIspStudentDetails.findByStatus", query = "SELECT h FROM HrTdIspStudentDetails h WHERE h.status = :status")})
public class HrTdIspStudentDetails implements Serializable {

    @OneToMany(mappedBy = "internshipStudentDetailsId", cascade = CascadeType.ALL)
    private List<HrTdIspStudentPlacement> hrTdIspStudentPlacementList = new ArrayList<>();

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_TD_ISP_STUDENT_DETAILS_SEQ")
    @SequenceGenerator(name = "HR_TD_ISP_STUDENT_DETAILS_SEQ", sequenceName = "HR_TD_ISP_STUDENT_DETAILS_SEQ", allocationSize = 1)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "FIRST_NAME")
    private String firstName;
    @Column(name = "MIDDLE_NAME")
    private String middleName;
    @Column(name = "LAST_NAME")
    private String lastName;
    @Column(name = "STUDENT_ID")
    private String studentId;
    @JoinColumn(name = "ADDRESS_ID", referencedColumnName = "ADDRESS_ID")
    @ManyToOne
    private HrAddresses addressId;
    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;
    @Column(name = "STREAM")
    private String stream;
    @JoinColumn(name = "INTERNSHIP_STUDENT_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrTdIspStudents internshipStudentId;
    @Column(name = "REMARK")
    private String remark;
    @Column(name = "STATUS")
    private Integer status;

    public HrTdIspStudentDetails() {
    }

    public HrTdIspStudentDetails(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public HrAddresses getAddressId() {
        return addressId;
    }

    public void setAddressId(HrAddresses addressId) {
        this.addressId = addressId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getStream() {
        return stream;
    }

    public void setStream(String stream) {
        this.stream = stream;
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

    public HrTdIspStudents getInternshipStudentId() {
        return internshipStudentId;
    }

    public void setInternshipStudentId(HrTdIspStudents internshipStudentId) {
        this.internshipStudentId = internshipStudentId;
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
        if (!(object instanceof HrTdIspStudentDetails)) {
            return false;
        }
        HrTdIspStudentDetails other = (HrTdIspStudentDetails) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return id.toString();
    }

    @XmlTransient
    public List<HrTdIspStudentPlacement> getHrTdIspStudentPlacementList() {
        if (hrTdIspStudentPlacementList == null) {
            hrTdIspStudentPlacementList = new ArrayList<>();
        }
        return hrTdIspStudentPlacementList;
    }

    public void setHrTdIspStudentPlacementList(List<HrTdIspStudentPlacement> hrTdIspStudentPlacementList) {
        this.hrTdIspStudentPlacementList = hrTdIspStudentPlacementList;
    }

}
