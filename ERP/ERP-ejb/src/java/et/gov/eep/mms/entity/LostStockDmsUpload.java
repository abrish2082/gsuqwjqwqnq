
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author w_station
 */
@Entity
@Table(name = "LOST_STOCK_DMS_UPLOAD")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LostStockDmsUpload.findAll", query = "SELECT l FROM LostStockDmsUpload l"),
    @NamedQuery(name = "LostStockDmsUpload.findById", query = "SELECT l FROM LostStockDmsUpload l WHERE l.id = :id"),
    @NamedQuery(name = "LostStockDmsUpload.findByDocId", query = "SELECT l FROM LostStockDmsUpload l WHERE l.docId = :docId")})
public class LostStockDmsUpload implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LOST_STOCK_DMS_UPLOAD_SEQ")
    @SequenceGenerator(name = "LOST_STOCK_DMS_UPLOAD_SEQ", sequenceName = "LOST_STOCK_DMS_UPLOAD_SEQ", allocationSize = 1)

    @Column(name = "ID", nullable = false, precision = 0, scale = -127)
    private BigDecimal id;
    @Column(name = "DOC_ID")
    private Integer docId;
    @JoinColumn(name = "LOST_ID", referencedColumnName = "LOST_STOCK_ID")
    @ManyToOne
    private MmsStockItemLost lostId;

    public LostStockDmsUpload() {
    }

    public LostStockDmsUpload(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public Integer getDocId() {
        return docId;
    }

    public void setDocId(Integer docId) {
        this.docId = docId;
    }

    public MmsStockItemLost getLostId() {
        return lostId;
    }

    public void setLostId(MmsStockItemLost lostId) {
        this.lostId = lostId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
      
        if (!(object instanceof LostStockDmsUpload)) {
            return false;
        }
        LostStockDmsUpload other = (LostStockDmsUpload) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.LostStockDmsUpload[ id=" + id + " ]";
    }

}
