/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.recruitment;

import et.gov.eep.hrms.entity.employee.HrEmployees;
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
 * @author user
 */
@Entity
@Table(name = "HR_ADVERTISEMENTS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrAdvertisements.findAll", query = "SELECT h FROM HrAdvertisements h "),
    @NamedQuery(name = "HrAdvertisements.findAllDistinict", query = "SELECT  DISTINCT(h.batchCode) FROM HrAdvertisements h"),
    @NamedQuery(name = "HrAdvertisements.findById", query = "SELECT h FROM HrAdvertisements h WHERE h.id = :id"),    
    @NamedQuery(name = "HrAdvertisements.findByAdvertDateFrom", query = "SELECT h FROM HrAdvertisements h WHERE h.advertDateFrom = :advertDateFrom"),
    @NamedQuery(name = "HrAdvertisements.findByAdvertDateTo", query = "SELECT h FROM HrAdvertisements h WHERE h.advertDateTo = :advertDateTo"),
    @NamedQuery(name = "HrAdvertisements.findByAdvertType", query = "SELECT h FROM HrAdvertisements h WHERE h.advertType = :advertType"),
    @NamedQuery(name = "HrAdvertisements.findByBatchCode", query = "SELECT h FROM HrAdvertisements h WHERE h.batchCode = :batchCode"),
    @NamedQuery(name = "HrAdvertisements.findByPreparedOn", query = "SELECT h FROM HrAdvertisements h WHERE h.preparedOn = :preparedOn")})
public class HrAdvertisements implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_ADVERTISEMENTS_SEQ")
    @SequenceGenerator(name = "HR_ADVERTISEMENTS_SEQ", sequenceName = "HR_ADVERTISEMENTS_SEQ", allocationSize = 1)
    @Column(name = "ID")
    private Integer id;
    @Size(max = 100)
    @Column(name = "ADVERT_DATE_FROM")
    private String advertDateFrom;
    @Size(max = 100)
    @Column(name = "ADVERT_DATE_TO")
    private String advertDateTo;
    @Size(max = 100)
    @Column(name = "ADVERT_TYPE")
    private String advertType;
    @Size(max = 100)
    @Column(name = "BATCH_CODE")
    private String batchCode;
    @Size(max = 100)
    @Column(name = "PREPARED_ON")
    private String preparedOn;
    @OneToMany(mappedBy = "advertId", cascade = CascadeType.ALL)
    private List<HrMedias> hrMediasList;
    @JoinColumn(name = "PREPARED_BY", referencedColumnName = "ID")
    @ManyToOne
    private HrEmployees preparedBy;
    @OneToMany(mappedBy = "advertId", cascade = CascadeType.ALL)
    private List<HrAdvertisedJobs> hrAdvertisedJobsList = new ArrayList<>();
//   @OneToMany(mappedBy = "batchCode")
//    private List<HrRecruitmentRequests> hrRecruitmentRequestsList;

    /**
     *
     */
    public HrAdvertisements() {
    }

    public HrAdvertisements(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public String getAdvertDateFrom() {
        return advertDateFrom;
    }

    /**
     *
     * @param advertDateFrom
     */
    public void setAdvertDateFrom(String advertDateFrom) {
        this.advertDateFrom = advertDateFrom;
    }

    /**
     *
     * @return
     */
    public String getAdvertDateTo() {
        return advertDateTo;
    }

    /**
     *
     * @param advertDateTo
     */
    public void setAdvertDateTo(String advertDateTo) {
        this.advertDateTo = advertDateTo;
    }

    /**
     *
     * @return
     */
    public String getAdvertType() {
        return advertType;
    }

    /**
     *
     * @param advertType
     */
    public void setAdvertType(String advertType) {
        this.advertType = advertType;
    }

    /**
     *
     * @return
     */
    public String getBatchCode() {
        return batchCode;
    }

    /**
     *
     * @param batchCode
     */
    public void setBatchCode(String batchCode) {
        this.batchCode = batchCode;
    }

    /**
     *
     * @return
     */
    public String getPreparedOn() {
        return preparedOn;
    }

    /**
     *
     * @param preparedOn
     */
    public void setPreparedOn(String preparedOn) {
        this.preparedOn = preparedOn;
    }

    /**
     *
     * @return
     */
    @XmlTransient
    public List<HrMedias> getHrMediasList() {
        if (hrMediasList == null) {
            hrMediasList = new ArrayList<>();
        }
        return hrMediasList;
    }

    /**
     *
     * @param hrMediasList
     */
    public void setHrMediasList(List<HrMedias> hrMediasList) {
        this.hrMediasList = hrMediasList;
    }

    /**
     *
     * @return
     */
    public HrEmployees getPreparedBy() {
        return preparedBy;
    }

    /**
     *
     * @param preparedBy
     */
    public void setPreparedBy(HrEmployees preparedBy) {
        this.preparedBy = preparedBy;
    }

    /**
     *
     * @param hrMedias
     */
    public void addToHrMedias(HrMedias hrMedias) {
        if (hrMedias.getAdvertId() != this) {
            this.getHrMediasList().add(hrMedias);
            hrMedias.setAdvertId(this);
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
        if (!(object instanceof HrAdvertisements)) {
            return false;
        }
        HrAdvertisements other = (HrAdvertisements) object;
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
    public List<HrAdvertisedJobs> getHrAdvertisedJobsList() {
        if(hrAdvertisedJobsList == null){
            hrAdvertisedJobsList= new ArrayList<>();
        }
        return hrAdvertisedJobsList;
    }

    public void setHrAdvertisedJobsList(List<HrAdvertisedJobs> hrAdvertisedJobsList) {
        this.hrAdvertisedJobsList = hrAdvertisedJobsList;
    }

//    @XmlTransient
//    public List<HrAdvertisedJobs> getHrAdvertisedJobsList() {
//        return hrAdvertisedJobsList;
//    }
//    public void setHrAdvertisedJobsList(List<HrAdvertisedJobs> hrAdvertisedJobsList) {
//        this.hrAdvertisedJobsList = hrAdvertisedJobsList;
//    }
}
