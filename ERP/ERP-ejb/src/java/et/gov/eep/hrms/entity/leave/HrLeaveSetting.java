/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.leave;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author user
 */
@Entity
@Table(name = "HR_LEAVE_SETTING")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrLeaveSetting.findAll", query = "SELECT h FROM HrLeaveSetting h"),
    @NamedQuery(name = "HrLeaveSetting.findById", query = "SELECT h FROM HrLeaveSetting h WHERE h.id = :id"),
    @NamedQuery(name = "HrLeaveSetting.findByDescription", query = "SELECT h FROM HrLeaveSetting h WHERE h.description = :description"),
    @NamedQuery(name = "HrLeaveSetting.findByGender", query = "SELECT h FROM HrLeaveSetting h WHERE h.gender = :gender"),
    @NamedQuery(name = "HrLeaveSetting.findByLeaveTypeId", query = "SELECT h FROM HrLeaveSetting h INNER JOIN HrLuLeaveTypes p WHERE h.leaveTypeId.id = :leaveTypeId"),
    @NamedQuery(name = "HrLeaveSetting.findByLeaveTypeCode", query = "SELECT h FROM HrLeaveSetting h WHERE h.leaveTypeCode = :leaveTypeCodes"),
    @NamedQuery(name = "HrLeaveSetting.findLikeLeaveTypeCode", query = "SELECT h FROM HrLeaveSetting h WHERE h.leaveTypeCode Like :leaveTypeCode"),
    @NamedQuery(name = "HrLeaveSetting.findLikeGender", query = "SELECT h FROM HrLeaveSetting h WHERE h.gender Like :gender or h.gender='Both' "),
    @NamedQuery(name = "HrLeaveSetting.findByMaxNumOfDays", query = "SELECT h FROM HrLeaveSetting h WHERE h.maxNumOfDays = :maxNumOfDays"),
    @NamedQuery(name = "HrLeaveSetting.findByMinNumOfDays", query = "SELECT h FROM HrLeaveSetting h WHERE h.minNumOfDays = :minNumOfDays"),
    @NamedQuery(name = "HrLeaveSetting.findByPaymentCode", query = "SELECT h FROM HrLeaveSetting h WHERE h.paymentCode = :paymentCode"),
    @NamedQuery(name = "HrLeaveSetting.findByEmployementType", query = "SELECT h FROM HrLeaveSetting h WHERE h.employementType = :employementType"),
    @NamedQuery(name = "HrLeaveSetting.findByStatus", query = "SELECT h FROM HrLeaveSetting h WHERE h.status = :status"),

    @NamedQuery(name = "HrLeaveSetting.findByLeaveType", query = "SELECT h FROM HrLeaveSetting h WHERE h.leaveTypeId= :hrLuLeaveTypes"),

    @NamedQuery(name = "HrLeaveSetting.findByIncludeSat", query = "SELECT h FROM HrLeaveSetting h WHERE h.includeSat = :includeSat"),
    @NamedQuery(name = "HrLeaveSetting.findByIncludeSun", query = "SELECT h FROM HrLeaveSetting h WHERE h.includeSun = :includeSun"),
    @NamedQuery(name = "HrLeaveSetting.findByIncludeHollyday", query = "SELECT h FROM HrLeaveSetting h WHERE h.includeHollyday = :includeHollyday")})
public class HrLeaveSetting implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_LEAVE_SETTING_SEQUENCE_SEQ")
    @SequenceGenerator(name = "HR_LEAVE_SETTING_SEQUENCE_SEQ", sequenceName = "HR_LEAVE_SETTING_SEQUENCE_SEQ", allocationSize = 1)
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Integer id;
    @Size(max = 100)
    @Column(name = "DESCRIPTION")
    private String description;
    @Size(max = 20)
    @Column(name = "GENDER")
    private String gender;
    @Size(max = 20)
    @Column(name = "LEAVE_TYPE_CODE")
    private String leaveTypeCode;
    @Column(name = "MAX_NUM_OF_DAYS")
    private Integer maxNumOfDays;
    @Column(name = "MIN_NUM_OF_DAYS")
    private Integer minNumOfDays;
    @Size(max = 20)
    @Column(name = "PAYMENT_CODE")
    private String paymentCode;
    @Size(max = 20)
    @Column(name = "EMPLOYEMENT_TYPE")
    private String employementType;
    @Size(max = 20)
    @Column(name = "STATUS")
    private String status;

    @Column(name = "PROCESSED_BY")
    private Integer processedBy;

    @Column(name = "TO_BALANCE")
    private boolean toBalance;
    @Column(name = "INCLUDE_SAT")
    private boolean includeSat;
    @Column(name = "INCLUDE_SUN")
    private boolean includeSun;
    @Column(name = "INCLUDE_HOLLYDAY")
    private boolean includeHollyday;
    @JoinColumn(name = "LEAVE_TYPE_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrLuLeaveTypes leaveTypeId;

    public HrLeaveSetting() {
    }

    public HrLeaveSetting(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProcessedBy() {
        return processedBy;
    }

    public void setProcessedBy(Integer processedBy) {
        this.processedBy = processedBy;
    }

    public boolean isIncludeSat() {
        return includeSat;
    }

    public void setIncludeSat(boolean includeSat) {
        this.includeSat = includeSat;
    }

    public boolean isIncludeSun() {
        return includeSun;
    }

    public void setIncludeSun(boolean includeSun) {
        this.includeSun = includeSun;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getToBalance() {
        return toBalance;
    }

    public void setToBalance(boolean toBalance) {
        this.toBalance = toBalance;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLeaveTypeCode() {
        return leaveTypeCode;
    }

    public void setLeaveTypeCode(String leaveTypeCode) {
        this.leaveTypeCode = leaveTypeCode;
    }

    public Integer getMaxNumOfDays() {
        return maxNumOfDays;
    }

    public void setMaxNumOfDays(Integer maxNumOfDays) {
        this.maxNumOfDays = maxNumOfDays;
    }

    public Integer getMinNumOfDays() {
        return minNumOfDays;
    }

    public void setMinNumOfDays(Integer minNumOfDays) {
        this.minNumOfDays = minNumOfDays;
    }

    public String getPaymentCode() {
        return paymentCode;
    }

    public void setPaymentCode(String paymentCode) {
        this.paymentCode = paymentCode;
    }

    public String getEmployementType() {
        return employementType;
    }

    public void setEmployementType(String employementType) {
        this.employementType = employementType;
    }

    public boolean isIncludeHollyday() {
        return includeHollyday;
    }

    public void setIncludeHollyday(boolean includeHollyday) {
        this.includeHollyday = includeHollyday;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public HrLuLeaveTypes getLeaveTypeId() {
        return leaveTypeId;
    }

    public void setLeaveTypeId(HrLuLeaveTypes leaveTypeId) {
        this.leaveTypeId = leaveTypeId;
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
        if (!(object instanceof HrLeaveSetting)) {
            return false;
        }
        HrLeaveSetting other = (HrLeaveSetting) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return leaveTypeCode;
    }

}
