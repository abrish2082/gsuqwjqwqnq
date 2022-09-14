/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.leave;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.fcms.entity.FmsLuBudgetYear;
import et.gov.eep.hrms.entity.leave.HrLeaveSchedule;
import et.gov.eep.hrms.entity.leave.HrLeaveScheduleTransfer;
import java.util.ArrayList;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author user
 */
@Stateless
public class HrLeaveScheduleTransferFacade extends AbstractFacade<HrLeaveScheduleTransfer> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HrLeaveScheduleTransferFacade() {
        super(HrLeaveScheduleTransfer.class);
    }

    //<editor-fold defaultstate="collapsed" desc="Bussiness IMmplementatin">
    public boolean isTransferRequestExist(HrLeaveSchedule hrleaveschdule) {
        Query q = em.createNamedQuery("HrLeaveScheduleTransfer.checkExistance");
        q.setParameter("sche", hrleaveschdule.getId());
        try {
            ArrayList<HrLeaveScheduleTransfer> lists = new ArrayList<>(q.getResultList());
            if (q.getResultList().size() > 0) {
                return true;
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }
    
    public ArrayList<HrLeaveScheduleTransfer> loadScheduleTransfeRequests() {
        
        Query query = em.createNamedQuery("HrLeaveScheduleTransfer.findAllRequests");
        
        try {
            if (query.getResultList().size() > 0) {
                ArrayList<HrLeaveScheduleTransfer> req = new ArrayList(query.getResultList());
                return req;
            }
            
        } catch (Exception ex) {
            ex.printStackTrace();
            
        }
        ArrayList<HrLeaveScheduleTransfer> req = new ArrayList<>();
        
        return req;
    }
    
//</editor-fold>
}
