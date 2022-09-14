package et.gov.eep.fcms.entity.stock;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author muller
 */
@Entity
@Table(name = "FMS_STOCK_LEDGER_CARD")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsStockLedgerCard.findAll", query = "SELECT f FROM FmsStockLedgerCard f"),
    @NamedQuery(name = "FmsStockLedgerCard.findById", query = "SELECT f FROM FmsStockLedgerCard f WHERE f.id = :id"),
    @NamedQuery(name = "FmsStockLedgerCard.findByMaterialCode", query = "SELECT f FROM FmsStockLedgerCard f WHERE f.materialCode = :materialCode"),
    @NamedQuery(name = "FmsStockLedgerCard.findByMatCode", query = "SELECT f FROM FmsStockLedgerCard f WHERE UPPER(f.materialCode) LIKE :materialCode"),
    @NamedQuery(name = "FmsStockLedgerCard.findByStoreNo", query = "SELECT f FROM FmsStockLedgerCard f WHERE f.storeNo LIKE :storeNo"),
    @NamedQuery(name = "FmsStockLedgerCard.findByAll", query = "SELECT f FROM FmsStockLedgerCard f WHERE UPPER(f.materialCode) LIKE :materialCode and f.storeNo LIKE :storeNo"),
    @NamedQuery(name = "FmsStockLedgerCard.findByRefNo", query = "SELECT f FROM FmsStockLedgerCard f WHERE f.refNo = :refNo"),
    @NamedQuery(name = "FmsStockLedgerCard.findByRefType", query = "SELECT f FROM FmsStockLedgerCard f WHERE f.refType = :refType"),
    @NamedQuery(name = "FmsStockLedgerCard.findByCurrentPrice", query = "SELECT f FROM FmsStockLedgerCard f WHERE f.currentPrice = :currentPrice"),
    @NamedQuery(name = "FmsStockLedgerCard.findByCurrentQuantity", query = "SELECT f FROM FmsStockLedgerCard f WHERE f.currentQuantity = :currentQuantity"),
    @NamedQuery(name = "FmsStockLedgerCard.findByCurrentTotalQuantity", query = "SELECT f FROM FmsStockLedgerCard f WHERE f.currentTotalQuantity = :currentTotalQuantity"),
    @NamedQuery(name = "FmsStockLedgerCard.findByPrvQuantity", query = "SELECT f FROM FmsStockLedgerCard f WHERE f.prvQuantity = :prvQuantity"),
    @NamedQuery(name = "FmsStockLedgerCard.findByWeightAvgPrice", query = "SELECT f FROM FmsStockLedgerCard f WHERE f.weightAvgPrice = :weightAvgPrice"),
    @NamedQuery(name = "FmsStockLedgerCard.findByMaterialId", query = "SELECT f FROM FmsStockLedgerCard f WHERE f.materialId = :materialId"),
    @NamedQuery(name = "FmsStockLedgerCard.findByMaterialName", query = "SELECT f FROM FmsStockLedgerCard f WHERE f.materialName = :materialName"),
    @NamedQuery(name = "FmsStockLedgerCard.findByAccountCode", query = "SELECT f FROM FmsStockLedgerCard f WHERE f.accountCode = :accountCode"),
    @NamedQuery(name = "FmsStockLedgerCard.findByStoreNo1", query = "SELECT f FROM FmsStockLedgerCard f WHERE f.storeNo = :storeNo")})

@SqlResultSetMapping(name = "OrderResultsMmsSivDetail",
        entities = {
            @EntityResult(entityClass = FmsStockLedgerCard.class, fields = {
                @FieldResult(name = "currentPrice", column = "U_PRICE"),
                @FieldResult(name = "currentQuantity", column = "APPROVED_QNTY"),
                @FieldResult(name = "weightAvgPrice", column = "WT_AVG_PR"),
                @FieldResult(name = "currentTotalQuantity", column = "Cur_Total_Qantity"),
                @FieldResult(name = "id", column = "MATERIAL_ID")})})
