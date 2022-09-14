/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.allowance;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.allowance.HrAllowanceInLocations;
import et.gov.eep.hrms.entity.payroll.HrPayrollEarningDeductions;
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
public class HrAllowanceInLocationsFacade extends AbstractFacade<HrAllowanceInLocations> {

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
    public HrAllowanceInLocationsFacade() {
        super(HrAllowanceInLocations.class);
    }

    // <editor-fold defaultstate="collapsed" desc="Named query">
    /**
     *
     * @return
     */
    public List<HrPayrollEarningDeductions> listOfAllowncesInLocation() {
        try {
            Query q = em.createNamedQuery("HrAllowanceInLocations.findAllAllowanceInJobTitle");
            return (List<HrPayrollEarningDeductions>) q.getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

    }

    /**
     *
     * @param al
     * @return
     */
    public HrAllowanceInLocations checkAllInLocation(HrAllowanceInLocations al) {
        try {
            Query q = em.createNamedQuery("HrAllowanceInLocations.checkAllInLoca");
            q.setParameter("code", al.getEarningDeductionCode().getCode());
//            q.setParameter("addressId", al.getLocationId().getAddressId());// this was commented after it i clone the project 
            //from the server and b/c of i just make some use of the table pay location and pay group
            q.setParameter("id", al.getLevelId().getId());
            return (HrAllowanceInLocations) q.getSingleResult();
        } catch (Exception ex) {
            return null;
        }
    }

    public HrAllowanceInLocations checkAllInLocationForUpdate(HrAllowanceInLocations al
    ) {
        try {
            Query q = em.createNamedQuery("HrAllowanceInLocations.checkAllInLocaForUpdate");
            q.setParameter(1, al.getEarningDeductionCode().getCode());
//            q.setParameter(2, al.getLocationId().getAddressId());
            q.setParameter(3, al.getLevelId().getId());
            q.setParameter(4, al.getId());

            if (q.getResultList().isEmpty()) {
                return null;
            } else {
                return (HrAllowanceInLocations) q.getSingleResult();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param al
     * @return
     */
    public HrAllowanceInLocations findAllInLocationById(HrAllowanceInLocations al
    ) {
        try {
            Query q = em.createNamedQuery("HrAllowanceInLocations.findById");
            q.setParameter("id", al.getId());

            return (HrAllowanceInLocations) q.getSingleResult();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

    }

    /**
     *
     * @param ed
     * @return
     */
    public List<HrAllowanceInLocations> findAllowncePaymentTypes(HrPayrollEarningDeductions ed
    ) {
        try {
            Query q = em.createNamedQuery("HrAllowanceInLocations.findPaymentTypes");
            q.setParameter("code", ed.getCode());
            return (List<HrAllowanceInLocations>) q.getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

    }
//</editor-fold>
}
