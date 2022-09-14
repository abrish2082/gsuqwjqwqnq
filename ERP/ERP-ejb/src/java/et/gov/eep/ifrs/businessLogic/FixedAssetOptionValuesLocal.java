/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.ifrs.businessLogic;

import et.gov.eep.ifrs.entity.IfrsOptionValues;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface FixedAssetOptionValuesLocal {

    public IfrsOptionValues getListOfOptionValue(Integer optionValueId);

    public List<IfrsOptionValues> getListOfOptionValueList(Integer optionValueId);

}
