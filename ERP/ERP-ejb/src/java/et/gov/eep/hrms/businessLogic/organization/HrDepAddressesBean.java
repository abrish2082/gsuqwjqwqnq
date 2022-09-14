/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.organization;

import et.gov.eep.hrms.entity.organization.HrDepAddresses;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.hrms.mapper.organization.HrDepAddressesFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author munir
 */
@Stateless
public class HrDepAddressesBean implements HrDepAddressesBeanLocal {

    @EJB
    HrDepAddressesFacade hrDepAddressesFacade;

    /**
     *
     * @param hrDepAddresses
     */
    @Override
    public void create(HrDepAddresses hrDepAddresses) {
        hrDepAddressesFacade.create(hrDepAddresses);
    }

    /**
     *
     * @param hrDepAddresses
     */
    @Override
    public void edit(HrDepAddresses hrDepAddresses) {
        hrDepAddressesFacade.edit(hrDepAddresses);
    }

    /**
     *
     * @param hrDepAddresses
     */
    @Override
    public void remove(HrDepAddresses hrDepAddresses) {
        hrDepAddressesFacade.remove(hrDepAddresses);
    }

    /**
     *
     * @param id
     * @return
     */
    @Override
    public HrDepAddresses find(Object id) {
        return hrDepAddressesFacade.find(id);
    }

    /**
     *
     * @return
     */
    @Override
    public List<HrDepAddresses> findAll() {
        return hrDepAddressesFacade.findAll();
    }

    /**
     *
     * @param range
     * @return
     */
    @Override
    public List<HrDepAddresses> findRange(int[] range) {
        return hrDepAddressesFacade.findRange(range);
    }

    /**
     *
     * @return
     */
    @Override
    public int count() {
        return hrDepAddressesFacade.count();
    }

    /**
     *
     * @param hrDepartments
     * @return
     */
    @Override
    public HrDepAddresses findDepartmentAddress(HrDepartments hrDepartments) {
        return hrDepAddressesFacade.findDepartmentAddress(hrDepartments);
    }

    @Override
    public List<HrDepAddresses> findByDeptId(int deptId) {
        return hrDepAddressesFacade.findByDeptId(deptId);
    }
}
