/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic.stock;


import javax.ejb.EJB;
import javax.ejb.Stateless;
import et.gov.eep.fcms.entity.stock.FmsStockRevaluationHistory;
import et.gov.eep.fcms.mapper.stock.FmsStockRevaluationHistoryFacade;
/**
 *
 * @author memube
 */
@Stateless
public class FmsStockRevaluationHistoryBean implements FmsStockRevaluationHistoryBeanLocal {

    @EJB
    private FmsStockRevaluationHistoryFacade fmsStockRevaluationFacade;

    @Override
    public void create(FmsStockRevaluationHistory fmsStockRevaluationHistory) {
       fmsStockRevaluationFacade.create(fmsStockRevaluationHistory);
    }

    @Override
    public void edit(FmsStockRevaluationHistory fmsStockRevaluationHistory) {
        fmsStockRevaluationFacade.edit(fmsStockRevaluationHistory);
    }

}
