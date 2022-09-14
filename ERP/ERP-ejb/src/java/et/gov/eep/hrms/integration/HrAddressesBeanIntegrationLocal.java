/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.integration;

import et.gov.eep.hrms.entity.address.HrAddresses;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author munir
 */
@Local
public interface HrAddressesBeanIntegrationLocal {

    /**
     *
     * @return
     */
    public List<HrAddresses> findAllAddressUnit();

    /**
     *
     * @param hrAddresses
     * @return
     */
    public HrAddresses findByAddressId(HrAddresses hrAddresses);

    /**
     *
     * @param hrAddresses
     * @return
     */
    public HrAddresses findAllAddressUnitAsOne(HrAddresses hrAddresses);

    public List<HrAddresses> findAll();
}
