/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.entity.loan;

import et.gov.eep.pms.entity.PmsCreateProjects;
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
 * @author PO
 */
@Entity
@Table(name = "FMS_LOAN_DETAIL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsLoanDetail.findAll", query = "SELECT f FROM FmsLoanDetail f"),
    @NamedQuery(name = "FmsLoanDetail.findByLoanDetailPk", query = "SELECT f FROM FmsLoanDetail f WHERE f.loanDetailPk = :loanDetailPk")})
public class FmsLoanDetail implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "LOAN_DETAIL_PK")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FMS_LOAN_DETAIL_SEQ")
    @SequenceGenerator( name = "FMS_LOAN_DETAIL_SEQ", sequenceName = "FMS_LOAN_DETAIL_SEQ", allocationSize = 1)
    private Integer loanDetailPk;
    @JoinColumn(name = "LOAN_ID_FK", referencedColumnName = "LOAN_ID")
    @ManyToOne
    private FmsLoan loanIdFk;
    @JoinColumn(name = "PROJECT_ID_FK", referencedColumnName = "PROJECT_ID")
    @ManyToOne
    private PmsCreateProjects projectIdFk;

    public FmsLoanDetail() {
    }

    public FmsLoanDetail(Integer loanDetailPk) {
        this.loanDetailPk = loanDetailPk;
    }

    public Integer getLoanDetailPk() {
        return loanDetailPk;
    }

    public void setLoanDetailPk(Integer loanDetailPk) {
        this.loanDetailPk = loanDetailPk;
    }

    public FmsLoan getLoanIdFk() {
        return loanIdFk;
    }

    public void setLoanIdFk(FmsLoan loanIdFk) {
        this.loanIdFk = loanIdFk;
    }

    public PmsCreateProjects getProjectIdFk() {
        return projectIdFk;
    }

    public void setProjectIdFk(PmsCreateProjects projectIdFk) {
        this.projectIdFk = projectIdFk;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (loanDetailPk != null ? loanDetailPk.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FmsLoanDetail)) {
            return false;
        }
        FmsLoanDetail other = (FmsLoanDetail) object;
        if ((this.loanDetailPk == null && other.loanDetailPk != null) || (this.loanDetailPk != null && !this.loanDetailPk.equals(other.loanDetailPk))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.fcms.entity.loan.FmsLoanDetail[ loanDetailPk=" + loanDetailPk + " ]";
    }
    
}
