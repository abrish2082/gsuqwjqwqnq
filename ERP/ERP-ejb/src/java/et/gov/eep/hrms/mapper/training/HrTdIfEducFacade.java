/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.training;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.lookup.HrLuEducLevels;
import et.gov.eep.hrms.entity.lookup.HrLuEducTypes;
import et.gov.eep.hrms.entity.training.HrTdIfEduc;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Behailu
 */
@Stateless
public class HrTdIfEducFacade extends AbstractFacade<HrTdIfEduc> {
    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HrTdIfEducFacade() {
        super(HrTdIfEduc.class);
    }

    public List<HrLuEducLevels> findallEducationlevels() {
       Query query = em.createNamedQuery("HrLuEducLevels.findAll");
        try {
            return query.getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<HrLuEducTypes> findallEducationQualificationList() {
        Query query = em.createNamedQuery("HrLuEducTypes.findAll");
        try {
            return query.getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<HrTdIfEduc> filterByLocation(int filterstatus) {
        Query query = em.createNamedQuery("HrTdIfEduc.findByLocation");
        query.setParameter("location", filterstatus);
        try {
            return (List<HrTdIfEduc>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public HrTdIfEduc loadEduDetail(BigDecimal id) {
         Query query = em.createNamedQuery("HrTdIfEduc.findById");
        query.setParameter("id", id);
        try {
            HrTdIfEduc selectedu = (HrTdIfEduc) query.getResultList().get(0);
            return selectedu;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
}
