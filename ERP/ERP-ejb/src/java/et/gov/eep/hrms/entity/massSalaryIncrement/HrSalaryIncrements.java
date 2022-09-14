/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.massSalaryIncrement;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
 * @author Behailu
 */
@Entity
@Table(name = "HR_SALARY_INCREMENTS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrSalaryIncrements.findAll", query = "SELECT h FROM HrSalaryIncrements h"),
    @NamedQuery(name = "HrSalaryIncrements.findById", query = "SELECT h FROM HrSalaryIncrements h WHERE h.id = :id"),
    @NamedQuery(name = "HrSalaryIncrements.findByPreparedOn", query = "SELECT h FROM HrSalaryIncrements h WHERE h.preparedOn = :preparedOn"),
    @NamedQuery(name = "HrSalaryIncrements.findByReferenceNo", query = "SELECT h FROM HrSalaryIncrements h WHERE h.referenceNo = :referenceNo"),
    @NamedQuery(name = "HrSalaryIncrements.findByRemark", query = "SELECT h FROM HrSalaryIncrements h WHERE h.remark = :remark")})
public class HrSalaryIncrements implements Serializable {
//    @OneToMany(mappedBy = "SalaryIncrementId", cascade = CascadeType.ALL)
//    private List<WfHrProcessed> WfHrProcessedList;

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_SALARY_INCREMENTS_SEQ")
    @SequenceGenerator(name = "HR_SALARY_INCREMENTS_SEQ", sequenceName = "HR_SALARY_INCREMENTS_SEQ", allocationSize = 1)
    private Integer id;
    @Size(max = 20)
    @Column(name = "PREPARED_ON")
    private String preparedOn;
    @Size(max = 20)
    @Column(name = " EFFECTIVE_DATE")
    private String effectiveDate;

    @Size(max = 50)
    @Column(name = "REFERENCE_NO")
    private String referenceNo;
    @Size(max = 200)
    @Column(name = "REMARK")
    private String remark;
    @Column(name = "STATUS")
    private Integer status;

    @Column(name = "PREPARED_BY")
    private Integer preparedBy;
    @OneToMany(mappedBy = "salaryIncrementId")
    private List<HrSalaryIncrementDetails> hrSalaryIncrementDetailsList;

    public HrSalaryIncrements() {
    }

    public HrSalaryIncrements(Integer id) {
        this.id = id;
    }

//<editor-fold defaultstate="collapsed" desc="getters & setters">
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getPreparedOn() {
        return preparedOn;
    }

    public void setPreparedOn(String preparedOn) {
        this.preparedOn = preparedOn;
    }

    public String getReferenceNo() {
        return referenceNo;
    }

    public void setReferenceNo(String referenceNo) {
        this.referenceNo = referenceNo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getPreparedBy() {
        return preparedBy;
    }

    public void setPreparedBy(Integer preparedBy) {
        this.preparedBy = preparedBy;
    }

    public String getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(String effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

//</editor-fold>
    @XmlTransient
    public List<HrSalaryIncrementDetails> getHrSalaryIncrementDetailsList() {
        return hrSalaryIncrementDetailsList;
    }

    public void setHrSalaryIncrementDetailsList(List<HrSalaryIncrementDetails> hrSalaryIncrementDetailsList) {
        this.hrSalaryIncrementDetailsList = hrSalaryIncrementDetailsList;
    }

    @XmlTransient

//    public List<WfHrProcessed> getWfHrProcessedList() {
//         if (WfHrProcessedList == null) {
//            WfHrProcessedList = new ArrayList<>();
//        }
//        return WfHrProcessedList;
//    }
//
//    public void setWfHrProcessedList(List<WfHrProcessed> WfHrProcessedList) {
//        this.WfHrProcessedList = WfHrProcessedList;
//    }
//    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrSalaryIncrements)) {
            return false;
        }
        HrSalaryIncrements other = (HrSalaryIncrements) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return id.toString();
    }

    //<editor-fold defaultstate="collapsed" desc="status static variables">
    public static int NEW_PLACEMENT = 1;
    public static int PROMOTION = 2;
    public static int TRANSFER = 3;
    public static int INCREMENT = 4;
    public static int ASSIGNMENT = 5;
    public static int ACCTING = 6;
    public static int JOB_ROTATION = 7;
    public static int SALARY_SCALE_CHANGE = 8;
    public static int DISCIPLINARY_CASE = 9;
    public static int PROBATION = 10;
    public static int REVOKE_ACCTING = 11;
    public static int OTHER = 12;
//</editor-fold>

}
