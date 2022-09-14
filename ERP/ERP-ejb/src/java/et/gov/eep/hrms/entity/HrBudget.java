/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author user
 */
@Entity
@Table(name = "HR_BUDGET")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrBudget.findAll", query = "SELECT h FROM HrBudget h"),
    @NamedQuery(name = "HrBudget.findByBudgetId", query = "SELECT h FROM HrBudget h WHERE h.budgetId = :budgetId"),
    @NamedQuery(name = "HrBudget.findByAmount", query = "SELECT h FROM HrBudget h WHERE h.amount = :amount"),
    @NamedQuery(name = "HrBudget.findByBudgetType", query = "SELECT h FROM HrBudget h WHERE h.budgetType = :budgetType"),
    @NamedQuery(name = "HrBudget.findByBudgetYear", query = "SELECT h FROM HrBudget h WHERE h.budgetYear = :budgetYear"),
    @NamedQuery(name = "HrBudget.findByRemark", query = "SELECT h FROM HrBudget h WHERE h.remark = :remark"),
    @NamedQuery(name = "HrBudget.findByStatus", query = "SELECT h FROM HrBudget h WHERE h.status = :status"),
    @NamedQuery(name = "HrBudget.findByApproveDate", query = "SELECT h FROM HrBudget h WHERE h.approveDate = :approveDate"),
    @NamedQuery(name = "HrBudget.findByCommentGiven", query = "SELECT h FROM HrBudget h WHERE h.commentGiven = :commentGiven")})
public class HrBudget implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "BUDGET_ID")
    private BigDecimal budgetId;
    @Column(name = "AMOUNT")
    private Double amount;
    @Size(max = 255)
    @Column(name = "BUDGET_TYPE")
    private String budgetType;
    @Size(max = 255)
    @Column(name = "BUDGET_YEAR")
    private String budgetYear;
    @Size(max = 255)
    @Column(name = "REMARK")
    private String remark;
    @Column(name = "STATUS")
    private BigInteger status;
    @Column(name = "APPROVE_DATE")
    @Temporal(TemporalType.DATE)
    private Date approveDate;
    @Size(max = 255)
    @Column(name = "COMMENT_GIVEN")
    private String commentGiven;

    /**
     *
     */
    public HrBudget() {
    }

    /**
     *
     * @param budgetId
     */
    public HrBudget(BigDecimal budgetId) {
        this.budgetId = budgetId;
    }

    /**
     *
     * @return
     */
    public BigDecimal getBudgetId() {
        return budgetId;
    }

    /**
     *
     * @param budgetId
     */
    public void setBudgetId(BigDecimal budgetId) {
        this.budgetId = budgetId;
    }

    /**
     *
     * @return
     */
    public Double getAmount() {
        return amount;
    }

    /**
     *
     * @param amount
     */
    public void setAmount(Double amount) {
        this.amount = amount;
    }

    /**
     *
     * @return
     */
    public String getBudgetType() {
        return budgetType;
    }

    /**
     *
     * @param budgetType
     */
    public void setBudgetType(String budgetType) {
        this.budgetType = budgetType;
    }

    /**
     *
     * @return
     */
    public String getBudgetYear() {
        return budgetYear;
    }

    /**
     *
     * @param budgetYear
     */
    public void setBudgetYear(String budgetYear) {
        this.budgetYear = budgetYear;
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
    public BigInteger getStatus() {
        return status;
    }

    /**
     *
     * @param status
     */
    public void setStatus(BigInteger status) {
        this.status = status;
    }

    /**
     *
     * @return
     */
    public Date getApproveDate() {
        return approveDate;
    }

    /**
     *
     * @param approveDate
     */
    public void setApproveDate(Date approveDate) {
        this.approveDate = approveDate;
    }

    /**
     *
     * @return
     */
    public String getCommentGiven() {
        return commentGiven;
    }

    /**
     *
     * @param commentGiven
     */
    public void setCommentGiven(String commentGiven) {
        this.commentGiven = commentGiven;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (budgetId != null ? budgetId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrBudget)) {
            return false;
        }
        HrBudget other = (HrBudget) object;
        if ((this.budgetId == null && other.budgetId != null) || (this.budgetId != null && !this.budgetId.equals(other.budgetId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.insa.erp.hrms.entity.HrBudget[ budgetId=" + budgetId + " ]";
    }
    
}
