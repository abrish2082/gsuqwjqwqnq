/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.address;

import et.gov.eep.hrms.entity.address.HrAddresses;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author munir
 */
@Local
public interface HrAddressesBeanLocal {

    /**
     *
     * @return
     */
    public List<HrAddresses> findAll();

    /**
     *
     * @return
     */
    public List<HrAddresses> findAllAddressUnit();

    /**
     *
     * @return
     */
    public List<HrAddresses> findAllAddressUnitAndCountries();

    /**
     *
     * @param hrAddresses
     * @return
     */
    public HrAddresses findAllAddressUnitAsOne(HrAddresses hrAddresses);

    /**
     *
     * @param hrAddresses
     * @return
     */
    public HrAddresses findByAddressId(HrAddresses hrAddresses);

    /**
     *
     * @param hrAddresses
     */
    public void save(HrAddresses hrAddresses);

    /**
     *
     * @param hrAddresses
     */
    public void edit(HrAddresses hrAddresses);

    public void saveOrUpdate(HrAddresses hrAddresses);

    /**
     *
     * @param hrAddresses
     */
    public void remove(HrAddresses hrAddresses);

}
