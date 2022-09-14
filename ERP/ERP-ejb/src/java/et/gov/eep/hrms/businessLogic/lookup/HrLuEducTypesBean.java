/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.lookup;

import et.gov.eep.hrms.entity.lookup.HrLuEducTypes;
import et.gov.eep.hrms.mapper.lookup.HrLuEducTypesFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author munir
 */
@Stateless
public class HrLuEducTypesBean implements HrLuEducTypesBeanLocal {

    @EJB
    HrLuEducTypesFacade hrLuEducTypesFacade;

    @Override
    public void create(HrLuEducTypes hrLuEducTypes) {
        hrLuEducTypesFacade.create(hrLuEducTypes);
    }

    @Override
    public void edit(HrLuEducTypes hrLuEducTypes) {
        hrLuEducTypesFacade.edit(hrLuEducTypes);
    }

    @Override
    public void remove(HrLuEducTypes hrLuEducTypes) {
        hrLuEducTypesFacade.remove(hrLuEducTypes);
    }

    @Override
    public HrLuEducTypes find(Object id) {
        return hrLuEducTypesFacade.find(id);
    }

    @Override
    public List<HrLuEducTypes> findAll() {
        return hrLuEducTypesFacade.findAll();
    }

    @Override
    public List<HrLuEducTypes> findRange(int[] range) {
        return hrLuEducTypesFacade.findRange(range);
    }

    @Override
    public int count() {
        return hrLuEducTypesFacade.count();
    }

    @Override
    public HrLuEducTypes searchEducTypeById(int id) {
        return hrLuEducTypesFacade.searchEducTypeById(id);
    }
}
