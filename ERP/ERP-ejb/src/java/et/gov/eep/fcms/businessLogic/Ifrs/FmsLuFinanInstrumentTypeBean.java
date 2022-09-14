/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic.Ifrs;

import java.util.ArrayList;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import et.gov.eep.fcms.entity.Ifrs.FmsLuFinanInstrumentType;
import et.gov.eep.fcms.mapper.Ifrs.FmsLuFinanInstrumentTypeFacade;

/**
 *
 * @author mz
 */
@Stateless
public class FmsLuFinanInstrumentTypeBean implements FmsLuFinanInstrumentTypeBeanLocal {

    @EJB
    FmsLuFinanInstrumentTypeFacade finInstrumentTypeFacade;

    @Override
    public ArrayList<FmsLuFinanInstrumentType> searchFinInstType(FmsLuFinanInstrumentType finInstType) {
        return finInstrumentTypeFacade.searchFinInstType(finInstType);

    }

}
