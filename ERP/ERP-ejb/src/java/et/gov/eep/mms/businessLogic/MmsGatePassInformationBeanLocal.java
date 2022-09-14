/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.businessLogic;

import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import java.util.List;
import javax.ejb.Local;
import et.gov.eep.mms.entity.MmsGatePassInformation;

/**
 *
 * @author Minab
 */
@Local
public interface MmsGatePassInformationBeanLocal {

    /**
     *
     * @param gatepassInformation
     */
    void create(MmsGatePassInformation gatepassInformation);

    /**
     *
     * @param gatepassInformation
     */
    void edit(MmsGatePassInformation gatepassInformation);

    /**
     *
     * @param gatePassEntity
     * @return
     */
    public List<MmsGatePassInformation> searchGatePassByParameterPrefix(MmsGatePassInformation gatePassEntity);

    /**
     *
     * @param gatePassEntity
     * @return
     */
    public List<MmsGatePassInformation> searchGatePassByParameterContains(MmsGatePassInformation gatePassEntity);

    /**
     *
     * @return
     */
    public List<MmsGatePassInformation> searchAllGatePassInfo();

    /**
     *
     * @return
     */
    public MmsGatePassInformation getLastGatePassNo();

    public List<MmsGatePassInformation> searchGatePassByParameterPrefixAndProcessedBy(MmsGatePassInformation gatePassEntity);

    public List<MmsGatePassInformation> searchGatePassByProcessedBy(MmsGatePassInformation gatePassEntity);

    public List<String> getMmsGatePassInfoColumnNameList();

    public List<MmsGatePassInformation> getGatePassListsByParameter(ColumnNameResolver columnNameResolver, MmsGatePassInformation gatePassEntity, String columnValue);

}
