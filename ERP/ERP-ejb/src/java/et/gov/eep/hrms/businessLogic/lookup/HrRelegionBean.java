/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.lookup;

import et.gov.eep.hrms.entity.lookup.HrLuReligions;
import et.gov.eep.hrms.mapper.lookup.HrLuReligionsFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author user
 */
@Stateless
public class HrRelegionBean implements HrRelegionBeanLocal {

    @EJB
    HrLuReligionsFacade hrLuReligionsFacade;
    
    /**
     *
     * @return
     */
    @Override
    public List<HrLuReligions> findAll(){        
        return hrLuReligionsFacade.findAll();
    }
}
