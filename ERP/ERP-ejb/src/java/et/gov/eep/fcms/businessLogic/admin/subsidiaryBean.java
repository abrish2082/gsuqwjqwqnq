/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic.admin;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import et.gov.eep.fcms.entity.admin.FmsCostcSystemJunction;
import et.gov.eep.fcms.entity.admin.FmsGeneralLedger;
import et.gov.eep.fcms.entity.admin.FmsSubsidiaryLedger;
import et.gov.eep.fcms.mapper.admin.FmsSubsidiaryLedgerFacade;
import et.gov.eep.mms.entity.IfrsFixedAsset;

/**
 *
 * @author pc
 */
@Stateless
public class subsidiaryBean implements subsidiaryBeanLocal {

//<editor-fold defaultstate="collapsed" desc="@EJB">
    @EJB
    private FmsSubsidiaryLedgerFacade fmsSubsid1aryLedger1Facade;
//</editor-fold>

    /**
     *
     * @param subsidiary
     */
    @Override
    public void create(FmsSubsidiaryLedger subsidiary) {
        fmsSubsid1aryLedger1Facade.create(subsidiary);
    }

    /**
     *
     * @param subsidiary
     */
    @Override
    public void edit(FmsSubsidiaryLedger subsidiary) {
        fmsSubsid1aryLedger1Facade.edit(subsidiary);
    }

    /**
     *
     * @param subsidiary
     */
    @Override
    public void delete(FmsSubsidiaryLedger subsidiary) {
        fmsSubsid1aryLedger1Facade.remove(subsidiary);
    }

    /**
     *
     * @param subsidiary
     * @return
     */
    @Override
    public ArrayList<FmsSubsidiaryLedger> searchSubsidiaryCode(FmsSubsidiaryLedger subsidiary) {
        return fmsSubsid1aryLedger1Facade.searchSubsidaryByCode(subsidiary);
    }

    /**
     *
     * @param subsidaryName
     * @return
     */
    @Override
    public ArrayList<FmsSubsidiaryLedger> searchSubsidiaryByName(String subsidaryName) {
        return fmsSubsid1aryLedger1Facade.searchSubsidiaryByName(subsidaryName);
    }

    /**
     *
     * @param subsidiary
     * @return
     */
    @Override
    public FmsSubsidiaryLedger getSubsidiaryInfo(FmsSubsidiaryLedger subsidiary) {
        return fmsSubsid1aryLedger1Facade.getSubsidaryInfo(subsidiary);
    }

    /**
     *
     * @return
     */
    @Override
    public List<FmsSubsidiaryLedger> findAll() {
        return fmsSubsid1aryLedger1Facade.findAll();
    }

    /**
     *
     * @param fmsCostCenter
     * @param fmsGeneralLedger
     * @return
     */
    @Override
    public ArrayList<FmsSubsidiaryLedger> findSubLedger(FmsGeneralLedger fmsGeneralLedger) {
        return fmsSubsid1aryLedger1Facade.findSubsidiaryName(fmsGeneralLedger);
    }

    @Override
    public ArrayList<FmsSubsidiaryLedger> findSLbyGLandCCSS(FmsGeneralLedger fmsGeneralLedger, FmsCostcSystemJunction costcSystemJunction) {
        return fmsSubsid1aryLedger1Facade.findSLbyGLandCCSS(fmsGeneralLedger, costcSystemJunction);
    }

    /**
     *
     * @return
     */
    @Override
    public ArrayList<FmsSubsidiaryLedger> getSubsidiaryLedgerCodeList() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     *
     * @param generalLedger
     * @return
     */
    @Override
    public List<FmsSubsidiaryLedger> getsubsidiaryLedgerList(FmsGeneralLedger generalLedger) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     *
     * @param subsidaryName
     * @return
     */
    @Override
    public ArrayList<FmsSubsidiaryLedger> findSubsidiaryName(String subsidaryName) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<FmsSubsidiaryLedger> searchSubsidiaries(FmsSubsidiaryLedger fmsSubsidiaryLedger) {
        return fmsSubsid1aryLedger1Facade.searchSubsidiaries(fmsSubsidiaryLedger);
    }

    @Override
    public FmsSubsidiaryLedger getSubsidiary(FmsSubsidiaryLedger subsidiary) {
        return fmsSubsid1aryLedger1Facade.getSubsidary(subsidiary);
    }

    @Override
    public FmsSubsidiaryLedger getLastSubsidiary() {
        return fmsSubsid1aryLedger1Facade.getLastSubsidary();
    }

    @Override
    public List<IfrsFixedAsset> getSLListByGlId(Integer generalLedgerId) {
        return fmsSubsid1aryLedger1Facade.getSLListByGlId(generalLedgerId);
    }

    @Override
    public FmsSubsidiaryLedger getSubsidiaryCode(int parseInt) {
        return fmsSubsid1aryLedger1Facade.getSubsidiaryCode(parseInt);
    }

    @Override
    public List<FmsSubsidiaryLedger> findSubsideryCodeByGlCode(FmsGeneralLedger fmsGeneralLedger) {
        return fmsSubsid1aryLedger1Facade.findSubsideryCodeByGlCode(fmsGeneralLedger);
    }

    @Override
    public List<FmsSubsidiaryLedger> findBySsCcJuncAndGL(FmsCostcSystemJunction fmsCostcSystemJunction, FmsGeneralLedger fmsGeneralLedger) {
        return fmsSubsid1aryLedger1Facade.findBySsCcJuncAndGL(fmsCostcSystemJunction, fmsGeneralLedger);
    }

    @Override
    public FmsSubsidiaryLedger findById(int fmsSubsidiaryLedger) {
        return fmsSubsid1aryLedger1Facade.findById(fmsSubsidiaryLedger);
    }

    @Override
    public FmsSubsidiaryLedger getSlCode(FmsSubsidiaryLedger fmsSubsidiaryLedger) {
        return fmsSubsid1aryLedger1Facade.getSlCode(fmsSubsidiaryLedger);
    }

    @Override
    public List<FmsSubsidiaryLedger> getAllSubListList() {
        return fmsSubsid1aryLedger1Facade.getFmsSubsidiaryLedgerSearchingParameterList();
    }
     @Override
    public List<FmsSubsidiaryLedger> searchAllVochNo(FmsSubsidiaryLedger fmsSubsidiaryLedger){
        return fmsSubsid1aryLedger1Facade.getVoch1No(fmsSubsidiaryLedger);
    }

}
