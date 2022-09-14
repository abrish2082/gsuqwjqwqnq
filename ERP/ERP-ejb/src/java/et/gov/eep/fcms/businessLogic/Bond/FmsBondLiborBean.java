/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic.Bond;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import et.gov.eep.fcms.entity.Bond.FmsBondLibor;
import et.gov.eep.fcms.mapper.Bond.FmsBondLiborFacade;

/**
 *
 * @author mora
 */
@Stateless
public class FmsBondLiborBean implements FmsBondLiborBeanLocal {

    @EJB
    FmsBondLiborFacade BondLiborFacade;

    @Override
    public void Create(FmsBondLibor BondLibor) {
        BondLiborFacade.create(BondLibor);
    }

    @Override
    public void edit(FmsBondLibor BondLibor) {
        BondLiborFacade.edit(BondLibor);
    }

    @Override
    public ArrayList<FmsBondLibor> searchBondType(Date day) {
        return BondLiborFacade.searchStartdate(day);
    }

    @Override
    public FmsBondLibor searchday(FmsBondLibor BondLibor) {
        return BondLiborFacade.searchday(BondLibor); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<FmsBondLibor> searchCrance(FmsBondLibor BondLibor) {
        return BondLiborFacade.searchCrance(BondLibor);
    }

    @Override
    public ArrayList<FmsBondLibor> searchLibor(FmsBondLibor BondLibor) {
        return BondLiborFacade.searchLibor(BondLibor);

    }

    @Override
    public ArrayList<FmsBondLibor> searchLiborRate(FmsBondLibor BondLibor) {
        return BondLiborFacade.searchLiborRate(BondLibor);
    }

    @Override
    public FmsBondLibor searchLiborNo(FmsBondLibor BondLibor) {
        return BondLiborFacade.searchLiborNo(BondLibor);
    }

    @Override
    public List<FmsBondLibor> searchAll() {
        return BondLiborFacade.findAll();
    }

    @Override
    public ArrayList<FmsBondLibor> searchByDateAndCurrncy(FmsBondLibor BondLibor) {
        return BondLiborFacade.searchByDateAndCurrncy(BondLibor);
    }

    @Override
    public FmsBondLibor searchdays(Date day, String currency) {
        return BondLiborFacade.searchdays(day, currency);
    }

}
