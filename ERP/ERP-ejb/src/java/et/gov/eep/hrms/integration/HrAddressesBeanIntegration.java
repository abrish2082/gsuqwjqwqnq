/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.integration;

import et.gov.eep.hrms.entity.address.HrAddresses;
import et.gov.eep.hrms.mapper.address.HrAddressesFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author munir
 */
@Stateless
public class HrAddressesBeanIntegration implements HrAddressesBeanIntegrationLocal {

    @EJB
    HrAddressesFacade hrAddressesFacade;

    /**
     *
     * @return
     */
    @Override
    public List<HrAddresses> findAllAddressUnit() {
        return hrAddressesFacade.findAllAddressUnit();
    }

    /**
     *
     * @param hrAddresses
     * @return
     */
    @Override
    public HrAddresses findByAddressId(HrAddresses hrAddresses) {
        return hrAddressesFacade.getAddressInfoByAddressId(hrAddresses);
    }

    /**
     *
     * @param hrAddresses
     * @return
     */
    @Override
    public HrAddresses findAllAddressUnitAsOne(HrAddresses hrAddresses) {
        return hrAddressesFacade.findAllAddressUnitAsOne(hrAddresses);
    }
    
    public List<HrAddresses> findAll() {
        return hrAddressesFacade.findAll();
    }
}
