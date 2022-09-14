/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.planning;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.planning.HrHrpRecruitmentRequest;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Ob
 */
@Stateless
public class HrHrpRecruitmentRequestFacade extends AbstractFacade<HrHrpRecruitmentRequest> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HrHrpRecruitmentRequestFacade() {
        super(HrHrpRecruitmentRequest.class);
    }


}
