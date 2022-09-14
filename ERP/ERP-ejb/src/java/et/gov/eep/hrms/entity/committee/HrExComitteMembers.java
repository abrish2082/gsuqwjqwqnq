/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.committee;

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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Robi
 */
@Entity
@Table(name = "HR_EX_COMITTE_MEMBERS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrExComitteMembers.findAll", query = "SELECT h FROM HrExComitteMembers h"),
    @NamedQuery(name = "HrExComitteMembers.findByExcmId", query = "SELECT h FROM HrExComitteMembers h WHERE h.excmId = :excmId"),
    @NamedQuery(name = "HrExComitteMembers.findByMemberName", query = "SELECT h FROM HrExComitteMembers h WHERE h.memberName = :memberName"),
    @NamedQuery(name = "HrExComitteMembers.findByOrganization", query = "SELECT h FROM HrExComitteMembers h WHERE h.organization = :organization"),
    @NamedQuery(name = "HrExComitteMembers.findByPosion", query = "SELECT h FROM HrExComitteMembers h WHERE h.posion = :posion"),
    @NamedQuery(name = "HrExComitteMembers.findByResponsibility", query = "SELECT h FROM HrExComitteMembers h WHERE h.responsibility = :responsibility"),
    @NamedQuery(name = "HrExComitteMembers.findByPhoneNumber", query = "SELECT h FROM HrExComitteMembers h WHERE h.phoneNumber = :phoneNumber")})
public class HrExComitteMembers implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "EXCM_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_EXT_COMITTE_MEMBERS_SEQ_GENERATOR")
    @SequenceGenerator(name = "HR_EXT_COMITTE_MEMBERS_SEQ_GENERATOR", sequenceName = "HR_EXT_COMITTE_MEMBERS_SEQ", allocationSize = 1)
    private BigDecimal excmId;
    @Size(max = 20)
    @Column(name = "MEMBER_NAME", length = 20)
    private String memberName;
    @Size(max = 20)
    @Column(name = "ORGANIZATION", length = 20)
    private String organization;
    @Size(max = 20)
    @Column(name = "POSION", length = 20)
    private String posion;
    @Size(max = 200)
    @Column(name = "RESPONSIBILITY", length = 200)
    private String responsibility;
    @Size(max = 20)
    @Column(name = "PHONE_NUMBER", length = 20)
    private String phoneNumber;
    @JoinColumn(name = "COMITTE_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrCommittees comitteId;

    public HrExComitteMembers() {
    }

    public HrExComitteMembers(BigDecimal excmId) {
        this.excmId = excmId;
    }
    
    public BigDecimal getExcmId() {
        return excmId;
    }

    public void setExcmId(BigDecimal excmId) {
        this.excmId = excmId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getPosion() {
        return posion;
    }

    public void setPosion(String posion) {
        this.posion = posion;
    }

    public String getResponsibility() {
        return responsibility;
    }

    public void setResponsibility(String responsibility) {
        this.responsibility = responsibility;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public HrCommittees getComitteId() {
        return comitteId;
    }

    public void setComitteId(HrCommittees comitteId) {
        this.comitteId = comitteId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (excmId != null ? excmId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrExComitteMembers)) {
            return false;
        }
        HrExComitteMembers other = (HrExComitteMembers) object;
        if ((this.excmId == null && other.excmId != null) || (this.excmId != null && !this.excmId.equals(other.excmId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.hrms.entity.committee.HrExComitteMembers[ excmId=" + excmId + " ]";
    }
    
}
