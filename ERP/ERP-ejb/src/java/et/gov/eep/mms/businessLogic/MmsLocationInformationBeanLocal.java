/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.businessLogic;


import et.gov.eep.mms.entity.MmsLocationInformation;
import java.util.ArrayList;
import javax.ejb.Local;

/**
 *
 * @author kimmyo
 */
@Local
public interface MmsLocationInformationBeanLocal {

    /**
     *
     * @param locationInformation
     */
    void create(MmsLocationInformation locationInformation);

    /**
     *
     * @param locationInformation
     */
    void edit(MmsLocationInformation locationInformation);

    /**
     *
     * @param locationInformation
     * @return
     */
    ArrayList<MmsLocationInformation> searchStoreAndShelfInfo(MmsLocationInformation locationInformation);

    /**
     *
     * @param locationInformation
     * @return
     */
    MmsLocationInformation getMmsLocationInformation(MmsLocationInformation locationInformation);

    /**
     *
     * @param locationInformation
     * @return
     */
    boolean getMmsLocationInformationDup(MmsLocationInformation locationInformation);
}
