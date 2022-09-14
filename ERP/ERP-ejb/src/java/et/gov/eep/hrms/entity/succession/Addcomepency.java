/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.succession;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.inject.Named;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author user
 */

@Entity
@Table(name = "ADDCOMEPENCY")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Addcomepency.findAll", query = "SELECT a FROM Addcomepency a"),
    @NamedQuery(name = "Addcomepency.findByCompetncyname", query = "SELECT a FROM Addcomepency a WHERE a.competncyname = :competncyname"),
    @NamedQuery(name = "Addcomepency.findByCompetencytype", query = "SELECT a FROM Addcomepency a WHERE a.competencytype = :competencytype"),
    @NamedQuery(name = "Addcomepency.findBySubtype", query = "SELECT a FROM Addcomepency a WHERE a.subtype = :subtype"),
    @NamedQuery(name = "Addcomepency.findById", query = "SELECT a FROM Addcomepency a WHERE a.id = :id")})
public class Addcomepency implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "COMPETNCYNAME")
    private String competncyname;
    @Size(max = 20)
    @Column(name = "COMPETENCYTYPE")
    private String competencytype;
    @Size(max = 20)
    @Column(name = "SUBTYPE")
    private String subtype;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
      @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ADDCOMPETENCY_SEQ")
    @SequenceGenerator(name = "ADDCOMPETENCY_SEQ", sequenceName = "ADDCOMPETENCY_SEQ", allocationSize = 1)
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private BigDecimal id;

    public Addcomepency() {
    }

    public Addcomepency(BigDecimal id) {
        this.id = id;
    }

    public Addcomepency(BigDecimal id, String competncyname) {
        this.id = id;
        this.competncyname = competncyname;
    }

    public String getCompetncyname() {
        return competncyname;
    }

    public void setCompetncyname(String competncyname) {
        this.competncyname = competncyname;
    }

    public String getCompetencytype() {
        return competencytype;
    }

    public void setCompetencytype(String competencytype) {
        this.competencytype = competencytype;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
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
        if (!(object instanceof Addcomepency)) {
            return false;
        }
        Addcomepency other = (Addcomepency) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.hrms.entity.successor.Addcomepency[ id=" + id + " ]";
    }
    
}
