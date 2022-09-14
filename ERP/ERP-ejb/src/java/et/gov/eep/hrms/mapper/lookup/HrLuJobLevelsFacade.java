/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.lookup;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.lookup.HrLuJobLevels;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Administrator
 */
@Stateless
public class HrLuJobLevelsFacade extends AbstractFacade<HrLuJobLevels> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    /**
     *
     * @return
     */
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     *
     */
    public HrLuJobLevelsFacade() {
        super(HrLuJobLevels.class);
    }

    public HrLuJobLevels searchJobLevelById(int id) {
        try {
            Query query = em.createNamedQuery("HrLuJobLevels.findById");
            query.setParameter("id", id);
            return (HrLuJobLevels) query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
     public HrLuJobLevels findLevel(HrLuJobLevels hrLuJobLevels) {
        try {
            System.out.println("------emp joblevel-----");
            Query query = em.createNamedQuery("HrLuJobLevels.findByJobLevel");
            query.setParameter("jobLevel", hrLuJobLevels.getJobLevel());
            return (HrLuJobLevels) query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
}
