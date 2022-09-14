/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.lookup;

import et.gov.eep.hrms.entity.lookup.HrLuNationalities;
import et.gov.eep.hrms.mapper.lookup.HrLuNationalitiesFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author user
 */
@Stateless
public class HrLuNationalityBean implements HrLuNationalityBeanLocal {

   @EJB
   HrLuNationalitiesFacade hrLuNationalitiesFacade;

    /**
     *
     * @return
     */
    @Override
    public List<HrLuNationalities> findAll(){        
        return  hrLuNationalitiesFacade.findAll();
    }
    
}
