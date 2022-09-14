/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.insure;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.insurance.HrLuInsuranceBranches;
import et.gov.eep.hrms.entity.insurance.HrLuInsurances;
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
public class HrLuInsurancesFacade extends AbstractFacade<HrLuInsurances> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HrLuInsurancesFacade() {
        super(HrLuInsurances.class);
    }

    //<editor-fold defaultstate="collapsed" desc="Bussiness IMmplementatin">
    public List<HrLuInsuranceBranches> findbyID(HrLuInsurances hrLuInsurances) {
        try {
            Query query = em.createNamedQuery("HrLuInsuranceBranches.insu", HrLuInsuranceBranches.class);
            query.setParameter("sid", hrLuInsurances.getId());
            return (List<HrLuInsuranceBranches>) query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }
    
    public List<HrLuInsurances> findbyID(HrLuInsuranceBranches hrLuInsuranceBranches) {
        try {
            Query query = em.createNamedQuery("HrLuInsuranceBranches.findById", HrLuInsuranceBranches.class);
            query.setParameter("id", hrLuInsuranceBranches.getInsuranceId());
            return (List<HrLuInsurances>) query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }
    
    public ArrayList<HrLuInsurances> insurancename(HrLuInsurances hrLuInsurances) {
        Query query = em.createNamedQuery("HrLuInsurances.findByTerminationNameLike");
        query.setParameter("insuranceName", hrLuInsurances.getInsuranceName().toUpperCase() + '%');
        try {
            ArrayList<HrLuInsurances> terminationNameList = new ArrayList(query.getResultList());
            return terminationNameList;
        } catch (Exception ex) {
            return null;
        }
    }
//</editor-fold>
}
