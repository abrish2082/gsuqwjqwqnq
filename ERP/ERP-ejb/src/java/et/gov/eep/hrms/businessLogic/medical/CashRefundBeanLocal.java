/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.medical;

import et.gov.eep.commonApplications.entity.Status;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.medical.HrLocalMedInstitutions;
import et.gov.eep.hrms.entity.medical.HrLocalMedSettlements;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;
import javax.faces.model.SelectItem;

/**
 *
 * @author INSA
 */
@Local
public interface CashRefundBeanLocal {

    public List<HrLocalMedInstitutions> findAll();

    public void save(HrLocalMedSettlements hrLocalMedSettlements);

    public void edit(HrLocalMedSettlements HrLocalMedSettlements);

    public void saveOrUpdate(HrLocalMedSettlements hrLocalMedSettlements);

    public ArrayList<HrLocalMedSettlements> findAllRequest();

    public ArrayList<HrLocalMedSettlements> findByName(HrEmployees empName);

    public List<SelectItem> filterByStatus();

    public List<HrLocalMedSettlements> loadMedicaCashRefund(int status);

    public HrLocalMedSettlements getSelectedRequest(int request);

    public List<HrLocalMedSettlements> loadMedSettlementList(Status status, int userId);

    public List<HrLocalMedSettlements> loadPrepaMedSettlementList(HrLocalMedSettlements hrLocalMedSettlements);

    public List<HrLocalMedSettlements> loadSettlementList(Status status, int userId);

    public List<HrLocalMedSettlements> loadReqMedSettList(Status status, int userId);

    public List<HrLocalMedSettlements> loadApproveMedSettList(Status status, int userId);
    
}
