/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic.Bond;

import java.util.ArrayList;
import javax.ejb.Local;
import et.gov.eep.fcms.entity.Bond.FmsBondType;

/**
 *
 * @author mora
 */
@Local
public interface FmsBondTypeBeanLocal {

    public void create(FmsBondType BondType);

    public void edit(FmsBondType BondType);

    public FmsBondType getBondTypeInfo(FmsBondType BondType);

    public ArrayList<FmsBondType> searchBondType(FmsBondType BondType);

}
