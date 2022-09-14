/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.payroll;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.payroll.HrPayrollBackPaymentGroups;
import et.gov.eep.hrms.entity.payroll.HrPayrollMaintainBackPay;
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
public class HrPayrollMaintainBackPayFacade extends AbstractFacade<HrPayrollMaintainBackPay>  {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HrPayrollMaintainBackPayFacade() {
        super(HrPayrollMaintainBackPay.class);
    }

    public int closeIndBackPayment(HrEmployees emp) {
        try {
            Query q = em.createNamedQuery("HrPayrollMaintainBackPay.closeIndBk");
          
            q.setParameter("empId", emp);
            return q.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    public List<HrPayrollMaintainBackPay> findEmployeesBackPayment(HrEmployees emp) {
        try {
            Query q = em.createNamedQuery("HrPayrollMaintainBackPay.findBkPaymentOfInd");
            q.setParameter("empId", emp);
            return (List<HrPayrollMaintainBackPay>) q.getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<HrPayrollMaintainBackPay> findEmployeesBackPaymentGroup() {
        try {
            Query q = em.createNamedQuery("HrPayrollMaintainBackPay.loadPaymentMadeForGroup");

            return (List<HrPayrollMaintainBackPay>) q.getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<HrPayrollMaintainBackPay> findEmployeesBackPaymentGroupEach(HrEmployees emp,HrPayrollBackPaymentGroups gr) {
        try {
            Query q = em.createNamedQuery("HrPayrollMaintainBackPay.loadPaymentMadeForGroupEach");
            q.setParameter("empId", emp);
             q.setParameter("backPayGroupId", gr);
            
            
            
            

            return (List<HrPayrollMaintainBackPay>) q.getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<HrPayrollMaintainBackPay> findEmployeesBackPaymentInd() {
        try {
            Query q = em.createNamedQuery("HrPayrollMaintainBackPay.loadPaymentMadeForInd");

            return (List<HrPayrollMaintainBackPay>) q.getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

}
