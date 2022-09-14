/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.commonApplications.entity;

import et.gov.eep.fcms.entity.Bond.FmsBondForeign;
import et.gov.eep.prms.entity.PrmsBidOpeningChecklstDt;
import et.gov.eep.prms.entity.PrmsLcRigistration;
import et.gov.eep.prms.entity.PrmsLcRigistrationAmend;
import et.gov.eep.prms.entity.PrmsMarketAssessmentDetail;
import et.gov.eep.prms.entity.PrmsServiceProvider;
import et.gov.eep.prms.entity.PrmsSupplyProfile;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author mubejbl
 */
@Entity
@Table(name = "COM_LU_COUNTRY")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ComLuCountry.findAll", query = "SELECT c FROM ComLuCountry c"),
    @NamedQuery(name = "ComLuCountry.findById", query = "SELECT c FROM ComLuCountry c WHERE c.id = :id"),
    @NamedQuery(name = "ComLuCountry.findCountryLists", query = "SELECT c FROM ComLuCountry c ORDER BY c.country"),
    @NamedQuery(name = "ComLuCountry.findByCountry", query = "SELECT c FROM ComLuCountry c WHERE c.country = :country")})

public class ComLuCountry implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "countryId")
    private List<PrmsSupplyProfile> prmsSupplyProfileList;
    @OneToMany(mappedBy = "countryId")
    private List<PrmsLcRigistrationAmend> prmsLcRigistrationAmendList;
    @OneToMany(mappedBy = "countryId")
    private List<PrmsLcRigistration> prmsLcRigistrationList;
    @OneToMany(mappedBy = "countryId")
    private List<PrmsServiceProvider> prmsServiceProviderList;
    @OneToMany(mappedBy = "countryId")
    private List<PrmsMarketAssessmentDetail> prmsMarketAssessmentDetailList;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "countryBondIssued")
    private Collection<FmsBondForeign> fmsBondForeignCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "countryId", fetch = FetchType.LAZY)
    private List<PrmsBidOpeningChecklstDt> prmsBidOpeningChecklstDts;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Integer id;
    @Size(max = 255)
    @Column(name = "COUNTRY")
    private String country;
      @Size(max = 20)
    @Column(name = "CALLING_CODE")
    private String callingCode;

    public ComLuCountry() {
    }

    public ComLuCountry(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCallingCode() {
        return callingCode;
    }

    public void setCallingCode(String callingCode) {
        this.callingCode = callingCode;
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
        if (!(object instanceof ComLuCountry)) {
            return false;
        }
        ComLuCountry other = (ComLuCountry) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return country;
    }

    @XmlTransient
    public Collection<FmsBondForeign> getFmsBondForeignCollection() {
        return fmsBondForeignCollection;
    }

    public void setFmsBondForeignCollection(Collection<FmsBondForeign> fmsBondForeignCollection) {
        this.fmsBondForeignCollection = fmsBondForeignCollection;
    }

    public List<PrmsMarketAssessmentDetail> getPrmsMarketAssessmentDetailList() {
        return prmsMarketAssessmentDetailList;
    }

    public void setPrmsMarketAssessmentDetailList(List<PrmsMarketAssessmentDetail> prmsMarketAssessmentDetailList) {
        this.prmsMarketAssessmentDetailList = prmsMarketAssessmentDetailList;
    }

    @XmlTransient
    public List<PrmsLcRigistration> getPrmsLcRigistrationList() {
        return prmsLcRigistrationList;
    }

    public void setPrmsLcRigistrationList(List<PrmsLcRigistration> prmsLcRigistrationList) {
        this.prmsLcRigistrationList = prmsLcRigistrationList;
    }

    @XmlTransient
    public List<PrmsServiceProvider> getPrmsServiceProviderList() {
        if (prmsServiceProviderList == null) {
            prmsServiceProviderList = new ArrayList<>();
        }
        return prmsServiceProviderList;
    }

    public void setPrmsServiceProviderList(List<PrmsServiceProvider> prmsServiceProviderList) {
        this.prmsServiceProviderList = prmsServiceProviderList;
    }

    @XmlTransient
    public List<PrmsLcRigistrationAmend> getPrmsLcRigistrationAmendList() {
        return prmsLcRigistrationAmendList;
    }

    public void setPrmsLcRigistrationAmendList(List<PrmsLcRigistrationAmend> prmsLcRigistrationAmendList) {
        this.prmsLcRigistrationAmendList = prmsLcRigistrationAmendList;
    }

    @XmlTransient

    public List<PrmsBidOpeningChecklstDt> getPrmsBidOpeningChecklstDts() {
        if (prmsBidOpeningChecklstDts == null) {
            prmsBidOpeningChecklstDts = new ArrayList<>();
        }
        return prmsBidOpeningChecklstDts;
    }

    public void setPrmsBidOpeningChecklstDts(List<PrmsBidOpeningChecklstDt> prmsBidOpeningChecklstDts) {
        this.prmsBidOpeningChecklstDts = prmsBidOpeningChecklstDts;
    }

    @XmlTransient

    public List<PrmsSupplyProfile> getPrmsSupplyProfileList() {
        if (prmsSupplyProfileList == null) {
            prmsSupplyProfileList = new ArrayList<>();
        }
        return prmsSupplyProfileList;
    }

    public void setPrmsSupplyProfileList(List<PrmsSupplyProfile> prmsSupplyProfileList) {
        this.prmsSupplyProfileList = prmsSupplyProfileList;
    }

    /**
     * @return the callingCode
     */
//    public String getCallingCode() {
//        return callingCode;
//    }
//
//    /**
//     * @param callingCode the callingCode to set
//     */
//    public void setCallingCode(String callingCode) {
//        this.callingCode = callingCode;
//    }

}
