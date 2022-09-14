/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.promotion;

import et.gov.eep.hrms.entity.promotion.HrLuPromExpRelation;
import et.gov.eep.hrms.mapper.promotion.HrLuPromExpRelationFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author user
 */
@Stateless
public class HrLuPromExpRelationBean implements HrLuPromExpRelationBeanLocal {

    @EJB
    HrLuPromExpRelationFacade hrLuPromExpRelationFacade;

    @Override
    public List<HrLuPromExpRelation> findAll() {
        return hrLuPromExpRelationFacade.findAll();
    }
}
