/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic.Ifrs;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import et.gov.eep.fcms.entity.Ifrs.FmsLuFinaInstrumentMeasure;
import et.gov.eep.fcms.mapper.Ifrs.FmsLuFinaInstrumentMeasureFacade;

/**
 *
 * @author mz
 */
@Stateless
public class FmsLuFinaInstrumentMeasureBean implements FmsLuFinaInstrumentMeasureBeanLocal {

    @EJB
    FmsLuFinaInstrumentMeasureFacade finInstMeasurmentFacade;

    @Override
    public List<FmsLuFinaInstrumentMeasure> searchall() {
        return finInstMeasurmentFacade.findAll();
    }

    @Override
    public List<FmsLuFinaInstrumentMeasure> defualtAndPL() {
        return finInstMeasurmentFacade.defualtAndPL();
    }

    @Override
    public ArrayList<FmsLuFinaInstrumentMeasure> searchfinIntMeasureList(FmsLuFinaInstrumentMeasure finInstMeasure) {
        return finInstMeasurmentFacade.searchfinIntMeasureList(finInstMeasure);
    }
}
