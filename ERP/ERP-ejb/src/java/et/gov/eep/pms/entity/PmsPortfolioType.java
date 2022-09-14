/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.pms.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Me
 */
@Entity
@Table(name = "PMS_PORTFOLIO_TYPE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PmsPortfolioType.findAll", query = "SELECT p FROM PmsPortfolioType p"),
    @NamedQuery(name = "PmsPortfolioType.findByPortfolioTypeId", query = "SELECT p FROM PmsPortfolioType p WHERE p.portfolioTypeId = :portfolioTypeId"),
    @NamedQuery(name = "PmsPortfolioType.findByTypeName", query = "SELECT p FROM PmsPortfolioType p WHERE p.typeName = :typeName"),
    @NamedQuery(name = "PmsPortfolioType.findByDescription", query = "SELECT p FROM PmsPortfolioType p WHERE p.description = :description"),
    @NamedQuery(name = "PmsPortfolioType.findByStatus", query = "SELECT p FROM PmsPortfolioType p WHERE p.status = :status")})
public class PmsPortfolioType implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "PORTFOLIO_TYPE_ID")
    private BigDecimal portfolioTypeId;
    @Column(name = "TYPE_NAME")
    private String typeName;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "STATUS")
    private String status;
    @OneToMany(mappedBy = "portfolioTypeId")
    private List<PmsCreatePortfolio> pmsCreatePortfolioList;

    public PmsPortfolioType() {
    }

    public PmsPortfolioType(BigDecimal portfolioTypeId) {
        this.portfolioTypeId = portfolioTypeId;
    }

    public BigDecimal getPortfolioTypeId() {
        return portfolioTypeId;
    }

    public void setPortfolioTypeId(BigDecimal portfolioTypeId) {
        this.portfolioTypeId = portfolioTypeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @XmlTransient
    public List<PmsCreatePortfolio> getPmsCreatePortfolioList() {
        return pmsCreatePortfolioList;
    }

    public void setPmsCreatePortfolioList(List<PmsCreatePortfolio> pmsCreatePortfolioList) {
        this.pmsCreatePortfolioList = pmsCreatePortfolioList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (portfolioTypeId != null ? portfolioTypeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PmsPortfolioType)) {
            return false;
        }
        PmsPortfolioType other = (PmsPortfolioType) object;
        if ((this.portfolioTypeId == null && other.portfolioTypeId != null) || (this.portfolioTypeId != null && !this.portfolioTypeId.equals(other.portfolioTypeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "javaapplication1.PmsPortfolioType[ portfolioTypeId=" + portfolioTypeId + " ]";
    }
    
}
