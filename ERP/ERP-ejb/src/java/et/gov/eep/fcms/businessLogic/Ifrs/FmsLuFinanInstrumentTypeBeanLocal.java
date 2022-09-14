/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic.Ifrs;

import java.util.ArrayList;
import javax.ejb.Local;
import et.gov.eep.fcms.entity.Ifrs.FmsLuFinanInstrumentType;

/**
 *
 * @author mz
 */
@Local
public interface FmsLuFinanInstrumentTypeBeanLocal {

    public ArrayList<FmsLuFinanInstrumentType> searchFinInstType(FmsLuFinanInstrumentType finInstType);

}
