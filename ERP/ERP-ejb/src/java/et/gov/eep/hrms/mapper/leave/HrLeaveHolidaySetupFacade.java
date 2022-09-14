/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.leave;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.fcms.entity.FmsLuBudgetYear;
import et.gov.eep.hrms.entity.leave.HrLeaveHolidaySetup;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author user
 */
@Stateless
public class HrLeaveHolidaySetupFacade extends AbstractFacade<HrLeaveHolidaySetup> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HrLeaveHolidaySetupFacade() {
        super(HrLeaveHolidaySetup.class);
    }
//<editor-fold defaultstate="collapsed" desc="Bussiness IMmplementatin">
    
    public List<HrLeaveHolidaySetup> findByBudgetyYear(FmsLuBudgetYear budgetYear) {
        try {
            Query query = em.createNamedQuery("HrLeaveHolidaySetup.findByBudgetYear");
            query.setParameter("budgetYear", budgetYear.getBudgetYear());
            return (List<HrLeaveHolidaySetup>) query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public int isBtwn(Date startDate, Date time) {
        Query query = em.createNamedQuery("HrLeaveHolidaySetup.isbeteween");
        query.setParameter("x", startDate);
        query.setParameter("y", time);
        if (query.getResultList().size() > 0) {
            return query.getResultList().size();
        }
        return 0;
    }
//</editor-fold>
}
