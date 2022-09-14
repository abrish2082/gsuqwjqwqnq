/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic.admin;

import et.gov.eep.fcms.entity.Vocher.FmsVoucher;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import et.gov.eep.fcms.entity.admin.FmsCostCenter;
import et.gov.eep.fcms.entity.admin.FmsLuSystem;
import et.gov.eep.fcms.mapper.admin.FmsLuSystemFacade;

/**
 *
 * @author muller
 */
@Stateless
public class FmsLuSystemBean implements FmsLuSystemBeanLocal {

    //<editor-fold defaultstate="collapsed" desc="@EJB"> 
    @EJB
    FmsLuSystemFacade fmsLuSystemFacade;

    //</editor-fold>
    /**
     *
     * @return
     */
   @Override
    public List<FmsLuSystem> searchAllVochNo(FmsLuSystem fmsLuSystem) {
        return fmsLuSystemFacade.getVoch1No(fmsLuSystem );
    }
@Override
    public List<FmsLuSystem> getFmsLuSystemSearchingParameterList() {
        return fmsLuSystemFacade.getFmsLuSystemSearchingParameterList();
    }
    @Override
    public List<FmsLuSystem> findAll() {
        return fmsLuSystemFacade.findAll();
    }

    @Override
    public List<FmsLuSystem> findProjSystem() {
        return fmsLuSystemFacade.findProjSystem();
    }

    @Override
    public List<FmsLuSystem> findOprSystem() {
        return fmsLuSystemFacade.findOprSystem();
    }

    /**
     *
     * @param fmsLuSystem
     * @return
     */
    @Override
    public FmsLuSystem getSysDetail(FmsLuSystem fmsLuSystem) {
        return fmsLuSystemFacade.getSystem(fmsLuSystem);
    }

    /**
     *
     * @param fmsLuSystem
     */
    @Override
    public void create(FmsLuSystem fmsLuSystem) {
        fmsLuSystemFacade.create(fmsLuSystem);
    }

    /**
     *
     * @param fmsLuSystem
     */
    @Override
    public void edit(FmsLuSystem fmsLuSystem) {
        fmsLuSystemFacade.edit(fmsLuSystem);
    }

    /**
     *
     * @param fmsLuSystem
     */
    @Override
    public void deleteSys(FmsLuSystem fmsLuSystem) {
        fmsLuSystemFacade.remove(fmsLuSystem);
    }

    /**
     *
     * @param fmsCostCenter
     * @return
     */
    @Override
    public List<FmsCostCenter> getCostC(FmsCostCenter fmsCostCenter) {
        return fmsLuSystemFacade.getCosTCenter(fmsCostCenter);
    }

    /**
     *
     * @return
     */
    @Override
    public List<FmsLuSystem> activeSystem() {
        return fmsLuSystemFacade.getActiveSystem();
    }

    /**
     *
     * @return
     */
    @Override
    public List<FmsLuSystem> activeSystemForCapi() {
        return fmsLuSystemFacade.getActiveSystemForCapitalBgt();
    }

    @Override
    public ArrayList<FmsLuSystem> findBySytemCode(FmsLuSystem luSystemEntity) {
        return fmsLuSystemFacade.findBySytemCode(luSystemEntity);
    }

    @Override
    public FmsLuSystem findBySytemCode2(FmsLuSystem luSystemEntity) {
        return fmsLuSystemFacade.findBySytemCode2(luSystemEntity);
    }

    @Override
    public ArrayList<FmsLuSystem> findBySytemCodeLike(FmsLuSystem luSystemEntity) {
        return fmsLuSystemFacade.findBySytemCodeLike(luSystemEntity);
    }

    //mahi
    @Override
    public FmsLuSystem searchbysystemidofsyscode(int sysId) {
        return fmsLuSystemFacade.findBySytemId(sysId);
    }

    @Override
    public FmsLuSystem getSystembyId(FmsLuSystem fmsLuSystem) {
        return fmsLuSystemFacade.getSystembyId(fmsLuSystem);

    }

}
