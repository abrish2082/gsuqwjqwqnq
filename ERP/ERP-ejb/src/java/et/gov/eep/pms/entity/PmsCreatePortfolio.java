/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.pms.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "PMS_CREATE_PORTFOLIO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PmsCreatePortfolio.findAll", query = "SELECT p FROM PmsCreatePortfolio p"),
    @NamedQuery(name = "PmsCreatePortfolio.findByPortfolioId", query = "SELECT p FROM PmsCreatePortfolio p WHERE p.portfolioId = :portfolioId"),
    @NamedQuery(name = "PmsCreatePortfolio.findByAssignPriorityId", query = "SELECT p FROM PmsCreatePortfolio p WHERE p.assignPriorityId = :assignPriorityId"),
    @NamedQuery(name = "PmsCreatePortfolio.findByPortfolioName", query = "SELECT p FROM PmsCreatePortfolio p WHERE p.portfolioName = :portfolioName"),
    @NamedQuery(name = "PmsCreatePortfolio.findByProjectLifeCycle", query = "SELECT p FROM PmsCreatePortfolio p WHERE p.projectLifeCycle = :projectLifeCycle"),
    @NamedQuery(name = "PmsCreatePortfolio.findByPortfolioState", query = "SELECT p FROM PmsCreatePortfolio p WHERE p.portfolioState = :portfolioState")})
public class PmsCreatePortfolio implements Serializable {
    @OneToMany(mappedBy = "portfolioId")
    private List<PmsMaintainProg> pmsMaintainProgList;
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "PORTFOLIO_ID")
    private BigDecimal portfolioId;
    @Column(name = "ASSIGN_PRIORITY_ID")
    private BigInteger assignPriorityId;
    @Column(name = "PORTFOLIO_NAME")
    private String portfolioName;
    @Column(name = "PROJECT_LIFE_CYCLE")
    private String projectLifeCycle;
    @Column(name = "PORTFOLIO_STATE")
    private String portfolioState;
    @JoinColumn(name = "PORTFOLIO_TYPE_ID", referencedColumnName = "PORTFOLIO_TYPE_ID")
    @ManyToOne
    private PmsPortfolioType portfolioTypeId;
    @OneToMany(mappedBy = "portfolioId")
    private List<PmsCreateProjects> pmsCreateProjectsList;

    public PmsCreatePortfolio() {
    }

    public PmsCreatePortfolio(BigDecimal portfolioId) {
        this.portfolioId = portfolioId;
    }

    public BigDecimal getPortfolioId() {
        return portfolioId;
    }

    public void setPortfolioId(BigDecimal portfolioId) {
        this.portfolioId = portfolioId;
    }

    public BigInteger getAssignPriorityId() {
        return assignPriorityId;
    }

    public void setAssignPriorityId(BigInteger assignPriorityId) {
        this.assignPriorityId = assignPriorityId;
    }

    public String getPortfolioName() {
        return portfolioName;
    }

    public void setPortfolioName(String portfolioName) {
        this.portfolioName = portfolioName;
    }

    public String getProjectLifeCycle() {
        return projectLifeCycle;
    }

    public void setProjectLifeCycle(String projectLifeCycle) {
        this.projectLifeCycle = projectLifeCycle;
    }

    public String getPortfolioState() {
        return portfolioState;
    }

    public void setPortfolioState(String portfolioState) {
        this.portfolioState = portfolioState;
    }

    public PmsPortfolioType getPortfolioTypeId() {
        return portfolioTypeId;
    }

    public void setPortfolioTypeId(PmsPortfolioType portfolioTypeId) {
        this.portfolioTypeId = portfolioTypeId;
    }

    @XmlTransient
    public List<PmsCreateProjects> getPmsCreateProjectsList() {
        if(pmsCreateProjectsList==null){
            pmsCreateProjectsList=new ArrayList<>();
        }
        return pmsCreateProjectsList;
    }

    public void setPmsCreateProjectsList(List<PmsCreateProjects> pmsCreateProjectsList) {
        this.pmsCreateProjectsList = pmsCreateProjectsList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (portfolioId != null ? portfolioId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PmsCreatePortfolio)) {
            return false;
        }
        PmsCreatePortfolio other = (PmsCreatePortfolio) object;
        if ((this.portfolioId == null && other.portfolioId != null) || (this.portfolioId != null && !this.portfolioId.equals(other.portfolioId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "javaapplication1.PmsCreatePortfolio[ portfolioId=" + portfolioId + " ]";
    }

    @XmlTransient
    public List<PmsMaintainProg> getPmsMaintainProgList() {
        return pmsMaintainProgList;
    }

    public void setPmsMaintainProgList(List<PmsMaintainProg> pmsMaintainProgList) {
        this.pmsMaintainProgList = pmsMaintainProgList;
    }

    }
