/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.training;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
 * @author insa
 */
@Entity
@Table(name = "HR_TD_PSVC_TRAINEES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrTdPsvcTrainees.findAll", query = "SELECT DISTINCT(h.yearOfTraining) FROM HrTdPsvcTrainees h"),
    @NamedQuery(name = "HrTdPsvcTrainees.findById", query = "SELECT h FROM HrTdPsvcTrainees h WHERE h.id = :id"),
    @NamedQuery(name = "HrTdPsvcTrainees.findByYear", query = "SELECT h FROM HrTdPsvcTrainees h WHERE h.yearOfTraining = :yearOfTraining"),
    @NamedQuery(name = "HrTdPsvcTrainees.findByBatchCode", query = "SELECT h FROM HrTdPsvcTrainees h WHERE h.batchCode = :batchCode"),
    @NamedQuery(name = "HrTdPsvcTrainees.findByRemark", query = "SELECT h FROM HrTdPsvcTrainees h WHERE h.remark = :remark"),
    @NamedQuery(name = "HrTdPsvcTrainees.findByPreparedBy", query = "SELECT h FROM HrTdPsvcTrainees h WHERE h.preparedBy = :preparedBy"),
    @NamedQuery(name = "HrTdPsvcTrainees.findByPreparedOn", query = "SELECT h FROM HrTdPsvcTrainees h WHERE h.preparedOn = :preparedOn")})
public class HrTdPsvcTrainees implements Serializable {

    @Column(name = "YEAR_OF_TRAINING")
    private Integer yearOfTraining;

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_TD_PSVC_TRAINEES_SEQ")
    @SequenceGenerator(name = "HR_TD_PSVC_TRAINEES_SEQ", sequenceName = "HR_TD_PSVC_TRAINEES_SEQ", allocationSize = 1)
    private Integer id;

    @Column(name = "BATCH_CODE")
    private String batchCode;

    @Size(max = 200)
    @Column(name = "REMARK")
    private String remark;

    @Column(name = "PREPARED_BY")
    private Integer preparedBy;

    @Column(name = "PREPARED_ON")
    private String preparedOn;

    @OneToMany(mappedBy = "traineeId", cascade = CascadeType.ALL)
    private List<HrTdPsvcTraineeDetails> hrTdPsvcTraineeDetailsList = new ArrayList<>();

    public HrTdPsvcTrainees() {
    }

    public HrTdPsvcTrainees(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getYearOfTraining() {
        return yearOfTraining;
    }

    public void setYearOfTraining(Integer yearOfTraining) {
        this.yearOfTraining = yearOfTraining;
    }

    public String getBatchCode() {
        return batchCode;
    }

    public void setBatchCode(String batchCode) {
        this.batchCode = batchCode;
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

    public String getPreparedOn() {
        return preparedOn;
    }

    public void setPreparedOn(String preparedOn) {
        this.preparedOn = preparedOn;
    }

    @XmlTransient
    public List<HrTdPsvcTraineeDetails> getHrTdPsvcTraineeDetailsList() {
        if (hrTdPsvcTraineeDetailsList == null) {
            hrTdPsvcTraineeDetailsList = new ArrayList<>();
        }
        return hrTdPsvcTraineeDetailsList;
    }

    public void setHrTdPsvcTraineeDetailsList(List<HrTdPsvcTraineeDetails> hrTdPsvcTraineeDetailsList) {
        this.hrTdPsvcTraineeDetailsList = hrTdPsvcTraineeDetailsList;
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
        if (!(object instanceof HrTdPsvcTrainees)) {
            return false;
        }
        HrTdPsvcTrainees other = (HrTdPsvcTrainees) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.HrTdPsvcTrainees[ id=" + id + " ]";
    }

    public void add(HrTdPsvcTraineeDetails hrTdPsvcTraineeDetails) {
        if (hrTdPsvcTraineeDetails.getTraineeId() != this) {
            this.getHrTdPsvcTraineeDetailsList().add(hrTdPsvcTraineeDetails);
            hrTdPsvcTraineeDetails.setTraineeId(this);
        }
    }
}
