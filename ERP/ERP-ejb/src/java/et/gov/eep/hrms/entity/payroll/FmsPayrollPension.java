/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.payroll;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Hanoc
 */
@Entity
@Table(name = "FMS_PAYROLL_PENSION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsPayrollPension.findAll", query = "SELECT f FROM FmsPayrollPension f"),
    @NamedQuery(name = "FmsPayrollPension.findByPensionNumber", query = "SELECT f FROM FmsPayrollPension f WHERE f.pensionNumber = :pensionNumber"),
    @NamedQuery(name = "FmsPayrollPension.findByPensionAddition", query = "SELECT f FROM FmsPayrollPension f WHERE f.pensionAddition = :pensionAddition"),
    @NamedQuery(name = "FmsPayrollPension.findByPensionDeduction", query = "SELECT f FROM FmsPayrollPension f WHERE f.pensionDeduction = :pensionDeduction"),
    @NamedQuery(name = "FmsPayrollPension.findByMakeAsCurrentPension", query = "SELECT f FROM FmsPayrollPension f WHERE f.makeAsCurrentPension = :makeAsCurrentPension"),
    @NamedQuery(name = "FmsPayrollPension.findByActiveYear", query = "SELECT f FROM FmsPayrollPension f WHERE f.activeYear = :activeYear")})
public class FmsPayrollPension implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
     @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FMS_SEQ_PAYROLL_PENSION")//
    @SequenceGenerator(name = "FMS_SEQ_PAYROLL_PENSION", sequenceName = "FMS_SEQ_PAYROLL_PENSION", allocationSize = 1)
    @NotNull
    @Column(name = "PENSION_NUMBER")
    private String pensionNumber;
    @Column(name = "PENSION_ADDITION")
    private BigInteger pensionAddition;
    @Column(name = "PENSION_DEDUCTION")
    private BigInteger pensionDeduction;
    @Column(name = "MAKE_AS_CURRENT_PENSION")
    private BigInteger makeAsCurrentPension;
    @Size(max = 20)
    @Column(name = "ACTIVE_YEAR")
    private String activeYear;

    /**
     *
     */
    public FmsPayrollPension() {
    }

    /**
     *
     * @param pensionNumber
     */
    public FmsPayrollPension(String pensionNumber) {
        this.pensionNumber = pensionNumber;
    }

    /**
     *
     * @return
     */
    public String getPensionNumber() {
        return pensionNumber;
    }

    /**
     *
     * @param pensionNumber
     */
    public void setPensionNumber(String pensionNumber) {
        this.pensionNumber = pensionNumber;
    }

    /**
     *
     * @return
     */
    public BigInteger getPensionAddition() {
        return pensionAddition;
    }

    /**
     *
     * @param pensionAddition
     */
    public void setPensionAddition(BigInteger pensionAddition) {
        this.pensionAddition = pensionAddition;
    }

    /**
     *
     * @return
     */
    public BigInteger getPensionDeduction() {
        return pensionDeduction;
    }

    /**
     *
     * @param pensionDeduction
     */
    public void setPensionDeduction(BigInteger pensionDeduction) {
        this.pensionDeduction = pensionDeduction;
    }

    /**
     *
     * @return
     */
    public BigInteger getMakeAsCurrentPension() {
        return makeAsCurrentPension;
    }

    /**
     *
     * @param makeAsCurrentPension
     */
    public void setMakeAsCurrentPension(BigInteger makeAsCurrentPension) {
        this.makeAsCurrentPension = makeAsCurrentPension;
    }

    /**
     *
     * @return
     */
    public String getActiveYear() {
        return activeYear;
    }

    /**
     *
     * @param activeYear
     */
    public void setActiveYear(String activeYear) {
        this.activeYear = activeYear;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pensionNumber != null ? pensionNumber.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FmsPayrollPension)) {
            return false;
        }
        FmsPayrollPension other = (FmsPayrollPension) object;
        if ((this.pensionNumber == null && other.pensionNumber != null) || (this.pensionNumber != null && !this.pensionNumber.equals(other.pensionNumber))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "folder.FmsPayrollPension[ pensionNumber=" + pensionNumber + " ]";
    }
   
    
}
