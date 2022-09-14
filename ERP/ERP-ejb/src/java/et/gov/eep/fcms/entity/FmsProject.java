/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author AB
 */
@Entity
@Table(name = "fms_project" )
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsProject.findAll", query = "SELECT f FROM FmsProject f"),
    @NamedQuery(name = "FmsProject.findByProjectId", query = "SELECT f FROM FmsProject f WHERE f.projectId = :projectId")})
public class FmsProject implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FMS_PRODUCTS_PRODUCT_ID_SEQ")
    @SequenceGenerator(name = "FMS_PRODUCTS_PRODUCT_ID_SEQ", sequenceName = "FMS_PRODUCTS_PRODUCT_ID_SEQ", allocationSize = 1)
    @Basic(optional = false)
    @Column(name = "PROJECT_ID")
    private Integer projectId;

    /**
     *
     */
    public FmsProject() {
    }

    /**
     *
     * @param projectId
     */
    public FmsProject(Integer projectId) {
        this.projectId = projectId;
    }

    /**
     *
     * @return
     */
    public Integer getProjectId() {
        return projectId;
    }

    /**
     *
     * @param projectId
     */
    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

//    public FmsChartOfAccount getChartOfAcctId() {
//        return chartOfAcctId;
//    }
//
//    public void setChartOfAcctId(FmsChartOfAccount chartOfAcctId) {
//        this.chartOfAcctId = chartOfAcctId;
//    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (projectId != null ? projectId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FmsProject)) {
            return false;
        }
        FmsProject other = (FmsProject) object;
        if ((this.projectId == null && other.projectId != null) || (this.projectId != null && !this.projectId.equals(other.projectId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.insa.erp.ibfms.entity.FmsProject[ projectId=" + projectId + " ]";
    }
    
}
