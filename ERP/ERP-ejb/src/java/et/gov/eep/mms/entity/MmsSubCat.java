/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.entity;

import et.gov.eep.prms.entity.PrmsMarketAssessmentDetail;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author user
 */
@Entity
@Table(name = "MMS_SUB_CAT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MmsSubCat.findAll", query = "SELECT m FROM MmsSubCat m"),
    @NamedQuery(name = "MmsSubCat.findBySubCatId", query = "SELECT m FROM MmsSubCat m WHERE m.subCatId = :subCatId"),
    @NamedQuery(name = "MmsSubCat.findBySubCatName", query = "SELECT m FROM MmsSubCat m WHERE m.catId.catName = :subCatName"),
    @NamedQuery(name = "MmsSubCat.findBySubCatNameDup", query = "SELECT m FROM MmsSubCat m WHERE m.subCatName = :subCatName"),
    @NamedQuery(name = "MmsSubCat.findBySubCatCodes", query = "SELECT m.subcatCode FROM MmsSubCat m WHERE m.subCatName = :subCatName"),
    @NamedQuery(name = "MmsSubCat.findBySubCatDescription", query = "SELECT m FROM MmsSubCat m WHERE m.subCatDescription = :subCatDescription"),
    @NamedQuery(name = "MmsSubCat.findByMaterialCode", query = "SELECT m FROM MmsSubCat m WHERE m.subcatCode = :materialCode")})
public class MmsSubCat implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MMS_SUB_CAT_SEQ")
    @SequenceGenerator(name = "MMS_SUB_CAT_SEQ", sequenceName = "MMS_SUB_CAT_SEQ", allocationSize = 1)
    @Column(name = "SUB_CAT_ID")
    private Integer subCatId;
    @Size(max = 250)
    @Column(name = "SUB_CAT_NAME")
    private String subCatName;
    @Size(max = 250)
    @Column(name = "SUB_CAT_DESCRIPTION")
    private String subCatDescription;
    @Size(max = 50)
    @Column(name = "SUBCAT_CODE")
    private String subcatCode;
    @Size(max = 255)
    @Column(name = "GL_CODE")
    private String glCode;
    
    @JoinColumn(name = "CAT_ID", referencedColumnName = "CAT_ID")
    @ManyToOne
    private MmsCategory catId;
    @OneToMany(mappedBy = "matSubcategory")
    private List<MmsItemRegistration> mmsItemRegistrationList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "subCategoryId")
    private List<PrmsMarketAssessmentDetail> prmsMarketAssessmentDetailList;

    /**
     *
     */
    public MmsSubCat() {
    }

    /**
     *
     * @param subCatId
     */
    public MmsSubCat(Integer subCatId) {
        this.subCatId = subCatId;
    }

    /**
     *
     * @return
     */
    public Integer getSubCatId() {
        return subCatId;
    }

    /**
     *
     * @param subCatId
     */
    public void setSubCatId(Integer subCatId) {
        this.subCatId = subCatId;
    }

    /**
     *
     * @return
     */
    public String getSubCatName() {
        return subCatName;
    }

    /**
     *
     * @param subCatName
     */
    public void setSubCatName(String subCatName) {
        this.subCatName = subCatName;
    }

    /**
     *
     * @return
     */
    public String getSubCatDescription() {
        return subCatDescription;
    }

    /**
     *
     * @param subCatDescription
     */
    public void setSubCatDescription(String subCatDescription) {
        this.subCatDescription = subCatDescription;
    }

    public String getSubcatCode() {
        return subcatCode;
    }

    public void setSubcatCode(String subcatCode) {
        this.subcatCode = subcatCode;
    }
    
   

    /**
     *
     * @return
     */
    public MmsCategory getCatId() {
        return catId;
    }

    /**
     *
     * @param catId
     */
    public void setCatId(MmsCategory catId) {
        this.catId = catId;
    }

    public String getGlCode() {
        return glCode;
    }

    public void setGlCode(String glCode) {
        this.glCode = glCode;
    }
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (subCatId != null ? subCatId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MmsSubCat)) {
            return false;
        }
        MmsSubCat other = (MmsSubCat) object;
        if ((this.subCatId == null && other.subCatId != null) || (this.subCatId != null && !this.subCatId.equals(other.subCatId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return subCatName;
    }

    public List<MmsItemRegistration> getMmsItemRegistrationList() {
        return mmsItemRegistrationList;
    }

    public void setMmsItemRegistrationList(List<MmsItemRegistration> mmsItemRegistrationList) {
        this.mmsItemRegistrationList = mmsItemRegistrationList;
    }

    @XmlTransient

    public List<PrmsMarketAssessmentDetail> getPrmsMarketAssessmentDetailList() {
        return prmsMarketAssessmentDetailList;
    }

    public void setPrmsMarketAssessmentDetailList(List<PrmsMarketAssessmentDetail> prmsMarketAssessmentDetailList) {
        this.prmsMarketAssessmentDetailList = prmsMarketAssessmentDetailList;
    }
    
}
