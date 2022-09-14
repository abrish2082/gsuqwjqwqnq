/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.insure;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.insurance.HrLuInsuranceBranches;
import et.gov.eep.hrms.entity.insurance.HrLuInsurances;
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
public class HrLuInsuranceBranchesFacade extends AbstractFacade<HrLuInsuranceBranches> {
    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HrLuInsuranceBranchesFacade() {
        super(HrLuInsuranceBranches.class);
    }

    public List<HrLuInsuranceBranches> findbyID(HrLuInsurances hrLuInsurances) {
      try {
            Query query = em.createNamedQuery("HrLuInsuranceBranches.findById", HrLuInsuranceBranches.class);
            query.setParameter("id", hrLuInsurances.getId());
            return (List<HrLuInsuranceBranches>) query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }
    
}
