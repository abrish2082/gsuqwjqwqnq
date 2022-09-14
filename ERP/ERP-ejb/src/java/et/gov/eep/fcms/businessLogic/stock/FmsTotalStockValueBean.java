/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic.stock;


import javax.ejb.EJB;
import javax.ejb.Stateless;
import et.gov.eep.fcms.entity.stock.FmsTotalStockValue;
import et.gov.eep.fcms.mapper.stock.FmsTotalStockValueFacade;
/**
 *
 * @author Terminal2
 */
@Stateless
public class FmsTotalStockValueBean implements FmsTotalStockValueBeanLocal {

    @EJB
    FmsTotalStockValueFacade totalStockValueFacade;

    @Override
    public FmsTotalStockValue getWeightedAverageValueByMatCode(String matcode) {
        return totalStockValueFacade.getWeightedAverageValueByMatCode(matcode);
    }

}
