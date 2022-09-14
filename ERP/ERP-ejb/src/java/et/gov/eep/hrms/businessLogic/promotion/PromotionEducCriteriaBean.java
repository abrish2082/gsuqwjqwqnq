/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.promotion;

import et.gov.eep.hrms.entity.lookup.HrLuEducLevels;
import et.gov.eep.hrms.entity.promotion.HrPromotionEducCriterias;
import et.gov.eep.hrms.mapper.promotion.HrPromotionEducCriteriasFacade;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.model.SelectItem;

/**
 *
 * @author user
 */
@Stateless
public class PromotionEducCriteriaBean implements PromotionEducCriteriaBeanLocal {

    @EJB
    HrPromotionEducCriteriasFacade hrPromotionEducCriteriasFacade;

    @Override
    public List<HrLuEducLevels> findAll() {
        return hrPromotionEducCriteriasFacade.findAlleduc();
    }

    @Override
    public void saveOrUpdate(HrPromotionEducCriterias get) {
        hrPromotionEducCriteriasFacade.saveOrUpdate(get);
    }

    @Override
    public List<HrPromotionEducCriterias> findAllEducation() {
        return hrPromotionEducCriteriasFacade.findAll();
    }

}
