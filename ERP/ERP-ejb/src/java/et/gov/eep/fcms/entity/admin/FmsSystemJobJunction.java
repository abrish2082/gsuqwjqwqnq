/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.entity.admin;


import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import et.gov.eep.pms.entity.PmsWorkAuthorization;
/**
 *
 * @author Terminal2
 */
@Entity
@Table(name = "FMS_SYSTEM_JOB_JUNCTION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsSystemJobJunction.findAll", query = "SELECT f FROM FmsSystemJobJunction f"),
    @NamedQuery(name = "FmsSystemJobJunction.findById", query = "SELECT f FROM FmsSystemJobJunction f WHERE f.id = :id"),
    @NamedQuery(name = "FmsSystemJobJunction.findBySSPMSId", query = "SELECT f FROM FmsSystemJobJunction f WHERE f.fmsLuSystem = :fmsLuSystem AND f.workAuthoFk = :workAuthoFk"),
    @NamedQuery(name = "FmsSystemJobJunction.findByJobNo", query = "SELECT f FROM FmsSystemJobJunction f WHERE f.jobNo = :jobNo"),
    @NamedQuery(name = "FmsSystemJobJunction.findByJobName", query = "SELECT f FROM FmsSystemJobJunction f WHERE f.jobName = :jobName")})
public class FmsSystemJobJunction implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID", nullable = false, precision = 0, scale = -127)
    private BigDecimal id;
    @Size(max = 20)
    @Column(name = "JOB_NO", length = 20)
    private String jobNo;
    @Size(max = 255)
    @Column(name = "JOB_NAME", length = 255)
    private String jobName;
    @JoinColumn(name = "SYSTEM_ID", referencedColumnName = "SYSTEM_ID")
    @ManyToOne
    private FmsLuSystem fmsLuSystem;
    @JoinColumn(name = "WORK_AUTHO", referencedColumnName = "WORK_AUTHO_ID")
    @ManyToOne
    private PmsWorkAuthorization workAuthoFk;

    public FmsSystemJobJunction() {
    }

    public FmsSystemJobJunction(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getJobNo() {
        return jobNo;
    }

    public void setJobNo(String jobNo) {
        this.jobNo = jobNo;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public FmsLuSystem getFmsLuSystem() {
        return fmsLuSystem;
    }

    public void setFmsLuSystem(FmsLuSystem fmsLuSystem) {
        this.fmsLuSystem = fmsLuSystem;
    }

    public PmsWorkAuthorization getWorkAuthoFk() {
        return workAuthoFk;
    }

    public void setWorkAuthoFk(PmsWorkAuthorization workAuthoFk) {
        this.workAuthoFk = workAuthoFk;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof FmsSystemJobJunction)) {
            return false;
        }
        FmsSystemJobJunction other = (FmsSystemJobJunction) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.fcms.entity.admin.FmsSystemJobJunction[ id=" + id + " ]";
    }

}
