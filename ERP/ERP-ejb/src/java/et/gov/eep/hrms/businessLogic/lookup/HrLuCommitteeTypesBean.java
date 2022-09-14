/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.lookup;

import et.gov.eep.hrms.entity.lookup.HrLuCommitteeTypes;
import et.gov.eep.hrms.mapper.lookup.HrLuCommitteeTypesFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author user
 */
@Stateless
public class HrLuCommitteeTypesBean implements HrLuCommitteeTypesBeanLocal {
   @EJB
   HrLuCommitteeTypesFacade committeeTypesFacade;
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    /**
     *
     * @return
     */
    @Override
    public List<HrLuCommitteeTypes> findAll() {
       return committeeTypesFacade.findAll(); // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
