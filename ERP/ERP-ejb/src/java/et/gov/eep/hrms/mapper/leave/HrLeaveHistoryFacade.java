/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.leave;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.leave.HrLeaveHistory;
import et.gov.eep.hrms.entity.leave.HrLuLeaveTypes;//HrLeaveHistory;
import java.util.ArrayList;
import java.util.Collections;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author insa
 */
@Stateless
public class HrLeaveHistoryFacade extends AbstractFacade<HrLeaveHistory> {
    
    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public HrLeaveHistoryFacade() {
        super(HrLeaveHistory.class);
    }

    //<editor-fold defaultstate="collapsed" desc="Bussiness IMmplementatin">
    @Override
    public ArrayList<HrLeaveHistory> findAll() {
        Query query = em.createNamedQuery("HrLeaveHistory.findAll");
        try {
            ArrayList<HrLeaveHistory> jobs = new ArrayList(query.getResultList());
            return jobs;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    public ArrayList<HrLeaveHistory> searchLeaveHistory(HrLeaveHistory leaveHistory, HrLuLeaveTypes leaveType, HrEmployees employee) {
        System.err.println("===" + leaveHistory.getYear());
        System.err.println("===" + leaveType.getId());
        System.err.println("===" + employee.getEmpId());
        Query query = em.createNativeQuery("SELECT H.* \n"
                + " FROM HR_LEAVE_HISTORY H \n "
                + " INNER JOIN HR_EMPLOYEE e \n "
                + " ON H.EMP_ID = e.EMP_ID \n "
                + " INNER JOIN HR_LU_LEAVE_TYPE T \n"
                + " ON H.LEAVE_TYPE_ID = T.ID \n"
                + " WHERE H.YEAR       = ?1 \n"
                + " AND e.EMP_ID       = ?2 \n"
                + " AND T.id           = ?3 ", HrLeaveHistory.class);
        
        try {

//            x=(Object) query.getResultList().get(0);
            query.setParameter(1, leaveHistory.getYear());
            query.setParameter(2, employee.getEmpId());
            query.setParameter(3, leaveType.getId());
            System.err.println("=list of leave history size===" + query.getResultList().size());
            ArrayList temp = new ArrayList();
            
            if (query.getResultList().size() > 0) {
                ArrayList<HrLeaveHistory> leaves = new ArrayList(query.getResultList());
                int b = query.getResultList().size();
                //put the obje
                for (int i = 0; i < b; i++) {
                    HrLeaveHistory history = new HrLeaveHistory();
                    history = (HrLeaveHistory) query.getResultList().get(i);
                    // if(history.getStatus()!=){
                    temp.add(history.getAvailableDay());
                    history = null;
                    
                }
                System.out.println("the size of new list==========" + temp.size());
                Collections.sort(temp, Collections.reverseOrder());
                System.out.println("the minimum is" + temp.get(b - 1));
                int indexval = (int) temp.get(b - 1);
                
                leaveHistory.setAvailableDay(indexval);
                return leaves;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    public ArrayList<HrLeaveHistory> searchPendingLeaveHistory() {
        
        Query query = em.createNativeQuery("SELECT H.* \n"
                + " FROM HR_LEAVE_HISTORY H \n "
                + " INNER JOIN HR_EMPLOYEE e \n "
                + " ON H.EMP_ID = e.EMP_ID \n "
                + " INNER JOIN HR_LU_LEAVE_TYPE T \n"
                + " ON H.LEAVE_TYPE_ID = T.ID \n", HrLeaveHistory.class);
        
        try {
            
            System.err.println("====" + query.getResultList().size());
            ArrayList temp = new ArrayList();
            
            if (query.getResultList().size() > 0) {
                ArrayList<HrLeaveHistory> leaves = new ArrayList(query.getResultList());
                int b = query.getResultList().size();
                //put the obje
                for (int i = 0; i < b; i++) {
                    HrLeaveHistory history = new HrLeaveHistory();
                    history = (HrLeaveHistory) query.getResultList().get(i);
                    
                    if ("Pending...".equals(history.getStatus())) {
                        temp.add(history);
                    }
                    history = null;
                    
                }
                
                return temp;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
//</editor-fold>
}
