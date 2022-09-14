/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.EmployeeBonus;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.EmployeeBonus.HrEmployeesBonusDetail;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author meles
 */
@Stateless
public class HrEmployeesBonusDetailFacade extends AbstractFacade<HrEmployeesBonusDetail> {
    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HrEmployeesBonusDetailFacade() {
        super(HrEmployeesBonusDetail.class);
    }
    
}
