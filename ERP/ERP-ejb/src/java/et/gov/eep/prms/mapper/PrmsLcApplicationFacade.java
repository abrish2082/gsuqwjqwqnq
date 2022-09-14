/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.mapper;
import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.prms.entity.PrmsLcApplication;
import java.math.BigDecimal;
import java.util.ArrayList;
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
public class PrmsLcApplicationFacade extends AbstractFacade<PrmsLcApplication> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PrmsLcApplicationFacade() {
        super(PrmsLcApplication.class);
    }

    public List<PrmsLcApplication> getLCAppNo(PrmsLcApplication prmsLcApplication) {
        System.out.println("--------yy-------"+prmsLcApplication.getLcAppNo());
        Query query = em.createNamedQuery("PrmsLcApplication.findByLcAppNos", PrmsLcApplication.class);
        query.setParameter("lcAppNo", prmsLcApplication.getLcAppNo()+ '%');
        try {
            List<PrmsLcApplication> marketAssessmentLst = new ArrayList(query.getResultList());
            System.out.println("-------------2========"+marketAssessmentLst.size());
            return marketAssessmentLst;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public PrmsLcApplication getSelectedId(BigDecimal id) {
        Query query = em.createNamedQuery("PrmsLcApplication.findByLcappid", PrmsLcApplication.class);
        query.setParameter("lcappid", id);
        try {
            PrmsLcApplication idlst = (PrmsLcApplication) query.getResultList().get(0);
            return idlst;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public PrmsLcApplication getLastLCAppNo() {
        Query query = em.createNamedQuery("PrmsLcApplication.findByMaxLCAppId", PrmsLcApplication.class);
        PrmsLcApplication result = null;
        try {
            if (query.getResultList().size() > 0) {
                result = (PrmsLcApplication) query.getResultList().get(0);
            }

            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

}
