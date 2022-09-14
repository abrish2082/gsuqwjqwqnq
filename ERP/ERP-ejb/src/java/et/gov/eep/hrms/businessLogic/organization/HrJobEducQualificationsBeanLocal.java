/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.organization;

import et.gov.eep.hrms.entity.organization.HrJobEducQualifications;
import et.gov.eep.hrms.entity.organization.HrJobTypes;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author munir
 */
@Local
public interface HrJobEducQualificationsBeanLocal {
    
     /**
     *
     * @param hrJobEducQualifications
     */
    void create(HrJobEducQualifications hrJobEducQualifications);

    /**
     *
     * @param hrJobEducQualifications
     */
    void edit(HrJobEducQualifications hrJobEducQualifications);

    /**
     *
     * @param hrJobEducQualifications
     */
    void remove(HrJobEducQualifications hrJobEducQualifications);

    /**
     *
     * @param id
     * @return
     */
    HrJobEducQualifications find(Object id);

    /**
     *
     * @return
     */
    List<HrJobEducQualifications> findAll();

    /**
     *
     * @param range
     * @return
     */
    List<HrJobEducQualifications> findRange(int[] range);

    /**
     *
     * @return
     */
    int count();

    public List<HrJobEducQualifications> getByJobID(HrJobTypes jobTypes);
    
}
