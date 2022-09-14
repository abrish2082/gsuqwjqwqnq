/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.insurance;

import et.gov.eep.commonApplications.entity.WfHrProcessed;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
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
@Table(name = "HR_INSURANCE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrInsurance.findAll", query = "SELECT h FROM HrInsurance h"),
    @NamedQuery(name = "HrInsurance.findById", query = "SELECT h FROM HrInsurance h WHERE h.id = :id"),
    @NamedQuery(name = "HrInsurance.findByStatus", query = "SELECT h FROM HrInsurance h WHERE h.status = :status"),
    @NamedQuery(name = "HrInsurance.findgByInsuranceName", query = "SELECT h FROM HrInsurance h WHERE h.insuranceId.insuranceName = :name"),
    @NamedQuery(name = "HrInsurance.findByPolicyNo", query = "SELECT h FROM HrInsurance h WHERE h.policyNo = :policyNo"),
    @NamedQuery(name = "HrInsurance.findByPobox", query = "SELECT h FROM HrInsurance h WHERE h.pobox = :pobox"),
    @NamedQuery(name = "HrInsurance.findByTelephone", query = "SELECT h FROM HrInsurance h WHERE h.telephone = :telephone"),
    @NamedQuery(name = "HrInsurance.findByInsuranceNameLike", query = "SELECT h FROM HrInsurance h WHERE UPPER(h.insuranceId.insuranceName)LIKE  :getInsuranceName"),
    @NamedQuery(name = "HrInsurance.searchbyduplicate", query = "SELECT h FROM HrInsurance h WHERE h.insuranceId.insuranceName = :InsuranceName AND h.branchId.branchName =:BranchName"),
    @NamedQuery(name = "HrInsurance.findByTerminationNameLike", query = "SELECT h FROM HrInsurance h WHERE UPPER(h.telephone)LIKE :ttelephone"),
    @NamedQuery(name = "HrInsurance.findByAddressId", query = "SELECT h FROM HrInsurance h WHERE h.addressId = :addressId"),
    @NamedQuery(name = "HrInsurance.findByRemark", query = "SELECT h FROM HrInsurance h WHERE h.remark = :remark")})
public class HrInsurance implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_INSURANCE_SEQ")
    @SequenceGenerator(name = "HR_INSURANCE_SEQ", sequenceName = "HR_INSURANCE_SEQ", allocationSize = 1)
    @NotNull
    @Column(name = "ID")
    private Integer id;
    @Size(max = 20)
    @Column(name = "POLICY_NO")
    private String policyNo;
    @Size(max = 20)
    @Column(name = "POBOX")
    private String pobox;
    @Size(max = 20)
    @Column(name = "TELEPHONE")
    private String telephone;
    @Column(name = "ADDRESS_ID")
    private BigInteger addressId;
    @Size(max = 200)
    @Column(name = "REMARK")
    private String remark;
    @Column(name = "STATUS")
    private Integer status;
    @Size(max = 20)
    @Column(name = "PREPARED_ON")
    private String preparedon;
    @Size(max = 20)
    @Column(name = "PREPARED_BY")
    private String preparedby;

    @OneToMany(mappedBy = "insuranceId")
    private List<HrInsuranceInjuredEmployee> hrInsuranceInjuredEmployeeList;
    @JoinColumn(name = "INSURANCE_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrLuInsurances insuranceId;
    @JoinColumn(name = "BRANCH_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrLuInsuranceBranches branchId;

    public HrInsurance() {
    }

    public HrInsurance(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPolicyNo() {
        return policyNo;
    }

    public void setPolicyNo(String policyNo) {
        this.policyNo = policyNo;
    }

    public String getPobox() {
        return pobox;
    }

    public void setPobox(String pobox) {
        this.pobox = pobox;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public BigInteger getAddressId() {
        return addressId;
    }

    public void setAddressId(BigInteger addressId) {
        this.addressId = addressId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getPreparedon() {
        return preparedon;
    }

    public void setPreparedon(String preparedon) {
        this.preparedon = preparedon;
    }

    public String getPreparedby() {
        return preparedby;
    }

    public void setPreparedby(String preparedby) {
        this.preparedby = preparedby;
    }

    @XmlTransient
    public List<HrInsuranceInjuredEmployee> getHrInsuranceInjuredEmployeeList() {
        return hrInsuranceInjuredEmployeeList;
    }

    public void setHrInsuranceInjuredEmployeeList(List<HrInsuranceInjuredEmployee> hrInsuranceInjuredEmployeeList) {
        this.hrInsuranceInjuredEmployeeList = hrInsuranceInjuredEmployeeList;
    }

    public HrLuInsurances getInsuranceId() {
        return insuranceId;
    }

    public void setInsuranceId(HrLuInsurances insuranceId) {
        this.insuranceId = insuranceId;
    }

    public HrLuInsuranceBranches getBranchId() {
        return branchId;
    }

    public void setBranchId(HrLuInsuranceBranches branchId) {
        this.branchId = branchId;
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
        if (!(object instanceof HrInsurance)) {
            return false;
        }
        HrInsurance other = (HrInsurance) object;
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
