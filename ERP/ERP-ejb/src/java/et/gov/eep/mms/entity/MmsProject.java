/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "MMS_PROJECT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MmsProject.findAll", query = "SELECT m FROM MmsProject m"),
    @NamedQuery(name = "MmsProject.findByProjectId", query = "SELECT m FROM MmsProject m WHERE m.projectId = :projectId"),
    @NamedQuery(name = "MmsProject.findByProjectName", query = "SELECT m FROM MmsProject m WHERE m.projectName = :projectName"),
    @NamedQuery(name = "MmsProject.findByProjectNo", query = "SELECT m FROM MmsProject m WHERE m.projectNo = :projectNo"),
    @NamedQuery(name = "MmsProject.findByContractorName", query = "SELECT m FROM MmsProject m WHERE m.contractorName = :contractorName"),
    @NamedQuery(name = "MmsProject.findByJobNo", query = "SELECT m FROM MmsProject m WHERE m.jobNo = :jobNo"),
  @NamedQuery(name = "MmsProject.findByJobNoLike", query = "SELECT m FROM MmsProject m WHERE m.jobNo LIKE :jobNo")})
public class MmsProject implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "PROJECT_ID")
    private BigDecimal projectId;
    @Size(max = 50)
    @Column(name = "PROJECT_NAME")
    private String projectName;
    @Size(max = 20)
    @Column(name = "PROJECT_NO")
    private String projectNo;
    @Size(max = 50)
    @Column(name = "CONTRACTOR_NAME")
    private String contractorName;
    @Size(max = 50)
    @Column(name = "JOB_NO")
    private String jobNo;

    /**
     *
     */
    public MmsProject() {
    }

    /**
     *
     * @param projectId
     */
    public MmsProject(BigDecimal projectId) {
        this.projectId = projectId;
    }

    /**
     *
     * @return
     */
    public BigDecimal getProjectId() {
        return projectId;
    }

    /**
     *
     * @param projectId
     */
    public void setProjectId(BigDecimal projectId) {
        this.projectId = projectId;
    }

    /**
     *
     * @return
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     *
     * @param projectName
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    /**
     *
     * @return
     */
    public String getProjectNo() {
        return projectNo;
    }

    /**
     *
     * @param projectNo
     */
    public void setProjectNo(String projectNo) {
        this.projectNo = projectNo;
    }

    /**
     *
     * @return
     */
    public String getContractorName() {
        return contractorName;
    }

    /**
     *
     * @param contractorName
     */
    public void setContractorName(String contractorName) {
        this.contractorName = contractorName;
    }

    /**
     *
     * @return
     */
    public String getJobNo() {
        return jobNo;
    }

    /**
     *
     * @param jobNo
     */
    public void setJobNo(String jobNo) {
        this.jobNo = jobNo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (projectId != null ? projectId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MmsProject)) {
            return false;
        }
        MmsProject other = (MmsProject) object;
        if ((this.projectId == null && other.projectId != null) || (this.projectId != null && !this.projectId.equals(other.projectId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return jobNo;
    }
    
}
