/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.promotion;

import et.gov.eep.hrms.entity.promotion.HrPromExprinceCriteria;
import et.gov.eep.hrms.mapper.promotion.HrPromExprinceCriteriaFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author user
 */
@Stateless
public class HrPromExprinceCriteriaBean implements HrPromExprinceCriteriaBeanLocal {

    @EJB
    HrPromExprinceCriteriaFacade hrPromExprinceCriteriaFacade;
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    @Override
    public void saveOrUpdate(HrPromExprinceCriteria get) {
        hrPromExprinceCriteriaFacade.saveOrUpdate(get);
    }

    @Override
    public List<HrPromExprinceCriteria> findAllExprienceRelations() {
        return hrPromExprinceCriteriaFacade.findAll();
    }
}
