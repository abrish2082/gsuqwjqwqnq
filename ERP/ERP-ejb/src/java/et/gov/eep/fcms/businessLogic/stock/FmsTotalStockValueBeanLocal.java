/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic.stock;

import javax.ejb.Local;
import et.gov.eep.fcms.entity.stock.FmsTotalStockValue;

/**
 *
 * @author Terminal2
 */
@Local
public interface FmsTotalStockValueBeanLocal {

    public FmsTotalStockValue getWeightedAverageValueByMatCode(String matcode);
}
