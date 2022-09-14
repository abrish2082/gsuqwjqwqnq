/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic.Bond;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;
import et.gov.eep.fcms.entity.Bond.FmsLuBondType;

/**
 *
 * @author mora
 */
@Local
public interface LuBondTypeBeanLocal {

    public void create(FmsLuBondType BondType);

    public void edit(FmsLuBondType BondType);

    public FmsLuBondType getLuBond(FmsLuBondType BondType);

    public ArrayList<FmsLuBondType> searchLuBond(FmsLuBondType BondType);

    public List<FmsLuBondType> searchLuBondType();

}
