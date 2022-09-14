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
@Table(name = "HR_TD_PSVC_RESULTS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrTdPsvcResults.findAll", query = "SELECT DISTINCT(h.semister) FROM HrTdPsvcResults h"),
    @NamedQuery(name = "HrTdPsvcResults.findById", query = "SELECT h FROM HrTdPsvcResults h WHERE h.id = :id"),
    @NamedQuery(name = "HrTdPsvcResults.findByTraineeId", query = "SELECT h FROM HrTdPsvcTraineeDetails h WHERE h.traineeId = :traineeId"),
    @NamedQuery(name = "HrTdPsvcResults.findByFromDate", query = "SELECT h FROM HrTdPsvcResults h WHERE h.fromDate = :fromDate"),
    @NamedQuery(name = "HrTdPsvcResults.findByToDate", query = "SELECT h FROM HrTdPsvcResults h WHERE h.toDate = :toDate"),
    @NamedQuery(name = "HrTdPsvcResults.findBySemister", query = "SELECT h FROM HrTdPsvcResults h WHERE h.semister = :semister"),
    @NamedQuery(name = "HrTdPsvcResults.findByPreparedBy", query = "SELECT h FROM HrTdPsvcResults h WHERE h.preparedBy = :preparedBy"),
    @NamedQuery(name = "HrTdPsvcResults.findByPreparedOn", query = "SELECT h FROM HrTdPsvcResults h WHERE h.preparedOn = :preparedOn")})
public class HrTdPsvcResults implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_TD_PSVC_RESULTS_SEQ")
    @SequenceGenerator(name = "HR_TD_PSVC_RESULTS_SEQ", sequenceName = "HR_TD_PSVC_RESULTS_SEQ", allocationSize = 1)
    @NotNull
    @Column(name = "ID")
    private Integer id;
    @Size(max = 20)
    @Column(name = "FROM_DATE")
    private String fromDate;
    @Size(max = 20)
    @Column(name = "TO_DATE")
    private String toDate;
    @Size(max = 20)
    @Column(name = "SEMISTER")
    private String semister;
    @Column(name = "PREPARED_BY")
    private Integer preparedBy;
    @Size(max = 20)
    @Column(name = "PREPARED_ON")
    private String preparedOn;
    @OneToMany(mappedBy = "traineeResultId", cascade = CascadeType.ALL)
    private List<HrTdPsvcResultDetails> hrTdPsvcResultDetailsList;
    @JoinColumn(name = "TRAINEE_DETAIL_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrTdPsvcTraineeDetails traineeDetailId;

    public HrTdPsvcResults() {
    }

    public HrTdPsvcResults(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getSemister() {
        return semister;
    }

    public void setSemister(String semister) {
        this.semister = semister;
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
    public List<HrTdPsvcResultDetails> getHrTdPsvcResultDetailsList() {
        if (hrTdPsvcResultDetailsList == null) {
            hrTdPsvcResultDetailsList = new ArrayList<>();
        }
        return hrTdPsvcResultDetailsList;
    }

    public void setHrTdPsvcResultDetailsList(List<HrTdPsvcResultDetails> hrTdPsvcResultDetailsList) {
        this.hrTdPsvcResultDetailsList = hrTdPsvcResultDetailsList;
    }

    public HrTdPsvcTraineeDetails getTraineeDetailId() {
        return traineeDetailId;
    }

    public void setTraineeDetailId(HrTdPsvcTraineeDetails traineeDetailId) {
        this.traineeDetailId = traineeDetailId;
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
        if (!(object instanceof HrTdPsvcResults)) {
            return false;
        }
        HrTdPsvcResults other = (HrTdPsvcResults) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.hrms.entity.training.HrTdPsvcResults[ id=" + id + " ]";
    }

    public void add(HrTdPsvcResultDetails hrTdPsvcResultDetails) {
        if (hrTdPsvcResultDetails.getTraineeResultId() != this) {
            this.getHrTdPsvcResultDetailsList().add(hrTdPsvcResultDetails);
            hrTdPsvcResultDetails.setTraineeResultId(this);
        }
    }

}
