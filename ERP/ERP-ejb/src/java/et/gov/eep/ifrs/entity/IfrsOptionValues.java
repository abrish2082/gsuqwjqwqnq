/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.ifrs.entity;

import et.gov.eep.mms.entity.IfrsFixedAssetAttribute;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author user
 */
@Entity
@Table(name = "IFRS_OPTION_VALUES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IfrsOptionValues.findAll", query = "SELECT i FROM IfrsOptionValues i"),
    @NamedQuery(name = "IfrsOptionValues.findById", query = "SELECT i FROM IfrsOptionValues i WHERE i.id = :id"),
    @NamedQuery(name = "IfrsOptionValues.findByValueName", query = "SELECT i FROM IfrsOptionValues i WHERE i.valueName = :valueName"),
    @NamedQuery(name = "IfrsOptionValues.findByFAAId", query = "SELECT i FROM IfrsOptionValues i WHERE i.faaId.id = :fAAId")})
public class IfrsOptionValues implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "IFRS_OPTION_VALUES_SEQ")
    @SequenceGenerator(name = "IFRS_OPTION_VALUES_SEQ", sequenceName = "IFRS_OPTION_VALUES_SEQ", allocationSize = 1)
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Integer id;
    @Size(max = 50)
    @Column(name = "VALUE_NAME")
    private String valueName;
    @JoinColumn(name = "FAA_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private IfrsFixedAssetAttribute faaId;

    @Transient
    List<Integer> optionValueListForCheckbox = new ArrayList<>();

    public IfrsOptionValues() {
    }

    public IfrsOptionValues(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getValueName() {
        return valueName;
    }

    public void setValueName(String valueName) {
        this.valueName = valueName;
    }

    public IfrsFixedAssetAttribute getFaaId() {
        return faaId;
    }

    public void setFaaId(IfrsFixedAssetAttribute faaId) {
        this.faaId = faaId;
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
        if (!(object instanceof IfrsOptionValues)) {
            return false;
        }
        IfrsOptionValues other = (IfrsOptionValues) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return valueName;
    }

    public List<Integer> getOptionValueListForCheckbox() {
        optionValueListForCheckbox.add(getId());
        return optionValueListForCheckbox;
    }

    public void setOptionValueListForCheckbox(List<Integer> optionValueListForCheckbox) {
        this.optionValueListForCheckbox = optionValueListForCheckbox;
    }

}
