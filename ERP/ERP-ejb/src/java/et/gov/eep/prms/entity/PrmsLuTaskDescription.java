/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author user
 */
@Entity
@Table(name = "PRMS_LU_TASK_DESCRIPTION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrmsLuTaskDescription.findAll", query = "SELECT p FROM PrmsLuTaskDescription p"),
    @NamedQuery(name = "PrmsLuTaskDescription.findById", query = "SELECT p FROM PrmsLuTaskDescription p WHERE p.id = :id"),
    @NamedQuery(name = "PrmsLuTaskDescription.findByDescription", query = "SELECT p FROM PrmsLuTaskDescription p WHERE p.description = :description")})
public class PrmsLuTaskDescription implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "ID", nullable = false, length = 500)
    private String id;
    @Size(max = 20)
    @Column(name = "DESCRIPTION", length = 500)
    private String description;
    @OneToMany(mappedBy = "luDescrId")
    private List<PrmsActionPlanDetail> prmsActionPlanDetailList;

    /**
     *
     */
    public PrmsLuTaskDescription() {
    }

    /**
     *
     * @param id
     */
    public PrmsLuTaskDescription(String id) {
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
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     *
     * @return
     */
    @XmlTransient
    public List<PrmsActionPlanDetail> getPrmsActionPlanDetailList() {
        return prmsActionPlanDetailList;
    }

    /**
     *
     * @param prmsActionPlanDetailList
     */
    public void setPrmsActionPlanDetailList(List<PrmsActionPlanDetail> prmsActionPlanDetailList) {
        this.prmsActionPlanDetailList = prmsActionPlanDetailList;
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
        if (!(object instanceof PrmsLuTaskDescription)) {
            return false;
        }
        PrmsLuTaskDescription other = (PrmsLuTaskDescription) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return description;
    }

}
