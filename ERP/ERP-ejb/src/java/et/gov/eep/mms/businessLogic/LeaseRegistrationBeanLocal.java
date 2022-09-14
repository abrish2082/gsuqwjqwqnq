/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.businessLogic;

import java.util.List;
import javax.ejb.Local;
import et.gov.eep.mms.entity.IfrsLease;

/**
 *
 * @author insa
 */
@Local
public interface LeaseRegistrationBeanLocal {

    public void saveOrUpdate(IfrsLease lease);

    public List<IfrsLease> findbyAll();

    public List<IfrsLease> searchLeaseInformationByAssetName(IfrsLease lease);

    public List<IfrsLease> searchLeaseInformationByLeaseCode(IfrsLease lease);

    /**
     *
     * @param lease
     */
}
