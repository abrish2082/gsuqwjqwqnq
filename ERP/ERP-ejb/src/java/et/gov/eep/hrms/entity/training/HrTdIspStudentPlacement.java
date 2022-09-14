/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.training;

import et.gov.eep.hrms.entity.organization.HrDepartments;
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
 * @author Benin
 */
@Entity
@Table(name = "HR_TD_ISP_STUDENT_PLACEMENT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrTdIspStudentPlacement.findAll", query = "SELECT h FROM HrTdIspStudentPlacement h"),
    @NamedQuery(name = "HrTdIspStudentPlacement.findById", query = "SELECT h FROM HrTdIspStudentPlacement h WHERE h.id = :id"),
    @NamedQuery(name = "HrTdIspStudentPlacement.findByDeptId", query = "SELECT h FROM HrTdIspStudentPlacement h WHERE h.deptId = :deptId"),
    @NamedQuery(name = "HrTdIspStudentPlacement.findByRemark", query = "SELECT h FROM HrTdIspStudentPlacement h WHERE h.remark = :remark"),
    @NamedQuery(name = "HrTdIspStudentPlacement.findByName", query = "SELECT h FROM HrTdIspStudentPlacement h WHERE UPPER(h.internshipStudentDetailsId.firstName) LIKE :firstName"),
    @NamedQuery(name = "HrTdIspStudentPlacement.findByInternshipStudentDetailsId", query = "SELECT h FROM HrTdIspStudentPlacement h WHERE h.internshipStudentDetailsId.id = :internshipStudentDetailsId")})
public class HrTdIspStudentPlacement implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_TRN_PLACEMENT_SEQ")
    @SequenceGenerator(name = "HR_TRN_PLACEMENT_SEQ", sequenceName = "HR_TRN_PLACEMENT_SEQ", allocationSize = 1)
    private Integer id;
    @JoinColumn(name = "DEPT_ID", referencedColumnName = "DEP_ID")
    @ManyToOne
    private HrDepartments deptId;
    @JoinColumn(name = "INTERNSHIP_STUDENT_DETAILS_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrTdIspStudentDetails internshipStudentDetailsId;
    @Size(max = 200)
    @Column(name = "REMARK")
    private String remark;
   
    public HrTdIspStudentPlacement() {
    }

    public HrTdIspStudentPlacement(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public HrDepartments getDeptId() {
        return deptId;
    }

    public void setDeptId(HrDepartments deptId) {
        this.deptId = deptId;
    }

    public HrTdIspStudentDetails getInternshipStudentDetailsId() {
        return internshipStudentDetailsId;
    }

    public void setInternshipStudentDetailsId(HrTdIspStudentDetails internshipStudentDetailsId) {
        this.internshipStudentDetailsId = internshipStudentDetailsId;
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
        if (!(object instanceof HrTdIspStudentPlacement)) {
            return false;
        }
        HrTdIspStudentPlacement other = (HrTdIspStudentPlacement) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return deptId.getDepName();
    }

}
