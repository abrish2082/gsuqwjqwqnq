/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.ifrs.businessLogic;

import et.gov.eep.ifrs.entity.IfrsOptionValues;
import et.gov.eep.ifrs.mapper.IfrsOptionValuesFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author user
 */
@Stateless
public class FixedAssetOptionValues implements FixedAssetOptionValuesLocal {
@EJB
 private IfrsOptionValuesFacade optionValuesFacade;

        @Override
    public IfrsOptionValues getListOfOptionValue(Integer optionValueId){
        return optionValuesFacade.getListOfOptionValue(optionValueId);
    }
    
    @Override
    public List<IfrsOptionValues> getListOfOptionValueList(Integer fAAId){
        return optionValuesFacade.getListOfOptionValueList(fAAId);
    }
    

}
