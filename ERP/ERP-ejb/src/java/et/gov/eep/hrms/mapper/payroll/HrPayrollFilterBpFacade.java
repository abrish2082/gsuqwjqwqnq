/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.payroll;

import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.payroll.HrPayrollFilterBp;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import et.gov.eep.commonApplications.mapper.AbstractFacade;

/**
 *
 * @author user
 */
@Stateless
public class HrPayrollFilterBpFacade extends AbstractFacade<HrPayrollFilterBp> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HrPayrollFilterBpFacade() {
        super(HrPayrollFilterBp.class);
    }

    public int removeEmpFromPayment(HrEmployees emp) {
        Query q = em.createNamedQuery("HrPayrollFilterBp.delete");
        q.setParameter("empId", emp.getId());
        return q.executeUpdate();

    }

    public List<HrEmployees> select() {
        Query q = em.createNamedQuery("HrPayrollFilterBp.SELL");
        return (List<HrEmployees>) q.getResultList();
    }

}
