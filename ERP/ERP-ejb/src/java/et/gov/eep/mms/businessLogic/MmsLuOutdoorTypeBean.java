/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.businessLogic;

import et.gov.eep.mms.entity.MmsLuOutdoorType;
import et.gov.eep.mms.mapper.MmsLuOutdoorTypeFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Sadik
 */
@Stateless
public class MmsLuOutdoorTypeBean implements MmsLuOutdoorTypeBeanLocal {
    @EJB
    MmsLuOutdoorTypeFacade outdoorTypeFacade;
    
    @Override
    public List<MmsLuOutdoorType> findAll() {
       return outdoorTypeFacade.findAll();
    }

    
}
