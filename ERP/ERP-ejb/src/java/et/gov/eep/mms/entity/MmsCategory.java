/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.entity;

import et.gov.eep.prms.entity.PrmsMarketAssessmentDetail;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author user
 */
@Entity
@Table(name = "MMS_CATEGORY")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MmsCategory.findAll", query = "SELECT m FROM MmsCategory m ORDER BY m.catName"),
    @NamedQuery(name = "MmsCategory.findByCatId", query = "SELECT m FROM MmsCategory m WHERE m.catId = :catId"),
    @NamedQuery(name = "MmsCategory.findCattname", query = "SELECT DISTINCT m FROM MmsCategory m INNER JOIN MmsItemRegistration h WHERE  m.catName IS NOT NULL AND h.matCategory IS NOT NULL"),
    @NamedQuery(name = "MmsCategory.findByCatName", query = "SELECT m FROM MmsCategory m WHERE m.catName = :catName"),
    @NamedQuery(name = "MmsCategory.findByCatCodes", query = "SELECT m.catCode FROM MmsCategory m WHERE m.catName = :catName"),
    @NamedQuery(name = "MmsCategory.findByCatCode", query = "SELECT m FROM MmsCategory m WHERE m.catCode = :cCode"),
    @NamedQuery(name = "MmsCategory.findByCatNameLike", query = "SELECT m FROM MmsCategory m WHERE m.catName LIKE :catName"),
    @NamedQuery(name = "MmsCategory.findByCatDescription", query = "SELECT m FROM MmsCategory m WHERE m.catDescription = :catDescription")})
public class MmsCategory implements Serializable {

    @Size(max = 250)
    @Column(name = "CAT_CODE")
    private String catCode;

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MMS_CAT_SEQ")
    @SequenceGenerator(name = "MMS_CAT_SEQ", sequenceName = "MMS_CAT_SEQ", allocationSize = 1)
    @Column(name = "CAT_ID")
    private Integer catId;
    @Size(max = 250)
    @Column(name = "CAT_NAME")
    private String catName;
    @Size(max = 250)
    @Column(name = "CAT_DESCRIPTION")
    private String catDescription;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "catId")
    private List<MmsSubCat> mmsSubCatList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "categoryId")
    private List<PrmsMarketAssessmentDetail> prmsMarketAssessmentDetailList;
    
    @OneToMany(mappedBy = "matCategory")
    private List<MmsItemRegistration> mmsItemRegistrationList;

    /**
     *
     */
    public MmsCategory() {
    }

    /**
     *
     * @param catId
     */
    public MmsCategory(int catId) {
        this.catId = catId;
    }

    /**
     *
     * @return
     */
    public Integer getCatId() {
        return catId;
    }

    /**
     *
     * @param catId
     */
    public void setCatId(Integer catId) {
        this.catId = catId;
    }

    /**
     *
     * @return
     */
    public String getCatName() {
        return catName;
    }

    /**
     *
     * @param catName
     */
    public void setCatName(String catName) {
        this.catName = catName;
    }

    /**
     *
     * @return
     */
    public String getCatDescription() {
        return catDescription;
    }

    /**
     *
     * @param catDescription
     */
    public void setCatDescription(String catDescription) {
        this.catDescription = catDescription;
    }

    /**
     *
     * @return
     */
    @XmlTransient
    public List<MmsSubCat> getMmsSubCatList() {
        if (mmsSubCatList == null) {
            mmsSubCatList = new ArrayList<>();
        }
        return mmsSubCatList;
    }

    /**
     *
     * @param mmsSubCatList
     */
    public void setMmsSubCatList(List<MmsSubCat> mmsSubCatList) {
        this.mmsSubCatList = mmsSubCatList;
    }

    @XmlTransient
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

    @Override
    public int hashCode() {
        int hash = 0;

        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MmsCategory)) {
            return false;
        }
        MmsCategory other = (MmsCategory) object;

        return true;
    }

    @Override
    public String toString() {
        return catName;
    }

    /**
     *
     * @return
     */
    public String getCatCode() {
        return catCode;
    }

    /**
     *
     * @param catCode
     */
    public void setCatCode(String catCode) {
        this.catCode = catCode;
    }

    /**
     *
     * @param subCategory
     */
    public void add(MmsSubCat subCategory) {
        if (subCategory.getCatId() != null) {
            this.getMmsSubCatList().add(subCategory);
            subCategory.setCatId(this);
        }

    }

}
