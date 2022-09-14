/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.lookup;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import et.gov.eep.hrms.entity.lookup.HrLuEducTypes;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Administrator
 */
@Stateless
public class HrLuEducTypesFacade extends AbstractFacade<HrLuEducTypes> {

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
    public HrLuEducTypesFacade() {
        super(HrLuEducTypes.class);
    }
    
     public HrLuEducTypes searchEducTypeById(int id) {
        Query query = em.createNamedQuery("HrLuEducTypes.findById");
        query.setParameter("id", id);
        return (HrLuEducTypes) query.getSingleResult();
    }     
    
    public HrLuEducTypes findbyLuEduType(HrLuEducTypes hrLuEducTypes) {
        Query query = em.createNamedQuery("HrLuEducTypes.findByEducType");
        query.setParameter("educType", hrLuEducTypes.getEducType());
        try {
            HrLuEducTypes educType = (HrLuEducTypes) query.getResultList().get(0);
            return educType;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    
}
