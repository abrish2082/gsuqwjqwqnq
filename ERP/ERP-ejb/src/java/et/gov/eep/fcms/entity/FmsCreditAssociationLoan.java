/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author user
 */
@Entity
@Table(name = "FMS_CREDIT_ASSOCIATION_LOAN")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsCreditAssociationLoan.findAll", query = "SELECT f FROM FmsCreditAssociationLoan f"),
    @NamedQuery(name = "FmsCreditAssociationLoan.findById", query = "SELECT f FROM FmsCreditAssociationLoan f WHERE f.id = :id"),
    @NamedQuery(name = "FmsCreditAssociationLoan.findByRequestedAmount", query = "SELECT f FROM FmsCreditAssociationLoan f WHERE f.requestedAmount = :requestedAmount"),
    @NamedQuery(name = "FmsCreditAssociationLoan.findByStartFrom", query = "SELECT f FROM FmsCreditAssociationLoan f WHERE f.startFrom = :startFrom"),
    @NamedQuery(name = "FmsCreditAssociationLoan.findByEndMonth", query = "SELECT f FROM FmsCreditAssociationLoan f WHERE f.endDate = :endDate"),
    @NamedQuery(name = "FmsCreditAssociationLoan.findByMonthlyDeduction", query = "SELECT f FROM FmsCreditAssociationLoan f WHERE f.monthlyDeduction = :monthlyDeduction"),
    @NamedQuery(name = "FmsCreditAssociationLoan.findByInterestRates", query = "SELECT f FROM FmsCreditAssociationLoan f WHERE f.interestRates = :interestRates"),
    @NamedQuery(name = "FmsCreditAssociationLoan.findByRemark", query = "SELECT f FROM FmsCreditAssociationLoan f WHERE f.remark = :remark"),
    @NamedQuery(name = "FmsCreditAssociationLoan.findByStatus", query = "SELECT f FROM FmsCreditAssociationLoan f WHERE f.status = :status")})
public class FmsCreditAssociationLoan implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "ID")
    private String id;
    @Size(max = 20)
    @Column(name = "REQUESTED_AMOUNT")
    private String requestedAmount;
    @Size(max = 20)
    @Column(name = "START_FROM")
    private String startFrom;
    @Size(max = 20)
    @Column(name = "END_DATE")
    private String endDate;
    @Size(max = 20)
    @Column(name = "MONTHLY_DEDUCTION")
    private String monthlyDeduction;
    @Size(max = 20)
    @Column(name = "INTEREST_RATES")
    private String interestRates;
    @Size(max = 200)
    @Column(name = "REMARK")
    private String remark;
    @Size(max = 20)
    @Column(name = "STATUS")
    private String status;
    @JoinColumn(name = "CREDIT_ASSOCATION_ID", referencedColumnName = "ID")
    @ManyToOne
    private FmsCreditAssocation creditAssocationId;

    /**
     *
     */
    public FmsCreditAssociationLoan() {
    }

    /**
     *
     * @param id
     */
    public FmsCreditAssociationLoan(String id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public String getRequestedAmount() {
        return requestedAmount;
    }

    /**
     *
     * @param requestedAmount
     */
    public void setRequestedAmount(String requestedAmount) {
        this.requestedAmount = requestedAmount;
    }

    /**
     *
     * @return
     */
    public String getStartFrom() {
        return startFrom;
    }

    /**
     *
     * @param startFrom
     */
    public void setStartFrom(String startFrom) {
        this.startFrom = startFrom;
    }

    /**
     *
     * @return
     */
    public String getEndDate() {
        return endDate;
    }

    /**
     *
     * @param endDate
     */
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }  

    /**
     *
     * @return
     */
    public String getMonthlyDeduction() {
        return monthlyDeduction;
    }

    /**
     *
     * @param monthlyDeduction
     */
    public void setMonthlyDeduction(String monthlyDeduction) {
        this.monthlyDeduction = monthlyDeduction;
    }

    /**
     *
     * @return
     */
    public String getInterestRates() {
        return interestRates;
    }

    /**
     *
     * @param interestRates
     */
    public void setInterestRates(String interestRates) {
        this.interestRates = interestRates;
    }

    /**
     *
     * @return
     */
    public String getRemark() {
        return remark;
    }

    /**
     *
     * @param remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     *
     * @return
     */
    public String getStatus() {
        return status;
    }

    /**
     *
     * @param status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     *
     * @return
     */
    public FmsCreditAssocation getCreditAssocationId() {
        return creditAssocationId;
    }

    /**
     *
     * @param creditAssocationId
     */
    public void setCreditAssocationId(FmsCreditAssocation creditAssocationId) {
        this.creditAssocationId = creditAssocationId;
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
        if (!(object instanceof FmsCreditAssociationLoan)) {
            return false;
        }
        FmsCreditAssociationLoan other = (FmsCreditAssociationLoan) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.insa.erp.ibfms.entity.FmsCreditAssociationLoan[ id=" + id + " ]";
    }
    
}
