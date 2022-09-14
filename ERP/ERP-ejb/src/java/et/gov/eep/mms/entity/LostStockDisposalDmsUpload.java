
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
@Table(name = "LOST_STOCK_DISPOSAL_DMS_UPLOAD")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LostStockDisposalDmsUpload.findAll", query = "SELECT l FROM LostStockDisposalDmsUpload l"),
    @NamedQuery(name = "LostStockDisposalDmsUpload.findById", query = "SELECT l FROM LostStockDisposalDmsUpload l WHERE l.id = :id"),
    @NamedQuery(name = "LostStockDisposalDmsUpload.findByDocId", query = "SELECT l FROM LostStockDisposalDmsUpload l WHERE l.docId = :docId")})
public class LostStockDisposalDmsUpload implements Serializable {

    private static final long serialVersionUID = 1L;
   
    @Id

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LOST_STOCK_DISP_DMS_UPLOAD_SEQ")
    @SequenceGenerator(name = "LOST_STOCK_DISP_DMS_UPLOAD_SEQ", sequenceName = "LOST_STOCK_DISP_DMS_UPLOAD_SEQ", allocationSize = 1)

    @Column(name = "ID", nullable = false, precision = 0, scale = -127)
    private BigDecimal id;
    @Column(name = "DOC_ID")
    private Integer docId;
    @JoinColumn(name = "STOCK_ID", referencedColumnName = "STOCK_ID")
    @ManyToOne
    private MmsStockDisposal stockId;

    public LostStockDisposalDmsUpload() {
    }

    public LostStockDisposalDmsUpload(BigDecimal id) {
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

    public MmsStockDisposal getStockId() {
        return stockId;
    }

    public void setStockId(MmsStockDisposal stockId) {
        this.stockId = stockId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
       
        if (!(object instanceof LostStockDisposalDmsUpload)) {
            return false;
        }
        LostStockDisposalDmsUpload other = (LostStockDisposalDmsUpload) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.LostStockDisposalDmsUpload[ id=" + id + " ]";
    }

}
