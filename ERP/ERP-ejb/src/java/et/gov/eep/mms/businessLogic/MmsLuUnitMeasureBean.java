/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.businessLogic;

import et.gov.eep.mms.entity.MmsLuUnitMeasure;
import et.gov.eep.mms.mapper.MmsLuUnitMeasureFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Sadik
 */
@Stateless
public class MmsLuUnitMeasureBean implements MmsLuUnitMeasureBeanLocal {
    
      @EJB
      MmsLuUnitMeasureFacade measureFacade;
    
    @Override
    public List<MmsLuUnitMeasure> findAll() {
       return measureFacade.findAll();
    }

   
}
