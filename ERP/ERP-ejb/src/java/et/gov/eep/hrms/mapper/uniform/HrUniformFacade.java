/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.uniform;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.organization.HrJobTypes;
import et.gov.eep.hrms.entity.uniform.HrUniform;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Behailu
 */
@Stateless
public class HrUniformFacade extends AbstractFacade<HrUniform> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HrUniformFacade() {
        super(HrUniform.class);
    }

    public List<HrUniform> findByJobId(Integer id) {
        HrJobTypes hrJobTypes= new HrJobTypes();
        hrJobTypes.setId(id);
        System.out.println("mapper jobId=="+hrJobTypes.getId());
        Query query = em.createNamedQuery("HrUniform.findByJobTypeId");
        query.setParameter("jobTypeId", hrJobTypes.getId());
        try {
            return (List<HrUniform>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public HrUniform loadUniformDetail(BigDecimal id) {
        Query query = em.createNamedQuery("HrUniform.findById");
        query.setParameter("id", id);
        try {
            HrUniform selectrequest = (HrUniform) query.getResultList().get(0);
            return selectrequest;
        } catch (Exception ex) {
            return null;
        }
    }

}
