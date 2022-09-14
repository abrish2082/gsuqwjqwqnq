/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic.Ifrs;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import et.gov.eep.fcms.entity.Ifrs.FinancialInstrumentDetail;
import et.gov.eep.fcms.entity.Ifrs.FinancialInstrumentRegister;
import et.gov.eep.fcms.mapper.Ifrs.FinancialInstrumentDetailFacade;

/**
 *
 * @author mz
 */
@Stateless
public class FinancialInstrumentDetailBean implements FinancialInstrumentDetailBeanLocal {

    @EJB
    FinancialInstrumentDetailFacade financialInstrumentDetailFacade;

    @Override
    public FinancialInstrumentDetail getFinancialInstRegDetInfo(FinancialInstrumentRegister finInstrumentRegister) {
        return financialInstrumentDetailFacade.getFinancialInstRegDetInfo(finInstrumentRegister);
    }

}
