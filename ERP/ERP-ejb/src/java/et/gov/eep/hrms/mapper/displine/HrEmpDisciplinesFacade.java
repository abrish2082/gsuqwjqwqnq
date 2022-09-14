/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.displine;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.displine.HrEmpDisciplines;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Abdi
 */
@Stateless
public class HrEmpDisciplinesFacade extends AbstractFacade<HrEmpDisciplines> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HrEmpDisciplinesFacade() {
        super(HrEmpDisciplines.class);
    }

    public List<HrEmpDisciplines> findPhaseOutList() {
        try {
            Query query = em.createNativeQuery("SELECT *\n"
                    + "FROM HR_EMP_DISCIPLINES ed\n"
                    + "where ed.STATUS = 0", HrEmpDisciplines.class);
            return (List<HrEmpDisciplines>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

}
