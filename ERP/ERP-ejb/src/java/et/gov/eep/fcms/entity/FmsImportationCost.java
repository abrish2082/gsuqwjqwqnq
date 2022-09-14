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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author beharuh
 */
@Entity
@Table(name = "FMS_IMPORTATION_COST")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsImportationCost.findAll", query = "SELECT f FROM FmsImportationCost f"),
    @NamedQuery(name = "FmsImportationCost.findById", query = "SELECT f FROM FmsImportationCost f WHERE f.id = :id"),
    @NamedQuery(name = "FmsImportationCost.findByProduct", query = "SELECT f FROM FmsImportationCost f WHERE upper(f.product) = :product or LOWER (f.product) =:product"),
    @NamedQuery(name = "FmsImportationCost.findByProductSelected", query = "SELECT DISTINCT upper(f.product) FROM FmsImportationCost f WHERE upper(f.product) like :product or lower(f.product) like :product"),
    @NamedQuery(name = "FmsImportationCost.findByType", query = "SELECT f FROM FmsImportationCost f WHERE f.type = :type"),
    @NamedQuery(name = "FmsImportationCost.findByRate1", query = "SELECT f FROM FmsImportationCost f WHERE f.rate1 = :rate1"),
    @NamedQuery(name = "FmsImportationCost.findByRate2", query = "SELECT f FROM FmsImportationCost f WHERE f.rate2 = :rate2"),
    @NamedQuery(name = "FmsImportationCost.findByRate3", query = "SELECT f FROM FmsImportationCost f WHERE f.rate3 = :rate3"),
    @NamedQuery(name = "FmsImportationCost.findByFormula", query = "SELECT f FROM FmsImportationCost f WHERE f.formula = :formula")})

public class FmsImportationCost implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FMS_SEQ_IMPORTATION_COST")
    @SequenceGenerator( name = "FMS_SEQ_IMPORTATION_COST", sequenceName = "FMS_SEQ_IMPORTATION_COST", allocationSize = 1)
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID", nullable = false)
    private Integer id;
    @Size(max = 45)
    @Column(length = 45)
    private String product;
    @Size(max = 45)
    @Column(length = 45)
    private String type;
    @Column(precision = 38, scale = 4)
    private double rate1;
    @Column(precision = 38, scale = 4)
    private double rate2;
    @Column(precision = 38, scale = 4)
    private double rate3;
    @Size(max = 50)
    @Column(length = 50)
    private String formula;

    /**
     *
     */
    public FmsImportationCost() {
    }

    /**
     *
     * @param id
     */
    public FmsImportationCost(Integer id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public String getProduct() {
        return product;
    }

    /**
     *
     * @param product
     */
    public void setProduct(String product) {
        this.product = product;
    }

    /**
     *
     * @return
     */
    public String getType() {
        return type;
    }

    /**
     *
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     *
     * @return
     */
    public double getRate1() {
        return rate1;
    }

    /**
     *
     * @param rate1
     */
    public void setRate1(double rate1) {
        this.rate1 = rate1;
    }

    /**
     *
     * @return
     */
    public double getRate2() {
        return rate2;
    }

    /**
     *
     * @param rate2
     */
    public void setRate2(double rate2) {
        this.rate2 = rate2;
    }

    /**
     *
     * @return
     */
    public double getRate3() {
        return rate3;
    }

    /**
     *
     * @param rate3
     */
    public void setRate3(double rate3) {
        this.rate3 = rate3;
    }

    /**
     *
     * @return
     */
    public String getFormula() {
        return formula;
    }

    /**
     *
     * @param formula
     */
    public void setFormula(String formula) {
        this.formula = formula;
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
        if (!(object instanceof FmsImportationCost)) {
            return false;
        }
        FmsImportationCost other = (FmsImportationCost) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.insa.erp.ibfms.entity.FmsImportationCost[ id=" + id + " ]";
    }

}
