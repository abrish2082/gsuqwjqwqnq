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
import et.gov.eep.fcms.entity.admin.FmsGeneralLedger;
import et.gov.eep.fcms.mapper.admin.FmsGeneralLedgerFacade;

/**
 *
 * @author Bin
 */
@Stateless
public class FmsGeneralLedgerBean implements FmsGeneralLedgerBeanLocal {

//<editor-fold defaultstate="collapsed" desc="@EJB">
    @EJB
    FmsGeneralLedgerFacade fmsGeneralLedgerFacade;
//</editor-fold>

    /**
     *
     * @return
     */
    @Override
    public List<FmsGeneralLedger> findAll() {
        return fmsGeneralLedgerFacade.findAll();
    }

    @Override
    public List<FmsGeneralLedger> getOprGLAccount() {
        return fmsGeneralLedgerFacade.getOprGLAccount();
    }

    @Override
    public List<FmsGeneralLedger> getProjGLAccount() {
        return fmsGeneralLedgerFacade.getProjGLAccount();
    }

    /**
     *
     * @param fmsGeneralLedger
     * @return
     */
    @Override
    public FmsGeneralLedger getByAccountTitle(FmsGeneralLedger fmsGeneralLedger) {
        return fmsGeneralLedgerFacade.getByACCountTitle(fmsGeneralLedger);
    }

    /**
     *
     * @param fmsGeneralLedger
     */
    @Override
    public void create(FmsGeneralLedger fmsGeneralLedger) {
        fmsGeneralLedgerFacade.create(fmsGeneralLedger);
    }

    /**
     *
     * @param fmsGeneralLedger
     */
    @Override
    public void edit(FmsGeneralLedger fmsGeneralLedger) {
        fmsGeneralLedgerFacade.edit(fmsGeneralLedger);
    }

    /**
     *
     * @param fmsGeneralLedger
     */
    @Override
    public void delete(FmsGeneralLedger fmsGeneralLedger) {
        fmsGeneralLedgerFacade.remove(fmsGeneralLedger);
    }

    /**
     *
     * @param fmsGeneralLedger
     * @return
     */
    @Override
    public FmsGeneralLedger getGLDetail(FmsGeneralLedger fmsGeneralLedger) {
        return fmsGeneralLedgerFacade.getGeneralLedger(fmsGeneralLedger);
    }

    @Override
    public FmsGeneralLedger getGLDetailByID(FmsGeneralLedger fmsGeneralLedger) {
        return fmsGeneralLedgerFacade.getByGlId(fmsGeneralLedger);
    }

    /**
     *
     * @return
     */
    @Override
    public List<FmsGeneralLedger> getGLALL() {
        return fmsGeneralLedgerFacade.findAll();

    }

    /**
     *
     * @param generalLedger
     * @return
     */
    @Override
    public boolean getGeneralLedgerDup(FmsGeneralLedger generalLedger) {
        return fmsGeneralLedgerFacade.getGeneralLedgerDup(generalLedger);
    }

    /**
     *
     * @param generalLedger
     * @return
     */
    @Override
    public List<FmsGeneralLedger> searchGL(FmsGeneralLedger generalLedger) {
        return fmsGeneralLedgerFacade.searchGL(generalLedger);
    }

    /**
     *
     * @param generalLedger
     * @return
     */
    @Override
    public FmsGeneralLedger getGLAccountInfo(FmsGeneralLedger generalLedger) {
        return fmsGeneralLedgerFacade.getGLAccountInfo(generalLedger);
    }

    /**
     *
     * @return
     */
    @Override
    public List<FmsGeneralLedger> getGeneralLederList() {
        return fmsGeneralLedgerFacade.findAll();
    }

    /**
     *
     * @param generalLedger
     * @return
     */
    @Override
    public FmsGeneralLedger getGlAccountCodeInfo(FmsGeneralLedger generalLedger) {
        return fmsGeneralLedgerFacade.getGlAccountCodeInfo(generalLedger);

    }

    /**
     *
     * @param generalLedger
     * @return
     */
    @Override
    public List<FmsGeneralLedger> searchGlAccountCode(FmsGeneralLedger generalLedger) {
        return fmsGeneralLedgerFacade.searchGlAccountCode(generalLedger);
    }

    /**
     *
     * @param glCode
     * @return
     */
    @Override
    public List<FmsGeneralLedger> searchGlAccountCode(String glCode) {
        return fmsGeneralLedgerFacade.searchGlAccountCode(glCode);
    }

    /**
     *
     * @param glCode
     * @return
     */
    @Override
    public List<FmsGeneralLedger> searchByGLCode(String glCode) {
        return fmsGeneralLedgerFacade.searchByGLCode(glCode);
    }

    /**
     *
     * @return
     */
    @Override
    public ArrayList<FmsGeneralLedger> getGeneralLedgerCodeList() {
        return fmsGeneralLedgerFacade.getGeneralLedgerCodeList();
    }

    /**
     *
     * @return
     */
    @Override
    public List<FmsGeneralLedger> getGLAccountALL() {
        return fmsGeneralLedgerFacade.searchGlAccountCode();
    }

    @Override
    public ArrayList<FmsGeneralLedger> searchGenLedger(FmsGeneralLedger fmsGeneralLedger) {
        return fmsGeneralLedgerFacade.searchGenLedger(fmsGeneralLedger);
    }

    @Override
    public FmsGeneralLedger getGlCode(FmsGeneralLedger generalLedger) {
        return fmsGeneralLedgerFacade.getGlcode(generalLedger);

    }

    @Override
    public FmsGeneralLedger getByGlId(FmsGeneralLedger fmsGeneralLedger) {
        return fmsGeneralLedgerFacade.getByGlId(fmsGeneralLedger);
    }

    @Override
    public List<FmsGeneralLedger> getByAccountType(Integer accountType) {
        return fmsGeneralLedgerFacade.getByAccountType(accountType);
    }

    @Override
    public List<FmsGeneralLedger> getGLListForFixedAsset() {
        return fmsGeneralLedgerFacade.getGLListForFixedAsset();
    }

    @Override
    public FmsGeneralLedger findByMasterId(int id) {

        return fmsGeneralLedgerFacade.findById(id);
    }

    @Override
    public List<FmsGeneralLedger> getFmsGeneralLedgerSearchingParameterList() {
        return fmsGeneralLedgerFacade.getFmsGeneralLedgerSearchingParameterList();
    }
    
    public List<FmsGeneralLedger> searchAllVochNo(FmsGeneralLedger fmsGeneralLedger){
        return fmsGeneralLedgerFacade.getGeneralLagers(fmsGeneralLedger);
    }
}
