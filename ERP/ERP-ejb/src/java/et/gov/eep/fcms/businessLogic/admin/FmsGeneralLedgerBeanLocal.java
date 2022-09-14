/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic.admin;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;
import et.gov.eep.fcms.entity.admin.FmsGeneralLedger;

/**
 *
 * @author Bin
 */
@Local
public interface FmsGeneralLedgerBeanLocal {

    public FmsGeneralLedger getByAccountTitle(FmsGeneralLedger fmsGeneralLedger);

    public boolean getGeneralLedgerDup(FmsGeneralLedger generalLedger);

    public void create(FmsGeneralLedger fmsGeneralLedger);

    public void edit(FmsGeneralLedger fmsGeneralLedger);

    public void delete(FmsGeneralLedger fmsGeneralLedger);

    public FmsGeneralLedger getGLDetail(FmsGeneralLedger fmsGeneralLedger);

    public FmsGeneralLedger getGLAccountInfo(FmsGeneralLedger generalLedger);

    public FmsGeneralLedger getGLDetailByID(FmsGeneralLedger fmsGeneralLedger);

    public FmsGeneralLedger getGlCode(FmsGeneralLedger generalLedger);

    public FmsGeneralLedger getByGlId(FmsGeneralLedger fmsGeneralLedger);

    public FmsGeneralLedger getGlAccountCodeInfo(FmsGeneralLedger generalLedger);

    public FmsGeneralLedger findByMasterId(int id);

    public List<FmsGeneralLedger> searchGL(FmsGeneralLedger generalLedger);

    public List<FmsGeneralLedger> getGeneralLederList();

    public List<FmsGeneralLedger> getGLALL();

    public List<FmsGeneralLedger> findAll();

    public List<FmsGeneralLedger> getOprGLAccount();

    public List<FmsGeneralLedger> getProjGLAccount();

    public List<FmsGeneralLedger> searchGlAccountCode(FmsGeneralLedger generalLedger);

    public List<FmsGeneralLedger> searchGlAccountCode(String glCode);

    public List<FmsGeneralLedger> searchByGLCode(String glCode);

    public List<FmsGeneralLedger> getGLAccountALL();

    public List<FmsGeneralLedger> getByAccountType(Integer accountType);

    public List<FmsGeneralLedger> getGLListForFixedAsset();

    public ArrayList<FmsGeneralLedger> getGeneralLedgerCodeList();

    public ArrayList<FmsGeneralLedger> searchGenLedger(FmsGeneralLedger fmsGeneralLedger);
    public List<FmsGeneralLedger> getFmsGeneralLedgerSearchingParameterList();
    public List<FmsGeneralLedger> searchAllVochNo(FmsGeneralLedger fmsGeneralLedger);

}
