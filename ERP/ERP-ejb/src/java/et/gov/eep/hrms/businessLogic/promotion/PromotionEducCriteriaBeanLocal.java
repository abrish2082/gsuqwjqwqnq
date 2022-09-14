/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.promotion;

import et.gov.eep.hrms.entity.lookup.HrLuEducLevels;
import et.gov.eep.hrms.entity.promotion.HrPromotionEducCriterias;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface PromotionEducCriteriaBeanLocal {

    public List<HrLuEducLevels> findAll();

    public void saveOrUpdate(HrPromotionEducCriterias get);

    public List<HrPromotionEducCriterias> findAllEducation();;

    

}
