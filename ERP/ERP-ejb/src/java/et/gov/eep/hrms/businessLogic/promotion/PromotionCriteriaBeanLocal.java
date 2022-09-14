/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.promotion;

import et.gov.eep.hrms.entity.promotion.HrPromotionCriterias;
import java.util.List;
import javax.ejb.Local;
import javax.faces.model.SelectItem;

/**
 *
 * @author user
 */
@Local
public interface PromotionCriteriaBeanLocal {
    
//    public List<SelectItem> criteriaTypes();
//
//    public String getCriteriaLabel(int criteriaId);

    public void saveOrUpdate(HrPromotionCriterias get);
    
//  public List<HrPromotionCriterias> findAllCriterias();

    public void edit(HrPromotionCriterias get);

    public List<HrPromotionCriterias> readAllCriterias();

   
}
