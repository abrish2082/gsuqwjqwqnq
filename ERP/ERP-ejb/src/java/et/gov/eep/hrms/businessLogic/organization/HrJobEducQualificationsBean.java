/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.organization;

import et.gov.eep.hrms.entity.organization.HrJobEducQualifications;
import et.gov.eep.hrms.entity.organization.HrJobTypes;
import et.gov.eep.hrms.mapper.organization.HrJobEducQualificationsFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author munir
 */
@Stateless
public class HrJobEducQualificationsBean implements HrJobEducQualificationsBeanLocal {
    
    @EJB
    HrJobEducQualificationsFacade hrJobEducQualificationsFacade;

    /**
     *
     * @param hrJobEducQualifications
     */
    @Override
    public void create(HrJobEducQualifications hrJobEducQualifications) {
        hrJobEducQualificationsFacade.create(hrJobEducQualifications);
    }

    /**
     *
     * @param hrJobEducQualifications
     */
    @Override
    public void edit(HrJobEducQualifications hrJobEducQualifications) {
        hrJobEducQualificationsFacade.edit(hrJobEducQualifications);
    }

    /**
     *
     * @param hrJobEducQualifications
     */
    @Override
    public void remove(HrJobEducQualifications hrJobEducQualifications) {
        hrJobEducQualificationsFacade.remove(hrJobEducQualifications);
    }

    /**
     *
     * @param id
     * @return
     */
    @Override
    public HrJobEducQualifications find(Object id) {
        return hrJobEducQualificationsFacade.find(id);
    }

    /**
     *
     * @return
     */
    @Override
    public List<HrJobEducQualifications> findAll() {
        return hrJobEducQualificationsFacade.findAll();
    }

    /**
     *
     * @param range
     * @return
     */
    @Override
    public List<HrJobEducQualifications> findRange(int[] range) {
        return hrJobEducQualificationsFacade.findRange(range);
    }

    /**
     *
     * @return
     */
    @Override
    public int count() {
        return hrJobEducQualificationsFacade.count();
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    @Override
    public List<HrJobEducQualifications> getByJobID(HrJobTypes jobTypes) {
    return hrJobEducQualificationsFacade.getByJobID(jobTypes);
    }
}
