/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic.Bond;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import et.gov.eep.fcms.entity.Bond.FmsLuBondType;
import et.gov.eep.fcms.mapper.FmsLuBondTypeFacade;

/**
 *
 * @author mora
 */
@Stateless
public class LuBondTypeBean implements LuBondTypeBeanLocal {

    @EJB
    FmsLuBondTypeFacade luBondTypeFacade;

    /**
     *
     * @param BondType
     */
    @Override
    public void create(FmsLuBondType BondType) {
        luBondTypeFacade.create(BondType);
    }

    @Override
    public void edit(FmsLuBondType BondType) {
        luBondTypeFacade.edit(BondType);
    }

    @Override
    public ArrayList<FmsLuBondType> searchLuBond(FmsLuBondType BondType) {
        return luBondTypeFacade.searchFmsLuBondType(BondType);
    }

    @Override
    public FmsLuBondType getLuBond(FmsLuBondType bondType) {
        return luBondTypeFacade.getFmsLuLubondTypeInfo(bondType);
    }

    @Override
    public List<FmsLuBondType> searchLuBondType() {
        return luBondTypeFacade.searchLuBondType();
    }

}
