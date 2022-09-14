/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic.Ifrs;

import javax.ejb.Local;
import et.gov.eep.fcms.entity.Ifrs.FinancialInstrumentDetail;
import et.gov.eep.fcms.entity.Ifrs.FinancialInstrumentRegister;

/**
 *
 * @author mz
 */
@Local
public interface FinancialInstrumentDetailBeanLocal {

    public FinancialInstrumentDetail getFinancialInstRegDetInfo(FinancialInstrumentRegister finInstrumentRegister);

}
