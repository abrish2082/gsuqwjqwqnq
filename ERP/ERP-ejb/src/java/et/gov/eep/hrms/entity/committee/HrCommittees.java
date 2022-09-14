/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.committee;

import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.lookup.HrLuCommitteeTypes;
import et.gov.eep.mms.entity.MmsInspection;
import et.gov.eep.prms.entity.PrmsBidOpeningCheckList;
import et.gov.eep.prms.entity.PrmsQuotation;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author user
 */
@Entity
@Table(name = "HR_COMMITTEES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrCommittees.findAll", query = "SELECT h FROM HrCommittees h"),
    @NamedQuery(name = "HrCommittees.findById", query = "SELECT h FROM HrCommittees h WHERE h.id = :id"),
    @NamedQuery(name = "HrCommittees.findByCommitteeName", query = "SELECT h FROM HrCommittees h WHERE h.committeeName = :committeeName"),
    @NamedQuery(name = "HrCommittees.findByResponsibility", query = "SELECT h FROM HrCommittees h WHERE h.responsibility = :responsibility"),
    @NamedQuery(name = "HrCommittees.findByEstablishedDate", query = "SELECT h FROM HrCommittees h WHERE h.establishedDate = :establishedDate"),
    @NamedQuery(name = "HrCommittees.findByValidFrom", query = "SELECT h FROM HrCommittees h WHERE h.validFrom = :validFrom"),
    @NamedQuery(name = "HrCommittees.findByValidTo", query = "SELECT h FROM HrCommittees h WHERE h.validTo = :validTo"),

    @NamedQuery(name = "HrCommittees.findByCommitteType", query = "SELECT h FROM HrCommittees h WHERE h.committeeTypeId = :committeeTypeId"),
    @NamedQuery(name = "HrCommittees.findByCommitteeNameLike", query = "SELECT h FROM HrCommittees h WHERE UPPER (h.committeeName) LIKE :committeeName"),
    @NamedQuery(name = "HrCommittees.findByPreparedOn", query = "SELECT h FROM HrCommittees h WHERE h.preparedOn = :preparedOn")})
public class HrCommittees implements Serializable {

    @JoinTable(name = "HR_COMMITTEES_HR_COMMITTEES", joinColumns = {
        @JoinColumn(name = "HRCOMMITTEELIST_ID", referencedColumnName = "ID", nullable = false)}, inverseJoinColumns = {
        @JoinColumn(name = "HRCOMMITTEES_ID", referencedColumnName = "ID", nullable = false)})
    @ManyToMany
    private List<HrCommittees> hrCommitteesList;
    @ManyToMany(mappedBy = "hrCommitteesList")
    private List<HrCommittees> hrCommitteesList1;
    @OneToMany(mappedBy = "comitteId", cascade = CascadeType.ALL)
    private List<HrExComitteMembers> hrExComitteMembersList;

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_COMMITTEES_SEQ_GENERATOR")
    @SequenceGenerator(name = "HR_COMMITTEES_SEQ_GENERATOR", sequenceName = "HR_COMMITTEES_SEQ", allocationSize = 1)
    private Integer id;

    @Size(max = 100)
    @Column(name = "COMMITTEE_NAME")
    private String committeeName;

    @Size(max = 200)
    @Column(name = "REMARK")
    private String remark;

    @Size(max = 20)
    @Column(name = "TYPE")
    private String type;

    @Size(max = 200)
    @Column(name = "RESPONSIBILITY")
    private String responsibility;

    @Column(name = "ESTABLISHED_DATE")
    private String establishedDate;

    @Column(name = "VALID_FROM")
    private String validFrom;

    @Column(name = "VALID_TO")
    private String validTo;

    @Size(max = 20)
    @Column(name = "PREPARED_ON")
    private String preparedOn;

    @JoinColumn(name = "PREPARED_BY", referencedColumnName = "ID")
    @ManyToOne
    private HrEmployees preparedBy;

    @JoinColumn(name = "COMMITTEE_TYPE_ID", referencedColumnName = "ID", unique = true, nullable = false)
    @ManyToOne
    private HrLuCommitteeTypes committeeTypeId;

