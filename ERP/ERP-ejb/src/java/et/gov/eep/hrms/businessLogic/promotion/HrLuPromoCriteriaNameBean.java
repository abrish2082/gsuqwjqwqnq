/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.promotion;

import et.gov.eep.hrms.entity.promotion.HrLuPromoCriteriaName;
import et.gov.eep.hrms.mapper.promotion.HrLuPromoCriteriaNameFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author user
 */
@Stateless
public class HrLuPromoCriteriaNameBean implements HrLuPromoCriteriaNameBeanLocal {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @EJB
    HrLuPromoCriteriaNameFacade hrLuPromoCriteriaNameFacade;

    @Override
    public List<HrLuPromoCriteriaName> loadCriterias() {
        return hrLuPromoCriteriaNameFacade.findAll();
    }

   
}
