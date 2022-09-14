/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic.admin;

import java.util.List;
import javax.ejb.Local;
import et.gov.eep.fcms.entity.admin.FmsCostCenter;
import et.gov.eep.fcms.entity.admin.FmsCostcSystemJunction;
import et.gov.eep.fcms.entity.admin.FmsLuSystem;
import et.gov.eep.hrms.entity.organization.HrDepartments;

/**
 *
 * @author admin
 */
@Local
public interface FmsCostCSystemJunctionBeanLocal {

    public List<FmsCostcSystemJunction> findUnmappedCostCenter(FmsLuSystem fmsLuSystem);

    public void create(FmsCostcSystemJunction costcSystemJunction);

    public void update(FmsCostcSystemJunction costcSystemJunction);

    public void saveOrUpdate(FmsCostcSystemJunction costcSystemJunction);

    public FmsCostcSystemJunction findById(FmsCostcSystemJunction costcSystemJunction);

    public FmsCostcSystemJunction findByCCandSS(FmsLuSystem fmsLuSystem, FmsCostCenter fmsCostCenter);

    public FmsCostcSystemJunction findBySystemCodeAndCostCenterCode(FmsLuSystem fmsLuSystem, FmsCostCenter fmsCostCenter);

    public FmsCostcSystemJunction findbyDepId(HrDepartments hrDepartments);

    public FmsCostcSystemJunction findbyDeptId(Integer hrDepartments);

    public List<FmsCostcSystemJunction> fetchMappedSystem(FmsLuSystem fmsLuSystem);
}