    @OneToMany(mappedBy = "committeeId", cascade = CascadeType.ALL)
    private List<HrCommitteeMembers> hrCommitteeMembersList;
    @OneToMany(mappedBy = "commiteeId", cascade = CascadeType.ALL)
    private List<PrmsBidOpeningCheckList> prmsBidOpeningCheckList = new ArrayList<>();
    
    @Transient
    private String Col_Value;

    public String getCol_Value() {
        return Col_Value;
    }
    public void setCol_Value(String Col_Value) {
        this.Col_Value = Col_Value;
    }

    public HrCommittees() {
    }

    public HrCommittees(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCommitteeName() {
        return committeeName;
    }

    public void setCommitteeName(String committeeName) {
        this.committeeName = committeeName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getResponsibility() {
        return responsibility;
    }

    public void setResponsibility(String responsibility) {
        this.responsibility = responsibility;
    }

    public String getEstablishedDate() {
        return establishedDate;
    }

    public void setEstablishedDate(String establishedDate) {
        this.establishedDate = establishedDate;
    }

    public String getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(String validFrom) {
        this.validFrom = validFrom;
    }

    public String getValidTo() {
        return validTo;
    }

    public void setValidTo(String validTo) {
        this.validTo = validTo;
    }

    public String getPreparedOn() {
        return preparedOn;
    }

    public void setPreparedOn(String preparedOn) {
        this.preparedOn = preparedOn;
    }

    public HrEmployees getPreparedBy() {
        return preparedBy;
    }

    public void setPreparedBy(HrEmployees preparedBy) {
        this.preparedBy = preparedBy;
    }

    public HrLuCommitteeTypes getCommitteeTypeId() {
        return committeeTypeId;
    }

    public void setCommitteeTypeId(HrLuCommitteeTypes committeeTypeId) {
        this.committeeTypeId = committeeTypeId;
    }

    @XmlTransient
    public List<PrmsBidOpeningCheckList> getPrmsBidOpeningCheckList() {
        return prmsBidOpeningCheckList;
    }

    public void setPrmsBidOpeningCheckList(List<PrmsBidOpeningCheckList> prmsBidOpeningCheckList) {
        this.prmsBidOpeningCheckList = prmsBidOpeningCheckList;
    }

    @XmlTransient
    public List<HrCommitteeMembers> getHrCommitteeMembersList() {
        if (hrCommitteeMembersList == null) {
            hrCommitteeMembersList = new ArrayList<>();
        }
        return hrCommitteeMembersList;
    }

    public void setHrCommitteeMembersList(List<HrCommitteeMembers> hrCommitteeMembersList) {
        this.hrCommitteeMembersList = hrCommitteeMembersList;
    }

    public void addCommitteeMember(HrCommitteeMembers hrCommitteeMembers) {
        if (hrCommitteeMembers.getCommitteeId() != this) {
            this.getHrCommitteeMembersList().add(hrCommitteeMembers);
            hrCommitteeMembers.setCommitteeId(this);
        }
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
        if (!(object instanceof HrCommittees)) {
            return false;
        }
        HrCommittees other = (HrCommittees) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return id.toString();
    }

    @XmlTransient
    public List<HrCommittees> getHrCommitteesList() {
        return hrCommitteesList;
    }

    public void setHrCommitteesList(List<HrCommittees> hrCommitteesList) {
        this.hrCommitteesList = hrCommitteesList;
    }

    @XmlTransient
    public List<HrCommittees> getHrCommitteesList1() {
        return hrCommitteesList1;
    }

    public void setHrCommitteesList1(List<HrCommittees> hrCommitteesList1) {
        this.hrCommitteesList1 = hrCommitteesList1;
    }

    @XmlTransient
    public List<HrExComitteMembers> getHrExComitteMembersList() {
        if (hrExComitteMembersList == null) {
            hrExComitteMembersList = new ArrayList<>();
        }
        return hrExComitteMembersList;
    }

    public void setHrExComitteMembersList(List<HrExComitteMembers> hrExComitteMembersList) {
        this.hrExComitteMembersList = hrExComitteMembersList;
    }

    public void addExCommitteeMember(HrExComitteMembers hrExCommitteeMembers) {
        if (hrExCommitteeMembers.getComitteId() != this) {
            System.out.println("======entity============");
            this.getHrExComitteMembersList().add(hrExCommitteeMembers);
            hrExCommitteeMembers.setComitteId(this);
        }
    }

}
