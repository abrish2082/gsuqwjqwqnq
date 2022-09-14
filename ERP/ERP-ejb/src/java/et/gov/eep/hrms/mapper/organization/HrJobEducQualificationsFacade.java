/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.organization;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.organization.HrJobEducQualifications;
import et.gov.eep.hrms.entity.organization.HrJobTypes;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Administrator
 */
@Stateless
public class HrJobEducQualificationsFacade extends AbstractFacade<HrJobEducQualifications> {

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
    public HrJobEducQualificationsFacade() {
        super(HrJobEducQualifications.class);
    }

    public List<HrJobEducQualifications> getByJobID(HrJobTypes jobTypes) {
        try {
            Query query = em.createNamedQuery("HrJobEducQualifications.findByJobID", HrJobEducQualifications.class);
            query.setParameter("jobId", jobTypes);
            return (List<HrJobEducQualifications>) query.getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

}
