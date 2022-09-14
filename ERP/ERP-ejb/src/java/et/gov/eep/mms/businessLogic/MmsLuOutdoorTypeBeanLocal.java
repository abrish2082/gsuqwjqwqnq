/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.businessLogic;

import et.gov.eep.mms.entity.MmsLuOutdoorType;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Sadik
 */
@Local
public interface MmsLuOutdoorTypeBeanLocal {
    public List<MmsLuOutdoorType> findAll();
}
