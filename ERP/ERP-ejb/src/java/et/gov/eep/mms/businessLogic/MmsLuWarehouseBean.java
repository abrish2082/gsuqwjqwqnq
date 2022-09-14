/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.businessLogic;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import et.gov.eep.mms.entity.MmsLuWareHouse;
import et.gov.eep.mms.mapper.MmsLuWareHouseFacade;

/**
 *
 * @author Minab
 */
@Stateless
public class MmsLuWarehouseBean implements MmsLuWarehouseBeanLocal {

    @EJB
    MmsLuWareHouseFacade luWareHouseFacade;

    @Override
    public List<MmsLuWareHouse> findAll() {
        return luWareHouseFacade.findAll();
    }
}
