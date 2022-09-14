/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.overtime;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.insurance.HrInsuranceInjuredEmployee;
import et.gov.eep.hrms.entity.overtime.HrOvertimeRates;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author meles
 */
@Stateless
public class HrOvertimeRatesFacade extends AbstractFacade<HrOvertimeRates> {
    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HrOvertimeRatesFacade() {
        super(HrOvertimeRates.class);
    }

    public List<HrOvertimeRates> findbyRateTypes(HrOvertimeRates hrOvertimeRate) {
         Query query = em.createNamedQuery("HrOvertimeRates.findByOtTypes");
        query.setParameter("otTypes", hrOvertimeRate.getOtTypes().toUpperCase() + '%');
        try {
            List<HrOvertimeRates> compList = new ArrayList(query.getResultList());
            return compList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public HrOvertimeRates getSelectedRequest(int id) {
        Query query = em.createNamedQuery("HrOvertimeRates.findById");
        query.setParameter("id", id);
        try {
            HrOvertimeRates selectrequest = (HrOvertimeRates) query.getResultList().get(0);
            return selectrequest;
        } catch (Exception ex) {
            return null;
        }
    }

    public boolean searchdup(HrOvertimeRates hrOvertimeRate) {
       boolean duplicaton;
        Query query = em.createNamedQuery("HrOvertimeRates.findByRateName1", HrOvertimeRates.class);
        query.setParameter("otTypes1", hrOvertimeRate.getOtTypes());
        try {
            if (query.getResultList().size() > 0) {
                duplicaton = true;
            } else {
                duplicaton = false;
            }
            return duplicaton;
        } catch (Exception ex) {
            return false;
        }
    }

    public ArrayList<HrOvertimeRates> findByRateType(HrOvertimeRates hrOvertimeRate) {
         Query queryReq = em.createNamedQuery("HrOvertimeRates.findByOtTypes");
        try {
            queryReq.setParameter("otTypes", hrOvertimeRate.getOtTypes());
            ArrayList<HrOvertimeRates> rateList = new ArrayList(queryReq.getResultList());
            return rateList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
}
