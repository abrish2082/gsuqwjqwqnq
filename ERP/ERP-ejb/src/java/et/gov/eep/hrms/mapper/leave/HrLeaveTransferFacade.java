/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.leave;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.fcms.entity.FmsLuBudgetYear;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.leave.HrLeaveTransfer;
import et.gov.eep.hrms.entity.leave.HrLeaveTransferDetail;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author insa
 */
@Stateless
public class HrLeaveTransferFacade extends AbstractFacade<HrLeaveTransfer> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HrLeaveTransferFacade() {
        super(HrLeaveTransfer.class);
    }
//<editor-fold defaultstate="collapsed" desc="Bussiness Immplementation">
    
    public List<String> getApproveList() {
        
        List<String> approvedList = null;
        //int x = 0;
        String x = "0";
        try {
            Query query = em.createNamedQuery("HrLeaveTransfer.findByStatus");
            query.setParameter("status", x);
            approvedList = (List<String>) query.getResultList();
            return approvedList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public HrLeaveTransfer findById(int transferId) {
        Query query = em.createNamedQuery("HrLeaveTransfer.findByTransferId");
        
        query.setParameter("transferId", transferId);
        try {
            HrLeaveTransfer delegatorInfo = (HrLeaveTransfer) query.getResultList().get(0);
            return delegatorInfo;
        } catch (Exception ex) {
            return null;
        }
    }
    
    public List<HrLeaveTransfer> findByStatus(int transferId) {
        try {
            Query query = em.createNamedQuery("HrLeaveTransfer.findByStatus");
            query.setParameter("status", transferId);
            return (List<HrLeaveTransfer>) query.getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public List<HrLeaveTransferDetail> getByParentID(HrLeaveTransfer hlt) {
        try {
            Query query = em.createNamedQuery("HrLeaveTransferDetail.findByParentId");
            query.setParameter("parentID", hlt);
            return (List<HrLeaveTransferDetail>) query.getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public HrLeaveTransfer findByRequester(HrEmployees employee, int budgetyearID) {
        try {
            Query query = em.createNamedQuery("HrLeaveTransfer.findByRequester");
            query.setParameter("empId", employee);
            query.setParameter("budgetyearID", budgetyearID);
            return (HrLeaveTransfer) query.getSingleResult();
        } catch (Exception ex) {
            //ex.printStackTrace();
            return null;
        }
    }
    
    public List<HrLeaveTransfer> findRequestList() {
        try {
            Query query = em.createNamedQuery("HrLeaveTransfer.findByStatus");
            query.setParameter("status", 0);
            return (List<HrLeaveTransfer>) query.getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
//</editor-fold>
}
