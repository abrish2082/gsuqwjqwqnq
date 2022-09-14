/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.address;

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
public class HrAddressesBean implements HrAddressesBeanLocal {

    @EJB
    HrAddressesFacade hrAddressesFacade;

    /**
     *
     * @return
     */
    @Override
    public List<HrAddresses> findAll() {
        return hrAddressesFacade.findAll();
    }

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
     * @return
     */
    @Override
    public List<HrAddresses> findAllAddressUnitAndCountries() {
        return hrAddressesFacade.findAllAddressUnitAndCountries();
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
     */
    @Override
    public void save(HrAddresses hrAddresses) {
        hrAddressesFacade.create(hrAddresses);
    }

    /**
     *
     * @param hrAddresses
     */
    public void edit(HrAddresses hrAddresses) {
        hrAddressesFacade.edit(hrAddresses);
    }

    @Override
    public void saveOrUpdate(HrAddresses hrAddresses) {
        hrAddressesFacade.saveOrUpdate(hrAddresses);
    }

    /**
     *
     * @param hrAddresses
     */
    @Override
    public void remove(HrAddresses hrAddresses) {
        hrAddressesFacade.remove(hrAddresses);
    }
}
