/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import et.gov.eep.fcms.entity.admin.FmsSubsidiaryLedger;
import et.gov.eep.ifrs.entity.IfrsDataType;
import et.gov.eep.ifrs.entity.IfrsFixedAssetAtValue;
import et.gov.eep.ifrs.entity.IfrsOptionValues;

/**
 *
 * @author user
 */
@Entity
@Table(name = "IFRS_FIXED_ASSET_ATTRIBUTE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IfrsFixedAssetAttribute.findAll", query = "SELECT i FROM IfrsFixedAssetAttribute i"),
    @NamedQuery(name = "IfrsFixedAssetAttribute.findById", query = "SELECT i FROM IfrsFixedAssetAttribute i WHERE i.id = :id"),
    @NamedQuery(name = "IfrsFixedAssetAttribute.findByFaaname", query = "SELECT i FROM IfrsFixedAssetAttribute i WHERE i.faaname = :faaname"),
    @NamedQuery(name = "IfrsFixedAssetAttribute.findByFieldType", query = "SELECT i FROM IfrsFixedAssetAttribute i WHERE i.fieldType = :fieldType"),
    @NamedQuery(name = "IfrsFixedAssetAttribute.findByRequired", query = "SELECT i FROM IfrsFixedAssetAttribute i WHERE i.required = :required"),
    @NamedQuery(name = "IfrsFixedAssetAttribute.findBySlCode", query = "SELECT i FROM IfrsFixedAssetAttribute i WHERE i.subsidiaryId = :subsidiaryId"),
    @NamedQuery(name = "IfrsFixedAssetAttribute.findByValid", query = "SELECT i FROM IfrsFixedAssetAttribute i WHERE i.valid = :valid")})
public class IfrsFixedAssetAttribute implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "IFRS_FIXED_ASSET_ATTRIBUTE_SEQ")
    @SequenceGenerator(name = "IFRS_FIXED_ASSET_ATTRIBUTE_SEQ", sequenceName = "IFRS_FIXED_ASSET_ATTRIBUTE_SEQ", allocationSize = 1)
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Integer id;
    @Size(max = 50)
    @Column(name = "FAANAME")
    private String faaname;
    @Size(max = 50)
    @Column(name = "FIELD_TYPE")
    private String fieldType;
    @Size(max = 20)
    @Column(name = "REQUIRED")
    private String required;
    @Size(max = 20)
    @Column(name = "VALID")
    private String valid;
    @JoinColumn(name = "SUBSIDIARY_ID", referencedColumnName = "SUBSIDIARY_ID")
    @OneToOne
    private FmsSubsidiaryLedger subsidiaryId;

    @JoinColumn(name = "DT_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private IfrsDataType dtId;

    @OneToOne(mappedBy = "attributeId")
    private IfrsFixedAssetAtValue ifrsFixedAssetAtValueList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "faaId")
    private List<IfrsOptionValues> ifrsOptionValuesList;

    @Transient
    ArrayList<String> optionValueListForCheckbox;

    public IfrsFixedAssetAttribute() {
    }

    public IfrsFixedAssetAttribute(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFaaname() {
        return faaname;
    }

    public void setFaaname(String faaname) {
        this.faaname = faaname;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public String getRequired() {
        return required;
    }

    public void setRequired(String required) {
        this.required = required;
    }

    public String getValid() {
        return valid;
    }

    public void setValid(String valid) {
        this.valid = valid;
    }

    public IfrsDataType getDtId() {
        return dtId;
    }

    public void setDtId(IfrsDataType dtId) {
        this.dtId = dtId;
    }

    public FmsSubsidiaryLedger getSubsidiaryId() {
        return subsidiaryId;
    }

    public void setSubsidiaryId(FmsSubsidiaryLedger subsidiaryId) {
        this.subsidiaryId = subsidiaryId;
    }

    public IfrsFixedAssetAtValue getIfrsFixedAssetAtValueList() {
        if (ifrsFixedAssetAtValueList == null) {
            ifrsFixedAssetAtValueList = new IfrsFixedAssetAtValue();
        }
        return ifrsFixedAssetAtValueList;
    }

    public void setIfrsFixedAssetAtValueList(IfrsFixedAssetAtValue ifrsFixedAssetAtValueList) {
        this.ifrsFixedAssetAtValueList = ifrsFixedAssetAtValueList;
    }

    @XmlTransient
    public List<IfrsOptionValues> getIfrsOptionValuesList() {
        if (ifrsOptionValuesList == null) {
            ifrsOptionValuesList = new ArrayList<>();
        }
        return ifrsOptionValuesList;
    }

    public void setIfrsOptionValuesList(List<IfrsOptionValues> ifrsOptionValuesList) {
        this.ifrsOptionValuesList = ifrsOptionValuesList;
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
        if (!(object instanceof IfrsFixedAssetAttribute)) {
            return false;
        }
        IfrsFixedAssetAttribute other = (IfrsFixedAssetAttribute) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return faaname;
    }

    public ArrayList<String> getOptionValueListForCheckbox() {
        return optionValueListForCheckbox;
    }

    public void setOptionValueListForCheckbox(ArrayList<String> optionValueListForCheckbox) {
        this.optionValueListForCheckbox = optionValueListForCheckbox;
    }

    public void addToOptionvalue(IfrsOptionValues optionvalue) {

        if (optionvalue.getFaaId() != this) {
            this.getIfrsOptionValuesList().add(optionvalue);
            optionvalue.setFaaId(this);

        }
    }
}
