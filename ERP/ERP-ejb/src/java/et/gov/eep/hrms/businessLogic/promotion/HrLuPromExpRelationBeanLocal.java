/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.promotion;

import et.gov.eep.hrms.entity.promotion.HrLuPromExpRelation;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface HrLuPromExpRelationBeanLocal {

    public List<HrLuPromExpRelation> findAll();
    
}