public class FmsStockLedgerCard implements Serializable {

    private static final long serialVersionUID = 1L;
    //<editor-fold defaultstate="collapsed" desc="variable declaration">
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FMS_STOCK_ITEM_INITIAL")
    @SequenceGenerator(name = "FMS_STOCK_ITEM_INITIAL", sequenceName = "FMS_STOCK_ITEM_INITIAL", allocationSize = 1)
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private BigDecimal id;
    @Column(name = "MATERIAL_CODE")
    private String materialCode;
    @Column(name = "REF_NO")
    private String refNo;
    @Size(max = 20)
    @Column(name = "REF_TYPE")
    private String refType;
    @Column(name = "CURRENT_PRICE")
    private BigDecimal currentPrice;
    @Column(name = "CURRENT_QUANTITY")
    private BigDecimal currentQuantity;
    @Column(name = "CURRENT_TOTAL_QUANTITY")
    private BigDecimal currentTotalQuantity;
    @Column(name = "PRV_QUANTITY")
    private BigDecimal prvQuantity;
    @Column(name = "WEIGHT_AVG_PRICE")
    private Double weightAvgPrice;
    @Column(name = "MATERIAL_ID")
    private Integer materialId;
    @Column(name = "MATERIAL_NAME")
    private String materialName;
    @Column(name = "STORE_NO")
    private String storeNo;
    @Column(name = "ACCOUNT_CODE")
    private String accountCode;
    @OneToMany(mappedBy = "slcId")
    private List<FmsStockRevaluationHistory> fmsStockRevaluationHistoryList;
//</editor-fold>

    public FmsStockLedgerCard() {
    }

    //<editor-fold defaultstate="collapsed" desc="getter and setter">

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getMaterialCode() {
        return materialCode;
    }

    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode;
    }

    public String getRefNo() {
        return refNo;
    }

    public void setRefNo(String refNo) {
        this.refNo = refNo;
    }

    public String getRefType() {
        return refType;
    }

    public void setRefType(String refType) {
        this.refType = refType;
    }

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(BigDecimal currentPrice) {
        this.currentPrice = currentPrice;
    }

    public BigDecimal getCurrentQuantity() {
        return currentQuantity;
    }

    public void setCurrentQuantity(BigDecimal currentQuantity) {
        this.currentQuantity = currentQuantity;
    }

    public BigDecimal getCurrentTotalQuantity() {
        return currentTotalQuantity;
    }

    public void setCurrentTotalQuantity(BigDecimal currentTotalQuantity) {
        this.currentTotalQuantity = currentTotalQuantity;
    }

    public BigDecimal getPrvQuantity() {
        return prvQuantity;
    }

    public void setPrvQuantity(BigDecimal prvQuantity) {
        this.prvQuantity = prvQuantity;
    }

    public Double getWeightAvgPrice() {
        return weightAvgPrice;
    }

    public void setWeightAvgPrice(Double weightAvgPrice) {
        this.weightAvgPrice = weightAvgPrice;
    }

    public Integer getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Integer materialId) {
        this.materialId = materialId;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getStoreNo() {
        return storeNo;
    }

    public void setStoreNo(String storeNo) {
        this.storeNo = storeNo;
    }

    public String getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    public List<FmsStockRevaluationHistory> getFmsStockRevaluationHistoryList() {
        return fmsStockRevaluationHistoryList;
    }

    public void setFmsStockRevaluationHistoryList(List<FmsStockRevaluationHistory> fmsStockRevaluationHistoryList) {
        this.fmsStockRevaluationHistoryList = fmsStockRevaluationHistoryList;
    }
//</editor-fold>

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof FmsStockLedgerCard)) {
            return false;
        }
        FmsStockLedgerCard other = (FmsStockLedgerCard) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return id.toString();
    }

}
