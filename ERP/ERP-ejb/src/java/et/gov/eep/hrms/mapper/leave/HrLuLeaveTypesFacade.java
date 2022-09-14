/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.leave;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.leave.HrLuLeaveTypes;
import java.util.ArrayList;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author insa
 */
@Stateless
public class HrLuLeaveTypesFacade extends AbstractFacade<HrLuLeaveTypes> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HrLuLeaveTypesFacade() {
        super(HrLuLeaveTypes.class);
    }
//<editor-fold defaultstate="collapsed" desc="Bussiness Immplementation">
    
    @Override
    public ArrayList<HrLuLeaveTypes> findAll() {
        Query query = em.createNamedQuery("HrLuLeaveTypes.findAll");
        try {
            ArrayList<HrLuLeaveTypes> types = new ArrayList(query.getResultList());
            return types;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    public HrLuLeaveTypes findLeaveTypeByName(HrLuLeaveTypes leaveTypes) {
        Query query = em.createNamedQuery("HrLuLeaveTypes.findByLeaveName");
        query.setParameter("leaveName", leaveTypes.getLeaveName());
        try {
            HrLuLeaveTypes relname = (HrLuLeaveTypes) query.getResultList().get(0);
            return relname;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public ArrayList<HrLuLeaveTypes> findLeavetypes(HrLuLeaveTypes leaveTypes) {
        Query query = em.createNamedQuery("HrLuLeaveTypes.findLikeLeaveName", HrLuLeaveTypes.class);
        query.setParameter("leaveName", leaveTypes.getLeaveName() + "%");
        try {
            
            ArrayList<HrLuLeaveTypes> leaveNames = new ArrayList(query.getResultList());
            return leaveNames;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
//</editor-fold>
}
