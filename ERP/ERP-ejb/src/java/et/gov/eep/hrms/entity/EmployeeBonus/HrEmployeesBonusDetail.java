/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.EmployeeBonus;

import et.gov.eep.hrms.entity.employee.HrEmployees;
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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author meles
 */
@Entity
@Table(name = "HR_EMPLOYEES_BONUS_DETAIL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrEmployeesBonusDetail.findAll", query = "SELECT h FROM HrEmployeesBonusDetail h"),
    @NamedQuery(name = "HrEmployeesBonusDetail.findById", query = "SELECT h FROM HrEmployeesBonusDetail h WHERE h.id = :id"),
    @NamedQuery(name = "HrEmployeesBonusDetail.findByIncomTax ", query = "SELECT h FROM HrEmployeesBonusDetail h WHERE h.incomTax = :incomTax"),
    @NamedQuery(name = "HrEmployeesBonusDetail.findByNetBonus", query = "SELECT h FROM HrEmployeesBonusDetail h WHERE h.netBonus = :netBonus"),
    @NamedQuery(name = "HrEmployeesBonusDetail.findByGrosBonus", query = "SELECT h FROM HrEmployeesBonusDetail h WHERE h.grosBonus = :grosBonus")})
public class HrEmployeesBonusDetail implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_EMPLOYEES_BONUS_DETAIL_SQN")
    @SequenceGenerator(name = "HR_EMPLOYEES_BONUS_DETAIL_SQN", sequenceName = "HR_EMPLOYEES_BONUS_DETAIL_SQN", allocationSize = 1)
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private BigDecimal id;
    @Column(name = "GROS_BONUS")
    private double grosBonus;
    @Column(name = "INCOME_TAX")
    private double incomTax;
    @Size(max = 200)
    @Column(name = "EXPERIANCE")
    private String experiance;
    @Size(max = 200)
    @Column(name = "REFERENCE_NO")
    private String referenceNo;
    @Column(name = "NET_BONUS")
    private double netBonus;
    @JoinColumn(name = "EMPLOYEE_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrEmployees employeeId;
    @JoinColumn(name = "BONUS_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrEmployeesBonus bonusId;

    public HrEmployeesBonusDetail() {
    }

    public HrEmployeesBonusDetail(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public double getGrosBonus() {
        return grosBonus;
    }

    public String getReferenceNo() {
        return referenceNo;
    }

    public void setReferenceNo(String referenceNo) {
        this.referenceNo = referenceNo;
    }

    public void setGrosBonus(double grosBonus) {
        this.grosBonus = grosBonus;
    }

    public double getIncomTax() {
        return incomTax;
    }

    public void setIncomTax(double incomTax) {
        this.incomTax = incomTax;
    }

    public double getNetBonus() {
        return netBonus;
    }

    public void setNetBonus(double netBonus) {
        this.netBonus = netBonus;
    }

    public HrEmployees getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(HrEmployees employeeId) {
        this.employeeId = employeeId;
    }

    public HrEmployeesBonus getBonusId() {
        return bonusId;
    }

    public void setBonusId(HrEmployeesBonus bonusId) {
        this.bonusId = bonusId;
    }

    public String getExperiance() {
        return experiance;
    }

    public void setExperiance(String experiance) {
        this.experiance = experiance;
    }

    @Transient
    String exp;
    @Transient
    int num;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getExp() {
        return exp;
    }

    public void setExp(String exp) {
        this.exp = exp;
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
        if (!(object instanceof HrEmployeesBonusDetail)) {
            return false;
        }
        HrEmployeesBonusDetail other = (HrEmployeesBonusDetail) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return id.toString();
    }

}
