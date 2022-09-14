/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.overtime;

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
 * @author meles
 */
@Entity
@Table(name = "HR_OVERTIME_RATES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrOvertimeRates.findAll", query = "SELECT h FROM HrOvertimeRates h"),
    @NamedQuery(name = "HrOvertimeRates.findById", query = "SELECT h FROM HrOvertimeRates h WHERE h.id = :id"),
    @NamedQuery(name = "HrOvertimeRates.findByRateName1", query = "SELECT h FROM HrOvertimeRates h WHERE h.otTypes =:otTypes1"),
    @NamedQuery(name = "HrOvertimeRates.findByOtTypes", query = "SELECT h FROM HrOvertimeRates h WHERE h.otTypes =:otTypes"),
    //@NamedQuery(name = "HrOvertimeRates.findByOtTypes", query = "SELECT h FROM HrOvertimeRates h WHERE UPPER(h.otTypes)LIKE :otTypes"),
    @NamedQuery(name = "HrOvertimeRates.findByOtRate", query = "SELECT h FROM HrOvertimeRates h WHERE h.otRate = :otRate"),
    @NamedQuery(name = "HrOvertimeRates.findByDescription", query = "SELECT h FROM HrOvertimeRates h WHERE h.description = :description")})
public class HrOvertimeRates implements Serializable {

    @Column(name = "OT_RATE")
    private double otRate;
    @OneToMany(mappedBy = "otRateId")
    private List<HrOvertimeRequestDetails> hrOvertimeRequestDetailsList;

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_OVERTIME_RATES_SEQ_GENERATOR")
    @SequenceGenerator(name = "HR_OVERTIME_RATES_SEQ_GENERATOR", sequenceName = "HR_OVERTIME_RATES_SEQ", allocationSize = 1)
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Integer id;
    @Size(max = 20)
    @Column(name = "OT_TYPES")
    private String otTypes;
    @Size(max = 20)
    @Column(name = "PREPARED_ON")
    private String preparedon;
    @Size(max = 20)
    @Column(name = "PREPARED_BY")
    private String preparedby;
    @Size(max = 200)
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "MAXIMUM_WORKING_HOUR")
    private Integer maximumWorkingHour;

    public HrOvertimeRates() {
    }

    public HrOvertimeRates(Integer id) {
        this.id = id;
    }

    public HrOvertimeRates(Integer id, String otTypes, double otRate) {
        this.id = id;
        this.otTypes = otTypes;
        this.otRate = otRate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOtTypes() {
        return otTypes;
    }

    public void setOtTypes(String otTypes) {
        this.otTypes = otTypes;
    }

    public double getOtRate() {
        return otRate;
    }

    public void setOtRate(double otRate) {
        this.otRate = otRate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getMaximumWorkingHour() {
        return maximumWorkingHour;
    }

    public void setMaximumWorkingHour(Integer maximumWorkingHour) {
        this.maximumWorkingHour = maximumWorkingHour;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrOvertimeRates)) {
            return false;
        }
        HrOvertimeRates other = (HrOvertimeRates) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.hrms.entity.overtime.HrOvertimeRates[ id=" + id + " ]";
    }

    @XmlTransient
    public List<HrOvertimeRequestDetails> getHrOvertimeRequestDetailsList() {
        return hrOvertimeRequestDetailsList;
    }

    public void setHrOvertimeRequestDetailsList(List<HrOvertimeRequestDetails> hrOvertimeRequestDetailsList) {
        this.hrOvertimeRequestDetailsList = hrOvertimeRequestDetailsList;
    }

}
