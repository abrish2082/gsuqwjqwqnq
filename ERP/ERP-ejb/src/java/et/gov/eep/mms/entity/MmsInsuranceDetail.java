
package et.gov.eep.mms.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author w_station
 */
@Entity
@Table(name = "MMS_INSURANCE_DETAIL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MmsInsuranceDetail.findAll", query = "SELECT m FROM MmsInsuranceDetail m"),
    @NamedQuery(name = "MmsInsuranceDetail.findByInsDetailId", query = "SELECT m FROM MmsInsuranceDetail m WHERE m.insDetailId = :insDetailId"),
    @NamedQuery(name = "MmsInsuranceDetail.frindByInsId", query = "SELECT m FROM MmsInsuranceDetail m WHERE m.insId.insId = :insId"),
    @NamedQuery(name = "MmsInsuranceDetail.findByCatagory", query = "SELECT m FROM MmsInsuranceDetail m WHERE m.catagory = :catagory"),
    @NamedQuery(name = "MmsInsuranceDetail.findByCost", query = "SELECT m FROM MmsInsuranceDetail m WHERE m.cost = :cost"),
    @NamedQuery(name = "MmsInsuranceDetail.findByDescription", query = "SELECT m FROM MmsInsuranceDetail m WHERE m.description = :description"),
    @NamedQuery(name = "MmsInsuranceDetail.findByInsuranceType", query = "SELECT m FROM MmsInsuranceDetail m WHERE m.insuranceType = :insuranceType"),
    @NamedQuery(name = "MmsInsuranceDetail.findByInsuranceValue", query = "SELECT m FROM MmsInsuranceDetail m WHERE m.insuranceValue = :insuranceValue"),
    @NamedQuery(name = "MmsInsuranceDetail.findByInsuranceYear", query = "SELECT m FROM MmsInsuranceDetail m WHERE m.insuranceYear = :insuranceYear"),
    @NamedQuery(name = "MmsInsuranceDetail.findByRegion", query = "SELECT m FROM MmsInsuranceDetail m WHERE m.region = :region"),
    @NamedQuery(name = "MmsInsuranceDetail.findBySivNo", query = "SELECT m FROM MmsInsuranceDetail m WHERE m.sivNo = :sivNo"),
    @NamedQuery(name = "MmsInsuranceDetail.findByTagNo", query = "SELECT m FROM MmsInsuranceDetail m WHERE m.tagNo = :tagNo")})
public class MmsInsuranceDetail implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id 
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "INSURANCE_DET_SEQ")
    @SequenceGenerator(name = "INSURANCE_DET_SEQ", sequenceName = "INSURANCE_DET_SEQ", allocationSize = 1)
    @Column(name = "INS_DETAIL_ID")
    private Integer insDetailId;
    @Size(max = 30)
    @Column(name = "CATAGORY", length = 30)
    private String catagory;
    @Size(max = 30)
    @Column(name = "COST", length = 30)
    private String cost;
    @Size(max = 50)
    @Column(name = "DESCRIPTION", length = 50)
    private String description;
    @Size(max = 30)
    @Column(name = "INSURANCE_TYPE", length = 30)
    private String insuranceType;
    @Column(name = "INSURANCE_VALUE")
    private Integer insuranceValue;
    @Size(max = 30)
    @Column(name = "INSURANCE_YEAR", length = 30)
    private String insuranceYear;
    @Size(max = 50)
    @Column(name = "REGION", length = 50)
    private String region;
    @Size(max = 30)
    @Column(name = "SIV_NO", length = 30)
    private String sivNo;
    @Size(max = 190)
    @Column(name = "TAG_NO", length = 190)
    private String tagNo;
    @Size(max = 190)
    @Column(name = "FA_CODE" , length = 190)
    private String faCode;
    @Size(max = 190)
    @Column(name = "ASSAT_NAME" , length = 190)
    private String assetName;
    @Size(max = 50)
    @Column(name = "ACCOUNT_CODE", length = 50)
    private String accountCode;
    @Column(name = "SELECT_OPT")    
    private Integer selectOpt;
    @Size(max = 70)
    @Column(name = "TRANSACTION_NO", length = 70)
    private String transaction_no;
    @JoinColumn(name = "INS_ID", referencedColumnName = "INS_ID")
    @ManyToOne
    private MmsInsurance insId;

    public MmsInsuranceDetail() {
    }

    public MmsInsuranceDetail(Integer insDetailId) {
        this.insDetailId = insDetailId;
    }

    public Integer getInsDetailId() {
        return insDetailId;
    }

    public void setInsDetailId(Integer insDetailId) {
        this.insDetailId = insDetailId;
    }

    public String getCatagory() {
        return catagory;
    }

    public void setCatagory(String catagory) {
        this.catagory = catagory;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInsuranceType() {
        return insuranceType;
    }

    public void setInsuranceType(String insuranceType) {
        this.insuranceType = insuranceType;
    }

    public String getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    public String getTransaction_no() {
        return transaction_no;
    }

    public void setTransaction_no(String transaction_no) {
        this.transaction_no = transaction_no;
    }
    
    public Integer getInsuranceValue() {
        return insuranceValue;
    }

    public void setInsuranceValue(Integer insuranceValue) {
        this.insuranceValue = insuranceValue;
    }

    public Integer getSelectOpt() {
        return selectOpt;
    }

    public void setSelectOpt(Integer selectOpt) {
        this.selectOpt = selectOpt;
    }
    
    public String getInsuranceYear() {
        return insuranceYear;
    }

    public void setInsuranceYear(String insuranceYear) {
        this.insuranceYear = insuranceYear;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getSivNo() {
        return sivNo;
    }

    public void setSivNo(String sivNo) {
        this.sivNo = sivNo;
    }

    public String getTagNo() {
        return tagNo;
    }

    public void setTagNo(String tagNo) {
        this.tagNo = tagNo;
    }

    public String getFaCode() {
        return faCode;
    }

    public void setFaCode(String faCode) {
        this.faCode = faCode;
    }

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public MmsInsurance getInsId() {
        return insId;
    }

    public void setInsId(MmsInsurance insId) {
        this.insId = insId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (insDetailId != null ? insDetailId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        
        if (!(object instanceof MmsInsuranceDetail)) {
            return false;
        }
        MmsInsuranceDetail other = (MmsInsuranceDetail) object;
        if ((this.insDetailId == null && other.insDetailId != null) || (this.insDetailId != null && !this.insDetailId.equals(other.insDetailId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "newEntitiy.MmsInsuranceDetail[ insDetailId=" + insDetailId + " ]";
    }
    
}
