/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.leave;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.leave.HrLuAllowedLeave;
import java.util.ArrayList;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author User
 */
@Stateless
public class HrLuAllowedLeaveFacade extends AbstractFacade<HrLuAllowedLeave> implements HrLuAllowedLeaveFacadeLocal {
    
    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HrLuAllowedLeaveFacade() {
        super(HrLuAllowedLeave.class);
    }
    
    //<editor-fold defaultstate="collapsed" desc="Bussiness Immplementation">
    @Override
    public ArrayList<HrLuAllowedLeave> findAll() {
        Query query = em.createNamedQuery("HrLuAllowedLeave.findAll");
        try {
            ArrayList<HrLuAllowedLeave> jobs = new ArrayList(query.getResultList());
            return jobs;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    public HrLuAllowedLeave findAlloedLeave(Integer hrLuAllowedLeave) {
        
        System.out.println("hrLuAllowedLeave.getNoOfYr()=from facade==" + hrLuAllowedLeave);
        Query query = em.createNativeQuery("select * from HR_LU_ALLOWED_LEAVE where NO_OF_YR ='" + hrLuAllowedLeave + "'", HrLuAllowedLeave.class);
        return (HrLuAllowedLeave) query.getSingleResult();
        
    }
//</editor-fold>
    
}
