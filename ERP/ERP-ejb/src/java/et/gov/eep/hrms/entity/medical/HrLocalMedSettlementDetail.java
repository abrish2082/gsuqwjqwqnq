/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.medical;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
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
 * @author INSA
 */
@Entity
@Table(name = "HR_LOCAL_MED_SETTLEMENT_DETAIL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrLocalMedSettlementDetail.findAll", query = "SELECT h FROM HrLocalMedSettlementDetail h"),
    @NamedQuery(name = "HrLocalMedSettlementDetail.findById", query = "SELECT h FROM HrLocalMedSettlementDetail h WHERE h.id = :id"),
    @NamedQuery(name = "HrLocalMedSettlementDetail.findByReceiptNumber", query = "SELECT h FROM HrLocalMedSettlementDetail h WHERE h.receiptNumber = :receiptNumber"),
    @NamedQuery(name = "HrLocalMedSettlementDetail.findByExpenseType", query = "SELECT h FROM HrLocalMedSettlementDetail h WHERE h.expenseType = :expenseType"),
    @NamedQuery(name = "HrLocalMedSettlementDetail.findByAmount", query = "SELECT h FROM HrLocalMedSettlementDetail h WHERE h.amount = :amount"),
    @NamedQuery(name = "HrLocalMedSettlementDetail.findByDescription", query = "SELECT h FROM HrLocalMedSettlementDetail h WHERE h.description = :description")})
public class HrLocalMedSettlementDetail implements Serializable {
    @Column(name = "AMOUNT")
    private Double amount;

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_LOCAL_MED_SET_DETAIL_SEQ")
    @SequenceGenerator(name = "HR_LOCAL_MED_SET_DETAIL_SEQ", sequenceName = "HR_LOCAL_MED_SET_DETAIL_SEQ", allocationSize = 1)
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Integer id;
    @Size(max = 20)
    @Column(name = "RECEIPT_NUMBER")
    private String receiptNumber;
    @Size(max = 20)
    @Column(name = "EXPENSE_TYPE")
    private String expenseType;
    @Size(max = 20)
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "MEDICAL_INSTITUTION")
    private String medicalInstitution;

    @JoinColumn(name = "SETTLEMENT_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrLocalMedSettlements settlementId;

    public HrLocalMedSettlementDetail() {
    }

    public HrLocalMedSettlementDetail(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getReceiptNumber() {
        return receiptNumber;
    }

    public void setReceiptNumber(String receiptNumber) {
        this.receiptNumber = receiptNumber;
    }

    public String getExpenseType() {
        return expenseType;
    }

    public void setExpenseType(String expenseType) {
        this.expenseType = expenseType;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMedicalInstitution() {
        return medicalInstitution;
    }

    public void setMedicalInstitution(String medicalInstitution) {
        this.medicalInstitution = medicalInstitution;
    }

    public HrLocalMedSettlements getSettlementId() {
        return settlementId;
    }

    public void setSettlementId(HrLocalMedSettlements settlementId) {
        this.settlementId = settlementId;
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
        if (!(object instanceof HrLocalMedSettlementDetail)) {
            return false;
        }
        HrLocalMedSettlementDetail other = (HrLocalMedSettlementDetail) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.HrLocalMedSettlementDetail[ id=" + id + " ]";
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

}
