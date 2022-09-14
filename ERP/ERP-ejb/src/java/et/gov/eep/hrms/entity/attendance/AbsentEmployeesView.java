/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.attendance;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author meles
 */
@Entity
@Table(name = "ABSENT_EMPLOYEES_VIEW")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AbsentEmployeesView.findAll", query = "SELECT a FROM AbsentEmployeesView a"),
    @NamedQuery(name = "AbsentEmployeesView.findByFirstName", query = "SELECT a FROM AbsentEmployeesView a WHERE a.firstName = :firstName"),
    @NamedQuery(name = "AbsentEmployeesView.findByMiddleName", query = "SELECT a FROM AbsentEmployeesView a WHERE a.middleName = :middleName"),
    @NamedQuery(name = "AbsentEmployeesView.findByEmpId", query = "SELECT a FROM AbsentEmployeesView a WHERE a.empId = :empId"),
    @NamedQuery(name = "AbsentEmployeesView.findByNoOfDaysAbsent", query = "SELECT a FROM AbsentEmployeesView a WHERE a.noOfDaysAbsent = :noOfDaysAbsent"),
    @NamedQuery(name = "AbsentEmployeesView.findByDateOfAbsence", query = "SELECT a FROM AbsentEmployeesView a WHERE a.dateOfAbsence = :dateOfAbsence"),
    @NamedQuery(name = "AbsentEmployeesView.findByReasonForAbsence", query = "SELECT a FROM AbsentEmployeesView a WHERE a.reasonForAbsence = :reasonForAbsence"),
    @NamedQuery(name = "AbsentEmployeesView.findByLateMinute", query = "SELECT a FROM AbsentEmployeesView a WHERE a.lateMinute = :lateMinute")})
public class AbsentEmployeesView implements Serializable {
    private static final long serialVersionUID = 1L;
    @Size(max = 20)
    @Column(name = "FIRST_NAME")
    @Id
    private String firstName;
    @Size(max = 20)
    @Column(name = "MIDDLE_NAME")
    private String middleName;
    @Column(name = "EMP_ID")
    private BigInteger empId;
    @Column(name = "NO_OF_DAYS_ABSENT")
    private BigInteger noOfDaysAbsent;
    @Size(max = 200)
    @Column(name = "DATE_OF_ABSENCE")
    private String dateOfAbsence;
    @Size(max = 20)
    @Column(name = "REASON_FOR_ABSENCE")
    private String reasonForAbsence;
    @Size(max = 20)
    @Column(name = "LATE_MINUTE")
    private String lateMinute;

    public AbsentEmployeesView() {
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

    public BigInteger getEmpId() {
        return empId;
    }

    public void setEmpId(BigInteger empId) {
        this.empId = empId;
    }

    public BigInteger getNoOfDaysAbsent() {
        return noOfDaysAbsent;
    }

    public void setNoOfDaysAbsent(BigInteger noOfDaysAbsent) {
        this.noOfDaysAbsent = noOfDaysAbsent;
    }

    public String getDateOfAbsence() {
        return dateOfAbsence;
    }

    public void setDateOfAbsence(String dateOfAbsence) {
        this.dateOfAbsence = dateOfAbsence;
    }

    public String getReasonForAbsence() {
        return reasonForAbsence;
    }

    public void setReasonForAbsence(String reasonForAbsence) {
        this.reasonForAbsence = reasonForAbsence;
    }

    public String getLateMinute() {
        return lateMinute;
    }

    public void setLateMinute(String lateMinute) {
        this.lateMinute = lateMinute;
    }
    
}
