/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Sadik
 */
@Entity
@Table(name = "MMS_LU_INSPECTION_INVOICE_TYPE", catalog = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MmsLuInspectionInvoiceType.findAll", query = "SELECT m FROM MmsLuInspectionInvoiceType m"),
    @NamedQuery(name = "MmsLuInspectionInvoiceType.findById", query = "SELECT m FROM MmsLuInspectionInvoiceType m WHERE m.id = :id"),
    @NamedQuery(name = "MmsLuInspectionInvoiceType.findByName", query = "SELECT m FROM MmsLuInspectionInvoiceType m WHERE m.name = :name"),
    @NamedQuery(name = "MmsLuInspectionInvoiceType.findByDescription", query = "SELECT m FROM MmsLuInspectionInvoiceType m WHERE m.description = :description")})

public class MmsLuInspectionInvoiceType implements Serializable {
    private static final long serialVersionUID = 1L;
    
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MMS_LU_INSP_INVOICE_TYPE_SEQ")
    @SequenceGenerator(name = "MMS_LU_INSP_INVOICE_TYPE_SEQ", sequenceName = "MMS_LU_INSP_INVOICE_TYPE_SEQ", allocationSize = 1)
    @Column(name = "ID", nullable = false, precision = 0, scale = -127)
    private Integer id;
    @Size(max = 45)
    @Column(name = "NAME", length = 45)
    private String name;
    @Size(max = 45)
    @Column(name = "DESCRIPTION", length = 45)
    private String description;

   public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

 

 

    @Override
    public String toString() {
        return name;
    }
}
