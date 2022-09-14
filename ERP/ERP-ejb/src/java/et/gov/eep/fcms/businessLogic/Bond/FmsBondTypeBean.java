/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic.Bond;


import java.util.ArrayList;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import et.gov.eep.fcms.entity.Bond.FmsBondType;
import et.gov.eep.fcms.mapper.Bond.FmsBondTypeFacade;
/**
 *
 * @author mora
 */
@Stateless
public class FmsBondTypeBean implements FmsBondTypeBeanLocal {

    @EJB
    FmsBondTypeFacade fmsBondTypeFacade;

    @Override
    public void create(FmsBondType BondType) {
        fmsBondTypeFacade.create(BondType);
    }

    @Override
    public void edit(FmsBondType BondType) {
        fmsBondTypeFacade.edit(BondType);
    }

    @Override
    public ArrayList<FmsBondType> searchBondType(FmsBondType BondType) {
        return fmsBondTypeFacade.searchFmsBondTypeId(BondType);
    }

    @Override
    public FmsBondType getBondTypeInfo(FmsBondType BondType) {
        return fmsBondTypeFacade.getFmsBondTypeInfo(BondType);
    }

}
