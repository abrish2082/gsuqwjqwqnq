/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.employee;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author user
 */
@Entity
@Table(name = "HR_EMP_FILES")
@XmlRootElement
@NamedQueries//<editor-fold defaultstate="collapsed" desc="">
        ({
            @NamedQuery(name = "HrEmpFiles.findAll", query = "SELECT h FROM HrEmpFiles h"),
            @NamedQuery(name = "HrEmpFiles.findById", query = "SELECT h FROM HrEmpFiles h WHERE h.id = :id"),
            @NamedQuery(name = "HrEmpFiles.findByFileName", query = "SELECT h FROM HrEmpFiles h WHERE h.fileName = :fileName"),
            @NamedQuery(name = "HrEmpFiles.findByFileType", query = "SELECT h FROM HrEmpFiles h WHERE h.fileType = :fileType"),
            @NamedQuery(name = "HrEmpFiles.findByFileExtension", query = "SELECT h FROM HrEmpFiles h WHERE h.fileExtension = :fileExtension"),
            @NamedQuery(name = "HrEmpFiles.findByRemark", query = "SELECT h FROM HrEmpFiles h WHERE h.remark = :remark")})
//</editor-fold>
public class HrEmpFiles implements Serializable {
    
    //<editor-fold defaultstate="collapsed" desc="Entity Attributes">
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private BigDecimal id;
    @Size(max = 50)
    @Column(name = "FILE_NAME")
    private String fileName;
    @Size(max = 50)
    @Column(name = "FILE_TYPE")
    private String fileType;
    @Lob
    @Column(name = "EMP_FILE")
    private Serializable empFile;
    @Size(max = 20)
    @Column(name = "FILE_EXTENSION")
    private String fileExtension;
    @Size(max = 200)
    @Column(name = "REMARK")
    private String remark;
    @JoinColumn(name = "EMP_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrEmployees empId;
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="getter and setter">
    
    /**
     *
     */
    public HrEmpFiles() {
    }
    
    /**
     *
     * @param id
     */
    public HrEmpFiles(BigDecimal id) {
        this.id = id;
    }
    
    /**
     *
     * @return
     */
    public BigDecimal getId() {
        return id;
    }
    
    /**
     *
     * @param id
     */
    public void setId(BigDecimal id) {
        this.id = id;
    }
    
    /**
     *
     * @return
     */
    public String getFileName() {
        return fileName;
    }
    
    /**
     *
     * @param fileName
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    
    /**
     *
     * @return
     */
    public String getFileType() {
        return fileType;
    }
    
    /**
     *
     * @param fileType
     */
    public void setFileType(String fileType) {
        this.fileType = fileType;
    }
    
    /**
     *
     * @return
     */
    public Serializable getEmpFile() {
        return empFile;
    }
    
    /**
     *
     * @param empFile
     */
    public void setEmpFile(Serializable empFile) {
        this.empFile = empFile;
    }
    
    /**
     *
     * @return
     */
    public String getFileExtension() {
        return fileExtension;
    }
    
    /**
     *
     * @param fileExtension
     */
    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
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
        if (!(object instanceof HrEmpFiles)) {
            return false;
        }
        HrEmpFiles other = (HrEmpFiles) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return "et.gov.eep.hrms.entity.HrEmpFiles[ id=" + id + " ]";
    }
//</editor-fold>
    
}
