/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.lookup;

import et.gov.eep.hrms.entity.employee.HrEmpLanguages;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
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
@Table(name = "HR_LU_LANGUAGES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrLuLanguages.findAll", query = "SELECT h FROM HrLuLanguages h"),
    @NamedQuery(name = "HrLuLanguages.findById", query = "SELECT h FROM HrLuLanguages h WHERE h.id = :id"),
    @NamedQuery(name = "HrLuLanguages.findByLanguageName", query = "SELECT h FROM HrLuLanguages h WHERE h.languageName = :languageName"),
    @NamedQuery(name = "HrLuLanguages.findByDescription", query = "SELECT h FROM HrLuLanguages h WHERE h.description = :description")})
public class HrLuLanguages implements Serializable {

    @OneToMany(mappedBy = "languageId")
    private List<HrEmpLanguages> hrEmpLanguagesList;
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @Column(name = "ID")
    private BigDecimal id;
    @Size(max = 30)
    @Column(name = "LANGUAGE_NAME")
    private String languageName;
    @Size(max = 255)
    @Column(name = "DESCRIPTION")
    private String description;

    /**
     *
     */
    public HrLuLanguages() {
    }

    /**
     *
     * @param id
     */
    public HrLuLanguages(BigDecimal id) {
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
    public String getLanguageName() {
        return languageName;
    }

    /**
     *
     * @param languageName
     */
    public void setLanguageName(String languageName) {
        this.languageName = languageName;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrLuLanguages)) {
            return false;
        }
        HrLuLanguages other = (HrLuLanguages) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return languageName;
    }

    /**
     *
     * @return
     */
    @XmlTransient
    public List<HrEmpLanguages> getHrEmpLanguagesList() {
        return hrEmpLanguagesList;
    }

    /**
     *
     * @param hrEmpLanguagesList
     */
    public void setHrEmpLanguagesList(List<HrEmpLanguages> hrEmpLanguagesList) {
        this.hrEmpLanguagesList = hrEmpLanguagesList;
    }

}
