/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic.Bond;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;
import et.gov.eep.fcms.entity.Bond.FmsBondLibor;

/**
 *
 * @author mora
 */
@Local
public interface FmsBondLiborBeanLocal {

    public void Create(FmsBondLibor BondLibor);

    public void edit(FmsBondLibor BondLibor);

    public FmsBondLibor searchday(FmsBondLibor BondLibor);

    public FmsBondLibor searchLiborNo(FmsBondLibor BondLibor);

    public FmsBondLibor searchdays(Date day, String currency);

    public ArrayList<FmsBondLibor> searchBondType(Date day);

    public ArrayList<FmsBondLibor> searchByDateAndCurrncy(FmsBondLibor BondLibor);

    public ArrayList<FmsBondLibor> searchCrance(FmsBondLibor BondLibor);

    public ArrayList<FmsBondLibor> searchLibor(FmsBondLibor BondLibor);

    public ArrayList<FmsBondLibor> searchLiborRate(FmsBondLibor BondLibor);

    public List<FmsBondLibor> searchAll();

}
