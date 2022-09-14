/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.organization;

import et.gov.eep.hrms.entity.organization.HrDepAddresses;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author munir
 */
@Local
public interface HrDepAddressesBeanLocal {

    /**
     *
     * @param hrDepAddresses
     */
    void create(HrDepAddresses hrDepAddresses);

    /**
     *
     * @param hrDepAddresses
     */
    void edit(HrDepAddresses hrDepAddresses);

    /**
     *
     * @param hrDepAddresses
     */
    void remove(HrDepAddresses hrDepAddresses);

    /**
     *
     * @param id
     * @return
     */
    HrDepAddresses find(Object id);

    /**
     *
     * @return
     */
    List<HrDepAddresses> findAll();

    /**
     *
     * @param range
     * @return
     */
    List<HrDepAddresses> findRange(int[] range);

    /**
     *
     * @return
     */
    int count();

    /**
     *
     * @param hrDepartments
     * @return
     */
    public HrDepAddresses findDepartmentAddress(HrDepartments hrDepartments);

    public List<HrDepAddresses> findByDeptId(int deptId);
}
