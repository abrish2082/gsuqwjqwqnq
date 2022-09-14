/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.leave;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.fcms.entity.FmsLuBudgetYear;
import et.gov.eep.hrms.entity.leave.HrLeaveAccruedBalance;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author User
 */
@Stateless
public class HrLeaveAccruedBalanceFacade extends AbstractFacade<HrLeaveAccruedBalance> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HrLeaveAccruedBalanceFacade() {
        super(HrLeaveAccruedBalance.class);
    }
//<editor-fold defaultstate="collapsed" desc="Bussiness IMmplementatin">
    
    public List<HrLeaveAccruedBalance> findAccruedLeaveByLeaveYear(FmsLuBudgetYear fmsLuBudgetYear) {
        Query query = em.createNamedQuery("HrLeaveAccruedBalance.findByLeaveYear");
        query.setParameter("leaveYear", fmsLuBudgetYear.getBudgetYear());
        try {
            ArrayList<HrLeaveAccruedBalance> balances = new ArrayList(query.getResultList());
            return balances;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public HrLeaveAccruedBalance findByBudgetYear(String PrevY) {
        HrLeaveAccruedBalance balances ;
        Query query = em.createNamedQuery("HrLeaveAccruedBalance.findByLeaveYear");
        query.setParameter("leaveYear", PrevY);
        if( query.getResultList().size() >=1){
            balances = (HrLeaveAccruedBalance) query.getResultList().get(0);
            return balances;
        }else{
            return null;
        }
        
    }
//</editor-fold>
}
