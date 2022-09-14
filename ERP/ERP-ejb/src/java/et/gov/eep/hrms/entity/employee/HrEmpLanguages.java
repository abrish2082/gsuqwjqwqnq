package et.gov.eep.hrms.entity.employee;

import et.gov.eep.hrms.entity.lookup.HrLuLanguages;
import java.io.Serializable;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author user
 */
@Entity
@Table(name = "HR_EMP_LANGUAGES")
@XmlRootElement
@NamedQueries//<editor-fold defaultstate="collapsed" desc="">
        ({
            @NamedQuery(name = "HrEmpLanguages.findAll", query = "SELECT h FROM HrEmpLanguages h"),
            @NamedQuery(name = "HrEmpLanguages.findById", query = "SELECT h FROM HrEmpLanguages h WHERE h.id = :id"),
            @NamedQuery(name = "HrEmpLanguages.findByLanguageType", query = "SELECT h FROM HrEmpLanguages h WHERE h.languageId = :languageType"),
            @NamedQuery(name = "HrEmpLanguages.findByReading", query = "SELECT h FROM HrEmpLanguages h WHERE h.reading = :reading"),
            @NamedQuery(name = "HrEmpLanguages.findByWriting", query = "SELECT h FROM HrEmpLanguages h WHERE h.writing = :writing"),
            @NamedQuery(name = "HrEmpLanguages.findBySpeaking", query = "SELECT h FROM HrEmpLanguages h WHERE h.speaking = :speaking"),
            @NamedQuery(name = "HrEmpLanguages.findByListening", query = "SELECT h FROM HrEmpLanguages h WHERE h.listening = :listening")})
//</editor-fold>
public class HrEmpLanguages implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="Entity Attributes">
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_EMP_LANGUAGES_SEQ")
    @SequenceGenerator(name = "HR_EMP_LANGUAGES_SEQ", sequenceName = "HR_EMP_LANGUAGES_SEQ", allocationSize = 1)
    @Column(name = "ID")
    private Long id;
    @JoinColumn(name = "LANGUAGE_TYPE", referencedColumnName = "ID")
    @ManyToOne
    private HrLuLanguages languageId;
    @Size(max = 20)
    @Column(name = "READING")
    private String reading;
    @Size(max = 20)
    @Column(name = "WRITING")
    private String writing;
    @Size(max = 20)
    @Column(name = "SPEAKING")
    private String speaking;
    @Size(max = 20)
    @Column(name = "LISTENING")
    private String listening;
    @JoinColumn(name = "EMP_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrEmployees empId;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="getter and setter">
    /**
     *
     */
    public HrEmpLanguages() {
    }
    
    /**
     *
     * @param id
     */
    public HrEmpLanguages(Long id) {
        this.id = id;
    }
    
    /**
     *
     * @return
     */
    public Long getId() {
        return id;
    }
    
    /**
     *
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }
    
    /**
     *
     * @return
     */
    public String getReading() {
        return reading;
    }
    
    /**
     *
     * @return
     */
    public HrLuLanguages getLanguageId() {
        if(languageId == null){
            languageId = new HrLuLanguages();
        }
        return languageId;
    }
    
    /**
     *
     * @param languageId
     */
    public void setLanguageId(HrLuLanguages languageId) {
        this.languageId = languageId;
    }
    
    /**
     *
     * @param reading
     */
    public void setReading(String reading) {
        this.reading = reading;
    }
    
    /**
     *
     * @return
     */
    public String getWriting() {
        return writing;
    }
    
    /**
     *
     * @param writing
     */
    public void setWriting(String writing) {
        this.writing = writing;
    }
    
    /**
     *
     * @return
     */
    public String getSpeaking() {
        return speaking;
    }
    
    /**
     *
     * @param speaking
     */
    public void setSpeaking(String speaking) {
        this.speaking = speaking;
    }
    
    /**
     *
     * @return
     */
    public String getListening() {
        return listening;
    }
    
    /**
     *
     * @param listening
     */
    public void setListening(String listening) {
        this.listening = listening;
    }
    
    /**
     *
     * @return
     */
    public HrEmployees getEmpId() {
        return empId;
    }
    
    /**
     *
     * @param empId
     */
    public void setEmpId(HrEmployees empId) {
        this.empId = empId;
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Other Methods">
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }
    
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrEmpLanguages)) {
            return false;
        }
        HrEmpLanguages other = (HrEmpLanguages) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return "entity.HrEmpLanguages[ id=" + id + " ]";
    }
//</editor-fold>
}
