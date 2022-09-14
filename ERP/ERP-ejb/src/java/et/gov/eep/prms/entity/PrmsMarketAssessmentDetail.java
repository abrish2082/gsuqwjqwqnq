/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.entity;

import et.gov.eep.commonApplications.entity.ComLuCountry;
import et.gov.eep.fcms.entity.FmsLuCurrency;
import et.gov.eep.mms.entity.MmsCategory;
import et.gov.eep.mms.entity.MmsItemRegistration;
import et.gov.eep.mms.entity.MmsServiceReg;
import et.gov.eep.mms.entity.MmsSubCat;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author user
 */
@Entity
@Table(name = "PRMS_MARKET_ASSESSMENT_DETAIL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrmsMarketAssessmentDetail.findAll", query = "SELECT p FROM PrmsMarketAssessmentDetail p"),
    @NamedQuery(name = "PrmsMarketAssessmentDetail.findById", query = "SELECT p FROM PrmsMarketAssessmentDetail p WHERE p.id = :id"),
    @NamedQuery(name = "PrmsMarketAssessmentDetail.findByAvailabilty", query = "SELECT p FROM PrmsMarketAssessmentDetail p WHERE p.availabilty = :availabilty"),
    @NamedQuery(name = "PrmsMarketAssessmentDetail.findByQuantity", query = "SELECT p FROM PrmsMarketAssessmentDetail p WHERE p.quantity = :quantity"),
    @NamedQuery(name = "PrmsMarketAssessmentDetail.findByItemNam", query = "SELECT p FROM PrmsMarketAssessmentDetail p WHERE p.marketAssmntId.marketAssNo LIKE :item"),
    @NamedQuery(name = "PrmsMarketAssessmentDetail.findByItemName", query = "SELECT p FROM PrmsMarketAssessmentDetail p WHERE p.itemId IS NOT NULL"),

    @NamedQuery(name = "PrmsMarketAssessmentDetail.searchINameByLatestDate", query = "SELECT DISTINCT(PRD) FROM PrmsMarketAssessment p join PrmsMarketAssessmentDetail PRD where p.registrationDate=(select max(p.registrationDate) from PrmsMarketAssessment p)"),

    @NamedQuery(name = "PrmsMarketAssessmentDetail.findByItemNm", query = "SELECT DISTINCT p FROM PrmsMarketAssessmentDetail p WHERE p.id=:itemNm"),
    @NamedQuery(name = "PrmsMarketAssessmentDetail.findByMarketAssNo", query = "SELECT p FROM PrmsMarketAssessmentDetail p WHERE p.marketAssmntId.marketAssNo =:assNo"),
    @NamedQuery(name = "PrmsMarketAssessmentDetail.findByMarketNo", query = "SELECT p FROM PrmsMarketAssessmentDetail p WHERE p.id = :serviceName"),
    @NamedQuery(name = "PrmsMarketAssessmentDetail.findByUnitPrice", query = "SELECT p FROM PrmsMarketAssessmentDetail p WHERE p.unitPrice = :unitPrice"),
    @NamedQuery(name = "PrmsMarketAssessmentDetail.findByRemark", query = "SELECT p FROM PrmsMarketAssessmentDetail p WHERE p.remark = :remark")})
public class PrmsMarketAssessmentDetail implements Serializable {

    @OneToMany(mappedBy = "marktAssDtlId")
    private List<PrmsPurchaseRequestDet> prmsPurchaseRequestDetList;

