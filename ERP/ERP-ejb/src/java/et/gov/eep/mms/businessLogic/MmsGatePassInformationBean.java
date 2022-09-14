/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.businessLogic;

import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import et.gov.eep.mms.entity.MmsGatePassInformation;
import et.gov.eep.mms.mapper.MmsGatePassInformationFacade;

/**
 *
 * @author Minab
 */
@Stateless
public class MmsGatePassInformationBean implements MmsGatePassInformationBeanLocal {

    @EJB
    MmsGatePassInformationFacade gatePassInfoFacade;

    /**
     *
     * @param gatepassInformation
     */
    @Override
    public void create(MmsGatePassInformation gatepassInformation) {
        gatePassInfoFacade.create(gatepassInformation);
    }

    /**
     *
     * @param gatepassInformation
     */
    @Override
    public void edit(MmsGatePassInformation gatepassInformation) {
        gatePassInfoFacade.edit(gatepassInformation);
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    /**
     *
     * @param gatePassEntity
     * @return
     */
    @Override
    public List<MmsGatePassInformation> searchGatePassByParameterPrefix(MmsGatePassInformation gatePassEntity) {
        return gatePassInfoFacade.searchGatePassByParameterPrefix(gatePassEntity);
    }

    /**
     *
     * @param gatePassEntity
     * @return
     */
    @Override
    public List<MmsGatePassInformation> searchGatePassByParameterPrefixAndProcessedBy(MmsGatePassInformation gatePassEntity) {
        return gatePassInfoFacade.searchGatePassByParameterPrefixAndProcessedBy(gatePassEntity);
    }

    @Override
    public List<MmsGatePassInformation> searchGatePassByParameterContains(MmsGatePassInformation gatePassEntity) {
        return gatePassInfoFacade.searchGatePassByParameterContains(gatePassEntity);
    }

    @Override
    public List<MmsGatePassInformation> searchGatePassByProcessedBy(MmsGatePassInformation gatePassEntity) {
        return gatePassInfoFacade.searchGatePassByProcessedBy(gatePassEntity);
    }

    /**
     *
     * @return
     */
    @Override
    public List<MmsGatePassInformation> searchAllGatePassInfo() {
        return gatePassInfoFacade.findAll();
    }

    /**
     *
     * @return
     */
    @Override
    public MmsGatePassInformation getLastGatePassNo() {
        return gatePassInfoFacade.getLastGetpassNo();
    }

    @Override
    public List<String> getMmsGatePassInfoColumnNameList() {
        return gatePassInfoFacade.getMmsGatePassInfoColumnNameList();
    }

    @Override
    public List<MmsGatePassInformation> getGatePassListsByParameter(ColumnNameResolver columnNameResolver, MmsGatePassInformation gatePassEntity, String columnValue) {
        return gatePassInfoFacade.getGatePassListsByParameter(columnNameResolver, gatePassEntity, columnValue);
    }
}
