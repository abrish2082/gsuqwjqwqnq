/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.pms.entity;

import et.gov.eep.pms.entity.PmsCreatePortfolio;
import et.gov.eep.pms.entity.PmsCreateProjects;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Me
 */
@Entity
@Table(name = "PMS_MAINTAIN_PROG")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PmsMaintainProg.findAll", query = "SELECT p FROM PmsMaintainProg p"),
    @NamedQuery(name = "PmsMaintainProg.findByProgramId", query = "SELECT p FROM PmsMaintainProg p WHERE p.programId = :programId"),
    @NamedQuery(name = "PmsMaintainProg.findByProgramName", query = "SELECT p FROM PmsMaintainProg p WHERE p.programName = :programName"),
    @NamedQuery(name = "PmsMaintainProg.findByProDate", query = "SELECT p FROM PmsMaintainProg p WHERE p.proDate = :proDate"),
    @NamedQuery(name = "PmsMaintainProg.findByRemark", query = "SELECT p FROM PmsMaintainProg p WHERE p.remark = :remark")})
public class PmsMaintainProg implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "PROGRAM_ID")
    private Integer programId;
    @Size(max = 200)
    @Column(name = "PROGRAM_NAME")
    private String programName;
    @Column(name = "PRO_DATE")
    @Temporal(TemporalType.DATE)
    private Date proDate;
    @Size(max = 400)
    @Column(name = "REMARK")
    private String remark;
    @OneToMany(mappedBy = "programId")
    private List<PmsCreateProjects> pmsCreateProjectsList;
    @JoinColumn(name = "PORTFOLIO_ID", referencedColumnName = "PORTFOLIO_ID")
    @ManyToOne
    private PmsCreatePortfolio portfolioId;

    public PmsMaintainProg() {
    }

    public PmsMaintainProg(Integer programId) {
        this.programId = programId;
    }

    public Integer getProgramId() {
        return programId;
    }

    public void setProgramId(Integer programId) {
        this.programId = programId;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public Date getProDate() {
        return proDate;
    }

    public void setProDate(Date proDate) {
        this.proDate = proDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @XmlTransient
    public List<PmsCreateProjects> getPmsCreateProjectsList() {
        return pmsCreateProjectsList;
    }

    public void setPmsCreateProjectsList(List<PmsCreateProjects> pmsCreateProjectsList) {
        this.pmsCreateProjectsList = pmsCreateProjectsList;
    }

    public PmsCreatePortfolio getPortfolioId() {
        return portfolioId;
    }

    public void setPortfolioId(PmsCreatePortfolio portfolioId) {
        this.portfolioId = portfolioId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (programId != null ? programId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PmsMaintainProg)) {
            return false;
        }
        PmsMaintainProg other = (PmsMaintainProg) object;
        if ((this.programId == null && other.programId != null) || (this.programId != null && !this.programId.equals(other.programId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.fcms.entity.PmsMaintainProg[ programId=" + programId + " ]";
    }
    
}