    @JoinColumn(name = "ITEM_ID", referencedColumnName = "MATERIAL_ID")
    @ManyToOne
    private MmsItemRegistration itemId;
    @JoinColumn(name = "CATEGORY_ID", referencedColumnName = "CAT_ID")
    @ManyToOne
    private MmsCategory categoryId;
    @JoinColumn(name = "SUB_CATEGORY_ID", referencedColumnName = "SUB_CAT_ID")
    @ManyToOne
    private MmsSubCat subCategoryId;
    @JoinColumn(name = "COUNTRY_ID", referencedColumnName = "ID")
    @ManyToOne
    private ComLuCountry countryId;
    @JoinColumn(name = "CURRENCY_ID", referencedColumnName = "CURRENCY_ID")
    @ManyToOne
    private FmsLuCurrency currencyId;
    @Column(name = "QUANTITY")
    private Integer quantity;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "UNIT_PRICE")
    private BigDecimal unitPrice;
    @JoinColumn(name = "SERVICE_ID", referencedColumnName = "SERVICE_ID")
    @ManyToOne
    private MmsServiceReg serviceId;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "ID", nullable = false, length = 20)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EE_MRKET_ASSMNT_DTLINFO_SEQ_GENERATOR")
    @SequenceGenerator(name = "EE_MRKET_ASSMNT_DTLINFO_SEQ_GENERATOR", sequenceName = "EE_MRKET_ASSMNT_DTLINFO_SEQ", allocationSize = 1)
    private String id;
    @Size(max = 20)
    @Column(name = "AVAILABILTY", length = 20)
    private String availabilty;
    @Column(name = "UNIT_MEASURE", length = 20)
    private String unitMeasure;
    @Size(max = 200)
    @Column(name = "REMARK", length = 200)
    private String remark;
    @Size(max = 20)
    @Column(name = "COUNTRY_OF_ORIGIN", length = 20)
    private String countryOfOrigin;
    @Size(max = 20)
    @Column(name = "PURCHASE_TYPE", length = 20)
    private String purchasetype;
    @Size(max = 20)
    @Column(name = "SOURCE_OF_ASSESSMENT", length = 20)
    private String sourceOfAssessment;
    @Column(name = "FROM_YEAR")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fromYear;
    @Column(name = "TO_YEAR")
    @Temporal(TemporalType.TIMESTAMP)
    private Date toYear;
    @JoinColumn(name = "MARKET_ASSMNT_ID", referencedColumnName = "ID")
    @ManyToOne
    private PrmsMarketAssessment marketAssmntId;

    /**
     *
     */
    public PrmsMarketAssessmentDetail() {
    }

    /**
     *
     * @param id
     */
    public PrmsMarketAssessmentDetail(String id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public String getAvailabilty() {
        return availabilty;
    }

    /**
     *
     * @param availabilty
     */
    public void setAvailabilty(String availabilty) {
        this.availabilty = availabilty;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    /**
     *
     * @return
     */
    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    /**
     *
     * @return
     */
    public String getRemark() {
        return remark;
    }

    /**
     *
     * @param remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     *
     * @return
     */
    /**
     *
     * @return
     */
    public PrmsMarketAssessment getMarketAssmntId() {
        return marketAssmntId;
    }

    /**
     *
     * @param marketAssmntId
     */
    public void setMarketAssmntId(PrmsMarketAssessment marketAssmntId) {
        this.marketAssmntId = marketAssmntId;
    }

    public String getUnitMeasure() {
        return unitMeasure;
    }

    public void setUnitMeasure(String unitMeasure) {
        this.unitMeasure = unitMeasure;
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
        if (!(object instanceof PrmsMarketAssessmentDetail)) {
            return false;
        }
        PrmsMarketAssessmentDetail other = (PrmsMarketAssessmentDetail) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return id;
    }

    public MmsServiceReg getServiceId() {
        return serviceId;
    }

    public void setServiceId(MmsServiceReg serviceId) {
        this.serviceId = serviceId;
    }

    public MmsItemRegistration getItemId() {
        return itemId;
    }

    public void setItemId(MmsItemRegistration itemId) {
        this.itemId = itemId;
    }

    @XmlTransient
    public List<PrmsPurchaseRequestDet> getPrmsPurchaseRequestDetList() {
        return prmsPurchaseRequestDetList;
    }

    public void setPrmsPurchaseRequestDetList(List<PrmsPurchaseRequestDet> prmsPurchaseRequestDetList) {
        this.prmsPurchaseRequestDetList = prmsPurchaseRequestDetList;
    }

    public String getCountryOfOrigin() {
        return countryOfOrigin;
    }

    public void setCountryOfOrigin(String countryOfOrigin) {
        this.countryOfOrigin = countryOfOrigin;
    }

    public String getPurchasetype() {
        return purchasetype;
    }

    public void setPurchasetype(String purchasetype) {
        this.purchasetype = purchasetype;
    }

    public String getSourceOfAssessment() {
        return sourceOfAssessment;
    }

    public void setSourceOfAssessment(String sourceOfAssessment) {
        this.sourceOfAssessment = sourceOfAssessment;
    }

    public Date getFromYear() {
        return fromYear;
    }

    public void setFromYear(Date fromYear) {
        this.fromYear = fromYear;
    }

    public Date getToYear() {
        return toYear;
    }

    public void setToYear(Date toYear) {
        this.toYear = toYear;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public MmsCategory getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(MmsCategory categoryId) {
        this.categoryId = categoryId;
    }

    public MmsSubCat getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(MmsSubCat subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public ComLuCountry getCountryId() {
        return countryId;
    }

    public void setCountryId(ComLuCountry countryId) {
        this.countryId = countryId;
    }

    public FmsLuCurrency getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(FmsLuCurrency currencyId) {
        this.currencyId = currencyId;
    }

}
