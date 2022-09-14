/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.lookup;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.lookup.HrLuEducLevels;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Administrator
 */
@Stateless
public class HrLuEducLevelsFacade extends AbstractFacade<HrLuEducLevels> {

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
    public HrLuEducLevelsFacade() {
        super(HrLuEducLevels.class);
    }

    public HrLuEducLevels searchEduclevelById(int id) {
        Query query = em.createNamedQuery("HrLuEducLevels.findById");
        query.setParameter("id", id);
        return (HrLuEducLevels) query.getSingleResult();
    }

    public HrLuEducLevels findbyLuEduLevel(HrLuEducLevels hrLuEducLevels) {
        Query query = em.createNamedQuery("HrLuEducLevels.findByEducLevel");
        query.setParameter("educLevel", hrLuEducLevels.getEducLevel());
        try {
            HrLuEducLevels educLevels = (HrLuEducLevels) query.getResultList().get(0);
            return educLevels;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

}
