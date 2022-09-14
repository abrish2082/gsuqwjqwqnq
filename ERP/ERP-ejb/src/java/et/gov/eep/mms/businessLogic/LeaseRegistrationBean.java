/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.businessLogic;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import et.gov.eep.mms.entity.IfrsLease;
import et.gov.eep.mms.mapper.IfrsLeaseFacade;

/**
 *
 * @author Minab
 */
@Stateless
public class LeaseRegistrationBean implements LeaseRegistrationBeanLocal {

    @EJB
    IfrsLeaseFacade IfrsLeaseFacade;

    @Override
    public void saveOrUpdate(IfrsLease lease) {
        IfrsLeaseFacade.saveOrUpdate(lease);
    }

    @Override
    public List<IfrsLease> findbyAll() {
        return IfrsLeaseFacade.findAll();
    }

    @Override
    public List<IfrsLease> searchLeaseInformationByAssetName(IfrsLease lease) {
        return IfrsLeaseFacade.searchLeaseInformationByAssetName(lease);
    }

    @Override
    public List<IfrsLease> searchLeaseInformationByLeaseCode(IfrsLease lease) {
        return IfrsLeaseFacade.searchLeaseInformationByLeaseCode(lease);
    }

}
