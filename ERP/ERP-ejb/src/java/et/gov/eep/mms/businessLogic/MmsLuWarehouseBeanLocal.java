/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.businessLogic;

import java.util.List;
import javax.ejb.Local;
import et.gov.eep.mms.entity.MmsLuWareHouse;

/**
 *
 * @author Minab
 */
@Local
public interface MmsLuWarehouseBeanLocal {

    public List<MmsLuWareHouse> findAll();

}
