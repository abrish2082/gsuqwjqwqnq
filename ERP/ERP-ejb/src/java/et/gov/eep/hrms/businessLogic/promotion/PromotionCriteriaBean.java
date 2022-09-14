/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.promotion;

import et.gov.eep.hrms.entity.promotion.HrPromotionCriterias;
import et.gov.eep.hrms.mapper.promotion.HrPromotionCriteriasFacade;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.model.SelectItem;
import javax.inject.Inject;

/**
 *
 * @author user
 */
@Stateless
public class PromotionCriteriaBean implements PromotionCriteriaBeanLocal {

    @EJB
    HrPromotionCriteriasFacade hrPromotionCriteriasFacade;

    @Inject
    HrPromotionCriterias hrPromotionCriterias;

//    @Override
//    public List<SelectItem> criteriaTypes() {
//        List<SelectItem> selectItems = new ArrayList<>();
//        selectItems.add(new SelectItem("", "--- Select ---"));
//        selectItems.add(new SelectItem(String.valueOf(hrPromotionCriterias.EDUCATION), "Education Level"));
//        selectItems.add(new SelectItem(String.valueOf(hrPromotionCriterias.PERFORMANCE), "Performance Result (BSC)"));
//        selectItems.add(new SelectItem(String.valueOf(hrPromotionCriterias.EXPERINCE), "Work Experiance"));
//        selectItems.add(new SelectItem(String.valueOf(hrPromotionCriterias.TRAINING), "TRAINING"));
//        selectItems.add(new SelectItem(String.valueOf(hrPromotionCriterias.INTERVIEWORTEST), "INTERVIEW OR TEST"));
//        return selectItems;
//    }
//
//    @Override
//    public String getCriteriaLabel(int criteriaId) {
//        if (criteriaId == hrPromotionCriterias.EDUCATION) {
//            return "Education Level";
//        } else if (criteriaId == hrPromotionCriterias.PERFORMANCE) {
//            return "Performance Result (BSC)";
//        } else if (criteriaId == hrPromotionCriterias.EXPERINCE) {
//            return "Work Experience";
//        } else if (criteriaId == hrPromotionCriterias.TRAINING) {
//            return "TRAINING";
//        } else if (criteriaId == hrPromotionCriterias.INTERVIEWORTEST) {
//            return "INTERVIEW OR TEST";
//        } else {
//            return "Bonus for Women";
//        }
//    }

    @Override
    public void saveOrUpdate(HrPromotionCriterias get) {
        hrPromotionCriteriasFacade.saveOrUpdate(get);
    }
//
//    @Override
//    public List<HrPromotionCriterias> findAllCriterias() {
//        List<HrPromotionCriterias> allCriterias = new ArrayList<>();
//        List<HrPromotionCriterias> readAllCriterias = hrPromotionCriteriasFacade.findAllCriterias();
//        for (int i = 0; i < readAllCriterias.size(); i++) {
//            hrPromotionCriterias = readAllCriterias.get(i);
//            hrPromotionCriterias.setCriteria(getCriteriaLabel(readAllCriterias.get(i).getId()));
//            allCriterias.add(hrPromotionCriterias);
//        }
//        return allCriterias;
//    }

    @Override
    public void edit(HrPromotionCriterias get) {
        hrPromotionCriteriasFacade.edit(get);
    }

    @Override
    public List<HrPromotionCriterias> readAllCriterias() {
        return hrPromotionCriteriasFacade.findAll();
    }

   
}
