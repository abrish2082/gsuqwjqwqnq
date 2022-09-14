/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic.admin;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import et.gov.eep.fcms.entity.admin.FmsCostCenter;
import et.gov.eep.fcms.entity.admin.FmsCostcSystemJunction;
import et.gov.eep.fcms.entity.admin.FmsLuSystem;
import et.gov.eep.fcms.mapper.admin.FmsCostcSystemJunctionFacade;
import et.gov.eep.hrms.entity.organization.HrDepartments;

/**
 *
 * @author admin
 */
@Stateless
public class FmsCostCSystemJunctionBean implements FmsCostCSystemJunctionBeanLocal {
    
//<editor-fold defaultstate="collapsed" desc="@EJB">
    @EJB
    FmsCostcSystemJunctionFacade fmsCostcSystemJunctionFacade;
//</editor-fold>
    
    @Override
    public List<FmsCostcSystemJunction> findUnmappedCostCenter(FmsLuSystem fmsLuSystem) {
        return fmsCostcSystemJunctionFacade.findUnmappedCostCenterName(fmsLuSystem);
    }

    @Override
    public void create(FmsCostcSystemJunction costcSystemJunction) {
        fmsCostcSystemJunctionFacade.create(costcSystemJunction);
    }

    @Override
    public void update(FmsCostcSystemJunction costcSystemJunction) {
        fmsCostcSystemJunctionFacade.edit(costcSystemJunction);
    }

    @Override
    public void saveOrUpdate(FmsCostcSystemJunction costcSystemJunction) {
        fmsCostcSystemJunctionFacade.saveOrUpdate(costcSystemJunction);
    }

    @Override
    public FmsCostcSystemJunction findById(FmsCostcSystemJunction costcSystemJunction) {
        return fmsCostcSystemJunctionFacade.findById(costcSystemJunction);
    }

    @Override
    public List<FmsCostcSystemJunction> fetchMappedSystem(FmsLuSystem fmsLuSystem) {
        return fmsCostcSystemJunctionFacade.fetchMappedSystem(fmsLuSystem);
    }

    @Override
    public FmsCostcSystemJunction findbyDepId(HrDepartments departmentId) {
        return fmsCostcSystemJunctionFacade.findbyDepId(departmentId);
    }

    @Override
    public FmsCostcSystemJunction findbyDeptId(Integer departmentId) {
        return fmsCostcSystemJunctionFacade.findbyDeptId(departmentId);
    }

    @Override
    public FmsCostcSystemJunction findByCCandSS(FmsLuSystem fmsLuSystem, FmsCostCenter fmsCostCenter) {
        return fmsCostcSystemJunctionFacade.findByCCandSSId(fmsLuSystem, fmsCostCenter);
    }

    @Override
    public FmsCostcSystemJunction findBySystemCodeAndCostCenterCode(FmsLuSystem fmsLuSystem, FmsCostCenter fmsCostCenter) {
        return fmsCostcSystemJunctionFacade.findBySystemCodeAndCostCenterCode(fmsLuSystem, fmsCostCenter);
    }
}
