/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.displine;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.displine.HrDisciplineOffenceTypes;
import et.gov.eep.hrms.entity.displine.HrDisciplinePenlitys;
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
public class HrDisciplinePenlitysFacade extends AbstractFacade<HrDisciplinePenlitys> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HrDisciplinePenlitysFacade() {
        super(HrDisciplinePenlitys.class);
    }

    public List<HrDisciplinePenlitys> findByOffenceId(HrDisciplineOffenceTypes offenceTypes) {
        List<HrDisciplinePenlitys> hrDisciplinePenlityses = null;
        try {
            Query query = em.createNamedQuery("HrDisciplinePenlitys.findByOffenceID", HrDisciplinePenlitys.class);
            query.setParameter("id", offenceTypes.getId());
            hrDisciplinePenlityses = (List<HrDisciplinePenlitys>) query.getResultList();
            return hrDisciplinePenlityses;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
