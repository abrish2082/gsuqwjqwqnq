/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.payroll;

import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author user
 */
@Entity
@Table(name = "HR_PAYROLL_BACK_PYMNTS_EDS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrPayrollBackPymntsEds.findAll", query = "SELECT h FROM HrPayrollBackPymntsEds h"),
    @NamedQuery(name = "HrPayrollBackPymntsEds.loadEarningForBk", query = "SELECT h FROM HrPayrollEarningDeductions h WHERE (h.criterias='Allowance in JL' or h.criterias='Allowance in JT' or h.criterias='Allowance in Loc' or h.criterias='Net Pay') and h.type='Earning' and   h.code NOT IN (SELECT S.earningDedId.code FROM HrPayrollBackPymntsEds S)"),
    @NamedQuery(name = "HrPayrollBackPymntsEds.findById", query = "SELECT h FROM HrPayrollBackPymntsEds h WHERE h.id = :id")})

public class HrPayrollBackPymntsEds implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_PAYROLL_BACK_PYMNTS_EDS_SEQ")
    @SequenceGenerator(name = "HR_PAYROLL_BACK_PYMNTS_EDS_SEQ", sequenceName = "HR_PAYROLL_BACK_PYMNTS_EDS_SEQ", allocationSize = 1)

    private BigDecimal id;
    @JoinColumn(name = "EARNING_DED_ID", referencedColumnName = "CODE")
    @ManyToOne
    private HrPayrollEarningDeductions earningDedId;

    /**
     *
     */
    public HrPayrollBackPymntsEds() {
    }

    /**
     *
     * @param id
     */
    public HrPayrollBackPymntsEds(BigDecimal id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public BigDecimal getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(BigDecimal id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public HrPayrollEarningDeductions getEarningDedId() {
        return earningDedId;
    }

    /**
     *
     * @param earningDedId
     */
    public void setEarningDedId(HrPayrollEarningDeductions earningDedId) {
        this.earningDedId = earningDedId;
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
        if (!(object instanceof HrPayrollBackPymntsEds)) {
            return false;
        }
        HrPayrollBackPymntsEds other = (HrPayrollBackPymntsEds) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.hrms.entity.payroll.HrPayrollBackPymntsEds[ id=" + id + " ]";
    }

}
