/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.training;

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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author insa
 */
@Entity
@Table(name = "HR_TD_ISP_PAYMENTS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrTdIspPayments.findAll", query = "SELECT DISTINCT(h.referenceLetter) FROM HrTdIspPayments h"),
    @NamedQuery(name = "HrTdIspPayments.findById", query = "SELECT h FROM HrTdIspPayments h WHERE h.id = :id"),
    @NamedQuery(name = "HrTdIspPayments.findByReferenceLetter", query = "SELECT h FROM HrTdIspPayments h WHERE h.referenceLetter = :referenceLetter"),
    @NamedQuery(name = "HrTdIspPayments.findByLetterMonth", query = "SELECT h FROM HrTdIspPayments h WHERE h.referenceLetter = :referenceLetter and h.month = :month "),
    @NamedQuery(name = "HrTdIspPayments.findByMonth", query = "SELECT h FROM HrTdIspPayments h WHERE h.month = :month"),
    @NamedQuery(name = "HrTdIspPayments.findByRemark", query = "SELECT h FROM HrTdIspPayments h WHERE h.remark = :remark"),
    @NamedQuery(name = "HrTdIspPayments.findByParentID", query = "SELECT h FROM HrTdIspStudentDetails h WHERE h.id = :id"),
    @NamedQuery(name = "HrTdIspPayments.findByPreparedOn", query = "SELECT h FROM HrTdIspPayments h WHERE h.preparedOn = :preparedOn")})
public class HrTdIspPayments implements Serializable {

    @OneToMany(mappedBy = "payementId", cascade = CascadeType.ALL)
    private List<HrTdIspPaymentDetails> hrTdIspPaymentDetailsList;
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_TD_ISP_PAYMENTS_SEQ")
    @SequenceGenerator(name = "HR_TD_ISP_PAYMENTS_SEQ", sequenceName = "HR_TD_ISP_PAYMENTS_SEQ", allocationSize = 1)
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Integer id;
    @Size(max = 20)
    @Column(name = "REFERENCE_LETTER")
    private String referenceLetter;
    @Column(name = "MONTH")
    private BigInteger month;
    @Size(max = 200)
    @Column(name = "REMARK")
    private String remark;
    @Size(max = 20)
    @Column(name = "FROM_DATE")
    private String fromDate;
    @Size(max = 20)
    @Column(name = "TO_DATE")
    private String toDate;
    @Size(max = 20)
    @Column(name = "PREPARED_ON")
    private String preparedOn;
    @Column(name = "PREPARED_BY")
    private Integer preparedBy;
//    @JoinColumn(name = "PREPARED_BY", referencedColumnName = "ID")
//    @ManyToOne
//    private HrEmployees preparedBy;

    public HrTdIspPayments() {
    }

    public HrTdIspPayments(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getReferenceLetter() {
        return referenceLetter;
    }

    public void setReferenceLetter(String referenceLetter) {
        this.referenceLetter = referenceLetter;
    }

    public BigInteger getMonth() {
        return month;
    }

    public void setMonth(BigInteger month) {
        this.month = month;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getPreparedOn() {
        return preparedOn;
    }

    public void setPreparedOn(String preparedOn) {
        this.preparedOn = preparedOn;
    }

    public Integer getPreparedBy() {
        return preparedBy;
    }

    public void setPreparedBy(Integer preparedBy) {
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
        if (!(object instanceof HrTdIspPayments)) {
            return false;
        }
        HrTdIspPayments other = (HrTdIspPayments) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.insurance.HrTdIspPayments[ id=" + id + " ]";
    }

    @XmlTransient
    public List<HrTdIspPaymentDetails> getHrTdIspPaymentDetailsList() {
        if (hrTdIspPaymentDetailsList == null) {
            hrTdIspPaymentDetailsList = new ArrayList<>();
        }
        return hrTdIspPaymentDetailsList;
    }

    public void setHrTdIspPaymentDetailsList(List<HrTdIspPaymentDetails> hrTdIspPaymentDetailsList) {
        this.hrTdIspPaymentDetailsList = hrTdIspPaymentDetailsList;
    }

    public void add(HrTdIspPaymentDetails hrTdIspPaymentDetails) {
        if (hrTdIspPaymentDetails.getPayementId() != this) {
            this.getHrTdIspPaymentDetailsList().add(hrTdIspPaymentDetails);
            hrTdIspPaymentDetails.setPayementId(this);
        }
    }

}
