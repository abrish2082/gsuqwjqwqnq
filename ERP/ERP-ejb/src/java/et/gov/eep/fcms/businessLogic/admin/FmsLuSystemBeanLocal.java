/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic.admin;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;
import et.gov.eep.fcms.entity.admin.FmsCostCenter;
import et.gov.eep.fcms.entity.admin.FmsLuSystem;

/**
 *
 * @author muller
 */
@Local
public interface FmsLuSystemBeanLocal {

    

    public void create(FmsLuSystem fmsLuSystem);

    public void edit(FmsLuSystem fmsLuSystem);

    public void deleteSys(FmsLuSystem fmsLuSystem);

    public List<FmsCostCenter> getCostC(FmsCostCenter fmsCostCenter);

    public List<FmsLuSystem> findAll();

    public List<FmsLuSystem> findProjSystem();

    public List<FmsLuSystem> findOprSystem();

    public List<FmsLuSystem> activeSystem();

    public List<FmsLuSystem> activeSystemForCapi();

    public ArrayList<FmsLuSystem> findBySytemCode(FmsLuSystem luSystemEntity);

    public ArrayList<FmsLuSystem> findBySytemCodeLike(FmsLuSystem luSystemEntity);

    public FmsLuSystem getSysDetail(FmsLuSystem fmsLuSystem);

    public FmsLuSystem findBySytemCode2(FmsLuSystem luSystemEntity);

    public FmsLuSystem searchbysystemidofsyscode(int sysId);

    public FmsLuSystem getSystembyId(FmsLuSystem fmsLuSystem);

//    public List<FmsLuSystem> getFmsVoucherSearchingParameterList();
    public List<FmsLuSystem> getFmsLuSystemSearchingParameterList();
    public List<FmsLuSystem> searchAllVochNo(FmsLuSystem fmsLuSystem);
}
