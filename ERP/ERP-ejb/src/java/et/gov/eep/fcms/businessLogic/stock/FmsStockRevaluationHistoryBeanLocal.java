/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic.stock;


import javax.ejb.Local;
import et.gov.eep.fcms.entity.stock.FmsStockRevaluationHistory;
/**
 *
 * @author memube
 */
@Local
public interface FmsStockRevaluationHistoryBeanLocal {

    public void create(FmsStockRevaluationHistory fmsStockRevaluationHistory);

    public void edit(FmsStockRevaluationHistory fmsStockRevaluationHistory);
}
