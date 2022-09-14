/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.evaluation;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.evaluation.HrEvaResultDetailProbation;
import java.util.ArrayList;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Ob
 */
@Stateless
public class HrEvaResultDetailProbationFacade extends AbstractFacade<HrEvaResultDetailProbation> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HrEvaResultDetailProbationFacade() {
        super(HrEvaResultDetailProbation.class);
    }

    @Override
    public ArrayList<HrEvaResultDetailProbation> findAll() {
        Query query = em.createNamedQuery("HrEvaResultDetailProbation.findAll", HrEvaResultDetailProbation.class);
        try {
            ArrayList<HrEvaResultDetailProbation> result = new ArrayList(query.getResultList());
            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

}
